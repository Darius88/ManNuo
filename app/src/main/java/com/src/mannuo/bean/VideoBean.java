package com.src.mannuo.bean;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by 飞 on 2015/9/12.
 * 视频实体类
 */
public class VideoBean implements Parcelable {


        private static final long serialVersionUID = -7920222595800367956L;
        private int id;
        private String title;
        private String album;
        private String artist;
        private String displayName;
        private String mimeType;
        private String path;
        private long size;
        private long duration;
        private Bitmap image;

        private boolean  isSelected;

    public VideoBean(){

    }

    public VideoBean(int id, String title, String album, String artist,String displayName, String mimeType, String path, long size,
                          long duration,boolean isSelected) {
                super();
                this.id = id;
                this.title = title;
                this.album = album;
                this.artist = artist;
                this.displayName = displayName;
                this.mimeType = mimeType;
                this.path = path;
                this.size = size;
                this.duration = duration;
                this.isSelected=isSelected;
           }


    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public String toString() {
        return "VideoBean{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", album='" + album + '\'' +
                ", artist='" + artist + '\'' +
                ", displayName='" + displayName + '\'' +
                ", mimeType='" + mimeType + '\'' +
                ", path='" + path + '\'' +
                ", size=" + size +
                ", duration=" + duration +
                ", image=" + image +
                ", isSelected=" + isSelected +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.album);
        dest.writeString(this.artist);
        dest.writeString(this.displayName);
        dest.writeString(this.mimeType);
        dest.writeString(this.path);
        dest.writeLong(this.size);
        dest.writeLong(this.duration);
        dest.writeParcelable(this.image, flags);
        dest.writeByte(this.isSelected ? (byte) 1 : (byte) 0);
    }

    protected VideoBean(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.album = in.readString();
        this.artist = in.readString();
        this.displayName = in.readString();
        this.mimeType = in.readString();
        this.path = in.readString();
        this.size = in.readLong();
        this.duration = in.readLong();
        this.image = in.readParcelable(Bitmap.class.getClassLoader());
        this.isSelected = in.readByte() != 0;
    }

    public static final Creator<VideoBean> CREATOR = new Creator<VideoBean>() {
        @Override
        public VideoBean createFromParcel(Parcel source) {
            return new VideoBean(source);
        }

        @Override
        public VideoBean[] newArray(int size) {
            return new VideoBean[size];
        }
    };
}
