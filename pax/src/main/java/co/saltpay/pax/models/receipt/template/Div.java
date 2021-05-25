package co.saltpay.pax.models.receipt.template;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.pax.gl.page.IPage;

import java.io.IOException;
import java.io.InputStream;

@JacksonXmlRootElement(localName = "div")
public class Div extends Element implements Printable {

    private static final String SOLID_SEPARATOR = "images/solid.png";
    private static final String DOTTED_SEPARATOR = "dotted-separator.png";
    private static final String EMPTY_SEPARATOR = "images/empty.png";

    @JacksonXmlProperty(localName = "label")
    private Label label;

    @JacksonXmlProperty(localName = "p")
    private Paragraph p;

    @JacksonXmlProperty(localName = "span")
    private Span span;

    @JacksonXmlProperty(localName = "img")
    private Img img;

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public Paragraph getP() {
        return p;
    }

    public void setP(Paragraph p) {
        this.p = p;
    }

    public Span getSpan() {
        return span;
    }

    public void setSpan(Span span) {
        this.span = span;
    }

    public Img getImg() {
        return img;
    }

    public void setImg(Img img) {
        this.img = img;
    }

    @Override
    public void appendTo(Context context, IPage page) {
        IPage.ILine line = null;

        // Two units (label & span)
        if (label != null && span != null) {
            line = page.addLine();
            label.appendTo(context, page, line);
            span.appendTo(context, page, line);
        }
        // One unit (p)
        else if (p != null) {
            line = page.addLine();
            p.appendTo(context, page, line);
        }
        else if (span != null) {
            line = page.addLine();
            span.appendTo(context, page, line);
        }
        // Separator
        else if (isSeparator()) {
            if (isDottedSeparator()) {
                try {
                    addBitmapSeparator(context, page, DOTTED_SEPARATOR);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (isSolidSeparator()) {
                try {
                    addBitmapSeparator(context, page, SOLID_SEPARATOR);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    addBitmapSeparator(context, page, EMPTY_SEPARATOR);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (img != null) {
            img.appendTo(context, page);
        }
    }

    @JsonIgnore
    public int getCssPropertyInt(String property) {
        String heightStr = getCssPropertyValue(property);
        int height = (heightStr != null) ? Integer.parseInt(heightStr.replaceAll("[^\\d.]", "")) : 0;
        return height;
    }

    private void addBitmapSeparator(Context context, IPage page, String type) throws IOException {

        Bitmap emptyBitmap = loadBitmap(context, EMPTY_SEPARATOR);
        Bitmap bitmap = loadBitmap(context, type);

        int marginTop = getCssPropertyInt("margin-top");
        int height = getCssPropertyInt("height");
        int marginBottom = getCssPropertyInt("margin-top");

        // Padding top
        addBitmap(page, emptyBitmap, marginTop);
        addBitmap(page, bitmap, height == 0 ? 1 : height);
        addBitmap(page, emptyBitmap, marginBottom);

    }

    private Bitmap loadBitmap(Context context, String file) throws IOException {
        AssetManager assetManager = context.getAssets();

        InputStream istr = null;
        Bitmap bitmap = null;
        try {
            istr = assetManager.openFd(file).createInputStream();
            bitmap = BitmapFactory.decodeStream(istr);
        } catch (IOException e) {
            e.printStackTrace();
            // handle exception
        }
        return bitmap;
    }

    private void addBitmap(IPage page, Bitmap bitmap, int n) {
        if (bitmap != null && page != null) {
            for (int i = 0; i < n; i++) {
                page.addLine().addUnit(bitmap);
            }
        }
    }
}
