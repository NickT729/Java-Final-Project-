package Lab_5_Starter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Paths;
import java.util.*;

public class CellPhoneInventory extends JFrame {
    JLabel labelModel, labelManufacturer, labelRetailPrice, labelBanner;
    JTextField jTextFieldModel, jTextFieldManufacturer, jTextFieldRetailPrice;
    JButton jButtonAdd, jButtonSave, jButtonNext, jButtonShow;
    JPanel newPanel, buttonPanel;
    ArrayList<CellPhone> phoneArrayList = new ArrayList<>();

    // Constructor to set up the GUI
    public CellPhoneInventory() {
        setTitle("Cellphone Inventory");
        setLayout(new BorderLayout());

        // Banner Label
        labelBanner = new JLabel("Cellphone Inventory Management");
        labelBanner.setFont(new Font("Serif", Font.BOLD, 20));
        labelBanner.setForeground(Color.BLUE);

        // Labels and text fields
        labelModel = new JLabel("Model");
        labelManufacturer = new JLabel("Manufacturer");
        labelRetailPrice = new JLabel("Retail Price");
        jTextFieldModel = new JTextField(15);
        jTextFieldManufacturer = new JTextField(15);
        jTextFieldRetailPrice = new JTextField(15);

        // Buttons
        jButtonAdd = new JButton("Add");
        jButtonSave = new JButton("Save");
        jButtonNext = new JButton("Next");
        jButtonShow = new JButton("Show Inventory");

        // Panels
        newPanel = new JPanel(new GridLayout(3, 2));
        newPanel.add(labelModel);
        newPanel.add(jTextFieldModel);
        newPanel.add(labelManufacturer);
        newPanel.add(jTextFieldManufacturer);
        newPanel.add(labelRetailPrice);
        newPanel.add(jTextFieldRetailPrice);
        buttonPanel = new JPanel(new GridLayout(2, 2));
        buttonPanel.add(jButtonAdd);
        buttonPanel.add(jButtonNext);
        buttonPanel.add(jButtonSave);
        buttonPanel.add(jButtonShow);
        // Add components to the frame (this is Line 60)
        add(labelBanner, BorderLayout.NORTH);
        add(newPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);  // Line 60

        // Exception handling within the Add button's action listener
        jButtonAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Read user inputs
                    String model = jTextFieldModel.getText();
                    String manufacturer = jTextFieldManufacturer.getText();
                    double price = Double.parseDouble(jTextFieldRetailPrice.getText());

                    // Create a new CellPhone object (which may throw custom exceptions)
                    CellPhone phone = new CellPhone(model, manufacturer, price);

                    // Add the phone to the inventory
                    phoneArrayList.add(phone);

                    // Display the updated inventory
                    StringBuilder inventoryDisplay = new StringBuilder();
                    for (CellPhone p : phoneArrayList) {
                        inventoryDisplay.append(p.toString());
                    }
                    JOptionPane.showMessageDialog(null, inventoryDisplay.toString());

                } catch (InvalidModelException ex) {
                    // Handle invalid model input
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Invalid Model", JOptionPane.ERROR_MESSAGE);
                } catch (InvalidManufacturerException ex) {
                    // Handle invalid manufacturer input
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Invalid Manufacturer", JOptionPane.ERROR_MESSAGE);
                } catch (InvalidRetailPriceException ex) {
                    // Handle invalid retail price input
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Invalid Retail Price", JOptionPane.ERROR_MESSAGE);
                } catch (NumberFormatException ex) {
                    // Handle invalid input for price (if user enters something that isn't a number)
                    JOptionPane.showMessageDialog(null, "Error: Please enter a valid number for price.", "Input Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    // Handle any other unexpected errors
                    JOptionPane.showMessageDialog(null, "Unexpected error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Next Button
        jButtonNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jTextFieldModel.setText(null);
                jTextFieldManufacturer.setText(null);
                jTextFieldRetailPrice.setText(null);
            }
        });

        // Save Button
        jButtonSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Formatter outCellPhoneList = null;
                try {
                    // Open a file stream for output
                    outCellPhoneList = new Formatter("cellPhones.txt");
                    for (CellPhone phone : phoneArrayList) {
                        outCellPhoneList.format("%s %s %.2f%n",
                                phone.getModel(),
                                phone.getManufacturer(),
                                phone.getRetailPrice());
                    }
                    JOptionPane.showMessageDialog(null, "Cellphone list saved to file.");
                } catch (SecurityException | FileNotFoundException | IllegalFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Error saving to file: " + ex.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Unexpected error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                } finally {
                    if (outCellPhoneList != null) {
                        outCellPhoneList.close();
                    }
                }
            }
        });

        // Show Inventory Button
        jButtonShow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String msg = String.format("%-20s%-20s%10s%n", "Model", "Manufacturer", "Retail Price");
                try (Scanner input = new Scanner(Paths.get("cellPhones.txt"))) {
                    while (input.hasNext()) {
                        msg += String.format("%-20s%-20s%10.2f%n",
                                input.next(),
                                input.next(),
                                input.nextDouble());
                    }
                } catch (NoSuchElementException | IllegalStateException | IOException ex) {
                    JOptionPane.showMessageDialog(null, "Error reading file: " + ex.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Unexpected error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
                JOptionPane.showMessageDialog(null, msg);
            }
        });
    }

    // Main Method to launch the GUI
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CellPhoneInventory frame = new CellPhoneInventory();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 300);
            frame.setVisible(true);
        });
    }
}
