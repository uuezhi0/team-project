package interface_adapter.search;

import entity.Book;

import java.util.ArrayList;
import java.util.List;

public class SearchViewModel {
    private List<Book> books;

    public static final String VIEW_NAME = "Search";
    public static final String SEARCH_LABEL = "Search:";
    public static final String SEARCH_BUTTON_LABEL = "Search";
    public static final String NUM_RECORDS_LABEL = "Number of Records:";

    private final List<SearchViewModelListener> listeners = new ArrayList<>();

    private SearchState searchState = new SearchState();

    public void setBooks(List<Book> books) {
        this.books = books;
    }

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

    public SearchState getSearchState() {
        return this.searchState;
    }

    public List<Book> getBooks() {
        return this.books;
    }


}
