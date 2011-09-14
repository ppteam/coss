package com.googlecode.coss.common.utils.text;

import java.util.ArrayList;
import java.util.List;

public class HtmlText {

    // prepared tags
    private static List<String> tags = new ArrayList<String>();

    static void init() {
        tags.add("&nbsp;");
        tags.add("<br/>");
    }

    static {
        init();
    }

    /**
     * <p>
     * Remove all standard HTML tags
     * </p>
     * 
     * @param source
     * @return
     */
    public static String removeTags(String source) {
        for (String tag : tags) {
            source = source.replace(tag, "");
        }
        char[] cs = source.toCharArray();
        StringBuilder sb = new StringBuilder();
        boolean nextIsHtml = false;
        for (char c : cs) {
            if (c == '<') {
                nextIsHtml = true;
            } else if (c == '>' && nextIsHtml) {
                nextIsHtml = false;
            } else {
                if (!nextIsHtml) {
                    sb.append(c);
                }
            }
        }
        return sb.toString();
    }

}
