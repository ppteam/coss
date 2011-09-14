package com.googlecode.coss.common.core.orm.mybatis.query;

public enum Compare {
    /** = */
    equals("="),
    /** != */
    unequal("!="),
    /** > */
    greater(">"),
    /** "<  or  &lt;" */
    less("<"),
    /** >= */
    greaterOrEqual(">="),
    /** <= or &lt;= */
    LesserOrEqual("<="),
    /** like */
    like("like"),
    /** exists 未实现 */
    exists("exists"),
    /** in 未实现 */
    in("in");

    private final String value;

    public String getValue() {
        return this.value;
    }

    private Compare(String value) {
        this.value = value;
    }

}
