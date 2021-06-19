package CLIENTS.Modele;

import common.Post;

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

    public Client(String username, String password,String name , String ques , String path) throws IOException {
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
        ImageIO.write(image , "jpg" , new File("src\\CLIENTS\\images\\"+username+".jpg"));
        byte[] size = ByteBuffer.allocate(40000).putInt(baos.size()).array();
        os.write(size);
        os.write(baos.toByteArray());
        os.flush();
        System.out.println("im connected");
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
