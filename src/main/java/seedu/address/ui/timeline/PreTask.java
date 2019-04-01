package seedu.address.ui.timeline;

public class PreTask {
    private String title;
    private String cate;
    private float start;
    private float end;

    public PreTask(String ttl, String cat, float s, float e) {
        this.title = ttl;
        this.cate = cat;
        this.start = s;
        this.end = e;
    }
    
    public String getTitle() {
        return title;
    }
    public String getCate() {
        return cate;
    }
    public float getStart() {
        return start;
    }
    public float getEnd() {
        return end;
    }
    
}
