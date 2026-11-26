package use_case.search;

import entity.Book;

import java.util.ArrayList;
import java.util.List;

public class SearchOutputData {

    private List<Book> books;


    public SearchOutputData(List<Book> books) {
        this.books = books;
    }

    public List<Book> getBooks() {
        return this.books;
    }
}
