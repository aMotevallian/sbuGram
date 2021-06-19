package CLIENTS.Controller;

import CLIENTS.Modele.Main;
import CLIENTS.Modele.PageLoader;
import common.Post;
import common.user;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Text;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class profile {
    public ImageView profileImage;
    public Text username;
    public ListView<Post> posts;
    public Text flwing;
    public Text flwr;
    public TextArea info;
    public Button done;


    @FXML
    public void initialize() {
        for (user user : Main.users){
            if (user.getUsername().equals(Main.currentUser)){
                flwing.setText(String.valueOf(user.getFollowings()));
                flwr.setText(String.valueOf(user.getFollowers()));
            }
        }
        done.setVisible(false);
        username.setText(Main.currentUser);
        info.setEditable(false);
        File f= new File("src\\CLIENTS\\images\\" + username.getText() + ".jpg");
        if (!f.exists()){
            profileImage.setImage(new Image("CLIENTS\\images\\defaultProfile.jpg" , false));
        }
        else {
            Image image = new Image("CLIENTS\\images\\" +username.getText() + ".jpg" ,false);
            profileImage.setImage(image);
        }
        List<Post> myPosts=new ArrayList<>();
        for (Post p: Main.posts){
            if (p.getPostedBy().equals(username.getText()))
                myPosts.add(p);
        }
        posts.setItems(FXCollections.observableArrayList(myPosts));
        posts.setCellFactory(posts -> new cell());
    }

    public void edit(ActionEvent actionEvent) {
        info.setEditable(true);
        done.setVisible(true);
    }

    public void goBack(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("menu");
    }

    public void done(ActionEvent actionEvent) {
        for (user user : Main.users){
            if (user.getUsername().equals(Main.currentUser)){
                Socket setInfo = null;
                try {
                    setInfo = new Socket("localhost" , 8000);
                    ObjectOutputStream oos = new ObjectOutputStream(setInfo.getOutputStream());
                    ObjectInputStream ois = new ObjectInputStream(setInfo.getInputStream());
                    oos.writeUTF("set info");
                    oos.flush();
                    oos.writeObject(user);
                    oos.flush();
                    oos.writeUTF(info.getText());
                    oos.flush();
                    Main.users= (ArrayList<common.user>) ois.readObject();
                    info.setEditable(false);
                    done.setVisible(false);
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }

                for (user us: Main.users){
                    if (us.getUsername().equals(Main.currentUser)){
                        info.setText(us.getInfo());
                    }
                }
            }
        }
    }
}
