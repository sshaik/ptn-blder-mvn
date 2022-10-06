package com.aq.test.patternbuilder;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aq.patterns.Token;
import com.aq.patterns.TokenBuilder;

import static com.aq.patterns.TokenConstants.*;



public class TestTokenBuilder {

	static final Logger logger = LoggerFactory.getLogger(TestTokenBuilder.class);
	
	@Test
	public void testMethod()
	{
		assert(1==1);
	}
	
	@Test 
	public void testAlphaWith3to5Length()
	{
		Token p = getPattern(3, 5, null, null);
		String alphaStr = TokenBuilder.alpha(p);
		logger.debug(alphaStr);
		Assert.assertTrue(2 < alphaStr.length() && alphaStr.length() < 6);
	}
	
	@Test 
	public void testLowerAlphaWith10to20Length()
	{
		File file = new File("/home/sshaik/workspace/logs/patternbuilde.txt");
		Token p = getPattern(10, 20, null, null);
		try{
		for(int i = 0 ; i < 1000 ; i++)
		{
			String alphaStr = TokenBuilder.lowerAlpha(p);
			int stringLength = alphaStr.length() ;
			Assert.assertTrue(10 <= stringLength && stringLength < 21);
			FileUtils.writeStringToFile(file, alphaStr+"\n", true);
		}
		}catch(Exception e)
		{
			logger.error("",e);
		}
		
	}
	
	@Test 
	public void testUpperAlphaWith5Length()
	{
		Token p = getPattern(5, 5, null, null);
		String alphaStr = TokenBuilder.upperAlpha(p);
		logger.debug(alphaStr);
		Assert.assertTrue(5 == alphaStr.length());
	}
	
	@Test 
	public void testDigit()
	{
		for(int i = 0 ; i< 20; i++){
			
		
		Token p = getPattern(5, 10, null, null);
		String digitStr = TokenBuilder.digit(p);
	//	logger.debug(digitStr);
		System.out.println(digitStr);
		//Assert.assertTrue(5 == digitStr.length());
		}
	}
	
	@Test
	public void testGetNewLine()
	{
		Token p = getPattern(2, 5, null, null);
		logger.debug("******* line starts here  ******* " + TokenBuilder.newLine(p) + "*** ends here ***");
	}
	
	
	@Test
	public void testspecialChars()
	{
		Token p = getPattern(3, 10, null, null);
		String specialCharStr = TokenBuilder.specialChar(p);
		logger.debug(specialCharStr);
	}
	
	
	@Test
	public void testspecialCharsWithInclusionCharsAsInput()
	{
		Token p = getPattern(3, 10, "#$%", null);
		String specialCharStr = TokenBuilder.specialChar(p);
		logger.debug(specialCharStr);
	}
	
	
	@Test
	public void testAlphaNumeric()
	{
		assert(1==2);
	}
	
	@Test 
	public void testOneOfGiven()
	{
		Token p = getPattern(3, 20, "asdffghj", null);
		//p.setTokenName("o");
		String str = TokenBuilder.oneOfTheGiven(p);
		System.out.println(str);
		//logger.debug(str);
	}
	
	@Test 
	public void testOneOfGivenWithNullValue()
	{
		Token p = getPattern(3, 20, null, null);
		String str = TokenBuilder.oneOfTheGiven(p);
			
		logger.debug(str);
	}
	
	@Test 
	public void javaPatternTest()
	{
		CharSequence charSeq = LOWER_ALPHA_STR;
		String exclusionChars = new StringBuilder().append('[').append(charSeq).append(']').toString();
		java.util.regex.Pattern p1 = java.util.regex.Pattern.compile(exclusionChars);
		String str = new String(ANY_CHARS);
		str= p1.matcher(str).replaceAll("");
		logger.debug(str);
	}
	
	@Test 
	public void testExceptGiven()
	{
		HashMap<String, String>  params = new HashMap<String, String>();
		params.put(EXCEPT_GIVEN,LOWER_ALPHA_STR);
		//params.put(RANGE_ENDING_POINT, "t");
		Token p = getPattern(20,25,params);
		p.setParameters(params);
		
		String str = TokenBuilder.exceptGiven(p);
		
		logger.debug(str);
	
		
		
		
		
	}
	
	@Test
	public void testReflectionForAlpha() throws NoSuchMethodException,InvocationTargetException, IllegalAccessException, IllegalArgumentException
	{
		//String methodName = "alpha";
		String methodName = new StringBuffer("get").append(WordUtils.capitalize("alpha")).toString();
	//	methodName = WordUtils.capitalize(methodName).trim();
		logger.debug(methodName);
		
		Method method = TokenBuilder.class.getMethod(methodName, Token.class);
		String str = (String) method.invoke(null, getPattern(3, 4, null, null));
		logger.debug(str);
		
	}
	
	@Test 
	public void testGivenRange()
	{
		// upper alpha test
		HashMap<String, String>  params = new HashMap<String, String>();
		params.put(RANGE_STARTING_POINT,"m");
		params.put(RANGE_ENDING_POINT, "t");
		Token p = getPattern(5,10,params);
		p.setParameters(params);
		
		
		String str = TokenBuilder.givenRange(p);
		logger.debug("ginve range str ==>" + str);
		
		params.clear();
		params.put(RANGE_STARTING_POINT,"D");
		params.put(RANGE_ENDING_POINT, "N");
		p.setParameters(params);
		p = getPattern(5, 6, params);
		str = TokenBuilder.givenRange(p);
		logger.debug(str);
		
		
		params.clear();
		params.put(RANGE_STARTING_POINT,"2");
		params.put(RANGE_ENDING_POINT, "7");
		p.setParameters(params);
		p = getPattern(3, 5, params);
		str = TokenBuilder.givenRange(p);
		logger.debug(str);
		
		// lower and upper mixed.
		params.clear();
		params.put(RANGE_STARTING_POINT,"n");
		params.put(RANGE_ENDING_POINT, "Z");
		p.setParameters(params);
		p = getPattern(3, 5, params);
		str = TokenBuilder.givenRange(p);
		logger.debug(str);
		
		
		params.clear();
		params.put(RANGE_STARTING_POINT,"n");
		params.put(RANGE_ENDING_POINT, "Z");
		p.setParameters(params);
		p = getPattern(3, 5, params);
		str = TokenBuilder.givenRange(p);
		logger.debug(str);
		
		// test improper combinaiton
		
		params.put(RANGE_STARTING_POINT,"Z");
		params.put(RANGE_ENDING_POINT, "N");
		p.setParameters(params);
		p = getPattern(3, 5, params);
		str = TokenBuilder.givenRange(p);
		logger.debug(str);
		
		
		
		}
	
	
	@Test
	public void testNumber()
	{
		HashMap<String, String> map = getParamsForNumber("100000.233", "200000.333", ",", "2");
		Token t = new Token();
		t.setTokenName("number");
		t.setTokenType("command");
		t.setParameters(map);
		
		System.out.println(TokenBuilder.number(t));
		
	}
	
	@Test
	public void testCurrency()
	{
		HashMap<String, String> map = getParamsForNumber("100000.233", "200000.333", ",", "2");
		Token t = new Token();
		t.setTokenName("number");
		t.setTokenType("command");
		map.put(PREFIX, "$");
		map.put(SUFFIX, "USD");
		t.setParameters(map);
		System.out.println(TokenBuilder.currencyNumber(t));	
	}
	
	@Test
	public void testUUid()
	{
		Token t = new Token();
		t.setTokenName("uuid");
		for(int i = 0 ; i<= 10 ; i++)
		System.out.println(TokenBuilder.uuid(t));
	}
	
	@Test
	public void testText()
	{
		Token t = new Token();
		HashMap<String, String> map  = new HashMap<String, String>();
		map.put(PREFIX, "start-- ");
		map.put(SUFFIX, " -- end");
		//map.put(TEXT_LENGTH, "4");
		t.setTokenName("text");
		t.setTokenType("command");
		t.setRmin(4);
		t.setRmax(4);
		
		
		t.setParameters(map);
		String text = TokenBuilder.text(t);
		System.out.println(text);
		
		
		
		
	}
	

	@Test
	public void testAlphaNumericText()
	{
		Token t = new Token();
		HashMap<String, String> map  = new HashMap<String, String>();
		map.put(PREFIX, "start-- ");
		map.put(SUFFIX, " -- end");
		//map.put(TEXT_LENGTH, "4");
		t.setTokenName("text");
		t.setTokenType("command");
		t.setRmin(4);
		t.setRmax(4);
		
		
		t.setParameters(map);
		String text = TokenBuilder.alphaNumericText(t);
		System.out.println(text);
		
		
		
		
	}
	
	
	
	
	@Test
	public void numberFormatTest()
	{
		Format format = com.ibm.icu.text.NumberFormat.getNumberInstance(new Locale("en", "us"));
		System.out.println(new String(format.format(new BigDecimal("23456.78"))));	
	}
	private HashMap<String, String> getParamsForNumber(String min, String max, String numberSeprator, String noOfDecimalPlaces)
	{
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(NMIN, min);
		map.put(NMAX,max);
		map.put(NUMBER_SEPERATOR, numberSeprator);
		map.put(NO_OF_DECIMAL_PLACES, noOfDecimalPlaces);
		
		return map;
	}
	
	
	@Test
	public void testLiteral()
	{
		Token t = new Token();
		HashMap<String , String> map = new HashMap<String, String>();
		map.put(LITERAL,"helloWorld");
		
		
	}
	
	
	@Test 
	public void testSSN(){
		
	
	StringBuilder strBuilder = new StringBuilder();
	
	//TokenBuilder.digit(getPattern(3, 3, null))
	
	}
	
	@Test
	public void  nextRandomNumber()
	{
		List <String> list = new ArrayList<String>();
		list.add("abc");
		list.add("zxc");
		list.add("223");
		list.add("323");
		list.add("423");
		list.add("523");
		list.add("623");
		list.add("723");
		
		
		for (int i = 0 ; i < 100; i++)
		{
			
			int j = RandomUtils.nextInt(0, list.size());
			logger.debug(list.get(j));
		}
	}
	
	private Token getPattern(int min, int max, HashMap<String, String> parameters)
	{
		Token pattern = new Token();
		pattern.setRmin(min);
		pattern.setRmax(max);
		pattern.setParameters(parameters);
		
		return pattern;
		
	}
		
	private Token getPattern(int min, int max, String inclusionCharStr, String exclusionChars)
	{
		Token pattern = new Token();
		pattern.setRmin(min);
		pattern.setRmax(max);
	
		return pattern;
	}
}