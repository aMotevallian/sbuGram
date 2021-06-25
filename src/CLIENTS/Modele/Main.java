package CLIENTS.Modele;

import common.Post;
import common.user;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Main extends Application {

    public static Map<String, String> clientsUserPass;
    public static Map<String, String> clientUserFindPass;
    public static ArrayList<Post> posts;
    public static String currentUser;
    public static ArrayList<user> users;
    public static final String IP="localhost";
    public static final int PORT=8000;


    static {
        try {
            Socket allData;
            allData = new Socket(IP, PORT);
            ObjectOutputStream oos = new ObjectOutputStream(allData.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(allData.getInputStream());
            oos.writeUTF("fileData");
            oos.flush();
            clientsUserPass = new ConcurrentHashMap<>((Map<String, String>) ois.readObject());
            clientUserFindPass = new ConcurrentHashMap<>((Map<String, String>) ois.readObject());
            allData.close();

            Socket getUsers = new Socket(IP, PORT);
            ObjectOutputStream os = new ObjectOutputStream(getUsers.getOutputStream());
            ObjectInputStream is = new ObjectInputStream(getUsers.getInputStream());
            os.writeUTF("get users");
            os.flush();
            users= (ArrayList<user>) is.readObject();
            getUsers.close();

            Socket clientPosts = new Socket(IP, PORT);
            ObjectOutputStream ooss = new ObjectOutputStream(clientPosts.getOutputStream());
            ObjectInputStream oiss = new ObjectInputStream(clientPosts.getInputStream());
            ooss.writeUTF("initialize posts");
            ooss.flush();
            posts = (ArrayList<Post>) oiss.readObject();
            clientPosts.close();

            Socket getImages = new Socket(IP, PORT);
            ObjectOutputStream oos2 = new ObjectOutputStream(getImages.getOutputStream());
            ObjectInputStream ois2 = new ObjectInputStream(getImages.getInputStream());
            oos2.writeUTF("send profile images");
            oos2.flush();
            for (int i=0 ; i<users.size() ; i++) {
                String name=null;
                name = ois2.readUTF();

                InputStream inputStream = getImages.getInputStream();
                byte[] sizeAr = new byte[40000];
                inputStream.read(sizeAr);
                int size = ByteBuffer.wrap(sizeAr).asIntBuffer().get();

                byte[] imageAr = new byte[size];
                inputStream.read(imageAr);

                BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageAr));
                File f = new File("src\\CLIENTS\\images\\" + name);
                if (!f.exists())
                    ImageIO.write(image, "jpg", f);
            }
            getImages.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        PageLoader.initStage(primaryStage);
        new PageLoader().load("login");
    }


    public static void main(String[] args) throws IOException, ParseException {
        launch(args);
    }

}
