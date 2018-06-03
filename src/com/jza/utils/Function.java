package com.jza.utils;

import java.util.LinkedList;
import java.util.List;

public class Function {
	private List<List<String>> output = new LinkedList<List<String>>();
	private List<IdentifierType> identifier = new LinkedList<IdentifierType>();
	public List<IdentifierType> getIdentifier() {
		return identifier;
	}
	public void setIdentifier(List<IdentifierType> identifier) {
		this.identifier = identifier;
	}
	public List<List<String>> getOutput() {
		return output;
	}
	public void setOutput(List<List<String>> output) {
		this.output = output;
	}
	@Override
	public String toString() {
		return "Function [output=" + output + ", identifier=" + identifier
				+ "]";
	}
	
	
	
}
