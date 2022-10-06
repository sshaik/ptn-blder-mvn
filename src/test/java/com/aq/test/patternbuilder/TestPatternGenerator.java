package com.aq.test.patternbuilder;


import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aq.patterns.DatePattern;
import com.aq.patterns.Pattern;
import com.aq.patterns.Block;
import com.aq.patterns.PatternGenerator;
import com.aq.patterns.Token;
import com.aq.patterns.TokenBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static com.aq.patterns.TokenConstants.*;

public class TestPatternGenerator {
	
	private static final Logger logger = LoggerFactory.getLogger(TestPatternGenerator.class);
	
	public List<String> tokenNamesList = new ArrayList<String>();
	

	@Test 
	public void testOneBlockOneToken()
	{
		try{
		JsonStringOrCompositeClass jscc = getCompositePatternJsonStr(1,1,1, 1);
		logger.debug(jscc.getJsonStr());
		logger.debug(PatternGenerator.generatePattern(jscc.getCp()));
		}catch(JsonProcessingException j)
		{
			logger.error(" ***** ERRROR  *************");
		}
		
	}
	
	
	@Test 
	public void test1Block3Token() 
	{
		
		try{
			File file = new File("/home/shakeel/workspace/logs/patternbuilde.txt");
			for(int i=0; i<100; i++){
			JsonStringOrCompositeClass jscc = getCompositePatternJsonStr(1,1,1, 3);
			String result = PatternGenerator.generatePattern(jscc.getCp());
			FileUtils.writeStringToFile(file, result,true);
			FileUtils.writeStringToFile(file, "\n",true);
		//	logger.debug(CompositePatternGenerator.generatePattern(jscc.getCp()));
			}
		}
			catch(JsonProcessingException j)
		{
			logger.error(" ***** ERRROR  *************");
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		
		
	}
	
	@Test 
	public void test2Block4Tokens()
	{
		File file = new File("/home/sshaik/workspace/logs/patternbuider.txt");
		
		try{
		JsonStringOrCompositeClass jscc = getCompositePatternJsonStr(2,4,2,4);
		logger.debug(jscc.getJsonStr());
		

		for(int i = 0; i< 100000; i++)
		{
			//FileUtils.writeStringToFile(file, CompositePatternGenerator.generatePattern(jscc.getCp())+"\n", true);
		logger.debug(PatternGenerator.generatePattern(jscc.getCp()));
		}
		FileUtils.writeStringToFile(file,new Date().toString());
		}catch(JsonProcessingException j)
		{
			logger.error(" ***** ERRROR  *************");
		}catch(Exception e)
		{
			logger.error("",e);
		}
		
		
	}
	
	
	/*
	 * 
	 * 
	 */
	private JsonStringOrCompositeClass getCompositePatternJsonStr(
			int minNoOfBlocks, int maxNoOfBlocks,
			int minTokensInBlock,
			int maxTokensInBlock) throws JsonProcessingException
	{
		Pattern cp = new Pattern();
	
		int randomNoOfBlocks = 0;
		int randomNoOfTokensInBlock = 0;
		
		if (maxNoOfBlocks < 1 )
		{
			minNoOfBlocks = 1;
			maxNoOfBlocks = 3;
		}
		if(maxTokensInBlock < 1)
		{
			minTokensInBlock = 1;
			maxTokensInBlock = 3;
		}
		
		
		randomNoOfBlocks =  RandomUtils.nextInt(minNoOfBlocks, maxNoOfBlocks+1);
		
		
		List<Block> patternBlockList = new ArrayList<Block>();
		for (int i = 0 ; i < randomNoOfBlocks ; i++)
		{
			Block patternBlock = new Block();
			randomNoOfTokensInBlock =  RandomUtils.nextInt(minTokensInBlock, maxTokensInBlock+1);
			
			List <Token> tokens = new ArrayList<Token>();
			for (int rnt = 0 ; rnt < randomNoOfTokensInBlock ; rnt++)
			{
				int randomTokenNameIndex =  RandomUtils.nextInt(0, tokensNames().size()) ;
				String tokenName = tokensNames().get(randomTokenNameIndex);
				tokens.add(getToken(tokenName));
			}
			patternBlock.setTokens(tokens);
			patternBlock.setName("pattern Blokc #" + i);
			
			patternBlockList.add(patternBlock);
		}
		cp.setBlocks(patternBlockList);
		
	
		JsonStringOrCompositeClass jsc = new JsonStringOrCompositeClass();
		
		
		
		cp.setName(UUID.randomUUID().toString());
		jsc.setCp(cp);
		ObjectMapper om = new ObjectMapper();

		String jsonStr = om.writerWithDefaultPrettyPrinter().writeValueAsString(cp);
		jsc.setJsonStr(jsonStr);
		return jsc;
		
	}
	
	@Test
	public void testDatePatternJsonString() throws JsonProcessingException
	
	{
		Pattern compositePattern = new Pattern();
		List <Block> blocks = new ArrayList<Block>();
		List <Token> tokens1forBlock1 = new ArrayList<Token>();
		Token  t = getToken("alpha");
		tokens1forBlock1.add(t);
		
		t= new Token();
		t = getToken("digit");
		tokens1forBlock1.add(t);
		
		Block block = new Block();
		block.setTokens(tokens1forBlock1);
		blocks.add(block);
		
		// block2
		List <Token> tokens1forBlock2 = new ArrayList<Token>();
		Token t2 = new Token();
		t2 = getToken("date");
		tokens1forBlock2.add(t2);
		Block block2 = new Block();
		block2.setName("block2");
		block2.setTokens(tokens1forBlock2);
		//System.out.println(t2.getParameters().get("START_DATE"));
		blocks.add(block2);
		
		
		//block3
		
				List <Token> tokens1forBlock3 = new ArrayList<Token>();
				Token t3 = new Token();
				t3 = getToken("year");
				tokens1forBlock3.add(t3);
				Block block3 = new Block();
				block3.setName("block3");
				block3.setTokens(tokens1forBlock3);
				
				//System.out.println(t2.getParameters().get("START_DATE"));
				
				blocks.add(block3);
				
				//block4
				
				List <Token> tokens1forBlock4 = new ArrayList<Token>();
				Token t4 = new Token();
				t4 = getToken("month");
				tokens1forBlock4.add(t4);
				Block block4 = new Block();
				block4.setName("block4");
				block4.setTokens(tokens1forBlock4);
				
				//System.out.println(t2.getParameters().get("START_DATE"));
				
				blocks.add(block4);
				
				
				List <Token> tokens1forBlock5 = new ArrayList<Token>();
				Token t5 = new Token();
				t5 = getToken("day");
				tokens1forBlock5.add(t5);
				Block block5 = new Block();
				block5.setName("block5");
				block5.setTokens(tokens1forBlock5);
				//System.out.println(t2.getParameters().get("START_DATE"));
				blocks.add(block5);		
				
				
				
				System.out.println(System.getProperty("java.version"));
				
				
		compositePattern.setBlocks(blocks);
		
		
		
		
				ObjectMapper om = new ObjectMapper();
				//jackson inialize objectReader
		//	ObjectWriter objectWriter =  om.

		
		String jsonStr = om.writerWithDefaultPrettyPrinter().writeValueAsString(compositePattern);
		
		System.out.println(jsonStr);
		

	}
	
	
	@Test
	public void testDatePattern()
	{
		DatePattern pattern = new DatePattern();
		HashMap<String, String> hashMap = new HashMap<String, String>();
		String startDate = new Long(ZonedDateTime.of(1980, 8, 12, 22, 18, 5, 0, ZoneId.systemDefault()).toEpochSecond() * 1000).toString();
		String endDate = new Long(ZonedDateTime.of(2000, 2, 25, 10, 18, 5, 0, ZoneId.systemDefault()).toEpochSecond() * 1000).toString();
		
		
		hashMap.put(START_DATE,startDate);
		hashMap.put(END_DATE, endDate);
		
		pattern.setDateMap(hashMap);
		
		
		
		
		List<Block> blocks = new ArrayList<Block>();
		Token token = new Token();
		token.setTokenName("date");
		HashMap<String, String> dateMap = new HashMap<String, String>();
		dateMap.put(OUT_DATE_PATTERN, "yyyy MMMM dd");
		token.setParameters(dateMap);
		Block dateBlock = new Block();
		List<Token> tokens = new  ArrayList<Token>();
		tokens.add(token);
		dateBlock.setTokens(tokens);
		blocks.add(dateBlock);
		
		
	//	List<Block> dateBlocks = new ArrayList<Block>();
		token = new Token();
		token.setTokenName("year");
		dateMap = new HashMap<String, String>();
		dateMap.put(OUT_DATE_PATTERN, "yyyy");
		token.setParameters(dateMap);
		Block yearBlock = new Block();
		//tokens.clear();
		tokens = new  ArrayList<Token>();
		tokens.add(token);
		yearBlock.setTokens(tokens);
		blocks.add(yearBlock);
		
		

		
				
		
		
		
		// month
		token = new Token();
		token.setTokenName("month");
		dateMap = new HashMap<String, String>();
		dateMap.put(OUT_DATE_PATTERN, "MMM");
		token.setParameters(dateMap);
		dateBlock = new Block();
		
		tokens = new  ArrayList<Token>();
		tokens.add(token);
		dateBlock.setTokens(tokens);
		blocks.add(dateBlock);
		
		
		pattern.setBlocks(blocks);
		String  str = PatternGenerator.generateDatePattern(pattern);
		System.out.println(str);
		
		
		
		

		// day
		token = new Token();
		token.setTokenName("month");
		dateMap = new HashMap<String, String>();
		dateMap.put(OUT_DATE_PATTERN, "dd");
		token.setParameters(dateMap);
		dateBlock = new Block();
		tokens.clear();
		//tokens = new  ArrayList<Token>();
		tokens.add(token);
		dateBlock.setTokens(tokens);
		blocks.add(dateBlock);
		
		
		

		// dow
		token = new Token();
		token.setTokenName("dow");
		dateMap = new HashMap<String, String>();
		dateMap.put(OUT_DATE_PATTERN, "EEEE");
		token.setParameters(dateMap);
		dateBlock = new Block();
		tokens.clear();
		//tokens = new  ArrayList<Token>();
		tokens.add(token);
		dateBlock.setTokens(tokens);
		blocks.add(dateBlock);
		
		
		
		
		// date of the week.
	
		token = new Token();
		token.setTokenName("dow");
		dateMap = new HashMap<String, String>();
		dateMap.put(OUT_DATE_PATTERN, "E");
		token.setParameters(dateMap);
		 dateBlock = new Block();
		 tokens.clear();
		//tokens = new  ArrayList<Token>();
		tokens.add(token);
		dateBlock.setTokens(tokens);
		dateBlock.setTokens(tokens);
		blocks.add(dateBlock);
		
		
		
		

		// hour of the day.
		 token = new Token();
		token.setTokenName("dow");
		dateMap = new HashMap<String, String>();
		dateMap.put(OUT_DATE_PATTERN, "E");
		token.setParameters(dateMap);
		 dateBlock = new Block();
		 tokens.clear();
		//tokens = new  ArrayList<Token>();
		tokens.add(token);
		dateBlock.setTokens(tokens);
		blocks.add(dateBlock);
		
		
		
		pattern.setBlocks(blocks);
		
		
		//String  str = PatternGenerator.generateDatePattern(pattern);
	//	System.out.println(str);
		
	}
	
	
	
	@Test
	public void testUSIndipendenceMsg() throws JsonProcessingException
	{
		String tz = "America/Detroit";
		ZonedDateTime givenDate = ZonedDateTime.of
				(1776, 7, 4, 01, 01, 01, 0, ZoneId.of(tz));
		long timeInMillis = givenDate.toEpochSecond() * 1000;
		String  givenDateStr = new Long(timeInMillis).toString();
		
		System.out.println(timeInMillis);
		
		Pattern pattern = new Pattern();
		pattern.setName("US Independence.");
		
		List<Block> blocks = new ArrayList<Block>();
		List<Token> tokens = new ArrayList<Token>();
		
		Token t = new Token();
		Block b = new Block();
		
		t.setTokenName(LITERAL);
		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put(LITERAL,"The independence day of USA is ");
		t.setParameters(hashMap);
		tokens.add(t);
		b.setTokens(tokens);
		blocks.add(b);
		
		// construct year
		t= new Token();
		t.setTokenName("year");
		hashMap = new HashMap<String, String>();
		hashMap.put(GIVEN_DATE, givenDateStr);
		hashMap.put(OUT_DATE_PATTERN, "yyyy");
		hashMap.put(TIME_ZONE, tz);
		
		
		t.setParameters(hashMap);
		
		tokens = new ArrayList<Token>();
		tokens.add(t);
		b = new Block();
		b.setTokens(tokens);
		blocks.add(b);
		
		
		
		
		// construct literal
		t= new Token();
		t.setTokenName(LITERAL);
		hashMap = new HashMap<String, String>();
		hashMap.put(LITERAL, ", Month");
		t.setParameters(hashMap);
		tokens = new ArrayList<Token>();
		tokens.add(t);
		b = new Block();
		b.setTokens(tokens);
		blocks.add(b);
	
		
		
		
		
		// construct month
		t= new Token();
		t.setTokenName("month");
		hashMap = new HashMap<String, String>();
		hashMap.put(GIVEN_DATE, givenDateStr);
		hashMap.put(IN_DATE_PATTERN, "MMMM");
		hashMap.put(TIME_ZONE, tz);
		t.setParameters(hashMap);
		tokens = new ArrayList<Token>();
		tokens.add(t);
		b = new Block();
		b.setTokens(tokens);
		blocks.add(b);
		
		

		// construct literal
		t= new Token();
		t.setTokenName(LITERAL);
		hashMap = new HashMap<String, String>();
		hashMap.put(LITERAL, ", day");
		
		
		t.setParameters(hashMap);
		tokens = new ArrayList<Token>();
		tokens.add(t);
		b = new Block();
		b.setTokens(tokens);
		blocks.add(b);
				
		// construct day
		t= new Token();
		t.setTokenName("day");
		hashMap = new HashMap<String, String>();
		hashMap.put(GIVEN_DATE, givenDateStr);
		hashMap.put(OUT_DATE_PATTERN, "dd");
		hashMap.put(TIME_ZONE, tz);
		t.setParameters(hashMap);
		tokens = new ArrayList<Token>();
		tokens.add(t);
		b = new Block();
		b.setTokens(tokens);
		blocks.add(b);
	
		
		
		
		// construct literal
		t= new Token();
		t.setTokenName(LITERAL);
		hashMap = new HashMap<String, String>();
		hashMap.put(LITERAL, ",");
		t.setParameters(hashMap);
		tokens = new ArrayList<Token>();
		tokens.add(t);
		b = new Block();
		b.setTokens(tokens);
		blocks.add(b);
		
		
		t= new Token();
		t.setTokenName("dow");
		hashMap = new HashMap<String, String>();
		hashMap.put(GIVEN_DATE, givenDateStr);
		hashMap.put(OUT_DATE_PATTERN, "EEEE");
		hashMap.put(TIME_ZONE, tz);
		t.setParameters(hashMap);
		tokens = new ArrayList<Token>();
		tokens.add(t);
		b = new Block();
		b.setTokens(tokens);
		blocks.add(b);
		
		
		
		// construct literal
				t= new Token();
				t.setTokenName(LITERAL);
				hashMap = new HashMap<String, String>();
				hashMap.put(LITERAL, ". It is ");
				t.setParameters(hashMap);
				tokens = new ArrayList<Token>();
				tokens.add(t);
				b = new Block();
				b.setTokens(tokens);
				blocks.add(b);
				
				
				t= new Token();
				t.setTokenName("day");
				hashMap = new HashMap<String, String>();
				hashMap.put(GIVEN_DATE, givenDateStr);
				hashMap.put(OUT_DATE_PATTERN, "D 'th day'");
				hashMap.put(TIME_ZONE, tz);
				t.setParameters(hashMap);
				tokens = new ArrayList<Token>();
				tokens.add(t);
				b = new Block();
				b.setTokens(tokens);
				blocks.add(b);
				

				t= new Token();
				t.setTokenName("year");
				hashMap = new HashMap<String, String>();
				hashMap.put(GIVEN_DATE, givenDateStr);
				hashMap.put(OUT_DATE_PATTERN, "'of the year :' yyyy");
				hashMap.put(TIME_ZONE, tz);
				t.setParameters(hashMap);
				tokens = new ArrayList<Token>();
				tokens.add(t);
				b = new Block();
				b.setTokens(tokens);
				blocks.add(b);
				
				t= new Token();
				t.setTokenName("quarter");
				hashMap = new HashMap<String, String>();
				hashMap.put(GIVEN_DATE, givenDateStr);
				hashMap.put(OUT_DATE_PATTERN, "' and quarter is Q'Q");
			//	hashMap.put(TIME_ZONE, "Asia/Calcutta");
				t.setParameters(hashMap);
				tokens = new ArrayList<Token>();
				tokens.add(t);
				b = new Block();
				b.setTokens(tokens);
				blocks.add(b);
				
				
				
				
								
				pattern.setBlocks(blocks);
				
		ObjectMapper ob = new ObjectMapper();
		String jsonStr = ob.writerWithDefaultPrettyPrinter().writeValueAsString(pattern);
		
		logger.debug(jsonStr);
				

		logger.debug(PatternGenerator.generatePattern(pattern));
	
		
	}
	
	
	@Test
	public void testDatePatternInMainPattern() throws IOException
	{
		Pattern pattern = new Pattern();
		pattern.setName("text and dates");
		
		
	    DatePattern datePattern = new DatePattern();
	    datePattern.setName("dob pattern");
	    
		HashMap<String, String> hashMap = new HashMap<String, String>();
		String startDate = new Long(ZonedDateTime.of(2000, 8, 12, 22, 18, 5, 0, ZoneId.systemDefault()).toEpochSecond() * 1000).toString();
		String endDate = new Long(ZonedDateTime.of(2014, 12, 25, 10, 18, 5, 0, ZoneId.systemDefault()).toEpochSecond() * 1000).toString();

		hashMap.put(START_DATE,startDate);
		hashMap.put(END_DATE, endDate);
		
		datePattern.setDateMap(hashMap);
		
		List<Block> blocks = new ArrayList<Block>();
		
		Token token = new Token();
		token.setTokenName("date");
		HashMap<String, String> dateMap = new HashMap<String, String>();
		dateMap.put(OUT_DATE_PATTERN, "yyyy MMMM dd");
		token.setParameters(dateMap);
		Block dateBlock = new Block();
		dateBlock.setName("block for date pattern");
		List<Token> tokens = new  ArrayList<Token>();
		tokens.add(token);
		dateBlock.setTokens(tokens);
		blocks.add(dateBlock);
		
		// Set blocks to date pattern
		datePattern.setBlocks(blocks);
		
		
		// set pattern to outer block.
		Block dstePatternBlock = new Block();
		dstePatternBlock.setDatePattern(datePattern);
		
	
		// outer blocks.
		
		List<Block> outerblocks = new ArrayList<Block>();
		token = new Token();
		token.setTokenName(LITERAL);
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put(LITERAL, "your date of birth is");
		token.setParameters(parameters);
		
		tokens = new ArrayList<Token>();
		tokens.add(token);
		Block literalBlock = new Block();
		literalBlock.setName("dob");
		literalBlock.setTokens(tokens);
		
		// add dob blokc
		outerblocks.add(literalBlock);
		
		// add block with pattern
		outerblocks.add(dstePatternBlock);
		
		token = new Token();
		token.setTokenName(LITERAL);
		parameters = new HashMap<String, String>();
		parameters.put(LITERAL, "celebrate your day");
		token.setParameters(parameters);
		tokens = new ArrayList<Token>();
		tokens.add(token);
		literalBlock = new Block();
		literalBlock.setName("dob wish");
		literalBlock.setTokens(tokens);
		
		// add wishes block
		outerblocks.add(literalBlock);
		
		// set outerblocks to pattern.
		pattern.setBlocks(outerblocks);
	
		String jsonStr = new ObjectMapper().writerWithDefaultPrettyPrinter()
				.writeValueAsString(pattern);
		System.out.println(PatternGenerator.generatePattern(jsonStr));
		// Construct Date Pattern
	}
	
	@Test
	public void testDateWithSingleToken() throws IOException
	{
		String tz = "America/Detroit";
		//ZonedDateTime givenDate = ZonedDateTime.of(1776, 7, 4, 01, 01, 01, 0, ZoneId.of(tz));
	//	long timeInMillis = givenDate.toEpochSecond() * 1000;
	//	String  givenDateStr = new Long(timeInMillis).toString();
		
		DatePattern pattern = new DatePattern();
		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put(START_DATE,"2002 12 12 10:10:10");
		hashMap.put(END_DATE,"2014 12 12 10:10:10");
		hashMap.put(TIME_ZONE,"America/Chicago");
		hashMap.put(IN_DATE_PATTERN,"yyyy MM dd HH:mm:ss");
		
		
		pattern.setDateMap(hashMap);
		
		List<Block> blocks = new ArrayList<Block>();
		List<Token> tokens = new ArrayList<Token>();
		Block b = new Block();
		//String datePatternStr = "'Random date is' yyyy 'month ' MMMM 'and day ' dd  'and dow is' EEEE ";
		String datePatternStr = "yyyy-MMMM-dd HH:MM:ss";
		Token dateToken = new Token();
		dateToken.setTokenName("date");
		HashMap<String, String> tokenMap = new HashMap<String, String>();
		tokenMap.put(OUT_DATE_PATTERN, datePatternStr);
		tokenMap.put(TIME_ZONE,"America/Chicago");
		dateToken.setParameters(tokenMap);
	
		
		tokens.add(dateToken);
		b.setTokens(tokens);
		blocks.add(b);
		pattern.setBlocks(blocks);
		
		String jsonStr = new ObjectMapper().writerWithDefaultPrettyPrinter()
				.writeValueAsString(pattern);
		System.out.println(jsonStr);
		
		System.out.println("2333");
		System.out.println(PatternGenerator.generateDatePattern(jsonStr));
	}
	
	
	private List<String> tokensNames()
	{
		List<String> tokens = new ArrayList<String>();
		tokens.add("alpha");
		tokens.add("upperAlpha");
		tokens.add("lowerAlpha");
		tokens.add("digit");
		tokens.add("givenRange");
		tokens.add("exceptGivenRange");
		tokens.add("oneOfTheGiven");
		return tokens;
	}
	
	private Token getToken(String tokenName)
	{
		int rmin  = RandomUtils.nextInt(5, 10);
		int rmax = RandomUtils.nextInt(10, 15);
		Token token = new Token();
		
		switch(tokenName)
		{
			case "alpha":
			case "upperAlpha":
			case "lowerAlpha":
			case "digit":
			case "specialChar":
			{
				//Token alphaToken = new Token();
				token.setTokenName(tokenName);
				token.setRmin(rmin);
				token.setRmax(rmax);
				token.setTokenType("reSymbol");
				break;
			}
			case "givenRange":
			{
				token.setTokenName(tokenName);
				token.setRmin(rmin);
				token.setRmax(rmax);
				token.setTokenType("reSymbol");
				HashMap<String, String> params = new HashMap<String,String>();
				params.put(RANGE_STARTING_POINT, "a");
				params.put(RANGE_ENDING_POINT, "n");
				token.setParameters(params);
				break;
			}
			case "exceptGivenRange":
			{
				
				token.setTokenName(tokenName);
				token.setRmin(rmin);
				token.setRmax(rmax);
				token.setTokenType("reSymbol");
				HashMap<String, String> params = new HashMap<String,String>();
				params.put(RANGE_STARTING_POINT, "2");
				params.put(RANGE_ENDING_POINT, "9");
				token.setParameters(params);
				break;
			}
			case "oneOfTheGiven":
			{
				token.setTokenName(tokenName);
				token.setRmin(rmin);
				token.setRmax(rmax);
				token.setTokenType("reSymbol");
				HashMap<String, String> params = new HashMap<String,String>();
				params.put(ONE_OF_GIVEN, "234567");
				token.setParameters(params);
				break;
				
			}
			case "date":
			{
				System.out.println(" ---- date");
				token.setTokenName(tokenName);
				token.setTokenType("command");
				HashMap<String, String> params = new HashMap<String,String>();
				
				SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
				String startDateInString = "08-12-2001";
				String endDateInString = "01-12-2014";
				try{
				Date startdate = sdf.parse(startDateInString);
				Date endDate = sdf.parse(endDateInString);
				params.put("START_DATE", new Long(startdate.getTime()).toString());
				params.put("END_DATE", new Long(endDate.getTime()).toString());
				
				token.setParameters(params);
				
				System.out.println("dfdfdfdf");

				}catch(ParseException p)
				{
					System.out.println("******************");
				}
				
				break;
				
			}
			case "year" : 
			{
				token.setTokenName(tokenName);
				token.setTokenType("command");
				HashMap<String, String> params = new HashMap<String,String>();
				params.put("type","YY");
				token.setParameters(params);
				break;
			}
			
			case "month":
			{
				token.setTokenName(tokenName);
				token.setTokenType("command");
				HashMap<String, String> params = new HashMap<String,String>();
				params.put("type","MM");
				token.setParameters(params);
				break;
			}
			
			case "hour":
			{
				token.setTokenName(tokenName);
				HashMap<String, String> params = new HashMap<String,String>();
				params.put("type","HH");
				token.setParameters(params);
				break;
				
			}
			case "minute":
			{
				token.setTokenName(tokenName);
				HashMap<String, String> params = new HashMap<String,String>();
				params.put("type","mm");
				token.setParameters(params);
				break;
			}
			
			case "day":
			{
				token.setTokenName(tokenName);
				token.setTokenType("command");
				HashMap<String, String> params = new HashMap<String,String>();
				params.put("type","DD");
				token.setParameters(params);
				break;
				
			}case "dow":
			{
				token.setTokenName(tokenName);
				token.setTokenType("command");
				HashMap<String, String> params = new HashMap<String,String>();
				params.put("type","HH");
				token.setParameters(params);
				
				break;
			}
				
					
			default:
				token = null;
		}
		return token;
		
		
	}
	
	class JsonStringOrCompositeClass
	{
		String jsonStr;
		Pattern cp;
		public String getJsonStr() {
			return jsonStr;
		}
		public void setJsonStr(String jsonStr) {
			this.jsonStr = jsonStr;
		}
		public Pattern getCp() {
			return cp;
		}
		public void setCp(Pattern cp) {
			this.cp = cp;
		}
		
		
		
	}
	
	
	
}



