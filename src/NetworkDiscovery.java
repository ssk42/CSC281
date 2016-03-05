import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

/**
 * @author knappa
 * @version 1.0
 */
public class NetworkDiscovery {

    private final int MTU;
    private final String SYN;
    private final String ACK;
    private Set<InetAddress> clientAddresses;
    private Set<InetAddress> serverAddresses;
    private Set<InetAddress> hostAddresses;
    private int port;
    private Thread listeningThread;
    private boolean listening;
    private Thread serverFinderThread;
    private boolean broadcasting;
    private ServerFinder serverFinder;

    public NetworkDiscovery(int port, String serviceName) {
        SYN = "SYN_" + serviceName;
        ACK = "ACK_" + serviceName;
        this.port = port;
        clientAddresses = new HashSet<>();
        serverAddresses = new HashSet<>();
        hostAddresses = new HashSet<>();

        // add our host addresses
        try {
            Enumeration interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = (NetworkInterface) interfaces.nextElement();
                for (InterfaceAddress address : networkInterface.getInterfaceAddresses())
                    hostAddresses.add(address.getAddress());
            }
        }
        catch (SocketException ignored) {}

        /* find the max MTU on our (valid, active, non-loopback - if appropriate) interfaces */
        int maxMTU = 0;
        try {
            Enumeration interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = (NetworkInterface) interfaces.nextElement();

                if (!networkInterface.isLoopback() && networkInterface.isUp()) {
                    maxMTU = Math.max(maxMTU, networkInterface.getMTU());
                }
            }
        }
        catch (SocketException ignored) {}
        /* MTU should be at least as big as an ethernet packet
         * https://en.wikipedia.org/wiki/Maximum_transmission_unit
         */
        MTU = Math.max(maxMTU, 1500);
    }

    public static void main(String[] args) {
        if (args.length > 0) {
            NetworkDiscovery networkDiscovery = new NetworkDiscovery(8888, "test");
            try {
                if (args[0].equals("server")) {
                    networkDiscovery.startListening();
                    Thread.sleep(60_000);
                    networkDiscovery.stopListening();
                    for (InetAddress address : networkDiscovery.foundClientAddresses())
                        System.out.println(address);
                } else if (args[0].equals("client")) {
                    networkDiscovery.startBroadcastingForServers();
                    Thread.sleep(60_000);
                    networkDiscovery.stopBroadcasting();
                    for (InetAddress address : networkDiscovery.foundServerAddresses())
                        System.out.println(address);
                }
            }
            catch (InterruptedException ignored) { }
        } else {
            System.out.println("tell me what to do: should I be the *client* or *server*");
        }
    }

    public void startListening() {
        listening = true;
        if (listeningThread != null) {
            listeningThread.interrupt();
            listeningThread.start();
        } else {
            listeningThread = new Thread(new ClientFinder());
            listeningThread.start();
        }
    }

    public void stopListening() {
        listening = false;
        listeningThread.interrupt();
    }

    public Set<InetAddress> foundClientAddresses() {
        return clientAddresses;
    }

    public void startBroadcastingForServers() {
        startBroadcastingForServers(1);
    }

    public void startBroadcastingForServers(int numServers) {
        broadcasting = true;
        if (serverFinderThread != null) {
            serverFinderThread.interrupt();
        } else {
            serverFinder = new ServerFinder();
            serverFinderThread = new Thread(serverFinder);
        }
        serverFinder.setNumServersToFind(numServers);
        serverFinderThread.start();
    }

    public void stopBroadcasting() {
        broadcasting = false;
        serverFinderThread.interrupt();
    }

    public Set<InetAddress> foundServerAddresses() {
        return serverAddresses;
    }

    /**
     * find some number of servers via UDP broadcast
     */
    private class ServerFinder implements Runnable {
        private int numServersToFind;

        public void setNumServersToFind(int numServersToFind) {
            this.numServersToFind = numServersToFind;
        }

        @Override
        public void run() {
            try {

                //System.out.println("b");

                DatagramSocket socket = new DatagramSocket(port);
                socket.setBroadcast(true);

                byte[] sendData = SYN.getBytes();

                @SuppressWarnings("Convert2Lambda")
                Thread broadcaster = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        do {
                            try {

                                /* broadcast on the generic local network */
                                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,
                                        InetAddress.getByName("255.255.255.255"), port);
                                System.out.println("Broadcast to 255.255.255.255");
                                socket.send(sendPacket);

                                /* broadcast on the specific local netmask of each interface */
                                Enumeration interfaces = NetworkInterface.getNetworkInterfaces();
                                while (interfaces.hasMoreElements() && broadcasting) {
                                    NetworkInterface networkInterface = (NetworkInterface) interfaces.nextElement();

                                    if (!networkInterface.isLoopback() && networkInterface.isUp()) {
                                        InetAddress broadcast;
                                        for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
                                            broadcast = interfaceAddress.getBroadcast();
                                            if (broadcast != null) {
                                                sendPacket = new DatagramPacket(sendData, sendData.length, broadcast, port);
                                                System.out.println("Broadcast to " + broadcast);
                                                socket.send(sendPacket);
                                            }
                                        }
                                    }
                                }
                            }
                            catch (IOException ignored) { }

                            /* wait one half second so we don't flood the network */
                            try { Thread.sleep(500); } catch (InterruptedException ignored) { }

                        } while (broadcasting);
                        /* cleanup */
                        socket.close();
                    }
                });
                broadcaster.start();

                byte[] receiveBuffer = new byte[MTU];
                while (numServersToFind > 0) {
                    DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                    socket.receive(receivePacket);

                    String message = new String(receivePacket.getData()).trim();
                    if (message.equals(ACK)) {
                        serverAddresses.add(receivePacket.getAddress());
                        System.out.println("found server: " + receivePacket.getAddress());
                        numServersToFind--;
                    }
                }

                /* stop broadcasting */
                broadcasting = false;
                //broadcaster.interrupt();

                /* cleanup */
                socket.close();

            }
            catch (IOException ignored) { }
        }


    }

    /**
     * listen for clients via UDP broadcast
     */
    private class ClientFinder implements Runnable {
        @Override
        public void run() {

            DatagramSocket socket;
            try {
                socket = new DatagramSocket(port, InetAddress.getByName("0.0.0.0"));
                socket.setBroadcast(true);
            }
            catch (SocketException | UnknownHostException e) {
                e.printStackTrace();
                return;
            }

            System.out.println("a");

            while (listening) {

                // Receive packet
                byte[] receiveBuffer = new byte[MTU];
                DatagramPacket packet = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                try { socket.receive(packet); }
                catch (IOException e) {
                    System.out.println("failed to receive");
                    e.printStackTrace();
                }

                System.out.println(packet);

                    /* determine if this is a packet from someone else (if appropriate), containing the signal */
                String message = new String(packet.getData()).trim();
                if (message.equals(SYN) && !hostAddresses.contains(packet.getAddress())) {

                        /* send ack */
                    byte[] sendData = ACK.getBytes();
                    DatagramPacket sendPacket = new DatagramPacket(sendData,
                            sendData.length, packet.getAddress(), packet.getPort());
                    try { socket.send(sendPacket); }
                    catch (IOException e) {
                        System.out.println("failed to send");
                        e.printStackTrace();
                    }

                        /* record client */
                    clientAddresses.add(packet.getAddress());
                    System.out.println("found client: " + packet.getAddress());
                }
            }

        }
    }

}
