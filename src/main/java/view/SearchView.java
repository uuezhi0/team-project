package view;

import entity.Book;
import interface_adapter.search.SearchController;
import interface_adapter.search.SearchViewModel;
import interface_adapter.search.SearchViewModelListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchView extends JPanel implements ActionListener, SearchViewModelListener {

    private final SearchViewModel searchViewModel;

    // TEXT FIELDS
    private final JTextField searchTextField = new JTextField(15);
    private final JButton searchButton;
    private final JTextField numRecordsTextField = new JTextField(15);

    private SearchController searchController = null;



    public SearchView(SearchViewModel  searchViewModel) {
        this.searchViewModel = searchViewModel;
        searchViewModel.addListener(this);

        searchButton = new JButton(SearchViewModel.SEARCH_BUTTON_LABEL);

        JPanel searchBar = new JPanel();
        searchBar.add(new JLabel(SearchViewModel.SEARCH_LABEL));
        searchBar.add(searchTextField);

        JPanel numRecordsBar = new JPanel();
        numRecordsBar.add(new JLabel(SearchViewModel.NUM_RECORDS_LABEL));
        numRecordsBar.add(numRecordsTextField);

        JPanel searchButtonPanel = new JPanel();
        searchButtonPanel.add(searchButton);

        searchButton.addActionListener(
                // This creates an anonymous subclass of ActionListener and instantiates it.
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(searchButton)) {
                            String topic = searchTextField.getText();
                            String numRecords = numRecordsTextField.getText();

                            searchController.execute(topic, numRecords);
                        }
                    }
                }
        );

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(searchBar, BorderLayout.NORTH);
        this.add(numRecordsBar, BorderLayout.NORTH);
        this.add(searchButtonPanel, BorderLayout.NORTH);



    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void stateChanged() {
        if(searchViewModel.getSearchState().getErrorMessage() == null) {
            int counter = 1;

            for(Book book : searchViewModel.getBooks()) {
                JPanel bookPanel = new JPanel();
                bookPanel.setLayout(new BoxLayout(bookPanel, BoxLayout.Y_AXIS));
                bookPanel.add(new JLabel("------ Book #" + counter + " ------"));
                bookPanel.add(new JLabel("Title: " + book.getTitle()));
                bookPanel.add(new JLabel("Author: " + book.getAuthor()));
                bookPanel.add(new JLabel("Call Number: " + book.getCallNumber()));
                this.add(bookPanel);
                counter++;
            }

        }
        else {
            JOptionPane.showMessageDialog(this, searchViewModel.getSearchState().getErrorMessage());
        }
    }

    public void setSearchController(SearchController searchController) {
        this.searchController = searchController;
    }
}
