/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.util.ArrayTool;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class PasswordChallenge {
    public static byte[] fromHexString(String pHexString) {
        int len = pHexString.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte)((Character.digit(pHexString.charAt(i), 16) << 4) + Character.digit(pHexString.charAt(i + 1), 16));
        }
        return data;
    }

    public static String toHexString(byte[] pBytes) {
        StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < pBytes.length; ++i) {
            hexString.append(Integer.toString((pBytes[i] & 255) + 256, 16).substring(1));
        }
        return hexString.toString();
    }

    public static byte[] concat(byte[] pBytes1, byte[] pBytes2) {
        int i;
        int size1 = pBytes1 != null ? pBytes1.length : 0;
        int size2 = pBytes2 != null ? pBytes2.length : 0;
        byte[] result = new byte[size1 + size2];
        for (i = 0; i < size1; ++i) {
            result[i] = pBytes1[i];
        }
        for (i = 0; i < size2; ++i) {
            result[i + size1] = pBytes2[i];
        }
        return result;
    }

    public static byte[] xor(byte[] pBytes, byte pMask) {
        byte[] result = null;
        if (ArrayTool.isProvided(pBytes)) {
            result = new byte[pBytes.length];
            for (int i = 0; i < result.length; ++i) {
                result[i] = (byte)(pBytes[i] ^ pMask);
            }
        } else {
            result = new byte[]{};
        }
        return result;
    }

    public static byte[] md5Encode(byte[] pBytes) throws NoSuchAlgorithmException {
        MessageDigest md5Digest = MessageDigest.getInstance("MD5");
        return md5Digest.digest(pBytes);
    }

    public static String createResponse(String pChallenge, byte[] pMd5EncodedPassword) throws IOException, NoSuchAlgorithmException {
        return PasswordChallenge.toHexString(pMd5EncodedPassword);
    }

    public static void main(String[] args) {
        try {
            System.out.println(PasswordChallenge.toHexString(PasswordChallenge.md5Encode(args[0].getBytes())));
        }
        catch (Exception pAll) {
            pAll.printStackTrace();
        }
    }
}

