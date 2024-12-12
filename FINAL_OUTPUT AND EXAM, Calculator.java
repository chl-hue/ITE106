import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;

public class PayrollSystem {

    private JFrame frame;
    private JTextField idField, nameField, rateField, hoursField;
    private JTextArea payrollArea;
    private ArrayList<Employee> employees = new ArrayList<>();
    private static final String FILE_NAME = "payroll.txt";

    public PayrollSystem() {
        loadRecordsFromFile();
        createGUI();
    }

    private void createGUI() {
        frame = new JFrame("Payroll System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);

        JPanel panel = new JPanel(new GridLayout(3, 1));
        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        inputPanel.add(new JLabel("ID:")); idField = new JTextField();
        inputPanel.add(idField);
        inputPanel.add(new JLabel("Name:")); nameField = new JTextField();
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Rate:")); rateField = new JTextField();
        inputPanel.add(rateField);
        inputPanel.add(new JLabel("Hours:")); hoursField = new JTextField();
        inputPanel.add(hoursField);
        panel.add(inputPanel);

        JPanel buttonPanel = new JPanel();
        addButton("Add Employee", e -> addEmployee()).setBounds(0, 0, 150, 30);
        addButton("Calculate Pay", e -> calculatePay()).setBounds(0, 0, 150, 30);
        addButton("Save", e -> saveRecordsToFile()).setBounds(0, 0, 150, 30);
        addButton("Display Records", e -> displayRecords()).setBounds(0, 0, 150, 30);

        panel.add(buttonPanel);

        payrollArea = new JTextArea(10, 40);
        payrollArea.setEditable(false);
        panel.add(new JScrollPane(payrollArea));

        frame.add(panel);
        frame.setVisible(true);
    }

    private void addEmployee() {
        try {
            int id = Integer.parseInt(idField.getText().trim());
            String name = nameField.getText().trim();
            double rate = Double.parseDouble(rateField.getText().trim());
            double hours = Double.parseDouble(hoursField.getText().trim());

            if (!name.isEmpty() && rate > 0 && hours > 0) {
                employees.add(new Employee(id, name, rate, hours));
                clearFields();
                JOptionPane.showMessageDialog(frame, "Employee added.");
            } else {
                throw new IllegalArgumentException("Invalid input.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Error: " + e.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void calculatePay() {
        StringBuilder result = new StringBuilder();
        for (Employee emp : employees) {
            result.append(emp).append(", Gross Pay: $").append(emp.calculateGrossPay())
                  .append(", Net Pay: $").append(emp.calculateNetPay()).append("\n");
        }
        payrollArea.setText(result.toString());
    }

    private void saveRecordsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Employee emp : employees) {
                writer.write(emp.toFileString());
                writer.newLine();
            }
            JOptionPane.showMessageDialog(frame, "Records saved.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error saving: " + e.getMessage());
        }
    }

    private void displayRecords() {
        StringBuilder result = new StringBuilder();
        for (Employee emp : employees) result.append(emp).append("\n");
        payrollArea.setText(result.toString());
    }

    private void loadRecordsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    employees.add(new Employee(Integer.parseInt(parts[0]), parts[1], 
                                               Double.parseDouble(parts[2]), Double.parseDouble(parts[3])));
                }
            }
        } catch (IOException ignored) {}
    }

    private void clearFields() {
        idField.setText(""); nameField.setText(""); rateField.setText(""); hoursField.setText("");
    }

    private JButton addButton(String text, ActionListener listener) {
        JButton button = new JButton(text);
        button.addActionListener(listener);
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PayrollSystem::new);
    }
}

class Employee {
    private int id;
    private String name;
    private double hourlyRate;
    private double hoursWorked;

    public Employee(int id, String name, double hourlyRate, double hoursWorked) {
        this.id = id; this.name = name; this.hourlyRate = hourlyRate; this.hoursWorked = hoursWorked;
    }

    public double calculateGrossPay() {
        return hourlyRate * hoursWorked;
    }

    public double calculateNetPay() {
        return calculateGrossPay() * 0.85; // 15% deduction
    }

    public String toFileString() {
        return id + "," + name + "," + hourlyRate + "," + hoursWorked;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Rate: $" + hourlyRate + ", Hours: " + hoursWorked;
    }
}

