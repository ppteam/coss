package com.googlecode.coss.common.utils.lang;

/**
 * <p>
 * Array Operation
 * </p>
 */
public class ArrayUtils {

	/**
	 * <p>
	 * Convert Integer[] to int[]
	 * </p>
	 * 
	 * @param integerArray
	 * @return
	 */
	public static int[] toIntArray(Integer[] integerArray) {
		int size = integerArray.length;
		int[] intArray = new int[size];
		for (int i = 0; i < size; i++) {
			intArray[i] = integerArray[i];
		}
		return intArray;
	}

	/**
	 * <p>
	 * Convert input string array to integer array
	 * </p>
	 * 
	 * @param strs
	 *            string array to convert
	 * @return
	 */
	public static int[] toIntArray(String[] strs) {
		int size = strs.length;
		int[] intArray = new int[size];
		for (int i = 0; i < size; i++) {
			intArray[i] = NumberUtils.toInt(strs[i]);
		}
		return intArray;
	}

	public static double[] toDoubleArray(String[] strs) {
		int size = strs.length;
		double[] doubleArray = new double[size];
		for (int i = 0; i < size; i++) {
			doubleArray[i] = NumberUtils.toDouble(strs[i]);
		}
		return doubleArray;
	}

	public static float[] toFloatArray(String[] strs) {
		int size = strs.length;
		float[] floatArray = new float[size];
		for (int i = 0; i < size; i++) {
			floatArray[i] = NumberUtils.toFloat(strs[i]);
		}
		return floatArray;
	}

	public static short[] toShortArray(String[] strs) {
		int size = strs.length;
		short[] shortArray = new short[size];
		for (int i = 0; i < size; i++) {
			shortArray[i] = NumberUtils.toShort(strs[i]);
		}
		return shortArray;
	}

	public static long[] toLongArray(String[] strs) {
		int size = strs.length;
		long[] longArray = new long[size];
		for (int i = 0; i < size; i++) {
			longArray[i] = NumberUtils.toLong(strs[i]);
		}
		return longArray;
	}

	public static byte[] toByteArray(String[] strs) {
		int size = strs.length;
		byte[] byteArray = new byte[size];
		for (int i = 0; i < size; i++) {
			byteArray[i] = NumberUtils.toByte(strs[i]);
		}
		return byteArray;
	}

	public static boolean[] toBooleanArray(String[] strs) {
		int size = strs.length;
		boolean[] booleanArray = new boolean[size];
		for (int i = 0; i < size; i++) {
			booleanArray[i] = BooleanUtils.toBoolean(strs[i]);
		}
		return booleanArray;
	}

	public static char[] toCharArray(String[] strs) {
		int size = strs.length;
		char[] charArray = new char[size];
		for (int i = 0; i < size; i++) {
			charArray[i] = CharUtils.toChar(strs[i]);
		}
		return charArray;
	}

	/**
	 * <p>
	 * Convert a string to integer array. the array items must split with ','
	 * </p>
	 * 
	 * @param str
	 *            etc. 1,2,3,5,6 or [1,3,5,7,9]
	 * @return
	 */
	public static int[] toIntArray(String str) {
		String[] strs = StringUtils.split(str.replace("[", "").replace("]", ""), ',', true);
		return toIntArray(strs);
	}

	public static String[] toStringArray(String str) {
		String[] strs = StringUtils.split(str.replace("[", "").replace("]", ""), ',', true);
		return strs;
	}

	public static double[] toDoubleArray(String str) {
		String[] strs = StringUtils.split(str.replace("[", "").replace("]", ""), ',', true);
		return toDoubleArray(strs);
	}

	public static long[] toLongArray(String str) {
		String[] strs = StringUtils.split(str.replace("[", "").replace("]", ""), ',', true);
		return toLongArray(strs);
	}

	public static short[] toShortArray(String str) {
		String[] strs = StringUtils.split(str.replace("[", "").replace("]", ""), ',', true);
		return toShortArray(strs);
	}

	public static byte[] toByteArray(String str) {
		String[] strs = StringUtils.split(str.replace("[", "").replace("]", ""), ',', true);
		return toByteArray(strs);
	}

	public static float[] toFloatArray(String str) {
		String[] strs = StringUtils.split(str.replace("[", "").replace("]", ""), ',', true);
		return toFloatArray(strs);
	}

	public static boolean[] toBooleanArray(String str) {
		String[] strs = StringUtils.split(str.replace("[", "").replace("]", ""), ',', true);
		return toBooleanArray(strs);
	}

	public static char[] toCharArray(String str) {
		String[] strs = StringUtils.split(str.replace("[", "").replace("]", ""), ',', true);
		return toCharArray(strs);
	}

	/**
	 * <p>
	 * Search target object form appointing target object array, and return the
	 * index
	 * </p>
	 * <p>
	 * If not found the return -1
	 * </p>
	 * 
	 * @param os
	 * @param target
	 * @return
	 */
	public static int indexOf(Object[] os, Object target) {
		int len = os.length;
		for (int i = 0; i < len; i++) {
			if (os[i].equals(target)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * <p>
	 * Search target object form appointing target object array in inverse, and
	 * return the index
	 * </p>
	 * <p>
	 * If not found the return -1
	 * </p>
	 * 
	 * @param os
	 * @param target
	 * @return
	 */
	public static int lastIndexOf(Object[] os, Object target) {
		int len = os.length;
		for (int i = len - 1; i >= 0; i--) {
			if (os[i].equals(target)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * <p>
	 * For program debug use, print each item of appointing array
	 * </p>
	 * 
	 * @param os
	 */
	public static void print(Object[] os) {
		if (os == null) {
			System.out.print("null\n");
			return;
		}
		System.out.print("[");
		int len = os.length;
		for (int i = 0; i < len; i++) {
			if (i > 0) {
				System.out.print(",");
			}
			System.out.print(os[i]);
		}
		System.out.print("]\n");
	}

	public static Object[] contact(Object[] objs1, Object... objs2) {
		Object[] dest = new Object[objs1.length + objs2.length];
		System.arraycopy(objs1, 0, dest, 0, objs1.length);
		System.arraycopy(objs2, 0, dest, objs1.length, objs2.length);
		return dest;
	}
}
