package repository;

import domain.Book;
import util.DBUtil;
import features.BookDialog;

import javax.swing.*;
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
        String sql = "INSERT INTO Book (title, author, isbn, available, publication_date, user_id) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getIsbn());
            statement.setBoolean(4, book.isAvailable());
            statement.setDate(5, new java.sql.Date(book.getPublicationDate().getTime()));
            statement.setInt(6, book.getUserId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get all books by user_id
    public List<Book> getAllBooksByUserId(int userId) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM Book WHERE user_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String isbn = resultSet.getString("isbn");
                boolean available = resultSet.getBoolean("available");
                Date publicationDate = resultSet.getDate("publication_date");

                books.add(new Book(id, title, author, isbn, available, publicationDate, userId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

    // Get a book by ID and user_id
    public Book getBookByIdAndUserId(int id, int userId) {
        Book book = null;
        String sql = "SELECT * FROM Book WHERE id = ? AND user_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.setInt(2, userId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String isbn = resultSet.getString("isbn");
                boolean available = resultSet.getBoolean("available");
                Date publicationDate = resultSet.getDate("publication_date");

                book = new Book(id, title, author, isbn, available, publicationDate, userId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return book;
    }

    // Update a book
    public void updateBook(Book book) {
        String sql = "UPDATE Book SET title = ?, author = ?, isbn = ?, available = ?, publication_date = ? WHERE id = ? AND user_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getIsbn());
            statement.setBoolean(4, book.isAvailable());
            statement.setDate(5, new java.sql.Date(book.getPublicationDate().getTime()));
            statement.setInt(6, book.getId());
            statement.setInt(7, book.getUserId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete a book
    public void deleteBook(int id, int userId) {
        // Prompt the user for confirmation
        int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this book?", "Delete Book", JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            String sql = "DELETE FROM Book WHERE id = ? AND user_id = ?";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);
                statement.setInt(2, userId);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Deletion canceled.");
        }
    }

    // Get a book by ID
    public Book getBookById(int bookId) {
        Book book = null;
        String sql = "SELECT * FROM Book WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, bookId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String title = resultSet.getString("title");
                    String author = resultSet.getString("author");
                    String isbn = resultSet.getString("isbn");
                    Date publicationDate = resultSet.getDate("publication_date");
                    boolean available = resultSet.getBoolean("available");
                    int userId = resultSet.getInt("user_id");

                    book = new Book(bookId, title, author, isbn, available, publicationDate, userId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception properly in your application
        }

        return book;
    }

    // Method to add or update a book using a dialog
    public Book addOrUpdateBookWithDialog(Book currentBook, int userId) {
        BookDialog dialog = new BookDialog(currentBook);
        dialog.setVisible(true);

        if (dialog.isConfirmed()) {
            Book updatedBook = dialog.getBook();
            if (updatedBook.getId() == 0) {
                // If book ID is 0, it means it's a new book (add operation)
                addBook(updatedBook);
            } else {
                // Otherwise, it's an existing book (update operation)
                updateBook(updatedBook);
            }
            return updatedBook;
        }

        return null; // Dialog was canceled
    }

    public List<Integer> getAllBookIds(int userId) {
        List<Integer> bookIds = new ArrayList<>();
        String sql = "SELECT id FROM Book where user_id = ?";

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int bookId = resultSet.getInt("id");
                bookIds.add(bookId);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception properly in your application
        }

        return bookIds;
    }
}
