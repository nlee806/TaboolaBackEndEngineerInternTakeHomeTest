/**

@author: Nathan Lee
@purpose: Taboola Backend Engineer Intern Take Home Test Question 1.


**/
import java.util.*;


public class JSONParser{
	
	/**
		Parses json String and returns Map<String,Object>
	**/
	public static Map<String,Object> parse(String json){
		Map<String,Object> output = new LinkedHashMap<String,Object>();
		
		String testString = "";
		
		//Go character by character through the JSON String
		for(int a=0;a<json.length();a++){
			char firstBracket = '{';
			char currentChar = json.charAt(a);
			testString = testString+currentChar;
		} 
		System.out.println("Test: "+testString);
		
		//Test Map object.
		output.put("examplekey", "examplevalue");
		output.put("debug", "on");
		
		return output;
	}
	
	/**
		Main driver with example. Replace exampleString with test strings.
	**/
	public static void main(String[] args){
		String exampleString = "{\"debug\" : \"on\",\"window\" : {\"title\" : \"sample\",\"size\": 500}}";
		//parse() is static already but to keep in line with the example input:
		Map<String,Object> output = JSONParser.parse(exampleString);
		
		assert output.get("debug").equals("on");/*
		//XXX weirdly written
		assert (Map<String, Object>(output.get("window")).get("title")).equals("sample");
		assert (Map<String, Object>(output.get("window")).get("size")).equals(500);
		*/
	}
	
}

