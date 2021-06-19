package CLIENTS.Controller;

import CLIENTS.Modele.Main;
import CLIENTS.Modele.PageLoader;
import common.Post;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class cellController {
    public AnchorPane root;
    public Label username;
    public Label title;
    public Circle img;
    public TextArea description;
    public Label date;
    public Text likes;

    Post post;
    boolean liked=false;

    public cellController(Post post) throws IOException {
        new PageLoader().load("cellC", this);
        this.post = post;
    }

    public AnchorPane init() {
        username.setText(post.getAuthor());
        title.setText(post.getTitle());
        description.setText(post.getDescription());
        description.setEditable(false);
        likes.setText(String.valueOf(post.getLikes()));
        likes.setTextAlignment(TextAlignment.CENTER);
        File f= new File("src\\CLIENTS\\images\\" + post.getAuthor() + ".jpg");
        if (!f.exists()){
            img.setFill(new ImagePattern(new Image("CLIENTS\\images\\defaultProfile.jpg" , false)));
        }
        else {
            Image image = new Image("CLIENTS\\images\\" + post.getAuthor() + ".jpg" ,false);
            img.setFill(new ImagePattern(image));
        }
        date.setText(post.getDateStr());
        return root;
    }
    public void addCm(ActionEvent actionEvent) throws IOException {
        new PageLoader().load2("cm" , new cm(post));
    }
    public void repost(ActionEvent actionEvent) {
        Post newPost=new Post();
        newPost.setTitle(post.getTitle());
        newPost.setAuthor(post.getAuthor());
        newPost.setDescription(post.getDescription());
        newPost.setPostedBy(Main.currentUser);
        try {
            Socket socket=new Socket("localhost" , 8000);
            ObjectOutputStream os=new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream is =new ObjectInputStream(socket.getInputStream());
            os.writeUTF("add post");
            os.flush();
            os.writeObject(newPost);
            os.flush();
            Main.posts= (ArrayList<Post>) is.readObject();
            socket.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            new PageLoader().load("profile");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void like(ActionEvent actionEvent) {
        if (liked){
            //do nothing.
            likes.setText(String.valueOf(post.getLikes()));
        }
        else {
            try {
                Socket like = new Socket("localhost", 8000);
                ObjectOutputStream oos = new ObjectOutputStream(like.getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(like.getInputStream());
                oos.writeUTF("like");
                oos.flush();
                oos.writeObject(post);
                oos.flush();
                oos.writeUTF(Main.currentUser);
                oos.flush();
                Main.posts = (ArrayList<Post>) ois.readObject();
                like.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            liked = true;
        }
    }
}
