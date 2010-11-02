package org.meanbean.bean.util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.meanbean.bean.info.PropertyInformation;
import org.meanbean.bean.info.PropertyInformationFactory;
import org.meanbean.bean.util.PropertyInformationFilter.PropertyVisibility;

public class PropertyInformationFilterTest {

	private static final Collection<PropertyInformation> EMPTY_PROPERTIES = new ArrayList<PropertyInformation>();

	private static final List<PropertyInformation> VALID_PROPERTIES = new ArrayList<PropertyInformation>() {
		private static final long serialVersionUID = 1L;
		{
			add(PropertyInformationFactory.create(null, false, true));
			add(PropertyInformationFactory.create(null, true, false));
			add(PropertyInformationFactory.create(null, true, true));
			add(PropertyInformationFactory.create(null, true, false));
			add(PropertyInformationFactory.create(null, false, true));
		}
	};

	@Test(expected = IllegalArgumentException.class)
	public void shouldPreventIllegalProperties() throws Exception {
		PropertyInformationFilter.filter(null, PropertyVisibility.READABLE);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldPreventIllegalFilter() throws Exception {
		PropertyInformationFilter.filter(EMPTY_PROPERTIES, null);
	}

	@Test
	public void shouldReturnEmptyCollectionWhenPassedEmptyCollection() throws Exception {
		for (PropertyVisibility filter : PropertyVisibility.values()) {
			assertThat("Incorrect properties for filter [" + filter + "].",
			        PropertyInformationFilter.filter(EMPTY_PROPERTIES, filter).isEmpty(), is(true));
		}
	}

	@Test
	public void shouldReturnEmptyCollectionWhenNoWritableMatchesAreFound() throws Exception {
		final boolean writable = false;
		Collection<PropertyInformation> inputProperties = new ArrayList<PropertyInformation>();
		inputProperties.add(PropertyInformationFactory.create(null, true, writable));
		inputProperties.add(PropertyInformationFactory.create(null, false, writable));
		inputProperties.add(PropertyInformationFactory.create(null, true, writable));
		Collection<PropertyInformation> filteredProperties = PropertyInformationFilter.filter(inputProperties,
		        PropertyVisibility.WRITABLE);
		assertThat("Incorrect filtered properties.", filteredProperties.isEmpty(), is(true));
	}

	@Test
	public void shouldReturnEmptyCollectionWhenNoReadableMatchesAreFound() throws Exception {
		final boolean readable = false;
		Collection<PropertyInformation> inputProperties = new ArrayList<PropertyInformation>();
		inputProperties.add(PropertyInformationFactory.create(null, readable, true));
		inputProperties.add(PropertyInformationFactory.create(null, readable, false));
		inputProperties.add(PropertyInformationFactory.create(null, readable, true));
		Collection<PropertyInformation> filteredProperties = PropertyInformationFilter.filter(inputProperties,
		        PropertyVisibility.READABLE);
		assertThat("Incorrect filtered properties.", filteredProperties.isEmpty(), is(true));
	}

	@Test
	public void shouldReturnEmptyCollectionWhenNoReadableWritableMatchesAreFound() throws Exception {
		Collection<PropertyInformation> inputProperties = new ArrayList<PropertyInformation>();
		inputProperties.add(PropertyInformationFactory.create(null, true, false));
		inputProperties.add(PropertyInformationFactory.create(null, true, false));
		inputProperties.add(PropertyInformationFactory.create(null, false, true));
		Collection<PropertyInformation> filteredProperties = PropertyInformationFilter.filter(inputProperties,
		        PropertyVisibility.READABLE_WRITABLE);
		assertThat("Incorrect filtered properties.", filteredProperties.isEmpty(), is(true));
	}

	@Test
	public void shouldReturnOnlyWritablePropertiesWhenRequested() throws Exception {
		Collection<PropertyInformation> filteredProperties = PropertyInformationFilter.filter(VALID_PROPERTIES,
		        PropertyVisibility.WRITABLE);
		assertThat("Incorrect filtered properties.", filteredProperties.size(), is(3));
		Iterator<PropertyInformation> iterator = filteredProperties.iterator();
		assertThat("Incorrect filtered properties.", iterator.next(), is(VALID_PROPERTIES.get(0)));
		assertThat("Incorrect filtered properties.", iterator.next(), is(VALID_PROPERTIES.get(2)));
		assertThat("Incorrect filtered properties.", iterator.next(), is(VALID_PROPERTIES.get(4)));
	}

	@Test
	public void shouldReturnOnlyReadablePropertiesWhenRequested() throws Exception {
		Collection<PropertyInformation> filteredProperties = PropertyInformationFilter.filter(VALID_PROPERTIES,
		        PropertyVisibility.READABLE);
		assertThat("Incorrect filtered properties.", filteredProperties.size(), is(3));
		Iterator<PropertyInformation> iterator = filteredProperties.iterator();
		assertThat("Incorrect filtered properties.", iterator.next(), is(VALID_PROPERTIES.get(1)));
		assertThat("Incorrect filtered properties.", iterator.next(), is(VALID_PROPERTIES.get(2)));
		assertThat("Incorrect filtered properties.", iterator.next(), is(VALID_PROPERTIES.get(3)));
	}

	@Test
	public void shouldReturnOnlyReadableWritablePropertiesWhenRequested() throws Exception {
		Collection<PropertyInformation> filteredProperties = PropertyInformationFilter.filter(VALID_PROPERTIES,
		        PropertyVisibility.READABLE_WRITABLE);
		assertThat("Incorrect filtered properties.", filteredProperties.size(), is(1));
		assertThat("Incorrect filtered properties.", filteredProperties.iterator().next(), is(VALID_PROPERTIES.get(2)));
	}
}