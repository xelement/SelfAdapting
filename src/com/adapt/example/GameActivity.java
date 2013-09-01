package com.adapt.example;

import com.adapt.library.SelfAdapting;
import com.adapt.screensize.R;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class GameActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SelfAdapting.init(this,SelfAdapting.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(new MainMenuView(this));
		System.out.println("SelfAdapting.widthPixels = "+SelfAdapting.widthPixels);
		System.out.println("SelfAdapting.heightPixels = "+SelfAdapting.heightPixels);
		System.out.println("SelfAdapting.screenOffsetX = "+SelfAdapting.screenOffsetX);
		System.out.println("SelfAdapting.screenOffsetY = "+SelfAdapting.screenOffsetY);
		System.out.println("SelfAdapting.actualScale = "+SelfAdapting.actualScale);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
