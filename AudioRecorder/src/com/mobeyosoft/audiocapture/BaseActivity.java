
package com.mobeyosoft.audiocapture;

import android.net.Uri;
import android.support.v4.app.FragmentActivity;

public class BaseActivity extends FragmentActivity {

	protected Uri mUri;

	@Override
	protected void onPause() {
		super.onPause();

		// when done playing, release the resources
		AudioWife.getInstance().release();
		mUri = null;
	}
}
