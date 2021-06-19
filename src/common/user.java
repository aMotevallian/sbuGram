package common;


import CLIENTS.Modele.Main;
import SERVER.Server;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class user implements Serializable {
    @Serial
    private static final long serialVersionUID = 8427276279870582913L;
    String username;
    String info;

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfo() {
        if (info==null)
            return "";
        return info;
    }

    public int followers , followings;
     ArrayList<String> followerList=new ArrayList<>();
     ArrayList<String> followingList=new ArrayList<>();

    public int getFollowers() {
        return this.followers;
    }

    public int getFollowings() {
        return this.followings;
    }

    public ArrayList<String> getFollowerList() {
        return followerList;
    }

    public ArrayList<String> getFollowingList() {
        return followingList;
    }

    public user(String username) {
        this.username = username;
    }
    public void follow(String username){
        followingList.add(username);
        followings++;
        for (user user: Server.users){
            if (user.getUsername().equals(username)) {
                user.followerList.add(this.getUsername());
                user.followers++;
            }
        }
    }

    public String getUsername() {
        return username;
    }

    public void unfollow(String username){
        followingList.remove(username);
        followings--;
        for (user user: Main.users){
            if (user.getUsername().equals(username)) {
                user.followerList.remove(this.getUsername());
                user.followers--;
            }
        }
    }
}
