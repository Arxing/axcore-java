package org.arxing.core;

import org.arxing.core.utils.Logger;
import org.arxing.core.utils.TimeUtils;

import java.io.IOException;
import java.util.Date;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        Date date = TimeUtils.now();
        TimeUtils.setMonth(date, 0);
        System.out.println(TimeUtils.format(TimeUtils.PATTERN_FULL_1, date));
    }
}
