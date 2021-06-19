package CLIENTS.Modele;

import CLIENTS.Controller.cell;
import CLIENTS.Controller.cellController;
import CLIENTS.Controller.cm;
import common.Post;
import common.comment;
import common.user;
import javafx.application.Application;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Main extends Application {

    public static Map<String, String> clientsUserPass;
    public static Map<String, String> clientUserFindPass;
    public static ArrayList<Post> posts;
    public static String currentUser;
    public static ArrayList<user> users;

    static {
        try {
            Socket client;
            client = new Socket("localhost", 8000);
            ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
            oos.writeUTF("fileData");
            oos.flush();
            clientsUserPass = new ConcurrentHashMap<>((Map<String, String>) ois.readObject());
            clientUserFindPass = new ConcurrentHashMap<>((Map<String, String>) ois.readObject());

            Socket client2 = new Socket("localhost", 8000);
            ObjectOutputStream os = new ObjectOutputStream(client2.getOutputStream());
            ObjectInputStream is = new ObjectInputStream(client2.getInputStream());
            os.writeUTF("get users");
            os.flush();
            users= (ArrayList<user>) is.readObject();

            Socket clientPosts = new Socket("localhost", 8000);
            ObjectOutputStream ooss = new ObjectOutputStream(clientPosts.getOutputStream());
            ObjectInputStream oiss = new ObjectInputStream(clientPosts.getInputStream());
            ooss.writeUTF("initialize posts");
            ooss.flush();
            posts = (ArrayList<Post>) oiss.readObject();

            Socket clientt = new Socket("localhost", 8000);
            ObjectOutputStream oos2 = new ObjectOutputStream(clientt.getOutputStream());
            ObjectInputStream ois2 = new ObjectInputStream(clientt.getInputStream());
            oos2.writeUTF("send profile images");
            oos2.flush();
            //int num=ois2.readInt();
            for (int i=0 ; i<users.size() ; i++) {
                String name=null;
                synchronized (ois2) {
                    name = ois2.readUTF();
                }
                    InputStream inputStream = clientt.getInputStream();
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
