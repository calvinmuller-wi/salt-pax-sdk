package co.saltpay.pax.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.IOException;
import java.nio.ByteOrder;

public class ConverterUtil {

    private static final char[] ARRAY_OF_CHAR = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    private static int strByte2Int(byte b) {
        int j;
        if ((b >= 'a') && (b <= 'z')) {
            j = b - 'a' + 0x0A;
        } else {
            if ((b >= 'A') && (b <= 'Z'))
                j = b - 'A' + 0x0A;
            else
                j = b - '0';
        }
        return j;
    }

    private static XmlMapper getXmlMapper() {
        JacksonXmlModule xmlModule = new JacksonXmlModule();
        xmlModule.setDefaultUseWrapper(false);
        xmlModule.addDeserializer(String.class, new StdDeserializer<String>(String.class) {
            @Override
            public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
                String result = StringDeserializer.instance.deserialize(p, ctxt);
                if (result.isEmpty()) {
                    return null;
                }
                return result;
            }
        });

        XmlMapper objectMapper = new XmlMapper(xmlModule);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);

        return objectMapper;
    }

    public static <T> T getModelObjFromXml(String requestObj, Class<T> tClass) {
        T type = null;

        XmlMapper objectMapper = getXmlMapper();

        try {
            type = objectMapper.readValue(requestObj, tClass);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return type;
    }

    public static String convertToJSON(Object data) {
        return convertToJSON(data, false);
    }

    public static String convertToJSON(Object data, boolean pretty) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            if (pretty) {
                return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
            } else {
                return mapper.writeValueAsString(data);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static <T> T getModelObjectFromJSON(String requestObj, Class<T> tClass) {
        ObjectMapper mapper = new ObjectMapper();

        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        //mapper.configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, false);

        try {
            return mapper.readValue(requestObj, tClass);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String bcdToStr(byte[] b) {
        if (b == null) {
            throw new IllegalArgumentException("bcdToStr input arg is null");
        }

        StringBuilder localStringBuilder = new StringBuilder(b.length * 2);
        for (byte i : b) {
            localStringBuilder.append(ARRAY_OF_CHAR[((i & 0xF0) >>> 4)]);
            localStringBuilder.append(ARRAY_OF_CHAR[(i & 0xF)]);
        }

        return localStringBuilder.toString();
    }

    public byte[] strToBcd(String str, EPaddingPosition paddingPosition) {
        if ((str == null) || (paddingPosition == null)) {
            throw new IllegalArgumentException("strToBcd input arg is null");
        }
        String s = str;
        int len = s.length();
        if (len % 2 != 0) {
            if (paddingPosition == EPaddingPosition.PADDING_RIGHT)
                s = s + "0";
            else {
                s = "0" + s;
            }
            len = s.length();
        }
        if (len >= 2) {
            len /= 2;
        }
        byte[] bcd = new byte[len];
        byte[] strBytes = s.getBytes();

        for (int p = 0; p < strBytes.length / 2; p++) {
            bcd[p] = (byte) ((strByte2Int(strBytes[(2 * p)]) << 4) + strByte2Int(strBytes[(2 * p + 1)]));
        }

        return bcd;
    }

    public void longToByteArray(long l, byte[] to, int offset, ByteOrder endian) {
        if ((to == null) || (endian == null)) {
            throw new IllegalArgumentException("longToByteArray input arg is null");
        }

        if (endian == ByteOrder.BIG_ENDIAN) {
            to[offset] = (byte) (int) (l >>> 56 & 0xFF);
            to[(offset + 1)] = (byte) (int) (l >>> 48 & 0xFF);
            to[(offset + 2)] = (byte) (int) (l >>> 40 & 0xFF);
            to[(offset + 3)] = (byte) (int) (l >>> 32 & 0xFF);
            to[(offset + 4)] = (byte) (int) (l >>> 24 & 0xFF);
            to[(offset + 5)] = (byte) (int) (l >>> 16 & 0xFF);
            to[(offset + 6)] = (byte) (int) (l >>> 8 & 0xFF);
            to[(offset + 7)] = (byte) (int) (l & 0xFF);
            return;
        }
        to[(offset + 7)] = (byte) (int) (l >>> 56 & 0xFF);
        to[(offset + 6)] = (byte) (int) (l >>> 48 & 0xFF);
        to[(offset + 5)] = (byte) (int) (l >>> 40 & 0xFF);
        to[(offset + 4)] = (byte) (int) (l >>> 32 & 0xFF);
        to[(offset + 3)] = (byte) (int) (l >>> 24 & 0xFF);
        to[(offset + 2)] = (byte) (int) (l >>> 16 & 0xFF);
        to[(offset + 1)] = (byte) (int) (l >>> 8 & 0xFF);
        to[offset] = (byte) (int) (l & 0xFF);
    }

    public byte[] longToByteArray(long l, ByteOrder endian) {
        if (endian == null) {
            throw new IllegalArgumentException("longToByteArray input arg is null");
        }

        byte[] arrayOfByte = new byte[8];

        if (endian == ByteOrder.BIG_ENDIAN) {
            arrayOfByte[0] = (byte) (int) (l >>> 56 & 0xFF);
            arrayOfByte[1] = (byte) (int) (l >>> 48 & 0xFF);
            arrayOfByte[2] = (byte) (int) (l >>> 40 & 0xFF);
            arrayOfByte[3] = (byte) (int) (l >>> 32 & 0xFF);
            arrayOfByte[4] = (byte) (int) (l >>> 24 & 0xFF);
            arrayOfByte[5] = (byte) (int) (l >>> 16 & 0xFF);
            arrayOfByte[6] = (byte) (int) (l >>> 8 & 0xFF);
            arrayOfByte[7] = (byte) (int) (l & 0xFF);
        } else {
            arrayOfByte[7] = (byte) (int) (l >>> 56 & 0xFF);
            arrayOfByte[6] = (byte) (int) (l >>> 48 & 0xFF);
            arrayOfByte[5] = (byte) (int) (l >>> 40 & 0xFF);
            arrayOfByte[4] = (byte) (int) (l >>> 32 & 0xFF);
            arrayOfByte[3] = (byte) (int) (l >>> 24 & 0xFF);
            arrayOfByte[2] = (byte) (int) (l >>> 16 & 0xFF);
            arrayOfByte[1] = (byte) (int) (l >>> 8 & 0xFF);
            arrayOfByte[0] = (byte) (int) (l & 0xFF);
        }

        return arrayOfByte;
    }

    public void intToByteArray(int paramInt1, byte[] paramArrayOfByte, int paramInt2, ByteOrder paramEEndian) {
        if ((paramArrayOfByte == null) || (paramEEndian == null)) {
            throw new IllegalArgumentException("longToByteArray input arg is null");
        }

        if (paramEEndian == ByteOrder.BIG_ENDIAN) {
            paramArrayOfByte[paramInt2] = (byte) (paramInt1 >>> 24 & 0xFF);
            paramArrayOfByte[(paramInt2 + 1)] = (byte) (paramInt1 >>> 16 & 0xFF);
            paramArrayOfByte[(paramInt2 + 2)] = (byte) (paramInt1 >>> 8 & 0xFF);
            paramArrayOfByte[(paramInt2 + 3)] = (byte) (paramInt1 & 0xFF);
            return;
        }
        paramArrayOfByte[paramInt2] = (byte) (paramInt1 & 0xFF);
        paramArrayOfByte[(paramInt2 + 1)] = (byte) (paramInt1 >>> 8 & 0xFF);
        paramArrayOfByte[(paramInt2 + 2)] = (byte) (paramInt1 >>> 16 & 0xFF);
        paramArrayOfByte[(paramInt2 + 3)] = (byte) (paramInt1 >>> 24 & 0xFF);
    }

    public byte[] intToByteArray(int paramInt, ByteOrder paramEEndian) {
        if (paramEEndian == null) {
            throw new IllegalArgumentException("intToByteArray input arg is null");
        }

        byte[] arrayOfByte = new byte[4];

        if (paramEEndian == ByteOrder.BIG_ENDIAN) {
            arrayOfByte[0] = (byte) (paramInt >>> 24 & 0xFF);
            arrayOfByte[1] = (byte) (paramInt >>> 16 & 0xFF);
            arrayOfByte[2] = (byte) (paramInt >>> 8 & 0xFF);
            arrayOfByte[3] = (byte) (paramInt & 0xFF);
        } else {
            arrayOfByte[0] = (byte) (paramInt & 0xFF);
            arrayOfByte[1] = (byte) (paramInt >>> 8 & 0xFF);
            arrayOfByte[2] = (byte) (paramInt >>> 16 & 0xFF);
            arrayOfByte[3] = (byte) (paramInt >>> 24 & 0xFF);
        }

        return arrayOfByte;
    }

    public void shortToByteArray(short paramShort, byte[] paramArrayOfByte, int paramInt, ByteOrder paramEEndian) {
        if ((paramArrayOfByte == null) || (paramEEndian == null)) {
            throw new IllegalArgumentException("shortToByteArray input arg is null");
        }

        if (paramEEndian == ByteOrder.BIG_ENDIAN) {
            paramArrayOfByte[paramInt] = (byte) (paramShort >>> 8 & 0xFF);
            paramArrayOfByte[(paramInt + 1)] = (byte) (paramShort & 0xFF);
            return;
        }
        paramArrayOfByte[paramInt] = (byte) (paramShort & 0xFF);
        paramArrayOfByte[(paramInt + 1)] = (byte) (paramShort >>> 8 & 0xFF);
    }

    public byte[] shortToByteArray(short paramShort, ByteOrder paramEEndian) {
        if (paramEEndian == null) {
            throw new IllegalArgumentException("shortToByteArray input arg is null");
        }

        byte[] arrayOfByte = new byte[2];

        if (paramEEndian == ByteOrder.BIG_ENDIAN) {
            arrayOfByte[0] = (byte) (paramShort >>> 8 & 0xFF);
            arrayOfByte[1] = (byte) (paramShort & 0xFF);
        } else {
            arrayOfByte[0] = (byte) (paramShort & 0xFF);
            arrayOfByte[1] = (byte) (paramShort >>> 8 & 0xFF);
        }

        return arrayOfByte;
    }

    public long longFromByteArray(byte[] paramArrayOfByte, int paramInt, ByteOrder paramEEndian) {
        if ((paramArrayOfByte == null) || (paramEEndian == null)) {
            throw new IllegalArgumentException("longFromByteArray input arg is null");
        }

        if (paramEEndian == ByteOrder.BIG_ENDIAN) {
            return paramArrayOfByte[(paramInt + 4)] << 24 & 0xFF000000 | paramArrayOfByte[(paramInt + 5)] << 16 & 0xFF0000 |
                    paramArrayOfByte[(paramInt + 6)] << 8 & 0xFF00 | paramArrayOfByte[(paramInt + 7)] & 0xFF;
        }
        return paramArrayOfByte[(paramInt + 3)] << 24 & 0xFF000000 | paramArrayOfByte[(paramInt + 2)] << 16 & 0xFF0000 |
                paramArrayOfByte[(paramInt + 1)] << 8 & 0xFF00 | paramArrayOfByte[paramInt] & 0xFF;
    }

    public int intFromByteArray(byte[] paramArrayOfByte, int paramInt, ByteOrder paramEEndian) {
        if ((paramArrayOfByte == null) || (paramEEndian == null)) {
            throw new IllegalArgumentException("intFromByteArray input arg is null");
        }

        if (paramEEndian == ByteOrder.BIG_ENDIAN) {
            return paramArrayOfByte[paramInt] << 24 & 0xFF000000 | paramArrayOfByte[(paramInt + 1)] << 16 & 0xFF0000 |
                    paramArrayOfByte[(paramInt + 2)] << 8 & 0xFF00 | paramArrayOfByte[(paramInt + 3)] & 0xFF;
        }
        return paramArrayOfByte[(paramInt + 3)] << 24 & 0xFF000000 | paramArrayOfByte[(paramInt + 2)] << 16 & 0xFF0000 |
                paramArrayOfByte[(paramInt + 1)] << 8 & 0xFF00 | paramArrayOfByte[paramInt] & 0xFF;
    }

    public short shortFromByteArray(byte[] paramArrayOfByte, int paramInt, ByteOrder paramEEndian) {
        if ((paramArrayOfByte == null) || (paramEEndian == null)) {
            throw new IllegalArgumentException("shortFromByteArray input arg is null");
        }

        if (paramEEndian == ByteOrder.BIG_ENDIAN) {
            return (short) (paramArrayOfByte[paramInt] << 8 & 0xFF00 | paramArrayOfByte[(paramInt + 1)] & 0xFF);
        }
        return (short) (paramArrayOfByte[(paramInt + 1)] << 8 & 0xFF00 | paramArrayOfByte[paramInt] & 0xFF);
    }

    public String stringPadding(String paramString, char paramChar, long paramLong, EPaddingPosition paramEPaddingPosition) {
        if ((paramString == null) || (paramEPaddingPosition == null)) {
            throw new IllegalArgumentException("stringPadding input arg is null");
        }

        if (paramString.length() >= paramLong) {
            return paramString;
        }

        if (paramEPaddingPosition == EPaddingPosition.PADDING_RIGHT) {
            StringBuilder sb = new StringBuilder(paramString);
            for (int i = 0; i < paramLong - paramString.length(); i++) {
                sb.append(paramChar);
            }

            return sb.toString();
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < paramLong - paramString.length(); i++) {
            sb.append(paramChar);
        }

        sb.append(paramString);
        return sb.toString();
    }

    public boolean isByteArrayValueSame(byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2, int paramInt3) {
        if ((paramArrayOfByte1 == null) || (paramArrayOfByte2 == null)) {
            return false;
        }

        if ((paramInt1 + paramInt3 > paramArrayOfByte1.length) || (paramInt2 + paramInt3 > paramArrayOfByte2.length)) {
            return false;
        }

        for (int i = 0; i < paramInt3; i++) {
            if (paramArrayOfByte1[(paramInt1 + i)] != paramArrayOfByte2[(paramInt2 + i)]) {
                return false;
            }
        }

        return true;
    }

    public enum EPaddingPosition {
        PADDING_LEFT,
        PADDING_RIGHT,
    }
}