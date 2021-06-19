package CLIENTS.Controller;

import common.Post;
import javafx.scene.control.ListCell;

import java.io.IOException;

public class cell extends ListCell<Post> {
    @Override
    protected void updateItem(Post post, boolean empty) {
        super.updateItem(post, empty);
        if (post!=null){
            try {
                setGraphic(new cellController(post).init());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
