package interface_adapter.search;

import use_case.search.SearchInputBoundary;
import use_case.search.SearchInputData;

public class SearchController {

    private final SearchInputBoundary searchInteractor;

    public SearchController(SearchInputBoundary searchInteractor) {
        this.searchInteractor = searchInteractor;
    }

    public void execute(String topic, String numRecords) {
        SearchInputData searchInputData = new SearchInputData(topic, numRecords);

        searchInteractor.execute(searchInputData);


    }


}
