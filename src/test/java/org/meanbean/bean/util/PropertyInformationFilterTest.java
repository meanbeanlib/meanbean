package org.meanbean.bean.util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertTrue;
import static org.meanbean.bean.info.PropertyInformationFactory.createReadOnlyProperty;
import static org.meanbean.bean.info.PropertyInformationFactory.createReadWriteProperty;
import static org.meanbean.bean.info.PropertyInformationFactory.createWriteOnlyProperty;
import static org.meanbean.bean.util.PropertyInformationFilter.filter;
import static org.meanbean.bean.util.PropertyInformationFilter.PropertyVisibility.READABLE;
import static org.meanbean.bean.util.PropertyInformationFilter.PropertyVisibility.READABLE_WRITABLE;
import static org.meanbean.bean.util.PropertyInformationFilter.PropertyVisibility.WRITABLE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.meanbean.bean.info.PropertyInformation;
import org.meanbean.bean.util.PropertyInformationFilter.PropertyVisibility;

public class PropertyInformationFilterTest {

    private static final Collection<PropertyInformation> EMPTY_PROPERTIES = Collections.emptyList();

    private static final PropertyInformation READ_ONLY_PROPERTY_1 = createReadOnlyProperty("R/O Prop 1");
    private static final PropertyInformation READ_ONLY_PROPERTY_2 = createReadOnlyProperty("R/O Prop 2");
    private static final PropertyInformation WRITE_ONLY_PROPERTY_1 = createWriteOnlyProperty("W/O Prop 1");
    private static final PropertyInformation WRITE_ONLY_PROPERTY_2 = createWriteOnlyProperty("W/O Prop 2");
    private static final PropertyInformation READ_WRITE_PROPERTY_1 = createReadWriteProperty("W/R Prop 1");
    private static final PropertyInformation READ_WRITE_PROPERTY_2 = createReadWriteProperty("W/R Prop 2");

    private static final List<PropertyInformation> VALID_PROPERTIES = new ArrayList<PropertyInformation>();
    static {
        VALID_PROPERTIES.add(READ_ONLY_PROPERTY_1);
        VALID_PROPERTIES.add(READ_ONLY_PROPERTY_2);
        VALID_PROPERTIES.add(WRITE_ONLY_PROPERTY_1);
        VALID_PROPERTIES.add(WRITE_ONLY_PROPERTY_2);
        VALID_PROPERTIES.add(READ_WRITE_PROPERTY_1);
        VALID_PROPERTIES.add(READ_WRITE_PROPERTY_2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldPreventIllegalProperties() throws Exception {
        // Given
        Collection<PropertyInformation> nullProperties = null;
        PropertyVisibility propertyVisibility = READABLE;
        // When
        filter(nullProperties, propertyVisibility);
        // Then - throw an IllegalArgumentException
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldPreventIllegalFilter() throws Exception {
        // Given
        PropertyVisibility nullPropertyVisibility = null;
        // When
        filter(EMPTY_PROPERTIES, nullPropertyVisibility);
        // Then - throw an IllegalArgumentException
    }

    @Test
    public void shouldReturnEmptyCollectionWhenPassedEmptyCollection() throws Exception {
        shouldReturnEmptyCollectionWhenPassedEmptyCollection(READABLE);
        shouldReturnEmptyCollectionWhenPassedEmptyCollection(WRITABLE);
        shouldReturnEmptyCollectionWhenPassedEmptyCollection(READABLE_WRITABLE);
    }

    private void shouldReturnEmptyCollectionWhenPassedEmptyCollection(PropertyVisibility propertyVisibility)
            throws Exception {
        assertTrue("Expected empty result.", filter(EMPTY_PROPERTIES, propertyVisibility).isEmpty());
    }

    @Test
    public void shouldReturnEmptyCollectionWhenNoWritableMatchesAreFound() throws Exception {
        // Given
        Collection<PropertyInformation> nonWritableProperties = new ArrayList<PropertyInformation>();
        nonWritableProperties.add(READ_ONLY_PROPERTY_1);
        nonWritableProperties.add(READ_ONLY_PROPERTY_2);
        // When
        Collection<PropertyInformation> filteredProperties = filter(nonWritableProperties, WRITABLE);
        // Then
        assertTrue("Expected empty result.", filteredProperties.isEmpty());
    }

    @Test
    public void shouldReturnEmptyCollectionWhenNoReadableMatchesAreFound() throws Exception {
        // Given
        Collection<PropertyInformation> nonReadableProperties = new ArrayList<PropertyInformation>();
        nonReadableProperties.add(WRITE_ONLY_PROPERTY_1);
        nonReadableProperties.add(WRITE_ONLY_PROPERTY_2);
        // When
        Collection<PropertyInformation> filteredProperties = filter(nonReadableProperties, READABLE);
        // Then
        assertTrue("Expected empty result.", filteredProperties.isEmpty());
    }

    @Test
    public void shouldReturnEmptyCollectionWhenNoReadableWritableMatchesAreFound() throws Exception {
        // Given
        Collection<PropertyInformation> inputProperties = new ArrayList<PropertyInformation>();
        inputProperties.add(READ_ONLY_PROPERTY_1);
        inputProperties.add(READ_ONLY_PROPERTY_2);
        inputProperties.add(WRITE_ONLY_PROPERTY_1);
        inputProperties.add(WRITE_ONLY_PROPERTY_2);
        // When
        Collection<PropertyInformation> filteredProperties = filter(inputProperties, READABLE_WRITABLE);
        // Then
        assertTrue("Expected empty result.", filteredProperties.isEmpty());
    }

    @Test
    public void shouldReturnOnlyWritablePropertiesWhenRequested() throws Exception {
        // Given
        // When
        Collection<PropertyInformation> filteredProperties = filter(VALID_PROPERTIES, WRITABLE);
        // Then
        assertThat("Incorrect number of properties.", filteredProperties.size(), is(4));
        List<PropertyInformation> filteredPropertiesList = new ArrayList<PropertyInformation>(filteredProperties);
        assertThat("Incorrect filtered properties.", filteredPropertiesList.get(0), is(WRITE_ONLY_PROPERTY_1));
        assertThat("Incorrect filtered properties.", filteredPropertiesList.get(1), is(WRITE_ONLY_PROPERTY_2));
        assertThat("Incorrect filtered properties.", filteredPropertiesList.get(2), is(READ_WRITE_PROPERTY_1));
        assertThat("Incorrect filtered properties.", filteredPropertiesList.get(3), is(READ_WRITE_PROPERTY_2));
    }

    @Test
    public void shouldReturnOnlyReadablePropertiesWhenRequested() throws Exception {
        // Given
        // When
        Collection<PropertyInformation> filteredProperties = filter(VALID_PROPERTIES, READABLE);
        // Then
        assertThat("Incorrect filtered properties.", filteredProperties.size(), is(4));
        List<PropertyInformation> filteredPropertiesList = new ArrayList<PropertyInformation>(filteredProperties);
        assertThat("Incorrect filtered properties.", filteredPropertiesList.get(0), is(READ_ONLY_PROPERTY_1));
        assertThat("Incorrect filtered properties.", filteredPropertiesList.get(1), is(READ_ONLY_PROPERTY_2));
        assertThat("Incorrect filtered properties.", filteredPropertiesList.get(2), is(READ_WRITE_PROPERTY_1));
        assertThat("Incorrect filtered properties.", filteredPropertiesList.get(3), is(READ_WRITE_PROPERTY_2));
    }

    @Test
    public void shouldReturnOnlyReadableWritablePropertiesWhenRequested() throws Exception {
        // Given
        // When
        Collection<PropertyInformation> filteredProperties = filter(VALID_PROPERTIES, READABLE_WRITABLE);
        // Then
        assertThat("Incorrect filtered properties.", filteredProperties.size(), is(2));
        List<PropertyInformation> filteredPropertiesList = new ArrayList<PropertyInformation>(filteredProperties);
        assertThat("Incorrect filtered properties.", filteredPropertiesList.get(0), is(READ_WRITE_PROPERTY_1));
        assertThat("Incorrect filtered properties.", filteredPropertiesList.get(1), is(READ_WRITE_PROPERTY_2));
    }
}