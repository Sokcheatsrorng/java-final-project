package features;

import domain.Member;
import repository.MemberDao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MemberDialog extends JDialog {
    private JTextField nameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JTextField addressField;
    private Member member;
    private MemberDao memberDao;
    private int userId; // New field to hold userId

    public MemberDialog() {}

    public MemberDialog(Member member, int userId) {
        this.member = member;
        this.userId = userId; // Store the userId
        setTitle(member == null ? "Add Member" : "Update Member");
        setLayout(new BorderLayout());
        setModal(true);
        setSize(300, 250);
        setLocationRelativeTo(null);

        memberDao = new MemberDao(); // Initialize DAO

        JPanel panel = new JPanel(new GridLayout(5, 2));
        panel.add(new JLabel("Name:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Email:"));
        emailField = new JTextField();
        panel.add(emailField);

        panel.add(new JLabel("Phone:"));
        phoneField = new JTextField();
        panel.add(phoneField);

        panel.add(new JLabel("Address:"));
        addressField = new JTextField();
        panel.add(addressField);

        add(panel, BorderLayout.CENTER);

        JButton saveButton = new JButton("Save");
        add(saveButton, BorderLayout.SOUTH);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveMember();
            }
        });

        if (member != null) {
            nameField.setText(member.getName());
            emailField.setText(member.getEmail());
            phoneField.setText(member.getPhone());
            addressField.setText(member.getAddress());
        }
    }

    private void saveMember() {
        String name = nameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String address = addressField.getText();

        if (member == null) {
            member = new Member(0, name, email, phone, address, userId); // Pass userId when creating new member
            memberDao.addMember(member); // Add member to database
        } else {
            member.setName(name);
            member.setEmail(email);
            member.setPhone(phone);
            member.setAddress(address);
            memberDao.updateMember(member); // Update member in database
        }
        dispose();
    }

    public Member getMember() {
        return member;
    }
}
