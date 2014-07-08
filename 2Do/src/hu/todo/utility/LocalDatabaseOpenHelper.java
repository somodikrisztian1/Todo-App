package hu.todo.utility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class LocalDatabaseOpenHelper extends SQLiteOpenHelper
{
	private static final String TAG = LocalDatabaseOpenHelper.class.toString();
	private static final String DATABASE_NAME = "cyDB.db";
	private static final int DATABASE_VERSION = 1;
	private static final String PREF_NAME = "database";
	private static final String PREF_KEY = "version";
	
	private Context context;

	public LocalDatabaseOpenHelper(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
		
		// Ezzel létrejön az adatbázis, vagy megnyílik ha volt
		SQLiteDatabase database = context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
		database.close();
		
		// Ezzel meg megtudjuk hol van
		File databaseLocation = context.getDatabasePath(DATABASE_NAME);
				
		int currentVersion = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).getInt(PREF_KEY, -1);
		
		// Ha nincs még adatbázis, vagy régebbi verzió
		if (currentVersion == -1)
		{
			Log.i(TAG, "Adatbázis nem létezett, bemásolása az assets mappából.");
			// másoljuk át az assetsből a helyére
			
			createDatabase(databaseLocation);
			context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit().putInt(PREF_KEY, DATABASE_VERSION).commit();
		} else
		{
			Log.i(TAG, "Adatbázis létezik, és friss.");
		}
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		// direkt üres
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		// direkt üres
	}

	private void createDatabase(File location)
	{
		InputStream src = null;
		try
		{
			src = context.getAssets().open(DATABASE_NAME);
			copyFile(src, new FileOutputStream(location));
		} catch (IOException e)
		{
			Log.e(TAG, "I/O hiba!", e);
		}
	}

	private void copyFile(InputStream in, OutputStream out) throws IOException
	{
		byte[] buffer = new byte[1024];
		int read;
		while ((read = in.read(buffer)) != -1)
		{
			out.write(buffer, 0, read);
		}
	}
	
	@Override
	public SQLiteDatabase getReadableDatabase()
	{
		SQLiteDatabase db = null;
		synchronized (LocalDatabaseOpenHelper.class)
		{
			 db = super.getReadableDatabase();
		}
		return db;
	}

	
	@Override
	public SQLiteDatabase getWritableDatabase()
	{
		SQLiteDatabase db = null;
		synchronized (LocalDatabaseOpenHelper.class)
		{
			 db = super.getWritableDatabase();
		}
		return db;
	}
}