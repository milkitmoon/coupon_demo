package com.milkit.app.common.pattern;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;



@Service
public class PatternMacherServiceImpl {
	
	
	private final static String regexKey = "\\#\\{([\\w|\\uac00-\\ud7a3]*)\\}"; // #{key}
	
	
	public static String getMachingMessage(String orginMessage, Map<String, String> symbol, String regexKey) throws Exception {
		String machingMessage = null;
		
		if(orginMessage != null && symbol != null) {
			Pattern pattern = Pattern.compile(regexKey);
	
			Matcher matcher = pattern.matcher(orginMessage);
			StringBuffer sb = new StringBuffer();
			while (matcher.find()) {
				String key = matcher.group(1);
				String replacementValue = symbol.get(key);
				if(replacementValue != null) {
					matcher.appendReplacement(sb, replacementValue);
				} else {
					matcher.appendReplacement(sb, "");
				}
			}
			matcher.appendTail(sb);
			machingMessage = sb.toString();
		}
		
		return machingMessage;
	}
	

	public static String getMachingMessage(String orginMessage, Map<String, String> symbol) throws Exception {
		return getMachingMessage(orginMessage, symbol, regexKey);
	}
	
	public static String getMachingMessage(String orginMessage, String[] symbol) throws Exception {
		AtomicInteger atomicInteger = new AtomicInteger(0);
		Map<String, String> map = Arrays.stream(symbol)
		.collect(Collectors.toMap (x -> String.valueOf(atomicInteger.getAndIncrement()), Function.identity()));
		
		return getMachingMessage(orginMessage, map, regexKey);
	}

	public static boolean hasMachingMessage(String orginMessage) throws Exception {
		return hasMachingMessage(orginMessage, regexKey);
	}
	
	public static boolean hasMachingMessage(String orginMessage, String regexKey) throws Exception {
		boolean hasMaching = false;
		
		Pattern pattern = Pattern.compile(regexKey);
		
		Matcher matcher = pattern.matcher(orginMessage);
		while (matcher.find()) {
			hasMaching = true;
			break;
		}
		
		return hasMaching;
	}



}
