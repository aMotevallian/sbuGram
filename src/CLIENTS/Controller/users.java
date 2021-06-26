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
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class users {
    public ListView<user> people;
    public ArrayList<user> users=new ArrayList<>();
    @FXML
    public void initialize() {
        try {
            Socket getUsers=new Socket(Main.IP , Main.PORT);
            ObjectOutputStream oos=new ObjectOutputStream(getUsers.getOutputStream());
            ObjectInputStream ois=new ObjectInputStream(getUsers.getInputStream());
            oos.writeUTF("get users");
            oos.flush();
            Main.users= (ArrayList<user>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        for (user user:Main.users){
            if (!user.getUsername().equals(Main.currentUser))
                users.add(user);
        }
        people.setItems(FXCollections.observableArrayList(users));
        people.setCellFactory(people -> new usercell());
    }


    public void goBack(ActionEvent actionEvent) {
        try {
            new PageLoader().load("menu");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
