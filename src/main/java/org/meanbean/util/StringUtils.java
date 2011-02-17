package org.meanbean.util;

import java.util.Arrays;
import java.util.List;

/**
 * Provides <code>String</code> utility methods.
 * 
 * @author Graham Williamson
 */
public class StringUtils {

	/**
	 * Construct a new StringUtils.
	 */
	private StringUtils() {
		// Do nothing - make non-instantiable
	}

	/**
	 * Join the specified items into a single <code>String</code> where each item is separated by the specified
	 * separator.
	 * 
	 * @param items
	 *            The items to join.
	 * @param separator
	 *            The character to insert between each item when it is joined.
	 * 
	 * @return A <code>String</code> containing the specified items joined with the separator between them, or
	 *         <code>null</code> if <code>null</code> is passed for the items.
	 */
	public static String join(String[] items, char separator) {
		return join(items, "" + separator);
	}

	/**
	 * Join the specified items into a single <code>String</code> where each item is separated by the specified
	 * separator.
	 * 
	 * @param items
	 *            The items to join.
	 * @param separator
	 *            The <code>String</code> to insert between each item when it is joined.
	 * 
	 * @return A <code>String</code> containing the specified items joined with the separator between them, or
	 *         <code>null</code> if <code>null</code> is passed for the items.
	 */
	public static String join(String[] items, String separator) {
		if (items == null) {
			return null;
		}
		List<String> listOfItems = Arrays.asList(items);
		return join(listOfItems, "" + separator);
	}

	/**
	 * Join the specified items into a single <code>String</code> where each item is separated by the specified
	 * separator.
	 * 
	 * @param items
	 *            The items to join.
	 * @param separator
	 *            The character to insert between each item when it is joined.
	 * 
	 * @return A <code>String</code> containing the specified items joined with the separator between them, or
	 *         <code>null</code> if <code>null</code> is passed for the items.
	 */
	public static String join(List<String> items, char separator) {
		return join(items, "" + separator);
	}

	/**
	 * Join the specified items into a single <code>String</code> where each item is separated by the specified
	 * separator.
	 * 
	 * @param items
	 *            The items to join.
	 * @param separator
	 *            The <code>String</code> to insert between each item when it is joined.
	 * 
	 * @return A <code>String</code> containing the specified items joined with the separator between them, or
	 *         <code>null</code> if <code>null</code> is passed for the items.
	 */
	public static String join(List<String> items, String separator) {
		if (items == null) {
			return null;
		}
		StringBuilder buf = new StringBuilder();
		for (String item : items) {
			buf.append(item).append(separator);
		}
		if (buf.length() > 0) { // remote last separator
			buf.delete(buf.lastIndexOf(separator), buf.length());
		}
		return buf.toString();
	}
}