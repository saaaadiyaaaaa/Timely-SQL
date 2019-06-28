package com.example.sqltodo.JSON;

public class YouTubeVideos {
    String videoUrl;
    String title;

    public YouTubeVideos() {
    }

    public YouTubeVideos(String videoUrl, String title) {
        this.videoUrl = videoUrl;
        this.title = title;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
