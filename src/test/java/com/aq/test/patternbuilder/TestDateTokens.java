package com.aq.test.patternbuilder;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import com.aq.patterns.Block;
import com.aq.patterns.DatePattern;
import com.aq.patterns.Pattern;
import com.aq.patterns.PatternGenerator;
import com.aq.patterns.Token;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static com.aq.patterns.TokenConstants.*;

public class TestDateTokens {
	
	
	@Test
	public void testDate() throws JsonProcessingException
	{
		DatePattern datePattern = new DatePattern();
		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put(GIVEN_DATE,new Long(System.currentTimeMillis()).toString());
		datePattern.setDateMap(hashMap);
		
		//DateToken
		List<Block> blocks = new ArrayList<Block>();
		List<Token> tokens = new ArrayList<Token>();
		Block b = new Block();
		Token t = new Token();
		
		t.setTokenName("date");
		HashMap<String, String> tokenMap = new HashMap<String, String>();
		tokenMap.put(IN_DATE_PATTERN, "MM dd yyyy");
		t.setParameters(tokenMap);
		tokens.add(t);
		b.setTokens(tokens);
		blocks.add(b);
		
		
		
		
		// month i single char
		tokens = new ArrayList<Token>();
		b = new Block();
		t = new Token();
		t.setTokenName("month");
		tokenMap = new HashMap<String, String>();
		tokenMap.put(OUT_DATE_PATTERN, "M");
		t.setParameters(tokenMap);
		tokens = new ArrayList<Token>();
		tokens.add(t);
		b.setTokens(tokens);
		blocks.add(b);
		
		// month in 2 char
		tokens = new ArrayList<Token>();
		b = new Block();
		t = new Token();
		t.setTokenName("month");
		tokenMap = new HashMap<String, String>();
		tokenMap.put(OUT_DATE_PATTERN, "MM");
		t.setParameters(tokenMap);
		tokens = new ArrayList<Token>();
		tokens.add(t);
		b.setTokens(tokens);
		blocks.add(b);
		
		// month in short name
		tokens = new ArrayList<Token>();
		b = new Block();
		t = new Token();
		t.setTokenName("month");
		tokenMap = new HashMap<String, String>();
		tokenMap.put(OUT_DATE_PATTERN, "MMM");
		t.setParameters(tokenMap);
		tokens = new ArrayList<Token>();
		tokens.add(t);
		b.setTokens(tokens);
		blocks.add(b);
		
		// month in full name
		tokens = new ArrayList<Token>();
		b = new Block();
		t = new Token();
		t.setTokenName("month");
		tokenMap = new HashMap<String, String>();
		tokenMap.put(OUT_DATE_PATTERN, "MMMM");
		t.setParameters(tokenMap);
		tokens = new ArrayList<Token>();
		tokens.add(t);
		b.setTokens(tokens);
		blocks.add(b);
		
		
		// dow in single char
		tokens = new ArrayList<Token>();
		b = new Block();
		t = new Token();
		t.setTokenName("dow");
		tokenMap = new HashMap<String, String>();
		tokenMap.put(OUT_DATE_PATTERN, "EEEE");
		t.setParameters(tokenMap);
		tokens = new ArrayList<Token>();
		tokens.add(t);
		b.setTokens(tokens);
		blocks.add(b);
		
		
		// quarter
				tokens = new ArrayList<Token>();
				b = new Block();
				t = new Token();
				t.setTokenName("quarter");
				tokenMap = new HashMap<String, String>();
				tokenMap.put(OUT_DATE_PATTERN, "'Q'Q");
				t.setParameters(tokenMap);
				tokens = new ArrayList<Token>();
				tokens.add(t);
				b.setTokens(tokens);
				blocks.add(b);
		
		
				
				
		
		
		
		datePattern.setBlocks(blocks);		
	//	System.out.println(PatternGenerator.generateDatePattern(datePattern));
		
		
		
		
		Pattern pattern = new Pattern();
		pattern.setName("dates");
		List<Block> patternBlocks = new ArrayList<Block>();
		tokens = new ArrayList<Token>();
		t = new Token();
		b = new Block();
		t.setTokenName("alpha");
		t.setRmin(10);
		t.setRmax(20);
		tokens = new ArrayList<Token>();
		tokens.add(t);
		b.setTokens(tokens);
		patternBlocks.add(b);
	
		
		b = new Block();
		b.setDatePattern(datePattern);
		patternBlocks.add(b);
		
		pattern.setBlocks(patternBlocks);
		
		
		
		System.out.println(PatternGenerator.generatePattern(pattern));
		
		ObjectMapper ob = new ObjectMapper();
		String jsonStr = ob.writerWithDefaultPrettyPrinter().writeValueAsString(pattern);
		
		System.out.println(jsonStr);
		
	}
	
	@Test
	public void testDate1()
	{
		//String dateStr = "2014 09 03 13:59:50";
		//String format = "yyyy MM dd HH:mm:ss";

		String dateStr = "2014/09/03 10:10:10";
		String format = "yyyy/MM/dd HH:mm:ss";
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format).withZone(ZoneId.of("America/Chicago"));
		ZonedDateTime date = ZonedDateTime.parse(dateStr, dtf);
		System.out.println(date);	
	}
	
	

	
	
}
