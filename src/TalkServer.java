import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

/**
 * @author knappa
 * @version 1.0
 */
public class TalkServer implements ClientListener {

    private static final int PORT = 2000;
    private final ServerSocket serverSocket;
    private final Thread socketListenerThread;
    private final Thread messageBroadcasterThread;
    private Set<PrintWriter> outputs;
    private Set<BufferedReader> inputs;
    private NetworkDiscovery networkDiscovery;

    public TalkServer() throws IOException {
        outputs = new HashSet<>();
        inputs = new HashSet<>();

        serverSocket = new ServerSocket(PORT);

        socketListenerThread = new Thread(new SocketListener());
        socketListenerThread.start();

        messageBroadcasterThread = new Thread(new MessageBroadcaster());
        messageBroadcasterThread.start();

        networkDiscovery = new NetworkDiscovery(PORT, "Talk");
        networkDiscovery.addClientListener(this);
        networkDiscovery.startListeningForClients();
    }

    public static void main(String[] args) throws IOException {
        TalkServer talkServer = new TalkServer();
    }

    /**
     * Called when a server is found. Adds appropriate input/output to the lists.
     *
     * @param address address of server
     */
    @Override
    public void onClientFound(InetAddress address) {
        System.out.println("Found a client:");
        System.out.println(address);
    }

    private void notifyClients(String message) {
        for (PrintWriter output : outputs) {
            System.out.println("notifying " + output);
            output.println(message);
        }
    }

    private class SocketListener implements Runnable {
        @Override
        public void run() {
            while (true) {
                System.out.println("listening...");
                try {
                    Socket socket = serverSocket.accept();
                    System.out.println("obtained socket from " + socket.getInetAddress());

                    PrintWriter out =
                            new PrintWriter(socket.getOutputStream(), true);
                    BufferedReader in =
                            new BufferedReader(
                                    new InputStreamReader(socket.getInputStream()));

                    outputs.add(out);
                    inputs.add(in);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class MessageBroadcaster implements Runnable {
        @Override
        public void run() {
            while (true) {

                try { Thread.sleep(100); } catch (InterruptedException ignored) { }

                for (BufferedReader input : inputs) {
                    try {
                        /* readLine blocks, so check if ready */
                        if (input != null && input.ready()) {
                            /* readLine returns null if there is no more input */
                            String message = input.readLine();

                            /* if there is a message */
                            if (message != null && message.length() > 0) {
                                notifyClients(message);
                                System.out.println(message);
                            }

                        }
                    } catch (IOException ignored) { }
                }

            }
        }
    }
}
