package CLIENTS.Controller;

import CLIENTS.Modele.Main;
import CLIENTS.Modele.PageLoader;
import common.Post;
import common.comment;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class cm {
    public ListView<comment> comments;
    public Circle profileImg;
    public TextArea commentBox;
    public Text username;


    Post post;

    public cm(Post post) throws IOException {
        this.post=post;
    }
    comment cm=new comment();

    @FXML
    public void initialize() throws FileNotFoundException {
        comments.setItems(FXCollections.observableArrayList(post.comments));
        comments.setCellFactory(comments -> new cmCell());
        username.setText(Main.currentUser);
        File f= new File("src\\CLIENTS\\images\\" + Main.currentUser + ".jpg");
        if (!f.exists()){
            profileImg.setFill(new ImagePattern(new Image("CLIENTS\\images\\defaultProfile.jpg" , false)));
        }
        else {
            Image image = new Image(new FileInputStream(f));
            profileImg.setFill(new ImagePattern(image));
        }
        cm.setUsername(Main.currentUser);
    }

    public void send(MouseEvent mouseEvent) throws FileNotFoundException {
        cm.setComment(commentBox.getText());
        try {
            Socket com=new Socket(Main.IP , Main.PORT);
            ObjectOutputStream oos=new ObjectOutputStream(com.getOutputStream());
            ObjectInputStream ois=new ObjectInputStream(com.getInputStream());
            oos.writeUTF("add cm");
            oos.writeObject(cm);
            oos.writeObject(post);
            oos.flush();
            Main.posts= (ArrayList<Post>) ois.readObject();
            com.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        commentBox.setText("");
        comments.getItems().add(cm);
        cm=new comment();
        File f= new File("src\\CLIENTS\\images\\" + Main.currentUser + ".jpg");
        if (!f.exists()){
            profileImg.setFill(new ImagePattern(new Image("CLIENTS\\images\\defaultProfile.jpg" , false)));
        }
        else {
            Image image = new Image(new FileInputStream(f));
            profileImg.setFill(new ImagePattern(image));
        }
        cm.setUsername(Main.currentUser);
    }
    public void back(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("home");
    }
}
