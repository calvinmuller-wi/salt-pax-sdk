package co.saltpay.pax.models.receipt;

public enum FontSize {

    FONT_SMALL(19),
    FONT_NORMAL(22),
    FONT_BIG(28),
    FONT_XLARGE(36),
    FONT_XXLARGE(42);

    private int fontSizeValue;

    FontSize(int fontSizeValue) {
        this.fontSizeValue = fontSizeValue;
    }

    public int getFontSize() {
        return fontSizeValue;
    }
}
