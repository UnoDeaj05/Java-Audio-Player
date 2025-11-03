import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import javax.sound.sampled.*;

class AudioPlayer {
    File file;
    Clip clip;
    AudioInputStream audioInputStream;

    // UI
    JFrame frame;
    JFileChooser fileChooser;
    JButton playButton, openButton;

    public AudioPlayer() {
        frame = new JFrame();
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        fileChooser = new JFileChooser();

        playButton = new JButton("Play");
        playButton.setBounds(350, 500, 100, 25);
        frame.add(playButton);
        playButton.setEnabled(false);

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clip.start();
            }
        });

        openButton = new JButton("Open file");
        openButton.setBounds(20, 20, 100, 100);
        frame.add(openButton);

        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileChooser.showOpenDialog(frame);
                file = fileChooser.getSelectedFile();
                try {
                    audioInputStream = AudioSystem.getAudioInputStream(file);
                    clip = AudioSystem.getClip();
                    clip.open(audioInputStream);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
                }
                playButton.setEnabled(true);
            }
        });

        frame.setLayout(null);
        frame.setVisible(true);
    }
    public static void main(String[] args) {
        new AudioPlayer();
    }
}