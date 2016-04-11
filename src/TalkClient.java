import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.WindowConstants;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author knappa
 * @version 1.0
 */
public class TalkClient extends JPanel
        implements ActionListener, ServerListener, Runnable {

    private static final int PORT = 2000;
    private final JTextPane textPane;
    private final JTextField textField;
    private final JTextField hostField;
    //private final NetworkDiscovery networkDiscovery;
    private String serverHostname;
    private InetAddress serverAddress;
    private Thread inputThread;
    private BufferedReader input;
    private PrintWriter output;
    private Socket socket;

    public TalkClient(String defaultHostname) {

        /*
        networkDiscovery = new NetworkDiscovery(PORT, "Talk");
        networkDiscovery.addServerListener(this);
        networkDiscovery.startBroadcastingForServers();
        */

        setLayout(new GridBagLayout());

        GridBagConstraints constraints;

        textPane = new JTextPane();
        textPane.setPreferredSize(new Dimension(300, 300));
        textPane.setEditable(false);
        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.BOTH;
        add(textPane, constraints);

        textField = new JTextField();
        textField.setColumns(80);
        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.BOTH;
        add(textField, constraints);

        textField.addActionListener(this);

        JLabel label = new JLabel("Server Name/IP");
        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.BOTH;
        add(label, constraints);

        hostField = new JTextField();
        hostField.setColumns(20);
        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.BOTH;
        add(hostField, constraints);


        /* connect to default server */
        this.serverHostname = defaultHostname;
        hostField.setText(defaultHostname);
        try {
            this.serverAddress = InetAddress.getByName(serverHostname);
            onServerFound(serverAddress);
        } catch (UnknownHostException e) {
            hostField.setText("Server Name Error");
        }

        hostField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                serverHostname = hostField.getText();
                try {
                    serverAddress = InetAddress.getByName(serverHostname);
                    onServerFound(serverAddress);
                } catch (UnknownHostException exp) {
                    hostField.setText("Server Name Error");
                }
            }
        });
    }

    /**
     * Called when a server is found
     *
     * @param address address of server
     */
    @Override
    public void onServerFound(InetAddress address) {

        try {

            /* close out any existing open sockets/writers/readers */
            if (output != null) output.close();
            if (input != null) input.close();
            if (socket != null) socket.close();

            socket = new Socket(address.getHostName(), PORT);

            output = new PrintWriter(socket.getOutputStream(), true);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            inputThread = new Thread(this);
            inputThread.start();
        } catch (IOException ignored) {
        }
    }

    public static void main(String[] args) {
        TalkClient talkClient = new TalkClient("www.instanton.org");
        JFrame frame = new JFrame();
        frame.add(talkClient);
        frame.setTitle("Talk Client");
        frame.setSize(300, 300);
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    /**
     * periodically polls for input
     */
    @Override
    public void run() {
        while (true) {
            try { Thread.sleep(100); } catch (InterruptedException ignored) { }

            try {
                if (input != null && input.ready()) {
                    String received = input.readLine();

                    if (received != null && received.length() > 0) {
                        String existingText = textPane.getText();
                        textPane.setText(existingText + received + "\n");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Invoked when an action occurs. (When you hit return input the textField)
     *
     * @param e ignored
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (output != null) {
            output.println(textField.getText());
        }
        textField.setText("");
    }


}
