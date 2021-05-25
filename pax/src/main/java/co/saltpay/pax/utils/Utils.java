package co.saltpay.pax.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class Utils extends Constants {

    //TODO tune this
    public static final int UPDATE_DELAY = 2000;
    private static final char[] hexArray = "0123456789ABCDEF".toCharArray();
    private static final AtomicLong LAST_TIME_MS = new AtomicLong();

    public static String toHexString(byte[] bytes) {
        if (null == bytes) return null;
        return toHexString(bytes, 0, bytes.length);
    }

    public static String toHexString(byte[] bytes, int start, int len) {
        if (null == bytes) return null;
        char[] hexChars = new char[len * 2];
        for (int j = start; j < start + len; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static String checkNull(String data) {
        if (data == null || (data.trim()).length() == 0) {
            return "";
        }
        return data;
    }

    public static boolean isEmptyString(CharSequence str) {
        return str == null || (str.toString().trim()).length() == 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String bytes2String(byte[] data) {
        String result = "";
        if (data.length > 0)
            result = new String(data, StandardCharsets.UTF_8);
        return result;
    }

    public static String generateNonce() {
        long now = System.currentTimeMillis();
        while (true) {
            long lastTime = LAST_TIME_MS.get();
            if (lastTime >= now) {
                now = lastTime + 1;
            }
            if (LAST_TIME_MS.compareAndSet(lastTime, now)) {
                return now + "";
            }
        }
    }

    public static synchronized String getCurrentDateAndTimeStamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return sdf.format(new Date());
    }


    public static boolean isDeviceAndroidTelpo() {
        return Build.MODEL.equals(TELPO_TPS900);
    }


    public static String convertBitMapToBase64String(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    public static Bitmap convertStringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        } catch (Exception e) {
            Log.e("Printer", "Exception inside convertStringToBitMap : " + e.getMessage());
            return null;
        }
    }

    public static String encodeToCompressedJPEGBase64(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 5, baos);
        byte[] b = baos.toByteArray();

        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    public static String getValidDate(String date) {
        return date.substring(6, 8) + "." + date.substring(4, 6) + "." + date.substring(0, 4);
    }

    public static String getValidTime(String date) {
        return date.substring(8, 10) + ":" + date.substring(10, 12);
    }

    public static Date getValidDatetime(String date) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, Integer.valueOf(date.substring(0, 4)));
        cal.set(Calendar.MONTH, Integer.valueOf(date.substring(4, 6)) - 1);
        cal.set(Calendar.DAY_OF_MONTH, Integer.valueOf(date.substring(6, 8)));
        cal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(date.substring(8, 10)));
        cal.set(Calendar.MINUTE, Integer.valueOf(date.substring(10, 12)));
        cal.set(Calendar.SECOND, Integer.valueOf(date.substring(12, 14)));
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }


    public static String padLeftZeros(String value, int length) {
        return String.format("%" + length + "s", value.trim()).replace(' ', '0');
    }



    public static String cleanCDataString(String str) {
        return str
                .replaceAll("\\r", "")
                .replaceAll("\\f", "")
                .replaceAll("\\b", "")
                .replaceAll("\\n", "")
                .replaceAll("\\t", "")
                .replaceAll("> +<", "><");
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static long formatAmount(String amount) {
        if (Util.isBlank(amount)) return 0;
        return Long.parseLong(amount.replace(",", "").replace(".", ""));
    }


    public static boolean isNotBlank(byte[] data) {
        return data != null;
    }

    public static boolean isEmptyList(List<?> list) {
        return list == null || list.isEmpty();
    }

    public static boolean isNotEmptyList(List<?> list) {
        return !isEmptyList(list);
    }

}
