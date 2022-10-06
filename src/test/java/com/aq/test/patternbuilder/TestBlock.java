package com.aq.test.patternbuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aq.patterns.Block;
import com.aq.patterns.Pattern;
import com.aq.patterns.PatternGenerator;
import com.aq.patterns.Token;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static com.aq.patterns.TokenConstants.*;


public class TestBlock {
	
	private static final Logger logger = LoggerFactory.getLogger(TestBlock.class); 
	
	
	@Test
	public void smokeTest()
	{
		assert(true);
		
	}
	
	@Test
	public void testGenerateJson() throws IOException
	{
		
		
		List<Block> blocks = new ArrayList<Block>();
		
		Block b1 = new Block();
		b1.setName("block-"+UUID.randomUUID());
	//	b1.setCondition(null);
		
		
		
		Token p1 = getPattern("alpha", "resymbol",10,15, null);
		ObjectMapper om = new ObjectMapper();
		Token p2 = getPattern("digit", "resymbol", 8,12, null);
		
		List<Token> b1PatternList = new ArrayList<Token>();
		
		b1PatternList.add(p1);
		b1PatternList.add(p2);
		b1.setTokens(b1PatternList);
		
		
		
		// 2nd block
		Block b2 = new Block();
		b2.setName("block-"+UUID.randomUUID());
		
		
		//Token1 for second block
		HashMap<String, String> hashMap2 =  new HashMap<String, String>();
		hashMap2.put(RANGE_STARTING_POINT, "M");
		hashMap2.put(RANGE_ENDING_POINT, "Y");
		
		Token p3 = getPattern("exceptGivenRange", "resymbol",3,7, hashMap2);
		List<Token> b2PatterList = new ArrayList<Token>();		
	
		b2PatterList.add(p3);
		
		
		//Token2 for second block
		
		
	
	
		
		b2.setTokens(b2PatterList);
	
		blocks.add(b1);
		blocks.add(b2);

		Pattern cp = new Pattern();
		cp.setName("cp="+UUID.randomUUID());
		cp.setBlocks(blocks);
		
		
		
		String jsonStr =om.writerWithDefaultPrettyPrinter().writeValueAsString(cp);
		String jsonStrAsPlainString = om.writeValueAsString(cp);

		System.out.println("json plain  print --> "+om.writeValueAsString(cp));
		System.out.println("json pretty print --> "+om.writerWithDefaultPrettyPrinter().writeValueAsString(cp));
		
		
		
		logger.debug(jsonStrAsPlainString);
	
		
		}
	
	
	@Test
	public void readFromString() throws IOException,JsonProcessingException,JsonMappingException
	{
		String jsonStr = "{\"name\":\"cp=841e9e3f-950f-4f2a-bbef-8958246e7c53\",\"blocks\":[{\"name\":\"block-67c5e20f-f677-4ea4-a564-106a5108f648\",\"tokens\":[{\"tokenName\":\"alpha\",\"tokenType\":\"resymbol\",\"rmin\":10,\"rmax\":15,\"parameters\":null},{\"tokenName\":\"digit\",\"tokenType\":\"resymbol\",\"rmin\":8,\"rmax\":12,\"parameters\":null}]},{\"name\":\"block-c8a9f60c-4c03-4026-83ef-1c91f21d2854\",\"tokens\":[{\"tokenName\":\"exceptGivenRange\",\"tokenType\":\"resymbol\",\"rmin\":3,\"rmax\":7,\"parameters\":{\"rangestart\":\"M\",\"rangeend\":\"Y\"}}]}]}";
		ObjectMapper om = new ObjectMapper();
		
		Pattern cp = om.readValue(jsonStr, Pattern.class);
		logger.debug(cp.getName());
	
		for (int i= 0 ; i < 100 ; i++)
		{
			logger.debug(PatternGenerator.generatePattern(cp));
		}
	}
	
	private Token getPattern(String tokenName, String tokenType, int rmin, int rmax, HashMap<String, String> map)
	{
		Token pattern = new Token();
		pattern.setTokenName(tokenName);
		pattern.setTokenType(tokenType);
		
		pattern.setRmin(rmin);
		pattern.setRmax(rmax);
		pattern.setParameters(map);
		
		return pattern;
	
		
	}
	
	
	

}
