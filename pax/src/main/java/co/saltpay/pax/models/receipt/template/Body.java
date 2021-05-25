package co.saltpay.pax.models.receipt.template;

import android.content.Context;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.pax.gl.page.IPage;

@JacksonXmlRootElement(localName = "body")
public class Body implements Printable {

    @JacksonXmlProperty(localName = "header")
    private Header header;
    @JacksonXmlProperty(localName = "main")
    private Main main;
    @JacksonXmlProperty(localName = "footer")
    private Footer footer;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Footer getFooter() {
        return footer;
    }

    public void setFooter(Footer footer) {
        this.footer = footer;
    }

    @Override
    public void appendTo(Context context, IPage page) {

        if (header != null) {
            header.appendTo(context, page);
        }

        if (main != null) {
            main.appendTo(context, page);
        }

        if (footer != null) {
            footer.appendTo(context, page);
        }

    }

}
