package org.meanbean.bean.util;

import org.meanbean.bean.info.PropertyInformation;
import org.meanbean.util.ValidationHelper;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Affords functionality to filter PropertyInformation based on given criteria, such as a property's public visibility
 * (readable, writable or both).
 * 
 * @author Graham Williamson
 */
public final class PropertyInformationFilter {

	/**
	 * Filter criteria based on a property's public visibility: readable, writable or both.
	 * 
	 * @author Graham Williamson
	 */
	public enum PropertyVisibility {

		/**
		 * Publicly readable property.
		 */
		READABLE() {
			@Override
			boolean isRelevant(PropertyInformation propertyInformation) {
				return propertyInformation != null ? propertyInformation.isReadable() : false;
			}
		},

		/**
		 * Publicly writable property.
		 */
		WRITABLE() {
			@Override
			boolean isRelevant(PropertyInformation propertyInformation) {
				return propertyInformation != null ? propertyInformation.isWritable() : false;
			}
		},

		/**
		 * Publicly readable and publicly writable property.
		 */
		READABLE_WRITABLE() {
			@Override
			boolean isRelevant(PropertyInformation propertyInformation) {
				return propertyInformation != null ? propertyInformation.isReadableWritable() : false;
			}
		};

		/**
		 * Is the specified property relevant to this filter criteria?
		 * 
		 * @param propertyInformation
		 *            The property that will either be ignored or included.
		 * 
		 * @return <code>true</code> if the specified property is relevant to this filter criteria; <code>false</code>
		 *         otherwise.
		 */
		abstract boolean isRelevant(PropertyInformation propertyInformation);

	}

	/**
	 * Construct a new PropertyInformationFilter.
	 */
	private PropertyInformationFilter() {
		// Make non-instantiable
	}

	/**
	 * Filter the specified Collection of properties based on the specified filter, returning a Collection of properties
	 * that are deemed relevant by the filter.
	 * 
	 * @param properties
	 *            A Collection of PropertyInformation objects to be filtered.
	 * @param filter
	 *            The filter to be used. This filter will specify whether properties that are readable, writable or both
	 *            are relevant.
	 * 
	 * @return A Collection of PropertyInformation objects deemed relevant by the specified filter.
	 * 
	 * @throws IllegalArgumentException
	 *             If either of the required parameters are deemed illegal. For example, if either is null.
	 */
	public static Collection<PropertyInformation> filter(Collection<PropertyInformation> properties,
	        PropertyVisibility filter) throws IllegalArgumentException {
		ValidationHelper.ensureExists("properties", "filter Collection of properties", properties);
		ValidationHelper.ensureExists("filter", "filter Collection of properties", filter);
		Collection<PropertyInformation> result = new ArrayList<PropertyInformation>();
		for (PropertyInformation propertyInformation : properties) {
			if (filter.isRelevant(propertyInformation)) {
				result.add(propertyInformation);
			}
		}
		return result;
	}

}
