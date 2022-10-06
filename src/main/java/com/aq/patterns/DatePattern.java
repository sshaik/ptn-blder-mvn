package com.aq.patterns;


import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)  
public class DatePattern 
{
	private String name;
	
	HashMap<String, String> dateMap = new HashMap<String, String>();
	private List <Block> blocks;
	

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public HashMap<String, String> getDateMap() {
		return dateMap;
	}
	public void setDateMap(HashMap<String, String> dateMap) {
		this.dateMap = dateMap;
	}
	public List<Block> getBlocks() {
		return blocks;
	}
	public void setBlocks(List<Block> blocks) {
		this.blocks = blocks;
	}
} 