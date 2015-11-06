package helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DataHelper {
	
	public static Calendar StringToCalendar(String formato, String data) {
		Date dataCovertida = null;
		SimpleDateFormat sdf = new SimpleDateFormat(formato);
		try {
			dataCovertida = sdf.parse(data);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(dataCovertida);
		return calendario;
	}
	
	public static String CalendarToString(String formato, Calendar calendario) {
		SimpleDateFormat sdf = new SimpleDateFormat(formato);
		return sdf.format(calendario.getTime());
	}
	
}
