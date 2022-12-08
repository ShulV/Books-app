package org.app1.util;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Component
public class StringParser {
    public static int[] parseStrNumSeqToList(String strNumSeq) {
        if (strNumSeq == null) {
            return new int[]{};
        }
        return Arrays.stream(strNumSeq.split(",")).mapToInt(Integer::parseInt).toArray();
    }
}
