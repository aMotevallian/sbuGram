package CLIENTS.Controller;

import CLIENTS.Modele.Main;
import CLIENTS.Modele.PageLoader;
import common.Post;
import common.user;
import javafx.application.Application;
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
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class profile {
    public Text username;
    public ListView<Post> posts;
    public Text flwing;
    public Text flwr;
    public TextArea info;
    public Button done;
    public Circle profile;


    @FXML
    public void initialize() throws IOException {
        Main.lastPage="profile";
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
        try {
            Socket clientPosts = new Socket(Main.IP, Main.PORT);
            ObjectOutputStream ooss = new ObjectOutputStream(clientPosts.getOutputStream());
            ObjectInputStream oiss = new ObjectInputStream(clientPosts.getInputStream());
            ooss.writeUTF("initialize posts");
            ooss.flush();
            Main.posts = (ArrayList<Post>) oiss.readObject();
            clientPosts.close();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        for (user user : Main.users){
            if (user.getUsername().equals(Main.currentUser)){
                flwing.setText(String.valueOf(user.getFollowings()));
                flwr.setText(String.valueOf(user.getFollowers()));
                info.setText(user.getInfo());
            }
        }
        done.setVisible(false);
        username.setText(Main.currentUser);
        info.setEditable(false);
        File f= new File("src\\CLIENTS\\images\\" + username.getText() + ".jpg");
        if (!f.exists()){
            profile.setFill(new ImagePattern(new Image("CLIENTS\\images\\defaultProfile.jpg" )));
        }
        else {
            Image image=new Image(new FileInputStream(f));
            profile.setFill(new ImagePattern(image));
        }
        List<Post> myPosts=new ArrayList<>();
        for (Post p: Main.posts){
            if (p.getPostedBy().equals(username.getText()))
                myPosts.add(p);
        }
        posts.setItems(FXCollections.observableArrayList(myPosts));
        posts.setCellFactory(posts -> new cell());
        posts.getItems().sort((o1, o2) -> {
            if (o1.getDate().after(o2.getDate()))
                return -1;
            if (o1.getDate().before(o2.getDate()))
                return 1;
            return 0;
        });
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

                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }

                for (user us: Main.users){
                    if (us.getUsername().equals(Main.currentUser)){
                        info.setText(us.getInfo());
                    }
                }
                done.setVisible(false);
            }
        }
    }
}
