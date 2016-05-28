/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.util;

import com.balancedbytes.games.ffb.FantasyFootballException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTool {
    public static final DateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    public static boolean isEqual(Date pDate1, Date pDate2) {
        if (pDate1 != null) {
            return pDate1.equals(pDate2);
        }
        if (pDate2 != null) {
            return pDate2.equals(pDate1);
        }
        return true;
    }

    public static String formatTimestamp(Date pTimestamp) {
        return TIMESTAMP_FORMAT.format(pTimestamp);
    }

    public static Date parseTimestamp(String pTimestamp) {
        try {
            return TIMESTAMP_FORMAT.parse(pTimestamp);
        }
        catch (ParseException parseException) {
            throw new FantasyFootballException(parseException);
        }
    }
}

