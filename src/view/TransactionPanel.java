package view;

import repository.TransactionDao;
import domain.Transaction;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TransactionPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private TransactionDao transactionDAO;

    public TransactionPanel() {
        transactionDAO = new TransactionDao();

        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new Object[]{"ID", "Book ID", "Member ID", "Issue Date", "Return Date"}, 0);
        table = new JTable(tableModel);
        loadTransactions();

        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel panel = new JPanel();
        JButton addButton = new JButton("Add Transaction");
        JButton updateButton = new JButton("Update Transaction");
        JButton deleteButton = new JButton("Delete Transaction");

        panel.add(addButton);
        panel.add(updateButton);
        panel.add(deleteButton);

        add(panel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> {
            // Code to add transaction
        });

        updateButton.addActionListener(e -> {
            // Code to update transaction
        });

        deleteButton.addActionListener(e -> {
            // Code to delete transaction
        });
    }

    private void loadTransactions() {
        tableModel.setRowCount(0);
        for (Transaction transaction : transactionDAO.getAllTransactions()) {
            tableModel.addRow(new Object[]{transaction.getId(), transaction.getBookId(), transaction.getTransactionDate(), transaction.getReturnDate()});
        }
    }
}
