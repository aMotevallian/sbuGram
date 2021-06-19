package common;

import java.io.Serial;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

public class Post implements Serializable {
    @Serial
    private static final long serialVersionUID = 3214608089268803053L;
    String title;
    String description;
    String author;
    String postedBy;
    String dateStr;
    Date date;

    public void setDate(String date) {
        this.dateStr = date;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date  getDate() {
        return date;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    int likes = 0;
    public ConcurrentSkipListSet<String> pplWhoLiked = new ConcurrentSkipListSet<>();
    public ArrayList<comment> comments = new ArrayList<>();

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public synchronized void like(String username) {
        pplWhoLiked.add(username);
        this.likes++;
    }

    public synchronized int getLikes() {
        return this.likes;
    }

    public synchronized void addCm(comment cm) {
        comments.add(cm);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(title, post.title);
    }

    @Override
    public String toString() {
        return this.title;
    }
/*public static int compare(Post o1, Post o2) throws ParseException {
        if (o1.getDateStr()){
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
            o1.setDate(formatter.parse(o1.getDateStr()));
        }
        if (o2.getDate()==null){
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
            o2.setDate(formatter.parse(o2.getDateStr()));
        }
    }*/
}
