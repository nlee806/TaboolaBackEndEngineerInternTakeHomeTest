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
		int indexClosedBrace = -1;
		String stringWriter = "";
		String name = "";
		Object value;
		
		//Go character by character through the JSON String
		for(int a=0;a<json.length();a++){
			char currentChar = json.charAt(a);
			
			System.out.println("currentChar "+ currentChar+" a "+a+" indexClosedBrace "+indexClosedBrace);
			
			if(containerBracket == true && (a>indexClosedBrace)){
				//---------------------------------------------------------------------------------------------------------
				//1a. Check for Strings. Opens StringWriter.
				//1c. Closes StringWriter.
				if(currentChar == '"'){
					openString = !openString; // Reverse the boolean, a quote begins or ends.
					if(openString == true){
						stringWriter = "";//empty the stringWriter.
					}
					else if(openString == false){//end of the word
						System.out.println("Finished String(Name?/Value?): "+stringWriter);	
					}	
				}
				//2c./3c. End Condition for Numbers, Values, Closes StringWriter.
				if(openString == true && isOtherDataType == true && (currentChar==',' || currentChar=='}')){
					openString = !openString;
					isOtherDataType = !isOtherDataType;
				}
				//1b./2b./3b. Save all characters inside of String, Writes to StringWriter.
				if(openString == true && currentChar!='"'){
					stringWriter = stringWriter+currentChar;
				}
				//----------------------------------------------------------------------------------------------------------
				//End Condition for String, 
				//NonStrings: Check for Numbers, Values, Arrays, Nested Objects.
				if(openString == false){ //Not a string.
					//2a. Check for numerics/numbers.
					if(Character.isDigit(currentChar) || currentChar == '.' || currentChar == 'E' || currentChar == 'e' || currentChar == '+' || currentChar == '-'){
						stringWriter = ""; //empty the stringWriter
						stringWriter = stringWriter+currentChar;
						openString = true; //save number as a String.
						isOtherDataType = true;
						isNumber = true;
					}
					//3a. Check for values: booleans and nulls.
					if(currentChar == 't' || currentChar == 'T' || (currentChar == 'f' && json.charAt(a+1)=='a') || (currentChar == 'F' && json.charAt(a+1)=='a') || (currentChar == 'F' && json.charAt(a+1)=='A') || currentChar == 'n' || currentChar == 'N'){
						stringWriter = ""; //empty the stringWriter
						stringWriter = stringWriter+currentChar;
						openString = true;
						isOtherDataType = true;
						if(currentChar == 'n' || currentChar == 'N'){
							isNull = true;
						}
						else{
							isBoolean = true;
						}
					}
					//TODO 5a. Check for arrays.
					if(currentChar == '['){
						
						
					}
					//4a. Check for nested objects.
					if(currentChar == '{'){ // new HashMap Object
						alreadyInBracket = true;
						int openedBraces = 1;
						indexClosedBrace = -1;
						boolean isString = false;
						for(int c=a+1;c<json.length();c++){
							if(indexClosedBrace == -1){ //If haven't found the closing brace yet
								if(json.charAt(c)=='"'){ //Ensure that brackets are not in a String.
									isString = !isString;
								}
								if(isString == false){ //If not part of a String
									if(json.charAt(c) =='{'){
										openedBraces++;
									}
									else if(json.charAt(c)=='}'){
										openedBraces = openedBraces-1;
									}
									if(openedBraces==0){
										indexClosedBrace = c;
									}
								}
							}
						}
						
						System.out.println("flag A "+a+ "flag Bracket "+indexClosedBrace);
						System.out.println("flag B "+json.substring(a,indexClosedBrace+1));
						
						Map<String,Object> nestedOutput = new LinkedHashMap<String,Object>();
						nestedOutput = parse(json.substring(a,indexClosedBrace+1));
						
						System.out.println("nestedOutput "+nestedOutput);
						
						value = nestedOutput;
						output.put(name, value); //TODO Change nestedOutput to value?
						
						System.out.println("Inserted- name: "+name+" value: "+value);
						
					}
					//1di. String is a Name.
					if(currentChar == ':'){ //Key-Value Divider.
						name = "";
						name = name + stringWriter;
						System.out.println("Assigned "+stringWriter+" to Name.");
						stringWriter = ""; //empty the stringWriter.
					}
					//1dii. String is a Value.
					//2d. Number is a Value.
					//3d. Bool/Null is a Value.
					//Check for end condition- new object to add.
				if(currentChar == ',' || (currentChar=='}')){
						addNewKeyValue = true;
					}
				}
			}
			//0. Skip over the first bracket- it is the JSON Object itself.
			if(containerBracket == false && currentChar == '{'){
				containerBracket = true;
			}
			if(addNewKeyValue == true){
				//1e. String is assigned to Value.
				//2e. Number String is converted to Number Object, assigned to Value.
				//3e. Bool/Null String is converted to Bool/Null Object, assigned to Value.
				if(isNumber == true){
					//Count Number of Digits Integer/Double vs Long, Check Scientific Notation.
					//Note: Technically should check for Double limits vs Long, but will assume E/e values are reasonably smaller.
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
					//2ei. Assign to Double, Integer, or Long.
					//Is an Integer or Double?
					stringWriter = stringWriter.replaceAll("\\s","");
					if((digits<=9)){
						if(stringWriter.contains(".")){ // Is a Double or Float
							value = Double.parseDouble(stringWriter); //assigned to Value.
							System.out.println("Assigned Double "+value+" to Value.");
						}
						else{ // Is an Integer
							value = Integer.parseInt(stringWriter); //assigned to Value.
							System.out.println("Assigned Int "+value+" to Value.");
						}
					}
					else{ //Is a Long
						Number toNumber = null;
						try{
							toNumber = DecimalFormat.getInstance().parse(stringWriter);
							}
						catch(Exception e){}
						value = toNumber; //assigned to Value.
						System.out.println("Assigned Long "+value+" to Value.");
					}
					isNumber = false; //finished assigning Number.
				}
				else if(isBoolean == true){
					boolean toBoolean = Boolean.valueOf(stringWriter);
					value = toBoolean; //assigned to Value.
					System.out.println("Assigned Bool "+value+" to Value.");
					isBoolean = false; //finished assigning Bool.
				}
				else if(isNull == true){
					value = null; //assigned to Value.
					System.out.println("Assigned Null "+value+" to Value.");
					isNull = false; //finished assigning Null.
				}
				else{ //String
					System.out.println("Assigned String "+stringWriter+" to Value.");
					value = stringWriter; //assigned to Value.
				}
				
				//check this TODO
				if(alreadyInBracket == true){
					System.out.println("indexClosedBrace "+indexClosedBrace+" JSONLength "+json.length());
					String restOfString = json.substring(indexClosedBrace,json.length());
					System.out.println("RestOfString "+restOfString);
					int distToNext = -1;
					boolean foundDistToNext = false;
					for(int e=indexClosedBrace;e<json.length();e++){
						if(foundDistToNext==false){
							if(json.charAt(e)!='}' && json.charAt(e)!=',' && json.charAt(e)!=' '){
								distToNext = e;
								System.out.println("distToNext"+distToNext+" jsoncharAt "+json.charAt(e));
								foundDistToNext = true;
							}
						}
					}
					if(distToNext == -1){
						distToNext = restOfString.length()+indexClosedBrace+1;
					}
					System.out.println("distToNext "+distToNext);
					
					System.out.println("Check- name "+name+" value "+value+" stringWriter "+stringWriter+" a "+a+" indexClosedBrace "+indexClosedBrace);
					if(a>distToNext){	//indexClosedBrace+1				
						System.out.println("Inserted- name "+name+" value "+value+" stringWriter "+stringWriter+" a "+a+" indexClosedBrace "+indexClosedBrace);
						addNewKeyValue = true;
						alreadyInBracket = false;
						output.put(name,value); //insert Name, Value to Hashmap.
					}
				}
				else{
					
					System.out.println("Inserted- name: "+name+" value: "+value);
					output.put(name,value); //insert Name, Value to Hashmap.
					System.out.println("output: "+output);
					addNewKeyValue = false;
				
				}
				addNewKeyValue = false; //finished assigning, inserting into Hashmap.
			}
			
			//TODO-Delete this.
			testString = testString+currentChar;
		} 
		
		//TODO-Delete this.
		System.out.println("Inputted String:---"+testString);
		System.out.println("Hashmap: "+output);
		
		return output;
	}
	
	/**
		main()
		Main driver with example. Replace exampleString with test strings.
	**/
	public static void main(String[] args) throws Exception{
		
		String exampleString = "{\"testNum\": -59832.45e10, \"testbool\": false, \"debug\" : \"on\",\"window\" : {\"title\" : \"sample\",\"size\": 500}, \"testnull\":null, \"testBraces\":{\"test1\":\"test1value\", \"test2\":{\"test2a\":{}}, \"test3\": \"(kk}-> pe \"}, \"test4\":5 }";
		//exampleString = "{\"debug\" : \"on\",\"window\" : {\"title\" : \"sample\",\"size\": 500}}";

		//parse() is static already but to keep in line with the example input:
		Map<String,Object> output = JSONParser.parse(exampleString);
		
		assert output.get("debug").equals("on");
		assert ((Map<String, Object>)output.get("window")).get("title").equals("sample");
		assert ((Map<String, Object>)output.get("window")).get("size").equals(500);
	}
	
}

