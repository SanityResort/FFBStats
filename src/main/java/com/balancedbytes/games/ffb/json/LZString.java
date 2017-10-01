/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class LZString {
    static String keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";

    public static String compress(String uncompressed) {
        int i;
        int value;
        if (uncompressed == null) {
            return "";
        }
        HashMap<String, Integer> context_dictionary = new HashMap<String, Integer>();
        HashSet<String> context_dictionaryToCreate = new HashSet<String>();
        String context_c = "";
        String context_wc = "";
        String context_w = "";
        double context_enlargeIn = 2.0;
        int context_dictSize = 3;
        int context_numBits = 2;
        String context_data_string = "";
        int context_data_val = 0;
        int context_data_position = 0;
        for (int ii = 0; ii < uncompressed.length(); ++ii) {
            int i2;
            context_c = "" + uncompressed.charAt(ii);
            if (!context_dictionary.containsKey(context_c)) {
                context_dictionary.put(context_c, context_dictSize++);
                context_dictionaryToCreate.add(context_c);
            }
            if (context_dictionary.containsKey(context_wc = context_w + context_c)) {
                context_w = context_wc;
                continue;
            }
            if (context_dictionaryToCreate.contains(context_w)) {
                if (context_w.charAt(0) < '\u0100') {
                    for (i2 = 0; i2 < context_numBits; ++i2) {
                        context_data_val <<= 1;
                        if (context_data_position == 15) {
                            context_data_position = 0;
                            context_data_string = context_data_string + (char)context_data_val;
                            context_data_val = 0;
                            continue;
                        }
                        ++context_data_position;
                    }
                    value = context_w.charAt(0);
                    for (i2 = 0; i2 < 8; ++i2) {
                        context_data_val = context_data_val << 1 | value & 1;
                        if (context_data_position == 15) {
                            context_data_position = 0;
                            context_data_string = context_data_string + (char)context_data_val;
                            context_data_val = 0;
                        } else {
                            ++context_data_position;
                        }
                        value >>= 1;
                    }
                } else {
                    value = 1;
                    for (i2 = 0; i2 < context_numBits; ++i2) {
                        context_data_val = context_data_val << 1 | value;
                        if (context_data_position == 15) {
                            context_data_position = 0;
                            context_data_string = context_data_string + (char)context_data_val;
                            context_data_val = 0;
                        } else {
                            ++context_data_position;
                        }
                        value = 0;
                    }
                    value = context_w.charAt(0);
                    for (i2 = 0; i2 < 16; ++i2) {
                        context_data_val = context_data_val << 1 | value & 1;
                        if (context_data_position == 15) {
                            context_data_position = 0;
                            context_data_string = context_data_string + (char)context_data_val;
                            context_data_val = 0;
                        } else {
                            ++context_data_position;
                        }
                        value >>= 1;
                    }
                }
                if (Double.valueOf(context_enlargeIn -= 1.0).intValue() == 0) {
                    context_enlargeIn = Math.pow(2.0, context_numBits);
                    ++context_numBits;
                }
                context_dictionaryToCreate.remove(context_w);
            } else {
                value = (Integer)context_dictionary.get(context_w);
                for (i2 = 0; i2 < context_numBits; ++i2) {
                    context_data_val = context_data_val << 1 | value & 1;
                    if (context_data_position == 15) {
                        context_data_position = 0;
                        context_data_string = context_data_string + (char)context_data_val;
                        context_data_val = 0;
                    } else {
                        ++context_data_position;
                    }
                    value >>= 1;
                }
            }
            if (Double.valueOf(context_enlargeIn -= 1.0).intValue() == 0) {
                context_enlargeIn = Math.pow(2.0, context_numBits);
                ++context_numBits;
            }
            context_dictionary.put(context_wc, context_dictSize++);
            context_w = new String(context_c);
        }
        if (!"".equals(context_w)) {
            if (context_dictionaryToCreate.contains(context_w)) {
                if (context_w.charAt(0) < '\u0100') {
                    for (i = 0; i < context_numBits; ++i) {
                        context_data_val <<= 1;
                        if (context_data_position == 15) {
                            context_data_position = 0;
                            context_data_string = context_data_string + (char)context_data_val;
                            context_data_val = 0;
                            continue;
                        }
                        ++context_data_position;
                    }
                    value = context_w.charAt(0);
                    for (i = 0; i < 8; ++i) {
                        context_data_val = context_data_val << 1 | value & 1;
                        if (context_data_position == 15) {
                            context_data_position = 0;
                            context_data_string = context_data_string + (char)context_data_val;
                            context_data_val = 0;
                        } else {
                            ++context_data_position;
                        }
                        value >>= 1;
                    }
                } else {
                    value = 1;
                    for (i = 0; i < context_numBits; ++i) {
                        context_data_val = context_data_val << 1 | value;
                        if (context_data_position == 15) {
                            context_data_position = 0;
                            context_data_string = context_data_string + (char)context_data_val;
                            context_data_val = 0;
                        } else {
                            ++context_data_position;
                        }
                        value = 0;
                    }
                    value = context_w.charAt(0);
                    for (i = 0; i < 16; ++i) {
                        context_data_val = context_data_val << 1 | value & 1;
                        if (context_data_position == 15) {
                            context_data_position = 0;
                            context_data_string = context_data_string + (char)context_data_val;
                            context_data_val = 0;
                        } else {
                            ++context_data_position;
                        }
                        value >>= 1;
                    }
                }
                if (Double.valueOf(context_enlargeIn -= 1.0).intValue() == 0) {
                    context_enlargeIn = Math.pow(2.0, context_numBits);
                    ++context_numBits;
                }
                context_dictionaryToCreate.remove(context_w);
            } else {
                value = (Integer)context_dictionary.get(context_w);
                for (i = 0; i < context_numBits; ++i) {
                    context_data_val = context_data_val << 1 | value & 1;
                    if (context_data_position == 15) {
                        context_data_position = 0;
                        context_data_string = context_data_string + (char)context_data_val;
                        context_data_val = 0;
                    } else {
                        ++context_data_position;
                    }
                    value >>= 1;
                }
            }
            if (Double.valueOf(context_enlargeIn -= 1.0).intValue() == 0) {
                context_enlargeIn = Math.pow(2.0, context_numBits);
                ++context_numBits;
            }
        }
        value = 2;
        for (i = 0; i < context_numBits; ++i) {
            context_data_val = context_data_val << 1 | value & 1;
            if (context_data_position == 15) {
                context_data_position = 0;
                context_data_string = context_data_string + (char)context_data_val;
                context_data_val = 0;
            } else {
                ++context_data_position;
            }
            value >>= 1;
        }
        do {
            context_data_val <<= 1;
            if (context_data_position == 15) break;
            ++context_data_position;
        } while (true);
        context_data_string = context_data_string + (char)context_data_val;
        return context_data_string;
    }

    public static String decompressHexString(String hexString) {
        if (hexString == null) {
            return "";
        }
        if (hexString.length() % 2 != 0) {
            throw new RuntimeException("Input string length should be divisible by two");
        }
        int[] intArr = new int[hexString.length() / 2];
        int i = 0;
        int k = 0;
        while (i < hexString.length()) {
            intArr[k] = Integer.parseInt("" + hexString.charAt(i) + hexString.charAt(i + 1), 16);
            i += 2;
            ++k;
        }
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < intArr.length; j += 2) {
            sb.append(Character.toChars(intArr[j] | intArr[j + 1] << 8));
        }
        return LZString.decompress(sb.toString());
    }

    public static String decompress(String compressed) {
        int resb;
        int power;
        if (compressed == null) {
            return "";
        }
        if (compressed == "") {
            return null;
        }
        ArrayList<String> dictionary = new ArrayList<String>(200);
        double enlargeIn = 4.0;
        int dictSize = 4;
        int numBits = 3;
        String entry = "";
        String c = "";
        Data data = Data.getInstance();
        data.string = compressed;
        data.val = compressed.charAt(0);
        data.position = 32768;
        data.index = 1;
        for (int i = 0; i < 3; ++i) {
            dictionary.add(i, "");
        }
        int bits = 0;
        double maxpower = Math.pow(2.0, 2.0);
        for (power = 1; power != Double.valueOf(maxpower).intValue(); power <<= 1) {
            resb = data.val & data.position;
            data.position >>= 1;
            if (data.position == 0) {
                data.position = 32768;
                data.val = data.string.charAt(data.index++);
            }
            bits |= (resb > 0 ? 1 : 0) * power;
        }
        switch (bits) {
            case 0: {
                bits = 0;
                maxpower = Math.pow(2.0, 8.0);
                for (power = 1; power != Double.valueOf(maxpower).intValue(); power <<= 1) {
                    resb = data.val & data.position;
                    data.position >>= 1;
                    if (data.position == 0) {
                        data.position = 32768;
                        data.val = data.string.charAt(data.index++);
                    }
                    bits |= (resb > 0 ? 1 : 0) * power;
                }
                c = c + (char)bits;
                break;
            }
            case 1: {
                bits = 0;
                maxpower = Math.pow(2.0, 16.0);
                for (power = 1; power != Double.valueOf(maxpower).intValue(); power <<= 1) {
                    resb = data.val & data.position;
                    data.position >>= 1;
                    if (data.position == 0) {
                        data.position = 32768;
                        data.val = data.string.charAt(data.index++);
                    }
                    bits |= (resb > 0 ? 1 : 0) * power;
                }
                c = c + (char)bits;
                break;
            }
            case 2: {
                return "";
            }
        }
        dictionary.add(3, c);
        String w = c;
        StringBuilder result = new StringBuilder(200);
        result.append(c);
        while (data.index <= data.string.length()) {
            bits = 0;
            maxpower = Math.pow(2.0, numBits);
            for (power = 1; power != Double.valueOf(maxpower).intValue(); power <<= 1) {
                resb = data.val & data.position;
                data.position >>= 1;
                if (data.position == 0) {
                    data.position = 32768;
                    data.val = data.string.charAt(data.index++);
                }
                bits |= (resb > 0 ? 1 : 0) * power;
            }
            int d = bits;
            switch (d) {
                case 0: {
                    bits = 0;
                    maxpower = Math.pow(2.0, 8.0);
                    for (power = 1; power != Double.valueOf(maxpower).intValue(); power <<= 1) {
                        resb = data.val & data.position;
                        data.position >>= 1;
                        if (data.position == 0) {
                            data.position = 32768;
                            data.val = data.string.charAt(data.index++);
                        }
                        bits |= (resb > 0 ? 1 : 0) * power;
                    }
                    String temp = "";
                    temp = temp + (char)bits;
                    dictionary.add(dictSize++, temp);
                    d = dictSize - 1;
                    enlargeIn -= 1.0;
                    break;
                }
                case 1: {
                    bits = 0;
                    maxpower = Math.pow(2.0, 16.0);
                    for (power = 1; power != Double.valueOf(maxpower).intValue(); power <<= 1) {
                        resb = data.val & data.position;
                        data.position >>= 1;
                        if (data.position == 0) {
                            data.position = 32768;
                            data.val = data.string.charAt(data.index++);
                        }
                        bits |= (resb > 0 ? 1 : 0) * power;
                    }
                    String temp = "";
                    temp = temp + (char)bits;
                    dictionary.add(dictSize++, temp);
                    d = dictSize - 1;
                    enlargeIn -= 1.0;
                    break;
                }
                case 2: {
                    return result.toString();
                }
            }
            if (Double.valueOf(enlargeIn).intValue() == 0) {
                enlargeIn = Math.pow(2.0, numBits);
                ++numBits;
            }
            if (d < dictionary.size() && dictionary.get(d) != null) {
                entry = (String)dictionary.get(d);
            } else if (d == dictSize) {
                entry = w + w.charAt(0);
            } else {
                return null;
            }
            result.append(entry);
            dictionary.add(dictSize++, w + entry.charAt(0));
            w = entry;
            if (Double.valueOf(enlargeIn -= 1.0).intValue() != 0) continue;
            enlargeIn = Math.pow(2.0, numBits);
            ++numBits;
        }
        return "";
    }

    public static String compressToUTF16(String input) {
        if (input == null) {
            return "";
        }
        String output = "";
        int current = 0;
        int status = 0;
        input = LZString.compress(input);
        block17 : for (int i = 0; i < input.length(); ++i) {
            char c = input.charAt(i);
            switch (status++) {
                case 0: {
                    output = output + (char)((c >> 1) + 32);
                    current = (c & '\u0001') << 14;
                    continue block17;
                }
                case 1: {
                    output = output + (char)(current + (c >> 2) + 32);
                    current = (c & 3) << 13;
                    continue block17;
                }
                case 2: {
                    output = output + (char)(current + (c >> 3) + 32);
                    current = (c & 7) << 12;
                    continue block17;
                }
                case 3: {
                    output = output + (char)(current + (c >> 4) + 32);
                    current = (c & 15) << 11;
                    continue block17;
                }
                case 4: {
                    output = output + (char)(current + (c >> 5) + 32);
                    current = (c & 31) << 10;
                    continue block17;
                }
                case 5: {
                    output = output + (char)(current + (c >> 6) + 32);
                    current = (c & 63) << 9;
                    continue block17;
                }
                case 6: {
                    output = output + (char)(current + (c >> 7) + 32);
                    current = (c & 127) << 8;
                    continue block17;
                }
                case 7: {
                    output = output + (char)(current + (c >> 8) + 32);
                    current = (c & 255) << 7;
                    continue block17;
                }
                case 8: {
                    output = output + (char)(current + (c >> 9) + 32);
                    current = (c & 511) << 6;
                    continue block17;
                }
                case 9: {
                    output = output + (char)(current + (c >> 10) + 32);
                    current = (c & 1023) << 5;
                    continue block17;
                }
                case 10: {
                    output = output + (char)(current + (c >> 11) + 32);
                    current = (c & 2047) << 4;
                    continue block17;
                }
                case 11: {
                    output = output + (char)(current + (c >> 12) + 32);
                    current = (c & 4095) << 3;
                    continue block17;
                }
                case 12: {
                    output = output + (char)(current + (c >> 13) + 32);
                    current = (c & 8191) << 2;
                    continue block17;
                }
                case 13: {
                    output = output + (char)(current + (c >> 14) + 32);
                    current = (c & 16383) << 1;
                    continue block17;
                }
                case 14: {
                    output = output + (char)(current + (c >> 15) + 32);
                    output = output + (char)((c & 32767) + 32);
                    status = 0;
                }
            }
        }
        output = output + (char)(current + 32);
        return output;
    }

    public static String decompressFromUTF16(String input) {
        if (input == null) {
            return "";
        }
        StringBuilder output = new StringBuilder(200);
        int current = 0;
        int status = 0;
        block18 : for (int i = 0; i < input.length(); ++i) {
            int c = input.charAt(i) - 32;
            switch (status++) {
                case 0: {
                    current = c << 1;
                    continue block18;
                }
                case 1: {
                    output.append((char)(current | c >> 14));
                    current = (c & 16383) << 2;
                    continue block18;
                }
                case 2: {
                    output.append((char)(current | c >> 13));
                    current = (c & 8191) << 3;
                    continue block18;
                }
                case 3: {
                    output.append((char)(current | c >> 12));
                    current = (c & 4095) << 4;
                    continue block18;
                }
                case 4: {
                    output.append((char)(current | c >> 11));
                    current = (c & 2047) << 5;
                    continue block18;
                }
                case 5: {
                    output.append((char)(current | c >> 10));
                    current = (c & 1023) << 6;
                    continue block18;
                }
                case 6: {
                    output.append((char)(current | c >> 9));
                    current = (c & 511) << 7;
                    continue block18;
                }
                case 7: {
                    output.append((char)(current | c >> 8));
                    current = (c & 255) << 8;
                    continue block18;
                }
                case 8: {
                    output.append((char)(current | c >> 7));
                    current = (c & 127) << 9;
                    continue block18;
                }
                case 9: {
                    output.append((char)(current | c >> 6));
                    current = (c & 63) << 10;
                    continue block18;
                }
                case 10: {
                    output.append((char)(current | c >> 5));
                    current = (c & 31) << 11;
                    continue block18;
                }
                case 11: {
                    output.append((char)(current | c >> 4));
                    current = (c & 15) << 12;
                    continue block18;
                }
                case 12: {
                    output.append((char)(current | c >> 3));
                    current = (c & 7) << 13;
                    continue block18;
                }
                case 13: {
                    output.append((char)(current | c >> 2));
                    current = (c & 3) << 14;
                    continue block18;
                }
                case 14: {
                    output.append((char)(current | c >> 1));
                    current = (c & 1) << 15;
                    continue block18;
                }
                case 15: {
                    output.append((char)(current | c));
                    status = 0;
                }
            }
        }
        return LZString.decompress(output.toString());
    }

    public static String decompressFromBase64(String input) throws Exception {
        return LZString.decompress(LZString.decode64(input));
    }

    public static String decode64(String input) {
        StringBuilder str = new StringBuilder(200);
        int ol = 0;
        int output_ = 0;
        int i = 0;
        while (i < input.length()) {
            int enc1 = keyStr.indexOf(input.charAt(i++));
            int enc2 = keyStr.indexOf(input.charAt(i++));
            int enc3 = keyStr.indexOf(input.charAt(i++));
            int enc4 = keyStr.indexOf(input.charAt(i++));
            int chr1 = enc1 << 2 | enc2 >> 4;
            int chr2 = (enc2 & 15) << 4 | enc3 >> 2;
            int chr3 = (enc3 & 3) << 6 | enc4;
            if (ol % 2 == 0) {
                output_ = chr1 << 8;
                if (enc3 != 64) {
                    str.append((char)(output_ | chr2));
                }
                if (enc4 != 64) {
                    output_ = chr3 << 8;
                }
            } else {
                str.append((char)(output_ | chr1));
                if (enc3 != 64) {
                    output_ = chr2 << 8;
                }
                if (enc4 != 64) {
                    str.append((char)(output_ | chr3));
                }
            }
            ol += 3;
        }
        return str.toString();
    }

    public static String encode64(String input) {
        StringBuilder result = new StringBuilder((input.length() * 8 + 1) / 3);
        int i = 0;
        int max = input.length() << 1;
        while (i < max) {
            int ch1;
            int ch2;
            int left = max - i;
            if (left >= 3) {
                ch1 = input.charAt(i >> 1) >> (1 - (i & 1) << 3) & 255;
                ch2 = input.charAt(i >> 1) >> (1 - (++i & 1) << 3) & 255;
                int ch3 = input.charAt(i >> 1) >> (1 - (++i & 1) << 3) & 255;
                ++i;
                result.append(keyStr.charAt(ch1 >> 2 & 63));
                result.append(keyStr.charAt((ch1 << 4) + (ch2 >> 4) & 63));
                result.append(keyStr.charAt((ch2 << 2) + (ch3 >> 6) & 63));
                result.append(keyStr.charAt(ch3 & 63));
                continue;
            }
            if (left == 2) {
                ch1 = input.charAt(i >> 1) >> (1 - (i & 1) << 3) & 255;
                ch2 = input.charAt(i >> 1) >> (1 - (++i & 1) << 3) & 255;
                ++i;
                result.append(keyStr.charAt(ch1 >> 2 & 63));
                result.append(keyStr.charAt((ch1 << 4) + (ch2 >> 4) & 63));
                result.append(keyStr.charAt(ch2 << 2 & 63));
                result.append('=');
                continue;
            }
            if (left != 1) continue;
            ch1 = input.charAt(i >> 1) >> (1 - (i & 1) << 3) & 255;
            ++i;
            result.append(keyStr.charAt(ch1 >> 2 & 63));
            result.append(keyStr.charAt(ch1 << 4 & 63));
            result.append('=');
            result.append('=');
        }
        return result.toString();
    }

    public static String compressToBase64(String input) {
        return LZString.encode64(LZString.compress(input));
    }

}

