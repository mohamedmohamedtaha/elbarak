package com.muslimcharityapps.elbarak.activity;
import android.graphics.Typeface;
import android.os.Bundle;
//import android.support.v7.app.ActionBarActivity;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.muslimcharityapps.elbarak.R;

public class Privacy extends AppCompatActivity {
	// Songs list
	TextView about, titre;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.privacy);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



		about = (TextView) findViewById(R.id.privacy_policy);
		titre = (TextView) findViewById(R.id.titre_content);


		// Font path
		String fontPath = "fonts/DROIDKUFI-REGULAR.TTF";
		String fontPath2 = "fonts/DROIDKUFI-BOLD.TTF";
		// Loading Font Face
		Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
		Typeface tf2 = Typeface.createFromAsset(getAssets(), fontPath2);
		// Applying font
		about.setTypeface(tf);
		titre.setTypeface(tf2);
		titre.setText(getString(R.string.msg_privacy_title));


		about.setText(getString(R.string.content_privacy));
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
