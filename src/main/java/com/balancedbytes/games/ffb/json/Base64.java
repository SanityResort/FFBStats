package com.balancedbytes.games.ffb.json;

import java.util.Arrays;

class Base64 {
    private static final char[] CA = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();
    private static final int[] IA = new int[256];

    private static char[] encodeToChar(byte[] sArr, boolean lineSep) {
        int sLen = sArr != null ? sArr.length : 0;
        if (sLen == 0) {
            return new char[0];
        }
        int eLen = sLen / 3 * 3;
        int cCnt = (sLen - 1) / 3 + 1 << 2;
        int dLen = cCnt + (lineSep ? (cCnt - 1) / 76 << 1 : 0);
        char[] dArr = new char[dLen];
        int s = 0;
        int d = 0;
        int cc = 0;
        while (s < eLen) {
            int i = (sArr[s++] & 255) << 16 | (sArr[s++] & 255) << 8 | sArr[s++] & 255;
            dArr[d++] = CA[i >>> 18 & 63];
            dArr[d++] = CA[i >>> 12 & 63];
            dArr[d++] = CA[i >>> 6 & 63];
            dArr[d++] = CA[i & 63];
            if (!lineSep || ++cc != 19 || d >= dLen - 2) continue;
            dArr[d++] = 13;
            dArr[d++] = 10;
            cc = 0;
        }
        int left = sLen - eLen;
        if (left > 0) {
            int i = (sArr[eLen] & 255) << 10 | (left == 2 ? (sArr[sLen - 1] & 255) << 2 : 0);
            dArr[dLen - 4] = CA[i >> 12];
            dArr[dLen - 3] = CA[i >>> 6 & 63];
            dArr[dLen - 2] = left == 2 ? CA[i & 63] : 61;
            dArr[dLen - 1] = 61;
        }
        return dArr;
    }

    static String encodeToString(byte[] sArr, boolean lineSep) {
        return new String(Base64.encodeToChar(sArr, lineSep));
    }

    static byte[] decodeFast(String s) {
        int sIx;
        int sLen = s.length();
        if (sLen == 0) {
            return new byte[0];
        }
        int eIx = sLen - 1;
        for (sIx = 0; sIx < eIx && IA[s.charAt(sIx) & 255] < 0; ++sIx) {
        }
        while (eIx > 0 && IA[s.charAt(eIx) & 255] < 0) {
            --eIx;
        }
        int pad = s.charAt(eIx) == '=' ? (s.charAt(eIx - 1) == '=' ? 2 : 1) : 0;
        int cCnt = eIx - sIx + 1;
        int sepCnt = sLen > 76 ? (s.charAt(76) == '\r' ? cCnt / 78 : 0) << 1 : 0;
        int len = ((cCnt - sepCnt) * 6 >> 3) - pad;
        byte[] dArr = new byte[len];
        int d = 0;
        int cc = 0;
        int eLen = len / 3 * 3;
        while (d < eLen) {
            int i = IA[s.charAt(sIx++)] << 18 | IA[s.charAt(sIx++)] << 12 | IA[s.charAt(sIx++)] << 6 | IA[s.charAt(sIx++)];
            dArr[d++] = (byte)(i >> 16);
            dArr[d++] = (byte)(i >> 8);
            dArr[d++] = (byte)i;
            if (sepCnt <= 0 || ++cc != 19) continue;
            sIx += 2;
            cc = 0;
        }
        if (d < len) {
            int i = 0;
            int j = 0;
            while (sIx <= eIx - pad) {
                i |= IA[s.charAt(sIx++)] << 18 - j * 6;
                ++j;
            }
            int r = 16;
            while (d < len) {
                dArr[d++] = (byte)(i >> r);
                r -= 8;
            }
        }
        return dArr;
    }

    static {
        Arrays.fill(IA, -1);
        int i = 0;
        int iS = CA.length;
        while (i < iS) {
            Base64.IA[Base64.CA[i]] = i++;
        }
        Base64.IA[61] = 0;
    }
}

