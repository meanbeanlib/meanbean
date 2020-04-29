/*-
 * ​​​
 * meanbean
 * ⁣⁣⁣
 * Copyright (C) 2010 - 2020 the original author or authors.
 * ⁣⁣⁣
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ﻿﻿﻿﻿﻿
 */

package org.meanbean.bean.util;

import org.meanbean.bean.info.PropertyInformation;
import org.meanbean.test.Configuration;
import org.meanbean.util.ValidationHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
	public enum PropertyVisibility implements Predicate<PropertyInformation> {

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

        @Override
        public boolean test(PropertyInformation propertyInformation) {
            return isRelevant(propertyInformation);
        }
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
	public static List<PropertyInformation> filter(Collection<PropertyInformation> properties,
	        Predicate<PropertyInformation> filter) throws IllegalArgumentException {
		ValidationHelper.ensureExists("properties", "filter Collection of properties", properties);
		ValidationHelper.ensureExists("filter", "filter Collection of properties", filter);
		List<PropertyInformation> result = new ArrayList<PropertyInformation>();
		for (PropertyInformation propertyInformation : properties) {
			if (filter.test(propertyInformation)) {
				result.add(propertyInformation);
			}
        }
        return result;
    }

    public static List<PropertyInformation> filter(Collection<PropertyInformation> properties, Configuration configuration) {
        ValidationHelper.ensureExists("properties", "filter Collection of properties", properties);
        return properties.stream()
                .filter(PropertyVisibility.READABLE_WRITABLE)
                .filter(property -> configuration == null || !configuration.isIgnoredProperty(property.getName()))
                .collect(Collectors.toList());
    }
}
