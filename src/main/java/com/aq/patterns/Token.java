package com.aq.patterns;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


//@JsonInclude(Include.NON_NULL)  

public class Token 
{	
	private String tokenName;
	private String tokenType;
	private int rmin;
	private int rmax;
	
	
	
	
	HashMap<String, String> parameters;
		
	public String getTokenName() {
		return tokenName;
	}
	public void setTokenName(String tokenName) {
		this.tokenName = tokenName;
	}
	public String getTokenType() {
		return tokenType;
	}
	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}
	public int getRmin() {
		return rmin;
	}
	public void setRmin(int rmin) {
		this.rmin = rmin;
	}
	public int getRmax() {
		return rmax;
	}
	public void setRmax(int rmax) {
		this.rmax = rmax;
	}
	public HashMap<String, String> getParameters() {
		return parameters;
	}
	public void setParameters(HashMap<String, String> parameters) {
		this.parameters = parameters;
	}	
}