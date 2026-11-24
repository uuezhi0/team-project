package use_case.search;

import entity.Book;

import java.util.ArrayList;
import java.util.List;

public class SearchOutputData {

    List<Book> books;


    public SearchOutputData(List<Book> books) {
        this.books = books;
    }
}
