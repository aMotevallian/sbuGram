package CLIENTS.Controller;

import CLIENTS.Modele.PageLoader;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class Menu {
    public void goToProfile(MouseEvent mouseEvent) throws IOException {
        new PageLoader().load("profile");
    }

    public void addPost(MouseEvent mouseEvent) throws IOException {
        new PageLoader().load("newPost");
    }

    public void logOut(MouseEvent mouseEvent) throws IOException {
        new PageLoader().load("logIn");
    }

    public void goBack(MouseEvent mouseEvent) throws IOException {
        new PageLoader().load("home");
    }

    public void addPeople(MouseEvent mouseEvent) {
        try {
            new PageLoader().load("users");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
