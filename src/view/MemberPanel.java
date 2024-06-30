package view;

import features.MemberDialog;
import repository.MemberDao;
import domain.Member;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MemberPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private MemberDao memberDAO;

    private int userId;

    public MemberPanel(int userId) {
        this.userId= userId;
        memberDAO = new MemberDao();

        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new Object[]{"ID", "Name", "Email", "Phone", "Address"}, 0);
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
            MemberDialog dialog = new MemberDialog();
            dialog.setVisible(true);
            Member member = dialog.getMember();
            if (member != null) {
                memberDAO.addMember(member);
                loadMembers();
            }
        });

        updateButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                int memberId = (int) tableModel.getValueAt(selectedRow, 0);
                Member existingMember = memberDAO.getMemberById(memberId,userId);
                MemberDialog dialog = new MemberDialog(existingMember,userId);
                dialog.setVisible(true);
                Member updatedMember = dialog.getMember();
                if (updatedMember != null) {
                    memberDAO.updateMember(updatedMember);
                    loadMembers();
                }
            } else {
                JOptionPane.showMessageDialog(MemberPanel.this, "Please select a member to update.");
            }
        });

        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                int memberId = (int) tableModel.getValueAt(selectedRow, 0);
                int choice = JOptionPane.showConfirmDialog(MemberPanel.this,
                        "Are you sure you want to delete this member?", "Delete Member", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    memberDAO.deleteMember(memberId,userId);
                    loadMembers();
                }
            } else {
                JOptionPane.showMessageDialog(MemberPanel.this, "Please select a member to delete.");
            }
        });
    }

    private void loadMembers() {
        tableModel.setRowCount(0);
        for (Member member : memberDAO.getAllMembers(userId)) {
            tableModel.addRow(new Object[]{member.getId(), member.getName(), member.getEmail(), member.getPhone(), member.getAddress()});
        }
    }
}
