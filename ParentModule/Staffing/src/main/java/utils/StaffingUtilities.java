package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang3.RandomStringUtils;

public class StaffingUtilities extends Utilities {

	/**
	 * It can be used to get current date or specified date with added time
	 * 
	 * @param dateFormat
	 * @param unitName : Calendar.MINUTE, Calendar.DAY, Calendar.HOUR 
	 * @param unitValue
	 * @param date
	 * @return {@link String}
	 * @author deepakkumar.hadiya
	 * @throws ParseException 
	 */
	public static String getFutureDateAndTimeForMentionedUnit(String dateFormat,int unitName, int unitValue, String... date) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		Calendar dateToProcess = Calendar.getInstance();
		if (date.length > 0) {
			dateToProcess.setTime(formatter.parse(date[0]));
		}
		dateToProcess.add(unitName, +unitValue);
		return formatter.format(dateToProcess.getTime());
	}

	/**
	 * Return a given date into millisecond format
	 * 
	 * @param date
	 * @param dateFormat
	 * @param timeZone
	 * @return {@link long}
	 * @author deepakkumar.hadiya
	 */
	public static long convertDateIntoMilliSecond(String date, String dateFormat, String... timeZone) {
		LocalDateTime localDateTime = LocalDateTime.parse(date,DateTimeFormatter.ofPattern(dateFormat));
		ZoneId zoneId = timeZone.length>0?ZoneId.of(timeZone[0]):ZonedDateTime.now().getZone();
		long millis = localDateTime.atZone(zoneId).toInstant().toEpochMilli();
		return millis;
	}

	/**
	 * Return a given millisecond formatted date into string format with specified
	 * time zone
	 * 
	 * @param dateInMilliSec
	 * @param dateFormat
	 * @return {@link String}
	 * @author deepakkumar.hadiya
	 */
	public static String convertMilliSecondIntoDateWithTimeZone(String dateInMilliSec, String dateFormat,
			String timeZone) {
		long date = Long.parseLong(dateInMilliSec);
		SimpleDateFormat simple = new SimpleDateFormat(dateFormat);
		TimeZone etTimeZone = TimeZone.getTimeZone(timeZone);
		Date result = new Date(date);
		simple.format(result);
		simple.setTimeZone(etTimeZone);
		return simple.format(result);
	}

	/**
	 * Returns the true if first date is after second date
	 * 
	 * @param date1
	 * @param date2
	 * @return {@link boolean}
	 * @author deepakkumar.hadiya
	 * @throws Exception 
	 */
	public static boolean firstDateIsAfterSecondDate(String dateFormat, String date1, String date2) throws Exception {
		dateFormat=!dateFormat.contains("SSS")?dateFormat.replace("ss", "ss.SSS"):dateFormat;
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		Date date01 = formatter.parse(addMilliSecondIntoDate(date1));
		Date date02 = formatter.parse(addMilliSecondIntoDate(date2));
		return date01.after(date02) || date01.compareTo(date02)==0;
	}
	
	/**
	 * Returns the true if given date is between two date
	 * 
	 * @param date1
	 * @param date2
	 * @return {@link boolean}
	 * @author deepakkumar.hadiya
	 * @throws Exception 
	 */
	public static boolean dateIsBetweenTwoDate(String dateFormat,String dateToCheck, String startDate, String endDate) throws Exception {
		return firstDateIsAfterSecondDate(dateFormat, dateToCheck, startDate) && firstDateIsAfterSecondDate(dateFormat, endDate, dateToCheck);
	}
	
	/** Method to append millisecond into date
	 * 
	 * @param date
	 * @return {@link String}
	 * @author deepakkumar.hadiya
	 * @throws  Exception
	 */
	private static String addMilliSecondIntoDate(String date) throws Exception {
		if(date!=null) {
			if(date.contains(".")) {
				String dateArray[]=date.split("\\.");
				date=date+RandomStringUtils.random(3-dateArray[1].length(),'0');
			}else {
				date=date+"."+RandomStringUtils.random(3,'0');
			}
		}else {
			throw new Exception("Given date is null");
		}
		return date;
	}
	
	/**
	 * Return a given millisecond formatted date into string format with specified
	 * time zone
	 * 
	 * @param dateInMilliSec
	 * @param dateFormat
	 * @return {@link String}
	 * @author deepakkumar.hadiya
	 */
	public static String getCurrentDateWithTimeZone(String dateFormat,
			String timeZone) {
		SimpleDateFormat simple = new SimpleDateFormat(dateFormat);
		TimeZone etTimeZone = TimeZone.getTimeZone(timeZone);
		Date result = new Date();
		simple.format(result);
		simple.setTimeZone(etTimeZone);
		return simple.format(result);
	}
	
	/**
	 * This method will get future date in date format.
	 * 
	 * @param no_of_days
	 * @return {@link Date}
	 * @throws ParseException 
	 */
	public static Date getFutureDateInDateFormat(int no_of_days) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		String futureDate = getFutureDateAndTimeForMentionedUnit("dd-MM-yyyy",Calendar.DATE, no_of_days + 1,Utilities.getTodayDate("dd-MM-yyyy"));
		Date futureDtInDateFormat = sdf.parse(futureDate);
		return futureDtInDateFormat;
	}
}
