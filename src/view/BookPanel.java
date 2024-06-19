package view;

import repository.BookDao;
import domain.Book;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BookPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private BookDao bookDAO;

    public BookPanel() {
        bookDAO = new BookDao();

        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new Object[]{"ID", "Title", "Author", "Available"}, 0);
        table = new JTable(tableModel);
        loadBooks();

        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel panel = new JPanel();
        JButton addButton = new JButton("Add Book");
        JButton updateButton = new JButton("Update Book");
        JButton deleteButton = new JButton("Delete Book");

        panel.add(addButton);
        panel.add(updateButton);
        panel.add(deleteButton);

        add(panel, BorderLayout.SOUTH);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Code to add book
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Code to update book
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Code to delete book
            }
        });
    }

    private void loadBooks() {
        tableModel.setRowCount(0);
        for (Book book : bookDAO.getAllBooks()) {
            tableModel.addRow(new Object[]{book.getId(), book.getTitle(), book.getAuthor(), book.isAvailable()});
        }
    }
}
