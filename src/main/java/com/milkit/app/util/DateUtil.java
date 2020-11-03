package com.milkit.app.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;



public class DateUtil {
	
	public static SimpleDateFormat getMotpDateFormat(String format) {
		return new SimpleDateFormat(format);
	}


	
	public static int compareDate(Date srcDate, Date compareDate) throws ParseException {
		if (srcDate.equals(compareDate)) return 0;

		return !srcDate.after(compareDate) ? 1 : -1;
	}
	
    public static int getDaysDiff(String sDate1, String sDate2) {
    	String dateStr1 = validChkDate(sDate1);
    	String dateStr2 = validChkDate(sDate2);
    	
        if (!checkDate(sDate1) || !checkDate(sDate2)) {
            throw new IllegalArgumentException("Invalid date format: args[0]=" + sDate1 + " args[1]=" + sDate2);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = sdf.parse(dateStr1);
            date2 = sdf.parse(dateStr2);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format: args[0]=" + dateStr1 + " args[1]=" + dateStr2);
        }
        int days1 = (int)((date1.getTime()/3600000)/24);
        int days2 = (int)((date2.getTime()/3600000)/24);
        
        return days2 - days1;
    }
    
    public static int getDaysDiff(Date srcDate, Date diffDate) {
        int days1 = (int)((srcDate.getTime()/3600000)/24);
        int days2 = (int)((diffDate.getTime()/3600000)/24);
        
        return days2 - days1;
    }
    
    public static int getMinDiff(String sDate1, String sDate2) {
//  	String dateStr1 = validChkDate(sDate1);
//    	String dateStr2 = validChkDate(sDate2);
    	
//        if (!checkDate(sDate1) || !checkDate(sDate2)) {
//            throw new IllegalArgumentException("Invalid date format: args[0]=" + sDate1 + " args[1]=" + sDate2);
//        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm", Locale.getDefault());
        
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = sdf.parse(sDate1);
            date2 = sdf.parse(sDate2);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format: args[0]=" + sDate1 + " args[1]=" + sDate2);
        }
        int minuite  = (1000 * 3600) / 60; //1분
        
        int days1 = (int)((date1.getTime()/minuite));
        int days2 = (int)((date2.getTime()/minuite));
        
        return days2 - days1;
    }
    
    public static int getMinDiff(Date date1, Date date2) {
//  	String dateStr1 = validChkDate(sDate1);
//    	String dateStr2 = validChkDate(sDate2);
    	
//        if (!checkDate(sDate1) || !checkDate(sDate2)) {
//            throw new IllegalArgumentException("Invalid date format: args[0]=" + sDate1 + " args[1]=" + sDate2);
//        }
        
    	int minDiff = 0;
    	
    	if(date1 != null && date2 != null) {
	        int minuite  = (1000 * 3600) / 60; //1분
	        
	        int days1 = (int)((date1.getTime()/minuite));
	        int days2 = (int)((date2.getTime()/minuite));
	       	minDiff = days2 - days1; 
    	}
        
        return minDiff;
    }

	
	public static String plusDay(String srcdateFormat, String srcdate, int addday) throws ParseException {
		SimpleDateFormat motpDateFormat = new SimpleDateFormat(srcdateFormat);

		Date src = motpDateFormat.parse(srcdate);
		Calendar cal = Calendar.getInstance();
		cal.setTime(src);
		cal.add(Calendar.DAY_OF_MONTH, addday);
		src = cal.getTime();
		return motpDateFormat.format(src);
	}
	
	public static String plusDay(Date srcdate, String targetdateFormat, int addday) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(targetdateFormat);
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(srcdate);
		cal.add(Calendar.DAY_OF_MONTH, addday);
		srcdate = cal.getTime();
		
		return dateFormat.format(srcdate);
	}
	
	
	public static String plusMonth(String srcdateFormat, String srcdate, int month) throws ParseException {
		SimpleDateFormat motpDateFormat = new SimpleDateFormat(srcdateFormat);

		Date src = motpDateFormat.parse(srcdate);
		Calendar cal = Calendar.getInstance();
		cal.setTime(src);
		cal.add(Calendar.MONTH, month);
		src = cal.getTime();
		return motpDateFormat.format(src);
	}

	public static String getCurrentTimeMs() {
		SimpleDateFormat motpDateFormatMs = new SimpleDateFormat("yyyyMMddHHmmssSS");
		
		return motpDateFormatMs.format(new Date(System.currentTimeMillis()));
	}
	
	
	public static Date parseDate(String time, String format) throws ParseException {
		SimpleDateFormat motpDateFormat = getMotpDateFormat(format);
		
		return motpDateFormat.parse(time);
	}
	
	public static Date parseDate(long time) throws ParseException {
		return new Date(time);
	}
	
	public static String parseDateString(long time, String outputFormat) throws ParseException {
		Date date = new Date(time);
		SimpleDateFormat format = new SimpleDateFormat(outputFormat);
		
	    return format.format(date).toString();
	}
	
	public static String parseDateString(String time, String inputFormat, String outputFormat) throws ParseException {
		SimpleDateFormat motpDateFormat = getMotpDateFormat(inputFormat);
		Date parseDate = motpDateFormat.parse(time);
		
		return getTimeString(parseDate, outputFormat);
	}
	
	public static String parseDateString(Date curDate, String formatStr) throws Exception {
		SimpleDateFormat motpDateFormatMs = new SimpleDateFormat(formatStr);
			
		return motpDateFormatMs.format(curDate);
	}
	
	public static String getCurrectTimeString(String format) {
		SimpleDateFormat motpDateFormatMs = new SimpleDateFormat(format);
		
		return motpDateFormatMs.format(new Date());
	}
	
	public static String getTimeString(Date date, String format) {
		String timeString = "";
		
		if(date != null && format != null) {
			SimpleDateFormat motpDateFormatMs = new SimpleDateFormat(format);
			timeString =  motpDateFormatMs.format(date);
		}
		
		return timeString;
	}
	
	public static String getCurrentYear() {
		SimpleDateFormat motpDateFormat = new SimpleDateFormat("yyyy");
		return motpDateFormat.format(new Date());
	}
	
	public static String getCurrentMonth() {
		SimpleDateFormat motpDateFormat = new SimpleDateFormat("MM");
		return motpDateFormat.format(new Date());
	}
	
	public static String getLastDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		return String.valueOf(calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
	}
	
	public static String getWeekDay(Date date) {
		String []weeks = {"일","월","화","수","목","금","토"};
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		return weeks[calendar.get(Calendar.DAY_OF_WEEK)-1];
	}
	
	public static String getWeekDay(String dateString, String dateFormat) throws ParseException {
		Date parseDate = DateUtil.parseDate(dateString, dateFormat);
		
		return getWeekDay(parseDate);
	}
	    
    
    public static int getLastDayOfMonth(int year, int month) throws Exception {
    	Calendar calendar = Calendar.getInstance();
       	calendar.set(year,month-1,1);
       	
       	return calendar.getActualMaximum(Calendar.DATE);
    }
    
    public static int getLastDayOfMonth(String yearMonth) throws Exception {
    	return getLastDayOfMonth(Integer.parseInt(yearMonth.substring(0, 4)), Integer.parseInt(yearMonth.substring(4, 6)));
    }
	
	
    public static String validChkDate(String dateStr) {
    	String _dateStr = dateStr;
    	
        if (dateStr == null || !(dateStr.trim().length() == 8 || dateStr.trim().length() == 10)) {
            throw new IllegalArgumentException("Invalid date format: " + dateStr);
        }
        if (dateStr.length() == 10) {
        	_dateStr = StringUtil.removeMinusChar(dateStr);
        }
        return _dateStr;
    }
    
    public static boolean checkDate(String sDate) {
//    	String dateStr = validChkDate(sDate);

        String year  = sDate.substring(0,4);
        String month = sDate.substring(4,6);
        String day   = sDate.substring(6);
   
        return checkDate(year, month, day);
    }   

    public static boolean checkDate(String year, String month, String day) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault());
            
            Date result = formatter.parse(year + "." + month + "." + day);
            String resultStr = formatter.format(result);
            if (resultStr.equalsIgnoreCase(year + "." + month + "." + day))
                return true;
            else
                return false;
        } catch (Exception e) {
            return false;
        }
    }
    

}
