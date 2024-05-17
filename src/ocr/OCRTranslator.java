/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Copyright ©2016-2017 Gary F. Pollice
 *******************************************************************************/
package ocr;

import java.util.HashMap;
import java.util.Scanner;


/**
 * This class has a single method that will translate OCR digits to a string of
 * text digits that correspond to the OCR digits.
 * 
 * @version Mar 13, 2019
 */
public class OCRTranslator
{
	/**
	 * Default constructor. You may not add parameters to this. This is
	 * the only constructor for this class and is what the master tests will use.
	 */
	public OCRTranslator()
	{

		// TODO Auto-generated constructor stub
	}
	/**
	 * Translate a string of OCR digits to a corresponding string of text
	 * digits. OCR digits are represented as three rows of characters (|, _, and space).
	 * @param top the top row of the OCR input
	 * @param middle the middle row of the OCR input
	 * @param bottom the third row of the OCR input
	 * @return a String containing the digits corresponding to the OCR input
	 * @throws an OCRException on error as noted in the specification
	 */
	public String translate(String top, String middle, String bottom) throws OCRException
	{
		HashMap<String,String> map = new HashMap<String,String>();//Creating HashMap    
		map.put(" _ | ||_|", "0");  //Put elements in Map  
		map.put(" ||", 	 	 "1");    
		map.put(" _  _||_ ", "2");   
		map.put("_ _|_|", 	 "3"); 
		map.put("   |_|  |", "4");    
		map.put(" _ |_  _|", "5");    
		map.put(" _ |_ |_|", "6");
		map.put("_  | |", 	 "7"); 
		map.put(" _ |_||_|", "8");   
		map.put(" _ |_|  |", "9");  
		map.put("", "");

		HashMap<String,String> validChar = new HashMap<String,String>();//Creating HashMap    
		validChar.put("|", "|");  //Put elements in Map  
		validChar.put("_", "_");
		validChar.put(" ", " ");


		String finish = "";
		String topTemp = "";
		String MiddleTemp = "";
		String BottomTemp = "";
		String comb = "";

		int numCounter = 0; // counts the length of the current OCR number in the string
		int charLoc = 0; //current location in the string
		int count = 0; //counts number of Integers read
		int spcCount = 0; //current number of blank spaces between numbers
		int cmpSpc = 0; //used to compare first space between numbers with other spaces

		boolean first = false;
		boolean done = false;
		boolean passed = false;

		String[] list = new String[10];



		if(top.length() == middle.length() && middle.length() == bottom.length()) {
			for(int i = 0; i < top.length();i++) {
				if(validChar.containsKey(Character.toString(top.charAt(i))) && validChar.containsKey(Character.toString(middle.charAt(i))) && validChar.containsKey(Character.toString(bottom.charAt(i)))) {
					if((top.charAt(i) == ' ' && middle.charAt(i) == ' ' && bottom.charAt(i) == ' ') == false){
						if(count > 1 && cmpSpc != spcCount && passed == false){
							throw new OCRException("Space Between Numbers Needs To Be The Same");
						}
						else if(count > 1 && cmpSpc == spcCount) {
							passed = true;
						}
						if(done == true) {
							first = true;
						}
						if(numCounter == 0) {
							charLoc += spcCount;
							spcCount = 0;
						}
						numCounter++;
						if(numCounter > 3) {
							throw new OCRException("Spaces Needed Between Numbers");
						}
					}
					else{
						if(spcCount == 0) {
							topTemp = top.substring(charLoc,charLoc + numCounter);
							MiddleTemp = middle.substring(charLoc,charLoc + numCounter);
							BottomTemp = bottom.substring(charLoc,charLoc + numCounter);
							charLoc = numCounter;
							numCounter = 0;
							count++;
							done = true;
						}
						if(first == false) {
							spcCount++;
							cmpSpc = spcCount;
						}
						else {
							spcCount++;
						}
					}
					comb = topTemp + MiddleTemp + BottomTemp;
					list[count] =  comb;

				}
				else {
					throw new OCRException("Invalid Characters in Input, You Can Only Input '|', '_', or ' ' (space)");
				}

			}
			if(count <= 1) {
				topTemp = top.substring(charLoc,charLoc + numCounter);
				MiddleTemp = middle.substring(charLoc,charLoc + numCounter);
				BottomTemp = bottom.substring(charLoc,charLoc + numCounter);
				charLoc += numCounter;
				comb = topTemp + MiddleTemp + BottomTemp;
				count++;
				list[count] = comb;
			}
			else {
				charLoc = charLoc + numCounter + 1;
				topTemp = top.substring(charLoc,charLoc + numCounter);
				MiddleTemp = middle.substring(charLoc,charLoc + numCounter);
				BottomTemp = bottom.substring(charLoc,charLoc + numCounter);
				comb = topTemp + MiddleTemp + BottomTemp;
				count++;
				list[count] = comb;

			}
			//loop through array to assign each OCR its matching number
			for(int p = 1; p < count + 1; p++) {
				if(map.containsKey(list[p])) {
					finish += map.get(list[p]);
				}
				else {
					return null;
				}
			}
		}
		else {
			throw new OCRException("Invalid Input, String Lengths Need To Be The Same");
		}
		return finish;
	}
}







