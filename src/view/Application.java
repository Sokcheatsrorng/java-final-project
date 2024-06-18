package view;

import repository.UserDao;

import javax.swing.*;
import java.awt.*;

public class Application extends JFrame {
    private BookPanel bookPanel;
    private MemberPanel memberPanel;
    private TransactionPanel transactionPanel;

    public Application() {
        setTitle("Library Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();
        bookPanel = new BookPanel();
        memberPanel = new MemberPanel();
        transactionPanel = new TransactionPanel();

        tabbedPane.addTab("Books", bookPanel);
        tabbedPane.addTab("Members", memberPanel);
        tabbedPane.addTab("Transactions", transactionPanel);

        add(tabbedPane, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        UserDao userDao = new UserDao();
        SwingUtilities.invokeLater(() -> {
            new LoginForm(userDao).setVisible(true);
        });
    }
}
