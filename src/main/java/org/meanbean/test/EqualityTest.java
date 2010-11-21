package org.meanbean.test;

/**
 * Equality test of two objects.
 * 
 * @author Graham Williamson
 */
public enum EqualityTest {

	/**
	 * Test the logical equality of two objects (x.equals(y)).
	 */
	LOGICAL {
		@Override
		public boolean test(Object x, Object y) {
			return x.equals(y);
		}
	},

	/**
	 * Test the absolute equality of two objects (x == y).
	 */
	ABSOLUTE {
		@Override
		public boolean test(Object x, Object y) {
			return x == y;
		}
	};

	/**
	 * Is object x equal to object y.
	 * 
	 * @param x
	 *            The first object to compare.
	 * @param y
	 *            The second object to compare.
	 * 
	 * @return <code>true</code> if the objects are considered equal; <code>false</code> otherwise.
	 */
	public abstract boolean test(Object x, Object y);
}