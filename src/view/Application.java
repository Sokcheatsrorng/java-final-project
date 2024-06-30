package view;

import repository.UserDao;

import javax.swing.*;
import java.awt.*;

public class Application extends JFrame {
    private BookPanel bookPanel;
    private MemberPanel memberPanel;
    private TransactionPanel transactionPanel;

    private int userId;

    public Application(int userId) {

        this.userId = userId;

        setTitle("Library Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create tabs for different functionalities
        JTabbedPane tabbedPane = new JTabbedPane();
        bookPanel = new BookPanel(userId);
        memberPanel = new MemberPanel(userId);
        transactionPanel = new TransactionPanel(userId);

        tabbedPane.addTab("Books", bookPanel);
        tabbedPane.addTab("Members", memberPanel);
        tabbedPane.addTab("Transactions", transactionPanel);

        add(tabbedPane, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        // Initialize UserDao for user authentication
        UserDao userDao = new UserDao();

        // Launch the login form in the event dispatch thread (EDT)
        SwingUtilities.invokeLater(() -> {
            new LoginForm(userDao).setVisible(true);
        });
    }
}
