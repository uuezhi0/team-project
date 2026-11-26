package use_case.search;

public class SearchInputData {
    private String topic;
    private String numRecords;

    public SearchInputData(String topic, String numRecords) {

        this.topic = topic;
        this.numRecords = numRecords;
    }

    public String getTopic() {
        return topic;
    }
    public String getNumRecords() {
        return numRecords;
    }



}
