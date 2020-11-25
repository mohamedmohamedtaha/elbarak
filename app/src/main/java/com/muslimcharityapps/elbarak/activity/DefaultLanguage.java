package com.muslimcharityapps.elbarak.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.muslimcharityapps.elbarak.R;
import com.muslimcharityapps.elbarak.utils.Values;

import java.util.Locale;

/**
 * Created by ssb on 13/3/17.
 */

public class DefaultLanguage extends AppCompatActivity implements Values {

    private ListView lvLanguage;
    private String[] supportedLanguage;
    private String[] supportedLanguageTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_default_language);

        supportedLanguage = getResources().getStringArray(R.array.array_supported_language);
        supportedLanguageTitle = getResources().getStringArray(R.array.array_supported_language_title);

        /*
        LANGUAGE LIST
         */

        lvLanguage = (ListView) findViewById(R.id.lvLanguage);
        lvLanguage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setApplicationLocale(supportedLanguage[position]);
                setResult(99);
                DefaultLanguage.this.finish();
            }
        });
        lvLanguage.setAdapter(new LanguageAdapter());
    }

    private class LanguageAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return supportedLanguageTitle.length;
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
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.row_language, parent, false);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.tvLanguage.setText(supportedLanguageTitle[position]);

            return convertView;
        }
    }

    private class ViewHolder {
        private TextView tvLanguage;

        public ViewHolder(View view) {
            tvLanguage = (TextView) view.findViewById(R.id.tvLanguage);
        }
    }

    private void setApplicationLocale(String locale) {
        Resources resources = getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = new Locale(locale.toLowerCase());
        resources.updateConfiguration(configuration, displayMetrics);

        SharedPreferences sharedpreferences = getSharedPreferences(SP_SONG_STATE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("language", locale);
        editor.commit();
    }
}
