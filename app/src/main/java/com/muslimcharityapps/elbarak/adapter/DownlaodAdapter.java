package com.muslimcharityapps.elbarak.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.muslimcharityapps.elbarak.R;
import com.muslimcharityapps.elbarak.activity.Dashboard;
import com.muslimcharityapps.elbarak.activity.PlayListActivity;
import com.muslimcharityapps.elbarak.activity.PlayListDownloaded;
import com.muslimcharityapps.elbarak.activity.PlayListFavorite;
import com.muslimcharityapps.elbarak.model.SongBean;
import com.muslimcharityapps.elbarak.rxdownload.DownloadableItem;

import java.io.File;
import java.util.ArrayList;

import static com.muslimcharityapps.elbarak.utils.Values.KEY_SONG_PATH;
import static com.muslimcharityapps.elbarak.utils.Values.KEY_SONG_TITLE;
import static com.muslimcharityapps.elbarak.utils.Values.MEDIA_PATH;
import static com.muslimcharityapps.elbarak.utils.Values.SP_SONG_INDEX;

public class DownlaodAdapter extends BaseAdapter {
    private ArrayList<SongBean> songsList = new ArrayList<>();
    private ArrayList<DownloadableItem> downloadableItems = new ArrayList<>();
    private boolean downlaodOrFavorite = false;

    private Context context;
    private NiftyDialogBuilder dialogBuilder;

    public DownlaodAdapter(ArrayList<SongBean> songsList, Context context) {
        this.songsList = songsList;
        this.context = context;
    }

    public DownlaodAdapter(ArrayList<DownloadableItem> downloadableItems, Context context, boolean downlaodOrFavorite) {
        this.downloadableItems = downloadableItems;
        this.context = context;
        this.downlaodOrFavorite = downlaodOrFavorite;
    }

    @Override
    public int getCount() {
        if (downlaodOrFavorite) {
            return downloadableItems.size();

        } else {
            return songsList.size();
        }
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        DownloadViewHolder viewHolder = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.playlist_item, parent, false);
            viewHolder = new DownloadViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (DownloadViewHolder) convertView.getTag();
        }

            /*
            CHECK FOR MULTI DELETE
             */

        if (songsList.get(position).isMultiDeleteEnabled()) {
            viewHolder.cbDelete.setVisibility(View.VISIBLE);
            viewHolder.imageView.setVisibility(View.GONE);
        } else {
            viewHolder.cbDelete.setVisibility(View.GONE);
            viewHolder.imageView.setVisibility(View.VISIBLE);
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   Toast.makeText(context, "No", Toast.LENGTH_SHORT).show();
//                try {
//                    int songIndex = 0;
//                  //  songIndex = Integer.parseInt(songsList.get(position).getPosition());
//
//            if (songsList.get(position).getSongIndex().contains("_")) {
//                songIndex = Integer.parseInt(songsList.get(position).getSongIndex().split("_")[1]) - 1;
//            } else {
//                songIndex = Integer.parseInt(songsList.get(position).getSongIndex());
//            }
//                    // Starting new intent
//                 //   Toast.makeText(context, "" + songIndex, Toast.LENGTH_SHORT).show();
//                    Intent in = new Intent(context, Dashboard.class);
//                    in.putExtra(SP_SONG_INDEX, songIndex);
//                    ((PlayListFavorite) context).setResult(100, in);
//                    ((PlayListFavorite) context).finish();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    Log.d("TAG", e.getMessage());
//                }

                try {
                    File home = new File(MEDIA_PATH);
                    final File file_xStore = new File(home,songsList.get(position).getSongTitle() + ".mp3");
                    Log.d("TAG", "file_xStore " + file_xStore);
                    int songIndex = 0;
                    //Integer.parseInt(songsList.get(position).getPosition());

                    if (songsList.get(position).getSongIndex().contains("_")) {
                        songsList.get(position).getPosition();
                        songIndex = Integer.parseInt(songsList.get(position).getSongIndex().split("_")[1]) - 1;
                        songsList.get(position).getSongPath();
                        Log.d("TAG", "songIndex " +songIndex);
                        Log.d("TAG", "getSongPath " +songsList.get(position).getSongPath());
                        Log.d("TAG", "songsList.get(position).getPosition() " +songsList.get(position).getPosition());
                    } else {
                        //   songIndex = Integer.parseInt(songsList.get(position).getSongIndex());
                        songsList.get(position).getSongPath();
                        songsList.get(position).getSongTitle();
                        Log.d("TAG", "songIndex two " +songIndex);
                        Log.d("TAG", "position two " +songsList.get(position).getPosition());

                        Log.d("TAG", "getSongPath two " +songsList.get(position).getSongPath());
                    }
                    Intent in = new Intent(context, Dashboard.class);
                     in.putExtra(SP_SONG_INDEX, songIndex);
                    in.putExtra(KEY_SONG_PATH,songsList.get(position).getSongPath());
                    // in.putExtra(KEY_SONG_POSITION, songsList.get(position).toString());
                    in.putExtra(KEY_SONG_TITLE,songsList.get(position).getSongTitle());
                    ((PlayListDownloaded) context).setResult(100, in);
                    ((PlayListDownloaded) context).finish();

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("TAG", e.getMessage());
                }
            }
        });
            /*
            DELETE CHECK BOX
             */

        viewHolder.cbDelete.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SongBean songBean = songsList.get(position);
                songBean.setToDelete(isChecked);
                songsList.set(position, songBean);
            }
        });

        if (songsList.get(position).isChecked()) {
            viewHolder.cbDelete.setChecked(true);
        } else {
            viewHolder.cbDelete.setChecked(false);
        }

            /*
            SETTING TITLE
             */

        viewHolder.title.setText(songsList.get(position).getSongTitle());

            /*
            DELETING SINGLE SONG
             */
        final DownloadViewHolder finalViewHolder = viewHolder;
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBuilder = NiftyDialogBuilder.getInstance(context);
                dialogBuilder
                        .isCancelableOnTouchOutside(false).withDuration(800)
                        .withTitle("" + finalViewHolder.title.getText().toString())
                        .withMessage(context.getString(R.string.msg_delete_song))
                        .withEffect(Effectstype.Fliph)
                        .withButton1Text(context.getString(R.string.lbl_yes))
                        .withButton2Text(context.getString(R.string.lbl_no))
                        .setButton1Click(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialogBuilder.dismiss();
                                dialogBuilder.cancel();
                                deleteSong(position, false);
                            }
                        }).setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogBuilder.dismiss();
                    }
                }).show();


            }
        });

        return convertView;
    }


    public class DownloadViewHolder {
        private ImageButton imageView;
        private TextView title;
        private CheckBox cbDelete;

        public DownloadViewHolder(View view) {
            imageView = (ImageButton) view.findViewById(R.id.imdelete);
            title = (TextView) view.findViewById(R.id.songTitle);
            cbDelete = (CheckBox) view.findViewById(R.id.cbDelete);
        }
    }

    private void deleteSong(int position, boolean isMultipleDelete) {
        File dir = new File(Environment.getExternalStorageDirectory() + "/AndroiSaJa/ElbarakSaJa/");
        File file = new File(Environment.getExternalStorageDirectory() + "/AndroiSaJa/ElbarakSaJa/" + songsList.get(position).getSongIndex() + ".mp3");
        boolean deleted = file.delete();

        if (deleted) {
            songsList.remove(position);
            if (!isMultipleDelete) {
                notifyDataSetChanged();
                Toast.makeText(context, context.getString(R.string.msg_song_deleted), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, context.getString(R.string.msg_delete_failed), Toast.LENGTH_SHORT).show();
        }
    }

}