package co.saltpay.pax.models.receipt.template;

import android.content.Context;

import com.pax.gl.page.IPage;

public interface PrintableUnit {

    void appendTo(Context context, IPage page, IPage.ILine line);

}
