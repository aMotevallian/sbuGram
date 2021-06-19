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
import java.util.ArrayList;
import java.util.Comparator;

public class home {
    public ListView<Post> postList;
    public ArrayList<Post> posts= new ArrayList<>();

    @FXML
    public void initialize() {
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
