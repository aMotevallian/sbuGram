package common;

import java.io.Serial;
import java.io.Serializable;

public class comment implements Serializable {
    @Serial
    private static final long serialVersionUID = 6993212211354653197L;
    String username;
    String comment;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUsername() {
        return username;
    }

    public String getComment() {
        return comment;
    }
}
