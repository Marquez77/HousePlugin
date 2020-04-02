package com.marquez.houseplugin.util;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateTime {
	
	private Instant instant;
	private String regex;
	
	public void setInstant(int year, int month, int day, int hrs, int min, int sec, int milli) {
		if(year == 0) this.instant = Instant.ofEpochMilli(((((day*24+hrs)*60+min)*60+sec)*1000+milli));
		else {
			@SuppressWarnings("deprecation")
			Date date = new Date(year, month, day, hrs, min, sec);
			this.instant = date.toInstant().plusMillis(milli);
		}
	}
	
	public DateTime(int year, int month, int day, int hour, int minute, int second, int millis) {
		setInstant(year, month, day, hour, minute, second, millis);
	}
	
	public DateTime(String datetime) {
		this.regex = datetime;
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		for(Regex regex : Regex.values()) {
			String r = String.join("|", regex.getRegex());
			StringBuilder sb = new StringBuilder();
			sb.append("([0-9]+)").append("(").append(r).append(")");
			Pattern p = Pattern.compile(sb.toString());
			Matcher m = p.matcher(datetime);
			if(m.find()) {
				int value = Integer.parseInt(m.group(1));
				map.put(regex.name(), value);
			}else {
				map.put(regex.name(), 0);
			}
		}
		setInstant(map.get("year"), map.get("month"), map.get("day"), map.get("hour"), map.get("minute"), map.get("second"), map.get("milli"));
	}
	
	public long getMillis() {
		return instant.toEpochMilli();
	}
	
	public int getSeconds() {
		return (int)(getMillis()/1000L);
	}
	
	public int getMinutes() {
		return getSeconds()/60;
	}
	
	public int getHours() {
		return getMinutes()/60;
	}
	
	public Date getDate() {
		return new Date(getMillis());
	}
	
	public String getRegex() {
		return this.regex;
	}

}

enum Regex {
	
	year("y", "year", "years"),
	month("M", "Month", "month"),
	day("d", "date", "day"),
	hour("h", "hour", "hours"),
	minute("m", "minute", "minutes"),
	second("s", "sec", "secs", "second", "seconds"),
	milli("ms", "milli", "millis");
	
	private String[] regex;
	
	Regex(String... regex) {
		this.regex = regex;
	}
	
	public String[] getRegex() {
		return this.regex;
	}
}
