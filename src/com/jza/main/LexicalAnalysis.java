package com.jza.main;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.jza.main.Exception.InputException;
import com.jza.utils.Function;
import com.jza.utils.IdentifierType;
import com.jza.utils.LexicalAnalysisUtil;

public class LexicalAnalysis {
	public static void main(String[] args) throws InputException, ParseException {
		Queue<Character> input = (Queue<Character>) LexicalAnalysisUtil.readFile("src/com/jza/utils/ImputFile.txt");
		List<List<String>> output = new LinkedList<List<String>>();
		/*System.out.println();
		System.out.println(input.toString());*/
		while (!input.isEmpty()) {
			char head = input.poll();
			if (LexicalAnalysisUtil.isLetter(head)) {
				//字符串
				String s = LexicalAnalysisUtil.stringCheck(head,input);
				//是关键词
				if (LexicalAnalysisUtil.isKeywords(s)) {
					output.add(new LinkedList<String>(Arrays.asList("1",LexicalAnalysisUtil.keywords.indexOf(s) + "")));
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
									output.add(new LinkedList<String>(Arrays.asList("5",LexicalAnalysisUtil.delimiters.indexOf(c1) + "")));
									/*if (!LexicalAnalysisUtil.constants.contains(Character.getNumericValue(c2)))
										LexicalAnalysisUtil.constants.add(Character.getNumericValue(c2));*/
									
									if (!LexicalAnalysisUtil.constants.contains(NumberFormat.getInstance().parse(c2 + "")))
										LexicalAnalysisUtil.constants.add(NumberFormat.getInstance().parse(c2 + ""));
									output.add(new LinkedList<String>(Arrays.asList("3",LexicalAnalysisUtil.constants.indexOf(NumberFormat.getInstance().parse(c2 + "")) + "")));
									output.add(new LinkedList<String>(Arrays.asList("5",LexicalAnalysisUtil.delimiters.indexOf(c3) + "")));

								}else {
									throw new InputException("输入文件有错误！！！");
								}
							}else if (c2 == ']') {
								output.add(new LinkedList<String>(Arrays.asList("5",LexicalAnalysisUtil.delimiters.indexOf(c1) + "")));
								output.add(new LinkedList<String>(Arrays.asList("5",LexicalAnalysisUtil.delimiters.indexOf(c2) + "")));
							}else {
								throw new InputException("输入文件有错误！！！");
							}

						}
						((Deque<Character>)input).offerFirst(c1);
						//数组处理完
						//添加定义性标识符						
						IdentifierType identifier = LexicalAnalysisUtil.addIdentifier(input);
						output.add(new LinkedList<String>(Arrays.asList("2",LexicalAnalysisUtil.identifier.indexOf(identifier) + "")));
						//判断是不是方法
						if (LexicalAnalysisUtil.isFunction(input)) {
							Function function = LexicalAnalysisUtil.addFunction(input);
							LexicalAnalysisUtil.functions.add(function);
							output.add(new LinkedList<String>(Arrays.asList("8",LexicalAnalysisUtil.functions.indexOf(function) + "")));
						}

						
						
					}
				//是标识符
				}else {
					
					boolean flag = true;
					for (int i = 0; i < LexicalAnalysisUtil.identifier.size(); i++) {
						IdentifierType identifierType = LexicalAnalysisUtil.identifier.get(i);
						if (identifierType.getType().containsValue(s)) {
							output.add(new LinkedList<String>(Arrays.asList("2",i + "")));
							flag = false;
							break;
						}
					}
					if(flag)
						throw new InputException("输入文件有错误！！！");
				}
			}else if (LexicalAnalysisUtil.isDigital(head)) {
				//是常数返回，不是抛异常
				Number constant = LexicalAnalysisUtil.isConstant(head,input);
				if (!LexicalAnalysisUtil.constants.contains(constant))
					LexicalAnalysisUtil.constants.add(constant);
				output.add(new LinkedList<String>(Arrays.asList("3",LexicalAnalysisUtil.constants.indexOf(constant) + "")));
				
			}else if (LexicalAnalysisUtil.isOperator(head)) {
				//操作符
				String s = LexicalAnalysisUtil.operatorEquals(head, input);
				output.add(new LinkedList<String>(Arrays.asList("4",LexicalAnalysisUtil.operators.indexOf(s) + "")));
			}else if (LexicalAnalysisUtil.isDelimiter(head)) { 
				//分隔符
				output.add(new LinkedList<String>(Arrays.asList("5",LexicalAnalysisUtil.delimiters.indexOf(head) + "")));
				//char
				if (head == '\'') {
					List<String> list = LexicalAnalysisUtil.charEquals(input);
					if (list.size() == 2) {
						if(!(LexicalAnalysisUtil.charAndString.contains(list.get(0))))
							LexicalAnalysisUtil.charAndString.add(list.get(0));
						output.add(new LinkedList<String>(Arrays.asList("7",LexicalAnalysisUtil.charAndString.indexOf(list.get(0)) + "")));
						
						output.add(new LinkedList<String>(Arrays.asList("5",LexicalAnalysisUtil.delimiters.indexOf(list.get(1).charAt(0)) + "")));
						
					}
				}else if (head == '"') {
					//String
					List<String> list = LexicalAnalysisUtil.stringEquals(input);
					if (list.size() == 2) {
						if(!(LexicalAnalysisUtil.charAndString.contains(list.get(0))))
							LexicalAnalysisUtil.charAndString.add(list.get(0));
						output.add(new LinkedList<String>(Arrays.asList("7",LexicalAnalysisUtil.charAndString.indexOf(list.get(0)) + "")));
						
						output.add(new LinkedList<String>(Arrays.asList("5",LexicalAnalysisUtil.delimiters.indexOf(list.get(1).charAt(0)) + "")));
						
					}
				}
			}else if (head == ' ') {
				
			}else if (head == 0) {
				
			} else {
				throw new InputException("输入文件有错误！！！");
			}
			
		}

		LexicalAnalysisUtil.writeFile("src/com/jza/utils/OutPutFile.txt", LexicalAnalysisUtil.keywords.toString());
		LexicalAnalysisUtil.writeFile("src/com/jza/utils/OutPutFile.txt", LexicalAnalysisUtil.identifier.toString());
		LexicalAnalysisUtil.writeFile("src/com/jza/utils/OutPutFile.txt", LexicalAnalysisUtil.constants.toString());
		LexicalAnalysisUtil.writeFile("src/com/jza/utils/OutPutFile.txt", LexicalAnalysisUtil.operators.toString());
		LexicalAnalysisUtil.writeFile("src/com/jza/utils/OutPutFile.txt", LexicalAnalysisUtil.delimiters.toString());
		LexicalAnalysisUtil.writeFile("src/com/jza/utils/OutPutFile.txt", LexicalAnalysisUtil.definedKeywords.toString());
		LexicalAnalysisUtil.writeFile("src/com/jza/utils/OutPutFile.txt", LexicalAnalysisUtil.charAndString.toString());
		LexicalAnalysisUtil.writeFile("src/com/jza/utils/OutPutFile.txt", LexicalAnalysisUtil.functions.toString());
		LexicalAnalysisUtil.writeFile("src/com/jza/utils/OutPutFile.txt", output.toString());
		
	}
}
