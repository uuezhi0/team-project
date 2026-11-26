package entity;

import java.util.ArrayList;
import java.util.List;

public class ReadingList {
    private final List<Book> books;

    public ReadingList() {
        this.books = new ArrayList<>();
    }

    public boolean containsBook(String bookId) {
        for (Book book : books) {
            if (book.getId().equals(bookId)) {
                return true;
            }
        }

            return false;
    }

    public boolean addBook(Book book) {
        if (containsBook(book.getId())) {
            return false;
        }

        books.add(book);
        return true; // returns boolean to communicate result of operation
    }

    public boolean removeBook(String bookId) {
        for (Book book : books) {
            if (book.getId().equals(bookId)) {
                books.remove(book);
                return true;
            }
        }

        return false;
    }

    public List<Book> getBooks() {
        return new ArrayList<>(books); // returns copy of saved books
    }
}