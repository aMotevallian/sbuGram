package CLIENTS.Controller;

import common.user;
import javafx.scene.control.ListCell;

import java.io.FileNotFoundException;

public class usercell extends ListCell<user> {
    @Override
    protected void updateItem(user user, boolean empty) {
        super.updateItem(user, empty);
        if (user!=null){

            try {
                setGraphic(new usercellController(user).init());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    }
}
