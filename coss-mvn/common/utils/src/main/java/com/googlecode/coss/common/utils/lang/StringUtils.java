package com.googlecode.coss.common.utils.lang;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.googlecode.coss.common.utils.lang.exception.Assert;
import com.googlecode.coss.common.utils.lang.reflect.ObjectUtils;

/**
 * <p>
 * String operation
 * </p>
 */
public class StringUtils {

	/**
	 * The empty String <code>""</code>.
	 */
	public static final String EMPTY = "";

	private static final String defaultCharset = Charsets.UTF_8;

	private static final String FOLDER_SEPARATOR = "/";

	private static final String WINDOWS_FOLDER_SEPARATOR = "\\";

	private static final String TOP_PATH = "..";

	private static final String CURRENT_PATH = ".";

	private static final char EXTENSION_SEPARATOR = '.';

	//---------------------------------------------------------------------
	// General convenience methods for working with Strings
	//---------------------------------------------------------------------

	/**
	 * Check that the given CharSequence is neither <code>null</code> nor of length 0.
	 * Note: Will return <code>true</code> for a CharSequence that purely consists of whitespace.
	 * <p><pre>
	 * StringUtils.hasLength(null) = false
	 * StringUtils.hasLength("") = false
	 * StringUtils.hasLength(" ") = true
	 * StringUtils.hasLength("Hello") = true
	 * </pre>
	 * @param str the CharSequence to check (may be <code>null</code>)
	 * @return <code>true</code> if the CharSequence is not null and has length
	 * @see #hasText(String)
	 */
	public static boolean hasLength(CharSequence str) {
		return (str != null && str.length() > 0);
	}

	/**
	 * Check that the given String is neither <code>null</code> nor of length 0.
	 * Note: Will return <code>true</code> for a String that purely consists of whitespace.
	 * @param str the String to check (may be <code>null</code>)
	 * @return <code>true</code> if the String is not null and has length
	 * @see #hasLength(CharSequence)
	 */
	public static boolean hasLength(String str) {
		return hasLength((CharSequence) str);
	}

	/**
	 * Check whether the given CharSequence has actual text.
	 * More specifically, returns <code>true</code> if the string not <code>null</code>,
	 * its length is greater than 0, and it contains at least one non-whitespace character.
	 * <p><pre>
	 * StringUtils.hasText(null) = false
	 * StringUtils.hasText("") = false
	 * StringUtils.hasText(" ") = false
	 * StringUtils.hasText("12345") = true
	 * StringUtils.hasText(" 12345 ") = true
	 * </pre>
	 * @param str the CharSequence to check (may be <code>null</code>)
	 * @return <code>true</code> if the CharSequence is not <code>null</code>,
	 * its length is greater than 0, and it does not contain whitespace only
	 * @see java.lang.Character#isWhitespace
	 */
	public static boolean hasText(CharSequence str) {
		if (!hasLength(str)) {
			return false;
		}
		int strLen = str.length();
		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Check whether the given String has actual text.
	 * More specifically, returns <code>true</code> if the string not <code>null</code>,
	 * its length is greater than 0, and it contains at least one non-whitespace character.
	 * @param str the String to check (may be <code>null</code>)
	 * @return <code>true</code> if the String is not <code>null</code>, its length is
	 * greater than 0, and it does not contain whitespace only
	 * @see #hasText(CharSequence)
	 */
	public static boolean hasText(String str) {
		return hasText((CharSequence) str);
	}

	/**
	 * Check whether the given CharSequence contains any whitespace characters.
	 * @param str the CharSequence to check (may be <code>null</code>)
	 * @return <code>true</code> if the CharSequence is not empty and
	 * contains at least 1 whitespace character
	 * @see java.lang.Character#isWhitespace
	 */
	public static boolean containsWhitespace(CharSequence str) {
		if (!hasLength(str)) {
			return false;
		}
		int strLen = str.length();
		for (int i = 0; i < strLen; i++) {
			if (Character.isWhitespace(str.charAt(i))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Check whether the given String contains any whitespace characters.
	 * @param str the String to check (may be <code>null</code>)
	 * @return <code>true</code> if the String is not empty and
	 * contains at least 1 whitespace character
	 * @see #containsWhitespace(CharSequence)
	 */
	public static boolean containsWhitespace(String str) {
		return containsWhitespace((CharSequence) str);
	}

	/**
	 * Remove start blank of given string
	 */
	public static String leftTrim(String str) {
		return str.replaceAll("^\\s+", "");
	}

	/**
	 * Remove end blank of given string
	 */
	public static String rightTrim(String str) {
		return str.replaceAll("\\s+$", "");
	}

	/**
	 * Trim leading and trailing whitespace from the given String.
	 * @param str the String to check
	 * @return the trimmed String
	 * @see java.lang.Character#isWhitespace
	 */
	public static String trimWhitespace(String str) {
		if (!hasLength(str)) {
			return str;
		}
		StringBuilder sb = new StringBuilder(str);
		while (sb.length() > 0 && Character.isWhitespace(sb.charAt(0))) {
			sb.deleteCharAt(0);
		}
		while (sb.length() > 0 && Character.isWhitespace(sb.charAt(sb.length() - 1))) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	/**
	 * Trim <i>all</i> whitespace from the given String:
	 * leading, trailing, and inbetween characters.
	 * @param str the String to check
	 * @return the trimmed String
	 * @see java.lang.Character#isWhitespace
	 */
	public static String trimAllWhitespace(String str) {
		if (!hasLength(str)) {
			return str;
		}
		StringBuilder sb = new StringBuilder(str);
		int index = 0;
		while (sb.length() > index) {
			if (Character.isWhitespace(sb.charAt(index))) {
				sb.deleteCharAt(index);
			} else {
				index++;
			}
		}
		return sb.toString();
	}

	/**
	 * Trim leading whitespace from the given String.
	 * @param str the String to check
	 * @return the trimmed String
	 * @see java.lang.Character#isWhitespace
	 */
	public static String trimLeadingWhitespace(String str) {
		if (!hasLength(str)) {
			return str;
		}
		StringBuilder sb = new StringBuilder(str);
		while (sb.length() > 0 && Character.isWhitespace(sb.charAt(0))) {
			sb.deleteCharAt(0);
		}
		return sb.toString();
	}

	/**
	 * Trim trailing whitespace from the given String.
	 * @param str the String to check
	 * @return the trimmed String
	 * @see java.lang.Character#isWhitespace
	 */
	public static String trimTrailingWhitespace(String str) {
		if (!hasLength(str)) {
			return str;
		}
		StringBuilder sb = new StringBuilder(str);
		while (sb.length() > 0 && Character.isWhitespace(sb.charAt(sb.length() - 1))) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	/**
	 * Trim all occurences of the supplied leading character from the given String.
	 * @param str the String to check
	 * @param leadingCharacter the leading character to be trimmed
	 * @return the trimmed String
	 */
	public static String trimLeadingCharacter(String str, char leadingCharacter) {
		if (!hasLength(str)) {
			return str;
		}
		StringBuilder sb = new StringBuilder(str);
		while (sb.length() > 0 && sb.charAt(0) == leadingCharacter) {
			sb.deleteCharAt(0);
		}
		return sb.toString();
	}

	/**
	 * Trim all occurences of the supplied trailing character from the given String.
	 * @param str the String to check
	 * @param trailingCharacter the trailing character to be trimmed
	 * @return the trimmed String
	 */
	public static String trimTrailingCharacter(String str, char trailingCharacter) {
		if (!hasLength(str)) {
			return str;
		}
		StringBuilder sb = new StringBuilder(str);
		while (sb.length() > 0 && sb.charAt(sb.length() - 1) == trailingCharacter) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	/**
	 * Test if the given String starts with the specified prefix,
	 * ignoring upper/lower case.
	 * @param str the String to check
	 * @param prefix the prefix to look for
	 * @see java.lang.String#startsWith
	 */
	public static boolean startsWithIgnoreCase(String str, String prefix) {
		if (str == null || prefix == null) {
			return false;
		}
		if (str.startsWith(prefix)) {
			return true;
		}
		if (str.length() < prefix.length()) {
			return false;
		}
		String lcStr = str.substring(0, prefix.length()).toLowerCase();
		String lcPrefix = prefix.toLowerCase();
		return lcStr.equals(lcPrefix);
	}

	/**
	 * Test if the given String ends with the specified suffix,
	 * ignoring upper/lower case.
	 * @param str the String to check
	 * @param suffix the suffix to look for
	 * @see java.lang.String#endsWith
	 */
	public static boolean endsWithIgnoreCase(String str, String suffix) {
		if (str == null || suffix == null) {
			return false;
		}
		if (str.endsWith(suffix)) {
			return true;
		}
		if (str.length() < suffix.length()) {
			return false;
		}

		String lcStr = str.substring(str.length() - suffix.length()).toLowerCase();
		String lcSuffix = suffix.toLowerCase();
		return lcStr.equals(lcSuffix);
	}

	/**
	 * Test whether the given string matches the given substring
	 * at the given index.
	 * @param str the original string (or StringBuilder)
	 * @param index the index in the original string to start matching against
	 * @param substring the substring to match at the given index
	 */
	public static boolean substringMatch(CharSequence str, int index, CharSequence substring) {
		for (int j = 0; j < substring.length(); j++) {
			int i = index + j;
			if (i >= str.length() || str.charAt(i) != substring.charAt(j)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Count the occurrences of the substring in string s.
	 * @param str string to search in. Return 0 if this is null.
	 * @param sub string to search for. Return 0 if this is null.
	 */
	public static int countOccurrencesOf(String str, String sub) {
		if (str == null || sub == null || str.length() == 0 || sub.length() == 0) {
			return 0;
		}
		int count = 0;
		int pos = 0;
		int idx;
		while ((idx = str.indexOf(sub, pos)) != -1) {
			++count;
			pos = idx + sub.length();
		}
		return count;
	}

	/**
	 * Replace all occurences of a substring within a string with
	 * another string.
	 * @param inString String to examine
	 * @param oldPattern String to replace
	 * @param newPattern String to insert
	 * @return a String with the replacements
	 */
	public static String replace(String inString, String oldPattern, String newPattern) {
		if (!hasLength(inString) || !hasLength(oldPattern) || newPattern == null) {
			return inString;
		}
		StringBuilder sb = new StringBuilder();
		int pos = 0; // our position in the old string
		int index = inString.indexOf(oldPattern);
		// the index of an occurrence we've found, or -1
		int patLen = oldPattern.length();
		while (index >= 0) {
			sb.append(inString.substring(pos, index));
			sb.append(newPattern);
			pos = index + patLen;
			index = inString.indexOf(oldPattern, pos);
		}
		sb.append(inString.substring(pos));
		// remember to append any characters to the right of a match
		return sb.toString();
	}

	/**
	 * Delete all occurrences of the given substring.
	 * @param inString the original String
	 * @param pattern the pattern to delete all occurrences of
	 * @return the resulting String
	 */
	public static String delete(String inString, String pattern) {
		return replace(inString, pattern, "");
	}

	/**
	 * Delete any character in a given String.
	 * @param inString the original String
	 * @param charsToDelete a set of characters to delete.
	 * E.g. "az\n" will delete 'a's, 'z's and new lines.
	 * @return the resulting String
	 */
	public static String deleteAny(String inString, String charsToDelete) {
		if (!hasLength(inString) || !hasLength(charsToDelete)) {
			return inString;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < inString.length(); i++) {
			char c = inString.charAt(i);
			if (charsToDelete.indexOf(c) == -1) {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	//---------------------------------------------------------------------
	// Convenience methods for working with formatted Strings
	//---------------------------------------------------------------------

	/**
	 * Quote the given String with single quotes.
	 * @param str the input String (e.g. "myString")
	 * @return the quoted String (e.g. "'myString'"),
	 * or <code>null<code> if the input was <code>null</code>
	 */
	public static String quote(String str) {
		return (str != null ? "'" + str + "'" : null);
	}

	/**
	 * Turn the given Object into a String with single quotes
	 * if it is a String; keeping the Object as-is else.
	 * @param obj the input Object (e.g. "myString")
	 * @return the quoted String (e.g. "'myString'"),
	 * or the input object as-is if not a String
	 */
	public static Object quoteIfString(Object obj) {
		return (obj instanceof String ? quote((String) obj) : obj);
	}

	/**
	 * Unqualify a string qualified by a '.' dot character. For example,
	 * "this.name.is.qualified", returns "qualified".
	 * @param qualifiedName the qualified name
	 */
	public static String unqualify(String qualifiedName) {
		return unqualify(qualifiedName, '.');
	}

	/**
	 * Unqualify a string qualified by a separator character. For example,
	 * "this:name:is:qualified" returns "qualified" if using a ':' separator.
	 * @param qualifiedName the qualified name
	 * @param separator the separator
	 */
	public static String unqualify(String qualifiedName, char separator) {
		return qualifiedName.substring(qualifiedName.lastIndexOf(separator) + 1);
	}

	/**
	 * Capitalize a <code>String</code>, changing the first letter to
	 * upper case as per {@link Character#toUpperCase(char)}.
	 * No other letters are changed.
	 * @param str the String to capitalize, may be <code>null</code>
	 * @return the capitalized String, <code>null</code> if null
	 */
	public static String capitalize(String str) {
		return changeFirstCharacterCase(str, true);
	}

	/**
	 * Uncapitalize a <code>String</code>, changing the first letter to
	 * lower case as per {@link Character#toLowerCase(char)}.
	 * No other letters are changed.
	 * @param str the String to uncapitalize, may be <code>null</code>
	 * @return the uncapitalized String, <code>null</code> if null
	 */
	public static String uncapitalize(String str) {
		return changeFirstCharacterCase(str, false);
	}

	/**
	 * Extract the filename from the given path,
	 * e.g. "mypath/myfile.txt" -> "myfile.txt".
	 * @param path the file path (may be <code>null</code>)
	 * @return the extracted filename, or <code>null</code> if none
	 */
	public static String getFilename(String path) {
		if (path == null) {
			return null;
		}
		int separatorIndex = path.lastIndexOf(FOLDER_SEPARATOR);
		return (separatorIndex != -1 ? path.substring(separatorIndex + 1) : path);
	}

	/**
	 * Extract the filename extension from the given path,
	 * e.g. "mypath/myfile.txt" -> "txt".
	 * @param path the file path (may be <code>null</code>)
	 * @return the extracted filename extension, or <code>null</code> if none
	 */
	public static String getFilenameExtension(String path) {
		if (path == null) {
			return null;
		}
		int sepIndex = path.lastIndexOf(EXTENSION_SEPARATOR);
		return (sepIndex != -1 ? path.substring(sepIndex + 1) : null);
	}

	/**
	 * Strip the filename extension from the given path,
	 * e.g. "mypath/myfile.txt" -> "mypath/myfile".
	 * @param path the file path (may be <code>null</code>)
	 * @return the path with stripped filename extension,
	 * or <code>null</code> if none
	 */
	public static String stripFilenameExtension(String path) {
		if (path == null) {
			return null;
		}
		int sepIndex = path.lastIndexOf(EXTENSION_SEPARATOR);
		return (sepIndex != -1 ? path.substring(0, sepIndex) : path);
	}

	/**
	 * Apply the given relative path to the given path,
	 * assuming standard Java folder separation (i.e. "/" separators).
	 * @param path the path to start from (usually a full file path)
	 * @param relativePath the relative path to apply
	 * (relative to the full file path above)
	 * @return the full file path that results from applying the relative path
	 */
	public static String applyRelativePath(String path, String relativePath) {
		int separatorIndex = path.lastIndexOf(FOLDER_SEPARATOR);
		if (separatorIndex != -1) {
			String newPath = path.substring(0, separatorIndex);
			if (!relativePath.startsWith(FOLDER_SEPARATOR)) {
				newPath += FOLDER_SEPARATOR;
			}
			return newPath + relativePath;
		} else {
			return relativePath;
		}
	}

	/**
	 * Parse the given <code>localeString</code> into a {@link Locale}.
	 * <p>This is the inverse operation of {@link Locale#toString Locale's toString}.
	 * @param localeString the locale string, following <code>Locale's</code>
	 * <code>toString()</code> format ("en", "en_UK", etc);
	 * also accepts spaces as separators, as an alternative to underscores
	 * @return a corresponding <code>Locale</code> instance
	 */
	public static Locale parseLocaleString(String localeString) {
		String[] parts = tokenizeToStringArray(localeString, "_ ", false, false);
		String language = (parts.length > 0 ? parts[0] : "");
		String country = (parts.length > 1 ? parts[1] : "");
		String variant = "";
		if (parts.length >= 2) {
			// There is definitely a variant, and it is everything after the country
			// code sans the separator between the country code and the variant.
			int endIndexOfCountryCode = localeString.indexOf(country) + country.length();
			// Strip off any leading '_' and whitespace, what's left is the variant.
			variant = trimLeadingWhitespace(localeString.substring(endIndexOfCountryCode));
			if (variant.startsWith("_")) {
				variant = trimLeadingCharacter(variant, '_');
			}
		}
		return (language.length() > 0 ? new Locale(language, country, variant) : null);
	}

	/**
	 * Determine the RFC 3066 compliant language tag,
	 * as used for the HTTP "Accept-Language" header.
	 * @param locale the Locale to transform to a language tag
	 * @return the RFC 3066 compliant language tag as String
	 */
	public static String toLanguageTag(Locale locale) {
		return locale.getLanguage() + (hasText(locale.getCountry()) ? "-" + locale.getCountry() : "");
	}

	//---------------------------------------------------------------------
	// Convenience methods for working with String arrays
	//---------------------------------------------------------------------

	/**
	 * Append the given String to the given String array, returning a new array
	 * consisting of the input array contents plus the given String.
	 * @param array the array to append to (can be <code>null</code>)
	 * @param str the String to append
	 * @return the new array (never <code>null</code>)
	 */
	public static String[] addStringToArray(String[] array, String str) {
		if (ObjectUtils.isEmpty(array)) {
			return new String[] { str };
		}
		String[] newArr = new String[array.length + 1];
		System.arraycopy(array, 0, newArr, 0, array.length);
		newArr[array.length] = str;
		return newArr;
	}

	/**
	 * Concatenate the given String arrays into one,
	 * with overlapping array elements included twice.
	 * <p>The order of elements in the original arrays is preserved.
	 * @param array1 the first array (can be <code>null</code>)
	 * @param array2 the second array (can be <code>null</code>)
	 * @return the new array (<code>null</code> if both given arrays were <code>null</code>)
	 */
	public static String[] concatenateStringArrays(String[] array1, String[] array2) {
		if (ObjectUtils.isEmpty(array1)) {
			return array2;
		}
		if (ObjectUtils.isEmpty(array2)) {
			return array1;
		}
		String[] newArr = new String[array1.length + array2.length];
		System.arraycopy(array1, 0, newArr, 0, array1.length);
		System.arraycopy(array2, 0, newArr, array1.length, array2.length);
		return newArr;
	}

	/**
	 * Merge the given String arrays into one, with overlapping
	 * array elements only included once.
	 * <p>The order of elements in the original arrays is preserved
	 * (with the exception of overlapping elements, which are only
	 * included on their first occurence).
	 * @param array1 the first array (can be <code>null</code>)
	 * @param array2 the second array (can be <code>null</code>)
	 * @return the new array (<code>null</code> if both given arrays were <code>null</code>)
	 */
	public static String[] mergeStringArrays(String[] array1, String[] array2) {
		if (ObjectUtils.isEmpty(array1)) {
			return array2;
		}
		if (ObjectUtils.isEmpty(array2)) {
			return array1;
		}
		List<String> result = new ArrayList<String>();
		result.addAll(Arrays.asList(array1));
		for (String str : array2) {
			if (!result.contains(str)) {
				result.add(str);
			}
		}
		return toStringArray(result);
	}

	/**
	 * Turn given source String array into sorted array.
	 * @param array the source array
	 * @return the sorted array (never <code>null</code>)
	 */
	public static String[] sortStringArray(String[] array) {
		if (ObjectUtils.isEmpty(array)) {
			return new String[0];
		}
		Arrays.sort(array);
		return array;
	}

	/**
	 * Copy the given Collection into a String array.
	 * The Collection must contain String elements only.
	 * @param collection the Collection to copy
	 * @return the String array (<code>null</code> if the passed-in
	 * Collection was <code>null</code>)
	 */
	public static String[] toStringArray(Collection<String> collection) {
		if (collection == null) {
			return null;
		}
		return collection.toArray(new String[collection.size()]);
	}

	/**
	 * Copy the given Enumeration into a String array.
	 * The Enumeration must contain String elements only.
	 * @param enumeration the Enumeration to copy
	 * @return the String array (<code>null</code> if the passed-in
	 * Enumeration was <code>null</code>)
	 */
	public static String[] toStringArray(Enumeration<String> enumeration) {
		if (enumeration == null) {
			return null;
		}
		List<String> list = Collections.list(enumeration);
		return list.toArray(new String[list.size()]);
	}

	/**
	 * Trim the elements of the given String array,
	 * calling <code>String.trim()</code> on each of them.
	 * @param array the original String array
	 * @return the resulting array (of the same size) with trimmed elements
	 */
	public static String[] trimArrayElements(String[] array) {
		if (ObjectUtils.isEmpty(array)) {
			return new String[0];
		}
		String[] result = new String[array.length];
		for (int i = 0; i < array.length; i++) {
			String element = array[i];
			result[i] = (element != null ? element.trim() : null);
		}
		return result;
	}

	/**
	 * Remove duplicate Strings from the given array.
	 * Also sorts the array, as it uses a TreeSet.
	 * @param array the String array
	 * @return an array without duplicates, in natural sort order
	 */
	public static String[] removeDuplicateStrings(String[] array) {
		if (ObjectUtils.isEmpty(array)) {
			return array;
		}
		Set<String> set = new TreeSet<String>();
		for (String element : array) {
			set.add(element);
		}
		return toStringArray(set);
	}

	/**
	 * Split a String at the first occurrence of the delimiter.
	 * Does not include the delimiter in the result.
	 * @param toSplit the string to split
	 * @param delimiter to split the string up with
	 * @return a two element array with index 0 being before the delimiter, and
	 * index 1 being after the delimiter (neither element includes the delimiter);
	 * or <code>null</code> if the delimiter wasn't found in the given input String
	 */
	public static String[] split(String toSplit, String delimiter) {
		if (!hasLength(toSplit) || !hasLength(delimiter)) {
			return null;
		}
		int offset = toSplit.indexOf(delimiter);
		if (offset < 0) {
			return null;
		}
		String beforeDelimiter = toSplit.substring(0, offset);
		String afterDelimiter = toSplit.substring(offset + delimiter.length());
		return new String[] { beforeDelimiter, afterDelimiter };
	}

	/**
	 * Take an array Strings and split each element based on the given delimiter.
	 * A <code>Properties</code> instance is then generated, with the left of the
	 * delimiter providing the key, and the right of the delimiter providing the value.
	 * <p>Will trim both the key and value before adding them to the
	 * <code>Properties</code> instance.
	 * @param array the array to process
	 * @param delimiter to split each element using (typically the equals symbol)
	 * @return a <code>Properties</code> instance representing the array contents,
	 * or <code>null</code> if the array to process was null or empty
	 */
	public static Properties splitArrayElementsIntoProperties(String[] array, String delimiter) {
		return splitArrayElementsIntoProperties(array, delimiter, null);
	}

	/**
	 * Take an array Strings and split each element based on the given delimiter.
	 * A <code>Properties</code> instance is then generated, with the left of the
	 * delimiter providing the key, and the right of the delimiter providing the value.
	 * <p>Will trim both the key and value before adding them to the
	 * <code>Properties</code> instance.
	 * @param array the array to process
	 * @param delimiter to split each element using (typically the equals symbol)
	 * @param charsToDelete one or more characters to remove from each element
	 * prior to attempting the split operation (typically the quotation mark
	 * symbol), or <code>null</code> if no removal should occur
	 * @return a <code>Properties</code> instance representing the array contents,
	 * or <code>null</code> if the array to process was <code>null</code> or empty
	 */
	public static Properties splitArrayElementsIntoProperties(String[] array, String delimiter, String charsToDelete) {

		if (ObjectUtils.isEmpty(array)) {
			return null;
		}
		Properties result = new Properties();
		for (String element : array) {
			if (charsToDelete != null) {
				element = deleteAny(element, charsToDelete);
			}
			String[] splittedElement = split(element, delimiter);
			if (splittedElement == null) {
				continue;
			}
			result.setProperty(splittedElement[0].trim(), splittedElement[1].trim());
		}
		return result;
	}

	/**
	 * Tokenize the given String into a String array via a StringTokenizer.
	 * Trims tokens and omits empty tokens.
	 * <p>The given delimiters string is supposed to consist of any number of
	 * delimiter characters. Each of those characters can be used to separate
	 * tokens. A delimiter is always a single character; for multi-character
	 * delimiters, consider using <code>delimitedListToStringArray</code>
	 * @param str the String to tokenize
	 * @param delimiters the delimiter characters, assembled as String
	 * (each of those characters is individually considered as delimiter).
	 * @return an array of the tokens
	 * @see java.util.StringTokenizer
	 * @see java.lang.String#trim()
	 * @see #delimitedListToStringArray
	 */
	public static String[] tokenizeToStringArray(String str, String delimiters) {
		return tokenizeToStringArray(str, delimiters, true, true);
	}

	/**
	 * Tokenize the given String into a String array via a StringTokenizer.
	 * <p>The given delimiters string is supposed to consist of any number of
	 * delimiter characters. Each of those characters can be used to separate
	 * tokens. A delimiter is always a single character; for multi-character
	 * delimiters, consider using <code>delimitedListToStringArray</code>
	 * @param str the String to tokenize
	 * @param delimiters the delimiter characters, assembled as String
	 * (each of those characters is individually considered as delimiter)
	 * @param trimTokens trim the tokens via String's <code>trim</code>
	 * @param ignoreEmptyTokens omit empty tokens from the result array
	 * (only applies to tokens that are empty after trimming; StringTokenizer
	 * will not consider subsequent delimiters as token in the first place).
	 * @return an array of the tokens (<code>null</code> if the input String
	 * was <code>null</code>)
	 * @see java.util.StringTokenizer
	 * @see java.lang.String#trim()
	 * @see #delimitedListToStringArray
	 */
	public static String[] tokenizeToStringArray(String str, String delimiters, boolean trimTokens,
			boolean ignoreEmptyTokens) {

		if (str == null) {
			return null;
		}
		StringTokenizer st = new StringTokenizer(str, delimiters);
		List<String> tokens = new ArrayList<String>();
		while (st.hasMoreTokens()) {
			String token = st.nextToken();
			if (trimTokens) {
				token = token.trim();
			}
			if (!ignoreEmptyTokens || token.length() > 0) {
				tokens.add(token);
			}
		}
		return toStringArray(tokens);
	}

	/**
	 * Take a String which is a delimited list and convert it to a String array.
	 * <p>A single delimiter can consists of more than one character: It will still
	 * be considered as single delimiter string, rather than as bunch of potential
	 * delimiter characters - in contrast to <code>tokenizeToStringArray</code>.
	 * @param str the input String
	 * @param delimiter the delimiter between elements (this is a single delimiter,
	 * rather than a bunch individual delimiter characters)
	 * @return an array of the tokens in the list
	 * @see #tokenizeToStringArray
	 */
	public static String[] delimitedListToStringArray(String str, String delimiter) {
		return delimitedListToStringArray(str, delimiter, null);
	}

	/**
	 * Take a String which is a delimited list and convert it to a String array.
	 * <p>A single delimiter can consists of more than one character: It will still
	 * be considered as single delimiter string, rather than as bunch of potential
	 * delimiter characters - in contrast to <code>tokenizeToStringArray</code>.
	 * @param str the input String
	 * @param delimiter the delimiter between elements (this is a single delimiter,
	 * rather than a bunch individual delimiter characters)
	 * @param charsToDelete a set of characters to delete. Useful for deleting unwanted
	 * line breaks: e.g. "\r\n\f" will delete all new lines and line feeds in a String.
	 * @return an array of the tokens in the list
	 * @see #tokenizeToStringArray
	 */
	public static String[] delimitedListToStringArray(String str, String delimiter, String charsToDelete) {
		if (str == null) {
			return new String[0];
		}
		if (delimiter == null) {
			return new String[] { str };
		}
		List<String> result = new ArrayList<String>();
		if ("".equals(delimiter)) {
			for (int i = 0; i < str.length(); i++) {
				result.add(deleteAny(str.substring(i, i + 1), charsToDelete));
			}
		} else {
			int pos = 0;
			int delPos;
			while ((delPos = str.indexOf(delimiter, pos)) != -1) {
				result.add(deleteAny(str.substring(pos, delPos), charsToDelete));
				pos = delPos + delimiter.length();
			}
			if (str.length() > 0 && pos <= str.length()) {
				// Add rest of String, but not in case of empty input.
				result.add(deleteAny(str.substring(pos), charsToDelete));
			}
		}
		return toStringArray(result);
	}

	/**
	 * Convert a CSV list into an array of Strings.
	 * @param str the input String
	 * @return an array of Strings, or the empty array in case of empty input
	 */
	public static String[] commaDelimitedListToStringArray(String str) {
		return delimitedListToStringArray(str, ",");
	}

	/**
	 * Convenience method to convert a CSV string list to a set.
	 * Note that this will suppress duplicates.
	 * @param str the input String
	 * @return a Set of String entries in the list
	 */
	public static Set<String> commaDelimitedListToSet(String str) {
		Set<String> set = new TreeSet<String>();
		String[] tokens = commaDelimitedListToStringArray(str);
		for (String token : tokens) {
			set.add(token);
		}
		return set;
	}

	/**
	 * Convenience method to return a String array as a delimited (e.g. CSV)
	 * String. E.g. useful for <code>toString()</code> implementations.
	 * @param arr the array to display
	 * @param delim the delimiter to use (probably a ",")
	 * @return the delimited String
	 */
	public static String arrayToDelimitedString(Object[] arr, String delim) {
		if (ObjectUtils.isEmpty(arr)) {
			return "";
		}
		if (arr.length == 1) {
			return ObjectUtils.nullSafeToString(arr[0]);
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			if (i > 0) {
				sb.append(delim);
			}
			sb.append(arr[i]);
		}
		return sb.toString();
	}

	/**
	 * Convenience method to return a String array as a CSV String.
	 * E.g. useful for <code>toString()</code> implementations.
	 * @param arr the array to display
	 * @return the delimited String
	 */
	public static String arrayToCommaDelimitedString(Object[] arr) {
		return arrayToDelimitedString(arr, ",");
	}

	/**
	 * <p>
	 * Checks if a String is empty ("") or null.
	 * </p>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @return <code>true</code> if the String is empty or null
	 */
	public static boolean isEmpty(String str) {
		return (str == null || str.length() < 1);
	}

	/**
	 * <p>
	 * Checks if a String is not empty ("") and not null.
	 * </p>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @return <code>true</code> if the String is not empty and not null
	 */
	public static boolean isNotEmpty(String str) {
		return !StringUtils.isEmpty(str);
	}

	/**
	 * <p>
	 * Checks if a String is whitespace, empty ("") or null.
	 * </p>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @return @return <code>true</code> if the String is null, empty or
	 *         whitespace
	 */
	public static boolean isBlank(String str) {
		if (str == null || str.length() < 1) {
			return true;
		}
		int strLen = str.length();
		for (int i = 0; i < strLen; i++) {
			if (Character.isWhitespace(str.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}

	/**
	 * <p>
	 * Checks if a String is not empty (""), not null and not whitespace only.
	 * </p>
	 * 
	 * @param str
	 * @return <code>true</code> if the String is not empty and not null and not
	 *         whitespace
	 */
	public static boolean isNotBlank(String str) {
		return !StringUtils.isBlank(str);
	}

	/**
	 * <p>
	 * Checks if a String can parse to Int
	 * </p>
	 * 
	 * @param str
	 *            The String to Check
	 * @return
	 */
	public static boolean isIntNumString(String str) {
		if (str == null) {
			return false;
		}
		try {
			Integer.parseInt(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * <p>
	 * Checks if a String can parse to Number
	 * </p>
	 * 
	 * @param str
	 *            The String to Check
	 * @return
	 */
	public static boolean isNumString(String str) {
		if (str == null) {
			return false;
		}
		try {
			Double.parseDouble(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * <p>
	 * <Convert String to its Upper case/p>
	 * 
	 * @param str
	 *            the String to convert
	 * @return <code>String of upper case</code>
	 */
	public static String toUppderCase(String str) {
		if (str == null || str.length() < 1) {
			return str;
		} else {
			return str.toUpperCase();
		}
	}

	/**
	 * <p>
	 * Convert String to its Lower case
	 * </p>
	 * 
	 * @param str
	 *            the String to convert
	 * @return <code>String of lower case</code>
	 */
	public static String toLowerCase(String str) {
		if (str == null || str.length() < 1) {
			return str;
		} else {
			return str.toLowerCase();
		}
	}

	/**
	 * <p>
	 * Checks if a String is in a String List, case sensitive
	 * </p>
	 * 
	 * @param strToCheck
	 *            String to check
	 * @param strList
	 *            String List
	 * @return <code>true</code>if strToCheck is in strList,<code>false</code>
	 *         otherwise
	 */
	public static boolean isInList(String strToCheck, String... strList) {
		return isInList(strToCheck, false, strList);
	}

	/**
	 * <p>
	 * Checks if a String is in a String List by appointing ignoreCase for case
	 * insensitive or sensitive
	 * </p>
	 * 
	 * @param strToCheck
	 *            String to check
	 * @param ignoreCase
	 *            whether ignore the case
	 * @param strList
	 *            String List
	 * @return <code>true</code>if strToCheck is in strList,<code>false</code>
	 *         otherwise
	 */
	public static boolean isInList(String strToCheck, boolean ignoreCase, String... strList) {
		for (String str : strList) {
			if (ignoreCase && str.equalsIgnoreCase(strToCheck)) {
				return true;
			} else if (!ignoreCase && str.equals(strToCheck)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * <p>
	 * Check if prefix starts with source
	 * </p>
	 * 
	 * @param source
	 *            source string
	 * @param prefix
	 *            prefix string to check
	 * @param toOffset
	 *            start position of source string
	 * @return
	 */
	public static boolean startsWithIgnoreCase(String source, String prefix, int toOffset) {
		if (source != null && prefix != null) {
			if (source.startsWith(prefix, toOffset)) {
				return true;
			} else {
				return (StringUtils.toLowerCase(source)).startsWith(StringUtils.toLowerCase(prefix), toOffset);
			}
		}
		return false;

	}

	/**
	 * <p>
	 * Check if prefix starts with source
	 * </p>
	 * 
	 * @param source
	 *            source string
	 * @param prefix
	 *            prefix string to check
	 * @return <code>true</code> if source string starts with prefix string
	 */
	public static boolean startsWith(String source, String prefix) {
		if (source != null && prefix != null) {
			if (source.startsWith(prefix)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * <p>
	 * Check if prefix starts with source
	 * </p>
	 * 
	 * @param source
	 *            source string
	 * @param prefix
	 *            prefix string to check
	 * @param toOffset
	 *            start position of source string
	 * @return
	 */
	public static boolean startsWith(String source, String prefix, int toOffset) {
		if (source != null && prefix != null) {
			if (source.startsWith(prefix, toOffset)) {
				return true;
			}
		}
		return false;

	}

	/**
	 * <p>
	 * eq String.getBytes("utf-8"), Exception insensitive
	 * </p>
	 * 
	 * @param str
	 *            input string
	 * @return bytes
	 */
	public static byte[] getBytes(String str) {
		try {
			return str.getBytes(defaultCharset);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * <p>
	 * Convert int to String
	 * </p>
	 * 
	 * @param i
	 * @return
	 */
	public static String toString(int i) {
		return i + EMPTY;
	}

	/**
	 * <p>
	 * Convert double to String
	 * </p>
	 * 
	 * @param d
	 * @return
	 */
	public static String toString(double d) {
		return d + EMPTY;
	}

	/**
	 * <p>
	 * Convert float to String
	 * </p>
	 * 
	 * @param f
	 * @return
	 */
	public static String toString(float f) {
		return f + EMPTY;
	}

	/**
	 * <p>
	 * Convert long to String
	 * </p>
	 * 
	 * @param l
	 * @return
	 */
	public static String toString(long l) {
		return l + EMPTY;
	}

	/**
	 * <p>
	 * Convert boolean to String
	 * </p>
	 * 
	 * @param b
	 * @return
	 */
	public static String toString(boolean b) {
		return b + EMPTY;
	}

	/**
	 * <p>
	 * Convert char to String
	 * </p>
	 * 
	 * @param c
	 * @return
	 */
	public static String toString(char c) {
		return c + EMPTY;
	}

	/**
	 * <p>
	 * Convert byte[] to Hex String
	 * </p>
	 * 
	 * @param src
	 *            source byte[]
	 * @return
	 */
	public static String toHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder();
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	/**
	 * <p>
	 * If input String is null return defaultValue, else return input String
	 * </p>
	 * 
	 * @param str
	 * @param defaultValue
	 * @return
	 */
	public static String toString(String str, String defaultValue) {
		if (str == null) {
			return defaultValue;
		}
		return str;
	}

	/**
	 * <p>
	 * Convert Integer List<Integer> to String List<String>
	 * </p>
	 * 
	 * @param intList
	 *            the Integer list to convert
	 * @return
	 */
	public static List<String> toStringList(List<Integer> intList) {
		List<String> strList = new ArrayList<String>();
		for (int i : intList) {
			strList.add(StringUtils.toString(i));
		}
		return strList;
	}

	/**
	 * <p>
	 * Convert Integer Array to String Array
	 * </p>
	 * 
	 * @param intArray
	 * @return
	 */
	public static String[] toStringArray(int[] intArray) {
		int length = intArray.length;
		String[] strArray = new String[length];
		for (int i = 0; i < length; i++) {
			strArray[i] = StringUtils.toString(intArray[i]);
		}
		return strArray;
	}

	/**
	 * <p>
	 * Check whether source string contains appointing check string
	 * </p>
	 * <p>
	 * if source is null return false
	 * </p>
	 * 
	 * @param source
	 *            the source string
	 * @param strToCheck
	 *            the string to check
	 * @return <code>true</code> if source contains strToCheck
	 */
	public static boolean contains(String source, String strToCheck) {
		if (source != null) {
			return source.contains(strToCheck);
		}
		return false;
	}

	/**
	 * String str = "abc\\#def#ghj 	#kkk # lll #3456\\##\\#\\#\\##09"; String[]
	 * strs = split(str); result: abc#def ghj kkk lll 3456# ### 09
	 * <p>
	 * Split input string to a string array, use split char flag '#' and ignore
	 * blank
	 * </p>
	 * 
	 * @param source
	 *            the source string to split
	 * @return
	 */
	public static String[] split(String source) {
		return split(source, '#', true);
	}

	/**
	 * String str = "abc\\#def#ghj 	#kkk # lll #3456\\##\\#\\#\\##09"; String[]
	 * strs = split(str,'#',true); result: abc#def ghj kkk lll 3456# ### 09
	 * <p>
	 * Split input string to a string array
	 * </p>
	 * 
	 * @param source
	 *            the source string to split
	 * @param splitChar
	 *            split char flag
	 * @param ignoreBlank
	 *            weather use trim for each element
	 * @return
	 */
	public static String[] split(String source, char splitChar, boolean ignoreBlank) {
		char[] chars = source.toCharArray();
		char escapeChar = '\\';
		StringBuilder sb = new StringBuilder();
		boolean ifRecSplitChar = false;
		List<String> strList = new ArrayList<String>();
		for (char c : chars) {
			if (!ifRecSplitChar && c == splitChar) {
				ifRecSplitChar = false;
				if (ignoreBlank) {
					strList.add(sb.toString().trim());
				} else {
					strList.add(sb.toString());
				}
				sb = new StringBuilder();
			} else {
				if (c == escapeChar) {
					ifRecSplitChar = true;
				} else {
					sb.append(c);
					ifRecSplitChar = false;
				}
			}
		}
		if (ignoreBlank) {
			strList.add(sb.toString().trim());
		} else {
			strList.add(sb.toString());
		}
		return strList.toArray(new String[] {});
	}

	public static boolean isNumeric(CharSequence cs) {
		if (cs == null || cs.length() == 0)
			return false;
		int sz = cs.length();
		for (int i = 0; i < sz; i++)
			if (!Character.isDigit(cs.charAt(i)))
				return false;

		return true;
	}

	/**
	 * <p>
	 * Check whether all character of input string is upper case
	 * </p>
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isUpperCase(String str) {
		if (StringUtils.isNotBlank(str)) {
			char[] cs = str.toCharArray();
			for (char c : cs) {
				if (Character.isLowerCase(c)) {
					return false;
				}
			}
			return true;
		} else {
			return true;
		}
	}

	/**
	 * <p>
	 * Check whether all character of input string is lower case
	 * </p>
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isLowerCase(String str) {
		if (StringUtils.isNotBlank(str)) {
			char[] cs = str.toCharArray();
			for (char c : cs) {
				if (Character.isUpperCase(c)) {
					return false;
				}
			}
			return true;
		} else {
			return true;
		}
	}

	/**
	 * Convert a String first letter to Upper case etc. 'abc' to 'Abc', 'ABC' to
	 * 'ABC', '' to '', 'a' to 'A'
	 * <p>
	 * </p>
	 * 
	 * @param str
	 * @return
	 */
	public static String firstLetterUpper(String str) {
		if (StringUtils.isBlank(str)) {
			return str;
		} else if (str.length() == 1) {
			return StringUtils.EMPTY + Character.toUpperCase(str.charAt(0));
		} else {
			char headerChar = str.charAt(0);
			return Character.toUpperCase(headerChar) + str.substring(1);
		}

	}

	/**
	 * Convert a String first letter to Lower case
	 * <p>
	 * </p>
	 * 
	 * @param str
	 * @return
	 */
	public static String firstLetterLower(String str) {
		if (StringUtils.isBlank(str)) {
			return str;
		} else if (str.length() == 1) {
			return StringUtils.EMPTY + Character.toLowerCase((str.charAt(0)));
		} else {
			char headerChar = str.charAt(0);
			return Character.toLowerCase(headerChar) + str.substring(1);
		}

	}

	/**
	 * <p>
	 * Split an inputString to string array, each string item length is segLen,
	 * the last item no included
	 * </p>
	 * 
	 * @param sourceStr
	 *            source string to split
	 * @param segLen
	 *            each segment length
	 * @return
	 */
	public static String[] getSegments(String sourceStr, int segLen) {
		int len = sourceStr.length();
		if (len < segLen) {
			return new String[] { sourceStr };
		} else {
			int segNum = len / segLen + 1;
			if (len % segLen == 0) {
				segNum = len / segLen;
			}
			String[] result = new String[segNum];
			for (int i = 0; i < segNum; i++) {

				result[i] = subString(sourceStr, i * segLen, (i + 1) * segLen);
			}
			return result;
		}
	}

	/**
	 * <p>
	 * String.subString extension, ignore IndexOutBoundsException
	 * </p>
	 * 
	 * @param sourceStr
	 * @param fromIndex
	 * @param toIndex
	 * @return
	 */
	public static String subString(String sourceStr, int fromIndex, int toIndex) {
		int len = sourceStr.length();
		if (toIndex > len) {
			return sourceStr.substring(fromIndex, len);
		} else {
			return sourceStr.substring(fromIndex, toIndex);
		}
	}

	/**
	 * <p>
	 * String.length extension, ignore NullPointerException
	 * </p>
	 * 
	 * @param sourceStr
	 * @param fromIndex
	 * @param toIndex
	 * @return
	 */
	public static int length(String str) {
		if (StringUtils.isEmpty(str)) {
			return 0;
		} else {
			return str.length();
		}
	}

	/**
	 * <p>
	 * Display first part string by appointing max length, the remains use
	 * appointing omit string instead
	 * </p>
	 * etc. omitString("loveyou", 2, "##") = lo##
	 * 
	 * @param source
	 * @param maxLength
	 * @param omitStr
	 * @return
	 */
	public static String omitString(String source, int maxLength, String omitStr) {
		if (StringUtils.length(source) < maxLength) {
			return source;
		} else {
			return StringUtils.subString(source, 0, maxLength) + omitStr;
		}
	}

	/**
	 * <p>
	 * Display first part string by appointing max length, the remains use '...'
	 * instead
	 * </p>
	 * etc. omitString("loveyou", 2) = lo...
	 * 
	 * @param source
	 * @param maxLength
	 * @return
	 */
	public static String omitString(String source, int maxLength) {
		return StringUtils.omitString(source, maxLength, "...");
	}

	/**
	 * <p>
	 * Get sub string before a appointing string, not including the appoint
	 * string
	 * <p>
	 * 
	 * @param source
	 * @param str
	 * @return
	 */
	public static String substringBefore(String source, String str) {
		Assert.notNull(source, "source string most not be null");
		if (str == null) {
			return source;
		} else if (StringUtils.EMPTY.equals(str)) {
			return StringUtils.EMPTY;
		}
		int index = source.indexOf(str);
		if (index == -1) {
			return StringUtils.EMPTY;
		}
		return subString(source, 0, index);
	}

	/**
	 * <p>
	 * Get sub string after a appointing string, not including the appoint
	 * string
	 * <p>
	 * 
	 * @param source
	 * @param str
	 * @return
	 */
	public static String substringAfter(String source, String str) {
		if (source == null) {
			return null;
		}
		if (str == null) {
			return StringUtils.EMPTY;
		} else if (StringUtils.EMPTY.equals(str)) {
			return source;
		}
		int index = source.indexOf(str);
		if (index == -1) {
			return StringUtils.EMPTY;
		}
		return subString(source, index + 1, source.length());
	}

	/**
	 * 复制字符串
	 * 
	 * @param cs
	 *            字符串
	 * @param num
	 *            数量
	 * @return 新字符串
	 */
	public static String dup(CharSequence cs, int num) {
		if (isEmpty(cs) || num <= 0)
			return "";
		StringBuilder sb = new StringBuilder(cs.length() * num);
		for (int i = 0; i < num; i++)
			sb.append(cs);
		return sb.toString();
	}

	/**
	 * 复制字符
	 * 
	 * @param c
	 *            字符
	 * @param num
	 *            数量
	 * @return 新字符串
	 */
	public static String dup(char c, int num) {
		if (c == 0 || num < 1)
			return "";
		StringBuilder sb = new StringBuilder(num);
		for (int i = 0; i < num; i++)
			sb.append(c);
		return sb.toString();
	}

	/**
	 * 将字符串首字母大写
	 * 
	 * @param s
	 *            字符串
	 * @return 首字母大写后的新字符串
	 */
	public static String capitalize(CharSequence s) {
		if (null == s)
			return null;
		int len = s.length();
		if (len == 0)
			return "";
		char char0 = s.charAt(0);
		if (Character.isUpperCase(char0))
			return s.toString();
		return new StringBuilder(len).append(Character.toUpperCase(char0)).append(s.subSequence(1, len)).toString();
	}

	/**
	 * 将字符串首字母小写
	 * 
	 * @param s
	 *            字符串
	 * @return 首字母小写后的新字符串
	 */
	public static String lowerFirst(CharSequence s) {
		if (null == s)
			return null;
		int len = s.length();
		if (len == 0)
			return "";
		char c = s.charAt(0);
		if (Character.isLowerCase(c))
			return s.toString();
		return new StringBuilder(len).append(Character.toLowerCase(c)).append(s.subSequence(1, len)).toString();
	}

	/**
	 * 检查两个字符串的忽略大小写后是否相等.
	 * 
	 * @param s1
	 *            字符串A
	 * @param s2
	 *            字符串B
	 * @return true 如果两个字符串忽略大小写后相等,且两个字符串均不为null
	 */
	public static boolean equalsIgnoreCase(String s1, String s2) {
		return s1 == null ? s2 == null : s1.equalsIgnoreCase(s2);
	}

	/**
	 * 检查两个字符串是否相等.
	 * 
	 * @param s1
	 *            字符串A
	 * @param s2
	 *            字符串B
	 * @return true 如果两个字符串相等,且两个字符串均不为null
	 */
	public static boolean equals(String s1, String s2) {
		return s1 == null ? s2 == null : s1.equals(s2);
	}

	/**
	 * 判断字符串是否以特殊字符开头
	 * 
	 * @param s
	 *            字符串
	 * @param c
	 *            特殊字符
	 * @return 是否以特殊字符开头
	 */
	public static boolean startsWithChar(String s, char c) {
		return null != s ? (s.length() == 0 ? false : s.charAt(0) == c) : false;
	}

	/**
	 * 判断字符串是否以特殊字符结尾
	 * 
	 * @param s
	 *            字符串
	 * @param c
	 *            特殊字符
	 * @return 是否以特殊字符结尾
	 */
	public static boolean endsWithChar(String s, char c) {
		return null != s ? (s.length() == 0 ? false : s.charAt(s.length() - 1) == c) : false;
	}

	/**
	 * @param cs
	 *            字符串
	 * @return 是不是为空字符串
	 */
	public static boolean isEmpty(CharSequence cs) {
		return null == cs || cs.length() == 0;
	}

	/**
	 * @param cs
	 *            字符串
	 * @return 是不是为空白字符串
	 */
	public static boolean isBlank(CharSequence cs) {
		if (null == cs)
			return true;
		int length = cs.length();
		for (int i = 0; i < length; i++) {
			if (!(Character.isWhitespace(cs.charAt(i))))
				return false;
		}
		return true;
	}

	/**
	 * 去掉字符串前后空白
	 * 
	 * @param cs
	 *            字符串
	 * @return 新字符串
	 */
	public static String trim(CharSequence cs) {
		if (null == cs)
			return null;
		if (cs instanceof String)
			return ((String) cs).trim();
		int length = cs.length();
		if (length == 0)
			return cs.toString();
		int l = 0;
		int last = length - 1;
		int r = last;
		for (; l < length; l++) {
			if (!Character.isWhitespace(cs.charAt(l)))
				break;
		}
		for (; r > l; r--) {
			if (!Character.isWhitespace(cs.charAt(r)))
				break;
		}
		if (l > r)
			return "";
		else if (l == 0 && r == last)
			return cs.toString();
		return cs.subSequence(l, r + 1).toString();
	}

	/**
	 * 将字符串按半角逗号，拆分成数组，空元素将被忽略
	 * 
	 * @param s
	 *            字符串
	 * @return 字符串数组
	 */
	public static String[] splitIgnoreBlank(String s) {
		return StringUtils.splitIgnoreBlank(s, ",");
	}

	/**
	 * 根据一个正则式，将字符串拆分成数组，空元素将被忽略
	 * 
	 * @param s
	 *            字符串
	 * @param regex
	 *            正则式
	 * @return 字符串数组
	 */
	public static String[] splitIgnoreBlank(String s, String regex) {
		if (null == s)
			return null;
		String[] ss = s.split(regex);
		List<String> list = new LinkedList<String>();
		for (String st : ss) {
			if (isBlank(st))
				continue;
			list.add(trim(st));
		}
		return list.toArray(new String[list.size()]);
	}

	/**
	 * 将一个整数转换成最小长度为某一固定数值的十进制形式字符串
	 * 
	 * @param d
	 *            整数
	 * @param width
	 *            宽度
	 * @return 新字符串
	 */
	public static String fillDigit(int d, int width) {
		return StringUtils.alignRight(String.valueOf(d), width, '0');
	}

	/**
	 * 将一个整数转换成最小长度为某一固定数值的十六进制形式字符串
	 * 
	 * @param d
	 *            整数
	 * @param width
	 *            宽度
	 * @return 新字符串
	 */
	public static String fillHex(int d, int width) {
		return StringUtils.alignRight(Integer.toHexString(d), width, '0');
	}

	/**
	 * 将一个整数转换成最小长度为某一固定数值的二进制形式字符串
	 * 
	 * @param d
	 *            整数
	 * @param width
	 *            宽度
	 * @return 新字符串
	 */
	public static String fillBinary(int d, int width) {
		return StringUtils.alignRight(Integer.toBinaryString(d), width, '0');
	}

	/**
	 * 将一个整数转换成固定长度的十进制形式字符串
	 * 
	 * @param d
	 *            整数
	 * @param width
	 *            宽度
	 * @return 新字符串
	 */
	public static String toDigit(int d, int width) {
		return StringUtils.cutRight(String.valueOf(d), width, '0');
	}

	/**
	 * 将一个整数转换成固定长度的十六进制形式字符串
	 * 
	 * @param d
	 *            整数
	 * @param width
	 *            宽度
	 * @return 新字符串
	 */
	public static String toHex(int d, int width) {
		return StringUtils.cutRight(Integer.toHexString(d), width, '0');
	}

	/**
	 * 将一个整数转换成固定长度的二进制形式字符串
	 * 
	 * @param d
	 *            整数
	 * @param width
	 *            宽度
	 * @return 新字符串
	 */
	public static String toBinary(int d, int width) {
		return StringUtils.cutRight(Integer.toBinaryString(d), width, '0');
	}

	/**
	 * 保证字符串为一固定长度。超过长度，切除，否则补字符。
	 * 
	 * @param s
	 *            字符串
	 * @param width
	 *            长度
	 * @param c
	 *            补字符
	 * @return 修饰后的字符串
	 */
	public static String cutRight(String s, int width, char c) {
		if (null == s)
			return null;
		int len = s.length();
		if (len == width)
			return s;
		if (len < width)
			return StringUtils.dup(c, width - len) + s;
		return s.substring(len - width, len);
	}

	/**
	 * 在字符串左侧填充一定数量的特殊字符
	 * 
	 * @param cs
	 *            字符串
	 * @param width
	 *            字符数量
	 * @param c
	 *            字符
	 * @return 新字符串
	 */
	public static String alignRight(CharSequence cs, int width, char c) {
		if (null == cs)
			return null;
		int len = cs.length();
		if (len >= width)
			return cs.toString();
		return new StringBuilder().append(dup(c, width - len)).append(cs).toString();
	}

	/**
	 * 在字符串右侧填充一定数量的特殊字符
	 * 
	 * @param cs
	 *            字符串
	 * @param width
	 *            字符数量
	 * @param c
	 *            字符
	 * @return 新字符串
	 */
	public static String alignLeft(CharSequence cs, int width, char c) {
		if (null == cs)
			return null;
		int length = cs.length();
		if (length >= width)
			return cs.toString();
		return new StringBuilder().append(cs).append(dup(c, width - length)).toString();
	}

	/**
	 * @param cs
	 *            字符串
	 * @param lc
	 *            左字符
	 * @param rc
	 *            右字符
	 * @return 字符串是被左字符和右字符包裹 -- 忽略空白
	 */
	public static boolean isQuoteByIgnoreBlank(CharSequence cs, char lc, char rc) {
		if (null == cs)
			return false;
		int len = cs.length();
		if (len < 2)
			return false;
		int l = 0;
		int last = len - 1;
		int r = last;
		for (; l < len; l++) {
			if (!Character.isWhitespace(cs.charAt(l)))
				break;
		}
		if (cs.charAt(l) != lc)
			return false;
		for (; r > l; r--) {
			if (!Character.isWhitespace(cs.charAt(r)))
				break;
		}
		return l < r && cs.charAt(r) == rc;
	}

	/**
	 * @param cs
	 *            字符串
	 * @param lc
	 *            左字符
	 * @param rc
	 *            右字符
	 * @return 字符串是被左字符和右字符包裹
	 */
	public static boolean isQuoteBy(CharSequence cs, char lc, char rc) {
		if (null == cs)
			return false;
		int length = cs.length();
		return length > 1 && cs.charAt(0) == lc && cs.charAt(length - 1) == rc;
	}

	/**
	 * 获得一个字符串集合中，最长串的长度
	 * 
	 * @param coll
	 *            字符串集合
	 * @return 最大长度
	 */
	public static int maxLength(Collection<? extends CharSequence> coll) {
		int re = 0;
		if (null != coll)
			for (CharSequence s : coll)
				if (null != s)
					re = Math.max(re, s.length());
		return re;
	}

	/**
	 * 获得一个字符串数组中，最长串的长度
	 * 
	 * @param array
	 *            字符串数组
	 * @return 最大长度
	 */
	public static <T extends CharSequence> int maxLength(T[] array) {
		int re = 0;
		if (null != array)
			for (CharSequence s : array)
				if (null != s)
					re = Math.max(re, s.length());
		return re;
	}

	/**
	 * 对obj进行toString()操作,如果为null返回""
	 * 
	 * @param obj
	 * @return obj.toString()
	 */
	public static String sNull(Object obj) {
		return sNull(obj, "");
	}

	/**
	 * 对obj进行toString()操作,如果为null返回def中定义的值
	 * 
	 * @param obj
	 * @param def
	 *            如果obj==null返回的内容
	 * @return obj的toString()操作
	 */
	public static String sNull(Object obj, String def) {
		return obj != null ? obj.toString() : def;
	}

	/**
	 * 对obj进行toString()操作,如果为空串返回""
	 * 
	 * @param obj
	 * @return obj.toString()
	 */
	public static String sBlank(Object obj) {
		return sBlank(obj, "");
	}

	/**
	 * 对obj进行toString()操作,如果为空串返回def中定义的值
	 * 
	 * @param obj
	 * @param def
	 *            如果obj==null返回的内容
	 * @return obj的toString()操作
	 */
	public static String sBlank(Object obj, String def) {
		if (null == obj)
			return def;
		String s = obj.toString();
		return StringUtils.isBlank(s) ? def : s;
	}

	/**
	 * 截去第一个字符
	 * <p>
	 * 比如:
	 * <ul>
	 * <li>removeFirst("12345") => 2345
	 * <li>removeFirst("A") => ""
	 * </ul>
	 * 
	 * @param str
	 *            字符串
	 * @return 新字符串
	 */
	public static String removeFirst(CharSequence str) {
		if (str == null)
			return null;
		if (str.length() > 1)
			return str.subSequence(1, str.length()).toString();
		return "";
	}

	/**
	 * 如果str中第一个字符和 c一致,则删除,否则返回 str
	 * <p>
	 * 比如:
	 * <ul>
	 * <li>removeFirst("12345",1) => "2345"
	 * <li>removeFirst("ABC",'B') => "ABC"
	 * <li>removeFirst("A",'B') => "A"
	 * <li>removeFirst("A",'A') => ""
	 * </ul>
	 * 
	 * @param str
	 *            字符串
	 * @param c
	 *            第一个个要被截取的字符
	 * @return 新字符串
	 */
	public static String removeFirst(String str, char c) {
		return (StringUtils.isEmpty(str) || c != str.charAt(0)) ? str : str.substring(1);
	}

	/**
	 * 判断一个字符串数组是否包括某一字符串
	 * 
	 * @param ss
	 *            字符串数组
	 * @param s
	 *            字符串
	 * @return 是否包含
	 */
	public static boolean isin(String[] ss, String s) {
		if (null == ss || ss.length == 0 || StringUtils.isBlank(s))
			return false;
		for (String w : ss)
			if (s.equals(w))
				return true;
		return false;
	}

	private static Pattern email_Pattern = Pattern
			.compile("^([\\w-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");

	/**
	 * 检查一个字符串是否为合法的电子邮件地址
	 * 
	 * @param input
	 *            需要检查的字符串
	 * @return true 如果是有效的邮箱地址
	 */
	public static synchronized final boolean isEmail(CharSequence input) {
		return email_Pattern.matcher(input).matches();
	}

	/**
	 * 将一个字符串某一个字符后面的字母变成大写，比如
	 * 
	 * <pre>
	 *  upperWord("hello-world", '-') => "helloWorld"
	 * </pre>
	 * 
	 * @param s
	 *            字符串
	 * @param c
	 *            字符
	 * 
	 * @return 转换后字符串
	 */
	public static String upperWord(CharSequence s, char c) {
		StringBuilder sb = new StringBuilder();
		int len = s.length();
		for (int i = 0; i < len; i++) {
			char ch = s.charAt(i);
			if (ch == c) {
				do {
					i++;
					if (i >= len)
						return sb.toString();
					ch = s.charAt(i);
				} while (ch == c);
				sb.append(Character.toUpperCase(ch));
			} else {
				sb.append(ch);
			}
		}
		return sb.toString();
	}

	public static String removeCrlf(String str) {
		if (str == null)
			return null;
		return StringUtils.join(StringUtils.tokenizeToStringArray(str, "\t\n\r\f"), " ");
	}

	private static final Map<String, String> XML = new HashMap<String, String>();
	static {
		XML.put("apos", "'");
		XML.put("quot", "\"");
		XML.put("amp", "&");
		XML.put("lt", "<");
		XML.put("gt", ">");
	}

	public static String unescapeXml(String str) {
		if (str == null)
			return null;
		for (String key : XML.keySet()) {
			String value = XML.get(key);
			str = StringUtils.replace(str, "&" + key + ";", value);
		}
		return str;
	}

	public static String removePrefix(String str, String prefix) {
		return removePrefix(str, prefix, false);
	}

	public static String removePrefix(String str, String prefix, boolean ignoreCase) {
		if (str == null)
			return null;
		if (prefix == null)
			return str;
		if (ignoreCase) {
			if (str.toLowerCase().startsWith(prefix.toLowerCase())) {
				return str.substring(prefix.length());
			}
		} else {
			if (str.startsWith(prefix)) {
				return str.substring(prefix.length());
			}
		}
		return str;
	}

	public static String getExtension(String str) {
		if (str == null)
			return null;
		int i = str.lastIndexOf('.');
		if (i >= 0) {
			return str.substring(i + 1);
		}
		return null;
	}

	public static boolean contains(String str, String... keywords) {
		if (str == null)
			return false;
		if (keywords == null)
			throw new IllegalArgumentException("'keywords' must be not null");

		for (String keyword : keywords) {
			if (str.contains(keyword.toLowerCase())) {
				return true;
			}
		}
		return false;
	}

	public static String defaultString(Object value) {
		if (value == null) {
			return "";
		}
		return value.toString();
	}

	public static String defaultIfEmpty(Object value, String defaultValue) {
		if (value == null || "".equals(value)) {
			return defaultValue;
		}
		return value.toString();
	}

	public static String makeAllWordFirstLetterUpperCase(String sqlName) {
		String[] strs = sqlName.toLowerCase().split("_");
		String result = "";
		String preStr = "";
		for (int i = 0; i < strs.length; i++) {
			if (preStr.length() == 1) {
				result += strs[i];
			} else {
				result += capitalize(strs[i]);
			}
			preStr = strs[i];
		}
		return result;
	}

	public static int indexOfByRegex(String input, String regex) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(input);
		if (m.find()) {
			return m.start();
		}
		return -1;
	}

	public static String toJavaVariableName(String str) {
		return uncapitalize(toJavaClassName(str));
	}

	public static String toJavaClassName(String str) {
		return makeAllWordFirstLetterUpperCase(StringUtils.toUnderscoreName(str));
	}

	public static String removeMany(String inString, String... keywords) {
		if (inString == null) {
			return null;
		}
		for (String k : keywords) {
			inString = replace(inString, k, "");
		}
		return inString;
	}

	/**copy from spring*/
	private static String changeFirstCharacterCase(String str, boolean capitalize) {
		if (str == null || str.length() == 0) {
			return str;
		}
		StringBuffer buf = new StringBuffer(str.length());
		if (capitalize) {
			buf.append(Character.toUpperCase(str.charAt(0)));
		} else {
			buf.append(Character.toLowerCase(str.charAt(0)));
		}
		buf.append(str.substring(1));
		return buf.toString();
	}

	private static final Random RANDOM = new Random();

	public static String randomNumeric(int count) {
		return random(count, false, true);
	}

	public static String random(int count, boolean letters, boolean numbers) {
		return random(count, 0, 0, letters, numbers);
	}

	public static String random(int count, int start, int end, boolean letters, boolean numbers) {
		return random(count, start, end, letters, numbers, null, RANDOM);
	}

	public static String random(int count, int start, int end, boolean letters, boolean numbers, char[] chars,
			Random random) {
		if (count == 0) {
			return "";
		} else if (count < 0) {
			throw new IllegalArgumentException("Requested random string length " + count + " is less than 0.");
		}
		if ((start == 0) && (end == 0)) {
			end = 'z' + 1;
			start = ' ';
			if (!letters && !numbers) {
				start = 0;
				end = Integer.MAX_VALUE;
			}
		}

		char[] buffer = new char[count];
		int gap = end - start;

		while (count-- != 0) {
			char ch;
			if (chars == null) {
				ch = (char) (random.nextInt(gap) + start);
			} else {
				ch = chars[random.nextInt(gap) + start];
			}
			if ((letters && Character.isLetter(ch)) || (numbers && Character.isDigit(ch)) || (!letters && !numbers)) {
				if (ch >= 56320 && ch <= 57343) {
					if (count == 0) {
						count++;
					} else {
						// low surrogate, insert high surrogate after putting it
						// in
						buffer[count] = ch;
						count--;
						buffer[count] = (char) (55296 + random.nextInt(128));
					}
				} else if (ch >= 55296 && ch <= 56191) {
					if (count == 0) {
						count++;
					} else {
						// high surrogate, insert low surrogate before putting
						// it in
						buffer[count] = (char) (56320 + random.nextInt(128));
						count--;
						buffer[count] = ch;
					}
				} else if (ch >= 56192 && ch <= 56319) {
					// private high surrogate, no effing clue, so skip it
					count++;
				} else {
					buffer[count] = ch;
				}
			} else {
				count++;
			}
		}
		return new String(buffer);
	}

	/**
	 * Convert a name in camelCase to an underscored name in lower case.
	 * Any upper case letters are converted to lower case with a preceding underscore.
	 * @param filteredName the string containing original name
	 * @return the converted name
	 */
	public static String toUnderscoreName(String name) {
		if (name == null)
			return null;

		String filteredName = name;
		if (filteredName.indexOf("_") >= 0 && filteredName.equals(filteredName.toUpperCase())) {
			filteredName = filteredName.toLowerCase();
		}
		if (filteredName.indexOf("_") == -1 && filteredName.equals(filteredName.toUpperCase())) {
			filteredName = filteredName.toLowerCase();
		}

		StringBuffer result = new StringBuffer();
		if (filteredName != null && filteredName.length() > 0) {
			result.append(filteredName.substring(0, 1).toLowerCase());
			for (int i = 1; i < filteredName.length(); i++) {
				String preChart = filteredName.substring(i - 1, i);
				String c = filteredName.substring(i, i + 1);
				if (c.equals("_")) {
					result.append("_");
					continue;
				}
				if (preChart.equals("_")) {
					result.append(c.toLowerCase());
					continue;
				}
				if (c.matches("\\d")) {
					result.append(c);
				} else if (c.equals(c.toUpperCase())) {
					result.append("_");
					result.append(c.toLowerCase());
				} else {
					result.append(c);
				}
			}
		}
		return result.toString();
	}

	public static String removeEndWiths(String inputString, String... endWiths) {
		for (String endWith : endWiths) {
			if (inputString.endsWith(endWith)) {
				return inputString.substring(0, inputString.length() - endWith.length());
			}
		}
		return inputString;
	}

	public static String join(List list, String seperator) {
		return join(list.toArray(new Object[0]), seperator);
	}

	public static String replace(int start, int end, String str, String replacement) {
		String before = str.substring(0, start);
		String after = str.substring(end);
		return before + replacement + after;
	}

	public static String join(Object[] array, String seperator) {
		if (array == null)
			return null;
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < array.length; i++) {
			result.append(array[i]);
			if (i != array.length - 1) {
				result.append(seperator);
			}
		}
		return result.toString();
	}

	public static int containsCount(String string, String keyword) {
		if (string == null)
			return 0;
		int count = 0;
		for (int i = 0; i < string.length(); i++) {
			int indexOf = string.indexOf(keyword, i);
			if (indexOf < 0) {
				break;
			}
			count++;
			i = indexOf;
		}
		return count;
	}

}
