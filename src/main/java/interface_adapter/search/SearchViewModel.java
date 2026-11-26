package interface_adapter.search;

import entity.Book;

import java.util.ArrayList;
import java.util.List;

public class SearchViewModel {
    private List<Book> books;
    public int numBooks = books.size();

    public static final String VIEW_NAME = "Search";
    public static final String SEARCH_LABEL = "Search:";
    public static final String SEARCH_BUTTON_LABEL = "Search";

    private final List<SearchViewModelListener> listeners = new ArrayList<>();


    public SearchViewModel() {
        this.books = new ArrayList<>();
    }

    public void addListener(SearchViewModelListener listener) {
        this.listeners.add(listener);
    }

    public void notifyStateChanged() {
        for(SearchViewModelListener listener : listeners) {
            listener.stateChanged();
        }
    }



}
