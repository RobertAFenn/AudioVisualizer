import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BugReportWindow extends JFrame {
    private JTextField bugReportTextField;
    private JButton submitButton;
    String bugReport = null;

    public BugReportWindow(Deque<String> Deque) {
        setTitle("Report a bug");
        setSize(400, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

        bugReportTextField = new JTextField(30);
        submitButton = new JButton("Submit");

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(new JLabel("Enter your bug report:"));
        panel.add(bugReportTextField);
        panel.add(submitButton);

        add(panel);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    String bugReport = bugReportTextField.getText();
                    addStringToDeque(Deque, bugReport);

                    System.out.println("Bug report submitted, You reported " + Deque.getBack());

                } catch (Exception exe) {
                    System.out.println(exe.getMessage());
                }

                dispose();
            }
        });
    }

    private Boolean addStringToDeque(Deque<String> d, String s) {

        d.addToBack(s);
        return true;
    }

    private String getTextFromBox() throws Exception {
        if (bugReport == null) {
            throw new Exception("bugReport is empty");
        }
        return bugReport;
    }

}
