import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RadialGradientPaint;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class Frame extends JFrame {
    private int height, width;
    private List<Particle> particles;
    private Random r = new Random();
    private JPanel starPanel;
    private String topTextString = "";

    Frame(int w, int h) {
        // Initiate Frame
        Image iconImage = Toolkit.getDefaultToolkit().getImage("icons8-music-100.png");
        setTitle("Audio Viewer");
        setIconImage(iconImage);
        setWidth(w);
        setHeight(h);
        setSize(w, h);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        addParticles();

        // Initialize overlayPanel, for adding interactions and visualizations
        JPanel overlayPanel = new JPanel();
        starPanel.add(overlayPanel);
        overlayPanel.setOpaque(false);

        // Load in custom font
        Font customFont = null;
        try {
            // Load the TTF font file
            InputStream is = new FileInputStream(new File("VCR_OSD_MONO_1.001.ttf"));
            customFont = Font.createFont(Font.TRUETYPE_FONT, is);

            // Set the font size and style
            customFont = customFont.deriveFont(Font.PLAIN, 35);

            // Test the font
            System.out.println("Font name: " + customFont.getFontName());

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Use the GridBagLayout
        overlayPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 0, 20, 0); // Add vertical spacing between elements

        topTextString = "Audio Visualizer";
        JLabel topText = new JLabel(topTextString);
        topText.setFont(customFont);
        topText.setForeground(Color.RED);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        overlayPanel.add(topText, gbc); // Add topText to the top middle with spacing

        FrameMenuLogic buttonLogic = new FrameMenuLogic(overlayPanel, topText, customFont);

        // Add VisualizerButton to the center middle with spacing
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        overlayPanel.add(buttonLogic.VisualizerButton, gbc);

        // Add empty space between buttons
        gbc.gridy = 2;
        gbc.weighty = 1.0; // Add vertical space
        overlayPanel.add(Box.createVerticalStrut(20), gbc);

        // Add importButton to the bottom middle with spacing
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.SOUTH;
        overlayPanel.add(buttonLogic.importButton, gbc);

    }

    private void addParticles() {
        starPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (Particle particle : particles) {
                    particle.draw(g);
                }
            }
        };
        add(starPanel);
        starPanel.setBackground(Color.BLACK);

        int particleCount = 2500;
        particles = new ArrayList<>(particleCount);
        for (int i = 0; i < particleCount; i++) {
            particles.add(createParticle());
        }

        int delay = 15;
        Timer timer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (Particle particle : particles) {
                    int speed = particle.getSpeed();
                    int newY = particle.getPosY() - (2 * speed);
                    if (newY < 0) {
                        // If the particle reaches the top, reset its position and transparency
                        newY = getHeight() - particle.getParticleSize();
                        int newX = r.nextInt(getWidth());
                        particle.setPosX(newX);
                        particle.setPosY(newY);
                        // Reset transparency based on the condition
                        if (r.nextInt(10) == 0) {
                            particle.setTransparency(255); // Fully opaque (white)
                        } else {
                            particle.setTransparency(getRandomTransparency());
                        }
                    } else {
                        int lerpFactor = 10; // Adjust this value for smoother or faster movement
                        int currentY = particle.getPosY();
                        int interpolatedY = lerp(currentY, newY, 1.0 / lerpFactor);
                        particle.setPosY(interpolatedY);
                    }
                    // Decrease transparency as particles go up
                    int currentTransparency = particle.getTransparency();
                    if (currentTransparency > 0) {
                        particle.setTransparency(currentTransparency - 1);
                    }
                }
                starPanel.repaint();
            }
        });
        timer.start();
    }

    // Got Bored, this has nothing to do with the project itself
    private Particle createParticle() {
        Particle particle = new Particle(5);
        int initialX = r.nextInt(getWidth());
        int initialY = r.nextInt(getHeight());
        particle.setPosX(initialX);
        particle.setPosY(initialY);
        particle.setSpeed(1 + r.nextInt(10)); // Assign a random speed to the particle
        // Set initial transparency to be higher for some particles (fully white)
        if (r.nextInt(10) == 0) {
            particle.setTransparency(255); // Fully opaque (white)
        } else {
            particle.setTransparency(getRandomTransparency());
        }
        return particle;
    }

    private int getRandomTransparency() {
        Random random = new Random();
        return random.nextInt(150) + 50; // Vary transparency between 50 and 200
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getWidth() {
        return width;
    }

    // Linear interpolation function
    private int lerp(int a, int b, double t) {
        return (int) (a * (1 - t) + b * t);
    }

    private class Particle {
        private int size, posX, posY, transparency, speed;

        Particle(int size) {
            this.size = size;
        }

        public int getParticleSize() {
            return size;
        }

        public void setPosX(int posX) {
            this.posX = posX;
        }

        public int getPosX() {
            return posX;
        }

        public void setPosY(int posY) {
            this.posY = posY;
        }

        public int getPosY() {
            return posY;
        }

        public void setTransparency(int transparency) {
            this.transparency = transparency;
        }

        public int getTransparency() {
            return transparency;
        }

        public void setSpeed(int speed) {
            this.speed = speed;
        }

        public int getSpeed() {
            return speed;
        }

        public void draw(Graphics g) {
            // Create a radial gradient with varying transparency
            Graphics2D g2d = (Graphics2D) g;
            Ellipse2D circle = new Ellipse2D.Float(posX, posY, size, size);
            float[] dist = { 0.0f, 1.0f };
            Color[] colors = { new Color(255, 255, 255, transparency), new Color(255, 255, 255, 0) };
            RadialGradientPaint gradient = new RadialGradientPaint(posX + size / 2, posY + size / 2, size / 2, dist,
                    colors);

            g2d.setPaint(gradient);
            g2d.fill(circle);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Frame(1000, 500);
            }
        });
    }
}
