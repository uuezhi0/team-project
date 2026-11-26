package interface_adapter.search;

import interface_adapter.ViewManagerModel;
import use_case.search.SearchOutputBoundary;
import use_case.search.SearchOutputData;

public class SearchPresenter implements SearchOutputBoundary {
    private final SearchViewModel searchViewModel;
    private final ViewManagerModel viewManagerModel;

    public SearchPresenter(ViewManagerModel viewManagerModel, SearchViewModel searchViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.searchViewModel = searchViewModel;
    }



    @Override
    public void prepareFailView(String errorMessage) {
        this.searchViewModel.getSearchState().setErrorMessage(errorMessage);
        this.searchViewModel.notifyStateChanged();
    }

    @Override
    public void prepareSuccessView(SearchOutputData searchOutputData) {
        this.searchViewModel.getSearchState().setErrorMessage(null);
        this.searchViewModel.setBooks(searchOutputData.getBooks());
        this.searchViewModel.notifyStateChanged();

        viewManagerModel.notifyViewChanged();
    }
}
