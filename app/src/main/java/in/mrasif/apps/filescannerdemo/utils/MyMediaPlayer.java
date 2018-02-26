package in.mrasif.apps.filescannerdemo.utils;

import android.media.MediaPlayer;

/**
 * Created by asif on 27/2/18.
 */

public class MyMediaPlayer {
    public static MediaPlayer mp=null;
    public static MediaPlayer getMediaPlayer(){
        if(null==mp){
            mp=new MediaPlayer();
        }
        return mp;
    }
}
