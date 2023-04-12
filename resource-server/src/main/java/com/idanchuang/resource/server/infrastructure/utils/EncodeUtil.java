package com.idanchuang.resource.server.infrastructure.utils;

import java.util.ArrayList;

/**
 * Created by develop at 2021/2/4.
 */
public class EncodeUtil {

    /**
     * Max number that can be encoded with EncodeUtil.
     */
    public static final long MAX_NUMBER = 9007199254740992L;

    private static final String DEFAULT_ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    private static final String DEFAULT_SEPS = "cfhistuCFHISTU";

    private static final int DEFAULT_MIN_HASH_LENGTH = 0;
    private static final double SEP_DIV = 3.5;
    private static final int GUARD_DIV = 12;

    private final String salt;
    private final int minHashLength;
    private final String alphabet;
    private final String seps;
    private final String guards;

    private static EncodeUtil encodeUtil;

    public static EncodeUtil getInstance() {
        if (null == encodeUtil) {
            encodeUtil = new EncodeUtil("abm666*2021");
        }
        return encodeUtil;
    }

    private EncodeUtil(String salt) {
        this(salt, 6, DEFAULT_ALPHABET);
    }

    private EncodeUtil(String salt, int minHashLength, String alphabet) {
        this.salt = salt;
        this.minHashLength = minHashLength > 0 ? minHashLength : DEFAULT_MIN_HASH_LENGTH;
        String initSeps = EncodeUtil.consistentShuffle(DEFAULT_SEPS, this.salt);

        if ((initSeps.isEmpty()) || (((float) alphabet.length() / initSeps.length()) > SEP_DIV)) {
            int sepsLength = (int) Math.ceil(alphabet.length() / SEP_DIV);
            final int diff = sepsLength - initSeps.length();
            initSeps += alphabet.substring(0, diff);
            alphabet = alphabet.substring(diff);
        }

        alphabet = EncodeUtil.consistentShuffle(alphabet, this.salt);
        final int guardCount = (int) Math.ceil((double) alphabet.length() / GUARD_DIV);

        String initGuards = alphabet.substring(0, guardCount);
        alphabet = alphabet.substring(guardCount);

        this.guards = initGuards;
        this.alphabet = alphabet;
        this.seps = initSeps;
    }

    /**
     * Encode numbers to string
     *
     * @param numbers the numbers to encode
     * @return the encoded string
     */
    public String encode(long numbers) {
        if (numbers < 0) {
            return "";
        }
        if (numbers > MAX_NUMBER) {
            throw new IllegalArgumentException("number can not be greater than " + MAX_NUMBER + "L");
        }
        return this._encode(numbers);
    }

    /**
     * Decode string to numbers
     *
     * @param hash the encoded string
     * @return decoded numbers
     */
    public long decode(String hash) {
        if (hash.isEmpty()) {
            return 0;
        }

        String validChars = this.alphabet + this.guards + this.seps;
        for (int i = 0; i < hash.length(); i++) {
            if (validChars.indexOf(hash.charAt(i)) == -1) {
                return 0;
            }
        }

        return this._decode(hash, this.alphabet)[0];
    }

    /* Private methods */

    private String _encode(long... numbers) {
        long numberHashInt = 0;
        for (int i = 0; i < numbers.length; i++) {
            numberHashInt += (numbers[i] % (i + 100));
        }
        String alphabet = this.alphabet;
        final char ret = alphabet.charAt((int) (numberHashInt % alphabet.length()));

        long num;
        long sepsIndex, guardIndex;
        String buffer;
        final StringBuilder ret_strB = new StringBuilder(this.minHashLength);
        ret_strB.append(ret);
        char guard;

        for (int i = 0; i < numbers.length; i++) {
            num = numbers[i];
            buffer = ret + this.salt + alphabet;

            alphabet = EncodeUtil.consistentShuffle(alphabet, buffer.substring(0, alphabet.length()));
            final String last = EncodeUtil.hash(num, alphabet);

            ret_strB.append(last);

            if (i + 1 < numbers.length) {
                if (last.length() > 0) {
                    num %= (last.charAt(0) + i);
                    sepsIndex = (int) (num % this.seps.length());
                } else {
                    sepsIndex = 0;
                }
                ret_strB.append(this.seps.charAt((int) sepsIndex));
            }
        }

        String ret_str = ret_strB.toString();
        if (ret_str.length() < this.minHashLength) {
            guardIndex = (numberHashInt + (ret_str.charAt(0))) % this.guards.length();
            guard = this.guards.charAt((int) guardIndex);

            ret_str = guard + ret_str;

            if (ret_str.length() < this.minHashLength) {
                guardIndex = (numberHashInt + (ret_str.charAt(2))) % this.guards.length();
                guard = this.guards.charAt((int) guardIndex);

                ret_str += guard;
            }
        }

        final int halfLen = alphabet.length() / 2;
        while (ret_str.length() < this.minHashLength) {
            alphabet = EncodeUtil.consistentShuffle(alphabet, alphabet);
            ret_str = alphabet.substring(halfLen) + ret_str + alphabet.substring(0, halfLen);
            final int excess = ret_str.length() - this.minHashLength;
            if (excess > 0) {
                final int start_pos = excess / 2;
                ret_str = ret_str.substring(start_pos, start_pos + this.minHashLength);
            }
        }

        return ret_str;
    }

    private long[] _decode(String hash, String alphabet) {
        final ArrayList<Long> ret = new ArrayList<>();

        int i = 0;
        final String regexp = "[" + this.guards + "]";
        String hashBreakdown = hash.replaceAll(regexp, " ");
        String[] hashArray = hashBreakdown.split(" ");

        if (hashArray.length == 3 || hashArray.length == 2) {
            i = 1;
        }

        if (hashArray.length > 0) {
            hashBreakdown = hashArray[i];
            if (!hashBreakdown.isEmpty()) {
                final char lottery = hashBreakdown.charAt(0);

                hashBreakdown = hashBreakdown.substring(1);
                hashBreakdown = hashBreakdown.replaceAll("[" + this.seps + "]", " ");
                hashArray = hashBreakdown.split(" ");

                String subHash,
                        buffer;
                for (final String aHashArray : hashArray) {
                    subHash = aHashArray;
                    buffer = lottery + this.salt + alphabet;
                    alphabet = EncodeUtil.consistentShuffle(alphabet, buffer.substring(0, alphabet.length()));
                    ret.add(EncodeUtil.unhash(subHash, alphabet));
                }
            }
        }

        // transform from List<Long> to long[]
        long[] arr = new long[ret.size()];
        for (int k = 0; k < arr.length; k++) {
            arr[k] = ret.get(k);
        }

        if (!this.encode(arr[0]).equals(hash)) {
            arr = new long[0];
        }

        return arr;
    }

    private static String consistentShuffle(String alphabet, String salt) {
        if (salt.length() <= 0) {
            return alphabet;
        }

        int asc_val, j;
        final char[] tmpArr = alphabet.toCharArray();
        for (int i = tmpArr.length - 1, v = 0, p = 0; i > 0; i--, v++) {
            v %= salt.length();
            asc_val = salt.charAt(v);
            p += asc_val;
            j = (asc_val + v + p) % i;
            final char tmp = tmpArr[j];
            tmpArr[j] = tmpArr[i];
            tmpArr[i] = tmp;
        }

        return new String(tmpArr);
    }

    private static String hash(long input, String alphabet) {
        String hash = "";
        final int alphabetLen = alphabet.length();

        do {
            final int index = (int) (input % alphabetLen);
            if (index >= 0 && index < alphabet.length()) {
                hash = alphabet.charAt(index) + hash;
            }
            input /= alphabetLen;
        } while (input > 0);

        return hash;
    }

    private static Long unhash(String input, String alphabet) {
        long number = 0, pos;

        for (int i = 0; i < input.length(); i++) {
            pos = alphabet.indexOf(input.charAt(i));
            number = number * alphabet.length() + pos;
        }

        return number;
    }
}
