package com.rrms.gui;

import javax.swing.*;
import java.awt.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RentalManagementGUI extends JFrame {

    private final HttpClient client = HttpClient.newHttpClient();

    public RentalManagementGUI() {
        setTitle("Rental Resource Management System");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();

        tabs.addTab("Resources", createResourcePanel());
        tabs.addTab("Customers", createCustomerPanel());
        tabs.addTab("Rentals", createRentalPanel());

        add(tabs);
    }

    private JPanel createResourcePanel() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField categoryField = new JTextField();
        JTextField priceField = new JTextField();

        panel.add(new JLabel("Resource ID (for update/delete):"));
        panel.add(idField);

        panel.add(new JLabel("Resource Name:"));
        panel.add(nameField);

        panel.add(new JLabel("Category:"));
        panel.add(categoryField);

        panel.add(new JLabel("Rental Price:"));
        panel.add(priceField);

        JButton addButton = new JButton("Add Resource");
        JButton viewButton = new JButton("View Resources");
        JButton updateButton = new JButton("Update Resource");
        JButton deleteButton = new JButton("Delete Resource");

        JPanel buttons = new JPanel(new GridLayout(2, 2, 8, 8));
        buttons.add(addButton);
        buttons.add(viewButton);
        buttons.add(updateButton);
        buttons.add(deleteButton);

        panel.add(new JLabel("Operations:"));
        panel.add(buttons);

        addButton.addActionListener(e -> {
            try {
                String name = nameField.getText().trim();
                String category = categoryField.getText().trim();
                double price = Double.parseDouble(priceField.getText().trim());

                if (name.isEmpty() || category.isEmpty() || price < 0) {
                    showMessage("Enter valid resource details.");
                    return;
                }

                String json = "{\"name\":\"" + escape(name)
                        + "\",\"category\":\"" + escape(category)
                        + "\",\"rentalPrice\":" + price + "}";

                sendRequest("POST", "/resources", json);

            } catch (NumberFormatException ex) {
                showMessage("Enter a valid rental price.");
            }
        });

        viewButton.addActionListener(e -> sendRequest("GET", "/resources", null));

        updateButton.addActionListener(e -> {
            try {
                long id = Long.parseLong(idField.getText().trim());
                String name = nameField.getText().trim();
                String category = categoryField.getText().trim();
                double price = Double.parseDouble(priceField.getText().trim());

                if (name.isEmpty() || category.isEmpty() || price < 0) {
                    showMessage("Enter valid resource details.");
                    return;
                }

                String json = "{\"name\":\"" + escape(name)
                        + "\",\"category\":\"" + escape(category)
                        + "\",\"rentalPrice\":" + price + "}";

                sendRequest("PUT", "/resources/" + id, json);

            } catch (NumberFormatException ex) {
                showMessage("Enter a valid resource ID and price.");
            }
        });

        deleteButton.addActionListener(e -> {
            try {
                long id = Long.parseLong(idField.getText().trim());

                int choice = JOptionPane.showConfirmDialog(
                        this,
                        "Delete resource ID " + id + "?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION
                );

                if (choice == JOptionPane.YES_OPTION) {
                    sendRequest("DELETE", "/resources/" + id, null);
                }

            } catch (NumberFormatException ex) {
                showMessage("Enter a valid resource ID.");
            }
        });

        return panel;
    }

    private JPanel createCustomerPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField phoneField = new JTextField();

        panel.add(new JLabel("Customer ID (for update/delete):"));
        panel.add(idField);

        panel.add(new JLabel("Customer Name:"));
        panel.add(nameField);

        panel.add(new JLabel("Phone Number (10 digits):"));
        panel.add(phoneField);

        JButton addButton = new JButton("Add Customer");
        JButton viewButton = new JButton("View Customers");
        JButton updateButton = new JButton("Update Customer");
        JButton deleteButton = new JButton("Delete Customer");

        JPanel buttons = new JPanel(new GridLayout(2, 2, 8, 8));
        buttons.add(addButton);
        buttons.add(viewButton);
        buttons.add(updateButton);
        buttons.add(deleteButton);

        panel.add(new JLabel("Operations:"));
        panel.add(buttons);

        addButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String phone = phoneField.getText().trim();

            if (name.isEmpty() || !phone.matches("[0-9]{10}")) {
                showMessage("Enter a name and a valid 10-digit phone number.");
                return;
            }

            String json = "{\"name\":\"" + escape(name)
                    + "\",\"phone\":\"" + escape(phone) + "\"}";

            sendRequest("POST", "/customers", json);
        });

        viewButton.addActionListener(e -> sendRequest("GET", "/customers", null));

        updateButton.addActionListener(e -> {
            try {
                long id = Long.parseLong(idField.getText().trim());
                String name = nameField.getText().trim();
                String phone = phoneField.getText().trim();

                if (name.isEmpty() || !phone.matches("[0-9]{10}")) {
                    showMessage("Enter a name and a valid 10-digit phone number.");
                    return;
                }

                String json = "{\"name\":\"" + escape(name)
                        + "\",\"phone\":\"" + escape(phone) + "\"}";

                sendRequest("PUT", "/customers/" + id, json);

            } catch (NumberFormatException ex) {
                showMessage("Enter a valid customer ID.");
            }
        });

        deleteButton.addActionListener(e -> {
            try {
                long id = Long.parseLong(idField.getText().trim());

                int choice = JOptionPane.showConfirmDialog(
                        this,
                        "Delete customer ID " + id + "?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION
                );

                if (choice == JOptionPane.YES_OPTION) {
                    sendRequest("DELETE", "/customers/" + id, null);
                }

            } catch (NumberFormatException ex) {
                showMessage("Enter a valid customer ID.");
            }
        });

        return panel;
    }

    private JPanel createRentalPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        JTextField idField = new JTextField();
        JTextField customerIdField = new JTextField();
        JTextField resourceIdField = new JTextField();
        JTextField rentalDateField = new JTextField();
        JTextField returnDateField = new JTextField();

        panel.add(new JLabel("Rental ID (for update/delete):"));
        panel.add(idField);

        panel.add(new JLabel("Customer ID:"));
        panel.add(customerIdField);

        panel.add(new JLabel("Resource ID:"));
        panel.add(resourceIdField);

        panel.add(new JLabel("Rental Date (YYYY-MM-DD):"));
        panel.add(rentalDateField);

        panel.add(new JLabel("Return Date (optional, YYYY-MM-DD):"));
        panel.add(returnDateField);

        JButton addButton = new JButton("Add Rental");
        JButton viewButton = new JButton("View Rentals");
        JButton updateButton = new JButton("Update Return Date");
        JButton deleteButton = new JButton("Delete Rental");

        JPanel buttons = new JPanel(new GridLayout(2, 2, 8, 8));
        buttons.add(addButton);
        buttons.add(viewButton);
        buttons.add(updateButton);
        buttons.add(deleteButton);

        JPanel outer = new JPanel(new BorderLayout(10, 10));
        outer.add(panel, BorderLayout.CENTER);
        outer.add(buttons, BorderLayout.SOUTH);

        addButton.addActionListener(e -> {
            try {
                long customerId = Long.parseLong(customerIdField.getText().trim());
                long resourceId = Long.parseLong(resourceIdField.getText().trim());

                String rentalDate = rentalDateField.getText().trim();
                String returnDate = returnDateField.getText().trim();

                if (!validDate(rentalDate)
                        || (!returnDate.isEmpty() && !validDate(returnDate))) {
                    showMessage("Use valid dates in YYYY-MM-DD format.");
                    return;
                }

                String json = "{\"customer\":{\"id\":" + customerId
                        + "},\"resource\":{\"id\":" + resourceId
                        + "},\"rentalDate\":\"" + rentalDate
                        + "\",\"returnDate\":"
                        + (returnDate.isEmpty() ? "null" : "\"" + returnDate + "\"")
                        + "}";

                sendRequest("POST", "/rentals", json);

            } catch (NumberFormatException ex) {
                showMessage("Enter valid numeric customer and resource IDs.");
            }
        });

        viewButton.addActionListener(e -> sendRequest("GET", "/rentals", null));

        updateButton.addActionListener(e -> {
            try {
                long id = Long.parseLong(idField.getText().trim());
                String returnDate = returnDateField.getText().trim();

                if (!returnDate.isEmpty() && !validDate(returnDate)) {
                    showMessage("Enter a valid return date in YYYY-MM-DD format.");
                    return;
                }

                String json = "{\"returnDate\":"
                        + (returnDate.isEmpty() ? "null" : "\"" + returnDate + "\"")
                        + "}";

                sendRequest("PUT", "/rentals/" + id, json);

            } catch (NumberFormatException ex) {
                showMessage("Enter a valid rental ID.");
            }
        });

        deleteButton.addActionListener(e -> {
            try {
                long id = Long.parseLong(idField.getText().trim());

                int choice = JOptionPane.showConfirmDialog(
                        this,
                        "Delete rental ID " + id + "?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION
                );

                if (choice == JOptionPane.YES_OPTION) {
                    sendRequest("DELETE", "/rentals/" + id, null);
                }

            } catch (NumberFormatException ex) {
                showMessage("Enter a valid rental ID.");
            }
        });

        return outer;
    }

    private void sendRequest(String method, String path, String json) {
        try {
            HttpRequest.Builder builder = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080" + path));

            if (json != null) {
                builder.header("Content-Type", "application/json");
            }

            switch (method) {
                case "POST":
                    builder.POST(HttpRequest.BodyPublishers.ofString(json));
                    break;
                case "PUT":
                    builder.PUT(HttpRequest.BodyPublishers.ofString(json));
                    break;
                case "DELETE":
                    builder.DELETE();
                    break;
                default:
                    builder.GET();
                    break;
            }

            HttpResponse<String> response = client.send(
                    builder.build(),
                    HttpResponse.BodyHandlers.ofString()
            );

            String body = response.body();

            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                if (method.equals("GET")) {
                    showMessage(body.isEmpty() ? "No records found." : body);
                } else {
                    showMessage(body.isEmpty()
                            ? "Operation completed successfully."
                            : body);
                }
            } else {
                showMessage("Request failed (HTTP "
                        + response.statusCode() + "):\n" + body);
            }

        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            showMessage("Request interrupted. Please try again.");
        } catch (Exception ex) {
            showMessage("Could not connect to Spring Boot. "
                    + "Make sure the application is running on port 8080.");
        }
    }

    private boolean validDate(String date) {
        if (!date.matches("\\d{4}-\\d{2}-\\d{2}")) {
            return false;
        }

        try {
            java.time.LocalDate.parse(date);
            return true;
        } catch (java.time.format.DateTimeParseException ex) {
            return false;
        }
    }

    private String escape(String value) {
        return value.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RentalManagementGUI gui = new RentalManagementGUI();
            gui.setVisible(true);
        });
    }
}