package com.google.OnlineShop.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class HashTagTest {

    @Autowired
    protected HashTag hashTag;

    protected String string = "return #test HashTag and #tag in #example#text";
    protected List<String> hashTags = new ArrayList<>();

    @Test
    protected void itShouldReturnHashTagListInString() {
        //Given
        List<String> tags = hashTag.getTagList(new StringBuilder(string));
        hashTags.add("test");
        hashTags.add("tag");
        hashTags.add("example");
        hashTags.add("text");
        //When,Then
        Assertions.assertThat(hashTags).isEqualTo(tags);
        Assertions.assertThat(tags.size()).isEqualTo(4);
    }
}
