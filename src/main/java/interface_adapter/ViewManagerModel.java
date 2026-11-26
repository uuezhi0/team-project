package interface_adapter;

import java.util.ArrayList;
import java.util.List;

public class ViewManagerModel {

    private final List<ViewManagerModelListener> listeners = new ArrayList<>();

    private String currentViewName = "";

    public String getCurrentViewName() {
        return this.currentViewName;
    }

    public void setState(String currentViewName) {
        this.currentViewName = currentViewName;
    }

    public void addListener(ViewManagerModelListener listener) {
        listeners.add(listener);
    }

    public void notifyViewChanged() {
        for (ViewManagerModelListener listener : listeners) {
            listener.viewChanged();
        }
    }

}
