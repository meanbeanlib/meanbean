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
    public int nextInt(int bound) {
        while (true) {
            int next = nextInt();
            if (next < bound) {
                return next;
            }
        }
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
