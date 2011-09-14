-- ===============================================================
-- 重新初始化数据库失败，需要删除之前的索引执行脚本
-- ===============================================================
-- alter table AUTHORITY_INFO drop index INDEX_AUTHOR_SIGN;

-- alter table AUTHORITY_INFO drop index INDEX_AUTHOR_NAME;

-- alter table USER_BASIC drop index INDEX_USER_LOGIN;

-- alter table PROJECT_INFO drop index INDEX_PROJECT_NAME;

-- alter table PROJECT_INFO drop index INDEX_PROJECT_NO;

-- alter table EDAILY_REPORT drop index INDEX_OWER_DATE;

-- alter table USER_ADDITION DROP FOREIGN KEY FK_Reference_34;

-- alter table SCOURCE_INFO DROP FOREIGN KEY FK_Reference_32;
 
-- alter table ROLE_MAP_AUTHOR DROP FOREIGN KEY FK_Reference_49;

-- alter table ROLE_MAP_AUTHOR DROP FOREIGN KEY FK_Reference_50;

-- alter table USER_BASIC DROP FOREIGN KEY FK_Reference_28;
      
-- alter table USER_MAP_ROLE DROP FOREIGN KEY FK_Reference_46;

-- alter table USER_MAP_ROLE DROP FOREIGN KEY FK_Reference_51;

-- alter table ACCESS_DATA_POWER DROP FOREIGN KEY FK_Reference_43;

-- alter table ACCESS_DATA_POWER DROP FOREIGN KEY FK_Reference_44;

-- alter table TEAM_TRANSFER_LOGS DROP FOREIGN KEY FK_Reference_29;

-- alter table TEAM_TRANSFER_LOGS DROP FOREIGN KEY FK_Reference_40;

-- alter table PROJECT_INFO DROP FOREIGN KEY FK_Reference_38;
    
-- alter table PROJECT_STATS DROP FOREIGN KEY FK_Reference_15;
      
-- alter table PRO_TRANSFER_LOGS DROP FOREIGN KEY FK_Reference_16;

-- alter table PRO_TRANSFER_LOGS DROP FOREIGN KEY FK_Reference_35;
      
-- alter table EDAILY_REPORT DROP FOREIGN KEY FK_Reference_17;

-- alter table EDAILY_REPORT DROP FOREIGN KEY FK_Reference_25;

-- alter table EDAILY_REPORT DROP FOREIGN KEY FK_Reference_36;
      
-- alter table EWEEKLY_REPORT DROP FOREIGN KEY FK_Reference_27;

-- alter table EWEEKLY_REPORT DROP FOREIGN KEY FK_Reference_31;
      
-- alter table PWEEKLY_REPORT DROP FOREIGN KEY FK_Reference_24;

-- alter table PWEEKLY_REPORT DROP FOREIGN KEY FK_Reference_37;

-- alter table PWEEKLY_MEMBERPLAN DROP FOREIGN KEY FK_Reference_33;
      
-- alter table PWEEKLY_PROBLEMS DROP FOREIGN KEY FK_Reference_26;

-- alter table TODO_LIST DROP FOREIGN KEY FK_Reference_42;

-- alter table CHECKING_RULE DROP FOREIGN KEY FK_REF_WR_M_DEPT;

-- alter table CHECKING_RULE DROP FOREIGN KEY FK_REF_WR_M_USER;

      
/*==============================================================*/
/* Index: INDEX_AUTHOR_NAME                                     */
/*==============================================================*/
create unique index INDEX_AUTHOR_NAME on AUTHORITY_INFO
(
   AUTHOR_NAME
);

/*==============================================================*/
/* Index: INDEX_AUTHOR_SIGN                                     */
/*==============================================================*/
create unique index INDEX_AUTHOR_SIGN on AUTHORITY_INFO
(
   AUTHOR_SIGN
);

/*==============================================================*/
/* Index: INDEX_USER_LOGIN                                      */
/*==============================================================*/
create unique index INDEX_USER_LOGIN on USER_BASIC
(
   USER_LOGINID
);

/*==============================================================*/
/* Index: INDEX_OWER_DATE                                       */
/*==============================================================*/
create index INDEX_OWER_DATE on EDAILY_REPORT
(
   USER_ID,
   REPORT_DATE
);

alter table USER_ADDITION add constraint FK_Reference_34 foreign key (USER_ID)
      references USER_BASIC (USER_ID) on delete restrict on update restrict;

alter table SCOURCE_INFO add constraint FK_Reference_32 foreign key (AUTHOR_ID)
      references AUTHORITY_INFO (AUTHOR_ID) on delete restrict on update restrict;
 
alter table ROLE_MAP_AUTHOR add constraint FK_Reference_49 foreign key (AUTHOR_ID)
      references AUTHORITY_INFO (AUTHOR_ID) on delete restrict on update restrict;

alter table ROLE_MAP_AUTHOR add constraint FK_Reference_50 foreign key (ROLE_ID)
      references ROLE_INFO (ROLE_ID) on delete restrict on update restrict;

alter table USER_BASIC add constraint FK_Reference_28 foreign key (DEPT_ID)
      references DEPARTMENT_INFO (DEPT_ID) on delete restrict on update restrict;
      
alter table USER_MAP_ROLE add constraint FK_Reference_46 foreign key (USER_ID)
      references USER_BASIC (USER_ID) on delete restrict on update restrict;

alter table USER_MAP_ROLE add constraint FK_Reference_51 foreign key (ROLE_ID)
      references ROLE_INFO (ROLE_ID) on delete restrict on update restrict;

alter table ACCESS_DATA_POWER add constraint FK_Reference_43 foreign key (USER_ID)
      references USER_BASIC (USER_ID) on delete restrict on update restrict;

alter table CHECKING_RULE add constraint FK_REF_WR_M_DEPT foreign key (DEPT_ID)
      references DEPARTMENT_INFO (DEPT_ID) on delete restrict on update restrict;

alter table CHECKING_RULE add constraint FK_REF_WR_M_USER foreign key (USER_ID)
      references USER_BASIC (USER_ID) on delete restrict on update restrict;
      
alter table ACCESS_DATA_POWER add constraint FK_Reference_44 foreign key (DEPT_ID)
      references DEPARTMENT_INFO (DEPT_ID) on delete restrict on update restrict;

alter table TEAM_TRANSFER_LOGS add constraint FK_Reference_29 foreign key (DEPT_ID)
      references DEPARTMENT_INFO (DEPT_ID) on delete restrict on update restrict;

alter table TEAM_TRANSFER_LOGS add constraint FK_Reference_40 foreign key (USER_ID)
      references USER_BASIC (USER_ID) on delete restrict on update restrict;

alter table PROJECT_INFO add constraint FK_Reference_38 foreign key (DEPT_ID)
      references DEPARTMENT_INFO (DEPT_ID) on delete restrict on update restrict;
    
alter table PROJECT_STATS add constraint FK_Reference_15 foreign key (PROJECT_ID)
      references PROJECT_INFO (PROJECT_ID) on delete restrict on update restrict;
      
alter table PRO_TRANSFER_LOGS add constraint FK_Reference_16 foreign key (PROJECT_ID)
      references PROJECT_INFO (PROJECT_ID) on delete restrict on update restrict;

alter table PRO_TRANSFER_LOGS add constraint FK_Reference_35 foreign key (USER_ID)
      references USER_BASIC (USER_ID) on delete restrict on update restrict;
      
alter table EDAILY_REPORT add constraint FK_Reference_17 foreign key (PROJECT_ID)
      references PROJECT_INFO (PROJECT_ID) on delete restrict on update restrict;

alter table EDAILY_REPORT add constraint FK_Reference_25 foreign key (DEPT_ID)
      references DEPARTMENT_INFO (DEPT_ID) on delete restrict on update restrict;

alter table EDAILY_REPORT add constraint FK_Reference_36 foreign key (USER_ID)
      references USER_BASIC (USER_ID) on delete restrict on update restrict;
      
alter table EWEEKLY_REPORT add constraint FK_Reference_27 foreign key (USER_ID)
      references USER_BASIC (USER_ID) on delete restrict on update restrict;

alter table EWEEKLY_REPORT add constraint FK_Reference_31 foreign key (DEPT_ID)
      references DEPARTMENT_INFO (DEPT_ID) on delete restrict on update restrict;
      
alter table PWEEKLY_REPORT add constraint FK_Reference_24 foreign key (PROJECT_ID)
      references PROJECT_INFO (PROJECT_ID) on delete restrict on update restrict;

alter table PWEEKLY_REPORT add constraint FK_Reference_37 foreign key (USER_ID)
      references USER_BASIC (USER_ID) on delete restrict on update restrict;

alter table PWEEKLY_MEMBERPLAN add constraint FK_Reference_33 foreign key (REPORT_ID)
      references PWEEKLY_REPORT (REPORT_ID) on delete restrict on update restrict;
      
alter table PWEEKLY_PROBLEMS add constraint FK_Reference_26 foreign key (REPORT_ID)
      references PWEEKLY_REPORT (REPORT_ID) on delete restrict on update restrict;
      
alter table TODO_LIST add constraint FK_Reference_42 foreign key (USER_ID)
      references USER_BASIC (USER_ID) on delete restrict on update restrict;
      
