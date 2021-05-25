package co.saltpay.pax.models.receipt.template;

import android.content.Context;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.pax.gl.page.IPage;

import java.util.List;

@JacksonXmlRootElement(localName = "footer")
public class Footer implements Printable {

    @JacksonXmlProperty(localName = "style", isAttribute = true)
    private String style;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "div")
    private List<Div> divs;

    public List<Div> getDivs() {
        return divs;
    }

    public void setDivs(List<Div> divs) {
        this.divs = divs;
    }

    @Override
    public void appendTo(Context context, IPage page) {
        for (Div div : divs) {
            div.appendTo(context, page);
        }
    }

}
