package co.saltpay.pax.models.receipt.template;

import android.content.Context;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.pax.gl.page.IPage;

import java.util.List;

@JacksonXmlRootElement(localName = "header")
public class Header implements Printable {

    @JacksonXmlProperty(localName = "style", isAttribute = true)
    private String style;

    @JacksonXmlProperty(localName = "img")
    private Img img;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "div")
    private List<Div> divs;

    public List<Div> getDivs() {
        return divs;
    }

    public void setDivs(List<Div> divs) {
        this.divs = divs;
    }

    @JsonIgnore
    public Img getLogo() {
        return img;
    }

    public void setLogo(Img img) {
        this.img = img;
    }

    @Override
    public void appendTo(Context context, IPage page) {
        // Print logo
        if (img != null) {
            img.appendTo(context, page);
        }
        for (Div div : divs) {
            div.appendTo(context, page);
        }
    }
}
