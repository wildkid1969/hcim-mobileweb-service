package com.hc360.mobile.webservice.utils;

public class XssProtectUtils {
	
	//判断输入是否存在“可能注入的字符”
	public static boolean isHaveXSS( String html ) {
		boolean flag=false;
		//验证特殊字符
		flag=RegexStringUtils.isSpecialChar(html);
		if(flag){
	    	return true;
		}
		//验证sql字符
//		flag=RegexStringUtils.isSqlCode(html);
//		if(flag){
//	    	return true;
//		}
		XSSFilterUtils filter=new XSSFilterUtils();
		//验证html字符
		flag=filter.filterTag(html);
		if(flag){
	    	return true;
		}
		//验证script等字符 会去除转义的一些字符
		flag=filter.filterAttribute(html);
		if(flag){
	    	return true;
		}
		return false;
	}

}
