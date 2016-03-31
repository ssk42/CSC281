import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * @author knappa
 * @version 1.0
 */
public class TalkClient extends JPanel
        implements ActionListener, ServerListener, Runnable {

    private static final int PORT = 2000;
    private final JTextPane textPane;
    private final JTextField textField;
    private final NetworkDiscovery networkDiscovery;
    private Thread inputThread;
    private BufferedReader input;
    private PrintWriter output;

    public TalkClient() {

        networkDiscovery = new NetworkDiscovery(PORT, "Talk");
        networkDiscovery.addServerListener(this);
        networkDiscovery.startBroadcastingForServers();

        setLayout(new GridBagLayout());

        GridBagConstraints constraints;

        textPane = new JTextPane();
        textPane.setPreferredSize(new Dimension(300, 300));
        textPane.setEditable(false);
        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.BOTH;
        add(textPane, constraints);

        textField = new JTextField();
        textField.setColumns(80);
        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.fill = GridBagConstraints.BOTH;
        add(textField, constraints);

        textField.addActionListener(this);
    }

    public static void main(String[] args) {
        TalkClient talkClient = new TalkClient();
        JFrame frame = new JFrame();
        frame.add(talkClient);
        frame.setTitle("Talk Client");
        frame.setSize(300, 300);
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
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

    /**
     * Called when a server is found
     *
     * @param address address of server
     */
    @Override
    public void onServerFound(InetAddress address) {
        try {
            Socket socket = new Socket(address.getHostName(), PORT);
            output = new PrintWriter(socket.getOutputStream(), true);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            inputThread = new Thread(this);
            inputThread.start();
        } catch (IOException ignored) {
        }
    }

    /**
     * periodically polls for input
     */
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ignored) {
            }

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
}
