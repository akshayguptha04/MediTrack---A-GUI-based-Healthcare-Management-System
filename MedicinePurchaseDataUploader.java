import javax.swing.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MedicinePurchaseDataUploader {

    public void uploadMedicinePurchaseData() {
        try {
            // Collect patient details
            String patientName = JOptionPane.showInputDialog("Enter Patient Name:");
            String dateString = JOptionPane.showInputDialog("Enter Purchase Date (DD-MM-YYYY):");
            Date purchaseDate = new SimpleDateFormat("dd-MM-yyyy").parse(dateString);
            String medicalStoreName = JOptionPane.showInputDialog("Enter Medical Store Name:");

            // Initialize a variable to store the total bill
            double totalBill = 0.0;
            StringBuilder medicinesBuilder = new StringBuilder();
            String medicineName;
            String medicineCost;

            while (true) {
                medicineName = JOptionPane.showInputDialog("Enter Medicine Name (or type 'done' to finish):");
                if (medicineName.equalsIgnoreCase("done")) {
                    break;
                }
                medicineCost = JOptionPane.showInputDialog("Enter Medicine Cost for " + medicineName + ":");

                // Add the cost to the total bill
                double cost = Double.parseDouble(medicineCost);
                totalBill += cost;

                // Append the medicine details to the builder
                medicinesBuilder.append("Medicine: ").append(medicineName).append("\n")
                        .append("Cost: ").append(medicineCost).append("\n\n");
            }

            // Generate file name using patient name and purchase date
            String safePatientName = patientName.replaceAll("[^a-zA-Z0-9]", "_"); // Replace special characters with underscores
            String filename = "medicinepurchases_" + safePatientName + "_" + new SimpleDateFormat("dd-MM-yyyy").format(purchaseDate) + ".txt";

            // Write purchase data to the file
            File file = new File(filename);
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write("Patient Name: " + patientName + "\n");
            writer.write("Purchase Date: " + new SimpleDateFormat("dd-MM-yyyy").format(purchaseDate) + "\n");
            writer.write("Medical Store: " + medicalStoreName + "\n");
            writer.write("Total Bill: " + totalBill + "\n");
            writer.write("Medicines Purchased:\n" + medicinesBuilder.toString());
            writer.close();

            // Confirmation message
            JOptionPane.showMessageDialog(null, "Medicine Purchase Data Uploaded Successfully!");
        } catch (Exception e) {
            // Error handling
            JOptionPane.showMessageDialog(null, "Data not provided.");
        }
    }
}