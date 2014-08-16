package com.hck.web;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Test {

	/**
	 * @param args
	 * @throws ParseException 
	 */
	public static void main(String[] args) throws ParseException {
		// TODO Auto-generated method stub
		List<Date> dates = new ArrayList<Date>();
		List<Date> finalDates = new ArrayList<Date>();

		String str_date ="27/08/2010";
		String end_date ="02/09/2010";

		DateFormat formatter ; 

		formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date  startDate = (Date)formatter.parse(str_date); 
		System.out.println("startDate:::"+startDate);
		Date  endDate = (Date)formatter.parse(end_date);
		long interval = 24*1000 * 60 * 60; // 1 hour in millis
		long endTime =endDate.getTime() ; // create your endtime here, possibly using Calendar or Date
		long curTime = startDate.getTime();
		while (curTime <= endTime) {
		    dates.add(new Date(curTime));
		    curTime += interval;
		}
		System.out.println("dates:::"+dates);
		for(int i=0;i<dates.size();i++){
		    Date lDate =(Date)dates.get(i);
		    String ds = formatter.format(lDate);    
		    System.out.println(" Date is ..." + ds);
		    finalDates.add(new Date(ds));
		}

System.out.println("finalDates:::"+finalDates);
	}

}
