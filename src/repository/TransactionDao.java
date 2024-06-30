package repository;

import domain.Transaction;
import util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDao {

    private final Connection connection;

    public TransactionDao() {
        this.connection = DBUtil.getConnection();
    }

    // Create a new transaction
    public void addTransaction(Transaction transaction) {
        String sql = "INSERT INTO Transaction (user_id, book_id, transaction_date, return_date) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, transaction.getUserId());
            statement.setInt(2, transaction.getBookId());
            statement.setDate(3, new java.sql.Date(transaction.getTransactionDate().getTime()));
            if (transaction.getReturnDate() != null) {
                statement.setDate(4, new java.sql.Date(transaction.getReturnDate().getTime()));
            } else {
                statement.setNull(4, Types.DATE);
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get all transactions
    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM Transaction";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int userId = resultSet.getInt("user_id");
                int bookId = resultSet.getInt("book_id");
                Date transactionDate = resultSet.getDate("transaction_date");
                Date returnDate = resultSet.getDate("return_date");

                transactions.add(new Transaction(id, userId, bookId, transactionDate, returnDate));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactions;
    }

    // Get a transaction by ID
    public Transaction getTransactionById(int id) {
        Transaction transaction = null;
        String sql = "SELECT * FROM Transaction WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                int bookId = resultSet.getInt("book_id");
                Date transactionDate = resultSet.getDate("transaction_date");
                Date returnDate = resultSet.getDate("return_date");

                transaction = new Transaction(id, userId, bookId, transactionDate, returnDate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transaction;
    }

    // Update a transaction
    public void updateTransaction(Transaction transaction) {
        String sql = "UPDATE Transaction SET user_id = ?, book_id = ?, transaction_date = ?, return_date = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, transaction.getUserId());
            statement.setInt(2, transaction.getBookId());
            statement.setDate(3, new java.sql.Date(transaction.getTransactionDate().getTime()));
            if (transaction.getReturnDate() != null) {
                statement.setDate(4, new java.sql.Date(transaction.getReturnDate().getTime()));
            } else {
                statement.setNull(4, Types.DATE);
            }
            statement.setInt(5, transaction.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete a transaction
    public void deleteTransaction(int id) {
        String sql = "DELETE FROM Transaction WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Transaction> getTransactionsByUserId(int userId) {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transaction WHERE user_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int bookId = resultSet.getInt("book_id");
                Date transactionDate = resultSet.getDate("transaction_date");
                Date returnDate = resultSet.getDate("return_date");

                Transaction transaction = new Transaction(id, userId, bookId, transactionDate, returnDate);
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exception properly in your application
        }

        return transactions;
    }

}
