package com.aq.patterns;

import static com.aq.patterns.TokenConstants.*;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.jmx.LoggerDynamicMBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TokenBuilder {

	static final Logger logger = LoggerFactory.getLogger(TokenBuilder.class);
	
	public static String alpha(Token pattern)
	{
		int i  = getRandonNumber(pattern);
		return RandomStringUtils.random(i, ALPHACHARS);
	}
	
	
	public static String lowerAlpha(Token pattern)
	{
		int i  = getRandonNumber(pattern);
		return RandomStringUtils.random(i, LOWER_ALPHA_CHARS);
	}
	
	
	public static String upperAlpha(Token pattern)
	{
		int i  = getRandonNumber(pattern);
		return RandomStringUtils.random(i, UPPER_ALPHA_CHARS);
	}
	
	
	public static String newLine(Token pattern)
	{
		int i  = getRandonNumber(pattern);
		StringBuilder sb = new StringBuilder();
		for (int n=0;n<=i;n++)
		{
			sb.append(System.lineSeparator());	
		}
		return sb.toString();
	}
	
	public static String digit(Token pattern)
	{
		int i  = getRandonNumber(pattern);
		return RandomStringUtils.random(i, numberChars);
		//return RandomStringUtils.randomNumeric(i);
		
	}
	
	
	
	
	public static String specialChar(Token pattern)
	{
		int i  = getRandonNumber(pattern);
		return RandomStringUtils.random(i, SPECIAL_CHARS);
	}
	
	public static String whiteSpace(Token pattern)
	{
		int i = getRandonNumber(pattern);
		return RandomStringUtils.random(i, whiteSpaceChar);
	}
	
	public static String alphaNumeric(Token pattern)
	{
		int i = getRandonNumber(pattern);
		return RandomStringUtils.randomAlphanumeric(i);
	}
	
	public static String anyChar(Token pattern)
	{
		int i = getRandonNumber(pattern);
		return RandomStringUtils.random(i,ANY_CHARS);
	}
	
	public static String anyCharNonSpace(Token pattern)
	{
		int i = getRandonNumber(pattern);
		return RandomStringUtils.random(i,anyCharWithoutSpaceChar);
	}
	
	public static String oneOfTheGiven(Token pattern)
	{
		
		HashMap<String, String> hashtable = pattern.getParameters();
		if(null == hashtable)
			return null;
		
		String oneOfgivenChars = hashtable.get(ONE_OF_GIVEN);
		
		
		int i = getRandonNumber(pattern);
		
		try{
			return RandomStringUtils.random(i,oneOfgivenChars);
		}catch(NullPointerException e)
		{
			return null;
		}
	}
	
	public static String exceptGiven(Token pattern)
	{
		
		HashMap<String,String> hashMap = pattern.getParameters();
		
		if ((null == hashMap) || (null == hashMap.get(EXCEPT_GIVEN)))
				return null;
	
		int i = getRandonNumber(pattern);
		String StringWithoutExcludedChars = null;
		String result = "";
		try{
			
			CharSequence charsSequene =  hashMap.get(EXCEPT_GIVEN);
			
			String exclusionChars = new StringBuilder().append("[").append(charsSequene).append("]").toString();
			logger.debug(exclusionChars);
			
			java.util.regex.Pattern jp = java.util.regex.Pattern.compile(exclusionChars);
			StringWithoutExcludedChars = jp.matcher(new String(ANY_CHARS)).replaceAll("");
			logger.debug(StringWithoutExcludedChars);
			result =  RandomStringUtils.random(i,StringWithoutExcludedChars);	
		}catch(NullPointerException n)
		{
			 result = null;
		}
		return result;
	}
	
	public static String givenRange(Token token)
	{
		//StringUtils.isAllUpperCase(startingChar)
		String candidateForRandomStrGen = null;
		
		if(!isValidRange(token))
		{
			return null;
		}
		
		 char startingChar = token.getParameters().get(RANGE_STARTING_POINT).charAt(0);
		 char endingChar = token.getParameters().get(RANGE_ENDING_POINT).charAt(0);
		
		 
		 
		 
		 //UPPER ALPHA
		if( Character.isUpperCase(startingChar) && Character.isUpperCase(endingChar))
		{
			 candidateForRandomStrGen = UPPER_ALPHA_STR.substring(UPPER_ALPHA_STR.indexOf(startingChar),
					UPPER_ALPHA_STR.indexOf(endingChar));
			 
		}
		// LOWER ALPHA
		else if( Character.isLowerCase(startingChar) && Character.isLowerCase(endingChar) )
		{
			 candidateForRandomStrGen = LOWER_ALPHA_STR.substring(LOWER_ALPHA_STR.indexOf(startingChar),
						LOWER_ALPHA_STR.indexOf(endingChar));
		}
		// DIGIT FROM 0 TO 9 FOR NOW. IS VALUES LIKE 234 TO 543 IS POSSIBLE? CHECK WITH MAHENDRA.
		else if( Character.isAlphabetic(startingChar) && Character.isAlphabetic(endingChar) )
		{
			 candidateForRandomStrGen = LOWER_ALPHA_STR.substring(LOWER_ALPHA_STR.indexOf(Character.toLowerCase(startingChar)),
						LOWER_ALPHA_STR.indexOf(Character.toLowerCase(endingChar)));
		}
		else if(Character.isDigit(startingChar) && Character.isDigit(endingChar))
		{
			int startingDigit = Character.getNumericValue(startingChar);
			int endingDigit = Character.getNumericValue(endingChar);
			if(startingDigit < endingDigit)
			{
				candidateForRandomStrGen = NUMBER_STR.substring(NUMBER_STR.indexOf(startingChar), NUMBER_STR.indexOf(endingChar));
			}else{
				candidateForRandomStrGen = null;
			}
		}
		else{
			candidateForRandomStrGen = null;
		}
		
		int i = getRandonNumber(token);
		return RandomStringUtils.random(i, candidateForRandomStrGen);	
	}
	
	/*
	 * Token name = exceptGivenRange
	 * Other than the given range, return any printable text.
	 */
	public static String exceptGivenRange(Token token)
	{
		
		if(!isValidRange(token))
		{
			logger.debug("Validation failed");
			return null;
		}
		String candidateForRandomStrGen = null;
		
		 char startingChar = token.getParameters().get(RANGE_STARTING_POINT).charAt(0);
		 char endingChar = token.getParameters().get(RANGE_ENDING_POINT).charAt(0);
		
		 //UPPER ALPHA
		if( Character.isUpperCase(startingChar) && Character.isUpperCase(endingChar))
		{
			 candidateForRandomStrGen = UPPER_ALPHA_STR.substring(UPPER_ALPHA_STR.indexOf(startingChar),
					UPPER_ALPHA_STR.indexOf(endingChar));
			 
			 
				String excludedRange = new StringBuilder("[").append(candidateForRandomStrGen).append(']').toString();
				java.util.regex.Pattern jp = java.util.regex.Pattern.compile(excludedRange);
				candidateForRandomStrGen = jp.matcher(new String(UPPER_ALPHA_STR)).replaceAll("");
				
		}
		// LOWER ALPHA
		else if( Character.isLowerCase(startingChar) && Character.isLowerCase(endingChar) )
		{
			 candidateForRandomStrGen = LOWER_ALPHA_STR.substring(LOWER_ALPHA_STR.indexOf(startingChar),
						LOWER_ALPHA_STR.indexOf(endingChar));
		}
		// DIGIT FROM 0 TO 9 FOR NOW. IS VALUES LIKE 234 TO 543 IS POSSIBLE? CHECK WITH MAHENDRA.
		else if( Character.isAlphabetic(startingChar) && Character.isAlphabetic(endingChar) )
		{
			 candidateForRandomStrGen = LOWER_ALPHA_STR.substring(LOWER_ALPHA_STR.indexOf(Character.toLowerCase(startingChar)),
						LOWER_ALPHA_STR.indexOf(Character.toLowerCase(endingChar)));
		}
		else if(Character.isDigit(startingChar) && Character.isDigit(endingChar))
		{
			int startingDigit = Character.getNumericValue(startingChar);
			int endingDigit = Character.getNumericValue(endingChar);
			if(startingDigit < endingDigit)
			{
				candidateForRandomStrGen = NUMBER_STR.substring(NUMBER_STR.indexOf(startingChar), NUMBER_STR.indexOf(endingChar));
			}else{
				candidateForRandomStrGen = null;
			}
		}
		else{
			candidateForRandomStrGen = null;
		}
		
		int i = getRandonNumber(token);
		return RandomStringUtils.random(i, candidateForRandomStrGen);	
	}
	
	
	public static String word(Token p)
	{
		int randomWordIndex = RandomUtils.nextInt(0, WORD_LIST.length);
		//int randomWordIndex = WORD_LIST[i]
		String word = WORD_LIST[randomWordIndex];
		return word;
		
	}
	
	public static String literal(Token token)
	{
		String literal = null == token.getParameters() ? null : token.getParameters().get(LITERAL);
		return literal;
	}
	
	public static String sentence(Token token)
	{
		//String sentence = null == token.getParameters() ? null : token.getParameters().get(SENTENCE);
		
		int randomSentenceIndex = RandomUtils.nextInt(0, SENTENCE_LIST.length);
		return SENTENCE_LIST[randomSentenceIndex];
		
	}
	
	
	
	public static String number(Token token)
	{
		double  min = 0d;
		double max = 0d;
		String separator = "";
		int noOfDecimals = 0;
		
		String result = null;
		
		if (null == token.getParameters())
			return null;
		
		try{
		
			min =  Double.parseDouble(token.getParameters().get(NMIN));
			max = Double.parseDouble(token.getParameters().get(NMAX));
			separator = token.getParameters().get(NUMBER_SEPERATOR);
			noOfDecimals = Integer.parseInt(token.getParameters().get(NO_OF_DECIMAL_PLACES));
			
			
			if(min > max)
				return null;
			
			double randomNumber = RandomUtils.nextDouble(min, max);
			logger.debug("Generated Random Number " + randomNumber);
			StringBuilder decimalPattern = new StringBuilder().append("#.");
			for (int i = 0 ; i<noOfDecimals; i++)
			{
				decimalPattern.append(0);
			}
			DecimalFormat df = new DecimalFormat(decimalPattern.toString());
			logger.debug("Decimal Format " + df.format(randomNumber));
			
			randomNumber = Double.parseDouble(df.format(randomNumber));
			//Format numberFmt = com.ibm.icu.text.NumberFormat.getNumberInstance(new Locale("en", "us")); // Should be good e
			
			NumberFormat  numberFormat = NumberFormat.getInstance(new Locale("en","US"));
			
			String randomFormattedNumberStr = numberFormat.format(new BigDecimal(randomNumber));
			
			result = randomFormattedNumberStr.replaceAll(",", separator);
			
			
			
		}catch(NullPointerException | NumberFormatException n)
		{
			logger.error("",n);
			result = null;
		}
		
		return result;
	}
	
	public static String currencyNumber(Token token)
	{
		String numberStr = number(token);
		StringBuilder strBuilder= new StringBuilder(numberStr);
		
		
		String currencyPrefix = token.getParameters().get(PREFIX);
		String currentSuffix = token.getParameters().get(SUFFIX);
		
		if(null != currencyPrefix)
			strBuilder.insert(0,currencyPrefix);
		
		if(null != currentSuffix)
			strBuilder.append(currentSuffix);
			
		return strBuilder.toString();
		
	
	}
	
	
	public static String uuid(Token t)
	{
		return UUID.randomUUID().toString();
	}
	
	public static String text(Token token)
	{
		String result = null;
		if (null == token.getParameters())
				return null;
		
		try{
			
		
		String prefix = token.getParameters().get(PREFIX);
		String suffix = token.getParameters().get(SUFFIX);
			
		StringBuilder stringBuilder = new StringBuilder();
		
		//int textLength = Integer.parseInt(token.getParameters().get(TEXT_LENGTH));
		int textLength = getRandonNumber(token);
		String randomText = RandomStringUtils.random(textLength,lowerAlphaConsonentsChars);
			
		stringBuilder.append(randomText);
			if(null != prefix)
				stringBuilder.insert(0, prefix);
			
			if(null != suffix)
				stringBuilder.append(suffix);
			
			result = stringBuilder.toString();
			
		}catch(NullPointerException | NumberFormatException n)
		{
			logger.error("",n);
			result = null;
		}
		return result;
	}
	
	public static String alphaNumericText(Token token)
	{
		if ( null == token.getParameters())
			return null;
		
		String result = null;
		try{
			
			String prefix = token.getParameters().get(PREFIX);
			String suffix = token.getParameters().get(SUFFIX);
				
			StringBuilder stringBuilder = new StringBuilder();
			
			//int textLength = Integer.parseInt(token.getParameters().get(TEXT_LENGTH));
			int textLength = getRandonNumber(token);
			String randomText = RandomStringUtils.randomAlphanumeric(textLength);
				
			stringBuilder.append(randomText);
				if(null != prefix)
					stringBuilder.insert(0, prefix);
				
				if(null != suffix)
					stringBuilder.append(suffix);
				
				result = stringBuilder.toString();
		}catch(NullPointerException n)
		{
			
		}
		return result;
	}
	
	
	
	
	/**
	 * <p>
	 * Returns Date in String format with given pattern.
	 * If giveDate exist in param, same date will be returned. Given date can be in millis. If not in millis, expected input is date in text with specified
	 * pattern.
	 * If range of dates exist (start and end), random date between start and end date will be returned.
	 * start date has to be earlier than end date. For invalid combination, start date will be considered as given date.
	 * output date format will be as per datePattern input. 
	 * default output date format will be in ISO ZONE format.
	 * For any parsing exception, date System date wit ISO Zone Date format will be returned.
	 * <p>
	 * 
	 * @param token
	 * @return date in string
	 */
	public static String date(Token token)
	{
		// the following condition returns System's date and time with zone in ISO format.
		if(null == token)
		{
			token = new Token();
			token.setParameters(new HashMap<String, String>());
			
		}else if(null != token && null == token.getParameters())
		{
			token.setParameters(new HashMap<String, String>());
		}
		
		String givenDate = token.getParameters().get(GIVEN_DATE);
		
		String startDate = token.getParameters().get(START_DATE);
		String endDate = token.getParameters().get(END_DATE);

		
		String inDatePattern = token.getParameters().get(IN_DATE_PATTERN);
		String outDatePattern = token.getParameters().get(OUT_DATE_PATTERN);
		
		ZonedDateTime randomDate = null;
		DateTimeFormatter formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME;	//default
		ZonedDateTime zstartDate = null;;
		ZonedDateTime zendDate = null;
		long days = 0;
		long randomDays = 0;
		
		ZoneId zoneId = isValidTimeZoneId(token.getParameters().get(TIME_ZONE)) ? 
				ZoneId.of(token.getParameters().get(TIME_ZONE)):ZoneId.systemDefault();
				
		
		logger.debug(" Date is constructing for timeZone --> "  + token.getParameters().get(TIME_ZONE));
		
	//	ZoneId zoneId = ZoneId.of(token.getParameters().get(TIME_ZONE));
				
		/*Condition 1. See if given date exist. given date in millis is not expected to come with incoming date pattern*/
		
		if (NumberUtils.isNumber(givenDate) && StringUtils.isEmpty(inDatePattern))
		{		
			randomDate = ZonedDateTime.from(Instant.ofEpochMilli(Long.parseLong(givenDate)).atZone(zoneId));			
		}
		else if(StringUtils.isAlphanumericSpace(givenDate) && StringUtils.isAlphanumericSpace(inDatePattern))
		{
			formatter = DateTimeFormatter.ofPattern(inDatePattern).withZone(zoneId);
			randomDate = ZonedDateTime.parse(givenDate,formatter);
		}
		else if (NumberUtils.isNumber(startDate) && NumberUtils.isNumber(endDate))
		{
			logger.debug(" start and end dates are in long number format. Dates will be derived form Epoch millis standard ");
			long lStrtDate = Long.parseLong(startDate);
			long lEndDate =  Long.parseLong(endDate);
			
			/*
			zoneId = isValidTimeZoneId(token.getParameters().get(TIME_ZONE)) ? 
					ZoneId.of(token.getParameters().get(TIME_ZONE)):ZoneId.systemDefault();
					*/
			
			
			zstartDate = ZonedDateTime.from(Instant.ofEpochMilli(lStrtDate).atZone(zoneId));
			zendDate = ZonedDateTime.from(Instant.ofEpochMilli(lEndDate).atZone(zoneId));
			
			logger.debug(" Start Date --> "+ zstartDate);
			logger.debug(" End Date --> "+ zendDate);
					
			
			// Caller need to ensure the start date is earlier than end date.
			/*
			 *
			if( 0 < zendDate.compareTo(zstartDate) )
			{
				 days = (ChronoUnit.DAYS.between(zstartDate, zendDate));
				 randomDays = RandomUtils.nextLong(0, days);
			}else
			{
				days = 0;
				randomDays = 0;
			}*/
			
			
			logger.debug("no of days between start and end " + days);	
			// randomly get date from by adding days to start date or by deducting days from end date.
			randomDate = randomDays % 2 == 0 ? zstartDate.plusDays(randomDays) : zendDate.minusDays(randomDays);
			logger.debug("random Date  --> "+ randomDate);
		}
		else if(!StringUtils.isEmpty(inDatePattern) 
					&& !StringUtils.isEmpty(startDate) 
					&& !StringUtils.isEmpty(endDate)) // see if start and given dates are in string date format.
		{
			logger.debug(" start date / end date / date pattern   -->  "+startDate + "/"+ endDate + "/" + inDatePattern);
			
			//formatter = DateTimeFormatter.ofPattern(inDatePattern).withZone(ZoneId.of("America/Chicago"));
			
			try
			{
				//DateTimeFormatter.ofPattern(format).withZone(ZoneId.of("America/Chicago"));
				formatter = DateTimeFormatter.ofPattern(inDatePattern).withZone(ZoneId.of("America/Chicago"));
				
				zstartDate = ZonedDateTime.parse(startDate, formatter);
				zendDate = ZonedDateTime.parse(endDate, formatter);
				
			}catch(DateTimeException dte)
			{
				StringBuilder errorMessage = 
						new StringBuilder("Error while parsing the start date : ")
										 .append(startDate).append(" and end date : ")
										 .append(endDate)
										 .append(" with date pattern : ")
										 .append(inDatePattern);
				
				logger.error(errorMessage.toString(),dte);
				ZonedDateTime d = ZonedDateTime.now();
				//formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME;
						
				return null;
			}
		
			if( 0 < zendDate.compareTo(zstartDate) )
			{
				
				
				 days = (ChronoUnit.DAYS.between(zstartDate, zendDate));
				 randomDays = RandomUtils.nextLong(0, days);
			}else
			{
				StringBuilder errorMessage = 
						new StringBuilder("Start Date :")
								.append(startDate)
								.append(" is greather than end date : ")
								.append(endDate)
								.append(" days difference is taken as 0 and and result date will be start date");
				
				logger.error(errorMessage.toString());
				
				days = 0;
				randomDays = 0;
			}
		
			randomDate = randomDays % 2 == 0 ? zstartDate.plusDays(randomDays) : zendDate.minusDays(randomDays);
			logger.debug("generated random Date  --> " + randomDate);
			
		}
		
		/*
		if(null != )
		{
			logger.debug("out datePattern --> " + outDatePattern);
			formatter = DateTimeFormatter.ofPattern(outDatePattern).withZone(zoneId);
		}*/
		
		
			String returnDate = formatter.format(randomDate);
			logger.debug("returning the date --> "+returnDate);
			return returnDate;
		}
	
	public static String date(ZonedDateTime date, String datePattern)
	{
		/*DateTimeFormatter formatter = null == datePattern ?
				DateTimeFormatter.ISO_ZONED_DATE_TIME : DateTimeFormatter.ofPattern(datePattern);*/
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePattern);
		return formatter.format(date);
	}
	
	
	public static String quarter(Token token)
	{
		return date(token);
	}
	
	public static String quarter(ZonedDateTime date, String quarterPattern)
	{
		DateTimeFormatter formatter = null == quarterPattern ?
				DateTimeFormatter.ofPattern("'Q'q") : DateTimeFormatter.ofPattern(quarterPattern);
		return formatter.format(date);
	}
	
	public static String month(Token token)
	{
		return date(token);
	}
	
	public static String month(ZonedDateTime date, String monthPattern)
	{
		DateTimeFormatter formatter = null == monthPattern ?
				DateTimeFormatter.ofPattern("MM") : DateTimeFormatter.ofPattern(monthPattern);
		return formatter.format(date);
	}
	

	public static String year(Token token)
	{
		return date(token);
	}
	
	
	public static String year(ZonedDateTime date, String yearPattern)
	{
		DateTimeFormatter formatter = null == yearPattern ?
				DateTimeFormatter.ofPattern("yyyy") : DateTimeFormatter.ofPattern(yearPattern);
		return formatter.format(date);
	}
	
	
	
	
	
	public static String day(Token token)
	{
		return date(token);
	}
	
	
	public static String day(ZonedDateTime date, String datePattern)
	{
		DateTimeFormatter formatter = null == datePattern ?
				DateTimeFormatter.ofPattern("dd") : DateTimeFormatter.ofPattern(datePattern);
		return formatter.format(date);
	}
	
	
	
	
	public static String dow(Token token)
	{
		return date(token);
	}
	
	public static String dow(ZonedDateTime date, String dowPattern)
	{
		DateTimeFormatter formatter = null == dowPattern ?
				DateTimeFormatter.ofPattern("EEE") : DateTimeFormatter.ofPattern(dowPattern);
		return formatter.format(date);
	}
	
	
	
	
	public static String ampm(Token token)
	{
		return date(token);
	}
	
	public static String ampm(ZonedDateTime date, String ampmpatter)
	{
		DateTimeFormatter formatter = null == ampmpatter ?
				DateTimeFormatter.ofPattern("a") : DateTimeFormatter.ofPattern(ampmpatter);
		return formatter.format(date);
	}
	
	public static String timeZone(Token token)
	
	{
		return date(token);
	}
	
	
	public static String gmtDiff(Token token)
	{
		return date(token);
	}
	
	public static String gmtDiff(ZonedDateTime date, String gmtDiffPattern)
	{
		DateTimeFormatter formatter = null == gmtDiffPattern ?
				DateTimeFormatter.ofPattern("O") : DateTimeFormatter.ofPattern(gmtDiffPattern);
		return formatter.format(date);
	}
	
	
	public static String hour(Token token)
	{
		return date(token);
	}
	
	public static String hour(ZonedDateTime date, String hourPattern)
	{
		DateTimeFormatter formatter = null == hourPattern ?
				DateTimeFormatter.ofPattern("H") : DateTimeFormatter.ofPattern(hourPattern);
		return formatter.format(date);
	}
	
	
	public static String minute(Token token)
	{
		return date(token);
	}
	
	public static String minute(ZonedDateTime date, String minutePattern)
	{
		DateTimeFormatter formatter = null == minutePattern ?
				DateTimeFormatter.ofPattern("mm") : DateTimeFormatter.ofPattern(minutePattern);
		return formatter.format(date);
	}
	
	public static String second(Token token)
	{
		return date(token);
	}
	
	public static String second(ZonedDateTime date, String secondPattern)
	{
		DateTimeFormatter formatter = null == secondPattern ?
				DateTimeFormatter.ofPattern("ss") : DateTimeFormatter.ofPattern(secondPattern);
		return formatter.format(date);
	}
	
	public static String milliSecond(Token token)
	{
		return date(token);
	}
	
	public static String milliSecond(ZonedDateTime date, String milliSecondPattern)
	{
		DateTimeFormatter formatter = null == milliSecondPattern ?
				DateTimeFormatter.ofPattern("S") : DateTimeFormatter.ofPattern(milliSecondPattern);
		return formatter.format(date);
	}
	
	
	
	
	
	public static String eraDesignator(Token token)
	{
		return date(token);
	}
	
	public static String eraDesignator(ZonedDateTime date, String eraDesignatorPattern)
	{
		DateTimeFormatter formatter = null == eraDesignatorPattern ?
				DateTimeFormatter.ofPattern("G") : DateTimeFormatter.ofPattern(eraDesignatorPattern);
		return formatter.format(date);
	}
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	private static boolean isValidRange(Token token)
	{
		boolean isValid = false;
		boolean isAlpha = false;
		boolean isDigit = false;
		char startingChar=' ';
		char endingChar = ' ';
		
		HashMap<String, String> params = null == token.getParameters() ? null: token.getParameters();
		
		if (null != token.getParameters() && params.containsKey(RANGE_STARTING_POINT) && params.containsKey(RANGE_ENDING_POINT))
		{
			startingChar = token.getParameters().get(RANGE_STARTING_POINT).charAt(0);
			endingChar = token.getParameters().get(RANGE_ENDING_POINT).charAt(0);
		}else
		{
			logger.error("either token is null or no expected params exist");
			return false;
		}
		
		
		if(Character.isAlphabetic(startingChar) && Character.isAlphabetic(endingChar))
		{
			isValid = true;
			isAlpha = true;
		}else if (Character.isDigit(startingChar) && Character.isDigit(endingChar))
		{
			isValid = true;
			isDigit = true;
		}else
		{
			logger.error("start and ending range chars are eihter numbers or alpha");
			return false;
		}
		
		
		
		if(isValid && isAlpha)
		{
			if(LOWER_ALPHA_STR.indexOf(Character.toLowerCase(startingChar)) <
						LOWER_ALPHA_STR.indexOf(Character.toLowerCase(endingChar)))
				isValid = true;
			else
			{
				logger.error("starting char is greather than ending char in range");
				isValid = false;
			}
		}else if (isValid && isDigit)
		{
			if(NUMBER_STR.indexOf(startingChar) < NUMBER_STR.indexOf(endingChar))
				isValid = true;
			else
			{
				logger.error("starting number is greathr than ending number in range");
				isValid = false;
			}
		}else{
			isValid = false;
		}
		
		return isValid;
	}
	
	private static boolean isValidParams(Token token,List<String> expectedParmas)
	{
		boolean isValid = true;
		HashMap<String, String> paramsMap = token.getParameters();

		try{		
			for(String param : expectedParmas)
			{
				if (null == (paramsMap.get(param)))
				{
					isValid = false;
					break;
				}
			}
		}catch(NullPointerException ne)
		{
			isValid = false;
		}
		return isValid;
	}
	
	
	private static int getRandonNumber(Token token)
	{
		int i = RandomUtils.nextInt(token.getRmin(), token.getRmax()+1); // max is excluded as per the API		
		return i;
	}
	
	private static boolean isValidTimeZoneId(String zoneId)
	
	{
		return ZoneId.getAvailableZoneIds().contains(zoneId);	
	}
	
	private static int validateStartAndEndDates(ZonedDateTime startDate, ZonedDateTime endDate)
	{
		int i = startDate.compareTo(endDate);
		System.out.println(" Comparison result --> " + i);
		
		return i;
		
	}
	
	
}
