package com.hc360.mobile.webservice.utils;

public class RegexStringUtils {
	public static final String OCT_NUM_REGEX = "\\d+";
	public static final String HEX_NUM_REGEX = "(?i)[0-9a-f]+";
	public static final String EMAIL_REGEX = "^([a-zA-Z0-9]+[\\_|\\-|\\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[\\_|\\-|\\.]?)*[a-zA-Z0-9]+\\.[a-zA-Z]{2,3}$";
	
	public static final String MOBILE_REGEX = "^0{0,1}(13[0-9]|14[0-9]|15[0-9]|17[0-9]|18[0-9])[0-9]{8}$";
	public static final String SQL_REGEX = ".*(=|and|exec|insert|select|delete|update|count|chr|mid|master|truncate|char|declare).*";

	public static final String SPECIAL_CHAR_REGEX=".*('|\"|\\|;|,|<|>|\\s).*";
	
	/**
	 * 验证手机号码
	 * @param mobile
	 * @return true 为有效的手机号码
	 */
	public static boolean isValideMobile(String mobile) 
	{
		return mobile.matches(MOBILE_REGEX);
	}
	
	
	
	/**
	 * 验证邮箱
	 * @param email
	 * @return true 为有效的邮箱地址
	 */
	public static boolean isValideEmail(String email) 
	{
		return email.matches(EMAIL_REGEX);
	}
	/**
	 * 校验16进制数
	 * @param value
	 * @return
	 */
	public static boolean isHexNumber(String value) 
	{
		String validateRegx = HEX_NUM_REGEX;
		return value.matches(validateRegx);
	}
	/**
	 * 校验是数字
	 * @param value
	 * @return
	 */
	public static boolean isOctNumber(String value) 
	{
		String validateRegx = OCT_NUM_REGEX;
		return value.matches(validateRegx);
	}
	/**
	 * 验证是否有非法字符串(与sql相关)
	 * @param value
	 * @return
	 */
	public static boolean isSqlCode(String value)
	{
		String validateRegx = SQL_REGEX;
		return value.matches(validateRegx) ;
	}
	/**
	 * 验证是否含有特殊字符
	 * @param value
	 * @return true 含特殊字符 ；false 不含特殊字符
	 */
	public static boolean isSpecialChar(String value){
		String validateRegx = SPECIAL_CHAR_REGEX;
		return value.matches(validateRegx) ;
	}
	/*public static void main(String [] args){
		
		String temp="ssfdfsasf 对的";
		String temp0="\"ssfdfsasf错的";
		String temp1="countdfsasf错的";
		String temp2="ssfdfs'asf错的12";
		String temp3="ssfdfsa\"sf错的12";
		String temp4="ssfdfsasf错的12";
		System.out.println("字符串，"+temp+"中:"+isSpecialChar(temp));
		System.out.println("字符串，"+temp0+"中:"+isSpecialChar(temp0));
		System.out.println("字符串，"+temp1+"中:"+isSqlCode(temp1));
		System.out.println("字符串，"+temp2+"中:"+isSpecialChar(temp2));
		System.out.println("字符串，"+temp3+"中:"+isSpecialChar(temp3));
		System.out.println("字符串，"+temp4+"中:"+isSpecialChar(temp4));
		
		String temp4="17411112222";
		System.out.println("字符串，"+temp4+"中:"+isValideMobile(temp4));
	}*/
}
