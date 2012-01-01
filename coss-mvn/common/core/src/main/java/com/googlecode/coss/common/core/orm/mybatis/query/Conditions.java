package com.googlecode.coss.common.core.orm.mybatis.query;

public class Conditions {

    public String toString() {
        StringBuilder sb = new StringBuilder().append(condition.toString()).append(' ')
                .append(fieldName).append(' ').append(compare.getValue()).append(' ').append(value)
                .append('\n');
        return sb.toString();
    }

    public Conditions ignoreCase() {
        ignoreCase = true;
        return this;
    }

    protected Conditions(String propertyName) {
        this.fieldName = propertyName;
        this.condition = Condition.AND;
        this.compare = Compare.equals;
    }

    protected Conditions(String propertyName, Condition condition) {
        this.fieldName = propertyName;
        this.condition = condition;
        this.compare = Compare.equals;
    }

    public Conditions(String propertyName, Condition condition, Compare compare) {
        this.fieldName = propertyName;
        this.condition = condition;
        this.compare = compare;
    }

    public Conditions(String propertyName, Condition condition, Compare compare, Object value) {
        this.fieldName = propertyName;
        this.condition = condition;
        this.compare = compare;
        this.value = value;
    }

    public static Conditions and(String propertyName, Compare compare, Object value) {
        return new Conditions(propertyName, Condition.AND, compare, value);
    }

    public static Conditions or(String propertyName, Compare compare, Object value) {
        return new Conditions(propertyName, Condition.OR, compare, value);
    }

    public static Conditions not(String propertyName, Compare compare, Object value) {
        return new Conditions(propertyName, Condition.NOT, compare, value);
    }

    public static Conditions and(String propertyName, Compare compare) {
        return new Conditions(propertyName, Condition.AND, compare);
    }

    public static Conditions or(String propertyName, Compare compare) {
        return new Conditions(propertyName, Condition.OR, compare);
    }

    public static Conditions not(String propertyName, Compare compare) {
        return new Conditions(propertyName, Condition.NOT, compare);
    }

    public static Conditions and(String propertyName) {
        return new Conditions(propertyName, Condition.AND, Compare.equals);
    }

    public static Conditions or(String propertyName) {
        return new Conditions(propertyName, Condition.OR, Compare.equals);
    }

    public static Conditions not(String propertyName) {
        return new Conditions(propertyName, Condition.NOT, Compare.equals);
    }

    private Condition condition;
    private Compare   compare;
    @SuppressWarnings("unused")
    private boolean   ignoreCase;
    private String    fieldName;
    private Object    value;

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public Compare getCompare() {
        return compare;
    }

    public void setCompare(Compare compare) {
        this.compare = compare;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getPropertyName() {
        return fieldName;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public static void main(String[] args) {
        Conditions c = Conditions.and("abc", Compare.greater);
        System.out.println(c);
    }
}
