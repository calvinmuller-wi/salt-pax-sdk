package co.saltpay.pax.models.receipt.template;

import android.content.Context;

import com.pax.gl.page.IPage;

public interface Printable {

    void appendTo(Context context, IPage page);

}
