package com.bootcamp.portal.utils;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

public class TextUtil {
	final static private String DATETIME_FORMAT_FOR_FILE_NAME = "MM-dd-yy HH-mm";
	final static private String DATETIME_FORMAT_WITH_FULL_MONTH = "dd MMM yyyy hh:mm aa";

	final static private String SALESFORCE_DATE_FORMAT = "yyyy-MM-dd";
	final static private String SALESFORCE_DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

	final static private String DATETIME_FORMAT = "MM/dd/yyyy HH:mm";
	final static private String DATETIME_FORMAT_WITH_MERIDIAN = "MM/dd/yyyy hh:mm aa";
	final static private String TIME_FORMAT = "HH:mm";
	final static private String TIME_FORMAT_WITH_MERIDIAN = "hh:mm aa";
	final static private String DATE_FORMAT = "MM/dd/yy";
	final static private String DATE_FORMAT_Y4 = "MM/dd/yyyy";
	final static public SimpleDateFormat simpleDateTimeFormatWithFullMonth = new SimpleDateFormat(
			DATETIME_FORMAT_WITH_FULL_MONTH);
	final static public SimpleDateFormat simpleDateTimeFormat = new SimpleDateFormat(
			DATETIME_FORMAT);
	final static public SimpleDateFormat simpleDateTimeFormatWithMeridian = new SimpleDateFormat(
			DATETIME_FORMAT_WITH_MERIDIAN);
	final static private SimpleDateFormat simpleDateFormatY4 = new SimpleDateFormat(
			DATE_FORMAT_Y4);
	final static public SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
			DATE_FORMAT);
	final static public SimpleDateFormat simpleDateTimeFormatForFileName = new SimpleDateFormat(
			DATETIME_FORMAT_FOR_FILE_NAME);

	final static public SimpleDateFormat salesForceDateTimeFormat = new SimpleDateFormat(
			SALESFORCE_DATETIME_FORMAT);
	final static public SimpleDateFormat salesForceDateFormat = new SimpleDateFormat(
			SALESFORCE_DATE_FORMAT);

	final static public SimpleDateFormat simpleTimeFormat = new SimpleDateFormat(
			TIME_FORMAT);
	final static public SimpleDateFormat simpleTimeFormatWithMeridian = new SimpleDateFormat(
			TIME_FORMAT_WITH_MERIDIAN);

	static private List<SimpleDateFormat> dateFormats = new ArrayList<>();

	static {
		dateFormats.add(simpleTimeFormatWithMeridian);
		dateFormats.add(simpleTimeFormat);
		dateFormats.add(simpleDateTimeFormat);
		dateFormats.add(simpleDateFormat);
		dateFormats.add(simpleDateFormatY4);
		dateFormats.add(salesForceDateTimeFormat);
		dateFormats.add(salesForceDateFormat);
		dateFormats.add(simpleDateTimeFormatWithMeridian);
	}

	public static String stringToSalesForceDateFormat(String value) {
		return salesForceDateFormat.format(stringToDate(value));
	}

	public static String stringToSalesForceDateTimeFormat(String value) {
		return salesForceDateTimeFormat.format(stringToDate(value));
	}

	public static String dateTimeToString(Date value) {
		return simpleDateTimeFormat.format(value);
	}

	public static String dateTimeToStringWithMeridian(Date value) {
		return simpleDateTimeFormatWithMeridian.format(value);
	}

	public static String dateToString(Date value) {
		return value == null ? "" : simpleDateFormat.format(value);
	}

	public static String timeToString(Date value) {
		return value == null ? "" : simpleTimeFormatWithMeridian.format(value);
	}

	//
	public static String dateToStringY4(Date value) {
		return value == null ? "" : simpleDateFormatY4.format(value);
	}

	public static String dateTimeToStringForFileName(Date value) {
		return simpleDateTimeFormatForFileName.format(value);
	}

	public static String getCurrentDate() {
		return simpleDateFormatY4.format(Calendar.getInstance().getTime());
	}

	public static String convertDateStr(String dt) {
		// from any format to MM/dd/yy
		return dateToString(stringToDate(dt));
	}

	public static Date stringToDate(String value) {
		if (StringUtils.isEmpty(value))
			return null;

		ParseException lastEx = null;
		for (SimpleDateFormat df : dateFormats) {
			try {
				return df.parse(value);
			} catch (ParseException e) {
				lastEx = e;
			}
		}
		throw new RuntimeException(lastEx);
	}

	public static boolean isDate(String value) {
		try {
			if (StringUtils.isEmpty(value))
				return false;
			stringToDate(value);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static String formatDouble(Double value) {
		return new DecimalFormat("#.#####").format(value);
	}

	public static String createFileName(String fileName) {
		return String.format("%s%s",
				Calendar.getInstance().getTime().getTime(), fileName);
	}

	public static String join(String separator, String... list) {
		String result = "";
		for (String s : list) {
			result += (StringUtils.isBlank(result) || StringUtils.isBlank(s) ? ""
					: ",")
					+ (StringUtils.isBlank(s) ? "" : s);
		}
		return result;
	}

	public static String doubleToStringthousandsSeparate(double value) {
		DecimalFormatSymbols formatSymbols = new DecimalFormatSymbols();
		formatSymbols.setDecimalSeparator('|');
		formatSymbols.setGroupingSeparator(',');
		String strange = "###,###,###.###";
		DecimalFormat df = new DecimalFormat(strange, formatSymbols);
		df.setGroupingSize(3);
		return df.format(value).replace("|", ".");
	}

	public static void parseEagerColumns(String[] columns,
			Set<String> joinColumns, Set<String> initColumns) {
		if (columns == null || joinColumns == null || initColumns == null)
			return;

		initColumns.clear();
		joinColumns.clear();

		Set<String> complexFieldsPrefixes = new HashSet<>();
		for (String v : columns) {
			if (v.contains(".")) {
				complexFieldsPrefixes.add(StringUtils.substringBefore(v, "."));
			}
		}
		if (complexFieldsPrefixes.isEmpty()) {
			initColumns.addAll(Arrays.asList(columns));
			return;
		}

		// there are complex fields
		// ("coProducts.coProductInventories.inventory")
		for (String v : columns) {
			if (!v.contains(".")) {
				if (complexFieldsPrefixes.contains(v)) {
					joinColumns.add(v);
				} else {
					initColumns.add(v);
				}
			} else {
				String[] parts = v.split("\\.");
				String path = "";
				for (String s : parts) {
					path += s;
					joinColumns.add(path);
					path += ".";
				}
			}
		}
	}

	public static Date getDateWithoutTime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	public static Date addMinutes(Date date, int minutesToAdd) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, minutesToAdd);
		return cal.getTime();
	}

	public static String getRefererUrl(HttpServletRequest request) {
		try {
			if (request.getHeader("Referer") == null) {
				return null;
			}
			return new URI(request.getHeader("Referer")).normalize().toURL()
					.getFile();
		} catch (MalformedURLException | URISyntaxException e) {
			// ok
		}
		return null;
	}

	public static String getRandomString() {
		Random r = new Random();
		return new Long(Math.abs(r.nextLong())).toString();
	}

	public static String firstLetterUpper(String value) {
		if (!StringUtils.isEmpty(value)) {
			char[] chs = value.toCharArray();
			chs[0] = Character.toUpperCase(chs[0]);
			return new String(chs);
		}
		return value;
	}

	private static StringBuilder toLabel(String camelCase) {
		String[] parts = StringUtils.split(camelCase, '.');
		char[] chs = parts[parts.length - 1].toCharArray();
		StringBuilder buf = new StringBuilder();
		buf.append(Character.toUpperCase(chs[0]));
		for (int i = 1; i < chs.length; i++) {
			char ch = chs[i];
			if (Character.isUpperCase(ch))
				buf.append(' ');
			buf.append(ch);
		}
		return buf;
	}

	public static String camelToLabel(String camelCase) {
		return camelToLabel(camelCase, true);
	}

	public static String camelToLabel(String camelCase, boolean addColon) {
		StringBuilder result = toLabel(camelCase);
		if (addColon) {
			result.append(':');
		}
		return result.toString();
	}

}