package com.jza.utils;

import java.util.HashMap;
import java.util.Map;

public class IdentifierType {
	private Map<String, String> type;
	public IdentifierType() {                                            
		type = new HashMap<String, String>();
		type.put("value", "");
		// 数值类型
		// byte
		type.put("type1", "0");
		// short
		type.put("type2", "0");
		// int
		type.put("type3", "0");
		// long
		type.put("type4", "0");
		// float
		type.put("type5", "0");
		// double
		type.put("type6", "0");
		// char
		type.put("type7", "0");
		// boolean
		type.put("type8", "0");
		// string
		type.put("type9", "0");
		// array
		type.put("type10", "0");
		// class
		type.put("type11", "0");
		// function
		type.put("type12", "0");
		// block
		type.put("type13", "0");
		// 访问权限
		// public
		type.put("type14", "0");
		// default
		type.put("type15", "0");
		// private
		type.put("type16", "0");
		//protected
		type.put("type17", "0");
		// static
		type.put("type18", "0");
		//abstract
		type.put("type19", "0");
		//方法内的局部变量
		type.put("type20", "0");
		//
		type.put("type21", "0");
	}
	public Map<String, String> getType() {
		return type;
	}
	public void setType(Map<String, String> type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "IdentifierType [type=" + type + "]";
	}
	
	
}
