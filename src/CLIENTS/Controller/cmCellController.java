package CLIENTS.Controller;

import CLIENTS.Modele.PageLoader;
import common.comment;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.io.File;
import java.io.IOException;

public class cmCellController {
    comment cm;
    @FXML
    Label cmBox , username;
    @FXML
    Circle profile;
    @FXML
    AnchorPane root;
    public cmCellController(comment cm) throws IOException {
        new PageLoader().load("cmCell", this);
        this.cm=cm;
    }
    public AnchorPane init() {
        username.setText(cm.getUsername());
        cmBox.setText(cm.getComment());
        File f= new File("src\\CLIENTS\\images\\" + cm.getUsername() + ".jpg");
        if (!f.exists()){
            profile.setFill(new ImagePattern(new Image("CLIENTS\\images\\defaultProfile.jpg" , false)));
        }
        else {
            Image image = new Image("CLIENTS\\images\\" + cm.getUsername()  + ".jpg" ,false);
            profile.setFill(new ImagePattern(image));
        }
        return root;
    }
}
