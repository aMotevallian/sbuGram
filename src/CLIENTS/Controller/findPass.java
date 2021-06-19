package CLIENTS.Controller;

import CLIENTS.Modele.Main;
import CLIENTS.Modele.PageLoader;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;

public class findPass {
    public TextField username;
    public Button showPass;
    public TextField inCaseQues;
    public Text password;
    public Text noMatch;
    public Text urPassIs;

    public void showPass(ActionEvent actionEvent) {
        if (Main.clientsUserPass.containsKey(username.getText())) {
            if (Main.clientUserFindPass.containsKey(username.getText()) && Main.clientUserFindPass.get(username.getText()).equals(inCaseQues.getText())){
                urPassIs.setVisible(true);
                password.setText(Main.clientsUserPass.get(username.getText()));
                password.setVisible(true);
                noMatch.setVisible(false);
            }
            else {
                urPassIs.setVisible(false);
                password.setVisible(false);
                noMatch.setVisible(true);
            }
        }
        else{
            urPassIs.setVisible(false);
            password.setVisible(false);
            noMatch.setVisible(true);
        }

    }

    public void goBack(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("logIn");
    }
}
