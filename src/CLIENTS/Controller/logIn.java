package CLIENTS.Controller;

import CLIENTS.Modele.Client;
import CLIENTS.Modele.Main;
import CLIENTS.Modele.PageLoader;
import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.IOException;

public class logIn {

    @FXML
    public TextField username;
    public Button logIn;
    public PasswordField password;
    public TextField pShow;
    public Text pForgot;
    public Text wrongP;

    public void logIn(ActionEvent actionEvent) throws IOException {
        String user=username.getText();
        String pass=password.getText();
        if (Main.clientsUserPass.containsKey(user) && Main.clientsUserPass.get(user).equals(pass)){
            wrongP.setVisible(false);
            Main.currentUser=user;
            new PageLoader().load("home");
            new Client(user , pass);

        }
        else {
            wrongP.setVisible(true);
        }
    }

    public void signUp(MouseEvent mouseEvent) throws IOException {
        new PageLoader().load("signUp");
    }

    public void findPassword(MouseEvent mouseEvent) throws IOException {
        new PageLoader().load("findPass");
    }

    public void show(ActionEvent actionEvent) {
        if (!pShow.isVisible()) {
            pShow.setVisible(true);
            password.setVisible(false);
            pShow.setText(password.getText());
        }
        else {
            pShow.setVisible(false);
            password.setVisible(true);
            password.setText(pShow.getText());
        }
    }

}
