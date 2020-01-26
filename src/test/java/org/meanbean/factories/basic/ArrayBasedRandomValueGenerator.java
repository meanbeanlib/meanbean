package org.meanbean.factories.basic;

import org.meanbean.util.RandomValueGenerator;

/**
 * Concrete RandomValueGenerator that creates values based on an array of values passed to it during construction. This
 * should only be used for testing.
 * 
 * @author Graham Williamson
 */
public class ArrayBasedRandomValueGenerator implements RandomValueGenerator {

	private final byte[][] bytes;

	private int byteIdx;

	private final int[] ints;

	private int intIdx;

	private final long[] longs;

	private int longIdx;

	private final float[] floats;

	private int floatIdx;

	private final double[] doubles;

	private int doubleIdx;

	private final boolean[] booleans;

	private int booleanIdx;

	public ArrayBasedRandomValueGenerator(byte[][] bytes, int[] ints, long[] longs, float[] floats, double[] doubles,
	        boolean[] booleans) {
		this.bytes = bytes;
		this.ints = ints;
		this.longs = longs;
		this.floats = floats;
		this.doubles = doubles;
		this.booleans = booleans;
	}

	@Override
    public byte nextByte() {
		return nextBytes(1)[0];
	}

	@Override
    public byte[] nextBytes(int size) {
		return bytes[byteIdx++];
	}

	@Override
    public int nextInt() {
		return ints[intIdx++];
	}

	@Override
    public long nextLong() {
		return longs[longIdx++];
	}

	@Override
    public float nextFloat() {
		return floats[floatIdx++];
	}

	@Override
    public double nextDouble() {
		return doubles[doubleIdx++];
	}

	@Override
    public boolean nextBoolean() {
		return booleans[booleanIdx++];
	}
}