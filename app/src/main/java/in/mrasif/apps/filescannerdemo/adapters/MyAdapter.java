package in.mrasif.apps.filescannerdemo.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import in.mrasif.apps.filescannerdemo.R;
import in.mrasif.apps.filescannerdemo.utils.FileType;
import in.mrasif.apps.filescannerdemo.utils.MyMediaPlayer;

/**
 * Created by asif on 26/2/18.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    Context context;
    List<FileType> fileTypes;

    public MyAdapter(Context context, List<FileType> fileTypes) {
        this.context = context;
        this.fileTypes = fileTypes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.file_items,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final FileType fileType=fileTypes.get(position);
        holder.tvTitle.setText(fileType.getFile().getName());
        holder.tvPath.setText(fileType.getFile().getAbsolutePath());
        holder.llItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer mp= MyMediaPlayer.getMediaPlayer();
                if(mp.isPlaying()){
                    mp.release();
                    MyMediaPlayer.mp=null;
                    mp=MyMediaPlayer.getMediaPlayer();
                }

                try {
                    mp.setDataSource(fileType.getFile().getAbsolutePath());
                    mp.prepare();
                    mp.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return fileTypes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle,tvPath;
        LinearLayout llItem;
        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle=itemView.findViewById(R.id.tvTitle);
            tvPath=itemView.findViewById(R.id.tvPath);
            llItem=itemView.findViewById(R.id.llItem);
        }
    }
}
