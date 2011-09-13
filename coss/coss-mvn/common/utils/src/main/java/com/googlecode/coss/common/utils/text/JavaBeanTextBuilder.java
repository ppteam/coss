package com.googlecode.coss.common.utils.text;

import java.util.List;

import com.googlecode.coss.common.utils.io.FileUtils;
import com.googlecode.coss.common.utils.lang.StringUtils;
import com.googlecode.coss.common.utils.text.JavaBean.Field;

/**
 * <p>
 * JavaBean text Builder
 * </p>
 */
public class JavaBeanTextBuilder {

	// store package & imports
	private StringBuilder headerSb = new StringBuilder();

	// store class body - fields
	private StringBuilder bodySb = new StringBuilder();

	// store class body - methods
	private StringBuilder methodSb = new StringBuilder();

	boolean[] alreadyImportFlag = new boolean[] { false, false, false, false, false };// Date,
																						// List,ArrayList,Map,HashMap

	/**
	 * <p>
	 * Build a new JavaBean java source text
	 * </p>
	 * 
	 * @param packageName
	 *            etc com.googlecode.coss.common.utils
	 * @param className
	 *            etc. Czat implements Cz
	 */
	public JavaBeanTextBuilder(String packageName, String className) {
		this(className);
		if (packageName != null) {
			headerSb.append("package ");
			headerSb.append(packageName);
			headerSb.append(";");
			headerSb.append("\r\n\r\n");
		}
	}

	// inner use
	protected JavaBeanTextBuilder(String className) {
		bodySb.append("public class ");
		bodySb.append(className);
		bodySb.append(" {");
		bodySb.append("\r\n");
	}

	/**
	 * <p>
	 * Add Field for class
	 * </p>
	 * 
	 * @param fieldName
	 *            field name
	 * @param fieldType
	 *            field type
	 * @param defaultValue
	 *            default value
	 */
	public void addField(String fieldName, String fieldType, Object defaultValue) {
		bodySb.append("\r\n");
		bodySb.append("\tprivate ");
		int index = fieldType.lastIndexOf(".");
		String shortType = fieldType;
		if (index != -1) {// etc. com.unclepeng.Abc
			shortType = shortType.substring(index + 1);// Abc
			addImport(fieldType);// import custom class
		}
		bodySb.append(shortType);
		bodySb.append(" ");

		bodySb.append(fieldName);
		if (defaultValue != null) {
			bodySb.append(" = ");
			if (shortType.equals("String")) {
				bodySb.append("\"");
				bodySb.append(defaultValue);
				bodySb.append("\"");
			} else {
				bodySb.append(defaultValue);
			}
		}
		bodySb.append(";");
		bodySb.append("\r\n");
		addGetMethod(fieldName, shortType);
		addSetMethod(fieldName, shortType);
		addImportByType(shortType);
	}

	/**
	 * <p>
	 * Add Ext Field for class
	 * </p>
	 * 
	 * @param fieldName
	 *            field name
	 * @param fieldType
	 *            field type
	 * @param defaultValue
	 *            default value
	 */
	public void addExtField(String fieldName, String fieldType, Object defaultValue) {
		bodySb.append("\r\n");
		bodySb.append("\tprivate ");
		int index = fieldType.lastIndexOf(".");
		String shortType = fieldType;
		if (index != -1) {// etc. com.unclepeng.Abc
			shortType = shortType.substring(index + 1);// Abc
			addImport(fieldType);// import custom class
		}
		bodySb.append(shortType);
		bodySb.append(" ");

		bodySb.append(fieldName);
		if (defaultValue != null) {
			bodySb.append(" = ");
			if (shortType.equals("String") && !StringUtils.contains(defaultValue.toString(), "(")) {
				bodySb.append("\"");
				bodySb.append(defaultValue);
				bodySb.append("\"");
			} else {
				bodySb.append(defaultValue);
			}
		}
		bodySb.append(";");
		bodySb.append("\r\n");
		addImportByType(shortType);
	}

	public void addImport(String imports) {
		this.headerSb.append("import ");
		this.headerSb.append(imports);
		this.headerSb.append(";\r\n");
	}

	private void addImportByType(String fieldType) {
		if (fieldType.equals("Date") && !alreadyImportFlag[0]) {
			this.headerSb.append("import java.util.Date;\r\n");
			alreadyImportFlag[0] = true;
		} else if (fieldType.equals("List") && !alreadyImportFlag[1]) {
			this.headerSb.append("import java.util.List;\r\n");
			alreadyImportFlag[1] = true;
		} else if (fieldType.equals("ArrayList") && !alreadyImportFlag[2]) {
			this.headerSb.append("import java.util.ArrayList;\r\n");
			alreadyImportFlag[2] = true;
		} else if (fieldType.startsWith("List<") && !alreadyImportFlag[1]) {
			this.headerSb.append("import java.util.List;\r\n");
			alreadyImportFlag[1] = true;
		} else if (fieldType.startsWith("ArrayList<") && !alreadyImportFlag[2]) {
			this.headerSb.append("import java.util.ArrayList;\r\n");
			alreadyImportFlag[2] = true;
		} else if (fieldType.equals("Map") && !alreadyImportFlag[3]) {
			this.headerSb.append("import java.util.Map;\r\n");
			alreadyImportFlag[3] = true;
		} else if (fieldType.equals("HashMap") && !alreadyImportFlag[4]) {
			this.headerSb.append("import java.util.HashMap;\r\n");
			alreadyImportFlag[4] = true;
		} else if (fieldType.startsWith("Map<") && !alreadyImportFlag[3]) {
			this.headerSb.append("import java.util.Map;\r\n");
			alreadyImportFlag[3] = true;
		} else if (fieldType.startsWith("HashMap<") && !alreadyImportFlag[4]) {
			this.headerSb.append("import java.util.HashMap;\r\n");
			alreadyImportFlag[4] = true;
		}
	}

	/**
	 * <p>
	 * Add Field
	 * </p>
	 * 
	 * @param fieldName
	 * @param fieldType
	 */
	public void addField(String fieldName, String fieldType) {
		addField(fieldName, fieldType, null);
	}

	/**
	 * <p>
	 * Add String Field
	 * </p>
	 * 
	 * @param fieldName
	 * @param fieldType
	 */
	public void addStringField(String fieldName) {
		addField(fieldName, "String", null);
	}

	/**
	 * <p>
	 * Add int Field
	 * </p>
	 * 
	 * @param fieldName
	 * @param fieldType
	 */
	public void addIntField(String fieldName) {
		addField(fieldName, "int", null);
	}

	// getter
	private void addGetMethod(String fieldName, String fieldType) {
		methodSb.append("\r\n");
		methodSb.append("\tpublic ");
		methodSb.append(fieldType);
		methodSb.append(" get");
		methodSb.append(StringUtils.firstLetterUpper(fieldName));
		methodSb.append("(){\r\n");
		methodSb.append("\t\treturn this.");
		methodSb.append(fieldName);
		methodSb.append(";");
		methodSb.append("\r\n");
		methodSb.append("\t}");
		methodSb.append("\r\n");
	}

	// setter
	private void addSetMethod(String fieldName, String fieldType) {
		methodSb.append("\r\n");
		methodSb.append("\tpublic ");
		methodSb.append("void");
		methodSb.append(" set");
		methodSb.append(StringUtils.firstLetterUpper(fieldName));
		methodSb.append("(");
		methodSb.append(fieldType);
		methodSb.append(" ");
		methodSb.append(fieldName);
		methodSb.append("){\r\n");
		methodSb.append("\t\tthis.");
		methodSb.append(fieldName);
		methodSb.append(" = ");
		methodSb.append(fieldName);
		methodSb.append(";");
		methodSb.append("\r\n");
		methodSb.append("\t}");
		methodSb.append("\r\n");
	}

	public void addExtMethod(String extMethod) {
		this.methodSb.append("\r\n");
		this.methodSb.append(extMethod);
	}

	public String toString() {
		return this.headerSb.toString() + "\r\n" + this.bodySb.toString() + this.methodSb + "}";
	}

	/**
	 * <p>
	 * Save Java File to appointing path
	 * </p>
	 * 
	 * @param path
	 *            file path to store java file
	 */
	public void saveToFile(String path) {
		FileUtils.saveStringToFile(path, this.toString(), "UTF-8");
	}

	/**
	 * <p>
	 * Get JavaBeanTextBuilder from JavaBean text description
	 * </p>
	 * 
	 * @param bean
	 * @return
	 */
	public static JavaBeanTextBuilder fromJavaBean(JavaBean bean) {
		JavaBeanTextBuilder beanFile = new JavaBeanTextBuilder(bean.getPackageName(), bean.getBeanClassName());
		List<Field> fields = bean.getValues();
		for (Field field : fields) {
			beanFile.addField(field.getFieldName(), field.getFieldType());
		}
		return beanFile;
	}
}
