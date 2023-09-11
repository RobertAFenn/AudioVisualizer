import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FrameMenuLogic implements ActionListener {

    public JButton menuButton, importButton, VisualizerButton;
    JPanel currentPanel;
    JLabel topText;
    Font customFont;

    // mostly using this constructor as a means to grab elements of the menu, could
    // this be more elegant? yes but its fine
    FrameMenuLogic(JPanel panel, JLabel topText, Font customFont) {
        currentPanel = panel;
        this.topText = topText;
        menuButton = new JButton("Return To Menu");
        VisualizerButton = new JButton("Select Items");
        importButton = new JButton("Import New Files");
        this.customFont = customFont;
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
        JButton[] menuButtons = getAllButtons(currentPanel);

        // Add other buttons to the menuButtons array as needed
        JButton[] additionalButtons = {
                menuButton, importButton, VisualizerButton
        };

        // Combine the menuButtons and additionalButtons arrays
        JButton[] buttons = new JButton[menuButtons.length + additionalButtons.length];

        System.arraycopy(menuButtons, 0, buttons, 0, menuButtons.length);
        System.arraycopy(additionalButtons, 0, buttons, menuButtons.length, additionalButtons.length);

        return buttons;
    }

    private JButton[] getAllButtons(Container container) {
        Component[] components = container.getComponents();
        List<JButton> buttonList = new ArrayList<>();

        for (Component component : components) {
            if (component instanceof JButton) {
                buttonList.add((JButton) component);
            }
        }

        JButton[] buttons = new JButton[buttonList.size()];
        buttonList.toArray(buttons);

        return buttons;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton myButton = (JButton) e.getSource();

        switch (myButton.getText()) {
            case "Return To Menu":
                // Handle menuButton click
                System.out.println("Menu button clicked");

                break;
            case "Import New Files":
                // Handle importButton click
                System.out.println("Import button clicked");
                // ! Bring up that file thing and add it to the music folder
                JFileChooser JFC = new JFileChooser();
                JFC.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int result = JFC.showOpenDialog(null);

                if (result == JFileChooser.APPROVE_OPTION) {
                    // The user selected a file
                    File selectedFile = JFC.getSelectedFile();
                    String filePath = selectedFile.getAbsolutePath();
                    System.out.println("Selected File: " + filePath);

                    // Specify the destination directory
                    Path destinationDirectory = Paths.get("music"); // Adjust the destination directory as needed

                    try {
                        // Move the file to the destination directory
                        Files.move(selectedFile.toPath(), destinationDirectory.resolve(selectedFile.getName()),
                                StandardCopyOption.REPLACE_EXISTING);
                        System.out.println("File moved successfully.");
                    } catch (IOException err) {
                        System.err.println("Error moving file: " + err.getMessage());
                    }
                } else if (result == JFileChooser.CANCEL_OPTION) {
                    // The user canceled the file chooser dialog
                    System.out.println("File selection canceled");
                }

                break;

            case "Select Items":
                // Handle VisualizerButton click
                System.out.println("Visualizer button clicked");
                removeAllButTopLabel();

                break;
            default:
                // Handle other buttons or cases here
                break;
        }
    }

    private void removeAllButTopLabel() {
        Component[] components = currentPanel.getComponents();

        for (Component component : components) {
            if (component instanceof JLabel) {
                JLabel label = (JLabel) component;
                System.out.println(label.getText());
                if (label != topText) {
                    currentPanel.remove(label);
                }
            } else {
                currentPanel.remove(component);
            }
        }

        currentPanel.revalidate(); // Refresh the panel to reflect the changes
        currentPanel.repaint(); // Repaint the panel
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
