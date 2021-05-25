package co.saltpay.pax.models.receipt.template;

import android.content.Context;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.pax.gl.page.IPage;

@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "html")
public class HtmlReceiptTemplate extends Element implements Printable {

    @JacksonXmlProperty(localName = "body")
    private Body body;

    @JacksonXmlProperty(localName = "head")
    private Head head;

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public Head getHead() {
        return head;
    }

    public void setHead(Head head) {
        this.head = head;
    }

    @Override
    public void appendTo(Context context, IPage page) {
        if (body != null) {
            body.appendTo(context, page);
        }
    }

}

