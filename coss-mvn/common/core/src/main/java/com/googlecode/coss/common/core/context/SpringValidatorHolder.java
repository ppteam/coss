package com.googlecode.coss.common.core.context;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;

/**
 * 用于持有Spring Validator,使调用Validator可以当静态方法使用.
 * 
 * <pre>
 * static 方法调用:
 * SpringValidatorHolder.validate(object);
 * </pre>
 * <pre>
 * spring配置:
 * &lt;bean class="cn.org.rapid_framework.beanvalidation.SpringValidatorHolder">
 * 	 &lt;preperty name="validator" ref="validator"/>
 * &lt;/bean>
 * </pre> 
 * 
 *
 */
public class SpringValidatorHolder implements InitializingBean {
	private static Validator validator;

	public void afterPropertiesSet() throws Exception {
		if (validator == null)
			throw new BeanCreationException("not found spring 'validator' for SpringValidatorHolder ");
	}

	@SuppressWarnings("all")
	public void setValidator(Validator v) {
		if (validator != null) {
			throw new IllegalStateException("SpringValidatorHolder already holded 'validator'");
		}
		validator = v;
	}

	private static Validator getRequiredValidator() {
		if (validator == null)
			throw new IllegalStateException("'validator' property is null,SpringValidatorHolder not yet init.");
		return validator;
	}

	public static Validator getValidator() {
		return getRequiredValidator();
	}

	public static boolean supports(Class<?> type) {
		return getRequiredValidator().supports(type);
	}

	public static void validate(Object object, Errors errors) {
		getRequiredValidator().validate(object, errors);
	}

	public static void validate(Object object) throws BindException {
		BeanPropertyBindingResult errors = new BeanPropertyBindingResult(object, object.getClass().getSimpleName());
		getRequiredValidator().validate(object, errors);
		if (errors.hasErrors()) {
			throw new BindException(errors);
		}
	}

	@SuppressWarnings("unchecked")
	public static String getErrorMessage(BindException e) {
		List<FieldError> errors = e.getFieldErrors();
		StringBuffer sb = null;
		if (errors != null && !errors.isEmpty())
			sb = new StringBuffer().append("\nValidate bean error:\n");
		else
			return null;
		for (Iterator<FieldError> it = errors.iterator(); it.hasNext();) {
			FieldError err = (FieldError) it.next();
			sb.append(err.getObjectName()).append(" ").append(err.getField()).append(": ").append("Rejected Value [")
					.append(err.getRejectedValue() + "]").append(", ").append(err.getCode()).append(" ")
					.append(err.getDefaultMessage()).append("\n");
		}
		return sb.toString();
	}

	public static void cleanHolder() {
		validator = null;
	}
}
