import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FrameMenuLogic implements ActionListener {

    public JButton menuButton, importButton, VisualizerButton;
    JPanel currentPanel;
    JLabel label;

    // mostly using this constructor as a means to grab elements of the menu, could
    // this be more elegant? yes but its fine
    FrameMenuLogic(JPanel panel, JLabel topText, Font customFont) {
        currentPanel = panel;
        label = topText;
        menuButton = new JButton("Return To Menu");
        VisualizerButton = new JButton("Select Items");
        importButton = new JButton("Import New Files");

        // Add this FrameMenuLogic instance as the ActionListener for the buttons
        menuButton.addActionListener(this);
        importButton.addActionListener(this);
        VisualizerButton.addActionListener(this);

        // Add mouse listeners to handle hover and click effects
        addMouseListeners(menuButton);
        addMouseListeners(importButton);
        addMouseListeners(VisualizerButton);

        InitializeButtons(customFont);
    }

    public JButton[] toArray() {
        JButton[] buttons = { menuButton, importButton, VisualizerButton };
        return buttons;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        JButton myButton = (JButton) e.getSource();

        if (e.getSource() == menuButton) {
            // Handle menuButton click
            System.out.println("Menu button clicked");
        } else if (e.getSource() == importButton) {
            // Handle importButton click
            System.out.println("Import button clicked");
        } else if (e.getSource() == VisualizerButton) {
            // Handle VisualizerButton click
            System.out.println("Visualizer button clicked");
        }
    }

    private void addMouseListeners(JButton button) { // I wish i had CSS
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Handle hover effect (when mouse enters the button)
                button.setBackground(new Color(255, 200, 200, 100)); // Change to a different color
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Handle release effect (when mouse exits the button)
                button.setBackground(new Color(255, 255, 255, 100)); // Restore the original background color
            }

            @Override
            public void mousePressed(MouseEvent e) {
                // Handle click effect (when mouse is pressed on the button)
                button.setBackground(new Color(255, 0, 0, 100)); // Change to a different color
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // Handle release effect (when mouse is released after a click)
                button.setBackground(new Color(255, 0, 0, 100)); // Restore the click background color
            }
        });
    }

    private void InitializeButtons(Font customFont) {
        for (JButton buttons : toArray()) {
            buttons.setPreferredSize(new Dimension(200, 50));
            buttons.setForeground(Color.WHITE);
            buttons.setBackground(new Color(255, 255, 255, 100));
            buttons.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            Font newFont = customFont.deriveFont(16.0f);
            buttons.setFont(newFont);

        }
    }
}
