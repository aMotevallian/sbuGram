package CLIENTS.Controller;

import common.comment;
import javafx.scene.control.ListCell;

import java.io.IOException;


public class cmCell extends ListCell<comment> {
    @Override
    protected void updateItem(comment cm, boolean empty) {
        super.updateItem(cm, empty);
        if (cm!=null){
            try {
                setGraphic(new cmCellController(cm).init());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
