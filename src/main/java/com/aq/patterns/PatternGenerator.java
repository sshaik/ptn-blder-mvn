package com.aq.patterns;

import static com.aq.patterns.TokenConstants.TIME_ZONE;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;

public class PatternGenerator {
	
	private static final Logger logger = LoggerFactory.getLogger(PatternGenerator.class);
	
	/**
	 * <p>
	 * Parse the Json into Pattern object. Parse thru pattern object's blocks and return the output.
	 * <p>
	 * @param String in JSON format
	 * @return String
	 */
	public static String generatePattern(String jsonString) throws JsonProcessingException, IOException
	{
			ObjectMapper mapper = new ObjectMapper();
			ObjectReader reader = mapper.reader(Pattern.class);
			Pattern pattern = reader.readValue(jsonString);
			return generatePattern(pattern);
	}

	/**
	 * <p>
	 * Parse the Json into DatePattern object. Parse thru date pattern object's blocks and return the 
	 * date related output in string format.
	 * <p>
	 * @param String in JSON format
	 * @return String
	 */
	
	public static String generateDatePattern(String jsonString) throws JsonProcessingException, IOException
	{
			ObjectMapper mapper = new ObjectMapper();
			ObjectReader reader = mapper.reader(DatePattern.class);
			DatePattern datePattern = reader.readValue(jsonString);
			return generateDatePattern(datePattern);
	}
	
	
	

	public static String generatePattern(Pattern pattern)
	{
		//1 Extract list of blocks from compositePattern
		StringBuilder stringBuilder = new StringBuilder();	
		String stringFromSelectedToken = null;
		List<Block> blocks = new ArrayList<Block>();
		
		
		for(Block b : blocks)
		{
			List<Token> tokens = b.getTokens();
			int noOftokens = null == tokens ? 0 : tokens.size();
			
			if(noOftokens < 1)
			{
				// see if datePatternExist in current Block instead of Tokens list.
				if(null != b.getDatePattern())
				{
					stringFromSelectedToken = generateDatePattern(b.getDatePattern());
				}else{
					stringFromSelectedToken = null;
				}
			}
			
			// if only one token exist in block
			else if(1==noOftokens)
			{
				try
				{
					stringFromSelectedToken = getPatternStringFromToken(tokens.get(0));	
				}catch (NoSuchTokenException e)
				{
					e.printStackTrace();
				}
			}
			else if (noOftokens  > 1)
			{
				// randomly select one token from tokens list
				int randomTokenIndex = RandomUtils.nextInt(0, noOftokens);
				Token randomToken = tokens.get(randomTokenIndex);
				try{
					stringFromSelectedToken = getPatternStringFromToken(randomToken);	
				}catch (NoSuchTokenException e)
				{
					logger.error(e.getMessage(), e);
				}
			}else 
			{
				stringFromSelectedToken = null;
			}
			stringBuilder.append(null == stringFromSelectedToken ? "":stringFromSelectedToken).append("    ");
		}
		return  stringBuilder.toString();
	}
	
	
	
	/*
	 * All the tokens in DatePattern expected to be date based.
	 * 
	 */
	public static String generateDatePattern(DatePattern datePattern)
	{
		
		// Construct main date from DatePattern hashmap parameters.
		Token dateToken = new Token();
		dateToken.setParameters(datePattern.getDateMap());
		String date = TokenBuilder.date(dateToken); 
		
		
		//ZoneId zoneId = 
		ZoneId zoneId = ZoneId.of(datePattern.getDateMap().get(TokenConstants.TIME_ZONE));
		
		
		String inDatePattern = datePattern.getDateMap().get(TokenConstants.IN_DATE_PATTERN);
		
		/*DateTimeFormatter dateTimeFormatter = null == inDatePattern ? DateTimeFormatter.ISO_ZONED_DATE_TIME :
												DateTimeFormatter.ofPattern(inDatePattern);
		*/
		
		DateTimeFormatter inDateTimeFormatter = DateTimeFormatter.ofPattern(inDatePattern).withZone(zoneId);
		ZonedDateTime mainDateTime = ZonedDateTime.parse(date, inDateTimeFormatter); /* rest of the date time tokens will be derived from this date */
		List<Block> blocks = datePattern.getBlocks();
		
		StringBuilder stringBuilder = new StringBuilder();	
		String stringFromSelectedToken = null;
		
		for(Block b : blocks)
		{
			List<Token> tokens = b.getTokens();
			int noOftokens = tokens.size();
			
			if(noOftokens < 1)
			{
				logger.error("No tokens exist in block");
				//check if datePattern exist
				return null;
			}
			
			// if only one token exist in block
			else if(1==noOftokens)
			{
				Token token = tokens.get(0);
				try{
					stringFromSelectedToken = getDatePatternFromString(token, mainDateTime);
					
				}catch (NoSuchTokenException e)
				{
					e.printStackTrace();
				}
			}
			else if (noOftokens  > 1)
			{
				int randomTokenIndex = RandomUtils.nextInt(0, noOftokens);
				Token randomToken = tokens.get(randomTokenIndex);
				try{
					stringFromSelectedToken = getDatePatternFromString(randomToken, mainDateTime);	
				}catch (NoSuchTokenException e)
				{
					logger.error(e.getMessage(), e);
				}
			}
			stringBuilder.append(null == stringFromSelectedToken ? "":stringFromSelectedToken).append("    ");
			
		}
		
		return  null == stringBuilder ? null : stringBuilder.toString();
	}
	
	
	
	private static String getPatternStringFromToken(Token token)  throws NoSuchTokenException
	{
		
		String StringFromToken = null;
		String methodName = token.getTokenName();
		
		Method method;
		
		logger.debug("method name in getPatternStringFromToken  --> " + methodName);
		try {
			method = TokenBuilder.class.getMethod(methodName, Token.class);
			StringFromToken =   (String) method.invoke(null, token);
		}catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException 
					| SecurityException e) 
		{

				logger.error(e.getMessage(),e);
				throw new NoSuchTokenException(e.getMessage(), e);
			} 
		return StringFromToken;
	}
	
	private static String getDatePatternFromString(Token token, ZonedDateTime zonedDateTime)  throws NoSuchTokenException
	{
		String StringFromToken = null;
		String methodName = token.getTokenName();
		String pattern = token.getParameters().get(TokenConstants.OUT_DATE_PATTERN);
		logger.debug("Out pattern ---------------> "+ pattern);
		
		Method method;
		
		logger.debug("method name in getPatternStringFromToken  --> " + methodName);
		try {
			method = TokenBuilder.class.getMethod(methodName, ZonedDateTime.class,String.class);
			StringFromToken =   (String) method.invoke(null,zonedDateTime,pattern);
		}catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException 
					| SecurityException e) 
		{

				logger.error(e.getMessage(),e);
				throw new NoSuchTokenException(e.getMessage(), e);
		} 
		return StringFromToken;
	}
}
