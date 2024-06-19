package repository;

import domain.Book;
import util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDao {

    private final Connection connection;

    public BookDao() {
        this.connection = DBUtil.getConnection();
    }

    // Create a new book
    public void addBook(Book book) {
        String sql = "INSERT INTO Book (title, author, isbn, available) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getIsbn());
            statement.setBoolean(4, book.isAvailable());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get all books
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM Book";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String isbn = resultSet.getString("isbn");
                boolean available = resultSet.getBoolean("available");

                books.add(new Book(id, title, author, isbn, available));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

    // Get a book by ID
    public Book getBookById(int id) {
        Book book = null;
        String sql = "SELECT * FROM Book WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String isbn = resultSet.getString("isbn");
                boolean available = resultSet.getBoolean("available");

                book = new Book(id, title, author, isbn, available);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return book;
    }

    // Update a book
    public void updateBook(Book book) {
        String sql = "UPDATE Book SET title = ?, author = ?, isbn = ?, available = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getIsbn());
            statement.setBoolean(4, book.isAvailable());
            statement.setInt(5, book.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete a book
    public void deleteBook(int id) {
        String sql = "DELETE FROM Book WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
