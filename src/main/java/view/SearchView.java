package view;

import interface_adapter.search.SearchViewModel;
import interface_adapter.search.SearchViewModelListener;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchView extends JPanel implements ActionListener, SearchViewModelListener {

    private final SearchViewModel searchViewModel;

    // TEXT FIELDS
    private final JTextField searchBar = new JTextField(15);
    private final JButton Search;




    public SearchView(SearchViewModel  searchViewModel) {
        this.searchViewModel = searchViewModel;
        searchViewModel.addListener(this);

        Search = new JButton(SearchViewModel.SEARCH_BUTTON_LABEL);

        JPanel Header = new JPanel();
        Header.add(new JLabel(SearchViewModel.SEARCH_LABEL));
        Header.add(searchBar);
        Header.add(Search);


        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(Header);


    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void stateChanged() {

    }
}
