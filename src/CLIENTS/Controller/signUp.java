package CLIENTS.Controller;

import CLIENTS.Modele.Client;
import CLIENTS.Modele.Main;
import CLIENTS.Modele.PageLoader;
import common.user;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class signUp {
    @FXML
    public TextField username , name , question;
    public Text notMatch , taken , notStrong , completeAll;
    public PasswordField password , password2;
    public TextField imgPath;
    public Text pathNotFound;


    public void signUp(ActionEvent actionEvent) throws IOException {
        File img=new File(imgPath.getText());
        String regex="([a-z]*[A-Z]*[0-9]*){8,}";
        if (Main.clientsUserPass.containsKey(username.getText())){
            taken.setVisible(true);
            notMatch.setVisible(false);
            notStrong.setVisible(false);
            completeAll.setVisible(false);
            pathNotFound.setVisible(false);
        }
        else if (!password.getText().equals(password2.getText())){
            taken.setVisible(false);
            notMatch.setVisible(true);
            notStrong.setVisible(false);
            completeAll.setVisible(false);
            pathNotFound.setVisible(false);
        }
        else if (!password.getText().matches(regex)){
            taken.setVisible(false);
            notMatch.setVisible(false);
            notStrong.setVisible(true);
            completeAll.setVisible(false);
            pathNotFound.setVisible(false);
        }
        else if (name.getText().equals("") || username.getText().equals("") || password.getText().equals("") || question.getText().equals("")){
            completeAll.setVisible(true);
            taken.setVisible(false);
            notMatch.setVisible(false);
            notStrong.setVisible(false);
            pathNotFound.setVisible(false);
        }
        else if (!img.exists()){
            pathNotFound.setVisible(true);
            completeAll.setVisible(false);
            taken.setVisible(false);
            notMatch.setVisible(false);
            notStrong.setVisible(false);
        }
        else{
            taken.setVisible(false);
            notMatch.setVisible(false);
            notStrong.setVisible(false);
            completeAll.setVisible(false);
            pathNotFound.setVisible(false);
            Main.clientsUserPass.put(username.getText() , password.getText());
            Main.clientUserFindPass.put(username.getText() , question.getText());
            new Client(username.getText() , password.getText() , name.getText() , question.getText() , imgPath.getText());
            Main.currentUser=username.getText();
            Socket client;
            client = new Socket("localhost", 8000);
            ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
            oos.writeUTF("get users");
            oos.flush();
            try {
                Main.users= (ArrayList<user>) ois.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            client.close();
            new PageLoader().load("home");
        }
    }

}
