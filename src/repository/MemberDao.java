package repository;

import domain.Member;
import util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MemberDao {

    private final Connection connection;

    public MemberDao() {
        this.connection = DBUtil.getConnection();
    }

    // Create a new member
    public void addMember(Member member) {
        String sql = "INSERT INTO Member (name, email, phone, address) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, member.getName());
            statement.setString(2, member.getEmail());
            statement.setString(3, member.getPhone());
            statement.setString(4, member.getAddress());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get all members
    public List<Member> getAllMembers() {
        List<Member> members = new ArrayList<>();
        String sql = "SELECT * FROM Member";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");
                String address = resultSet.getString("address");

                members.add(new Member(id, name, email, phone, address));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return members;
    }

    // Get a member by ID
    public Member getMemberById(int id) {
        Member member = null;
        String sql = "SELECT * FROM Member WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");
                String address = resultSet.getString("address");

                member = new Member(id, name, email, phone, address);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return member;
    }

    // Update a member
    public void updateMember(Member member) {
        String sql = "UPDATE Member SET name = ?, email = ?, phone = ?, address = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, member.getName());
            statement.setString(2, member.getEmail());
            statement.setString(3, member.getPhone());
            statement.setString(4, member.getAddress());
            statement.setInt(5, member.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete a member
    public void deleteMember(int id) {
        String sql = "DELETE FROM Member WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
