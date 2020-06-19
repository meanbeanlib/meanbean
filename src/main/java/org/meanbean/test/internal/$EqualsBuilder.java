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

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.meanbean.test.internal;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Assists in implementing {@link Object#equals(Object)} methods.</p>
 *
 * <p> This class provides methods to build a good equals method for any
 * class. It follows rules laid out in
 * <a href="http://www.oracle.com/technetwork/java/effectivejava-136174.html">Effective Java</a>
 * , by Joshua Bloch. In particular the rule for comparing <code>doubles</code>,
 * <code>floats</code>, and arrays can be tricky. Also, making sure that
 * <code>equals()</code> and <code>hashCode()</code> are consistent can be
 * difficult.</p>
 *
 * <p>Two Objects that compare as equals must generate the same hash code,
 * but two Objects with the same hash code do not have to be equal.</p>
 *
 * <p>All relevant fields should be included in the calculation of equals.
 * Derived fields may be ignored. In particular, any field used in
 * generating a hash code must be used in the equals method, and vice
 * versa.</p>
 *
 * <p>Typical use for the code is as follows:</p>
 * <pre>
 * public boolean equals(Object obj) {
 *   if (obj == null) { return false; }
 *   if (obj == this) { return true; }
 *   if (obj.getClass() != getClass()) {
 *     return false;
 *   }
 *   MyClass rhs = (MyClass) obj;
 *   return new EqualsBuilder()
 *                 .appendSuper(super.equals(obj))
 *                 .append(field1, rhs.field1)
 *                 .append(field2, rhs.field2)
 *                 .append(field3, rhs.field3)
 *                 .isEquals();
 *  }
 * </pre>
 *
 * <p> Alternatively, there is a method that uses reflection to determine
 * the fields to test. Because these fields are usually private, the method,
 * <code>reflectionEquals</code>, uses <code>AccessibleObject.setAccessible</code> to
 * change the visibility of the fields. This will fail under a security
 * manager, unless the appropriate permissions are set up correctly. It is
 * also slower than testing explicitly.  Non-primitive fields are compared using
 * <code>equals()</code>.</p>
 *
 * <p> A typical invocation for this method would look like:</p>
 * <pre>
 * public boolean equals(Object obj) {
 *   return EqualsBuilder.reflectionEquals(this, obj);
 * }
 * </pre>
 *
 * <p>The {@link EqualsExclude} annotation can be used to exclude fields from being
 * used by the <code>reflectionEquals</code> methods.</p>
 *
 * @since 1.0
 */
public class $EqualsBuilder {

	/**
	 * If the fields tested are equals.
	 * The default value is <code>true</code>.
	 */
	private boolean isEquals = true;

	private List<Class<?>> bypassReflectionClasses;

	/**
	 * <p>Constructor for EqualsBuilder.</p>
	 *
	 * <p>Starts off assuming that equals is <code>true</code>.</p>
	 * @see Object#equals(Object)
	 */
	public $EqualsBuilder() {
		// set up default classes to bypass reflection for
		bypassReflectionClasses = new ArrayList<>();
		bypassReflectionClasses.add(String.class); //hashCode field being lazy but not transient
	}

	public static boolean objectsEqual(Object lhs, Object rhs) {
		return new $EqualsBuilder()
				.append(lhs, rhs)
				.isEquals();
	}

	//-------------------------------------------------------------------------

	/**
	 * <p>Set <code>Class</code>es whose instances should be compared by calling their <code>equals</code>
	 * although being in recursive mode. So the fields of theses classes will not be compared recursively by reflection.</p>
	 *
	 * <p>Here you should name classes having non-transient fields which are cache fields being set lazily.<br>
	 * Prominent example being {@link String} class with its hash code cache field. Due to the importance
	 * of the <code>String</code> class, it is included in the default bypasses classes. Usually, if you use
	 * your own set of classes here, remember to include <code>String</code> class, too.</p>
	 * @param bypassReflectionClasses  classes to bypass reflection test
	 * @return EqualsBuilder - used to chain calls.
	 * @since 3.8
	 */
	public $EqualsBuilder setBypassReflectionClasses(List<Class<?>> bypassReflectionClasses) {
		this.bypassReflectionClasses = bypassReflectionClasses;
		return this;
	}

	//-------------------------------------------------------------------------

	/**
	 * <p>Adds the result of <code>super.equals()</code> to this builder.</p>
	 *
	 * @param superEquals  the result of calling <code>super.equals()</code>
	 * @return EqualsBuilder - used to chain calls.
	 * @since 2.0
	 */
	public $EqualsBuilder appendSuper(final boolean superEquals) {
		if (!isEquals) {
			return this;
		}
		isEquals = superEquals;
		return this;
	}

	//-------------------------------------------------------------------------

	/**
	 * <p>Test if two <code>Object</code>s are equal using either
	 * #{@link #reflectionAppend(Object, Object)}, if object are non
	 * primitives (or wrapper of primitives) or if field <code>testRecursive</code>
	 * is set to <code>false</code>. Otherwise, using their
	 * <code>equals</code> method.</p>
	 *
	 * @param lhs  the left hand object
	 * @param rhs  the right hand object
	 * @return EqualsBuilder - used to chain calls.
	 */
	public $EqualsBuilder append(final Object lhs, final Object rhs) {
		if (!isEquals) {
			return this;
		}
		if (lhs == rhs) {
			return this;
		}
		if (lhs == null || rhs == null) {
			this.setEquals(false);
			return this;
		}
		final Class<?> lhsClass = lhs.getClass();
		if (lhsClass.isArray()) {
			// factor out array case in order to keep method small enough
			// to be inlined
			appendArray(lhs, rhs);
		} else {
			isEquals = lhs.equals(rhs);
		}
		return this;
	}

	/**
	 * <p>Test if an <code>Object</code> is equal to an array.</p>
	 *
	 * @param lhs  the left hand object, an array
	 * @param rhs  the right hand object
	 */
	private void appendArray(final Object lhs, final Object rhs) {
		// First we compare different dimensions, for example: a boolean[][] to a boolean[]
		// then we 'Switch' on type of array, to dispatch to the correct handler
		// This handles multi dimensional arrays of the same depth
		if (lhs.getClass() != rhs.getClass()) {
			this.setEquals(false);
		} else if (lhs instanceof long[]) {
			append((long[]) lhs, (long[]) rhs);
		} else if (lhs instanceof int[]) {
			append((int[]) lhs, (int[]) rhs);
		} else if (lhs instanceof short[]) {
			append((short[]) lhs, (short[]) rhs);
		} else if (lhs instanceof char[]) {
			append((char[]) lhs, (char[]) rhs);
		} else if (lhs instanceof byte[]) {
			append((byte[]) lhs, (byte[]) rhs);
		} else if (lhs instanceof double[]) {
			append((double[]) lhs, (double[]) rhs);
		} else if (lhs instanceof float[]) {
			append((float[]) lhs, (float[]) rhs);
		} else if (lhs instanceof boolean[]) {
			append((boolean[]) lhs, (boolean[]) rhs);
		} else {
			// Not an array of primitives
			append((Object[]) lhs, (Object[]) rhs);
		}
	}

	/**
	 * <p>
	 * Test if two <code>long</code> s are equal.
	 * </p>
	 *
	 * @param lhs
	 *                  the left hand <code>long</code>
	 * @param rhs
	 *                  the right hand <code>long</code>
	 * @return EqualsBuilder - used to chain calls.
	 */
	public $EqualsBuilder append(final long lhs, final long rhs) {
		if (!isEquals) {
			return this;
		}
		isEquals = lhs == rhs;
		return this;
	}

	/**
	 * <p>Test if two <code>int</code>s are equal.</p>
	 *
	 * @param lhs  the left hand <code>int</code>
	 * @param rhs  the right hand <code>int</code>
	 * @return EqualsBuilder - used to chain calls.
	 */
	public $EqualsBuilder append(final int lhs, final int rhs) {
		if (!isEquals) {
			return this;
		}
		isEquals = lhs == rhs;
		return this;
	}

	/**
	 * <p>Test if two <code>short</code>s are equal.</p>
	 *
	 * @param lhs  the left hand <code>short</code>
	 * @param rhs  the right hand <code>short</code>
	 * @return EqualsBuilder - used to chain calls.
	 */
	public $EqualsBuilder append(final short lhs, final short rhs) {
		if (!isEquals) {
			return this;
		}
		isEquals = lhs == rhs;
		return this;
	}

	/**
	 * <p>Test if two <code>char</code>s are equal.</p>
	 *
	 * @param lhs  the left hand <code>char</code>
	 * @param rhs  the right hand <code>char</code>
	 * @return EqualsBuilder - used to chain calls.
	 */
	public $EqualsBuilder append(final char lhs, final char rhs) {
		if (!isEquals) {
			return this;
		}
		isEquals = lhs == rhs;
		return this;
	}

	/**
	 * <p>Test if two <code>byte</code>s are equal.</p>
	 *
	 * @param lhs  the left hand <code>byte</code>
	 * @param rhs  the right hand <code>byte</code>
	 * @return EqualsBuilder - used to chain calls.
	 */
	public $EqualsBuilder append(final byte lhs, final byte rhs) {
		if (!isEquals) {
			return this;
		}
		isEquals = lhs == rhs;
		return this;
	}

	/**
	 * <p>Test if two <code>double</code>s are equal by testing that the
	 * pattern of bits returned by <code>doubleToLong</code> are equal.</p>
	 *
	 * <p>This handles NaNs, Infinities, and <code>-0.0</code>.</p>
	 *
	 * <p>It is compatible with the hash code generated by
	 * <code>HashCodeBuilder</code>.</p>
	 *
	 * @param lhs  the left hand <code>double</code>
	 * @param rhs  the right hand <code>double</code>
	 * @return EqualsBuilder - used to chain calls.
	 */
	public $EqualsBuilder append(final double lhs, final double rhs) {
		if (!isEquals) {
			return this;
		}
		return append(Double.doubleToLongBits(lhs), Double.doubleToLongBits(rhs));
	}

	/**
	 * <p>Test if two <code>float</code>s are equal by testing that the
	 * pattern of bits returned by doubleToLong are equal.</p>
	 *
	 * <p>This handles NaNs, Infinities, and <code>-0.0</code>.</p>
	 *
	 * <p>It is compatible with the hash code generated by
	 * <code>HashCodeBuilder</code>.</p>
	 *
	 * @param lhs  the left hand <code>float</code>
	 * @param rhs  the right hand <code>float</code>
	 * @return EqualsBuilder - used to chain calls.
	 */
	public $EqualsBuilder append(final float lhs, final float rhs) {
		if (!isEquals) {
			return this;
		}
		return append(Float.floatToIntBits(lhs), Float.floatToIntBits(rhs));
	}

	/**
	 * <p>Test if two <code>booleans</code>s are equal.</p>
	 *
	 * @param lhs  the left hand <code>boolean</code>
	 * @param rhs  the right hand <code>boolean</code>
	 * @return EqualsBuilder - used to chain calls.
	  */
	public $EqualsBuilder append(final boolean lhs, final boolean rhs) {
		if (!isEquals) {
			return this;
		}
		isEquals = lhs == rhs;
		return this;
	}

	/**
	 * <p>Performs a deep comparison of two <code>Object</code> arrays.</p>
	 *
	 * <p>This also will be called for the top level of
	 * multi-dimensional, ragged, and multi-typed arrays.</p>
	 *
	 * <p>Note that this method does not compare the type of the arrays; it only
	 * compares the contents.</p>
	 *
	 * @param lhs  the left hand <code>Object[]</code>
	 * @param rhs  the right hand <code>Object[]</code>
	 * @return EqualsBuilder - used to chain calls.
	 */
	public $EqualsBuilder append(final Object[] lhs, final Object[] rhs) {
		if (!isEquals) {
			return this;
		}
		if (lhs == rhs) {
			return this;
		}
		if (lhs == null || rhs == null) {
			this.setEquals(false);
			return this;
		}
		if (lhs.length != rhs.length) {
			this.setEquals(false);
			return this;
		}
		for (int i = 0; i < lhs.length && isEquals; ++i) {
			append(lhs[i], rhs[i]);
		}
		return this;
	}

	/**
	 * <p>Deep comparison of array of <code>long</code>. Length and all
	 * values are compared.</p>
	 *
	 * <p>The method {@link #append(long, long)} is used.</p>
	 *
	 * @param lhs  the left hand <code>long[]</code>
	 * @param rhs  the right hand <code>long[]</code>
	 * @return EqualsBuilder - used to chain calls.
	 */
	public $EqualsBuilder append(final long[] lhs, final long[] rhs) {
		if (!isEquals) {
			return this;
		}
		if (lhs == rhs) {
			return this;
		}
		if (lhs == null || rhs == null) {
			this.setEquals(false);
			return this;
		}
		if (lhs.length != rhs.length) {
			this.setEquals(false);
			return this;
		}
		for (int i = 0; i < lhs.length && isEquals; ++i) {
			append(lhs[i], rhs[i]);
		}
		return this;
	}

	/**
	 * <p>Deep comparison of array of <code>int</code>. Length and all
	 * values are compared.</p>
	 *
	 * <p>The method {@link #append(int, int)} is used.</p>
	 *
	 * @param lhs  the left hand <code>int[]</code>
	 * @param rhs  the right hand <code>int[]</code>
	 * @return EqualsBuilder - used to chain calls.
	 */
	public $EqualsBuilder append(final int[] lhs, final int[] rhs) {
		if (!isEquals) {
			return this;
		}
		if (lhs == rhs) {
			return this;
		}
		if (lhs == null || rhs == null) {
			this.setEquals(false);
			return this;
		}
		if (lhs.length != rhs.length) {
			this.setEquals(false);
			return this;
		}
		for (int i = 0; i < lhs.length && isEquals; ++i) {
			append(lhs[i], rhs[i]);
		}
		return this;
	}

	/**
	 * <p>Deep comparison of array of <code>short</code>. Length and all
	 * values are compared.</p>
	 *
	 * <p>The method {@link #append(short, short)} is used.</p>
	 *
	 * @param lhs  the left hand <code>short[]</code>
	 * @param rhs  the right hand <code>short[]</code>
	 * @return EqualsBuilder - used to chain calls.
	 */
	public $EqualsBuilder append(final short[] lhs, final short[] rhs) {
		if (!isEquals) {
			return this;
		}
		if (lhs == rhs) {
			return this;
		}
		if (lhs == null || rhs == null) {
			this.setEquals(false);
			return this;
		}
		if (lhs.length != rhs.length) {
			this.setEquals(false);
			return this;
		}
		for (int i = 0; i < lhs.length && isEquals; ++i) {
			append(lhs[i], rhs[i]);
		}
		return this;
	}

	/**
	 * <p>Deep comparison of array of <code>char</code>. Length and all
	 * values are compared.</p>
	 *
	 * <p>The method {@link #append(char, char)} is used.</p>
	 *
	 * @param lhs  the left hand <code>char[]</code>
	 * @param rhs  the right hand <code>char[]</code>
	 * @return EqualsBuilder - used to chain calls.
	 */
	public $EqualsBuilder append(final char[] lhs, final char[] rhs) {
		if (!isEquals) {
			return this;
		}
		if (lhs == rhs) {
			return this;
		}
		if (lhs == null || rhs == null) {
			this.setEquals(false);
			return this;
		}
		if (lhs.length != rhs.length) {
			this.setEquals(false);
			return this;
		}
		for (int i = 0; i < lhs.length && isEquals; ++i) {
			append(lhs[i], rhs[i]);
		}
		return this;
	}

	/**
	 * <p>Deep comparison of array of <code>byte</code>. Length and all
	 * values are compared.</p>
	 *
	 * <p>The method {@link #append(byte, byte)} is used.</p>
	 *
	 * @param lhs  the left hand <code>byte[]</code>
	 * @param rhs  the right hand <code>byte[]</code>
	 * @return EqualsBuilder - used to chain calls.
	 */
	public $EqualsBuilder append(final byte[] lhs, final byte[] rhs) {
		if (!isEquals) {
			return this;
		}
		if (lhs == rhs) {
			return this;
		}
		if (lhs == null || rhs == null) {
			this.setEquals(false);
			return this;
		}
		if (lhs.length != rhs.length) {
			this.setEquals(false);
			return this;
		}
		for (int i = 0; i < lhs.length && isEquals; ++i) {
			append(lhs[i], rhs[i]);
		}
		return this;
	}

	/**
	 * <p>Deep comparison of array of <code>double</code>. Length and all
	 * values are compared.</p>
	 *
	 * <p>The method {@link #append(double, double)} is used.</p>
	 *
	 * @param lhs  the left hand <code>double[]</code>
	 * @param rhs  the right hand <code>double[]</code>
	 * @return EqualsBuilder - used to chain calls.
	 */
	public $EqualsBuilder append(final double[] lhs, final double[] rhs) {
		if (!isEquals) {
			return this;
		}
		if (lhs == rhs) {
			return this;
		}
		if (lhs == null || rhs == null) {
			this.setEquals(false);
			return this;
		}
		if (lhs.length != rhs.length) {
			this.setEquals(false);
			return this;
		}
		for (int i = 0; i < lhs.length && isEquals; ++i) {
			append(lhs[i], rhs[i]);
		}
		return this;
	}

	/**
	 * <p>Deep comparison of array of <code>float</code>. Length and all
	 * values are compared.</p>
	 *
	 * <p>The method {@link #append(float, float)} is used.</p>
	 *
	 * @param lhs  the left hand <code>float[]</code>
	 * @param rhs  the right hand <code>float[]</code>
	 * @return EqualsBuilder - used to chain calls.
	 */
	public $EqualsBuilder append(final float[] lhs, final float[] rhs) {
		if (!isEquals) {
			return this;
		}
		if (lhs == rhs) {
			return this;
		}
		if (lhs == null || rhs == null) {
			this.setEquals(false);
			return this;
		}
		if (lhs.length != rhs.length) {
			this.setEquals(false);
			return this;
		}
		for (int i = 0; i < lhs.length && isEquals; ++i) {
			append(lhs[i], rhs[i]);
		}
		return this;
	}

	/**
	 * <p>Deep comparison of array of <code>boolean</code>. Length and all
	 * values are compared.</p>
	 *
	 * <p>The method {@link #append(boolean, boolean)} is used.</p>
	 *
	 * @param lhs  the left hand <code>boolean[]</code>
	 * @param rhs  the right hand <code>boolean[]</code>
	 * @return EqualsBuilder - used to chain calls.
	 */
	public $EqualsBuilder append(final boolean[] lhs, final boolean[] rhs) {
		if (!isEquals) {
			return this;
		}
		if (lhs == rhs) {
			return this;
		}
		if (lhs == null || rhs == null) {
			this.setEquals(false);
			return this;
		}
		if (lhs.length != rhs.length) {
			this.setEquals(false);
			return this;
		}
		for (int i = 0; i < lhs.length && isEquals; ++i) {
			append(lhs[i], rhs[i]);
		}
		return this;
	}

	/**
	 * <p>Returns <code>true</code> if the fields that have been checked
	 * are all equal.</p>
	 *
	 * @return boolean
	 */
	public boolean isEquals() {
		return this.isEquals;
	}

	/**
	 * <p>Returns <code>true</code> if the fields that have been checked
	 * are all equal.</p>
	 *
	 * @return <code>true</code> if all of the fields that have been checked
	 *         are equal, <code>false</code> otherwise.
	 *
	 * @since 3.0
	 */
	public Boolean build() {
		return Boolean.valueOf(isEquals());
	}

	/**
	 * Sets the <code>isEquals</code> value.
	 *
	 * @param isEquals The value to set.
	 * @since 2.1
	 */
	protected void setEquals(final boolean isEquals) {
		this.isEquals = isEquals;
	}

	/**
	 * Reset the EqualsBuilder so you can use the same object again
	 * @since 2.5
	 */
	public void reset() {
		this.isEquals = true;
	}
}
