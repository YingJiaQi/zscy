package com.syard.common.utils;

import java.io.StringReader;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.xml.sax.InputSource;

public class Helper {
	public static Date str2Date(String dateStr) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = dateFormat.parse(dateStr);
		} catch (ParseException e) {
		}
		return date;
	}

	public static String Date2String(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = "";
		try {
			dateStr = dateFormat.format(date);
		} catch (Exception e) {
		}
		return dateStr;
	}

	public static String String2Utf8(String s) {
		String utf8 = s;
		try {
			utf8 = new String(s.getBytes("gbk"), "utf-8");
		} catch (Exception e) {
		}
		return utf8;
	}

	public static Float percent2Flaot(String ss) {
		Float pf = null;
		if (ss.contains("%")) {
			ss = ss.replaceAll("%", "");
			Float f = Float.valueOf(ss);
			pf = f / 100;
		}
		return pf;
	}

	// 生成4随机验证码
	public static String VCode() {
		String verifCode = "";
		Random rd = new Random();
		for (int i = 0; i < 4; i++) {
			verifCode += rd.nextInt(10);
		}
		return verifCode;
	}
}
