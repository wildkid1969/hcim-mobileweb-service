/**
 * Copyright 2008 Gerard Toonstra
 *
 * As an exception, this particular file 
 * in the project is public domain to allow totally
 * free derivations of this code.
 * 
 */

package com.hc360.mobile.webservice.utils;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;


/**
 * Implementation of a relatively simple XSS filter. This implementation removes
 * dangerous tags and attributes from tags. It does not verify the validity of 
 * URL's (that may contain links to JavaScript for example). It does not remove all
 * event handlers that may still contain XSS vulnerabilities. 
 * 
 * Embedded objects are removed because those may contain XSS vulnerabilities in 
 * their own scripting language (ActionScript for Flash for example).
 * 
 * Feel free to derive your own implementation from this file.
 * 
 * @author gt
 *
 */
public class XSSFilterUtils{
	
	private static final Set<String> FORBIDDEN_TAGS = new HashSet<String>();
	
	// The tags to be removed. Case insensitive of course.
	static {
		FORBIDDEN_TAGS.add( "script" );
		FORBIDDEN_TAGS.add( "embed" );
		FORBIDDEN_TAGS.add( "object" );
		FORBIDDEN_TAGS.add( "layer" );//1
		FORBIDDEN_TAGS.add( "style" );//4
		FORBIDDEN_TAGS.add( "meta" );//1
		FORBIDDEN_TAGS.add( "iframe" );
		FORBIDDEN_TAGS.add( "frame" );
		FORBIDDEN_TAGS.add( "link" );//3
		FORBIDDEN_TAGS.add( "import" );//2
		FORBIDDEN_TAGS.add( "xml" );//10
	}
	
	/**
	 * This function is called to determine if an attribute should be filtered or not.
	 * 
	 * @param tagName	The name of the tag the attribute belongs to
	 * @param attrName	The name of the attribute to be filtered
	 * @param attrValue	The value of the attribute
	 */
	public boolean filterAttribute( String attrValue) {
		return isScriptedAttributeValue( attrValue );
	}

	

	public boolean filterTag(String tagName) {
		if ( FORBIDDEN_TAGS.contains( tagName )) {
			return true;
		}
		return false;
	}

	
	/**
	 * Private method that determines if an attribute value is scripted
	 * (potentially loaded with an XSS attack vector).
	 * 
	 * @param attrValue	The value of the attribute
	 * @return "true" if the attribute is scripted. "false" if not.
	 */
	private boolean isScriptedAttributeValue( String attrValue ) {
		String temp = decode( attrValue );
		temp = temp.trim().toLowerCase();
		if(temp.length()!=attrValue.length()){
			return true;
		}

		if (temp.contains("javascript:")){
			return true;
		}
		if(temp.contains("mocha:")){
			return true;
		}
		if(temp.contains("eval")){//2
			return true;
		}
		if(temp.contains("vbscript:")){
			return true;
		}
		if(temp.contains("livescript:")){
			return true;
		}
		if(temp.contains("expression(")){
			return true;
		}
		if(temp.contains("url(")){
			return true;
		}
		if(temp.contains("&{")){
			return true;
		}
		if(temp.contains("&#")){
			return true;
		}
		return false;
	}
	
	/**
	 * Private method to remove control characters from the value
	 * 
	 * @param value	The value being modified
	 * @return	The value free from control characters
	 */
	private String decode( String value ) {
		value = value.replace("\u0000", "" );
		value = value.replace("\u0001", "" );
		value = value.replace("\u0002", "" );
		value = value.replace("\u0003", "" );
		value = value.replace("\u0004", "" );
		value = value.replace("\u0005", "" );
		value = value.replace("\u0006", "" );
		value = value.replace("\u0007", "" );
		value = value.replace("\u0008", "" );
		value = value.replace("\u0009", "" );
		value = value.replace("\n", "" );
		value = value.replace("\u000B", "" );
		value = value.replace("\u000C", "" );
		value = value.replace("\r", "" );
		value = value.replace("\u000E", "" );
		value = value.replace("\u000F", "" );
		value = value.replace("\u0010", "" );
		value = value.replace("\u0011", "" );
		value = value.replace("\u0012", "" );
		value = value.replace("\u0013", "" );
		value = value.replace("\u0014", "" );
		value = value.replace("\u0015", "" );
		value = value.replace("\u0016", "" );
		value = value.replace("\u0017", "" );
		value = value.replace("\u0018", "" );
		value = value.replace("\u0019", "" );
		value = value.replace("\u001A", "" );
		value = value.replace("\u001B", "" );
		value = value.replace("\u001C", "" );
		value = value.replace("\u001D", "" );
		value = value.replace("\u001E", "" );
		value = value.replace("\u001F", "" );
		return value;
	}
	
	
	/** 
	   * SQL注入于XSS攻击自动检测 
	   * @param str 
	   * @return 
	   */ 
	  public static boolean checkSqlInjection(String str) { 
	     Logger logger = Logger.getLogger("u3"); 
	     String limit = "-|script|alert|hex|\\(|\\)|\\*|iframe|\\+|\\%|window|cookie|and|or|net user|/add|execute|into|outfile|exec|net|select|count|char|insert|delete|drop|from|master|truncate|char|declare|or|by|;|#|%|`|:|\"|\'|"; 
	     String keys[] = limit.split("|"); 
	     for (int i = 0; i < keys.length; i++) { 
	        Pattern checkSQL = Pattern.compile(keys[i],Pattern.CASE_INSENSITIVE); 
	        Matcher matcherFaild = checkSQL.matcher(str.trim()); 
	        if (matcherFaild.find()) { 
	          logger.info("攻击地址："+str); 
	        return false; 
	        } 
	    } 
	    return true; 
	  } 
	   
	  /** 
	   * 模拟PHP的htmlSpecialChars，替换HTML标记 
	   * @param str 
	   * @return 
	   */ 
	  public static String htmlSpecialChars(String str) { 
	     str = str.replaceAll("&", "&amp;"); 
	     str = str.replaceAll("<", "&lt;"); 
	     str = str.replaceAll(">", "&gt;"); 
	     str = str.replaceAll("\"", "&quot;"); 
	     str = str.replaceAll("'", "&#x27;"); 
	     
	     return str; 
	  } 
	  
	  public static String cleanXSS(String value) {
	        //You'll need to remove the spaces from the html entities below
	        value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
	        value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
	        value = value.replaceAll("'", "&#39;");
	        value = value.replaceAll("eval\\((.*)\\)", "");
	        value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
	        value = value.replaceAll("script", "");
	        return value;
	   }
	  public static String cleanSimpleXSS(String value) {
		  //You'll need to remove the spaces from the html entities below
		  value = value.replaceAll("eval\\((.*)\\)", "");
	      value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
		  value = value.replaceAll("script", "");
		  return value;
	  }
}
