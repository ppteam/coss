/**
 * 
 */
package com.googlecode.coss.common.utils.collections;

import com.googlecode.coss.common.utils.lang.CharUtils;

/**
 * <p>
 * Stack for Character
 * </p>
 */
public class CharStack {

    private ArrayStack<Character> stack;

    /**
     * <p>
     * Construct a new empty CharStack
     * </p
     */
    public CharStack() {
        this.stack = new ArrayStack<Character>();
    }

    /**
     * <p>
     * Construct a new empty CharStack with an initial size
     * </p>
     * 
     * @param size
     */
    public CharStack(int size) {
        this.stack = new ArrayStack<Character>(size);
    }

    /**
     * <p>
     * Push a char to stack
     * </p>
     * 
     * @param c
     */
    public void push(char c) {
        stack.push(c);
    }

    /**
     * <p>
     * Push char one by one from string head to tail
     * </p>
     * 
     * @param str
     */
    public void pushString(String str) {
        char[] cs = str.toCharArray();
        for (char c : cs) {
            this.push(c);
        }
    }

    /**
     * <p>
     * Pop a char form stack
     * </p>
     * 
     * @return
     */
    public char pop() {
        return stack.pop();
    }

    /**
     * <p>
     * Check the stack whether it do not contains any elements
     * </p>
     * 
     * @return
     */
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    /**
     * <p>
     * Return the elements number of the stack
     * </p>
     * 
     * @return
     */
    public int size() {
        return stack.size();
    }

    /**
     * <p>
     * Return a char sequence from stack top to bottom and stop when meet any
     * char of input
     * </p>
     * <p>
     * etc. String str = "My name is unclpeng. Come form China. Ha Ha";
     * System.out.println(stack.popUntil('.')); return Ha Ha
     * </p>
     * 
     * @param cs
     * @return
     */
    public String popUntil(char... cs) {
        StringBuilder sb = new StringBuilder();

        if (!isEmpty() && cs != null) {
            char c = this.pop();
            boolean reachEnd = false;
            while (CharUtils.notInArray(c, cs)) {
                sb.append(c);
                if (!isEmpty()) {
                    c = this.pop();
                } else {
                    reachEnd = true;
                    break;
                }
            }
            if (!reachEnd) {
                this.stack.push(c);
            }
        }
        return sb.reverse().toString();
    }

    /**
     * <p>
     * Return a char sequence from stack bottom to top and stop when meet any
     * char of input
     * </p>
     * <p>
     * etc. String str = "My name is unclpeng. Come form China. Ha Ha";
     * System.out.println(stack.popFromLastUntil('.')); return Come form China.
     * Ha Ha
     * </p>
     * 
     * @param cs
     * @return
     */
    public String popFromLastUntil(char... cs) {
        int size = this.size();
        int fromIndex = 0;
        for (int i = 0; i < size; i++) {
            char c = this.stack.get(i);
            if (CharUtils.inArray(c, cs)) {
                fromIndex = i;
                break;
            }
        }
        StringBuilder sb = new StringBuilder();
        if (fromIndex != 0) {
            // fromIndex++;
        }
        fromIndex++;
        for (int i = fromIndex, j = 0; i < size; i++) {

            sb.append(this.stack.remove(i - j));
            j++;
        }
        return sb.toString();

    }

}
