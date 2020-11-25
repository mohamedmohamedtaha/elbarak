package com.muslimcharityapps.elbarak.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import com.muslimcharityapps.elbarak.R;
import com.muslimcharityapps.elbarak.activity.Dashboard;
import com.muslimcharityapps.elbarak.activity.PlayListActivity;
import com.muslimcharityapps.elbarak.activity.PlayListFavorite;
import com.muslimcharityapps.elbarak.rxdownload.DownloadableItem;

import java.util.ArrayList;

import static com.muslimcharityapps.elbarak.utils.Values.SP_SONG_INDEX;

public class CustomAdapter extends BaseAdapter{
    private final String ADS = "ads";

    private ArrayList<DownloadableItem> downloadableItemList;
    private Context context;

    public CustomAdapter(ArrayList<DownloadableItem> downloadableItemList, Context context) {
        this.downloadableItemList = downloadableItemList;
        this.context = context;
    }

    @Override
        public int getCount() {
            return downloadableItemList.size();
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
            ViewHolder viewHolder = null;

            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.playlist_item, parent, false);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            /*
            SETTING TITLE
             */

            viewHolder.title.setText(downloadableItemList.get(position).getItemTitle());

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int songIndex = Integer.valueOf(downloadableItemList.get(position).getPosition());

                    if (((PlayListFavorite) context).getAd().equalsIgnoreCase("0")) {
                        if (((PlayListFavorite) context).getInterstitial().isLoaded()) {
                            ((PlayListFavorite) context).getInterstitial().show();
                            ((PlayListFavorite) context).getEditor().putString(ADS, "1");
                            ((PlayListFavorite) context).getEditor().commit();
                        }
                    }

                    Intent in = new Intent(context, Dashboard.class);
                    in.putExtra(SP_SONG_INDEX, songIndex);
                    //   in.putExtra(SP_SONG_INDEX, downloadableItemList.get(songIndex).getItemTitle());
                    //      in.putExtra(INTENT_DATA, ((PlayListFavorite) context).getSongsListData());

                    // in.putExtra("songIndex",songIndex);
                    // setResult(105, in);
                    ((PlayListFavorite) context).setResult(100, in);
                    ((PlayListFavorite) context).finish();

                }
            });



            return convertView;
        }

    public class ViewHolder {
        private TextView title;
        private CheckBox cbDelete;

        public ViewHolder(View view) {
            title = (TextView) view.findViewById(R.id.songTitle);
            cbDelete = (CheckBox) view.findViewById(R.id.cbDelete);
            cbDelete.setVisibility(View.GONE);

        }
    }
}