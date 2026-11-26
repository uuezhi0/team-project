package use_case.search;


import app.API;
import entity.Book;

import java.util.List;

public class SearchInteractor implements SearchInputBoundary{
    private SearchOutputBoundary searchPresenter;


    public SearchInteractor(SearchOutputBoundary searchPresenter) {
        this.searchPresenter = searchPresenter;
    }
    public void execute(SearchInputData searchInputData) {
        if(searchInputData.getTopic().equals("")) {
            searchPresenter.prepareFailView("Enter a topic to search!");
        }
        else {
            try {
                int numRecords = Integer.parseInt(searchInputData.getNumRecords());
                if(numRecords <= 0) {
                    searchPresenter.prepareFailView("Number of records must be at least one.");
                    return;
                }
                if(numRecords > 10) {
                    searchPresenter.prepareFailView("Number of records must be at most 10.");
                    return;
                }

                List<Book> books = API.getByTopic((searchInputData.getTopic()), numRecords);
                if(books.isEmpty()) {
                    searchPresenter.prepareFailView("No books found in Robarts. Try another topic.");
                    return;
                }

                final SearchOutputData searchOutputData = new SearchOutputData(books);
                searchPresenter.prepareSuccessView(searchOutputData);
            }
            catch (NumberFormatException e){
                searchPresenter.prepareFailView("Number of records must be an integer.");
            }



        }

    }
}
