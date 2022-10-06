package com.aq.patterns;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)  
public class Block {
	
	
	private String name;
	private List<Token> tokens;
	
	
	private DatePattern datePattern;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	} 	
	
	public List<Token> getTokens() {
		return tokens;
	}

	public void setTokens(List<Token> tokens) {
		this.tokens = tokens;
	}

	public DatePattern getDatePattern() {
		return datePattern;
	}

	public void setDatePattern(DatePattern datePattern) {
		this.datePattern = datePattern;
	}
	
	
}
