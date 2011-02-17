package org.meanbean.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * Unit tests to test the functionality afforded by the StringUtils class.
 * 
 * @author Graham Williamson
 */
public class StringUtilsTest {

	private static final String LIST_1_ITEM_1 = "Graham";
	private static final String LIST_1_ITEM_2 = "Robert";
	private static final String LIST_1_ITEM_3 = "James";
	private static final String LIST_1_ITEM_4 = "Williamson";

	private static final String LIST_2_ITEM_1 = "Kerry";
	private static final String LIST_2_ITEM_2 = "Julia";
	private static final String LIST_2_ITEM_3 = "Williamson";

	@Test
	public void testJoinWithListAndString() {
		String result1 = StringUtils.join(createTestList1(), ",");
		assertEquals(createTestString1(","), result1);

		String result2 = StringUtils.join(createTestList1(), ":");
		assertEquals(createTestString1(":"), result2);

		String result3 = StringUtils.join(createTestList1(), ";");
		assertEquals(createTestString1(";"), result3);
		assertNotSame(createTestString1(":"), result3);
		assertNotSame(createTestString1(","), result3);

		String result4 = StringUtils.join(createTestList2(), ",");
		assertEquals(createTestString2(","), result4);

		String result5 = StringUtils.join(createTestList2(), ":");
		assertEquals(createTestString2(":"), result5);

		String result6 = StringUtils.join(createTestList2(), ";");
		assertEquals(createTestString2(";"), result6);
		assertNotSame(createTestString2(":"), result6);
		assertNotSame(createTestString2(","), result6);

		assertNull("should return null", StringUtils.join((List<String>) null, ";"));
	}

	@Test
	public void testJoinWithListAndCharacter() {
		String result1 = StringUtils.join(createTestList1(), ',');
		assertEquals(createTestString1(","), result1);

		String result2 = StringUtils.join(createTestList1(), ':');
		assertEquals(createTestString1(":"), result2);

		String result3 = StringUtils.join(createTestList1(), ';');
		assertEquals(createTestString1(";"), result3);
		assertNotSame(createTestString1(":"), result3);
		assertNotSame(createTestString1(","), result3);

		String result4 = StringUtils.join(createTestList2(), ',');
		assertEquals(createTestString2(","), result4);

		String result5 = StringUtils.join(createTestList2(), ':');
		assertEquals(createTestString2(":"), result5);

		String result6 = StringUtils.join(createTestList2(), ';');
		assertEquals(createTestString2(";"), result6);
		assertNotSame(createTestString2(":"), result6);
		assertNotSame(createTestString2(","), result6);
	}

	@Test
	public void testJoinWithArrayAndString() {
		String result1 = StringUtils.join(createTestArray1(), ",");
		assertEquals(createTestString1(","), result1);

		String result2 = StringUtils.join(createTestArray1(), ":");
		assertEquals(createTestString1(":"), result2);

		String result3 = StringUtils.join(createTestArray1(), ";");
		assertEquals(createTestString1(";"), result3);
		assertNotSame(createTestString1(":"), result3);
		assertNotSame(createTestString1(","), result3);

		String result4 = StringUtils.join(createTestArray2(), ",");
		assertEquals(createTestString2(","), result4);

		String result5 = StringUtils.join(createTestArray2(), ":");
		assertEquals(createTestString2(":"), result5);

		String result6 = StringUtils.join(createTestArray2(), ";");
		assertEquals(createTestString2(";"), result6);
		assertNotSame(createTestString2(":"), result6);
		assertNotSame(createTestString2(","), result6);

		assertNull("should return null", StringUtils.join((String[]) null, ";"));
	}

	@Test
	public void testJoinWithArrayAndCharacter() {
		String result1 = StringUtils.join(createTestArray1(), ',');
		assertEquals(createTestString1(","), result1);

		String result2 = StringUtils.join(createTestArray1(), ':');
		assertEquals(createTestString1(":"), result2);

		String result3 = StringUtils.join(createTestArray1(), ';');
		assertEquals(createTestString1(";"), result3);
		assertNotSame(createTestString1(":"), result3);
		assertNotSame(createTestString1(","), result3);

		String result4 = StringUtils.join(createTestArray2(), ',');
		assertEquals(createTestString2(","), result4);

		String result5 = StringUtils.join(createTestArray2(), ':');
		assertEquals(createTestString2(":"), result5);

		String result6 = StringUtils.join(createTestArray2(), ';');
		assertEquals(createTestString2(";"), result6);
		assertNotSame(createTestString2(":"), result6);
		assertNotSame(createTestString2(","), result6);
	}

	private List<String> createTestList1() {
		List<String> result = new ArrayList<String>();
		result.add(LIST_1_ITEM_1);
		result.add(LIST_1_ITEM_2);
		result.add(LIST_1_ITEM_3);
		result.add(LIST_1_ITEM_4);
		return result;
	}

	private List<String> createTestList2() {
		List<String> result = new ArrayList<String>();
		result.add(LIST_2_ITEM_1);
		result.add(LIST_2_ITEM_2);
		result.add(LIST_2_ITEM_3);
		return result;
	}

	private String[] createTestArray1() {
		String[] result = new String[4];
		result[0] = LIST_1_ITEM_1;
		result[1] = LIST_1_ITEM_2;
		result[2] = LIST_1_ITEM_3;
		result[3] = LIST_1_ITEM_4;
		return result;
	}

	private String[] createTestArray2() {
		String[] result = new String[3];
		result[0] = LIST_2_ITEM_1;
		result[1] = LIST_2_ITEM_2;
		result[2] = LIST_2_ITEM_3;
		return result;
	}

	private String createTestString1(String divider) {
		return LIST_1_ITEM_1 + divider + LIST_1_ITEM_2 + divider + LIST_1_ITEM_3 + divider + LIST_1_ITEM_4;
	}

	private String createTestString2(String divider) {
		return LIST_2_ITEM_1 + divider + LIST_2_ITEM_2 + divider + LIST_2_ITEM_3;
	}
}