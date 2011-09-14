package com.neusoft.model.udmgr.dao.pojo;

import java.util.Date;

import com.neusoft.core.dao.Impl.AbstractEntity;

public class TodoList extends AbstractEntity<String> {

	private static final long serialVersionUID = 2964973306398168770L;

	public TodoList() {
	}

	public String getTodoId() {
		return todoId;
	}

	public void setTodoId(String todoId) {
		this.todoId = todoId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	public Date getDeadDate() {
		return deadDate;
	}

	public void setDeadDate(Date deadDate) {
		this.deadDate = deadDate;
	}

	public String getTodoDes() {
		return todoDes;
	}

	public void setTodoDes(String todoDes) {
		this.todoDes = todoDes;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neusoft.core.dao.Iface.IEntity#getId()
	 */
	public String getId() {
		return todoId;
	}

	public Integer getOverTag() {
		return overTag;
	}

	public void setOverTag(Integer overTag) {
		this.overTag = overTag;
	}

	public Integer getTodoType() {
		return todoType;
	}

	public void setTodoType(Integer todoType) {
		this.todoType = todoType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neusoft.core.dao.Iface.IEntity#getTVName()
	 */
	public String getTVName() {
		return "TODO_LIST";
	}

	// TODO_ID char(32) not null comment '逻辑ID 无实际意义',
	private String todoId;
	// USER_ID char(32) not null comment '记录人ID',
	private String userId;
	// ENTYR_DATE date not null comment '记录日期',
	private Date entryDate;
	// DEAD_DATE date not null comment '截至日期',
	private Date deadDate;
	// TODO_DES varchar(256) not null comment '代办表述',
	private String todoDes;
	// STATUS int(1) not null comment '完成情况 0/1 进行中/完成',
	private Integer status;
	// 附加参数 是否超期
	private Integer overTag;
	// 是否是代办 0/1 自身/其他附加
	private Integer todoType;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TodoList [todoId=" + todoId + ", userId=" + userId + ", entryDate=" + entryDate
				+ ", deadDate=" + deadDate + ", todoDes=" + todoDes + ", status=" + status + "]";
	}

}
