import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class PhonebookApp {
    private JFrame frame;
    private JTextField nameField, phoneField, searchField;
    private JTextArea contactArea;
    private ArrayList<Contact> contacts = new ArrayList<>();
    private final String FILE_NAME = "phonebook.txt";

    public PhonebookApp() {
        loadContactsFromFile();
        createGUI();
    }

    private void createGUI() {
        frame = new JFrame("Phonebook");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        
        JPanel panel = new JPanel(new GridLayout(4, 1));
        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
        inputPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Phone:"));
        phoneField = new JTextField();
        inputPanel.add(phoneField);
        panel.add(inputPanel);
        
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add");
        JButton deleteButton = new JButton("Delete");
        JButton updateButton = new JButton("Update");
        addButton.addActionListener(e -> addContact());
        deleteButton.addActionListener(e -> deleteContact());
        updateButton.addActionListener(e -> updateContact());
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(updateButton);
        panel.add(buttonPanel);
        
        JPanel searchPanel = new JPanel();
        searchPanel.add(new JLabel("Search:"));
        searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> searchContact());
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        panel.add(searchPanel);

        contactArea = new JTextArea(10, 40);
        contactArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(contactArea);
        panel.add(scrollPane);

        frame.add(panel);
        frame.setVisible(true);
        displayContacts();
    }

    private void addContact() {
        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();
        if (!name.isEmpty() && !phone.isEmpty()) {
            contacts.add(new Contact(name, phone));
            saveContactsToFile();
            displayContacts();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(frame, "Name and Phone cannot be empty.");
        }
    }

    private void deleteContact() {
        String name = nameField.getText().trim();
        contacts.removeIf(contact -> contact.getName().equalsIgnoreCase(name));
        saveContactsToFile();
        displayContacts();
        clearFields();
    }

    private void updateContact() {
        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();
        for (Contact contact : contacts) {
            if (contact.getName().equalsIgnoreCase(name)) {
                contact.setPhone(phone);
                saveContactsToFile();
                displayContacts();
                clearFields();
                return;
            }
        }
        JOptionPane.showMessageDialog(frame, "Contact not found.");
    }

    private void searchContact() {
        String query = searchField.getText().trim();
        StringBuilder result = new StringBuilder();
        for (Contact contact : contacts) {
            if (contact.getName().equalsIgnoreCase(query)) {
                result.append(contact).append("\n");
            }
        }
        contactArea.setText(result.length() == 0 ? "No contacts found." : result.toString());
    }

    private void displayContacts() {
        StringBuilder result = new StringBuilder();
        for (Contact contact : contacts) {
            result.append(contact).append("\n");
        }
        contactArea.setText(result.toString());
    }

    private void clearFields() {
        nameField.setText("");
        phoneField.setText("");
        searchField.setText("");
    }

    private void saveContactsToFile() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(contacts);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error saving contacts.");
        }
    }

    private void loadContactsFromFile() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            contacts = (ArrayList<Contact>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // File may not exist on first run
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PhonebookApp::new);
    }
}

class Contact implements Serializable {
    private String name, phone;
    public Contact(String name, String phone) { this.name = name; this.phone = phone; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    @Override
    public String toString() { return "Name: " + name + ", Phone: " + phone; }
}
