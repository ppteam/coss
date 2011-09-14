package com.googlecode.coss.common.utils.lang;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.googlecode.coss.common.utils.lang.exception.Assert;

/**
 * <p>
 * Number operation
 * </p>
 */
public class NumberUtils {

    /**
     * Convert the given number into an instance of the given target class.
     * 
     * @param number the number to convert
     * @param targetClass the target class to convert to
     * @return the converted number
     * @throws IllegalArgumentException if the target class is not supported
     *             (i.e. not a standard Number subclass as included in the JDK)
     * @see java.lang.Byte
     * @see java.lang.Short
     * @see java.lang.Integer
     * @see java.lang.Long
     * @see java.math.BigInteger
     * @see java.lang.Float
     * @see java.lang.Double
     * @see java.math.BigDecimal
     */
    @SuppressWarnings("unchecked")
    public static <T extends Number> T convertNumberToTargetClass(Number number,
                                                                  Class<T> targetClass)
            throws IllegalArgumentException {

        Assert.notNull(number, "Number must not be null");
        Assert.notNull(targetClass, "Target class must not be null");

        if (targetClass.isInstance(number)) {
            return (T) number;
        } else if (targetClass.equals(Byte.class)) {
            long value = number.longValue();
            if (value < Byte.MIN_VALUE || value > Byte.MAX_VALUE) {
                raiseOverflowException(number, targetClass);
            }
            return (T) new Byte(number.byteValue());
        } else if (targetClass.equals(Short.class)) {
            long value = number.longValue();
            if (value < Short.MIN_VALUE || value > Short.MAX_VALUE) {
                raiseOverflowException(number, targetClass);
            }
            return (T) new Short(number.shortValue());
        } else if (targetClass.equals(Integer.class)) {
            long value = number.longValue();
            if (value < Integer.MIN_VALUE || value > Integer.MAX_VALUE) {
                raiseOverflowException(number, targetClass);
            }
            return (T) new Integer(number.intValue());
        } else if (targetClass.equals(Long.class)) {
            return (T) new Long(number.longValue());
        } else if (targetClass.equals(BigInteger.class)) {
            if (number instanceof BigDecimal) {
                // do not lose precision - use BigDecimal's own conversion
                return (T) ((BigDecimal) number).toBigInteger();
            } else {
                // original value is not a Big* number - use standard long conversion
                return (T) BigInteger.valueOf(number.longValue());
            }
        } else if (targetClass.equals(Float.class)) {
            return (T) new Float(number.floatValue());
        } else if (targetClass.equals(Double.class)) {
            return (T) new Double(number.doubleValue());
        } else if (targetClass.equals(BigDecimal.class)) {
            // always use BigDecimal(String) here to avoid unpredictability of BigDecimal(double)
            // (see BigDecimal javadoc for details)
            return (T) new BigDecimal(number.toString());
        } else {
            throw new IllegalArgumentException("Could not convert number [" + number
                    + "] of type [" + number.getClass().getName() + "] to unknown target class ["
                    + targetClass.getName() + "]");
        }
    }

    /**
     * Raise an overflow exception for the given number and target class.
     * 
     * @param number the number we tried to convert
     * @param targetClass the target class we tried to convert to
     */
    private static void raiseOverflowException(Number number, Class targetClass) {
        throw new IllegalArgumentException("Could not convert number [" + number + "] of type ["
                + number.getClass().getName() + "] to target class [" + targetClass.getName()
                + "]: overflow");
    }

    /**
     * Parse the given text into a number instance of the given target class,
     * using the corresponding <code>decode</code> / <code>valueOf</code>
     * methods.
     * <p>
     * Trims the input <code>String</code> before attempting to parse the
     * number. Supports numbers in hex format (with leading "0x", "0X" or "#")
     * as well.
     * 
     * @param text the text to convert
     * @param targetClass the target class to parse into
     * @return the parsed number
     * @throws IllegalArgumentException if the target class is not supported
     *             (i.e. not a standard Number subclass as included in the JDK)
     * @see java.lang.Byte#decode
     * @see java.lang.Short#decode
     * @see java.lang.Integer#decode
     * @see java.lang.Long#decode
     * @see #decodeBigInteger(String)
     * @see java.lang.Float#valueOf
     * @see java.lang.Double#valueOf
     * @see java.math.BigDecimal#BigDecimal(String)
     */
    @SuppressWarnings("unchecked")
    public static <T extends Number> T parseNumber(String text, Class<T> targetClass) {
        Assert.notNull(text, "Text must not be null");
        Assert.notNull(targetClass, "Target class must not be null");
        String trimmed = StringUtils.trimAllWhitespace(text);

        if (targetClass.equals(Byte.class)) {
            return (T) (isHexNumber(trimmed) ? Byte.decode(trimmed) : Byte.valueOf(trimmed));
        } else if (targetClass.equals(Short.class)) {
            return (T) (isHexNumber(trimmed) ? Short.decode(trimmed) : Short.valueOf(trimmed));
        } else if (targetClass.equals(Integer.class)) {
            return (T) (isHexNumber(trimmed) ? Integer.decode(trimmed) : Integer.valueOf(trimmed));
        } else if (targetClass.equals(Long.class)) {
            return (T) (isHexNumber(trimmed) ? Long.decode(trimmed) : Long.valueOf(trimmed));
        } else if (targetClass.equals(BigInteger.class)) {
            return (T) (isHexNumber(trimmed) ? decodeBigInteger(trimmed) : new BigInteger(trimmed));
        } else if (targetClass.equals(Float.class)) {
            return (T) Float.valueOf(trimmed);
        } else if (targetClass.equals(Double.class)) {
            return (T) Double.valueOf(trimmed);
        } else if (targetClass.equals(BigDecimal.class) || targetClass.equals(Number.class)) {
            return (T) new BigDecimal(trimmed);
        } else {
            throw new IllegalArgumentException("Cannot convert String [" + text
                    + "] to target class [" + targetClass.getName() + "]");
        }
    }

    /**
     * Parse the given text into a number instance of the given target class,
     * using the given NumberFormat. Trims the input <code>String</code> before
     * attempting to parse the number.
     * 
     * @param text the text to convert
     * @param targetClass the target class to parse into
     * @param numberFormat the NumberFormat to use for parsing (if
     *            <code>null</code>, this method falls back to
     *            <code>parseNumber(String, Class)</code>)
     * @return the parsed number
     * @throws IllegalArgumentException if the target class is not supported
     *             (i.e. not a standard Number subclass as included in the JDK)
     * @see java.text.NumberFormat#parse
     * @see #convertNumberToTargetClass
     * @see #parseNumber(String, Class)
     */
    public static <T extends Number> T parseNumber(String text, Class<T> targetClass,
                                                   NumberFormat numberFormat) {
        if (numberFormat != null) {
            Assert.notNull(text, "Text must not be null");
            Assert.notNull(targetClass, "Target class must not be null");
            DecimalFormat decimalFormat = null;
            boolean resetBigDecimal = false;
            if (numberFormat instanceof DecimalFormat) {
                decimalFormat = (DecimalFormat) numberFormat;
                if (BigDecimal.class.equals(targetClass) && !decimalFormat.isParseBigDecimal()) {
                    decimalFormat.setParseBigDecimal(true);
                    resetBigDecimal = true;
                }
            }
            try {
                Number number = numberFormat.parse(StringUtils.trimAllWhitespace(text));
                return convertNumberToTargetClass(number, targetClass);
            } catch (ParseException ex) {
                throw new IllegalArgumentException("Could not parse number: " + ex.getMessage());
            } finally {
                if (resetBigDecimal) {
                    decimalFormat.setParseBigDecimal(false);
                }
            }
        } else {
            return parseNumber(text, targetClass);
        }
    }

    /**
     * Determine whether the given value String indicates a hex number, i.e.
     * needs to be passed into <code>Integer.decode</code> instead of
     * <code>Integer.valueOf</code> (etc).
     */
    private static boolean isHexNumber(String value) {
        int index = (value.startsWith("-") ? 1 : 0);
        return (value.startsWith("0x", index) || value.startsWith("0X", index) || value.startsWith(
                "#", index));
    }

    /**
     * Decode a {@link java.math.BigInteger} from a {@link String} value.
     * Supports decimal, hex and octal notation.
     * 
     * @see BigInteger#BigInteger(String, int)
     */
    private static BigInteger decodeBigInteger(String value) {
        int radix = 10;
        int index = 0;
        boolean negative = false;

        // Handle minus sign, if present.
        if (value.startsWith("-")) {
            negative = true;
            index++;
        }

        // Handle radix specifier, if present.
        if (value.startsWith("0x", index) || value.startsWith("0X", index)) {
            index += 2;
            radix = 16;
        } else if (value.startsWith("#", index)) {
            index++;
            radix = 16;
        } else if (value.startsWith("0", index) && value.length() > 1 + index) {
            index++;
            radix = 8;
        }

        BigInteger result = new BigInteger(value.substring(index), radix);
        return (negative ? result.negate() : result);
    }

    /**
     * <p>
     * Convert a <code>String</code> to an <code>int</code>, returning
     * <code>zero</code> if the conversion fails.
     * </p>
     * 
     * @param str the string to convert, may be null
     * @return the int represented by the string, or <code>zero</code> if
     *         conversion fails
     */
    public static int toInt(String str) {
        return toInt(str, 0);
    }

    /**
     * <p>
     * Convert a <code>String</code> to an <code>int</code>, returning a default
     * value if the conversion fails.
     * </p>
     * <p>
     * If the string is <code>null</code>, the default value is returned.
     * </p>
     * 
     * @param str the string to convert, may be null
     * @param defaultValue the default value
     * @return the int represented by the string, or the default if conversion
     *         fails
     */
    public static int toInt(String str, int defaultValue) {
        if (str == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException nfe) {
            return defaultValue;
        }
    }

    /**
     * <p>
     * Convert a <code>String</code> to an <code>int</code>, throw Exception
     * instead of returning a int value if the conversion fails.
     * </p>
     * 
     * @param str
     * @return
     */
    public static int toIntStrict(String str) {
        if (str == null) {
            throw new IllegalArgumentException("String 'null' can not parse to int");
        }
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException nfe) {
            throw new IllegalArgumentException(String.format("String '%s' can not parse to int",
                    str));
        }
    }

    /**
     * <p>
     * Convert a boolean to an int value
     * </p>
     * 
     * @param b the boolean value to convert
     * @return 1 when b is true, else return 0.
     */
    public static int toInt(boolean b) {
        return b ? BooleanUtils.NUM_TRUE : BooleanUtils.NUM_FALSE;
    }

    /**
     * <p>
     * Convert a <code>String</code> to a <code>long</code>, returning
     * <code>zero</code> if the conversion fails.
     * </p>
     * <p>
     * If the string is <code>null</code>, <code>zero</code> is returned.
     * </p>
     * 
     * @param str the string to convert, may be null
     * @return the long represented by the string, or <code>0</code> if
     *         conversion fails
     */
    public static long toLong(String str) {
        return toLong(str, 0L);
    }

    /**
     * <p>
     * Convert a <code>String</code> to a <code>long</code>, returning a default
     * value if the conversion fails.
     * </p>
     * <p>
     * If the string is <code>null</code>, the default value is returned.
     * </p>
     * 
     * @param str the string to convert, may be null
     * @param defaultValue the default value
     * @return the long represented by the string, or the default if conversion
     *         fails
     */
    public static long toLong(String str, long defaultValue) {
        if (str == null) {
            return defaultValue;
        }
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException nfe) {
            return defaultValue;
        }
    }

    /**
     * <p>
     * Convert a <code>String</code> to an <code>long</code>, throw Exception
     * instead of returning a long value if the conversion fails.
     * </p>
     * 
     * @param str
     * @return
     */
    public static long toLongStrict(String str) {
        if (str == null) {
            throw new IllegalArgumentException("String 'null' can not parse to Long object");
        }
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException nfe) {
            throw new IllegalArgumentException(String.format(
                    "String '%s' can not parse to Long object", str));
        }
    }

    /**
     * <p>
     * Convert a <code>String</code> to a <code>float</code>, returning
     * <code>0.0f</code> if the conversion fails.
     * </p>
     * <p>
     * If the string <code>str</code> is <code>null</code>, <code>0.0f</code> is
     * returned.
     * </p>
     * 
     * @param str the string to convert, may be <code>null</code>
     * @return the float represented by the string, or <code>0.0f</code> if
     *         conversion fails
     */
    public static float toFloat(String str) {
        return toFloat(str, 0.0f);
    }

    /**
     * <p>
     * Convert a <code>String</code> to a <code>float</code>, returning a
     * default value if the conversion fails.
     * </p>
     * <p>
     * If the string <code>str</code> is <code>null</code>, the default value is
     * returned.
     * </p>
     * 
     * @param str the string to convert, may be <code>null</code>
     * @param defaultValue the default value
     * @return the float represented by the string, or defaultValue if
     *         conversion fails
     */
    public static float toFloat(String str, float defaultValue) {
        if (str == null) {
            return defaultValue;
        }
        try {
            return Float.parseFloat(str);
        } catch (NumberFormatException nfe) {
            return defaultValue;
        }
    }

    /**
     * <p>
     * Convert a <code>String</code> to a <code>float</code>, throw
     * IllegalArgumentException if the conversion fails.
     * </p>
     * <p>
     * </p>
     * 
     * @param str
     * @return
     */
    public static float toFloatStrict(String str) {
        if (str == null) {
            throw new IllegalArgumentException("String 'null' can not parse to Float object");
        }
        try {
            return Float.parseFloat(str);
        } catch (NumberFormatException nfe) {
            throw new IllegalArgumentException(String.format(
                    "String '%s' can not parse to Float object", str));
        }
    }

    /**
     * <p>
     * Convert a <code>String</code> to a <code>double</code>, returning
     * <code>0.0d</code> if the conversion fails.
     * </p>
     * <p>
     * If the string <code>str</code> is <code>null</code>, <code>0.0d</code> is
     * returned.
     * </p>
     * 
     * @param str the string to convert, may be <code>null</code>
     * @return the double represented by the string, or <code>0.0d</code> if
     *         conversion fails
     */
    public static double toDouble(String str) {
        return toDouble(str, 0.0d);
    }

    /**
     * <p>
     * Convert a <code>String</code> to a <code>double</code>, returning a
     * default value if the conversion fails.
     * </p>
     * <p>
     * If the string <code>str</code> is <code>null</code>, the default value is
     * returned.
     * </p>
     * 
     * @param str the string to convert, may be <code>null</code>
     * @param defaultValue the default value
     * @return the double represented by the string, or defaultValue if
     *         conversion fails
     */
    public static double toDouble(String str, double defaultValue) {
        if (str == null) {
            return defaultValue;
        }
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return defaultValue;
        }
    }

    /**
     * <p>
     * Convert a <code>String</code> to a <code>double</code>, throw
     * IllegalArgumentException if the conversion fails.
     * </p>
     * <p>
     * </p>
     * 
     * @param str
     * @return
     */
    public static double toDoubleStrict(String str) {
        if (str == null) {
            throw new IllegalArgumentException("String 'null' can not parse to Double object");
        }
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            throw new IllegalArgumentException(String.format(
                    "String '%s' can not parse to Double object", str));
        }
    }

    /**
     * <p>
     * Convert a <code>String</code> to a <code>short</code>, returning
     * <code>0</code> if the conversion fails.
     * </p>
     * <p>
     * If the string <code>str</code> is <code>null</code>, <code>0</code> is
     * returned.
     * </p>
     * 
     * @param str
     * @return
     * @since 2.0
     */
    public static short toShort(String str) {
        short s = 0;
        return toShort(str, s);
    }

    /**
     * <p>
     * Convert a <code>String</code> to a <code>short</code>, returning a
     * default value if the conversion fails.
     * </p>
     * <p>
     * If the string <code>str</code> is <code>null</code>, the default value is
     * returned.
     * </p>
     * 
     * @param str
     * @param defaultValue
     * @return
     * @since 2.0
     */
    public static short toShort(String str, short defaultValue) {
        if (str == null) {
            return defaultValue;
        }
        try {
            return Short.parseShort(str);
        } catch (NumberFormatException nfe) {
            return defaultValue;
        }
    }

    /**
     * <p>
     * Convert a <code>String</code> to a <code>short</code>, throw
     * IlleaglArgumentException if the conversion fails.
     * 
     * @param str
     * @return
     */
    public static short toShortStrict(String str) {
        if (str == null) {
            throw new IllegalArgumentException("String 'null' can not parse to Short object");
        }
        try {
            return Short.parseShort(str);
        } catch (NumberFormatException nfe) {
            throw new IllegalArgumentException(String.format(
                    "String '%s' can not parse to Short object", str));
        }
    }

    /**
     * <p>
     * Convert a <code>String</code> to a <code>BigDecimal</code>, returning
     * <code>0</code> if the conversion fails.
     * </p>
     * <p>
     * If the string <code>str</code> is <code>null</code>, <code>0</code> is
     * returned.
     * </p>
     * 
     * @param str
     * @return
     * @since 2.0
     */
    public static BigDecimal toBigDecimal(String str) {
        try {
            return new BigDecimal(str);
        } catch (NumberFormatException nfe) {
            return new BigDecimal(0);
        }
    }

    /**
     * <p>
     * Convert a <code>String</code> to a <code>BigDecimal</code>, returning a
     * default value if the conversion fails.
     * </p>
     * <p>
     * If the string <code>str</code> is <code>null</code>, the default value is
     * returned.
     * </p>
     * 
     * @param str
     * @param defaultValue
     * @return
     * @since 2.0
     */
    public static BigDecimal toBigDecimal(String str, BigDecimal defaultValue) {
        try {
            return new BigDecimal(str);
        } catch (NumberFormatException nfe) {
            return defaultValue;
        }
    }

    /**
     * <p>
     * Convert a <code>String</code> to a <code>BigDecimal</code>, throw
     * IllegalArgumentException if the conversion fails.
     * </p>
     * 
     * @param str
     * @return
     */
    public static BigDecimal toBigDecimalStrict(String str) {
        try {
            return new BigDecimal(str);
        } catch (NumberFormatException nfe) {
            throw new IllegalArgumentException(String.format(
                    "String '%s' can not parse to BidDecimal object", str));
        }
    }

    /**
     * <p>
     * Convert a <code>String</code> to a <code>BigInteger</code>, returning
     * <code>0</code> if the conversion fails.
     * </p>
     * <p>
     * If the string <code>str</code> is <code>null</code>, <code>0</code> is
     * returned.
     * </p>
     * 
     * @param str
     * @return
     * @since 2.0
     */
    public static BigInteger toBigInteger(String str) {
        try {
            return new BigInteger(str);
        } catch (NumberFormatException nfe) {
            return new BigInteger("0");
        }
    }

    /**
     * <p>
     * Convert a <code>String</code> to a <code>BigInteger</code>, returning a
     * default value if the conversion fails.
     * </p>
     * <p>
     * If the string <code>str</code> is <code>null</code>, the default value is
     * returned.
     * </p>
     * 
     * @param str
     * @param defaultValue
     * @return
     * @since 2.0
     */
    public static BigInteger toBigInteger(String str, BigInteger defaultValue) {
        try {
            return new BigInteger(str);
        } catch (NumberFormatException nfe) {
            return defaultValue;
        }
    }

    /**
     * <p>
     * Convert a <code>String</code> to a <code>BigInteger</code>, throw
     * IllegalArgumentException if the conversion fails.
     * </p>
     * 
     * @param str
     * @return
     */
    public static BigInteger toBigIntegerStrict(String str) {
        try {
            return new BigInteger(str);
        } catch (NumberFormatException nfe) {
            throw new IllegalArgumentException(String.format(
                    "String '%s' can not parse to BigInteger object", str));
        }
    }

    /**
     * <p>
     * Convert a <code>String</code> to a <code>byte</code>, returning
     * <code>0</code> if the conversion fails.
     * </p>
     * <p>
     * If the string <code>str</code> is <code>null</code>, <code>0</code> is
     * returned.
     * </p>
     * 
     * @param str
     * @return
     * @since 2.0
     */
    public static byte toByte(String str) {
        try {
            return new Byte(str);
        } catch (NumberFormatException nfe) {
            return new Byte("0");
        }
    }

    /**
     * <p>
     * Convert a <code>String</code> to a <code>byte</code>, returning a default
     * value if the conversion fails.
     * </p>
     * <p>
     * If the string <code>str</code> is <code>null</code>, the default value is
     * returned.
     * </p>
     * 
     * @param str
     * @param defaultValue
     * @return
     * @since 2.0
     */
    public static byte toByte(String str, byte defaultValue) {
        try {
            return new Byte(str);
        } catch (NumberFormatException nfe) {
            return defaultValue;
        }
    }

    /**
     * <p>
     * Convert a <code>String</code> to a <code>byte</code>, throw
     * IllegalArgumentException if the conversion fails.
     * </p>
     * 
     * @param str
     * @return
     */
    public static byte toByteStrict(String str) {
        try {
            return new Byte(str);
        } catch (NumberFormatException nfe) {
            throw new IllegalArgumentException(String.format(
                    "String '%s' can not parse to Byte object", str));
        }
    }

    /**
     * <p>
     * Returns the minimum value in an long array.
     * </p>
     * 
     * @param array an array, must not be null or empty
     * @return the minimum value in the array
     * @throws IllegalArgumentException if <code>array</code> is
     *             <code>null</code>
     * @throws IllegalArgumentException if <code>array</code> is empty
     */
    public static long min(long... array) {
        // Validates input
        if (array == null) {
            throw new IllegalArgumentException("The Array must not be null");
        } else if (array.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty.");
        }

        // Finds and returns min
        long min = array[0];
        for (long l : array) {
            if (l < min) {
                min = l;
            }
        }
        return min;
    }

    /**
     * <p>
     * Returns the minimum value in an int array.
     * </p>
     * 
     * @param array an array, must not be null or empty
     * @return the minimum value in the array
     * @throws IllegalArgumentException if <code>array</code> is
     *             <code>null</code>
     * @throws IllegalArgumentException if <code>array</code> is empty
     */
    public static int min(int... array) {
        // Validates input
        if (array == null) {
            throw new IllegalArgumentException("The Array must not be null");
        } else if (array.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty.");
        }

        // Finds and returns min
        int min = array[0];
        for (int i : array) {
            if (i < min) {
                min = i;
            }
        }
        return min;
    }

    /**
     * <p>
     * Returns the minimum value in an int array.
     * </p>
     * 
     * @param array an array, must not be null or empty
     * @return the minimum value in the array
     * @throws IllegalArgumentException if <code>array</code> is
     *             <code>null</code>
     * @throws IllegalArgumentException if <code>array</code> is empty
     */
    public static int minInt(int... array) {
        return min(array);
    }

    /**
     * <p>
     * Returns the minimum value in an short array.
     * </p>
     * 
     * @param array an array, must not be null or empty
     * @return the minimum value in the array
     * @throws IllegalArgumentException if <code>array</code> is
     *             <code>null</code>
     * @throws IllegalArgumentException if <code>array</code> is empty
     */
    public static short min(short... array) {
        // Validates input
        if (array == null) {
            throw new IllegalArgumentException("The Array must not be null");
        } else if (array.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty.");
        }

        // Finds and returns min
        short min = array[0];
        for (short s : array) {
            if (s < min) {
                min = s;
            }
        }
        return min;
    }

    /**
     * <p>
     * Returns the minimum value in an byte array.
     * </p>
     * 
     * @param array an array, must not be null or empty
     * @return the minimum value in the array
     * @throws IllegalArgumentException if <code>array</code> is
     *             <code>null</code>
     * @throws IllegalArgumentException if <code>array</code> is empty
     */
    public static byte min(byte... array) {
        // Validates input
        if (array == null) {
            throw new IllegalArgumentException("The Array must not be null");
        } else if (array.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty.");
        }

        // Finds and returns min
        byte min = array[0];
        for (byte b : array) {
            if (b < min) {
                min = b;
            }
        }
        return min;
    }

    /**
     * <p>
     * Returns the minimum value in an double array.
     * </p>
     * 
     * @param array an array, must not be null or empty
     * @return the minimum value in the array
     * @throws IllegalArgumentException if <code>array</code> is
     *             <code>null</code>
     * @throws IllegalArgumentException if <code>array</code> is empty
     */
    public static double min(double... array) {
        // Validates input
        if (array == null) {
            throw new IllegalArgumentException("The Array must not be null");
        } else if (array.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty.");
        }

        // Finds and returns min
        double min = array[0];
        for (double d : array) {
            if (Double.isNaN(d)) {
                return Double.NaN;
            }
            if (d < min) {
                min = d;
            }
        }
        return min;
    }

    /**
     * <p>
     * Returns the minimum value in an float array.
     * </p>
     * 
     * @param array an array, must not be null or empty
     * @return the minimum value in the array
     * @throws IllegalArgumentException if <code>array</code> is
     *             <code>null</code>
     * @throws IllegalArgumentException if <code>array</code> is empty
     */
    public static float min(float... array) {
        // Validates input
        if (array == null) {
            throw new IllegalArgumentException("The Array must not be null");
        } else if (array.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty.");
        }

        // Finds and returns min
        float min = array[0];
        for (float f : array) {
            if (Float.isNaN(f)) {
                return Float.NaN;
            }
            if (f < min) {
                min = f;
            }
        }
        return min;
    }

    /**
     * <p>
     * Returns the maximum value in an long array.
     * </p>
     * 
     * @param array an array, must not be null or empty
     * @return the minimum value in the array
     * @throws IllegalArgumentException if <code>array</code> is
     *             <code>null</code>
     * @throws IllegalArgumentException if <code>array</code> is empty
     */
    public static long max(long... array) {
        // Validates input
        if (array == null) {
            throw new IllegalArgumentException("The Array must not be null");
        } else if (array.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty.");
        }

        // Finds and returns max
        long max = array[0];
        for (long l : array) {
            if (l > max) {
                max = l;
            }
        }
        return max;
    }

    /**
     * <p>
     * Returns the maximum value in an int array.
     * </p>
     * 
     * @param array an array, must not be null or empty
     * @return the minimum value in the array
     * @throws IllegalArgumentException if <code>array</code> is
     *             <code>null</code>
     * @throws IllegalArgumentException if <code>array</code> is empty
     */
    public static int max(int... array) {
        // Validates input
        if (array == null) {
            throw new IllegalArgumentException("The Array must not be null");
        } else if (array.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty.");
        }

        // Finds and returns max
        int max = array[0];
        for (int i : array) {
            if (i > max) {
                max = i;
            }
        }
        return max;
    }

    /**
     * <p>
     * Returns the maximum value in an int array.
     * </p>
     * 
     * @param array an array, must not be null or empty
     * @return the minimum value in the array
     * @throws IllegalArgumentException if <code>array</code> is
     *             <code>null</code>
     * @throws IllegalArgumentException if <code>array</code> is empty
     */
    public static int maxInt(int... array) {
        return max(array);
    }

    /**
     * <p>
     * Returns the maximum value in an short array.
     * </p>
     * 
     * @param array an array, must not be null or empty
     * @return the minimum value in the array
     * @throws IllegalArgumentException if <code>array</code> is
     *             <code>null</code>
     * @throws IllegalArgumentException if <code>array</code> is empty
     */
    public static short max(short... array) {
        // Validates input
        if (array == null) {
            throw new IllegalArgumentException("The Array must not be null");
        } else if (array.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty.");
        }

        // Finds and returns max
        short max = array[0];
        for (short s : array) {
            if (s > max) {
                max = s;
            }
        }
        return max;
    }

    /**
     * <p>
     * Returns the maximum value in an byte array.
     * </p>
     * 
     * @param array an array, must not be null or empty
     * @return the minimum value in the array
     * @throws IllegalArgumentException if <code>array</code> is
     *             <code>null</code>
     * @throws IllegalArgumentException if <code>array</code> is empty
     */
    public static byte max(byte... array) {
        // Validates input
        if (array == null) {
            throw new IllegalArgumentException("The Array must not be null");
        } else if (array.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty.");
        }

        // Finds and returns max
        byte max = array[0];
        for (byte b : array) {
            if (b > max) {
                max = b;
            }
        }
        return max;
    }

    /**
     * <p>
     * Returns the maximum value in an double array.
     * </p>
     * 
     * @param array an array, must not be null or empty
     * @return the minimum value in the array
     * @throws IllegalArgumentException if <code>array</code> is
     *             <code>null</code>
     * @throws IllegalArgumentException if <code>array</code> is empty
     */
    public static double max(double... array) {
        // Validates input
        if (array == null) {
            throw new IllegalArgumentException("The Array must not be null");
        } else if (array.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty.");
        }

        // Finds and returns max
        double max = array[0];
        for (double d : array) {
            if (Double.isNaN(d)) {
                return Double.NaN;
            }
            if (d > max) {
                max = d;
            }
        }
        return max;
    }

    /**
     * <p>
     * Returns the maximum value in an float array.
     * </p>
     * 
     * @param array an array, must not be null or empty
     * @return the minimum value in the array
     * @throws IllegalArgumentException if <code>array</code> is
     *             <code>null</code>
     * @throws IllegalArgumentException if <code>array</code> is empty
     */
    public static float max(float... array) {
        // Validates input
        if (array == null) {
            throw new IllegalArgumentException("The Array must not be null");
        } else if (array.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty.");
        }

        // Finds and returns max
        float max = array[0];
        for (float f : array) {
            if (Float.isNaN(f)) {
                return Float.NaN;
            }
            if (f > max) {
                max = f;
            }
        }
        return max;
    }

    /**
     * <p>
     * Convert String[] to int[]
     * </p>
     * 
     * @param strs string array
     * @return
     */
    public static int[] toIntArray(String[] strs) {
        if (strs == null) {
            throw new IllegalArgumentException("string array must not be null");
        }
        int length = strs.length;
        int[] ints = new int[length];
        for (int i = 0; i < length; i++) {
            ints[i] = Integer.parseInt(strs[i]);
        }
        return ints;
    }

    /**
     * <p>
     * Convert String List to integer List
     * </p>
     * 
     * @param strList string list
     * @return
     */
    public static List<Integer> toIntList(List<String> strList) {
        if (strList == null) {
            throw new IllegalArgumentException("string array must not be null");
        }
        List<Integer> intList = new ArrayList<Integer>();
        for (String str : strList) {
            intList.add(Integer.parseInt(str));
        }
        return intList;
    }
}
