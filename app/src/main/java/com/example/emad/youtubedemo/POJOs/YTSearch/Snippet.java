
package com.example.emad.youtubedemo.POJOs.YTSearch;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Snippet {

    @SerializedName("publishedAt")
    @Expose
    private String publishedAt;
    @SerializedName("channelId")
    @Expose
    private String channelId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("thumbnails")
    @Expose
    private Thumbnails thumbnails;
    @SerializedName("channelTitle")
    @Expose
    private String channelTitle;
    @SerializedName("liveBroadcastContent")
    @Expose
    private String liveBroadcastContent;

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Thumbnails getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(Thumbnails thumbnails) {
        this.thumbnails = thumbnails;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public void setChannelTitle(String channelTitle) {
        this.channelTitle = channelTitle;
    }

    public String getLiveBroadcastContent() {
        return liveBroadcastContent;
    }

    public void setLiveBroadcastContent(String liveBroadcastContent) {
        this.liveBroadcastContent = liveBroadcastContent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Snippet snippet = (Snippet) o;

        if (publishedAt != null ? !publishedAt.equals(snippet.publishedAt) : snippet.publishedAt != null)
            return false;
        if (channelId != null ? !channelId.equals(snippet.channelId) : snippet.channelId != null)
            return false;
        if (title != null ? !title.equals(snippet.title) : snippet.title != null) return false;
        if (description != null ? !description.equals(snippet.description) : snippet.description != null)
            return false;
        if (thumbnails != null ? !thumbnails.equals(snippet.thumbnails) : snippet.thumbnails != null)
            return false;
        if (channelTitle != null ? !channelTitle.equals(snippet.channelTitle) : snippet.channelTitle != null)
            return false;
        return liveBroadcastContent != null ? liveBroadcastContent.equals(snippet.liveBroadcastContent) : snippet.liveBroadcastContent == null;
    }

    @Override
    public int hashCode() {
        int result = publishedAt != null ? publishedAt.hashCode() : 0;
        result = 31 * result + (channelId != null ? channelId.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (thumbnails != null ? thumbnails.hashCode() : 0);
        result = 31 * result + (channelTitle != null ? channelTitle.hashCode() : 0);
        result = 31 * result + (liveBroadcastContent != null ? liveBroadcastContent.hashCode() : 0);
        return result;
    }
}
