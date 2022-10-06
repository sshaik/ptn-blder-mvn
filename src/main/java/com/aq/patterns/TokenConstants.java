package com.aq.patterns;

import java.util.ArrayList;
import java.util.List;

/* protected class */
	public final class TokenConstants {

	/*
	 * Declaring both String and char array to avoid conversion between
	 * String and char array and vice versa to improve performance.
	 * Declared all possible scenarios to avoid string or char array concatenation
	 * Declare all possible chars and strings to avoid string manipulations.
	 */
	public static final String LOWER_ALPHA_STR = "abcdefghijklmnopqrstuvwxyz";
	public static final char[] LOWER_ALPHA_CHARS = new char[]{'a','b','c','d', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm' ,'n', 'o', 'p','q', 'r', 's', 't', 'u',
															'v','w','x','y','z'};
	public static final String UPPER_ALPHA_STR ="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final char[] UPPER_ALPHA_CHARS = new char[]{'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
	public static final String ALPHA_STR = "aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ";
	public static final char[] ALPHACHARS = new char[]{'a','A','b','B','c','C','d','D','e','E','f','F',
																 'g','G','h','H','i','I','j','J','k','K','l','L',
																 'm','M','n','N','o','O','p','P','q','Q','r','R',
																 's','S','t','T','u','U','v','V','w','W','x','X',
																 'y','Y','z','Z'};
	
	public static final char[] ALPHANUMERIC = new char[]{'a','A','b','B','c','C','d','D','e','E','f','F',
		 'g','G','h','H','i','I','j','J','k','K','l','L',
		 'm','M','n','N','o','O','p','P','q','Q','r','R',
		 's','S','t','T','u','U','v','V','w','W','x','X',
		 'y','Y','z','Z','0','1','2','3','4','5','6','7','8','9'};

	
	
	public static final String LOWER_VOWELS_STR="aeiou";
	public static final char[] LOWER_VOWERL_CHARS = new char[]{'a','e','i','o','u'};
	public static final String upperVolwelsStr="AEIOU";
	public static final char[] upperVowelsChars = new char[]{'A','E','I','O','U'};
	public static final String lowerAlphaConsonentsStr = "bcdfghjklmnpqrstvwxyz";
	public static final char[] lowerAlphaConsonentsChars = new char[]{'b','c','d','f', 'g', 'h', 'j', 'k', 'l', 'm' ,'n','p','q', 'r', 's', 't','v','w','x','y','z'};
	public static final String upplerAlphaConsonentsStr ="BCDFGHJKLMNPQRSTVWXYZ";
	public static final char[] upplerAlphaConsonentsChars = new char[]{'B','C','D','F','G','H','J','K','L','M','N','P','Q','R','S','T','V','W','X','Y','Z'};
	public static final String NUMBER_STR="0123456789";
	public static final char[] numberChars = new char[]{'0','1','2','3','4','5','6','7','8','9'};
	public static final String evenNumberStr = "02468"; // is 0 is even number. Yes as per wikipedia.
	public static final char[] evenNumberChars = new char[]{'0','2','4','6','8'};
	public static final String oddNumberStr = "13579";
	public static final char[] oddNumberChars= new char[]{'1','3','5','7','9'};
	public static final String specialcharStr = "~!@#$%^&*()_-+{[}]|:;\"\'<,>.?/";
	public static final char[] SPECIAL_CHARS = new char[]{'~', '!', '@', '#', '$', '%', '^', '&', '*', '(',
														 ')', '_', '-', '+', '{', '[', '}', ']', '|', ':', ';',  '\\',  '"', '<', '>', '.', '?', '/','\''};
	public static final char[] whiteSpaceChar = new char[]{' '};
	public static final String arthematicCharString = "-+=*/<>%";
	public static final char[] arthematicChars =  new char[]{'-','+','=','/','<','>','%'};
	public static final char[] ANY_CHARS= new char[]{'a','A','b','B','c','C','d','D','e','E','f','F',
		 											'g','G','h','H','i','I','j','J','k','K','l','L',
		 											'm','M','n','N','o','O','p','P','q','Q','r','R',
		 											's','S','t','T','u','U','v','V','w','W','x','X',
		 											'y','Y','z','Z',
		 											'0','1','2','3','4','5','6','7','8','9',
		 											'~', '!', '@', '#', '$', '%', '^', '&', '*', '(',
														 ')', '_', '-', '+', '{', '[', '}', ']', '|', ':', ';',  '\\',  '"', '<', '>', '.', '?','/','\'',
														 '-','+','=','/','<','>','%',' '
														 };
	public static final char[] anyCharWithoutSpaceChar= new char[]{'a','A','b','B','c','C','d','D','e','E','f','F',
			'g','G','h','H','i','I','j','J','k','K','l','L',
			'm','M','n','N','o','O','p','P','q','Q','r','R',
			's','S','t','T','u','U','v','V','w','W','x','X',
			'y','Y','z','Z',
			'0','1','2','3','4','5','6','7','8','9',
			'~', '!', '@', '#', '$', '%', '^', '&', '*', '(',
			 ')', '_', '-', '+', '{', '[', '}', ']', '|', ':', ';',  '\\',  '"', '<', '>', '.', '?','/','\'',
			 '-','+','=','/','<','>','%',' '
			 };
	
	
	//TODO - check with mahendra what is the final deal with sentences. Do we need to maintain any dictionry at DB or in java clas
	public static final String[] WORD_LIST = new String[]{"Hello","World","Nice","Good","Excellent","Average","Positive","Hyper","Yes","Do","Perform"
		,"Finish","Complete","Execute","Deliver","Furniture","House","Castle","There","Here","Gallent","Twist","Congratulation","Health"};
	
	public static final String[] SENTENCE_LIST = new String[]
			{"I can\'t change the direction of the wind, but I can adjust my sails to always reach my destination.",
		     "The measure of who we are is what we do with what we have.",
		     "Try to be a rainbow in someone's cloud.",
		     "Change your thoughts and you change your world.",
		     "It is never too late to be what you might have been."	,
		     "They can because they think the can",
		     "The surest way not to fail is to determine to succeed."
			};
	
	public static final String[] YEAR_QUARTERS = new String[]{"Q1","Q2","Q3","Q4"};
	
	
	
	/* Constants for parameter Names */
	public static final String ONE_OF_GIVEN = "one-of-the-given";
	public static final String EXCEPT_GIVEN = "except-given";
	public static final String GIVEN_RANGE = "given-range";
	public static final String RANGE_STARTING_POINT = "rangestart";
	public static final String RANGE_ENDING_POINT = "rangeend";
	public static final String LITERAL = "literal";
	public static final String SENTENCE = "sentence";
	
	// use the following constants for number parameters
	public static final String NMIN = "min";
	public static final String NMAX = "max";
	public static final String NUMBER_SEPERATOR = "separator";
	public static final String NO_OF_DECIMAL_PLACES = "decplaces";
	
	// use the number constants and as well as following 2 constants for CURRENCY suffix and prefix
	
	public static final String PREFIX ="prefix";
	public static final String SUFFIX =  "suffix";
	public static final String TEXT_LENGTH = "";
	
	// random date between range
	// What could be the date format
	public static final String START_DATE = "startDate";
	public static final String END_DATE = "endDate";
	public static final String 	GIVEN_DATE = "givenDate";
	
	
	//possible token constants
	public static final String IN_DATE_PATTERN = "inDatepattern";
	public static final String OUT_DATE_PATTERN = "outDatepattern";
	
	public static final String TIME_ZONE = "tzpattern";
	
	
	// refer patterns in http://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html	
	
	
	
	
	
		 						

		 
													
	

	
}
