package CLIENTS.Modele;

import common.Post;
import common.user;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class Client {
    String username , password , name , ques , path;
    Socket client;
    ObjectOutputStream oos;
    ObjectInputStream ois;

    public Client(String username, String password,String name , String ques , String path) throws IOException, ClassNotFoundException {
        this.username = username;
        this.password = password;
        this.name=name;
        this.ques=ques;
        this.path=path;
        Main.currentUser=username;
        client=new Socket("localhost" , 8000);
        oos=new ObjectOutputStream(client.getOutputStream());
        ois=new ObjectInputStream(client.getInputStream());
        oos.writeUTF("signup");
        oos.flush();
        oos.writeUTF(username + "\t"+password +"\t"+name+"\t"+ques+"\t" +path);
        oos.flush();
        OutputStream os= client.getOutputStream();
        BufferedImage image= ImageIO.read(new File(path));

        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        ImageIO.write(image , "jpg" , baos);

        byte[] size = ByteBuffer.allocate(40000).putInt(baos.size()).array();
        os.write(size);
        os.write(baos.toByteArray());
        os.flush();
        System.out.println("im connected");
        Socket getUsers;
        getUsers = new Socket("localhost", 8000);
        ObjectOutputStream oos = new ObjectOutputStream(getUsers.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(getUsers.getInputStream());
        oos.writeUTF("get users");
        oos.flush();
        try {
            Main.users= (ArrayList<user>) ois.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        getUsers.close();
    }
    public Client(String username, String password) throws IOException {
        this.username = username;
        this.password = password;
        Main.currentUser=username;
        client=new Socket("localhost" , 8000);
        oos=new ObjectOutputStream(client.getOutputStream());
        ois=new ObjectInputStream(client.getInputStream());
        oos.writeUTF("login");
        oos.flush();
        oos.writeUTF(username + "\t"+password );
        oos.flush();
        System.out.println("im connected");
    }
}
