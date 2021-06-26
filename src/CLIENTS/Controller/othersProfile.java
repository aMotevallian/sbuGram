package CLIENTS.Controller;

import CLIENTS.Modele.Main;
import CLIENTS.Modele.PageLoader;
import common.Post;
import common.user;
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

public class othersProfile {
    @FXML
    Circle pic;
    @FXML
    Text username , flwingNum , flwrNum;
    @FXML
    TextArea info;
    @FXML
    ListView<Post> posts;

    String name;
    public othersProfile(String username) {
        this.name=username;
    }
    @FXML
    public void initialize() throws FileNotFoundException {
        Main.lastPage="othersProfile";
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
        ArrayList<Post> postz=new ArrayList<>();
        for (Post p: Main.posts){
            if (p.getPostedBy().equals(name))
                postz.add(p);
        }
        posts.setItems(FXCollections.observableArrayList(postz));
        posts.setCellFactory(posts -> new cell());
        posts.getItems().sort((o1, o2) -> {
            if (o1.getDate().after(o2.getDate()))
                return -1;
            if (o1.getDate().before(o2.getDate()))
                return 1;
            return 0;
        });

        username.setText(name);
        File f= new File("src\\CLIENTS\\images\\" + name + ".jpg");
        if (!f.exists()){
            pic.setFill(new ImagePattern(new Image("CLIENTS\\images\\defaultProfile.jpg" , false)));
        }
        else {
            Image image = new Image(new FileInputStream(f));
            pic.setFill(new ImagePattern(image));
        }


        user thisUser=null;
        for (user u:Main.users)
            if (u.getUsername().equals(name)){
                thisUser=u;
                info.setText(u.getInfo());
                info.setEditable(false);
            }
        if (thisUser != null) {
            flwingNum.setText(String.valueOf(thisUser.getFollowings()));
            flwrNum.setText(String.valueOf(thisUser.getFollowers()));
        }

    }
    @FXML
    public void back(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("users");
    }

}
