/**
 * Copyright 2014 the original author or authors. All rights reserved.
 */
package com.syard.common.utils;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang3.StringUtils;

/**
 * @author huwk
 * @since 0.0.1
 *
 */
public class CharUtil {
    
	/**
	 * 转换编码IOS-8859-1到GB2312
	 * @param text
	 * @return
	 */
	public static String ISO2GB(String text){
		String result = "";
		try {
			result = new String(text.getBytes("ISO-8859-1"),"GB2312");
		} catch (UnsupportedEncodingException e) {
			result = e.toString();
		}
		return result;
	}
	
	/**
	 * 转换编码GB2312到ISO-8859-1
	 * @param text
	 * @return
	 */
	public static String GB2ISO(String text){
		String result = "";
		try {
			result = new String(text.getBytes("GB2312"),"ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			result = e.toString();
		}
		return result;
	}
	
	/**
	 * 转换编码ISO-8859-1到utf8
	 * @param text
	 * @return
	 */
	public static String ISO2Utf8(String text){
		String result = "";
		try {
			result = new String(text.getBytes("ISO-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			result = e.toString();
		}
		return result;
	}
	/***
	 * UTF8URL编码
	 * @param text
	 * @return
	 */
	public static String Utf8URLencode(String text){
		StringBuffer result = new StringBuffer();
		for(int i=0;i<text.length();i++){
			char c = text.charAt(i);
			if(c>=0 && c<255){
				result.append(c);
			}else{
				byte[] b = new byte[0];
				try {
					b = Character.toString(c).getBytes("UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				for(int j=0;j<b.length;j++){
					int k = b[j];
					if(k<0){
						k+=256;
					}
					result.append("%"+Integer.toHexString(k).toUpperCase());
				}
			}
		}
		return result.toString();
	}
	/**
	 * Utf8URL解码
	 * @param text
	 * @return
	 */
	public static String Utf8URLdecode(String text){
		String result = "";
		int p = 0;
		if(text != null && text.length()>0){
			text = text.toLowerCase();
			p = text.indexOf("%e");
			if(p == -1){
				return text;
			}
			while(p != -1){
				result += text.substring(0,p);
				text = text.substring(p,text.length());
				if(text == "" || text.length()<9){
					return result;
				}
				result += CodeToWord(text.substring(0,9));
				text = text.substring(9,text.length());
				p = text.indexOf("%e");
			}
		}
		return result+text;
	}
	/**
	 * utf8URL编码转字符
	 * @param text
	 * @return
	 */
	private static String CodeToWord(String text){
		String result = "";
		if(Utf8codeCheck(text)){
			byte[] code = new byte[3];
			code[0] = (byte)(Integer.parseInt(text.substring(1,3),16)-256);
			code[1] = (byte)(Integer.parseInt(text.substring(4,6),16)-256);
			code[2] = (byte)(Integer.parseInt(text.substring(7,9),16)-256);
			
			try {
				result = new String(code,"UTF-8");
			} catch (UnsupportedEncodingException e) {
				result = null;
			}
		}else{
			result = text;
		}
		return result;
	}
	/**
	 * 编码是否有效
	 * @param text
	 * @return
	 */
	private static boolean Utf8codeCheck(String text){
		String sign = "";
		if(text.startsWith("%e")){
			for(int p=0;p!=-1;){
				p = text.indexOf("%",p);
				if(p != -1){
					p++;
				}
				sign += p;
			}
		}
		return sign.equals("147-1");
	}
	/**
	 * 是否Utf8Url
	 * @param text
	 * @return
	 */
	public static boolean isUtf8Url(String text){
		text= text.toLowerCase();
		int p = text.indexOf("%");
		if(p != -1 && text.length()-p>p){
			text = text.substring(p,p+9);
		}
		return Utf8codeCheck(text);
	}
	
    public static String getEncodingStr(String str) {      
        String encode = "GB2312";      
       try {      
           if (str.equals(new String(str.getBytes(encode), encode))) {      
                String s = encode;      
               return s;      
            }      
        } catch (Exception exception) {      
        }      
        encode = "ISO-8859-1";      
       try {      
           if (str.equals(new String(str.getBytes(encode), encode))) {      
                String s1 = encode;      
               return s1;      
            }      
        } catch (Exception exception1) {      
        }      
        encode = "UTF-8";      
       try {      
           if (str.equals(new String(str.getBytes(encode), encode))) {      
                String s2 = encode;      
               return s2;      
            }      
        } catch (Exception exception2) {      
        }      
        encode = "GBK";      
       try {      
           if (str.equals(new String(str.getBytes(encode), encode))) {      
                String s3 = encode;      
               return s3;      
            }      
        } catch (Exception exception3) {      
        }      
       return "";      
    }   
    
    public static String textToUtf8(String text){
    	String encoding = getEncodingStr(text);
    	String result = "";
    	try {
    		if(StringUtils.equalsIgnoreCase("UTF-8", encoding) || StringUtils.equalsIgnoreCase("UTF8", encoding)){
    			result =  text;
    		}else if(StringUtils.equalsIgnoreCase("gb2312", encoding)){
    			result = text;
    		}else if(StringUtils.equalsIgnoreCase("ISO-8859-1", encoding)){
    			result = new String(text.getBytes("ISO-8859-1"),"UTF-8");
    		}else if(StringUtils.equalsIgnoreCase("GBK", encoding)){
    			result = new String(text.getBytes("ISO-8859-1"),"UTF-8");
    		}else{
    			result = text;
    		}
		} catch (UnsupportedEncodingException e) {
			result = e.toString();
		}
    	return result;
    }
    
    public static String gbToUtf8(String str) throws UnsupportedEncodingException {   
        StringBuffer sb = new StringBuffer();   
        for (int i = 0; i < str.length(); i++) {   
            String s = str.substring(i, i + 1);   
            if (s.charAt(0) > 0x80) {   
                byte[] bytes = s.getBytes("Unicode");   
                String binaryStr = "";   
                for (int j = 2; j < bytes.length; j += 2) {   
                    // the first byte   
                    String hexStr = getHexString(bytes[j + 1]);   
                    String binStr = getBinaryString(Integer.valueOf(hexStr, 16));   
                    binaryStr += binStr;   
                    // the second byte   
                    hexStr = getHexString(bytes[j]);   
                    binStr = getBinaryString(Integer.valueOf(hexStr, 16));   
                    binaryStr += binStr;   
                }   
                // convert unicode to utf-8   
                String s1 = "1110" + binaryStr.substring(0, 4);   
                String s2 = "10" + binaryStr.substring(4, 10);   
                String s3 = "10" + binaryStr.substring(10, 16);   
                byte[] bs = new byte[3];   
                bs[0] = Integer.valueOf(s1, 2).byteValue();   
                bs[1] = Integer.valueOf(s2, 2).byteValue();   
                bs[2] = Integer.valueOf(s3, 2).byteValue();   
                String ss = new String(bs, "UTF-8");   
                sb.append(ss);   
            } else {   
                sb.append(s);   
            }   
        }   
        return sb.toString();   
    }   
    private static String getHexString(byte b) {   
        String hexStr = Integer.toHexString(b);   
        int m = hexStr.length();   
        if (m < 2) {   
            hexStr = "0" + hexStr;   
        } else {   
            hexStr = hexStr.substring(m - 2);   
        }   
        return hexStr;   
    }   
  
    private static String getBinaryString(int i) {   
        String binaryStr = Integer.toBinaryString(i);   
        int length = binaryStr.length();   
        for (int l = 0; l < 8 - length; l++) {   
            binaryStr = "0" + binaryStr;   
        }   
        return binaryStr;   
    }   
}
