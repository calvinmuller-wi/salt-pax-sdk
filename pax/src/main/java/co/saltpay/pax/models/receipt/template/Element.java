package co.saltpay.pax.models.receipt.template;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.pax.gl.page.IPage;

import co.saltpay.pax.models.receipt.FontSize;
import co.saltpay.pax.models.receipt.FontStyle;

public abstract class Element {

    private static final String SEPARATOR_CLASS = "separator";
    private static final String SOLID_CLASS = "solid";
    private static final String DOTTED_CLASS = "dotted";
    private static final String BOLD_CLASS = "bold";
    private static final String ITALIC_CLASS = "italic";
    private static final String LARGE_CLASS = "large";
    private static final String XLARGE_CLASS = "xlarge";
    private static final String XXLARGE_CLASS = "xxlarge";
    private static final String SMALL_CLASS = "small";
    private static final String ALIGN_CENTER_CLASS = "center";
    private static final String ALIGN_RIGHT_CLASS = "right";

    @JacksonXmlProperty(localName = "style", isAttribute = true)
    private String styleAttr;

    @JacksonXmlProperty(localName = "class", isAttribute = true)
    private String className;

    @JsonIgnore
    public boolean isSeparator() {
        return hasClass(SEPARATOR_CLASS);
    }

    @JsonIgnore
    public boolean isSolidSeparator() {
        return hasClass(SEPARATOR_CLASS) && hasClass(SOLID_CLASS);
    }

    @JsonIgnore
    public boolean isDottedSeparator() {
        return hasClass(SEPARATOR_CLASS) && hasClass(DOTTED_CLASS);
    }

    @JsonIgnore
    public boolean isBold() {
        return hasClass(BOLD_CLASS);
    }

    @JsonIgnore
    public boolean isItalic() {
        return hasClass(ITALIC_CLASS);
    }

    @JsonIgnore
    public boolean isLarge() {
        return hasClass(LARGE_CLASS);
    }

    @JsonIgnore
    public boolean isXLarge() {
        return hasClass(XLARGE_CLASS);
    }

    @JsonIgnore
    public boolean isXXLarge() {
        return hasClass(XXLARGE_CLASS);
    }

    @JsonIgnore
    public boolean isSmall() {
        return hasClass(SMALL_CLASS);
    }

    @JsonIgnore
    public boolean isAlignCenter() {
        return hasClass(ALIGN_CENTER_CLASS);
    }

    @JsonIgnore
    public boolean isAlignRight() {
        return hasClass(ALIGN_RIGHT_CLASS);
    }

    @JsonIgnore
    private boolean hasClass(String classStr) {
        if (className != null) {
            String[] classes = className.split(" ");
            for (String cl : classes) {
                if (cl.equalsIgnoreCase(classStr)) {
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }

    @JsonIgnore
    public FontSize getFontSize() {
        if (isXXLarge()) {
            return FontSize.FONT_XXLARGE;
        } else if (isXLarge()) {
            return FontSize.FONT_XLARGE;
        } else if (isLarge()) {
            return FontSize.FONT_BIG;
        } else if (isSmall()) {
            return FontSize.FONT_SMALL;
        } else {
            return FontSize.FONT_NORMAL;
        }
    }

    @JsonIgnore
    public IPage.EAlign getTextAlign() {
        if (isAlignCenter()) {
            return IPage.EAlign.CENTER;
        } else if (isAlignRight()) {
            return IPage.EAlign.RIGHT;
        } else {
            return IPage.EAlign.LEFT;
        }
    }

    @JsonIgnore
    public FontStyle getFontStyle() {
        if (isBold()) {
            return FontStyle.TEXT_STYLE_BOLD;
        } else if (isItalic()) {
            return FontStyle.TEXT_STYLE_ITALIC;
        } else {
            return FontStyle.TEXT_STYLE_NORMAL;
        }
    }

    @JsonIgnore
    public String getFontface() {
        String fontface = getCssPropertyValue("font-family");
        if (fontface != null) {
            return fontface.toLowerCase();
        }
        return fontface;
    }

    @JsonIgnore
    public String getCssPropertyValue(String property) {
        if (styleAttr != null) {
            String[] rules = styleAttr.split(";");
            for (String rule : rules) {
                String[] parts = rule.split(":");
                if (parts.length > 1 && parts[0].trim().equalsIgnoreCase(property)) {
                    return parts[1].trim().replaceAll("^[\\\"']", "").replaceAll("[\\\"']$", "");
                }
            }
        }
        return null;
    }

}
