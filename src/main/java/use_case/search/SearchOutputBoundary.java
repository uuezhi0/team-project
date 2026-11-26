package use_case.search;

public interface SearchOutputBoundary {

    void prepareFailView(String errorMessage);
    void prepareSuccessView(SearchOutputData searchOutputData);


}
