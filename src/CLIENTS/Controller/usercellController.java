package CLIENTS.Controller;

import CLIENTS.Modele.Main;
import CLIENTS.Modele.PageLoader;
import common.user;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class usercellController {
    public AnchorPane root;
    public Circle profile;
    public Text username;
    boolean followed=false;
    boolean unfollowed=false;

    user user;
    public usercellController(user user) {
        try {
            new PageLoader().load("usercell", this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.user=user;
    }

    public AnchorPane init() throws FileNotFoundException {
        username.setText(user.getUsername());
        File f= new File("src\\CLIENTS\\images\\" + user.getUsername() + ".jpg");
        if (!f.exists()){
            profile.setFill(new ImagePattern(new Image("CLIENTS\\images\\defaultProfile.jpg" , false)));
        }
        else {
            Image image = new Image(new FileInputStream(f));
            profile.setFill(new ImagePattern(image));
        }
        return root;
    }

    public void follow(ActionEvent actionEvent) {
        if (!followed) {
            for (user user : Main.users) {
                if (user.getUsername().equals(Main.currentUser)) {
                    try {
                        Socket follow = new Socket("localhost", 8000);
                        ObjectOutputStream oos = new ObjectOutputStream(follow.getOutputStream());
                        ObjectInputStream ois = new ObjectInputStream(follow.getInputStream());
                        oos.writeUTF("follow");
                        oos.flush();
                        oos.writeObject(user);
                        oos.flush();
                        oos.writeUTF(this.user.getUsername());
                        oos.flush();
                        Main.users = (ArrayList<common.user>) ois.readObject();
                        follow.close();
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
            followed = true;
        }
    }

    public void unfollow(ActionEvent actionEvent) {
        if (!unfollowed) {
            for (user user : Main.users) {
                if (user.getUsername().equals(Main.currentUser)) {
                    try {
                        Socket unfollow = new Socket("localhost", 8000);
                        ObjectOutputStream oos = new ObjectOutputStream(unfollow.getOutputStream());
                        ObjectInputStream ois = new ObjectInputStream(unfollow.getInputStream());
                        oos.writeUTF("unfollow");
                        oos.flush();
                        oos.writeObject(user);
                        oos.flush();
                        oos.writeUTF(this.user.getUsername());
                        oos.flush();
                        Main.users = (ArrayList<common.user>) ois.readObject();
                        unfollow.close();
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
            unfollowed = true;
        }
    }
    public void visitPage (MouseEvent mouseEvent) throws IOException {
        System.out.println(this.user.getUsername());
        new PageLoader().load2("othersProfile" , new othersProfile(this.user.getUsername()));
    }
}
