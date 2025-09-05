import javax.swing.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.awt.*;

public class DataViewer {

    public void displayPrescriptionData() {
        displayData("prescription");
    }

    public void displayMedicinePurchaseData() {
        displayData("medicinepurchase");
    }

    public void displayTestResultData() {
        displayData("testresult");
    }

    private void displayData(String dataType) {
        try {
            // Step 1: Ask for a specific date or all dates
            String dateFilterChoice = (String) JOptionPane.showInputDialog(
                    null,
                    "Do you want to filter by a specific date?",
                    "Date Filter",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    new String[] {"Yes", "No"},
                    "No"
            );

            String date = "";
            if ("Yes".equals(dateFilterChoice)) {
                date = JOptionPane.showInputDialog("Enter the specific date (DD-MM-YYYY):");
            }

            // Step 2: Ask for a specific patient or all patients
            String patientFilterChoice = (String) JOptionPane.showInputDialog(
                    null,
                    "Do you want to filter by a specific patient?",
                    "Patient Filter",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    new String[] {"Yes", "No"},
                    "No"
            );

            String patientName = "";
            if ("Yes".equals(patientFilterChoice)) {
                patientName = JOptionPane.showInputDialog("Enter the patient's name:");
            }

            // Get the files to display based on the data type
            File folder = new File(".");
            File[] files = folder.listFiles((dir, name) -> name.startsWith(dataType));

            List<String> filesToDisplay = new ArrayList<>();
            StringBuilder allData = new StringBuilder();

            if (files != null) {
                for (File file : files) {
                    boolean matchesDate = date.isEmpty() || file.getName().contains(date);
                    boolean matchesPatient = patientName.isEmpty() || file.getName().contains(patientName);

                    if (matchesDate && matchesPatient) {
                        filesToDisplay.add(file.getName());
                    }
                }

                if (filesToDisplay.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No data found for the selected criteria.");
                } else {
                    // Display the files in a new window
                    JFrame displayFrame = new JFrame("View " + dataType + " Data");
                    displayFrame.setSize(600, 400);
                    displayFrame.setLocationRelativeTo(null); // Center the window
                    displayFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Make sure it closes properly

                    JTextArea textArea = new JTextArea();
                    textArea.setEditable(false);
                    textArea.setFont(new Font("Monospaced", Font.PLAIN, 12)); // Monospaced font for clarity
                    textArea.setTabSize(4);

                    // Add header for better clarity
                    textArea.append("Displaying " + dataType + " data:\n\n");

                    // Variable to calculate the total bill for medicine purchases
                    double totalBill = 0.0;

                    for (String fileName : filesToDisplay) {
                        File file = new File(fileName);
                        BufferedReader reader = new BufferedReader(new FileReader(file));
                        allData.append("******************************************************\n");
                        allData.append("File: " + fileName + "\n");
                        allData.append("******************************************************\n");

                        // Display the file content (just what the user has entered)
                        String line;
                        while ((line = reader.readLine()) != null) {
                            allData.append(line + "\n");

                            // Add a line space after each data aspect (e.g., name, age, etc.)
                            if (line.contains(":")) {
                                allData.append("\n");  // Adding a line space after each aspect
                            }

                            // Only for medicine purchases, calculate the total bill
                            if (dataType.equals("medicinepurchase") && line.contains("Cost:")) {
                                // Extracting the cost and adding to the total bill
                                String[] parts = line.split(":");
                                if (parts.length == 2) {
                                    try {
                                        double cost = Double.parseDouble(parts[1].trim());
                                        totalBill += cost;
                                    } catch (NumberFormatException e) {
                                        // Handle invalid cost format
                                    }
                                }
                            }
                        }

                        allData.append("\n------------------------------------------------------------\n\n");
                        reader.close();
                    }

                    // Set all accumulated data in the JTextArea
                    textArea.setText(allData.toString());

                    JScrollPane scrollPane = new JScrollPane(textArea);
                    displayFrame.add(scrollPane);

                    // Add an Exit button
                    JButton exitButton = new JButton("Exit");
                    exitButton.addActionListener(e -> displayFrame.dispose());
                    displayFrame.add(exitButton, BorderLayout.SOUTH);

                    // Ensure the window is always on top
                    displayFrame.setAlwaysOnTop(true);

                    displayFrame.setVisible(true);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // You can replace this with other types if you want
        new DataViewer().displayPrescriptionData();
    }
}