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
        String sql = "INSERT INTO Member (name, email, phone, address, user_id) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, member.getName());
            statement.setString(2, member.getEmail());
            statement.setString(3, member.getPhone());
            statement.setString(4, member.getAddress());
            statement.setInt(5, member.getUserId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get all members for a specific user
    public List<Member> getAllMembers(int userId) {
        List<Member> members = new ArrayList<>();
        String sql = "SELECT * FROM Member WHERE user_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");
                String address = resultSet.getString("address");

                members.add(new Member(id, name, email, phone, address, userId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return members;
    }

    // Get a member by ID for a specific user
    public Member getMemberById(int id, int userId) {
        Member member = null;
        String sql = "SELECT * FROM Member WHERE id = ? AND user_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.setInt(2, userId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");
                String address = resultSet.getString("address");

                member = new Member(id, name, email, phone, address, userId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return member;
    }

    // Update a member
    public void updateMember(Member member) {
        String sql = "UPDATE Member SET name = ?, email = ?, phone = ?, address = ? WHERE id = ? AND user_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, member.getName());
            statement.setString(2, member.getEmail());
            statement.setString(3, member.getPhone());
            statement.setString(4, member.getAddress());
            statement.setInt(5, member.getId());
            statement.setInt(6, member.getUserId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete a member
    public void deleteMember(int id, int userId) {
        String sql = "DELETE FROM Member WHERE id = ? AND user_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.setInt(2, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get all member IDs for a specific user
    public List<Integer> getAllMemberIds(int userId) {
        List<Integer> memberIds = new ArrayList<>();
        String sql = "SELECT id FROM Member WHERE user_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                memberIds.add(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return memberIds;
    }
}
