package utils;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang3.RandomStringUtils;
import org.w3c.dom.Document;

import com.aventstack.extentreports.Status;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Utilities {

	/**
	 * This method will load the xml file and return the Document object
	 * 
	 * @param xmlPath
	 * @return
	 * @throws Exception
	 * @throws IOException
	 */
	public static Document loadXML(String xmlPath) throws Exception, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(xmlPath);
		return document;
	}

	/**
	 * This method will return the current date and time based on specified format
	 * 
	 * @param format
	 * @return date and time based on specified format
	 */
	public static String getCurrentDateAndTime(String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		Date date = new Date();
		String formattedDate = formatter.format(date);
		return formattedDate;
	}

	/**
	 * Get today
	 * 
	 * @param format
	 * @return {@link String}
	 */
	public static String getTodayDate(String format) {
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(currentDate.getTime());
	}

	/**
	 * Get Last day of year
	 * 
	 * @param format
	 * @return {@link String}
	 */
	public static String getLastDayofYear() {
		return "12/31/" + Calendar.getInstance().get(Calendar.YEAR);
	}

	/**
	 * Obtain the yesterday date
	 * 
	 * @param format
	 * @return yesterday
	 */
	public static String getYesterday(String format) {
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		currentDate.add(Calendar.DATE, -1);
		return formatter.format(currentDate.getTime());
	}

	/**
	 * Get tomorrow
	 * 
	 * @param format
	 * @return {@link String} tomorrow date in formart
	 */
	public static String getTomorrow(String format) {
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		currentDate.add(Calendar.DATE, +1);
		return formatter.format(currentDate.getTime());
	}

	/**
	 * This method will return past date
	 * 
	 * @param format
	 * @param past
	 * @return String
	 */
	public static String getDateInPast(String format, int past) {
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		currentDate.add(Calendar.DATE, -past);
		return formatter.format(currentDate.getTime());
	}

	/**
	 * Get Random number as Day
	 * 
	 * @return {int} Random Number
	 */
	public static int getRandomDay() {
		Random randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(30);
		return randomInt;
	}

	/**
	 * Get Random number as month
	 * 
	 * @return {int} Random Number
	 */
	public static int getRandomMonth() {
		Random randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(12);
		return randomInt;
	}

	/**
	 * return a date ahead of specified days from current date
	 * 
	 * @param format
	 * @param noOfDays
	 * @return String
	 */
	public static String getFutureDate(String format, int noOfDays) {
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		currentDate.add(Calendar.DATE, +noOfDays);
		return formatter.format(currentDate.getTime());
	}

	/**
	 * This method will return the future date from current date
	 * 
	 * @param format
	 * @param noOfDays
	 * @param noOfMonths
	 * @param noOfYears
	 * @return String
	 */
	public static String getFutureDate(String format, int noOfDays, int noOfMonths, int noOfYears) {
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		currentDate.add(Calendar.DATE, +noOfDays);
		currentDate.add(Calendar.MONTH, +noOfMonths);
		currentDate.add(Calendar.YEAR, +noOfYears);
		return formatter.format(currentDate.getTime());
	}

	/**
	 * This method will return the current execution project
	 * 
	 * @return String
	 */
	public static String getCurrentExecutionProjectName() {
		String[] directories = System.getProperty("user.dir").split("/");
		int totalItems = directories.length;
		return directories[totalItems - 1];
	}

	/**
	 * This method will return a random value form the list provided.
	 * 
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public static int getRandomIdFromList(List<Integer> ids) throws Exception {
		if (ids.isEmpty()) {
			throw new Exception("The list is empty");
		}
		int id = ids.get(new Random().nextInt(ids.size()));
		return id;
	}

	/**
	 * This method will create a json body from the map provided and return the json
	 * body as a String
	 * 
	 * @param map
	 * @return
	 */
	public static String createJsonBodyFromMap(Map<Object, Object> map) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(map);
	}

	/**
	 * Added by imran.khan to convert Long date to formatted date
	 * 
	 * @param date
	 * @param format
	 * @return
	 * @throws ParseException
	 */
	public static String convertLongToFormattedDate(Long date, String format) throws ParseException {

		Date newEndDate = new Date(date);
		SimpleDateFormat df2 = new SimpleDateFormat(format);
		String dateText = df2.format(newEndDate);
		ExtentHelper.test.log(Status.INFO, "Formatted getTempAssignmentEndDate: " + dateText);
		return dateText;
	}

	/**
	 * This method will return today in milliseconds.
	 * 
	 * @param ids
	 * @return String
	 */
	public static String getTodayInMilliseconds() {
		return String.valueOf(new Date().getTime());
	}

	/**
	 * This method will return a random number between two number.
	 * 
	 * @param min
	 * @param max
	 * @return
	 */
	public static int getRandomNumberBetween(int min, int max) {
		Random rand = new Random();
		if (max == 0)
			return 0;
		return rand.nextInt(max - min) + min;
	}

	/**
	 * This method will return a date in different format.
	 * 
	 * @param format
	 * @return String
	 */
	public static String changeDateFormat(String myDate) throws ParseException {
		SimpleDateFormat postFormat = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat preFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date dt = preFormat.parse(myDate);
		return postFormat.format(dt);
	}

	/**
	 * This method will return the future date according to provided date
	 * 
	 * @param date
	 * @param i
	 * @return
	 * @throws ParseException
	 */
	public static String futureDtWithRespectToProvideDate(String date, int i) throws ParseException {
		String sDate = changeDateFormat(date);
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Calendar c = Calendar.getInstance();
		c.setTime(sdf.parse(sDate));
		c.add(Calendar.DATE, i); // number of days to add
		String finaldt = sdf.format(c.getTime()); // dt is now the new date
		return finaldt;
	}

	/***
	 * This method will convert milis to hh mm ss ms
	 * 
	 * @param millis
	 * @return String
	 */
	public static String convertMiliSecondsToHourMinSecMilliSec(long millis) {
		String hms = String.format("%02dh:%02dm:%02ds:%02dms", TimeUnit.MILLISECONDS.toHours(millis),
				TimeUnit.MILLISECONDS.toMinutes(millis)
						- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
				TimeUnit.MILLISECONDS.toSeconds(millis)
						- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)),
				TimeUnit.MILLISECONDS.toMillis(millis)
						- TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(millis)));
		return hms;
	}

	/**
	 * This method will convert a java object to a json string.
	 * 
	 * @param object
	 * @return gson
	 * @author german.massello
	 */
	public static String convertJavaObjectToJson(Object obj) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(obj);
	}

	/**
	 * This method will return random String of specified character length
	 * 
	 * @param stringLength
	 * @return
	 */
	public String gererateRandomString(int stringLength) {
		int leftLimit = 97; // letter 'a'
		int rightLimit = 122; // letter 'z'
		Random random = new Random();

		String generatedString = random.ints(leftLimit, rightLimit + 1).limit(stringLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

		return generatedString;
	}

	/**
	 * This method will return a random PhoneNumber
	 * 
	 * @return String
	 */
	public static String generateRandomPhoneNumber() {
		Random rand = new Random();
		int num1 = (rand.nextInt(7) + 1) * 100 + (rand.nextInt(8) * 10) + rand.nextInt(8);
		int num2 = rand.nextInt(743);
		int num3 = rand.nextInt(10000);

		DecimalFormat df3 = new DecimalFormat("000"); // 3 zeros
		DecimalFormat df4 = new DecimalFormat("0000"); // 4 zeros

		String phoneNumber = df3.format(num1) + df3.format(num2) + df4.format(num3);
		return phoneNumber;
	}
	
	/**
	 * This method will return a date with hh mm
	 * @param myDate
	 * @return String
	 * @author akshata.dongare
	 */
	public static String getDateWithTime(String myDate) throws ParseException {
		SimpleDateFormat preFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date dt = preFormat.parse(myDate);
		SimpleDateFormat postFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm");
		return postFormat.format(dt);
	}
	/***
	 * This method will return random alpha numeric strings
	 * @param length
	 * @return String
	 */
	public static String generateRandomAlphaNumericString(int length)
	{
		return RandomStringUtils.randomAlphanumeric(length);
	}
}
