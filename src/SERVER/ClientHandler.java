package SERVER;

import common.Post;
import common.comment;
import common.user;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.util.ArrayList;

public class ClientHandler extends Thread {
    Socket client;
    ObjectInputStream ois;
    ObjectOutputStream oos;
    ArrayList<Post> posts;

    public ClientHandler(Socket client) {
        this.client = client;
        try {
            oos = new ObjectOutputStream(client.getOutputStream());
            ois = new ObjectInputStream(client.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            String action = ois.readUTF();
            switch (action) {
                case "fileData" -> {
                    oos.writeObject(Server.map);
                    oos.flush();
                    oos.writeObject(Server.userQ);
                    oos.flush();
                }
                case "signup" -> {
                    String[] userPass = ois.readUTF().split("\t");
                    String username = null;
                    String pass = null;
                    String name = null;
                    String ques = null;
                    String imgPath=null;
                    if (userPass.length == 5) {
                        username = userPass[0];
                        pass = userPass[1];
                        name = userPass[2];
                        ques = userPass[3];
                        imgPath=userPass[4];
                        Server.map.put(username, pass);
                        Server.userQ.put(username, ques);
                        BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\Users\\Atenaa\\IdeaProjects\\sbuGram\\src\\SERVER\\userPassServerSide.txt", true));
                        bw.write(username + "\t" + pass + "\t" + name + "\t" + ques);
                        bw.flush();
                        bw.newLine();
                        bw.flush();
                    }
                    InputStream inputStream=client.getInputStream();
                    byte[] sizeAr = new byte[40000];
                    inputStream.read(sizeAr);
                    int size = ByteBuffer.wrap(sizeAr).asIntBuffer().get();

                    byte[] imageAr = new byte[size];
                    inputStream.read(imageAr);

                    BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageAr));
                    File f=new File("src\\SERVER\\profileImagesServer\\"+username+".jpg");
                    ImageIO.write(image, "jpg",f);
                    Server.users.add(new user(username));

                    System.out.println(username + " registered."+"\t"+imgPath);
                    java.util.Date date = new java.util.Date();
                    System.out.println("time: " + date);
                    System.out.println();
                }
                case "login" -> {
                    String[] userPass = null;
                    String username = null;
                    String pass = null;
                    try {
                        userPass = ois.readUTF().split("\t");
                        if (userPass.length == 2) {
                            username = userPass[0];
                            pass = userPass[1];
                        }
                        System.out.println("action: login");
                        System.out.println(username + " login.");
                        java.util.Date date = new java.util.Date();
                        System.out.println("time: " + date);
                        System.out.println();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                case "initialize posts" -> {
                    oos.writeObject(Server.postList);
                    oos.flush();
                }
                case "add post" -> {
                    try {
                        Post newPost= (Post) ois.readObject();
                        Server.postList.add(newPost);
                        BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\Users\\Atenaa\\IdeaProjects\\sbuGram\\src\\SERVER\\posts.txt", true));
                        bw.write(newPost.getTitle() + "\t" + newPost.getDescription() + "\t" + newPost.getAuthor() + "\t" + newPost.getPostedBy() +"\t"+ newPost.getDate());
                        bw.flush();
                        bw.newLine();
                        bw.flush();
                        oos.writeObject(Server.postList);
                        oos.flush();
                        bw.close();

                        System.out.println(newPost.getAuthor()+" publish");
                        System.out.println("message:" + newPost.getTitle()+" "+newPost.getPostedBy());
                        java.util.Date date = new java.util.Date();
                        System.out.println("time: " + date);
                        System.out.println();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                case "get users" -> {
                    oos.writeObject(Server.users);
                    oos.flush();
                }
                case "like" -> {
                    try {
                        Post post= (Post) ois.readObject();
                        String username=ois.readUTF();
                        for (Post p:Server.postList){
                            if (post.getTitle().equals(p.getTitle()) && post.getPostedBy().equals(p.getPostedBy())){
                                p.like(username);
                            }
                        }
                        oos.writeObject(Server.postList);
                        oos.flush();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                }
                case "add cm" -> {
                    try {
                        comment cm= (comment) ois.readObject();
                        Post post= (Post) ois.readObject();
                        for (Post p:Server.postList){
                            if (post.equals(p))
                                p.addCm(cm);
                        }
                        oos.writeObject(Server.postList);
                        oos.flush();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                case "follow" -> {
                    try {
                        user user= (common.user) ois.readObject();
                        String gotFollowed=ois.readUTF();
                        for (user us:Server.users){
                            if (us.getUsername().equals(user.getUsername())){
                                us.follow(gotFollowed);
                            }
                        }
                        oos.writeObject(Server.users);
                        oos.flush();

                        System.out.println("action: follow");
                        System.out.println(user.getUsername()+" action");
                        System.out.println(gotFollowed);
                        java.util.Date date = new java.util.Date();
                        System.out.println("time: " + date);
                        System.out.println();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                case "unfollow" -> {
                    try {
                        user user= (common.user) ois.readObject();
                        String gotUnFollowed=ois.readUTF();
                        for (user us:Server.users){
                            if (us.getUsername().equals(user.getUsername())){
                                us.unfollow(gotUnFollowed);
                            }
                        }
                        oos.writeObject(Server.users);
                        oos.flush();

                        System.out.println("action: unfollow");
                        System.out.println(user.getUsername()+" action");
                        System.out.println(gotUnFollowed);
                        java.util.Date date = new java.util.Date();
                        System.out.println("time: " + date);
                        System.out.println();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                case "set info" -> {
                    try {
                        user user= (common.user) ois.readObject();
                        String info=ois.readUTF();
                        for (user us:Server.users){
                            if (us.getUsername().equals(user.getUsername())){
                                us.setInfo(info);
                            }
                        }
                        oos.writeObject(Server.users);
                        oos.flush();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                case "send profile images" -> {
                    int num=0;
                    File file1=new File("src\\SERVER\\profileImagesServer");
                    num= file1.listFiles().length;
                    File file2=new File("src\\SERVER\\profileImagesServer");
                    for (File f: file2.listFiles()){
                        if (f.isFile() && f.getName().endsWith(".jpg"))
                            oos.writeUTF(f.getName());
                        oos.flush();

                        OutputStream os = client.getOutputStream();
                        BufferedImage image = ImageIO.read(f);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        ImageIO.write(image, "jpg", baos);
                        byte[] size = ByteBuffer.allocate(40000).putInt(baos.size()).array();
                        os.write(size);
                        os.write(baos.toByteArray());
                        os.flush();
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
