package view;

import features.TransactionDialog;
import repository.TransactionDao;
import domain.Transaction;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

public class TransactionPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private TransactionDao transactionDAO;

    private int userId;

    public TransactionPanel(int userId) {
        transactionDAO = new TransactionDao();
        this.userId =userId;

        setLayout(new BorderLayout());

        // Initialize table and table model
        tableModel = new DefaultTableModel(new Object[]{"ID", "Book ID", "Member ID", "Issue Date", "Return Date"}, 0);
        table = new JTable(tableModel);
        loadTransactions(); // Load initial data into the table

        add(new JScrollPane(table), BorderLayout.CENTER);

        // Panel for buttons
        JPanel panel = new JPanel();
        JButton addButton = new JButton("Add Transaction");
        JButton updateButton = new JButton("Update Transaction");
        JButton deleteButton = new JButton("Delete Transaction");

        panel.add(addButton);
        panel.add(updateButton);
        panel.add(deleteButton);

        add(panel, BorderLayout.SOUTH);

        // Add ActionListener for Add button
        // Inside addButton ActionListener
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TransactionDialog dialog = new TransactionDialog(userId); // Pass userId to the dialog
                dialog.setVisible(true);
                Transaction transaction = dialog.getTransaction();
                if (transaction != null) {
                    transactionDAO.addTransaction(transaction);
                    loadTransactions();
                }
            }
        });


// Inside updateButton ActionListener
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    int transactionId = (int) tableModel.getValueAt(selectedRow, 0);
                    Transaction existingTransaction = transactionDAO.getTransactionById(transactionId);
                    if (existingTransaction != null) {
                        TransactionDialog dialog = new TransactionDialog(userId);
                        dialog.setVisible(true);
                        Transaction updatedTransaction = dialog.getTransaction();
                        if (updatedTransaction != null) {
                            transactionDAO.updateTransaction(updatedTransaction);
                            loadTransactions();
                        }
                    } else {
                        JOptionPane.showMessageDialog(TransactionPanel.this, "Selected transaction not found.");
                    }
                } else {
                    JOptionPane.showMessageDialog(TransactionPanel.this, "Please select a transaction to update.");
                }
            }
        });


        // Add ActionListener for Delete button
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    int transactionId = (int) tableModel.getValueAt(selectedRow, 0);
                    int choice = JOptionPane.showConfirmDialog(TransactionPanel.this,
                            "Are you sure you want to delete this transaction?", "Delete Transaction", JOptionPane.YES_NO_OPTION);
                    if (choice == JOptionPane.YES_OPTION) {
                        transactionDAO.deleteTransaction(transactionId);
                        loadTransactions();
                    }
                } else {
                    JOptionPane.showMessageDialog(TransactionPanel.this, "Please select a transaction to delete.");
                }
            }
        });
    }

    // Method to load transactions into the table
    private void loadTransactions() {
        tableModel.setRowCount(0); // Clear current table data
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Date format for display

        // Populate table with transactions for the specific user
        for (Transaction transaction : transactionDAO.getTransactionsByUserId(userId)) {
            Object[] row = new Object[]{
                    transaction.getId(),
                    transaction.getBookId(),
                    transaction.getUserId(),
                    dateFormat.format(transaction.getTransactionDate()),
                    transaction.getReturnDate() != null ? dateFormat.format(transaction.getReturnDate()) : ""
            };
            tableModel.addRow(row); // Add row to table model
        }
    }

}
