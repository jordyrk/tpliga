package org.tpl.business.util;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;

public class SplitterTest {
    @Test
    public void testCreateCommaSeparetedString() throws Exception {
        List<String> list = new ArrayList<String>();
        list.add("one");
        list.add("two");
        String commaSeparetedString = Splitter.createCommaSeparetedString(list);
        assertEquals("one,two", commaSeparetedString);


    }
}
