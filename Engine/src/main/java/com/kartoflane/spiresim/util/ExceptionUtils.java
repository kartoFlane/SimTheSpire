package com.kartoflane.spiresim.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionUtils {
    private ExceptionUtils() {
    }

    public static String getStackTrace(Throwable e) {
        // close() actually doesn't do anything on StringWriter, but let's
        // call it anyway, since otherwise it just looks wrong.
        StringWriter sw = new StringWriter();
        try (PrintWriter pw = new PrintWriter(sw)) {
            e.printStackTrace(pw);
            return sw.toString();
        }
    }
}
