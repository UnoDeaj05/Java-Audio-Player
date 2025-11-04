import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import javax.sound.sampled.*;
import javax.swing.filechooser.FileNameExtensionFilter;

class AudioPlayer {
    // UI
    int RESOLUTION_X = 300;
    int RESOLUTION_Y = 400;
    JFrame frame;
    JFileChooser fileChooser;
    JButton playButton, openButton, forwardButton, backwardButton;
    JSlider progresSlider;
    JLabel currentTime, endTime;

    // Audio
    boolean playing = false;
    File file;
    Clip clip;
    AudioInputStream audioInputStream;
    FileNameExtensionFilter filter = new FileNameExtensionFilter("Audio Files (*.wav, *.aiff, *.au)", 
    "wav", ".aiff", ".au");


    public AudioPlayer() {
        frame = new JFrame();
        frame.setSize(RESOLUTION_X, RESOLUTION_Y);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        fileChooser = new JFileChooser();
        fileChooser.setFileFilter(filter);

        progresSlider = new JSlider();
        progresSlider.setBounds(10, 250, RESOLUTION_X - 10, 20);
        frame.add(progresSlider);

        currentTime = new JLabel("--:--");
        endTime = new JLabel("--:--");
        currentTime.setBounds(30, 300, 80, 25);
        endTime.setBounds(230, 300, 80, 25);
        frame.add(currentTime);
        frame.add(endTime);

        playButton = new JButton("Play");
        playButton.setBounds(115, 325, 70, 25);
        frame.add(playButton);
        playButton.setEnabled(false);

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!playing) {
                    clip.start();
                    playing = true;
                    playButton.setText("Pause");
                } else {
                    clip.stop();
                    playing = false;
                    playButton.setText("Play");
                }
            }
        });

        backwardButton = new JButton("<<");
        backwardButton.setBounds(65, 325, 50, 25);
        frame.add(backwardButton);

        forwardButton = new JButton(">>");
        forwardButton.setBounds(185, 325, 50, 25);
        frame.add(forwardButton);

        openButton = new JButton("Open");
        openButton.setBounds(10, 10, 70, 25);
        frame.add(openButton);

        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileChooser.showOpenDialog(frame);
                file = fileChooser.getSelectedFile();
                long seconds;
                long minutes;

                try {
                    if(file != null) {
                        audioInputStream = AudioSystem.getAudioInputStream(file);
                        clip = AudioSystem.getClip();
                        clip.open(audioInputStream);
                        playButton.setEnabled(true);
                        seconds = (clip.getMicrosecondLength() / 1000000) % 60;
                        minutes = (clip.getMicrosecondLength() / 1000000) / 60;
                        endTime.setText(String.valueOf(minutes) + ":" + String.valueOf(seconds));
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
                }
            }
        });

        frame.setLayout(null);
        frame.setVisible(true);
    }
    public static void main(String[] args) {
        new AudioPlayer();
    }
}