import javax.swing.*;
import java.awt.*;

public class SplashScreen {
    public void showSplashScreen() {
        JFrame splashFrame = new JFrame("MediTrack Splash Screen");
        splashFrame.setSize(800, 500);
        splashFrame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null); // Use null layout for precise positioning
        panel.setBackground(Color.WHITE);
        splashFrame.add(panel);

        // Title label
        JLabel titleLabel = new JLabel("MediTrack, Your health in one click", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        titleLabel.setForeground(new Color(184, 134, 11));
        titleLabel.setBounds(50, 120, 700, 50); // Move upwards by setting y to 120

        // Loading label
        JLabel loadingLabel = new JLabel("Loading", JLabel.CENTER);
        loadingLabel.setFont(new Font("Segoe UI", Font.PLAIN, 33));
        loadingLabel.setForeground(new Color(169, 169, 169));
        loadingLabel.setBounds(50, 250, 700, 50); // Move upwards by setting y to 250

        // Add labels to panel
        panel.add(titleLabel);
        panel.add(loadingLabel);

        splashFrame.setUndecorated(true);
        splashFrame.setVisible(true);

        // Animation logic for "Loading..."
        new Thread(() -> {
            String baseText = "Loading";
            int dotCount = 0;

            try {
                for (int i = 0; i < 30; i++) { // Run the animation for ~10 seconds (30 iterations)
                    dotCount = (dotCount + 1) % 4; // Cycle through 0, 1, 2, 3
                    String dots = ".".repeat(dotCount);
                    loadingLabel.setText(baseText + dots);
                    Thread.sleep(300); // Update every 300ms
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            splashFrame.dispose();

            // Open the main menu after the splash screen
            SwingUtilities.invokeLater(() -> {
                MainMenu mainMenu = new MainMenu();
                mainMenu.createAndShowGUI();
            });
        }).start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SplashScreen().showSplashScreen();
        });
    }
}