package org.meanbean.util;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * Unit tests to test the functionality afforded by the StringUtils class.
 * 
 * @author Graham Williamson
 */
public class StringUtilsTest {

    private static final List<String> NULL_LIST = null;

    private static final String[] NULL_ARRAY = null;

    private static final String INPUT_ITEM_1 = "A";
    private static final String INPUT_ITEM_2 = "B";
    private static final String INPUT_ITEM_3 = "C";
    private static final String INPUT_ITEM_4 = "D";

    private static final List<String> INPUT_LIST = new ArrayList<String>();
    static {
        INPUT_LIST.add(INPUT_ITEM_1);
        INPUT_LIST.add(INPUT_ITEM_2);
        INPUT_LIST.add(INPUT_ITEM_3);
        INPUT_LIST.add(INPUT_ITEM_4);
    }

    private static final String[] INPUT_ARRAY = { INPUT_ITEM_1, INPUT_ITEM_2, INPUT_ITEM_3, INPUT_ITEM_4 };

    @Test
    public void shouldJoinListWithString() throws Exception {
        shouldJoinListWithString(INPUT_LIST, ",", expectedStringJoinedBy(","));
        shouldJoinListWithString(INPUT_LIST, ":", expectedStringJoinedBy(":"));
        shouldJoinListWithString(INPUT_LIST, ";", expectedStringJoinedBy(";"));
    }

    private void shouldJoinListWithString(List<String> list, String delimiter, String expected) {
        // Given - input parameters
        // When
        String actualResultString = StringUtils.join(list, delimiter);
        // Then
        assertThat(actualResultString, is(expected));
    }

    @Test
    public void shouldReturnNullWhenJoiningNullListWithString() {
        // Given
        String delimiter = ";";
        // When
        String actualResultString = StringUtils.join(NULL_LIST, delimiter);
        // Then
        assertNull("should return null", actualResultString);
    }

    @Test
    public void shouldJoinListWithCharacter() throws Exception {
        shouldJoinListWithCharacter(INPUT_LIST, ',', expectedStringJoinedBy(","));
        shouldJoinListWithCharacter(INPUT_LIST, ':', expectedStringJoinedBy(":"));
        shouldJoinListWithCharacter(INPUT_LIST, ';', expectedStringJoinedBy(";"));
    }

    private void shouldJoinListWithCharacter(List<String> list, char delimiter, String expected) {
        // Given - input parameters
        // When
        String actualResultString = StringUtils.join(list, delimiter);
        // Then
        assertThat(actualResultString, is(expected));
    }

    @Test
    public void shouldReturnNullWhenJoiningNullListWithCharacter() {
        // Given
        char delimiter = ';';
        // When
        String actualResultString = StringUtils.join(NULL_LIST, delimiter);
        // Then
        assertNull("should return null", actualResultString);
    }

    @Test
    public void shouldJoinArrayWithString() throws Exception {
        shouldJoinArrayWithString(INPUT_ARRAY, ",", expectedStringJoinedBy(","));
        shouldJoinArrayWithString(INPUT_ARRAY, ":", expectedStringJoinedBy(":"));
        shouldJoinArrayWithString(INPUT_ARRAY, ";", expectedStringJoinedBy(";"));
    }

    private void shouldJoinArrayWithString(String[] array, String delimiter, String expected) {
        // Given - input parameters
        // When
        String actualResultString = StringUtils.join(array, delimiter);
        // Then
        assertThat(actualResultString, is(expected));
    }

    @Test
    public void shouldReturnNullWhenJoiningNullArrayWithString() {
        // Given
        String delimiter = ";";
        // When
        String actualResultString = StringUtils.join(NULL_ARRAY, delimiter);
        // Then
        assertNull("should return null", actualResultString);
    }

    @Test
    public void shouldJoinArrayWithCharacter() {
        shouldJoinListWithCharacter(INPUT_ARRAY, ',', expectedStringJoinedBy(","));
        shouldJoinListWithCharacter(INPUT_ARRAY, ':', expectedStringJoinedBy(":"));
        shouldJoinListWithCharacter(INPUT_ARRAY, ';', expectedStringJoinedBy(";"));
    }

    private void shouldJoinListWithCharacter(String[] array, char delimiter, String expected) {
        // Given - input parameters
        // When
        String actualResultString = StringUtils.join(array, delimiter);
        // Then
        assertThat(actualResultString, is(expected));
    }

    @Test
    public void shouldReturnNullWhenJoiningNullArrayWithCharacter() {
        // Given
        char delimiter = ';';
        // When
        String actualResultString = StringUtils.join(NULL_ARRAY, delimiter);
        // Then
        assertNull("should return null", actualResultString);
    }

    private String expectedStringJoinedBy(String delimiter) {
        return INPUT_ITEM_1 + delimiter + INPUT_ITEM_2 + delimiter + INPUT_ITEM_3 + delimiter + INPUT_ITEM_4;
    }
}