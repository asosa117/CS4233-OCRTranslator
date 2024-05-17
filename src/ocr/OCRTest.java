/* This is just a placeholder. */
package ocr;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OCRTest {

	OCRTranslator ocr = new OCRTranslator();

	@Test
	void testNull() { assertNull(ocr.translate("||","||","||")); }

	@Test
	void testStringLengthsTop() {
		OCRException exception = assertThrows(OCRException.class, () -> ocr.translate(" "," |"," |"));
		assertEquals("Invalid Input, String Lengths Need To Be The Same", exception.getMessage()); }
	
	@Test
	void testStringLengthsMiddle() { 
		OCRException exception = assertThrows(OCRException.class, () -> ocr.translate("b|","b","b|"));
		assertEquals("Invalid Input, String Lengths Need To Be The Same", exception.getMessage()); }
	
	@Test
	void testStringLengthsBottom() { 
		OCRException exception = assertThrows(OCRException.class, () -> ocr.translate("b|","b|","b"));
		assertEquals("Invalid Input, String Lengths Need To Be The Same", exception.getMessage()); }

	@Test
	void testEmptyString() {  assertEquals("", ocr.translate("", "", "")); }

	@Test
	void testInvalidCharInputTop() { 
		OCRException exception = assertThrows(OCRException.class, () -> ocr.translate("o","|","|"));
		assertEquals("Invalid Characters in Input, You Can Only Input '|', '_', or ' ' (space)", exception.getMessage()); }
	
	@Test
	void testInvalidCharInputMiddle() { 
		OCRException exception = assertThrows(OCRException.class, () -> ocr.translate("|","o","|"));
		assertEquals("Invalid Characters in Input, You Can Only Input '|', '_', or ' ' (space)", exception.getMessage()); }
	
	@Test
	void testInvalidCharInputBottom() { 
		OCRException exception = assertThrows(OCRException.class, () -> ocr.translate("|","|","o"));
		assertEquals("Invalid Characters in Input, You Can Only Input '|', '_', or ' ' (space)", exception.getMessage()); }
	
	@Test
	void testOneIntInputOne() { assertEquals("1", ocr.translate(" ", "|", "|")); }
	
	@Test
	void testOneIntInputTwo() { assertEquals("7", ocr.translate("_ ", " |", " |")); }
	
	@Test
	void testOneIntInputThree() { assertEquals("0", ocr.translate(" _ ", "| |", "|_|")); }

	@Test
	void testTwoIntsInput() { assertEquals("17", ocr.translate("  _ ", "|  |", "|  |")); }
	
	@Test
	void testThreeIntsInputOne() { assertEquals("045", ocr.translate(" _       _ ", "| | |_| |_ ", "|_|   |  _|")); }
	
	@Test
	void testThreeIntsInputTwo() { assertEquals("075", ocr.translate(" _  _   _ ", "| |  | |_ ", "|_|  |  _|")); }
	
	@Test
	void testThreeIntsInputThree() { assertEquals("071", ocr.translate(" _  _   ", "| |  | |", "|_|  | |")); }

	@Test
	void testSameSpaces() { 
	OCRException exception = assertThrows(OCRException.class, () -> ocr.translate(" _        _ ", "| |  |_| |_ ", "|_|    | |_ "));
	assertEquals("Space Between Numbers Needs To Be The Same", exception.getMessage()); }
	
	@Test
	void testNoWhiteSpace() { 
		OCRException exception = assertThrows(OCRException.class, () -> ocr.translate("|_| ", "|_||", "|_||"));
		assertEquals("Spaces Needed Between Numbers", exception.getMessage()); }
}