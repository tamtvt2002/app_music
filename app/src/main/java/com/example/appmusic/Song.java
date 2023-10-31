package com.example.appmusic;

import java.io.Serializable;

public class Song implements Serializable {
    private String Title, Author;
    private int FileMusic, FileAvatar, IDSong;

    public Song(String title, String author, int fileMusic, int fileAvatar, int IDSong) {
        Title = title;
        Author = author;
        FileMusic = fileMusic;
        FileAvatar = fileAvatar;
        this.IDSong = IDSong;
    }

    public int getIDSong() {
        return IDSong;
    }

    public String getTitle() {
        return Title;
    }

    public String getAuthor() {
        return Author;
    }

    public int getFileMusic() {
        return FileMusic;
    }

    public int getFileAvatar() {
        return FileAvatar;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public void setFileMusic(int fileMusic) {
        FileMusic = fileMusic;
    }

    public void setFileAvatar(int fileAvatar) {
        FileAvatar = fileAvatar;
    }
}

