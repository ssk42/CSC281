import javax.swing.JFrame;
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
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author knappa
 * @version 1.0
 */
public class WebGrabber extends JPanel implements ActionListener {

    private final JTextPane textPane;
    private final JTextField textField;

    public WebGrabber() {

        setLayout(new GridBagLayout());

        GridBagConstraints constraints;

        textPane = new JTextPane();
        textPane.setPreferredSize(new Dimension(300, 300));
        textPane.setEditable(false);
        textPane.setContentType("text/html");
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
        WebGrabber webGrabber = new WebGrabber();
        JFrame frame = new JFrame();
        frame.add(webGrabber);
        frame.setTitle("WebGrabber");
        frame.setSize(300, 300);
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        try {

            URL url = new URL(textField.getText());

            // print out url info to stdout

            System.out.println("protocol = " + url.getProtocol());
            System.out.println("authority = " + url.getAuthority());
            System.out.println("host = " + url.getHost());
            System.out.println("port = " + url.getPort());
            System.out.println("path = " + url.getPath());
            System.out.println("query = " + url.getQuery());
            System.out.println("filename = " + url.getFile());
            System.out.println("ref = " + url.getRef());

            openURL(url);

        } catch (MalformedURLException e1) {
            textPane.setText("URL Parse Error");
        }

    }

    private void openURL(URL url) {
        try {
            BufferedReader in =
                    new BufferedReader(
                            new InputStreamReader(url.openStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
            }
            in.close();

            textPane.setPage(url);

        } catch (IOException e) {
            textPane.setText("URL Open error");
        }
    }

}
