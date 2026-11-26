package app;

import interface_adapter.ViewManagerModel;
import interface_adapter.search.SearchViewModel;
import view.SearchView;
import view.ViewManager;

import javax.swing.*;
import java.awt.*;

public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    final ViewManagerModel viewManagerModel = new ViewManagerModel();
    ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    private SearchView searchView;
    private SearchViewModel searchViewModel;

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }


    public AppBuilder addSearchView() {
        searchViewModel = new SearchViewModel();
        searchView = new SearchView(searchViewModel);
        cardPanel.add(searchView, SearchViewModel.VIEW_NAME);
        return this;
    }
    public AppBuilder addSearchUseCase() {


        return this;
    }


    public JFrame build() {
        final JFrame application = new JFrame("Library App");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        application.add(cardPanel);

        viewManagerModel.setState(SearchViewModel.VIEW_NAME);
        viewManagerModel.notifyViewChanged();

        return application;
    }




}
