package co.saltpay.pax.models.receipt.template;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Base64;

import androidx.annotation.RequiresApi;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.pax.gl.page.IPage;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Img extends Element implements Printable {

    @JacksonXmlProperty(localName = "src", isAttribute = true)
    private String src;

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    @Override
    public void appendTo(Context context, IPage page) {
        Bitmap bm = this.toBitmap();
        if (bm != null) {
            page.addLine().addUnit(this.toBitmap(), getTextAlign());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public Bitmap toBitmap() {
        String base64Img = getBase64Img();

        // Base64 Img src
        if (base64Img != null) {
            byte[] decodedString = Base64.decode(base64Img.trim(), Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        }
        // URL
        else {
            try {
                return Picasso.get().load(getSrc()).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

//        If all fails return an empty bitmap
        Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
        return Bitmap.createBitmap(1, 1, conf);
    }

    private String getBase64Img() {
        Pattern p = Pattern.compile("^data:image.*base64,\\s*(.*)");
        Matcher m = p.matcher(getSrc());

        if (m.matches()) {
            return m.group(1).trim();
        } else {
            return null;
        }
    }

}
