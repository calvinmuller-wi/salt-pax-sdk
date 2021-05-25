package co.saltpay.pax.utils;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//import co.saltpay.pax_printer.shared.TerminalType;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class Util {

    public static final String DEFAULT_DATA_KEY = "data";
    private static final Pattern URL_PATTERN = Pattern.compile("^(https?)://.*");
    public static String DATECS_BLUETOOTH_MAC_START = "68:AA:D2";
    public static byte[] empty = new byte[0];
    private static Charset encoding = StandardCharsets.UTF_8;

    public static boolean isNotBlank(String str) {
        // Stolen from Apache StringUtils
        // Checks if a CharSequence is not empty (""), not null and not whitespace only.
        return !Util.isBlank(str);
    }

    public static int asciiBytesToInt(byte[] dataBytes) {
        int ret = 0;
        for (byte c : dataBytes) {
            if (c >= '0' && c <= '9') {
                ret = ret * 16;
                ret += (c - '0');
            } else if (c >= 'A' && c <= 'F') {
                ret = ret * 16;
                ret += ((c - 'A') + 10);
            } else if (c >= 'a' && c <= 'f') {
                ret = ret * 16;
                ret += ((c - 'a') + 10);
            }
        }
        return ret;
    }

    public static boolean isBlank(String str) {
        int strLen = (str != null) ? str.length() : 0;
        if (str == null || (strLen == 0)) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((!Character.isWhitespace(str.charAt(i)))) {
                return false;
            }
        }
        return true;
    }

    public static int binaryToInt(byte[] number) {
        if (number.length > 4) {
            throw new IllegalArgumentException("Invalid byte[] size to convert to int");
        }
        int value = 0;
        for (byte aNumber : number) {
            value = value << 8; // value = value * 256;
            value = (aNumber & 0xff) + value;
        }
        return value;
    }

    public static byte[] intToBinary(int number, int length) {
        if (length > 4) {
            throw new IllegalArgumentException("Invalid length size to convert to byte[]");
        }
        byte[] bytes = new byte[length];
        for (int i = 0; i < bytes.length; i++) {
            if (i == bytes.length - 1) {
                bytes[i] = (byte) (number);
            } else {
                bytes[i] = (byte) (number >> (8 * (bytes.length - i - 1)) & 0xFF);
            }
        }
        return bytes;
    }

    public static byte[] longToBcd(long number, int byteCount) {
        byte[] bytes = new byte[byteCount];

        byteCount *= 2;
        while (byteCount-- != 0) {
            byte digit = (byte) (number % 10);
            number /= 10;
            if (byteCount % 2 != 0) {
                bytes[byteCount / 2] = digit;
            } else {
                bytes[byteCount / 2] = (byte) ((digit << 4) | bytes[byteCount / 2]);
            }
        }

        if (number != 0) // we were unable to finish converting the number into the designated byte array.
        {
            throw new IllegalArgumentException("Unable to parse a long number to an byte[]");
        }

        return bytes;
    }

    public static long bcdToLong(byte[] number) {
        return Long.parseLong(BCDtoString(number));
    }

    private static String BCDtoString(byte bcd) {
        StringBuilder sb = new StringBuilder();

        byte high = (byte) (bcd & 0xf0);
        high >>>= (byte) 4;
        high = (byte) (high & 0x0f);
        byte low = (byte) (bcd & 0x0f);

        sb.append(high);
        sb.append(low);

        return sb.toString();
    }

    private static String BCDtoString(byte[] bcd) {

        StringBuilder sb = new StringBuilder();

        for (byte b : bcd) {
            sb.append(BCDtoString(b));
        }

        return sb.toString();
    }

    public static String bytesToHexString(byte[] data) {
        return bytesToHexString(data, 0, data.length);
    }

    public static String bytesToHexString(byte[] data, int offset, int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = offset; i < length; i++) {
            char[] current = byteToHex(data[i]);
            sb.append(current[0]);
            sb.append(current[1]);
        }
        return sb.toString().toUpperCase();
    }

    private static char[] byteToHex(byte data) {
        boolean lowercase = true;
        char[] result = new char[2];
        result[0] = nibbleToHex((data >>> 4) & 0x0f, lowercase);
        result[1] = nibbleToHex(data & 0x0f, lowercase);
        return result;
    }

    private static char nibbleToHex(int i, boolean lowcase) {
        return (char) (i += (i < 0xa) ? '0' : lowcase ? 'a' - 0xa : 'A' - 0xa);
    }

    public static byte[] hexToBytes(String hex) {
        if (hex == null) {
            return empty;
        }
        byte[] result = new byte[hex.length() / 2];
        for (int i = 0, j = 0; i < result.length; i++) {
            int k = hexToNibble(hex.charAt(j++)) << 4;
            k |= hexToNibble(hex.charAt(j++));
            result[i] = (byte) k;
        }
        return result;
    }

    private static int hexToNibble(char c) {
        int current = c;
        current -=
                (current < 'A') ? '0' :
                        (current < 'a') ? 'A' - 0xa :
                                'a' - 0xa;
        return current;
    }

    /**
     * Helper method to read from a stream
     *
     * @param stream the stream to read from
     * @param len    how many bytes to read
     * @return a new byte[] containing the read values
     */
    public static byte[] readBytes(ByteArrayInputStream stream, int len) {
        if (len == 0) {
            return empty;
        }
        byte[] res = new byte[len];
        int read = stream.read(res, 0, len);
        if (read == len) {
            return res;
        } else {
            return empty;
        }
    }

    public static byte[] readBytes(byte[] arr, int len) {
        if (len == 0) {
            return empty;
        }
        byte[] res = new byte[len];
        System.arraycopy(arr, 0, res, 0, len);
        return res;
    }

    public static String getSendableCardPresent(String str) {
        if (str == null) {
            return "1";
        }
        str = str.toLowerCase();
        String ret = "0";
        if ((Util.isBlank(str)) || str.equals("1") || str.equals("yes") || str.equals("true")) {
            ret = "1";
        }

        return ret;
    }

    public static Map simpleDataMap(Object data) {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put(DEFAULT_DATA_KEY, data);
        return dataMap;
    }

    public static String getFormattedNowDate() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }

    public static byte[] getByteArrayDateForInit() {
        String date = getFormattedNowDate();
        return Util.longToBcd(Long.parseLong(date), 7);
    }

    public static Map<String, String> mapWithMap(Map<String, String> map) {
        Map<String, String> newMap = new HashMap<>();

        return mapWithMaps(newMap, map);
    }

    public static Map<String, String> mapWithMaps(Map<String, String>... maps) {
        Map<String, String> newMap = new HashMap<>();

        for (Map<String, String> map : maps) {
            if (map != null) {
                newMap.putAll(map);
            }
        }

        return newMap;
    }

    public static boolean stringFlagToBoolean(String flag) {
        if (flag == null) {
            return false;
        }

        switch (flag) {
            case "1":
                return true;
            case "0":
            default:
                return false;
        }
    }

    public static boolean charFlagToBoolean(char flag) {
        return stringFlagToBoolean(String.valueOf(flag));
    }

//    public static TerminalType getDatecsModelFromSerialNumber(String serial) {
//        return new DatecsTerminalTypeCalculator().calculateFromSN(serial);
//    }

    public static boolean isHttpURL(String str) {
        if (str != null) {
            Matcher m = URL_PATTERN.matcher(str);
            return m.matches();
        } else {
            return false;
        }
    }

}
