package hu.todo.utility;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.view.Surface;
import android.view.WindowManager;

/**
 * Letiltja a forgatást és engedélyezi
 */
public class OrientationLocker {

	public static void lockScreenOrientation(Activity activity) {
		switch (((WindowManager) activity.getSystemService(Context.WINDOW_SERVICE))
		        .getDefaultDisplay().getRotation()) {
		    case Surface.ROTATION_90: 
		        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); 
		        break;
		    case Surface.ROTATION_180: 
		    	activity.setRequestedOrientation(9/* reversePortait */); 
		        break;          
		    case Surface.ROTATION_270: 
		    	activity.setRequestedOrientation(8/* reverseLandscape */); 
		        break;
		    default : 
		    	activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); 
		    }
	}
	 
	public static void unlockScreenOrientation(Activity activity) {
	    activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
	}
}
