package hu.todo.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CalendarFormatter {
	public static Calendar ISO8601(String str) {
		try {
			str = str.substring(0, 22) + str.substring(23);
			Calendar r = Calendar.getInstance();
			r.setTime(ISO8601.parse(str));
			return r;
		} catch (ParseException pe) {
			pe.printStackTrace();
			return null;
		} catch (StringIndexOutOfBoundsException se) {
			se.printStackTrace();
			return null;
		}
	}

	public static String ISO8601(Calendar c) {
		String s = ISO8601.format(c.getTime());
		return (s.substring(0, 22) + ":" + s.substring(22));
	}

	public static final SimpleDateFormat ISO8601 = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault());

}
