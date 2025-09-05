import javax.swing.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestResultDataUploader {

    public void uploadTestResultData() {
        try {
            // Collect patient details
            String patientName = JOptionPane.showInputDialog("Enter Patient Name:");
            String age = JOptionPane.showInputDialog("Enter Patient Age:");
            String gender = JOptionPane.showInputDialog("Enter Patient Gender (Male/Female/Other):");

            // Collect test details
            String testName = JOptionPane.showInputDialog("Enter Test Name (e.g., Blood Test, X-ray, etc.):");
            String dateString = JOptionPane.showInputDialog("Enter Test Date (DD-MM-YYYY):");
            Date testDate = new SimpleDateFormat("dd-MM-yyyy").parse(dateString);
            String diagnosticCenterName = JOptionPane.showInputDialog("Enter Diagnostic Center Name:");

            // Collect test results
            StringBuilder testResultsBuilder = new StringBuilder();
            String testResult;

            while (true) {
                testResult = JOptionPane.showInputDialog("Enter Test Result (or type 'done' to finish):");
                if (testResult.equalsIgnoreCase("done")) {
                    break;
                }
                testResultsBuilder.append(testResult).append("\n");
            }

            // Generate file name using patient name and test date
            String safePatientName = patientName.replaceAll("[^a-zA-Z0-9]", "_"); // Replace special characters with underscores
            String filename = "testresults_" + safePatientName + "_" + new SimpleDateFormat("dd-MM-yyyy").format(testDate) + ".txt";

            // Write test result data to the file
            File file = new File(filename);
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write("Patient Name: " + patientName + "\n");
            writer.write("Age: " + age + "\n");
            writer.write("Gender: " + gender + "\n");
            writer.write("Test Name: " + testName + "\n");
            writer.write("Diagnostic Center: " + diagnosticCenterName + "\n");
            writer.write("Test Date: " + new SimpleDateFormat("dd-MM-yyyy").format(testDate) + "\n");
            writer.write("Test Results:\n" + testResultsBuilder.toString());
            writer.close();

            // Confirmation message
            JOptionPane.showMessageDialog(null, "Test Result Data Uploaded Successfully!");
        } catch (Exception e) {
            // Error handling
            JOptionPane.showMessageDialog(null, "Data not provided.");
        }
    }
}