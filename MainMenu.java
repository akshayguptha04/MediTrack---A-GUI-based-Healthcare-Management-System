import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu {

    public void createAndShowGUI() {
        JFrame frame = new JFrame("MediTrack Main Menu");
        frame.setSize(600, 500);
        frame.setLocationRelativeTo(null); // Center the window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(245, 245, 245)); // Light gray background

        // Add greeting label at the top
        JLabel greetingLabel = new JLabel("Hello user, what do you want to do today?", JLabel.CENTER);
        greetingLabel.setFont(new Font("Arial", Font.PLAIN, 20)); // Simple font styling
        greetingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        greetingLabel.setForeground(new Color(0, 0, 0)); // Black text color
        greetingLabel.setBorder(BorderFactory.createEmptyBorder(40, 0, 20, 0)); // Space above and below

        // Create normal buttons
        JButton uploadButton = new JButton("Upload Data");
        uploadButton.setFont(new Font("Arial", Font.PLAIN, 20)); // Simple font styling
        uploadButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        uploadButton.setFocusPainted(false); // Removes focus border
        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showUploadOptions(frame);
            }
        });

        JButton viewButton = new JButton("View Data");
        viewButton.setFont(new Font("Arial", Font.PLAIN, 20));
        viewButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        viewButton.setFocusPainted(false);
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showViewOptions(frame);
            }
        });

        // Create Exit button
        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Arial", Font.PLAIN, 20));
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setFocusPainted(false);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showExitAnimation(frame);
            }
        });

        // Add greeting label and buttons to the panel with spacing
        panel.add(greetingLabel); // Add the greeting label
        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Space between greeting and buttons
        panel.add(uploadButton);
        panel.add(Box.createRigidArea(new Dimension(0, 30))); // Space between buttons
        panel.add(viewButton);
        panel.add(Box.createRigidArea(new Dimension(0, 30))); // Space between "View Data" and "Exit" button
        panel.add(exitButton);
        panel.add(Box.createRigidArea(new Dimension(0, 80))); // Bottom margin

        frame.add(panel);
        frame.setVisible(true);
    }

    private void showExitAnimation(JFrame frame) {
        // Remove all components from the frame
        frame.getContentPane().removeAll();

        // Create a label for the exit message
        JLabel exitMessage = new JLabel("", JLabel.CENTER);
        exitMessage.setFont(new Font("Arial", Font.PLAIN, 30));
        exitMessage.setForeground(new Color(0, 0, 0)); // Black text color
        exitMessage.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add the exit message label to the frame
        frame.getContentPane().add(exitMessage);

        // Set up a timer to animate the text by gradually revealing it
        final String message = "Have a nice day!";
        final StringBuilder animatedMessage = new StringBuilder();

        Timer timer = new Timer(100, new ActionListener() {
            int index = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (index < message.length()) {
                    animatedMessage.append(message.charAt(index));
                    exitMessage.setText(animatedMessage.toString());
                    index++;
                } else {
                    // Once the message is fully displayed, close the application after a short delay
                    Timer closeTimer = new Timer(1000, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            System.exit(0);
                        }
                    });
                    closeTimer.setRepeats(false); // Do not repeat
                    closeTimer.start();
                }
            }
        });

        timer.start();

        // Refresh the frame to show the message
        frame.revalidate();
        frame.repaint();
    }

    private void showUploadOptions(JFrame frame) {
        String[] options = {"Prescription", "Medicine Purchase", "Test Results"};
        String choice = (String) JOptionPane.showInputDialog(null, "Select Data Type to Upload", "Upload Data",
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if (choice != null) {
            switch (choice) {
                case "Prescription":
                    new PrescriptionDataUploader().uploadPrescriptionData();
                    break;
                case "Medicine Purchase":
                    new MedicinePurchaseDataUploader().uploadMedicinePurchaseData();
                    break;
                case "Test Results":
                    new TestResultDataUploader().uploadTestResultData();
                    break;
            }
        }
        // After completing the upload operation, return to the main menu
        returnToMainMenu(frame);
    }

    private void showViewOptions(JFrame frame) {
        String[] options = {"Prescription", "Medicine Purchase", "Test Results"};
        String choice = (String) JOptionPane.showInputDialog(null, "Select Data Type to View", "View Data",
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if (choice != null) {
            switch (choice) {
                case "Prescription":
                    new DataViewer().displayPrescriptionData();
                    break;
                case "Medicine Purchase":
                    new DataViewer().displayMedicinePurchaseData();
                    break;
                case "Test Results":
                    new DataViewer().displayTestResultData();
                    break;
            }
        }
        // After completing the view operation, return to the main menu
        returnToMainMenu(frame);
    }

    private void returnToMainMenu(JFrame frame) {
        // Clear the current frame content
        frame.getContentPane().removeAll();

        // Rebuild and show the main menu again
        createAndShowGUI();

        // Refresh the frame to display the updated content
        frame.revalidate();
        frame.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainMenu().createAndShowGUI();
            }
        });
    }
}
