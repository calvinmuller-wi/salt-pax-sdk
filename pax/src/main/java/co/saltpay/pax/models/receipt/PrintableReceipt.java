package co.saltpay.pax.models.receipt;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;

import com.pax.gl.page.IPage;
import com.pax.gl.page.PaxGLPage;

import co.saltpay.pax.models.receipt.template.HtmlReceiptTemplate;
import co.saltpay.pax.utils.ConverterUtil;
import co.saltpay.pax.utils.Utils;

public class PrintableReceipt {

    private static final String TYPE_FACE_EXTENSION = ".ttf";
    private static final int RECEIPT_WIDTH = 384;

    protected String htmlReceiptStr;
    protected HtmlReceiptTemplate htmlReceipt;

    protected PaxGLPage iPaxGLPage;
    protected Context context;
    protected Bitmap bitmap;

    protected PrintableReceipt() {

    }

    protected PrintableReceipt(Context context) {
        iPaxGLPage = PaxGLPage.getInstance(context);
        this.context = context;
    }

    public PrintableReceipt(String htmlReceiptStr, Context context) throws InstantiationException {
        this(context);
        loadReceiptFromString(htmlReceiptStr);
    }

    protected void loadReceiptFromString(String htmlReceiptStr) throws InstantiationException {
        this.htmlReceiptStr = htmlReceiptStr;
        if (this.htmlReceiptStr != null) {
            htmlReceipt = ConverterUtil.getModelObjFromXml(Utils.cleanCDataString(htmlReceiptStr), HtmlReceiptTemplate.class);
        } else {
            throw new InstantiationException("Receipt can not be null");
        }
    }

    public Bitmap toBitMap() {
        if (bitmap == null) {
            if (htmlReceipt != null && iPaxGLPage != null) {
                IPage page = iPaxGLPage.createPage();
                htmlReceipt.appendTo(context, page);
                bitmap = iPaxGLPage.pageToBitmap(page, RECEIPT_WIDTH);
            } else if (htmlReceipt == null) {
                throw new IllegalStateException("There was an error parsing the receipt");
            } else if (iPaxGLPage == null) { //NOSONAR wanted check
                throw new IllegalStateException("Receipt must be instantiated with Android context in order to get BitMap");
            }
        }
        return bitmap;
    }

    public String toString() {
        return htmlReceiptStr;
    }
}

