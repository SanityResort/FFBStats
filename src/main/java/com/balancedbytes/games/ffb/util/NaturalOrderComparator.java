/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.util;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class NaturalOrderComparator
implements Comparator<String> {
    private int compareRight(String a, String b) {
        int bias = 0;
        int ia = 0;
        int ib = 0;
        do {
            char ca = this.charAt(a, ia);
            char cb = this.charAt(b, ib);
            if (!Character.isDigit(ca) && !Character.isDigit(cb)) {
                return bias;
            }
            if (!Character.isDigit(ca)) {
                return -1;
            }
            if (!Character.isDigit(cb)) {
                return 1;
            }
            if (ca < cb) {
                if (bias == 0) {
                    bias = -1;
                }
            } else if (ca > cb) {
                if (bias == 0) {
                    bias = 1;
                }
            } else if (ca == '\u0000' && cb == '\u0000') {
                return bias;
            }
            ++ia;
            ++ib;
        } while (true);
    }

    @Override
    public int compare(String a, String b) {
        int ia = 0;
        int ib = 0;
        int nza = 0;
        int nzb = 0;
        do {
            int result;
            nzb = 0;
            nza = 0;
            char ca = this.charAt(a, ia);
            char cb = this.charAt(b, ib);
            while (Character.isSpaceChar(ca) || ca == '0') {
                nza = ca == '0' ? ++nza : 0;
                ca = this.charAt(a, ++ia);
            }
            while (Character.isSpaceChar(cb) || cb == '0') {
                nzb = cb == '0' ? ++nzb : 0;
                cb = this.charAt(b, ++ib);
            }
            if (Character.isDigit(ca) && Character.isDigit(cb) && (result = this.compareRight(a.substring(ia), b.substring(ib))) != 0) {
                return result;
            }
            if (ca == '\u0000' && cb == '\u0000') {
                return nza - nzb;
            }
            if (ca < cb) {
                return -1;
            }
            if (ca > cb) {
                return 1;
            }
            ++ia;
            ++ib;
        } while (true);
    }

    private char charAt(String s, int i) {
        if (i >= s.length()) {
            return '\u0000';
        }
        return s.charAt(i);
    }

    public static void main(String[] args) {
        String[] strings = new String[]{"1-2", "1-02", "1-20", "10-20", "fred", "jane", "pic01", "pic2", "pic02", "pic02a", "pic3", "pic4", "pic 4 else", "pic 5", "pic05", "pic 5", "pic 5 something", "pic 6", "pic   7", "pic100", "pic100a", "pic120", "pic121", "pic02000", "tom", "x2-g8", "x2-y7", "x2-y08", "x8-y8"};
        List<String> orig = Arrays.asList(strings);
        System.out.println("Original: " + orig);
        List<String> scrambled = Arrays.asList(strings);
        Collections.shuffle(scrambled);
        System.out.println("Scrambled: " + scrambled);
        Collections.sort(scrambled, new NaturalOrderComparator());
        System.out.println("Sorted: " + scrambled);
    }
}

