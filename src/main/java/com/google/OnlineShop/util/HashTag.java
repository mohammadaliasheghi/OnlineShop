package com.google.OnlineShop.util;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public record HashTag() {

    public List<String> getTagList(StringBuilder text) {
        List<String> hashTagsList = new ArrayList<>();
        text.append(" ");
        int i = 0;
        int count = 0;
        while (i < text.length()) {
            count++;
            if (text.charAt(i) == '#' && text.charAt(i + 1) != ' ') {
                List<String> tags = getHashTagText(i, String.valueOf(text));
                if (tags.size() > 0) {
                    for (String tag : tags) {
                        count += tag.length();
                        hashTagsList.add(tag);
                    }
                    i = count;
                }
            } else
                i++;
        }
        return hashTagsList;
    }

    private List<String> getHashTagText(int c, String text) {
        List<String> tags = new ArrayList<>();
        String findHashTag = text.substring(c + 1);
        String endTag = findHashTag.substring(0, findHashTag.indexOf(" ")) + " ";
        String hashTag;
        int i = 0;
        while (endTag.charAt(i) != ' ') {
            if (endTag.charAt(i) == '#') {
                hashTag = endTag.substring(0, endTag.indexOf("#"));
                endTag = endTag.substring(i + 1);
                i = 0;
                tags.add(hashTag);
            } else
                i++;
        }
        String lastTag = endTag.substring(0, endTag.indexOf(" "));
        tags.add(lastTag);

        return tags;
    }
}
