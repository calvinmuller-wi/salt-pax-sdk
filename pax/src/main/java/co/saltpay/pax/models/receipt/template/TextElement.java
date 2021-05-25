package co.saltpay.pax.models.receipt.template;

import android.content.Context;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
import com.pax.gl.page.IPage;

public abstract class TextElement extends Element implements PrintableUnit {

    @JacksonXmlText
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public void appendTo(Context context, IPage page, IPage.ILine line) {
        IPage.ILine.IUnit unit = page.createUnit();
        unit.setText(getValue());
        unit.setFontSize(getFontSize().getFontSize());
        unit.setTextStyle(getFontStyle().getFontStyle());
        unit.setAlign(getTextAlign());
        line.addUnit(unit);
    }
}
