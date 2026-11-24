package entity;

public class Book {
    private final String id;
    private final String title;
    private final String author;
    private final String callNumber;

    public Book(String id, String title, String author, String callNumber) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.callNumber = callNumber;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getCallNumber() {
        return callNumber;
    }
}
