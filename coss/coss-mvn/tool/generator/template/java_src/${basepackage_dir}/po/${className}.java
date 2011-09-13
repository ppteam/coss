<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.po;

import javax.validation.constraints.*;
import org.hibernate.validator.constraints.*;
import ${import_common}.BaseDataObject;

/* Persistent objects */
public class ${className} extends BaseDataObject implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;

	<#--可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息-->
	<#list table.columns as column>
	${column.hibernateValidatorExprssion}
	private ${column.javaType} ${column.columnNameLower};
	</#list>

<@generateJavaColumns/>

<#--
<@generatePropertyColumnMapper/>
<@generateColumnConst/>
<@generateDateFields/>
<@generateDateFieldProperties/>
-->
}

<#macro generatePropertyColumnMapper>
	public static Map<String,String> FIELD_MAP = new HashMap<String, String>();

	static{
	<#list table.columns as column>
		FIELD_MAP.put("${column.columnNameLower}", "${column.sqlName}");
	</#list>
	}
</#macro>

<#macro generateColumnConst>

<#list table.columns as column>
	public static final String ${column.getConstantName()} = "${column.columnNameLower}";
</#list>

</#macro>

<#macro generateDateFields>

<#list table.columns as column>
<#if column.isDateTimeColumn && !column.contains("begin,start,end")>
/** ${column.columnAlias} */
protected ${column.javaType} ${column.columnNameLower}Begin;
protected ${column.javaType} ${column.columnNameLower}End;
</#if>
</#list>

</#macro>

<#macro generateDateFieldProperties>
<#list table.columns as column>
<#if column.isDateTimeColumn && !column.contains("begin,start,end")>
public ${column.javaType} get${column.columnName}Begin() {
	return this.${column.columnNameLower}Begin;
}

public void set${column.columnName}Begin(${column.javaType} value) {
	this.${column.columnNameLower}Begin = value;
}	

public ${column.javaType} get${column.columnName}End() {
	return this.${column.columnNameLower}End;
}

public void set${column.columnName}End(${column.javaType} value) {
	this.${column.columnNameLower}End = value;
}
</#if>	
</#list>
</#macro>


<#macro generateJavaColumns>
	<#list table.columns as column>
	public void set${column.columnName}(${column.javaType} value) {
		this.${column.columnNameLower} = value;
	}
	
	public ${column.javaType} get${column.columnName}() {
		return this.${column.columnNameLower};
	}
	</#list>
</#macro>

<#macro generateJavaOneToMany>
	<#list table.exportedKeys.associatedTables?values as foreignKey>
	<#assign fkSqlTable = foreignKey.sqlTable>
	<#assign fkTable    = fkSqlTable.className>
	<#assign fkPojoClass = fkSqlTable.className>
	<#assign fkPojoClassVar = fkPojoClass?uncap_first>
	
	protected Set ${fkPojoClassVar}s = null;
	public void set${fkPojoClass}s(Set<${fkPojoClass}> ${fkPojoClassVar}){
		this.${fkPojoClassVar}s = ${fkPojoClassVar};
	}
	
	public Set<${fkPojoClass}> get${fkPojoClass}s() {
		return ${fkPojoClassVar}s;
	}
	</#list>
</#macro>

<#macro generateJavaManyToOne>
	<#list table.importedKeys.associatedTables?values as foreignKey>
	<#assign fkSqlTable = foreignKey.sqlTable>
	<#assign fkTable    = fkSqlTable.className>
	<#assign fkPojoClass = fkSqlTable.className>
	<#assign fkPojoClassVar = fkPojoClass?uncap_first>
	
	protected ${fkPojoClass} ${fkPojoClassVar};
	
	public void set${fkPojoClass}(${fkPojoClass} ${fkPojoClassVar}){
		this.${fkPojoClassVar} = ${fkPojoClassVar};
	}
	
	public ${fkPojoClass} get${fkPojoClass}() {
		return ${fkPojoClassVar};
	}
	</#list>
</#macro>
