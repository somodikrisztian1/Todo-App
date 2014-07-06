package hu.todo.function;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class SystemFunctions
{
	
	private SystemFunctions(){};
	
	
	public static boolean isOnline(Context context)
	{ // megnézi hogy most van-e kapcsolat
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnected()) // netInfo.isConnectedOrConnecting()
		{
			return true;
		}
		return false;
	}
	
}