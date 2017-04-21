package com.hc360.mobile.webservice.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.SignatureException;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Utils {

	public static String md5Encode(String inStr) throws Exception {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return "";
		}

		byte[] byteArray = inStr.getBytes("UTF-8");
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16) {
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString().toLowerCase();
	}
	
	 /**
     * 字符串加密
     * @param content 需要加密的字符串
     * @param key 密钥
     * @param input_charset 编码格式
     * @return 加密结果
     */
    public static String sign(String content, String key, String input_charset) {
    	content = content + key;
        return DigestUtils.md5Hex(getContentBytes(content, input_charset));
    }
    
    /**
     * MD5验证
     * @param content 需要加密的字符串
     * @param sign 传过来的加密结果
     * @param key 密钥
     * @param input_charset 编码格式
     * @return 验证结果
     */
    public static boolean verify(String content, String sign, String key, String input_charset) {
    	content = content + key;
    	String mysign = DigestUtils.md5Hex(getContentBytes(content, input_charset));
    	if(mysign.equals(sign)) {
    		return true;
    	}else {
    		return false;
    	}
    }

    /**
     * @param content
     * @param charset
     * @return
     * @throws SignatureException
     * @throws UnsupportedEncodingException 
     */
    private static byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
        }
    }
    
    public static void main(String[] args) throws Exception {
		String text = "123456";
		String sign = "a49452da4abcebdcc1fa96d3cc49aada";
		String key = "2008";
		String text1=text;
		
		boolean result = verify(text, sign, key, "utf-8");
		
		System.out.println(result);
		System.out.println(sign(text1,"2008","utf-8"));
		
	}
	
}
