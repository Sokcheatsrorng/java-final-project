package view;

import repository.MemberDao;
import domain.Member;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MemberPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private MemberDao memberDAO;

    public MemberPanel() {
        memberDAO = new MemberDao();

        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new Object[]{"ID", "Name", "Email"}, 0);
        table = new JTable(tableModel);
        loadMembers();

        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel panel = new JPanel();
        JButton addButton = new JButton("Add Member");
        JButton updateButton = new JButton("Update Member");
        JButton deleteButton = new JButton("Delete Member");

        panel.add(addButton);
        panel.add(updateButton);
        panel.add(deleteButton);

        add(panel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> {
            // Code to add member
        });

        updateButton.addActionListener(e -> {
            // Code to update member
        });

        deleteButton.addActionListener(e -> {
            // Code to delete member
        });
    }

    private void loadMembers() {
        tableModel.setRowCount(0);
        for (Member member : memberDAO.getAllMembers()) {
            tableModel.addRow(new Object[]{member.getId(), member.getName(), member.getEmail()});
        }
    }
}
