package SERVER;

import common.Post;
import common.user;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    public static ServerSocket server;
    public static Map<String , String> map=new ConcurrentHashMap<>();
    public static Map<String , String> userQ=new ConcurrentHashMap<>();
    public static List<Post> postList=new ArrayList<>();
    public static ArrayList<user> users=new ArrayList<>();


    public Server() throws IOException {
        BufferedReader br=new BufferedReader(new FileReader("C:\\Users\\Atenaa\\IdeaProjects\\sbuGram\\src\\SERVER\\userPassServerSide.txt" ));
        String line;
        while ((line=br.readLine())!=null){
            String[] userPass=line.split("\t");
            if (userPass.length==4) {
                map.put(userPass[0], userPass[1]);
                userQ.put(userPass[0], userPass[3]);
                users.add(new user(userPass[0]));
            }
        }
        br=new BufferedReader(new FileReader("C:\\Users\\Atenaa\\IdeaProjects\\sbuGram\\src\\SERVER\\posts.txt" ));
        while ((line=br.readLine())!=null){
            String[] post=line.split("\t");
            if (post.length==5) {
                Post p=new Post();
                p.setAuthor(post[2]);
                p.setTitle(post[0]);
                p.setDescription(post[1]);
                p.setPostedBy(post[3]);
                p.setDate(post[4]);
                try {
                    p.setDate(new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(post[4]));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                postList.add(p);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        if (server!=null)
            server.close();
        server=new ServerSocket(8000);
        new Server();

        while (true){
            Socket client;
            try {
                client=server.accept();
                new ClientHandler(client).start();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
