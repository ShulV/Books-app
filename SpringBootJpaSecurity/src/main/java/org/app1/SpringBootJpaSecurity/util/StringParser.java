package org.app1.SpringBootJpaSecurity.util;

import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class StringParser {
    public static int[] parseStrNumSeqToList(String strNumSeq) {
        if (strNumSeq == null) {
            return new int[]{};
        }
        return Arrays.stream(strNumSeq.split(",")).mapToInt(Integer::parseInt).toArray();
    }
}
