package com.syard.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;

public class FileDigestUtil {
	
	private static byte[] buffer = new byte[1024];

	public static String getMD5(File file){
		int len = 0;
		try{
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			InputStream inputStream = new FileInputStream(file);
			while ((len = inputStream.read(buffer)) != -1){
				messageDigest.update(buffer, 0, len);
			}
			inputStream.close();
			BigInteger bigInteger = new BigInteger(1, messageDigest.digest());
			String b = bigInteger.toString(16);
			for(int i=b.length();i<32;i++){
				b = "0"+b;
			}
			return b;
		}
		catch (Exception e){
			return null;
		}
	}
	public static String getMD5(String password){
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		char[] charArray = password.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();

	}
}

