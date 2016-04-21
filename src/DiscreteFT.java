import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;

/**
 * @author knappa
 * @version 1.0
 */
public class DiscreteFT extends JComponent {

    //private static final int BUFFER_SIZE = 524288; // approx 1 min
    //private static final int BUFFER_SIZE = 16384;
    private static final int BUFFER_SIZE = 8192; // just over a second
    private static final float HZ = 8000.0f;
    private final AudioFormat format;
    private final BufferDisplay bufferDisplay;
    private final DataLine.Info speakerInfo;
    private final DataLine.Info micInfo;
    private final FourierDisplay cosineFourierDisplay;
    private final FourierDisplay sineFourierDisplay;
    private byte[] buffer;
    private double[] ftCosines, ftSines;

    public DiscreteFT() throws Exception {
        buffer = new byte[BUFFER_SIZE];
        ftCosines = new double[BUFFER_SIZE];
        ftSines = new double[BUFFER_SIZE];
        format = new AudioFormat(HZ, 8, 1, true, true);

        // format is an AudioFormat object
        micInfo = new DataLine.Info(TargetDataLine.class, format);
        if (!AudioSystem.isLineSupported(micInfo))
            throw new Exception("microphone not supported");

        speakerInfo = new DataLine.Info(Clip.class, format);
        if (!AudioSystem.isLineSupported(speakerInfo))
            throw new Exception("speaker not supported");

        setLayout(new GridBagLayout());

        GridBagConstraints constraints;

        JButton recordButton = new JButton("Record");
        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        add(recordButton, constraints);
        recordButton.addActionListener(e -> captureAudio());

        JButton processButton = new JButton("Process");
        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 0;
        add(processButton, constraints);
        processButton.addActionListener(e -> processAudio());

        JButton restrictButton = new JButton("Restrict to Vocal Range");
        constraints = new GridBagConstraints();
        constraints.gridx = 2;
        constraints.gridy = 0;
        add(restrictButton, constraints);
        restrictButton.addActionListener(e -> restrictToVocalRange());

        JButton middleCButton = new JButton("Add Middle A");
        constraints = new GridBagConstraints();
        constraints.gridx = 3;
        constraints.gridy = 0;
        add(middleCButton, constraints);
        middleCButton.addActionListener(e -> addMiddleA());

        JButton harmonizeButton = new JButton("Harmonize");
        constraints = new GridBagConstraints();
        constraints.gridx = 4;
        constraints.gridy = 0;
        add(harmonizeButton, constraints);
        harmonizeButton.addActionListener(e -> perfect5th());


        JButton inverseFT = new JButton("Inverse FT");
        constraints = new GridBagConstraints();
        constraints.gridx = 5;
        constraints.gridy = 0;
        add(inverseFT, constraints);
        inverseFT.addActionListener(e -> reverseFFT());

        JButton playbackButton = new JButton("Playback");
        constraints = new GridBagConstraints();
        constraints.gridx = 5;
        constraints.gridy = 0;
        add(playbackButton);
        playbackButton.addActionListener(e -> playbackAudio());

        bufferDisplay = new BufferDisplay();
        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 6;
        add(bufferDisplay, constraints);

        cosineFourierDisplay = new FourierDisplay(ftCosines);
        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 6;
        add(cosineFourierDisplay, constraints);

        sineFourierDisplay = new FourierDisplay(ftSines);
        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 6;
        add(sineFourierDisplay, constraints);

    }

    private void playbackAudio() {
        try {
            Clip speaker = (Clip) AudioSystem.getLine(speakerInfo);
            System.out.println("playing back");
            speaker.open(format, buffer, 0, BUFFER_SIZE);
            speaker.start();
            System.out.println("done playing back");
        } catch (LineUnavailableException e) {
            e.printStackTrace();
            System.out.println("speaker oops");
        }
    }

    private void processAudio() {
        for (int i = 0; i < BUFFER_SIZE; i++) {
            double cosineSum = 0;
            double sineSum = 0;
            for (int j = 0; j < BUFFER_SIZE; j++) {
                cosineSum += buffer[j] * Math.cos(-(2 * Math.PI * i * j) / (double) BUFFER_SIZE);
                sineSum += buffer[j] * Math.sin(-(2 * Math.PI * i * j) / (double) BUFFER_SIZE);
            }
            ftCosines[i] = cosineSum / (double) BUFFER_SIZE;
            ftSines[i] = sineSum / (double) BUFFER_SIZE;
            //System.out.println(i + " " + ftSines[i]);
        }
        cosineFourierDisplay.updateImage();
        sineFourierDisplay.updateImage();
    }

    private void captureAudio() {

        // Obtain and open the microphone.
        System.out.println("capturing");

        try {
            TargetDataLine microphone = (TargetDataLine) AudioSystem.getLine(micInfo);
            microphone.open(format);
            microphone.flush();
            microphone.start();
            microphone.read(buffer, 0, BUFFER_SIZE);
            System.out.println("finished");
            microphone.close();
        } catch (LineUnavailableException ignored) { }

        bufferDisplay.updateImage();

        //System.out.println(Arrays.toString(buffer));


    }

    private void restrictToVocalRange() {

        double lowBound = 400;
        double highBound = 3500;

        for (int i = 0; i < lowBound * BUFFER_SIZE / HZ; i++) {
            ftCosines[i] = 0;
            ftSines[i] = 0;
        }

        for (int i = BUFFER_SIZE - 1; i >= highBound * BUFFER_SIZE / HZ; i--) {
            ftCosines[i] = 0;
            ftSines[i] = 0;
        }

        cosineFourierDisplay.updateImage();
        sineFourierDisplay.updateImage();
    }

    private void reverseFFT() {
        for (int i = 0; i < BUFFER_SIZE; i++) {
            double realSum = 0;
            //double imaginarySum = 0;
            for (int j = 0; j < BUFFER_SIZE; j++) {
                realSum += ftCosines[j] * Math.cos(2 * Math.PI * i * j / (double) BUFFER_SIZE);
                realSum -= ftSines[j] * Math.sin(2 * Math.PI * i * j / (double) BUFFER_SIZE);
                realSum -= ftSines[j] * Math.sin(2 * Math.PI * i * j / (double) BUFFER_SIZE);

                //imaginarySum += ftCosines[j] * Math.sin(2 * Math.PI * i * j / (double) BUFFER_SIZE);
                //imaginarySum += ftSines[j] * Math.cos(2 * Math.PI * i * j / (double) BUFFER_SIZE);
            }
            buffer[i] = (byte) (realSum);
            //System.out.println(i + " " + buffer[i] + " " + (imaginarySum/BUFFER_SIZE));
        }
        bufferDisplay.updateImage();
    }

    private void addMiddleA() {

        double middleA = 440.0;

        int location = (int) ((middleA) * BUFFER_SIZE / HZ);

        ftCosines[location] = 8;
        ftSines[location] = 0;

        cosineFourierDisplay.updateImage();
        sineFourierDisplay.updateImage();
    }

    private void perfect5th() {
        double[] temp = new double[ftSines.length];
        System.arraycopy(ftSines, 0, temp, 0, ftSines.length);
        for (int i = 0; i < ftSines.length; i++) {
            ftSines[i] = temp[i] + temp[i * 2 / 3];
        }

        System.arraycopy(ftCosines, 0, temp, 0, ftCosines.length);
        for (int i = 0; i < ftCosines.length; i++) {
            ftCosines[i] = temp[i] + temp[i * 2 / 3];
        }

        cosineFourierDisplay.updateImage();
        sineFourierDisplay.updateImage();
    }

    public static void main(String[] args) throws Exception {

        JFrame frame = new JFrame("audio");
        frame.add(new DiscreteFT());
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    private class BufferDisplay extends JComponent {
        private static final int X_SIZE = 500, Y_SIZE = 200;
        BufferedImage image;

        BufferDisplay() {
            setPreferredSize(new Dimension(X_SIZE, Y_SIZE));
            image = new BufferedImage(X_SIZE, Y_SIZE, BufferedImage.TYPE_INT_RGB);
            updateImage();
        }

        private void updateImage() {
            Graphics2D g2 = image.createGraphics();
            g2.setColor(Color.WHITE);
            g2.fillRect(0, 0, X_SIZE, Y_SIZE);

            // baseline
            g2.setColor(Color.RED);
            g2.drawLine(0, Y_SIZE / 2, X_SIZE, Y_SIZE / 2);

            int xPos, yPos;
            for (int i = 0; i < BUFFER_SIZE; i += 1) {
                xPos = (int) ((double) (X_SIZE * i) / BUFFER_SIZE);
                yPos = (int) ((Y_SIZE / 2) * ((double) buffer[i]) / (Byte.MAX_VALUE + 1) + (Y_SIZE / 2));
                image.setRGB(xPos, yPos, 0);
            }
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.drawImage(image, 0, 0, null);
        }
    }

    private class FourierDisplay extends JComponent {
        private static final int X_SIZE = 500, Y_SIZE = 200;
        BufferedImage image;
        double[] array;

        FourierDisplay(double[] array) {
            setPreferredSize(new Dimension(X_SIZE, Y_SIZE));
            image = new BufferedImage(X_SIZE, Y_SIZE, BufferedImage.TYPE_INT_RGB);
            this.array = array;
            updateImage();
        }

        private void updateImage() {
            Graphics2D g2 = image.createGraphics();
            g2.setColor(Color.WHITE);
            g2.fillRect(0, 0, X_SIZE, Y_SIZE);

            // baseline
            g2.setColor(Color.RED);
            g2.drawLine(0, Y_SIZE / 2, X_SIZE, Y_SIZE / 2);

            // savitzky-golay filter, to make display better
            double[] filteredValues = new double[array.length];
            for (int i = 2; i < array.length - 2; i++) {
                filteredValues[i] = (-3.0 * array[i - 2] + 12.0 * array[i - 1] + 17.0 * array[i]
                        - 12.0 * array[i + 1] + 3.0 * array[i + 1]) / 35.0;
            }

            double maxValue = 0;
            for (int i = 0; i < BUFFER_SIZE; i++)
                if (Math.abs(filteredValues[i]) > maxValue)
                    maxValue = Math.abs(filteredValues[i]);

            maxValue *= 1.1;

            int xPos, yPos;
            for (int i = 0; i < BUFFER_SIZE; i += 1) {
                xPos = (int) Math.floor((double) (X_SIZE * i) / (double) BUFFER_SIZE);
                yPos = (int) Math.floor((1 + (filteredValues[i] / maxValue)) * Y_SIZE / 2);
                if (xPos >= 0 && xPos < X_SIZE && yPos >= 0 && yPos < Y_SIZE)
                    image.setRGB(xPos, yPos, 0);
                else
                    System.out.println("oops:" + xPos + " " + yPos);
            }
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.drawImage(image, 0, 0, null);
        }
    }

}
