package com.muslimcharityapps.elbarak.activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
//import android.support.v7.app.ActionBarActivity;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.muslimcharityapps.elbarak.R;

public class About extends AppCompatActivity {
	// Songs list
	TextView about, titre;
	ImageButton btnShare;
	private ImageButton btnRate;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);

		if (getSupportActionBar() != null) {

			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
			getSupportActionBar().setDisplayShowHomeEnabled(true);
		}



		about = (TextView) findViewById(R.id.txta_about);
		titre = (TextView) findViewById(R.id.titre_content);
		btnShare = (ImageButton) findViewById(R.id.btnShare);
		btnRate = (ImageButton) findViewById(R.id.btnRate);

		// Font path
		String fontPath = "fonts/DROIDKUFI-REGULAR.TTF";
		String fontPath2 = "fonts/DROIDKUFI-BOLD.TTF";
		// Loading Font Face
		Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
		Typeface tf2 = Typeface.createFromAsset(getAssets(), fontPath2);
		// Applying font
		about.setTypeface(tf);
		titre.setTypeface(tf2);
		titre.setText(getString(R.string.lbl_about_title_content));
		btnRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                About.this.startActivity(new Intent(
                        "android.intent.action.VIEW",
                        Uri.parse("https://play.google.com/store/apps/details?id=com.muslimcharityapps.elbarak")));
            }
        });
		btnShare.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_SEND);
				intent.setType("text/plain");
				intent.putExtra(
						Intent.EXTRA_TEXT,
						getString(R.string.share_phrase)
								+ "https://play.google.com/store/apps/details?id=com.muslimcharityapps.elbarak");
				startActivity(Intent.createChooser(intent, "Share"));
			}
		});

		about.setText(getString(R.string.abt_description));
	}

		@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				this.finish();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
