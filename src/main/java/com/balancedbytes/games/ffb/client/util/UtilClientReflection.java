/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.client.util;

import javax.swing.*;
import java.lang.reflect.Method;

public class UtilClientReflection {
    private static int javaVersionMajor = -1;
    private static int javaVersionMinor = -1;

    public static boolean compliesTo(int major, int minor) {
        String version;
        String[] list;
        if (javaVersionMajor < 0 && (list = (version = System.getProperty("java.specification.version")).split("\\.")).length >= 2) {
            javaVersionMajor = Integer.parseInt(list[0]);
            javaVersionMinor = Integer.parseInt(list[1]);
        }
        return javaVersionMajor > major || javaVersionMajor == major && javaVersionMinor >= minor;
    }

    public static OS getOS() {
        String os = System.getProperty("os.name");
        if (os.contains("Windows")) {
            return OS.Windows;
        }
        if (os.contains("Linux")) {
            return OS.Linux;
        }
        if (os.contains("Mac OS X")) {
            return OS.OSX;
        }
        return OS.Other;
    }

    private static void callMethodBoolToVoid(Object o, String method, boolean flag) {
        try {
            Method m = o.getClass().getMethod(method, Boolean.TYPE);
            m.invoke(o, flag);
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    private static int callMethodIntToInt(Object o, String method, int value) {
        int result = 0;
        try {
            Method m = o.getClass().getMethod(method, Integer.TYPE);
            result = (Integer)m.invoke(o, value);
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
        return result;
    }

    public static void setFillsViewportHeight(JTable table, boolean flag) {
        if (UtilClientReflection.compliesTo(1, 6)) {
            UtilClientReflection.callMethodBoolToVoid(table, "setFillsViewportHeight", flag);
        }
    }

    public static void setAutoCreateRowSorter(JTable table, boolean flag) {
        if (UtilClientReflection.compliesTo(1, 6)) {
            UtilClientReflection.callMethodBoolToVoid(table, "setAutoCreateRowSorter", flag);
        }
    }

    public static int convertRowIndexToModel(JTable table, int row) {
        if (UtilClientReflection.compliesTo(1, 6)) {
            return UtilClientReflection.callMethodIntToInt(table, "convertRowIndexToModel", row);
        }
        return table.getSelectedRow();
    }

    public static enum OS {
        Windows,
        Linux,
        OSX,
        Other;
        

        private OS() {
        }
    }

}

