package features;

import domain.Transaction;
import domain.User;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import repository.BookDao;
import repository.MemberDao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class TransactionDialog extends JDialog {
    private JComboBox<Integer> bookIdComboBox;
    private JComboBox<Integer> memberIdComboBox;
    private JDatePickerImpl transactionDatePicker;
    private JDatePickerImpl returnDatePicker;
    private Transaction transaction;
    private BookDao bookDao;
    private MemberDao memberDao;
    private int userId; // Added userId field

    public TransactionDialog(int userId) {
        this.userId = userId; // Store the userId
        setTitle("Add Transaction");
        setLayout(new BorderLayout());
        setModal(true);
        setSize(400, 250);
        setLocationRelativeTo(null);

        // Initialize DAOs
        bookDao = new BookDao();
        memberDao = new MemberDao();

        JPanel panel = new JPanel(new GridLayout(4, 2));

        panel.add(new JLabel("Book ID:"));
        bookIdComboBox = new JComboBox<>();
        populateBookIds(); // Populate the combo box with book IDs
        panel.add(bookIdComboBox);

        panel.add(new JLabel("Member ID:"));
        memberIdComboBox = new JComboBox<>();
        populateMemberIds(); // Populate the combo box with member IDs
        panel.add(memberIdComboBox);

        panel.add(new JLabel("Transaction Date:"));
        UtilDateModel transactionModel = new UtilDateModel();
        Properties transactionProps = new Properties();
        transactionProps.put("text.today", "Today");
        transactionProps.put("text.month", "Month");
        transactionProps.put("text.year", "Year");
        JDatePanelImpl transactionDatePanel = new JDatePanelImpl(transactionModel, transactionProps);
        transactionDatePicker = new JDatePickerImpl(transactionDatePanel, new org.jdatepicker.impl.DateComponentFormatter());
        panel.add(transactionDatePicker);

        panel.add(new JLabel("Return Date:"));
        UtilDateModel returnModel = new UtilDateModel();
        Properties returnProps = new Properties();
        returnProps.put("text.today", "Today");
        returnProps.put("text.month", "Month");
        returnProps.put("text.year", "Year");
        JDatePanelImpl returnDatePanel = new JDatePanelImpl(returnModel, returnProps);
        returnDatePicker = new JDatePickerImpl(returnDatePanel, new org.jdatepicker.impl.DateComponentFormatter());
        panel.add(returnDatePicker);

        add(panel, BorderLayout.CENTER);

        JButton saveButton = new JButton("Save");
        add(saveButton, BorderLayout.SOUTH);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int bookId = (int) bookIdComboBox.getSelectedItem();
                    int memberId = (int) memberIdComboBox.getSelectedItem();
                    Date transactionDate = (Date) transactionDatePicker.getModel().getValue();
                    Date returnDate = (Date) returnDatePicker.getModel().getValue();

                    // Validate bookId and memberId
                    if (isValidId(bookId) && isValidId(memberId)) {
                        transaction = new Transaction(0,bookId,memberId,transactionDate,returnDate );
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(TransactionDialog.this,
                                "Invalid input for Book ID or Member ID. Please select a valid ID from the list.",
                                "Input Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(TransactionDialog.this,
                            "Invalid input for Book ID or Member ID. Please select a valid ID from the list.",
                            "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public Transaction getTransaction() {
        return transaction;
    }

    private boolean isValidId(int id) {
        // Simple validation: ID must be greater than 0
        return id > 0;
    }

    private void populateBookIds() {
        List<Integer> bookIds = bookDao.getAllBookIds(userId); // Fetch book IDs for the specific user
        for (int id : bookIds) {
            bookIdComboBox.addItem(id);
        }
    }

    private void populateMemberIds() {
        List<Integer> memberIds = memberDao.getAllMemberIds(userId); // Fetch all member IDs (if needed)
        for (int id : memberIds) {
            memberIdComboBox.addItem(id);
        }
    }
}
