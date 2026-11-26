package entity;

public class Classification {
    private final String id;
    private final String prefix;
    private final double start;
    private final double end;
    private final String subject;
    private final String parentId;
    private final boolean obsoleteStart;
    private final boolean obsoleteEnd;

    public Classification(String id,
                          String prefix,
                          double start,
                          double end,
                          String subject,
                          String parentId,
                          boolean obsoleteStart,
                          boolean obsoleteEnd) {
        this.id = id;
        this.prefix = prefix;
        this.start = start;
        this.end = end;
        this.subject = subject;
        this.parentId = parentId;
        this.obsoleteStart = obsoleteStart;
        this.obsoleteEnd = obsoleteEnd;
    }

    public String getId() {return id;}

    public String getPrefix() {return prefix;}

    public double getStart() {return start;}

    public double getEnd() {return end;}

    public String getSubject() {return subject;}

    public String getParentId() {return parentId;}

    public boolean isObsoleteStart() {return obsoleteStart;}

    public boolean isObsoleteEnd() {return obsoleteEnd;}

    public boolean contains(double number) {
        return number >= start && number <= end;
    }

    public boolean matches(String prefix, double number) {
        return this.prefix.equals(prefix) && contains(number);
    }
}
