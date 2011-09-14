package com.neusoft.model.reportforms.dao.pojo;

/**
 * {项目 人员建模}
 * 
 * @author thinker
 * 
 */
public class ProjectMember {

    private String projectName;

    private String projectId;

    private String userName;

    private String userId;

    private String roleType;

    public ProjectMember() {
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

}
