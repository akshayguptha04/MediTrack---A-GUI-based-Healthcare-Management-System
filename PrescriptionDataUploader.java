import javax.swing.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PrescriptionDataUploader {

    public void uploadPrescriptionData() {
        try {
            // Collect patient and prescription details
            String patientName = JOptionPane.showInputDialog("Enter Patient Name:");
            String age = JOptionPane.showInputDialog("Enter Patient Age:");
            String gender = JOptionPane.showInputDialog("Enter Patient Gender (Male/Female/Other):");
            String doctorName = JOptionPane.showInputDialog("Enter Doctor Name:");
            String healthConcern = JOptionPane.showInputDialog("Enter Health Concern:");
            String dateString = JOptionPane.showInputDialog("Enter Prescription Date (DD-MM-YYYY):");
            Date prescriptionDate = new SimpleDateFormat("dd-MM-yyyy").parse(dateString);
            String duration = JOptionPane.showInputDialog("Enter Duration (e.g., 7 days, 14 days):");

            // Collect medicine details
            StringBuilder medicinesBuilder = new StringBuilder();
            String medicineName;
            String dosage;
            String timings;

            while (true) {
                medicineName = JOptionPane.showInputDialog("Enter Medicine Name (or type 'done' to finish):");
                if (medicineName.equalsIgnoreCase("done")) {
                    break;
                }
                dosage = JOptionPane.showInputDialog("Enter Dosage:");
                timings = JOptionPane.showInputDialog("Enter Timings:");

                medicinesBuilder.append("Medicine: ").append(medicineName).append("\n")
                        .append("Dosage: ").append(dosage).append("\n")
                        .append("Timings: ").append(timings).append("\n\n");
            }

            // Format the patient's name to remove spaces and create a valid file name
            String formattedPatientName = patientName.trim().replaceAll(" ", "_");

            // Generate file name based on patient's name and prescription date
            String filename = "prescription_" + formattedPatientName + "_" +
                    new SimpleDateFormat("dd-MM-yyyy").format(prescriptionDate) + ".txt";
            File file = new File(filename);

            // Write prescription data to the file
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write("Patient Name: " + patientName + "\n");
            writer.write("Age: " + age + "\n");
            writer.write("Gender: " + gender + "\n");
            writer.write("Doctor Name: " + doctorName + "\n");
            writer.write("Health Concern: " + healthConcern + "\n");
            writer.write("Prescription Date: " + new SimpleDateFormat("dd-MM-yyyy").format(prescriptionDate) + "\n");
            writer.write("Duration: " + duration + "\n");
            writer.write("Medicines:\n" + medicinesBuilder.toString());
            writer.close();

            // Confirmation message
            JOptionPane.showMessageDialog(null, "Prescription Data Uploaded Successfully!");
        } catch (Exception e) {
            // Error message
            JOptionPane.showMessageDialog(null, "Data not provided.");
        }
    }
}