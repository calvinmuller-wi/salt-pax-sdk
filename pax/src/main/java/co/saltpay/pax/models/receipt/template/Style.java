package co.saltpay.pax.models.receipt.template;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

public class Style {

    @JacksonXmlProperty(localName = "type", isAttribute = true)
    private String type;

    @JacksonXmlText
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
