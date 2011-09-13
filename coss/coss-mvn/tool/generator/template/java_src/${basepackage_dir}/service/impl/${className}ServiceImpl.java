<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.coss.common.core.service.BaseService;
import ${basepackage}.po.${className};
import ${basepackage}.dao.${className}Dao;
import ${basepackage}.service.${className}Service;

@Service("${classNameLower}Service")
@Transactional
public class ${className}ServiceImpl extends BaseService<${className},${table.idColumn.javaType}> implements ${className}Service{

	private ${className}Dao ${classNameLower}Dao;
	
	@Resource
	public void set${className}Dao(${className}Dao dao) {
		this.${classNameLower}Dao = dao;
	}
	
	public ${className}Dao getSqlMapDao() {
		return this.${classNameLower}Dao;
	}
	
	<#list table.columns as column>
	<#if column.unique && !column.pk>
	public int deleteBy${column.columnName}(${column.javaType} ${column.columnNameFirstLower}){
		return ${classNameLower}Dao.deleteBy${column.columnName}(${column.columnNameFirstLower});
	}
	
	</#if>
	</#list>
	
	<#list table.columns as column>
	<#if column.unique && !column.pk>
	@Transactional(readOnly=true)
	public ${className} getBy${column.columnName}(${column.javaType} ${column.columnNameFirstLower}) {
		return ${classNameLower}Dao.getBy${column.columnName}(${column.columnNameFirstLower});
	}
	
	</#if>
	</#list>
}
