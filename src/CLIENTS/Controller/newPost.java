package CLIENTS.Controller;

import CLIENTS.Modele.Main;
import CLIENTS.Modele.PageLoader;
import common.Post;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;

public class newPost {
    public TextArea description;
    public TextField title;
    Post current=new Post();

    public void publish(ActionEvent actionEvent) {
        current.setAuthor(Main.currentUser);
        current.setTitle(title.getText());
        current.setDescription(description.getText());
        current.setPostedBy(Main.currentUser);
        Date date=new java.util.Date();
        current.setDate( date);
        current.setDate(date.toString());
        Main.posts.add(current);
        try {
            Socket socket=new Socket("localhost" , 8000);
            ObjectOutputStream os=new ObjectOutputStream(socket.getOutputStream());
            os.writeUTF("add post");
            os.flush();
            os.writeObject(current);
            os.flush();
            new PageLoader().load("home");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
