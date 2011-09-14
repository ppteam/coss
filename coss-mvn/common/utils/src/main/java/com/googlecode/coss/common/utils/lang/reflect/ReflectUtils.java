/**
 * 
 */
package com.googlecode.coss.common.utils.lang.reflect;

import java.beans.BeanInfo;
import java.beans.Expression;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.googlecode.coss.common.utils.lang.StringUtils;
import com.googlecode.coss.common.utils.lang.exception.Assert;
import com.googlecode.coss.common.utils.text.DateFormatUtils;

/**
 * <p>
 * Java Bean Operation
 * </p>
 * 
 * 
 */
public class ReflectUtils {

	/**
	 * Attempt to find a {@link Field field} on the supplied {@link Class} with the
	 * supplied <code>name</code>. Searches all superclasses up to {@link Object}.
	 * @param clazz the class to introspect
	 * @param name the name of the field
	 * @return the corresponding Field object, or <code>null</code> if not found
	 */
	public static Field findField(Class<?> clazz, String name) {
		return findField(clazz, name, null);
	}

	/**
	 * Attempt to find a {@link Field field} on the supplied {@link Class} with the
	 * supplied <code>name</code> and/or {@link Class type}. Searches all superclasses
	 * up to {@link Object}.
	 * @param clazz the class to introspect
	 * @param name the name of the field (may be <code>null</code> if type is specified)
	 * @param type the type of the field (may be <code>null</code> if name is specified)
	 * @return the corresponding Field object, or <code>null</code> if not found
	 */
	public static Field findField(Class<?> clazz, String name, Class<?> type) {
		Assert.notNull(clazz, "Class must not be null");
		Assert.isTrue(name != null || type != null, "Either name or type of the field must be specified");
		Class<?> searchType = clazz;
		while (!Object.class.equals(searchType) && searchType != null) {
			Field[] fields = searchType.getDeclaredFields();
			for (Field field : fields) {
				if ((name == null || name.equals(field.getName())) && (type == null || type.equals(field.getType()))) {
					return field;
				}
			}
			searchType = searchType.getSuperclass();
		}
		return null;
	}

	/**
	 * Set the field represented by the supplied {@link Field field object} on the
	 * specified {@link Object target object} to the specified <code>value</code>.
	 * In accordance with {@link Field#set(Object, Object)} semantics, the new value
	 * is automatically unwrapped if the underlying field has a primitive type.
	 * <p>Thrown exceptions are handled via a call to {@link #handleReflectionException(Exception)}.
	 * @param field the field to set
	 * @param target the target object on which to set the field
	 * @param value the value to set; may be <code>null</code>
	 */
	public static void setField(Field field, Object target, Object value) {
		try {
			field.set(target, value);
		} catch (IllegalAccessException ex) {
			handleReflectionException(ex);
			throw new IllegalStateException("Unexpected reflection exception - " + ex.getClass().getName() + ": "
					+ ex.getMessage());
		}
	}

	/**
	 * Get the field represented by the supplied {@link Field field object} on the
	 * specified {@link Object target object}. In accordance with {@link Field#get(Object)}
	 * semantics, the returned value is automatically wrapped if the underlying field
	 * has a primitive type.
	 * <p>Thrown exceptions are handled via a call to {@link #handleReflectionException(Exception)}.
	 * @param field the field to get
	 * @param target the target object from which to get the field
	 * @return the field's current value
	 */
	public static Object getField(Field field, Object target) {
		try {
			return field.get(target);
		} catch (IllegalAccessException ex) {
			handleReflectionException(ex);
			throw new IllegalStateException("Unexpected reflection exception - " + ex.getClass().getName() + ": "
					+ ex.getMessage());
		}
	}

	/**
	 * Attempt to find a {@link Method} on the supplied class with the supplied name
	 * and no parameters. Searches all superclasses up to <code>Object</code>.
	 * <p>Returns <code>null</code> if no {@link Method} can be found.
	 * @param clazz the class to introspect
	 * @param name the name of the method
	 * @return the Method object, or <code>null</code> if none found
	 */
	public static Method findMethod(Class<?> clazz, String name) {
		return findMethod(clazz, name, new Class[0]);
	}

	/**
	 * Attempt to find a {@link Method} on the supplied class with the supplied name
	 * and parameter types. Searches all superclasses up to <code>Object</code>.
	 * <p>Returns <code>null</code> if no {@link Method} can be found.
	 * @param clazz the class to introspect
	 * @param name the name of the method
	 * @param paramTypes the parameter types of the method
	 * (may be <code>null</code> to indicate any signature)
	 * @return the Method object, or <code>null</code> if none found
	 */
	public static Method findMethod(Class<?> clazz, String name, Class<?>... paramTypes) {
		Assert.notNull(clazz, "Class must not be null");
		Assert.notNull(name, "Method name must not be null");
		Class<?> searchType = clazz;
		while (searchType != null) {
			Method[] methods = (searchType.isInterface() ? searchType.getMethods() : searchType.getDeclaredMethods());
			for (Method method : methods) {
				if (name.equals(method.getName())
						&& (paramTypes == null || Arrays.equals(paramTypes, method.getParameterTypes()))) {
					return method;
				}
			}
			searchType = searchType.getSuperclass();
		}
		return null;
	}

	/**
	 * Invoke the specified {@link Method} against the supplied target object with no arguments.
	 * The target object can be <code>null</code> when invoking a static {@link Method}.
	 * <p>Thrown exceptions are handled via a call to {@link #handleReflectionException}.
	 * @param method the method to invoke
	 * @param target the target object to invoke the method on
	 * @return the invocation result, if any
	 * @see #invokeMethod(java.lang.reflect.Method, Object, Object[])
	 */
	public static Object invokeMethod(Method method, Object target) {
		return invokeMethod(method, target, new Object[0]);
	}

	/**
	 * Invoke the specified {@link Method} against the supplied target object with the
	 * supplied arguments. The target object can be <code>null</code> when invoking a
	 * static {@link Method}.
	 * <p>Thrown exceptions are handled via a call to {@link #handleReflectionException}.
	 * @param method the method to invoke
	 * @param target the target object to invoke the method on
	 * @param args the invocation arguments (may be <code>null</code>)
	 * @return the invocation result, if any
	 */
	public static Object invokeMethod(Method method, Object target, Object... args) {
		try {
			return method.invoke(target, args);
		} catch (Exception ex) {
			handleReflectionException(ex);
		}
		throw new IllegalStateException("Should never get here");
	}

	/**
	 * Invoke the specified JDBC API {@link Method} against the supplied target
	 * object with no arguments.
	 * @param method the method to invoke
	 * @param target the target object to invoke the method on
	 * @return the invocation result, if any
	 * @throws SQLException the JDBC API SQLException to rethrow (if any)
	 * @see #invokeJdbcMethod(java.lang.reflect.Method, Object, Object[])
	 */
	public static Object invokeJdbcMethod(Method method, Object target) throws SQLException {
		return invokeJdbcMethod(method, target, new Object[0]);
	}

	/**
	 * Invoke the specified JDBC API {@link Method} against the supplied target
	 * object with the supplied arguments.
	 * @param method the method to invoke
	 * @param target the target object to invoke the method on
	 * @param args the invocation arguments (may be <code>null</code>)
	 * @return the invocation result, if any
	 * @throws SQLException the JDBC API SQLException to rethrow (if any)
	 * @see #invokeMethod(java.lang.reflect.Method, Object, Object[])
	 */
	public static Object invokeJdbcMethod(Method method, Object target, Object... args) throws SQLException {
		try {
			return method.invoke(target, args);
		} catch (IllegalAccessException ex) {
			handleReflectionException(ex);
		} catch (InvocationTargetException ex) {
			if (ex.getTargetException() instanceof SQLException) {
				throw (SQLException) ex.getTargetException();
			}
			handleInvocationTargetException(ex);
		}
		throw new IllegalStateException("Should never get here");
	}

	/**
	 * Handle the given reflection exception. Should only be called if no
	 * checked exception is expected to be thrown by the target method.
	 * <p>Throws the underlying RuntimeException or Error in case of an
	 * InvocationTargetException with such a root cause. Throws an
	 * IllegalStateException with an appropriate message else.
	 * @param ex the reflection exception to handle
	 */
	public static void handleReflectionException(Exception ex) {
		if (ex instanceof NoSuchMethodException) {
			throw new IllegalStateException("Method not found: " + ex.getMessage());
		}
		if (ex instanceof IllegalAccessException) {
			throw new IllegalStateException("Could not access method: " + ex.getMessage());
		}
		if (ex instanceof InvocationTargetException) {
			handleInvocationTargetException((InvocationTargetException) ex);
		}
		if (ex instanceof RuntimeException) {
			throw (RuntimeException) ex;
		}
		handleUnexpectedException(ex);
	}

	/**
	 * Handle the given invocation target exception. Should only be called if no
	 * checked exception is expected to be thrown by the target method.
	 * <p>Throws the underlying RuntimeException or Error in case of such a root
	 * cause. Throws an IllegalStateException else.
	 * @param ex the invocation target exception to handle
	 */
	public static void handleInvocationTargetException(InvocationTargetException ex) {
		rethrowRuntimeException(ex.getTargetException());
	}

	/**
	 * Rethrow the given {@link Throwable exception}, which is presumably the
	 * <em>target exception</em> of an {@link InvocationTargetException}. Should
	 * only be called if no checked exception is expected to be thrown by the
	 * target method.
	 * <p>Rethrows the underlying exception cast to an {@link RuntimeException} or
	 * {@link Error} if appropriate; otherwise, throws an
	 * {@link IllegalStateException}.
	 * @param ex the exception to rethrow
	 * @throws RuntimeException the rethrown exception
	 */
	public static void rethrowRuntimeException(Throwable ex) {
		if (ex instanceof RuntimeException) {
			throw (RuntimeException) ex;
		}
		if (ex instanceof Error) {
			throw (Error) ex;
		}
		handleUnexpectedException(ex);
	}

	/**
	 * Rethrow the given {@link Throwable exception}, which is presumably the
	 * <em>target exception</em> of an {@link InvocationTargetException}. Should
	 * only be called if no checked exception is expected to be thrown by the
	 * target method.
	 * <p>Rethrows the underlying exception cast to an {@link Exception} or
	 * {@link Error} if appropriate; otherwise, throws an
	 * {@link IllegalStateException}.
	 * @param ex the exception to rethrow
	 * @throws Exception the rethrown exception (in case of a checked exception)
	 */
	public static void rethrowException(Throwable ex) throws Exception {
		if (ex instanceof Exception) {
			throw (Exception) ex;
		}
		if (ex instanceof Error) {
			throw (Error) ex;
		}
		handleUnexpectedException(ex);
	}

	/**
	 * Throws an IllegalStateException with the given exception as root cause.
	 * @param ex the unexpected exception
	 */
	private static void handleUnexpectedException(Throwable ex) {
		throw new IllegalStateException("Unexpected exception thrown", ex);
	}

	/**
	 * Determine whether the given method explicitly declares the given
	 * exception or one of its superclasses, which means that an exception of
	 * that type can be propagated as-is within a reflective invocation.
	 * @param method the declaring method
	 * @param exceptionType the exception to throw
	 * @return <code>true</code> if the exception can be thrown as-is;
	 * <code>false</code> if it needs to be wrapped
	 */
	public static boolean declaresException(Method method, Class<?> exceptionType) {
		Assert.notNull(method, "Method must not be null");
		Class<?>[] declaredExceptions = method.getExceptionTypes();
		for (Class<?> declaredException : declaredExceptions) {
			if (declaredException.isAssignableFrom(exceptionType)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Determine whether the given field is a "public static final" constant.
	 * @param field the field to check
	 */
	public static boolean isPublicStaticFinal(Field field) {
		int modifiers = field.getModifiers();
		return (Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers));
	}

	/**
	 * Determine whether the given method is an "equals" method.
	 * @see java.lang.Object#equals(Object)
	 */
	public static boolean isEqualsMethod(Method method) {
		if (method == null || !method.getName().equals("equals")) {
			return false;
		}
		Class<?>[] paramTypes = method.getParameterTypes();
		return (paramTypes.length == 1 && paramTypes[0] == Object.class);
	}

	/**
	 * Determine whether the given method is a "hashCode" method.
	 * @see java.lang.Object#hashCode()
	 */
	public static boolean isHashCodeMethod(Method method) {
		return (method != null && method.getName().equals("hashCode") && method.getParameterTypes().length == 0);
	}

	/**
	 * Determine whether the given method is a "toString" method.
	 * @see java.lang.Object#toString()
	 */
	public static boolean isToStringMethod(Method method) {
		return (method != null && method.getName().equals("toString") && method.getParameterTypes().length == 0);
	}

	/**
	 * Make the given field accessible, explicitly setting it accessible if
	 * necessary. The <code>setAccessible(true)</code> method is only called
	 * when actually necessary, to avoid unnecessary conflicts with a JVM
	 * SecurityManager (if active).
	 * @param field the field to make accessible
	 * @see java.lang.reflect.Field#setAccessible
	 */
	public static void makeAccessible(Field field) {
		if ((!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers()) || Modifier
				.isFinal(field.getModifiers())) && !field.isAccessible()) {
			field.setAccessible(true);
		}
	}

	/**
	 * Make the given method accessible, explicitly setting it accessible if
	 * necessary. The <code>setAccessible(true)</code> method is only called
	 * when actually necessary, to avoid unnecessary conflicts with a JVM
	 * SecurityManager (if active).
	 * @param method the method to make accessible
	 * @see java.lang.reflect.Method#setAccessible
	 */
	public static void makeAccessible(Method method) {
		if ((!Modifier.isPublic(method.getModifiers()) || !Modifier.isPublic(method.getDeclaringClass().getModifiers()))
				&& !method.isAccessible()) {
			method.setAccessible(true);
		}
	}

	/**
	 * Make the given constructor accessible, explicitly setting it accessible
	 * if necessary. The <code>setAccessible(true)</code> method is only called
	 * when actually necessary, to avoid unnecessary conflicts with a JVM
	 * SecurityManager (if active).
	 * @param ctor the constructor to make accessible
	 * @see java.lang.reflect.Constructor#setAccessible
	 */
	public static void makeAccessible(Constructor<?> ctor) {
		if ((!Modifier.isPublic(ctor.getModifiers()) || !Modifier.isPublic(ctor.getDeclaringClass().getModifiers()))
				&& !ctor.isAccessible()) {
			ctor.setAccessible(true);
		}
	}

	/**
	 * Perform the given callback operation on all matching methods of the given
	 * class and superclasses.
	 * <p>The same named method occurring on subclass and superclass will appear
	 * twice, unless excluded by a {@link MethodFilter}.
	 * @param clazz class to start looking at
	 * @param mc the callback to invoke for each method
	 * @see #doWithMethods(Class, MethodCallback, MethodFilter)
	 */
	public static void doWithMethods(Class<?> clazz, MethodCallback mc) throws IllegalArgumentException {
		doWithMethods(clazz, mc, null);
	}

	/**
	 * Perform the given callback operation on all matching methods of the given
	 * class and superclasses (or given interface and super-interfaces).
	 * <p>The same named method occurring on subclass and superclass will appear
	 * twice, unless excluded by the specified {@link MethodFilter}.
	 * @param clazz class to start looking at
	 * @param mc the callback to invoke for each method
	 * @param mf the filter that determines the methods to apply the callback to
	 */
	public static void doWithMethods(Class<?> clazz, MethodCallback mc, MethodFilter mf)
			throws IllegalArgumentException {

		// Keep backing up the inheritance hierarchy.
		Method[] methods = clazz.getDeclaredMethods();
		for (Method method : methods) {
			if (mf != null && !mf.matches(method)) {
				continue;
			}
			try {
				mc.doWith(method);
			} catch (IllegalAccessException ex) {
				throw new IllegalStateException("Shouldn't be illegal to access method '" + method.getName() + "': "
						+ ex);
			}
		}
		if (clazz.getSuperclass() != null) {
			doWithMethods(clazz.getSuperclass(), mc, mf);
		} else if (clazz.isInterface()) {
			for (Class<?> superIfc : clazz.getInterfaces()) {
				doWithMethods(superIfc, mc, mf);
			}
		}
	}

	/**
	 * Get all declared methods on the leaf class and all superclasses. Leaf
	 * class methods are included first.
	 */
	public static Method[] getAllDeclaredMethods(Class<?> leafClass) throws IllegalArgumentException {
		final List<Method> methods = new ArrayList<Method>(32);
		doWithMethods(leafClass, new MethodCallback() {
			public void doWith(Method method) {
				methods.add(method);
			}
		});
		return methods.toArray(new Method[methods.size()]);
	}

	/**
	 * Invoke the given callback on all fields in the target class, going up the
	 * class hierarchy to get all declared fields.
	 * @param clazz the target class to analyze
	 * @param fc the callback to invoke for each field
	 */
	public static void doWithFields(Class<?> clazz, FieldCallback fc) throws IllegalArgumentException {
		doWithFields(clazz, fc, null);
	}

	/**
	 * Invoke the given callback on all fields in the target class, going up the
	 * class hierarchy to get all declared fields.
	 * @param clazz the target class to analyze
	 * @param fc the callback to invoke for each field
	 * @param ff the filter that determines the fields to apply the callback to
	 */
	public static void doWithFields(Class<?> clazz, FieldCallback fc, FieldFilter ff) throws IllegalArgumentException {

		// Keep backing up the inheritance hierarchy.
		Class<?> targetClass = clazz;
		do {
			Field[] fields = targetClass.getDeclaredFields();
			for (Field field : fields) {
				// Skip static and final fields.
				if (ff != null && !ff.matches(field)) {
					continue;
				}
				try {
					fc.doWith(field);
				} catch (IllegalAccessException ex) {
					throw new IllegalStateException("Shouldn't be illegal to access field '" + field.getName() + "': "
							+ ex);
				}
			}
			targetClass = targetClass.getSuperclass();
		} while (targetClass != null && targetClass != Object.class);
	}

	/**
	 * Given the source object and the destination, which must be the same class
	 * or a subclass, copy all fields, including inherited fields. Designed to
	 * work on objects with public no-arg constructors.
	 * @throws IllegalArgumentException if the arguments are incompatible
	 */
	public static void shallowCopyFieldState(final Object src, final Object dest) throws IllegalArgumentException {
		if (src == null) {
			throw new IllegalArgumentException("Source for field copy cannot be null");
		}
		if (dest == null) {
			throw new IllegalArgumentException("Destination for field copy cannot be null");
		}
		if (!src.getClass().isAssignableFrom(dest.getClass())) {
			throw new IllegalArgumentException("Destination class [" + dest.getClass().getName()
					+ "] must be same or subclass as source class [" + src.getClass().getName() + "]");
		}
		doWithFields(src.getClass(), new FieldCallback() {
			public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
				makeAccessible(field);
				Object srcValue = field.get(src);
				field.set(dest, srcValue);
			}
		}, COPYABLE_FIELDS);
	}

	/**
	 * Action to take on each method.
	 */
	public interface MethodCallback {

		/**
		 * Perform an operation using the given method.
		 * @param method the method to operate on
		 */
		void doWith(Method method) throws IllegalArgumentException, IllegalAccessException;
	}

	/**
	 * Callback optionally used to method fields to be operated on by a method callback.
	 */
	public interface MethodFilter {

		/**
		 * Determine whether the given method matches.
		 * @param method the method to check
		 */
		boolean matches(Method method);
	}

	/**
	 * Callback interface invoked on each field in the hierarchy.
	 */
	public interface FieldCallback {

		/**
		 * Perform an operation using the given field.
		 * @param field the field to operate on
		 */
		void doWith(Field field) throws IllegalArgumentException, IllegalAccessException;
	}

	/**
	 * Callback optionally used to filter fields to be operated on by a field callback.
	 */
	public interface FieldFilter {

		/**
		 * Determine whether the given field matches.
		 * @param field the field to check
		 */
		boolean matches(Field field);
	}

	/**
	 * Pre-built FieldFilter that matches all non-static, non-final fields.
	 */
	public static FieldFilter COPYABLE_FIELDS = new FieldFilter() {

		public boolean matches(Field field) {
			return !(Modifier.isStatic(field.getModifiers()) || Modifier.isFinal(field.getModifiers()));
		}
	};

	/**
	 * Pre-built MethodFilter that matches all non-bridge methods.
	 */
	public static MethodFilter NON_BRIDGED_METHODS = new MethodFilter() {

		public boolean matches(Method method) {
			return !method.isBridge();
		}
	};

	/**
	 * Pre-built MethodFilter that matches all non-bridge methods
	 * which are not declared on <code>java.lang.Object</code>.
	 */
	public static MethodFilter USER_DECLARED_METHODS = new MethodFilter() {

		public boolean matches(Method method) {
			return (!method.isBridge() && method.getDeclaringClass() != Object.class);
		}
	};

	/**
	 * <p>
	 * Copy field value form source to destination, not include specify field
	 * name
	 * </p>
	 * <p>
	 * Final & static field are excluded
	 * </p>
	 * 
	 * @param <E>
	 * @param source
	 *            source object
	 * @param dest
	 *            destination object
	 * @param excludedFieldName
	 *            field name specify for not included
	 * @return
	 */
	public static <E extends Object> E copy(Object source, E dest, String... excludedFieldName) {
		if (source == null || dest == null) {
			return null;
		}
		Field[] fields = ClassUtils.getNoStaticNorFinalFieldArray(source);
		try {
			if (ClassUtils.isSameType(source, dest)) {// same type object
				for (Field field : fields) {
					field.setAccessible(true);
					if (StringUtils.isInList(field.getName(), excludedFieldName)) {// filter
						continue;
					}
					field.set(dest, field.get(source));
				}
			} else {
				for (Field field : fields) {
					field.setAccessible(true);
					if (StringUtils.isInList(field.getName(), excludedFieldName)) {// filter
						continue;
					}
					Field destField = null;
					try {
						destField = dest.getClass().getDeclaredField(field.getName());
					} catch (NoSuchFieldException e) {
					}
					if (destField != null) {
						destField.setAccessible(true);
						destField.set(dest, field.get(source));
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return dest;
	}

	/**
	 * <p>
	 * Return standard JavaBean set Method Name
	 * </p>
	 * <p>
	 * etc. input: 'age' output: 'setAge'
	 * </p>
	 * 
	 * @param field
	 * @return
	 */
	public static String getSetMethodName(String field) {
		return "set" + StringUtils.firstLetterUpper(field);
	}

	/**
	 * <p>
	 * Return standard JavaBean get Method Name
	 * </p>
	 * <p>
	 * etc. input: 'age' output: 'getAge'
	 * </p>
	 * 
	 * @param field
	 * @return
	 */
	public static String getGetMethodName(String field) {
		return "get" + StringUtils.firstLetterUpper(field);
	}

	/**
	 * <p>
	 * Inject a instance set value for all fields in Map
	 * </p>
	 * <p>
	 * This will call setField method for all fields, if failure set field value
	 * directly for using reflection
	 * </p>
	 * 
	 * @param classFullName
	 * @param fieldNameAndValues
	 * @return
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 */
	public static Object inject(String classFullName, Map<String, Object> fieldNameAndValues)
			throws InstantiationException, ClassNotFoundException, IllegalAccessException {
		return inject(Class.forName(classFullName), fieldNameAndValues);

	}

	/**
	 * <p>
	 * Inject a instance set value for all fields in Map
	 * </p>
	 * <p>
	 * This will call setField method for all fields, if failure set field value
	 * directly for using reflection
	 * </p>
	 * 
	 * @param c
	 *            Class
	 * @param fieldNameAndValues
	 * @return
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 */
	public static Object inject(Class<?> c, Map<String, Object> fieldNameAndValues) throws InstantiationException,
			ClassNotFoundException, IllegalAccessException {
		Object target = c.newInstance();
		return inject(target, fieldNameAndValues);
	}

	/**
	 * <p>
	 * Inject a instance set value for all fields in Map
	 * </p>
	 * <p>
	 * This will call setField method for all fields, if failure set field value
	 * directly for using reflection
	 * </p>
	 * 
	 * @param target
	 *            taget instance
	 * @param fieldNameAndValues
	 * @return
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 */
	public static Object inject(Object target, Map<String, Object> fieldNameAndValues) throws InstantiationException,
			ClassNotFoundException, IllegalAccessException {
		Set<Map.Entry<String, Object>> keyValues = fieldNameAndValues.entrySet();
		for (Map.Entry<String, Object> keyValue : keyValues) {
			String methodName = getSetMethodName(keyValue.getKey());
			Expression exp = new Expression(target, methodName, new Object[] { keyValue.getValue() });
			try {
				exp.getValue();
			} catch (Exception e) {
				ReflectUtils.setFieldValue(target, keyValue.getKey(), keyValue.getValue());
			}
		}
		return target;
	}

	/**
	 * <p>
	 * Inject a value to target's field, the value must match actual type of the
	 * field
	 * </p>
	 * <p>
	 * First it calls setXXX method, if fails set field value directory
	 * </p>
	 * 
	 * @param target
	 *            target Object to inject
	 * @param field
	 *            target field to inject
	 * @param value
	 *            field value
	 */
	public static void injectField(Object target, Field field, Object value) {
		String setMethodName = ReflectUtils.getSetMethodName(field.getName());
		try {
			ReflectUtils.executeMethod(target, setMethodName, new Object[] { value });
		} catch (Exception e) {
			ReflectUtils.setFieldValue(target, field, value);
		}
	}

	public static boolean isBasicType(Class<?> c) {
		if (String.class.equals(c)) {
			return true;
		} else if (Integer.class.equals(c) || int.class.equals(c)) {
			return true;
		} else if (Double.class.equals(c) || double.class.equals(c)) {
			return true;
		} else if (Float.class.equals(c) || float.class.equals(c)) {
			return true;
		} else if (Long.class.equals(c) || long.class.equals(c)) {
			return true;
		} else if (Short.class.equals(c) || short.class.equals(c)) {
			return true;
		} else if (Boolean.class.equals(c) || boolean.class.equals(c)) {
			return true;
		} else if (Byte.class.equals(c) || byte.class.equals(c)) {
			return true;
		} else if (Character.class.equals(c) || char.class.equals(c)) {
			return true;
		} else if (Date.class.equals(c)) {
			return true;
		} else if (List.class.equals(c) || ArrayList.class.equals(c)) {
			return true;
		} else if (String[].class.equals(c)) {
			return true;
		} else if (int[].class.equals(c) || Integer[].class.equals(c)) {
			return true;
		} else if (long[].class.equals(c) || Long[].class.equals(c)) {
			return true;
		} else if (short[].class.equals(c) || Short[].class.equals(c)) {
			return true;
		} else if (float[].class.equals(c) || Float[].class.equals(c)) {
			return true;
		} else if (double[].class.equals(c) || Double[].class.equals(c)) {
			return true;
		} else if (byte[].class.equals(c) || Byte[].class.equals(c)) {
			return true;
		} else if (boolean[].class.equals(c) || Boolean[].class.equals(c)) {
			return true;
		} else if (char[].class.equals(c) || Character[].class.equals(c)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @see #org.apache.commons.beanutils.PropertyUtils.describe(obj)
	 */
	public static Map describe(Object obj) {
		if (obj instanceof Map)
			return (Map) obj;
		Map map = new HashMap();
		PropertyDescriptor[] descriptors = getPropertyDescriptors(obj.getClass());
		for (int i = 0; i < descriptors.length; i++) {
			String name = descriptors[i].getName();
			Method readMethod = descriptors[i].getReadMethod();
			if (readMethod != null) {
				try {
					map.put(name, readMethod.invoke(obj, new Object[] {}));
				} catch (Exception e) {
					throw new RuntimeException("error get property value,name:" + name + " on bean:" + obj, e);
				}
			}
		}
		return map;
	}

	public static PropertyDescriptor[] getPropertyDescriptors(Class beanClass) {
		BeanInfo beanInfo = null;
		try {
			beanInfo = Introspector.getBeanInfo(beanClass);
		} catch (IntrospectionException e) {
			return (new PropertyDescriptor[0]);
		}
		PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
		if (descriptors == null) {
			descriptors = new PropertyDescriptor[0];
		}
		return descriptors;
	}

	public static PropertyDescriptor getPropertyDescriptors(Class beanClass, String name) {
		for (PropertyDescriptor pd : getPropertyDescriptors(beanClass)) {
			if (pd.getName().equals(name)) {
				return pd;
			}
		}
		return null;
	}

	public static void copyProperties(Object target, Object source) {
		copyProperties(target, source, null);
	}

	public static void copyProperties(Object target, Object source, String[] ignoreProperties) {
		if (target instanceof Map) {
			throw new UnsupportedOperationException("target is Map unsuported");
		}

		PropertyDescriptor[] targetPds = getPropertyDescriptors(target.getClass());
		List ignoreList = (ignoreProperties != null) ? Arrays.asList(ignoreProperties) : null;

		for (int i = 0; i < targetPds.length; i++) {
			PropertyDescriptor targetPd = targetPds[i];
			if (targetPd.getWriteMethod() != null
					&& (ignoreProperties == null || (!ignoreList.contains(targetPd.getName())))) {
				try {
					if (source instanceof Map) {
						Map map = (Map) source;
						if (map.containsKey(targetPd.getName())) {
							Object value = map.get(targetPd.getName());
							setProperty(target, targetPd, value);
						}
					} else {
						PropertyDescriptor sourcePd = getPropertyDescriptors(source.getClass(), targetPd.getName());
						if (sourcePd != null && sourcePd.getReadMethod() != null) {
							Object value = getProperty(source, sourcePd);
							setProperty(target, targetPd, value);
						}
					}
				} catch (Throwable ex) {
					throw new IllegalArgumentException("Could not copy properties on:" + targetPd.getDisplayName(), ex);
				}
			}
		}
	}

	private static Object getProperty(Object source, PropertyDescriptor sourcePd) throws IllegalAccessException,
			InvocationTargetException {
		Method readMethod = sourcePd.getReadMethod();
		if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
			readMethod.setAccessible(true);
		}
		Object value = readMethod.invoke(source, new Object[0]);
		return value;
	}

	private static void setProperty(Object target, PropertyDescriptor targetPd, Object value)
			throws IllegalAccessException, InvocationTargetException {
		Method writeMethod = targetPd.getWriteMethod();
		if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
			writeMethod.setAccessible(true);
		}
		writeMethod.invoke(target, new Object[] { convert(value, writeMethod.getParameterTypes()[0]) });
	}

	private static Object convert(Object value, Class<?> targetType) {
		if (value == null)
			return null;
		if (targetType == String.class) {
			return value.toString();
		} else {
			return convert(value.toString(), targetType);
		}
	}

	private static Object convert(String value, Class<?> targetType) {
		if (targetType == Byte.class || targetType == byte.class) {
			return new Byte(value);
		}
		if (targetType == Short.class || targetType == short.class) {
			return new Short(value);
		}
		if (targetType == Integer.class || targetType == int.class) {
			return new Integer(value);
		}
		if (targetType == Long.class || targetType == long.class) {
			return new Long(value);
		}
		if (targetType == Float.class || targetType == float.class) {
			return new Float(value);
		}
		if (targetType == Double.class || targetType == double.class) {
			return new Double(value);
		}
		if (targetType == BigDecimal.class) {
			return new BigDecimal(value);
		}
		if (targetType == BigInteger.class) {
			return BigInteger.valueOf(Long.parseLong(value));
		}
		if (targetType == Boolean.class || targetType == boolean.class) {
			return new Boolean(value);
		}
		if (targetType == boolean.class) {
			return new Boolean(value);
		}
		if (targetType == char.class) {
			return value.charAt(0);
		}
		if (DateFormatUtils.isDateType(targetType)) {
			return DateFormatUtils.parse(value, targetType, "yyyyMMdd", "yyyy-MM-dd", "yyyyMMddHHmmSS",
					"yyyy-MM-dd HH:mm:ss", "HH:mm:ss");
		}

		throw new IllegalArgumentException("cannot convert value:" + value + " to targetType:" + targetType);
	}

	/**
	 * 判断判断是否包含某接口
	 * 
	 * @param c
	 * @param szInterface
	 * @return
	 */
	public boolean hasInterface(Class c, String szInterface) {
		Class[] face = c.getInterfaces();
		for (int i = 0, j = face.length; i < j; i++) {
			if (face[i].getName().equals(szInterface)) {
				return true;
			} else {
				Class[] face1 = face[i].getInterfaces();
				for (int x = 0; x < face.length; x++) {
					if (face1[x].getName().equals(szInterface)) {
						return true;
					} else if (hasInterface(face1[x], szInterface)) {
						return true;
					}
				}
			}
		}
		if (null != c.getSuperclass()) {
			return hasInterface(c.getSuperclass(), szInterface);
		}
		return false;
	}

	/**
	 * <p>
	 * Execute method use reflection
	 * </p>
	 * 
	 * @see java.beans.Expression
	 * @param target
	 *            target object
	 * @param methodName
	 *            the method for execute
	 * @param arguments
	 *            the arguments to this method
	 * @return execute result
	 */
	public static Object executeMethod(Object target, String methodName, Object[] arguments) {
		Expression exp = new Expression(target, methodName, arguments);
		try {
			return exp.getValue();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * <p>
	 * Get object field value.
	 * </p>
	 * 
	 * @param obj
	 *            target object
	 * @param name
	 *            field name
	 * @return field value
	 */
	public static Object getFieldValue(Object obj, String name) {
		try {
			return ClassUtils.getField(obj, name).get(obj);
		} catch (Exception e) {
			throw new IllegalArgumentException(String.format("Field name %s not found.", name));
		}
	}

	/**
	 * <p>
	 * Set object field value
	 * </p>
	 * 
	 * @param obj
	 * @param fieldName
	 * @param value
	 */
	public static void setFieldValue(Object obj, String fieldName, Object value) {
		Field field = null;
		try {
			field = ClassUtils.getField(obj, fieldName);
			setFieldValue(obj, field, value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void setFieldValue(Object obj, Field field, Object value) {
		try {
			field.set(obj, value);
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	/**
	 * 反射读取属性 2010-2-2,上午03:29:44
	 * 
	 * @param targetClass
	 * @param fieldName
	 * @return
	 * @throws Exception
	 */
	public static Object getProperty(Class<?> targetClass, String fieldName) {
		Field field;
		Object value;
		try {
			field = targetClass.getField(fieldName);
			value = field.get(null);
		} catch (Exception e) {
			throw new RuntimeException();
		}
		return value;
	}

}
