package view;

import features.BookDialog;
import repository.BookDao;
import domain.Book;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;

public class BookPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private BookDao bookDAO;

    private int userId;

    public BookPanel(int userId) {

        this.userId = userId;

        bookDAO = new BookDao();

        setLayout(new BorderLayout());

        // Updated table model with additional columns for ISBN and PUBLISH_DATE
        tableModel = new DefaultTableModel(new Object[]{"ID", "Title", "Author", "Available", "ISBN", "PUBLISH_DATE"}, 0);
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
                BookDialog dialog = new BookDialog(null);
                dialog.setVisible(true);
                Book book = dialog.getBook();
                if (book != null) {
                    book.setUserId(userId); // Set user ID for the new book
                    bookDAO.addBook(book);
                    loadBooks();
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    int bookId = (int) tableModel.getValueAt(selectedRow, 0);
                    Book existingBook = bookDAO.getBookByIdAndUserId(bookId, userId); // Fetch book by ID and user ID
                    BookDialog dialog = new BookDialog(existingBook);
                    dialog.setVisible(true);
                    Book updatedBook = dialog.getBook();
                    if (updatedBook != null) {
                        bookDAO.updateBook(updatedBook);
                        loadBooks();
                    }
                } else {
                    JOptionPane.showMessageDialog(BookPanel.this, "Please select a book to update.");
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    int bookId = (int) tableModel.getValueAt(selectedRow, 0);

                    // Use a flag to track if the confirmation dialog has been shown
                    boolean confirmed = false;

                    // Show confirmation dialog only once
                    if (!confirmed) {
                        int choice = JOptionPane.showConfirmDialog(BookPanel.this,
                                "Are you sure you want to delete this book?", "Delete Book", JOptionPane.YES_NO_OPTION);
                        confirmed = true; // Set the flag to true after showing the dialog

                        if (choice == JOptionPane.YES_OPTION) {
                            bookDAO.deleteBook(bookId, userId); // Delete book by ID and user ID
                            loadBooks(); // Reload the table
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(BookPanel.this, "Please select a book to delete.");
                }
            }
        });


    }

    private void loadBooks() {
        tableModel.setRowCount(0);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Format for PUBLISH_DATE

        List<Book> books = bookDAO.getAllBooksByUserId(userId);
        for (Book book : books) {
            Object[] row = new Object[]{
                    book.getId(),
                    book.getTitle(),
                    book.getAuthor(),
                    book.isAvailable(),
                    book.getIsbn(),
                    book.getPublicationDate() != null ? dateFormat.format(book.getPublicationDate()) : ""
            };
            tableModel.addRow(row);
        }
    }
}
