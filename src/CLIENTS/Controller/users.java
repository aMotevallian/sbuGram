package CLIENTS.Controller;

import CLIENTS.Modele.Main;
import CLIENTS.Modele.PageLoader;
import common.user;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.ArrayList;

public class users {
    public ListView<user> people;
    public ArrayList<user> users=new ArrayList<>();
    @FXML
    public void initialize() {
        for (user user:Main.users){
            if (!user.getUsername().equals(Main.currentUser))
                users.add(user);
        }
        people.setItems(FXCollections.observableArrayList(users));
        people.setCellFactory(people -> new usercell());
    }

    public void goToMenu(MouseEvent mouseEvent) {
        try {
            new PageLoader().load("menu");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void goBack(ActionEvent actionEvent) {
        try {
            new PageLoader().load("menu");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
