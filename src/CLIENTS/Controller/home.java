package CLIENTS.Controller;

import CLIENTS.Modele.Main;
import CLIENTS.Modele.PageLoader;
import common.Post;
import common.user;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Comparator;

public class home {
    public ListView<Post> postList;
    public ArrayList<Post> posts= new ArrayList<>();

    @FXML
    public void initialize() {
        Main.lastPage="home";
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
        user mainUser=null;
        for (user user:Main.users)
            if (Main.currentUser.equals(user.getUsername()))
                mainUser=user;
        for (Post p:Main.posts){
            if (mainUser != null) {
                if (mainUser.getFollowingList().contains(p.getPostedBy()) || p.getPostedBy().equals(Main.currentUser))
                    posts.add(p);
            }
        }

        postList.setItems(FXCollections.observableArrayList(posts));
        postList.setCellFactory(postList -> new cell());
        postList.getItems().sort((o1, o2) -> {
            if (o1.getDate().after(o2.getDate()))
                return -1;
            if (o1.getDate().before(o2.getDate()))
                return 1;
            return 0;
        });

    }
    public void goToMenu(MouseEvent mouseEvent) throws IOException {
        new PageLoader().load("menu");
    }

    public void refresh(MouseEvent mouseEvent) {
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
        posts=new ArrayList<>();
        user mainUser=null;
        for (user user:Main.users)
            if (Main.currentUser.equals(user.getUsername()))
                mainUser=user;
        for (Post p:Main.posts){
            if (mainUser != null) {
                if (mainUser.getFollowingList().contains(p.getPostedBy()) || p.getPostedBy().equals(Main.currentUser))
                    posts.add(p);
            }
        }
        postList.setItems(FXCollections.observableArrayList(posts));
        postList.setCellFactory(postList -> new cell());
        postList.getItems().sort((o1, o2) -> {
            if (o1.getDate().after(o2.getDate()))
                return -1;
            if (o1.getDate().before(o2.getDate()))
                return 1;
            return 0;
        });
    }
}
