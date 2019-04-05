package seedu.address.ui.timeline;

/**
 * This class is to store processed data.
 */
public class PreTask {
    private String title;
    private String cate;
    private String day;
    private String month;
    private String year;
    private float start;
    private float end;

    public PreTask(String ttl, String cat, float s, float e) {
        this.title = ttl;
        this.cate = cat;
        this.start = s;
        this.end = e;
    }
    public void setDay(String s) {
        this.day = s;
    }

    public void setMonth(String s) {
        this.month = s;
    }

    public void setYear(String s) {
        this.year = s;
    }


    public String getTitle() {
        return title;
    }

    public String getCate() {
        return cate;
    }

    public String getDat() {
        return day;
    }

    public String getMonth() {
        return month;
    }
    
    public String getYear() {
        return year;
    }

    public float getStart() {
        return start;
    }
    
    public float getEnd() {
        return end;
    }
}
