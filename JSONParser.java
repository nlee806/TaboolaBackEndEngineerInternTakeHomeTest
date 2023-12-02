/**

@author: Nathan Lee
@purpose: Taboola Backend Engineer Intern Take Home Test Question 1.
Assumption: Input JSON String is in valid JSON Format.

**/
import java.util.*;
import java.lang.Number;
import java.text.NumberFormat;
import java.text.DecimalFormat;


public class JSONParser{
	
	/**
		parse()
		Parses json String and returns Map<String,Object>
	**/
	public static Map<String,Object> parse(String json) throws Exception{
		Map<String,Object> output = new LinkedHashMap<String,Object>();
		
		System.out.println("NEW JSON");
		
		//TODO-Delete this.
		String testString = "";
		
		boolean containerBracket = false;
		boolean openString = false; //In the middle of a String Yes/No
		boolean addNewKeyValue = false;
		boolean isOtherDataType = false;
		boolean isNumber = false;
		boolean isBoolean = false;
		boolean isNull = false;
		boolean alreadyInBracket = false;
		int numNestedBrackets = 0;
		String stringWriter = "";
		String name = "";
		Object value;
		
		//Go character by character through the JSON String
		for(int a=0;a<json.length();a++){
			char currentChar = json.charAt(a);
			
			System.out.println("currentChar "+ currentChar);
			
			if(containerBracket == true){
				if(currentChar == '"'){
					openString = !openString; // Reverse the boolean, a quote begins or ends.
					if(openString == true){
						stringWriter = "";//empty the stringWriter.
					}
					else if(openString == false){//end of the word
						//stringWriter = stringWriter+""; //Save the last string written.
						
						//TODO Delete this.
						System.out.println(stringWriter);	
					}	
				}
				if(openString == true && isOtherDataType == true && (currentChar==',' || currentChar=='}')){
					openString = !openString;
					isOtherDataType = !isOtherDataType;
				}
				if(openString == true && currentChar!='"'){ //Saving the string that's being printed.
					stringWriter = stringWriter+currentChar;
				}
				if(openString == false){ //Not a string.
					if(currentChar == ':'){ //Key-Value Divider.
						name = "";
						name = name + stringWriter;
					}
					//Check for numerics/numbers.
					if(Character.isDigit(currentChar) || currentChar == '.' || currentChar == 'E' || currentChar == 'e' || currentChar == '+' || currentChar == '-'){
						//use Number
						stringWriter = "";
						stringWriter = stringWriter+currentChar;
						openString = true;
						isOtherDataType = true;
						isNumber = true;
					}
					//Check for values: booleans and nulls.
					if(currentChar == 't' || (currentChar == 'f' && json.charAt(a+1)=='a') || currentChar == 'n'){
						stringWriter = "";
						stringWriter = stringWriter+currentChar;
						openString = true;
						isOtherDataType = true;
						if(currentChar == 'n'){
							isNull = true;
						}
						else{
							isBoolean = true;
						}
					}
					//TODO Check for arrays.
					if(currentChar == '['){
						
					}
					//Check for nested objects.
					if(currentChar == '{'){ // new HashMap Object
						alreadyInBracket = true;
						int openedBraces = 1;
						int indexClosedBrace = -1;
						for(int c=a+1;c<json.length();c++){
							if(json.charAt(c) =='{' && indexClosedBrace == -1){
								openedBraces++;
							}
							else if(json.charAt(c)=='}' && indexClosedBrace == -1){
								openedBraces = openedBraces-1;
							}
							if(openedBraces==0 && indexClosedBrace == -1){
								indexClosedBrace = c;
							}
						}
						//String containerlessString = json.substring(0,indexClosedBrace);
						//int closingContainerBracket = containerlessString.lastIndexOf('}');
						int closingContainerBracket = indexClosedBrace;
						
						System.out.println("flag A "+a+ "flag Bracket "+closingContainerBracket);
						System.out.println("flag B "+json.substring(a,closingContainerBracket+1));
						Map<String,Object> nestedOutput = new LinkedHashMap<String,Object>();
						nestedOutput = parse(json.substring(a,closingContainerBracket+1));
						System.out.println("nestedOutput "+nestedOutput);
						
						value = nestedOutput;
						System.out.println("value "+value);
						
						//TODO Delete this.
						output.put(name, nestedOutput);
						
						System.out.println("+++");
						
					}
					//Check for end - new object to add.
					if(currentChar == ',' || (currentChar=='}')){
						addNewKeyValue = true;
					}
				}
			}
			//Skip over the first bracket- it is the JSON Object itself.
			if(containerBracket == false && currentChar == '{'){
				containerBracket = true;
			}
			if(addNewKeyValue == true){
				if(isNumber == true){
					//Count Number of Digits Integer/Double vs Long.
					//TODO Technically should check for Double limits vs Long, but will assume E/e values are reasonably smaller.
					int digits = 0;
					boolean beforeE = true;
					for(int b=0;b<stringWriter.length();b++){
						if(beforeE){
							if(stringWriter.charAt(b)>=48&&stringWriter.charAt(b)<=57){
								digits++;
							}
							if(stringWriter.charAt(b)=='E' || stringWriter.charAt(b)=='e'){
								beforeE = false;
							}
						}
					}
					//Is an Integer or Double
					System.out.println("Check STRINGWRITER "+stringWriter+" "+stringWriter.getClass()+" "+stringWriter.contains("e"));
					if((digits<=9)){
						if(stringWriter.contains(".")){ // Is a Double or Float
							value = Double.parseDouble(stringWriter);
						}
						else{ // Is an Integer
							value = Integer.parseInt(stringWriter);
						}
					}
					else{ //Is a Long
						Number toNumber = null;
						try{
							toNumber = DecimalFormat.getInstance().parse(stringWriter);
							}
						catch(Exception e){}
						value = toNumber;
					}
					
					System.out.println("Check VALUE "+value.getClass());
					
					isNumber = false;
				}
				else if(isBoolean == true){
					boolean toBoolean = Boolean.valueOf(stringWriter);
					value = toBoolean;
					isBoolean = false;
				}
				else if(isNull == true){
					value = null;
					isNull = false;
				}
				else{
					value = stringWriter;
				}
				
				System.out.println("name: "+name+" value: "+value);
				output.put(name,value);
				System.out.println("output: "+output);
				addNewKeyValue = false;
			}
			
			//TODO-Delete this.
			testString = testString+currentChar;
		} 
		
		//TODO-Delete this.
		System.out.println("Inputted String:---"+testString);
		
		//TODO-Delete these-Test Map object.
		//output.put("examplekey", "examplevalue");
		//output.put("debug", "on");
		//output.put("window", new LinkedHashMap<String, Object>().put("title","sample"));
		//output.put("testInnerMap", new LinkedHashMap<String, Object>().put("test1name","test1value"));
		//System.out.println("testOutputWindow: "+output.get("window"));
		//output.put("testArray", [3,4,5]);
		
		System.out.println("testoutput: "+output);
		//assert output.get(Map<String, Object>)
		
		return output;
	}
	
	/**
		main()
		Main driver with example. Replace exampleString with test strings.
	**/
	public static void main(String[] args) throws Exception{
		
		String exampleString = "{\"testNum\": -59832.45e10, \"testbool\": false, \"debug\" : \"on\",\"window\" : {\"title\" : \"sample\",\"size\": 500}, \"testnull\":null, \"testBraces\":{\"test1\":\"test1value\"}}";
		//exampleString = "{\"debug\" : \"on\",\"window\" : {\"title\" : \"sample\",\"size\": 500}}";

		//parse() is static already but to keep in line with the example input:
		//try{
		Map<String,Object> output = JSONParser.parse(exampleString);
		/*}
		catch(Exception e){
			//Will ignore for now.
		}*/
		
		assert output.get("debug").equals("on");
		
		//assert output.get("size").equals(500);
		
		System.out.println("testAssert "+(output.get("size")).getClass());
		System.out.println(((Map<String, Object>)output.get("window")).get("size"));
		
		assert ((Map<String, Object>)output.get("window")).get("title").equals("sample");
		assert ((Map<String, Object>)output.get("window")).get("size").equals(500);
	}
	
}

