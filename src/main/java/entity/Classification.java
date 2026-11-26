package entity;

public class Classification {
    private String id;
    private String prefix;
    private double start;
    private double end;
    private String subject;
    private String parentId;
    private boolean obsoleteStart;
    private boolean obsoleteEnd;

    public Classification(String id, String prefix, double start, double end, String subject, String parentId) {
        this.id = id;
        this.prefix = prefix;
        this.start = start;
        this.end = end;
        this.subject = subject;
        this.parentId = parentId;
        this.obsoleteStart = false;
        this.obsoleteEnd = false;
    }

    public boolean contains(double number) {
        return number >= start && number <= end;
    }

    public String getId() {return id;}

    public String getPrefix() {return prefix;}

    public double getStart() {return start;}

    public double getEnd() {return end;}

    public String getSubject() {return subject;}

    public String getParentId() {return parentId;}

    public boolean isObsoleteStart() {return obsoleteStart;}

    public boolean isObsoleteEnd() {return obsoleteEnd;}


}
