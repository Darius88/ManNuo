package com.src.mannuo.utils;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.src.mannuo.bean.VideoBean;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhangmeng on 2018/12/19.
 */

public class SearchFileUtil {



    /**
     * @Description: String类型毫秒数转换成日期
     * [@param](http://my.oschina.net/param) lo 毫秒数
     * @return String yyyy-MM-dd HH:mm:ss
     */
    public static String TimeToDate(long time){
        Date date = new Date(time);
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sd.format(date);
    }




    public static String getNetFileSizeDescription(long size) {
        StringBuffer bytes = new StringBuffer();
        DecimalFormat format = new DecimalFormat("###.0");
        if (size >= 1024 * 1024 * 1024) {
            double i = (size / (1024.0 * 1024.0 * 1024.0));
            bytes.append(format.format(i)).append("GB");
        }
        else if (size >= 1024 * 1024) {
            double i = (size / (1024.0 * 1024.0));
            bytes.append(format.format(i)).append("MB");
        }
        else if (size >= 1024) {
            double i = (size / (1024.0));
            bytes.append(format.format(i)).append("KB");
        }
        else if (size < 1024) {
            if (size <= 0) {
                bytes.append("0B");
            }
            else {
                bytes.append((int) size).append("B");
            }
        }
        return bytes.toString();
    }





    public static ArrayList<VideoBean> getList(Context context, int height) {
        ArrayList<VideoBean> list = null;
        if (context != null) {
            Cursor cursor = context.getContentResolver().query(
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null,
                    null, null);
            if (cursor != null) {
                list = new ArrayList<>();
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(cursor
                            .getColumnIndexOrThrow(MediaStore.Video.Media._ID));
                    String title = cursor
                            .getString(cursor
                                    .getColumnIndexOrThrow(MediaStore.Video.Media.TITLE));
                    String album = cursor
                            .getString(cursor
                                    .getColumnIndexOrThrow(MediaStore.Video.Media.ALBUM));
                    String artist = cursor
                            .getString(cursor
                                    .getColumnIndexOrThrow(MediaStore.Video.Media.ARTIST));
                    String displayName = cursor
                            .getString(cursor
                                    .getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME));
                    String mimeType = cursor
                            .getString(cursor
                                    .getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE));
                    String path = cursor
                            .getString(cursor
                                    .getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
                    long duration = cursor
                            .getInt(cursor
                                    .getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));
                    long size = cursor
                            .getLong(cursor
                                    .getColumnIndexOrThrow(MediaStore.Video.Media.SIZE));
                    VideoBean video = new VideoBean(id, title, album, artist, displayName, mimeType, path, size, duration,false);
                    Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(path, MediaStore.Images.Thumbnails.MINI_KIND);
                    if (bitmap != null) {
                        bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() * height / bitmap.getHeight(), height, true);
                        video.setImage(bitmap);
                    }
                    list.add(video);
                }
                cursor.close();
            }
        }
        return list;
    }



}
