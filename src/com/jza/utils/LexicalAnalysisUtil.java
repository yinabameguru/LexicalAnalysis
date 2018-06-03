package com.jza.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream.PutField;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.junit.Test;

import com.jza.main.Exception.InputException;

public class LexicalAnalysisUtil {
	//1
	public static List<String> keywords = new LinkedList<String>(Arrays.asList("abstract", "boolean", "break", "byte","case", "catch","String", "char", "class", "continue", "default", "do","double", "else", "extends", "final", "finally", "float", "for","if", "implements", "import", "instanceof", "int", "interface","long", "native", "new", "package", "private", "protected","public", "return", "short", "static", "super", "switch","synchronized", "this", "throw", "throws", "transient", "try","void", "volatile", "while","strictfp","enum","goto","const","assert","true","false"));
	//2
	public static List<IdentifierType> identifier = new LinkedList<IdentifierType>();
	//3
	public static List<Number> constants = new LinkedList<Number>();
	//4
	public static List<String> operators = new LinkedList<String>(Arrays.asList("+", "-", "*", "/", "=", ">", "<", "&", "|","!","<=",">=","!=","==","&&","||"));
	//5
	public static List<Character> delimiters = new LinkedList<Character>(Arrays.asList('\'', ',', ';', '{', '}', '(', ')', '[', ']', '_',':', '.', '"','\\'));
	//6
	public static List<String> definedKeywords = new LinkedList<String>(Arrays.asList("boolean","byte","char","class","double","float","int","long","void","enum","short","String"));
	//7
	public static List<String> charAndString = new LinkedList<String>();
	//8
	public static List<Function> functions = new LinkedList<Function>();
	
	public static List<Character> readFile(String fileName) {
		List<Character> list = new LinkedList<Character>();
        File file = new File(fileName);
        Reader reader = null;
        try {
            reader = new InputStreamReader(new FileInputStream(file));
            int tempchar;
            int tempchar2;
            while ((tempchar = reader.read()) != -1) {
            	//处理注释
            	if(((char)tempchar) == '/'){
            		tempchar2 = reader.read();
            		if(tempchar2 == -1){
            			System.out.print((char) tempchar);
                        list.add(((char)tempchar));
                        break;
            		}else if (((char)tempchar2) == '/') {
            			do {
            				tempchar = reader.read();
						} while (((char)tempchar) != '\r' && ((char)tempchar) != '\n');
					}else {
						System.out.print((char) tempchar);
	                    list.add(((char)tempchar));
	                    list.add(((char)tempchar2));
					}
            	}
                if (((char) tempchar) != '\r' && ((char) tempchar) != '\n' && ((char) tempchar) != '\t') {
//                    System.out.print((char) tempchar);
                    list.add(((char)tempchar));
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
	}
	
	public static void writeFile(String file, String conent) {
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(file, true)));
			out.write(conent + "\r\n");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static boolean isLetter(char c) {
		if ((65 <= c && c <= 90) || (97 <= c && c <= 122)) {
			return true;
		}else {
			return false;
		}
	}
	
	public static boolean isDigital(char c) {
		if (48 <= c && c <= 57) {
			return true;
		}else {
			return false;
		}
	}
	
	public static boolean isOperator(char c) {
		if (operators.contains(c + "")) {
			return true;
		}else {
			return false;
		}
	}
	
	public static List<String> charEquals(Queue<Character> input) {
		List<String> list = new LinkedList<String>();
		char temp1 = input.poll();
		char temp2 = 0;
		try {
			temp2 = input.poll();
			
		} catch (NullPointerException e) {
			
		}
		if (temp2 == '\'') {
			list.add(temp1 + "");
			list.add(temp2 + "");
		}else {
			((Deque<Character>)input).addFirst(temp2);
			((Deque<Character>)input).addFirst(temp1);
		}
		return list;
	}
	
	public static List<String> stringEquals(Queue<Character> input) throws InputException {
		List<String> list = new LinkedList<String>();
		String s = "";
		char c = 0;
		try {
			while (!((c = input.poll()) == '"')) {
				s += c;
			}
		} catch (NullPointerException e) {
			throw new InputException("输入文件有错误！！！");
		}
		list.add(s);
		list.add("\"");
		return list;
	}
	
	public static String operatorEquals(char c,Queue<Character> input) {
		String s = c + "";
		char peek = input.peek();
		switch (c) {
		case '<':
			if(peek == '='){
				input.poll();
				s = s + peek;
			}
			break;
		case '>':
			if(peek == '='){
				input.poll();
				s = s + peek;
			}
			break;
		case '=':
			if(peek == '='){
				input.poll();
				s = s + peek;
			}
			
			break;
		case '!':
			if(peek == '='){
				input.poll();
				s = s + peek;
			}
			
			break;
		case '|':
			if(peek == '|'){
				input.poll();
				s = s + peek;
			}
			break;
		case '&':
			if(peek == '&'){
				input.poll();
				s = s + peek;
			}
			break;

		default:
			break;
		}
		return s;
	}
	
	public static boolean isDelimiter(char c) {
		if (delimiters.contains(c)) {
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean isKeywords(String s) {
		if (keywords.contains(s)) {
			return true;
		}else {
			return false;
		}
	}
	
	public static String stringCheck(char c,Queue<Character> input) {
		String s = c +"";
		char head = input.poll();
		while (isLetter(head) || isDigital(head)) {
			s += head;
			head = input.poll();
		}
		((Deque<Character>)input).offerFirst(head);
		return s;
	}
	
	public static Number isConstant(char c,Queue<Character> input) throws InputException, ParseException{
		String s = c +"";
		char head = input.poll();
		int count = 0;
		while (isDigital(head) || head == '.') {
			s += head;
			if (head == '.')
				count++;
			head = input.poll();
		}
		if (!(count == 0 || count == 1))
			throw new InputException("输入文件有错误！！！");
		if (isOperator(head) || isDelimiter(head)) {
			((Deque<Character>)input).offerFirst(head);
			return NumberFormat.getInstance().parse(s);
		}else {
			throw new InputException("输入文件有错误！！！");
		}
	}
	
	
	@Test
	public void taisite(){
		System.out.println(isOperator('+'));
		Queue<String> queue = new LinkedList<>();
		queue.add("a");
		queue.add("f");
		Deque<String> deque = (Deque<String>) queue;
		deque.add("v");
		while (!queue.isEmpty()) {
			System.out.println(queue.poll());
		}
	}

	public static boolean isDefinedKeywords(String s) {
		boolean flag = false;
		if (definedKeywords.contains(s))
			flag = true;
		return flag;
	}

	public static IdentifierType addIdentifier(Queue<Character> input) throws InputException {
		char c = input.poll();
		while (c == ' ') {
			c = input.poll();
		}
		if(!isLetter(c))
			throw new InputException("输入文件有错误！！！");
		String s = stringCheck(c, input);

		for (int i = 0; i < identifier.size(); i++) {
			if (identifier.get(i).getType().containsValue(s))
				throw new InputException("输入文件有错误！！！");
		}
		IdentifierType identifierType = new IdentifierType();
		Map<String, String> type = identifierType.getType();
		type.put("value", s);
		identifierType.setType(type);
		identifier.add(identifierType);
		return identifierType;
	}
	
	public static boolean isFunction(Queue<Character> input) {
		boolean flag = false;
		char c = input.poll();
		if (c == '(')
			flag = true;
		((Deque<Character>)input).addFirst(c);
		return flag;
	}
	
	public static IdentifierType addFunctionIdentifier(Queue<Character> input,Function function) throws InputException {
		List<IdentifierType> identifier = function.getIdentifier();
		
		char c = input.poll();
		while (c == ' ') {
			c = input.poll();
		}
		if(!isLetter(c))
			throw new InputException("输入文件有错误！！！");
		String s = stringCheck(c, input);

		for (int i = 0; i < identifier.size(); i++) {
			if (identifier.get(i).getType().containsValue(s))
				throw new InputException("输入文件有错误！！！");
		}
		IdentifierType identifierType = new IdentifierType();
		Map<String, String> type = identifierType.getType();
		type.put("value", s);
		type.put("type20", "1");
		identifierType.setType(type);
		identifier.add(identifierType);
		function.setIdentifier(identifier);
		
		return identifierType;
	}
	
	
	public static Function addFunction(Queue<Character> input) throws InputException, ParseException {
		Function function = new Function();
		List<List<String>> output1 = new LinkedList<List<String>>();
		boolean start = true;
		int count = 0;
		while (!input.isEmpty()) {
			char head = input.poll();
			if (LexicalAnalysisUtil.isLetter(head)) {
				//字符串
				String s = LexicalAnalysisUtil.stringCheck(head,input);
				//是关键词
				if (LexicalAnalysisUtil.isKeywords(s)) {
					output1.add(new LinkedList<String>(Arrays.asList("1",LexicalAnalysisUtil.keywords.indexOf(s) + "")));
					//是for
					if (s.equals("for")) {
						
					}
					//是this
					if (s.equals("this")) {
						char c = input.poll();
						if (c == '.') {
							char c2 = input.poll();
							String s2 = stringCheck(c2, input);
							boolean flag = false;
							for (int i = 0; i < LexicalAnalysisUtil.identifier.size(); i++) {
								IdentifierType identifierType = LexicalAnalysisUtil.identifier.get(i);
								if (identifierType.getType().containsValue(s2)) {
									output1.add(new LinkedList<String>(Arrays.asList("2",LexicalAnalysisUtil.identifier.indexOf(identifierType) + "")));
									flag = true;
									break;
								}
							}
							if (flag == false)
								throw new InputException("输入文件有错误！！！");
						}else {
							throw new InputException("输入文件有错误！！！");
						}
					}
					
					
					//是定义性标识符
					if(LexicalAnalysisUtil.isDefinedKeywords(s)){
						//处理数组
						char c1 = 0;
						char c2 = 0;
						char c3 = 0;
						while ((c1 = input.poll()) == '[') {
							c2 = input.poll();
							if (LexicalAnalysisUtil.isDigital(c2)) {
								c3 = input.poll();
								if(c3 == ']'){
									output1.add(new LinkedList<String>(Arrays.asList("5",LexicalAnalysisUtil.delimiters.indexOf(c1) + "")));
									/*if (!LexicalAnalysisUtil.constants.contains(Character.getNumericValue(c2)))
										LexicalAnalysisUtil.constants.add(Character.getNumericValue(c2));*/
									
									if (!LexicalAnalysisUtil.constants.contains(NumberFormat.getInstance().parse(c2 + "")))
										LexicalAnalysisUtil.constants.add(NumberFormat.getInstance().parse(c2 + ""));
									output1.add(new LinkedList<String>(Arrays.asList("3",LexicalAnalysisUtil.constants.indexOf(NumberFormat.getInstance().parse(c2 + "")) + "")));
									output1.add(new LinkedList<String>(Arrays.asList("5",LexicalAnalysisUtil.delimiters.indexOf(c3) + "")));

								}else {
									throw new InputException("输入文件有错误！！！");
								}
							}else if (c2 == ']') {
								output1.add(new LinkedList<String>(Arrays.asList("5",LexicalAnalysisUtil.delimiters.indexOf(c1) + "")));
								output1.add(new LinkedList<String>(Arrays.asList("5",LexicalAnalysisUtil.delimiters.indexOf(c2) + "")));
							}else {
								throw new InputException("输入文件有错误！！！");
							}

						}
						((Deque<Character>)input).offerFirst(c1);
						//数组处理完
						
						//添加定义性标识符
						IdentifierType identifier = LexicalAnalysisUtil.addFunctionIdentifier(input,function);
						output1.add(new LinkedList<String>(Arrays.asList("2",function.getIdentifier().indexOf(identifier) + "")));
						
						if (LexicalAnalysisUtil.isFunction(input))
							throw new InputException("输入文件有错误！！！");
						
					}
				//是标识符
				}else {
					//
					boolean flag = true;
					
					for (int i = 0; i < function.getIdentifier().size(); i++) {
						IdentifierType identifierType = function.getIdentifier().get(i);
						if (identifierType.getType().containsValue(s)) {
							output1.add(new LinkedList<String>(Arrays.asList("2",function.getIdentifier().indexOf(identifierType) + "")));
							flag = false;
							break;
						}
					}
					if (flag) {
						for (int i = 0; i < LexicalAnalysisUtil.identifier.size(); i++) {
							IdentifierType identifierType = LexicalAnalysisUtil.identifier.get(i);
							if (identifierType.getType().containsValue(s)) {
								output1.add(new LinkedList<String>(Arrays.asList("2",i + "")));
								flag = false;
								break;
							}
						}
					}
					//使用为声明的标识符
					
					if(flag)
						throw new InputException("输入文件有错误！！！");
				}
			}else if (LexicalAnalysisUtil.isDigital(head)) {
				//是常数返回，不是抛异常
				Number constant = LexicalAnalysisUtil.isConstant(head,input);
				if (!LexicalAnalysisUtil.constants.contains(constant))
					LexicalAnalysisUtil.constants.add(constant);
				output1.add(new LinkedList<String>(Arrays.asList("3",LexicalAnalysisUtil.constants.indexOf(constant) + "")));
				
			}else if (LexicalAnalysisUtil.isOperator(head)) {
				//操作符
				String s = LexicalAnalysisUtil.operatorEquals(head, input);
				output1.add(new LinkedList<String>(Arrays.asList("4",LexicalAnalysisUtil.operators.indexOf(s) + "")));
			}else if (LexicalAnalysisUtil.isDelimiter(head)) { 
				//分隔符
				output1.add(new LinkedList<String>(Arrays.asList("5",LexicalAnalysisUtil.delimiters.indexOf(head) + "")));
				if (head == '{') {
					count++;
				}else if (head == '}') {
					count--;
					if (count == 0) {
						function.setOutput(output1);
						return function;
					}
				}
				//char
				if (head == '\'') {
					List<String> list = LexicalAnalysisUtil.charEquals(input);
					if (list.size() == 2) {
						if(!(LexicalAnalysisUtil.charAndString.contains(list.get(0))))
							LexicalAnalysisUtil.charAndString.add(list.get(0));
						output1.add(new LinkedList<String>(Arrays.asList("7",LexicalAnalysisUtil.charAndString.indexOf(list.get(0)) + "")));
						
						output1.add(new LinkedList<String>(Arrays.asList("5",LexicalAnalysisUtil.delimiters.indexOf(list.get(1).charAt(0)) + "")));
						
					}
				}else if (head == '"') {
					//String
					List<String> list = LexicalAnalysisUtil.stringEquals(input);
					if (list.size() == 2) {
						if(!(LexicalAnalysisUtil.charAndString.contains(list.get(0))))
							LexicalAnalysisUtil.charAndString.add(list.get(0));
						output1.add(new LinkedList<String>(Arrays.asList("7",LexicalAnalysisUtil.charAndString.indexOf(list.get(0)) + "")));
						
						output1.add(new LinkedList<String>(Arrays.asList("5",LexicalAnalysisUtil.delimiters.indexOf(list.get(1).charAt(0)) + "")));
						
					}
				}
			}else if (head == ' ') {
				
			}else if (head == 0) {
				
			} else {
				throw new InputException("输入文件有错误！！！");
			}
			
		}
		
		return function;
	}
	
}
