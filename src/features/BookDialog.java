package features;

import domain.Book;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Properties;

public class BookDialog extends JDialog {
    private JTextField titleField;
    private JTextField authorField;
    private JTextField isbnField;
    private JCheckBox availableCheckBox;
    private JDatePickerImpl publicationDatePicker;
    private Book book;

    private boolean confirmed;

    public BookDialog(Book book) {
        this.book = book;
        this.confirmed = false;

        setTitle(book == null ? "Add Book" : "Update Book");
        setLayout(new BorderLayout());
        setModal(true);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(6, 2));
        panel.add(new JLabel("Title:"));
        titleField = new JTextField();
        panel.add(titleField);

        panel.add(new JLabel("Author:"));
        authorField = new JTextField();
        panel.add(authorField);

        panel.add(new JLabel("ISBN:"));
        isbnField = new JTextField();
        panel.add(isbnField);

        panel.add(new JLabel("Publication Date:"));
        UtilDateModel model = new UtilDateModel();
        Properties properties = new Properties();
        properties.put("text.today", "Today");
        properties.put("text.month", "Month");
        properties.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, properties);
        publicationDatePicker = new JDatePickerImpl(datePanel, new org.jdatepicker.impl.DateComponentFormatter());
        panel.add(publicationDatePicker);

        panel.add(new JLabel("Available:"));
        availableCheckBox = new JCheckBox();
        panel.add(availableCheckBox);

        add(panel, BorderLayout.CENTER);

        JButton saveButton = new JButton("Save");
        add(saveButton, BorderLayout.SOUTH);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveBook();
            }
        });

        if (book != null) {
            fillFieldsWithBookData();
        }
    }

    private void saveBook() {
        String title = titleField.getText();
        String author = authorField.getText();
        String isbn = isbnField.getText();
        boolean available = availableCheckBox.isSelected();
        Date publicationDate = (Date) publicationDatePicker.getModel().getValue();

        if (book == null) {
            book = new Book();
        }

        book.setTitle(title);
        book.setAuthor(author);
        book.setIsbn(isbn);
        book.setAvailable(available);
        book.setPublicationDate(publicationDate);

        confirmed = true;
        dispose();
    }

    private void fillFieldsWithBookData() {
        titleField.setText(book.getTitle());
        authorField.setText(book.getAuthor());
        isbnField.setText(book.getIsbn());
        availableCheckBox.setSelected(book.isAvailable());

        if (book.getPublicationDate() != null) {
            UtilDateModel model = (UtilDateModel) publicationDatePicker.getModel();
            model.setValue(book.getPublicationDate());
        }
    }

    public Book getBook() {
        return book;
    }

    public boolean isConfirmed() {
        return confirmed;
    }
}
