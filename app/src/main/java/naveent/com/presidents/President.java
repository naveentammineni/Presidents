package naveent.com.presidents;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by NaveenT on 9/10/14.
 */
public class President implements Serializable{
    private long id;
    private String name;
    private int image;
    private String bio;

    public President() {
    }

    public President(long id, String name, int image, String bio) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.bio = bio;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
