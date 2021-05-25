package co.saltpay.pax.models.receipt;

public enum FontStyle {

    TEXT_STYLE_NORMAL(0),
    TEXT_STYLE_BOLD(1),
    TEXT_STYLE_UNDERLINE(2),
    TEXT_STYLE_ITALIC(4);

    private int fontStyleValue;

    FontStyle(int fontStyleValue) {
        this.fontStyleValue = fontStyleValue;
    }

    public int getFontStyle() {
        return fontStyleValue;
    }
}
