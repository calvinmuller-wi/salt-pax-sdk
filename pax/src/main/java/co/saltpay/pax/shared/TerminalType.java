package co.saltpay.pax.shared;//package co.saltpay.pax_printer.shared;
//
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//
//import co.saltpay.pax_printer.utils.Util;
//
//public enum TerminalType {
//
//    UNKNOWN(Manufacturer.INVALID, Collections.EMPTY_LIST),
//    MPED400(Manufacturer.DATECS, Arrays.asList("MPED400")),
//    MPED800(Manufacturer.DATECS, Arrays.asList("MPED800")),
//    HILITE(Manufacturer.DATECS, Arrays.asList("HILITE")),
//    HIPLUS(Manufacturer.DATECS, Arrays.asList("HIPLUS")),
//    HIPRO(Manufacturer.DATECS, Arrays.asList("HIPRO")),
//    HIFIVE(Manufacturer.DATECS, Arrays.asList("HIFIVE")),
//    PAXA920(Manufacturer.PAX, Arrays.asList("PAXA920", "A920")),
//    PAXA80(Manufacturer.PAX, Arrays.asList("PAXA80", "A80")),
//    TELPOTPS900(Manufacturer.TELPO, Arrays.asList("TELPOTPS900", "TPS900"));
//
//    private Manufacturer manufacturer;
//    private List<String> aliases;
//
//    TerminalType(Manufacturer manufacturer, List<String> aliases) {
//        this.manufacturer = manufacturer;
//        this.aliases = aliases;
//    }
//
//    public static TerminalType parseTerminalType(String typeAsString) {
//        if (Util.isNotBlank(typeAsString)) {
//            for (TerminalType type : TerminalType.values()) {
//                if (type.getAliases().contains(typeAsString.toUpperCase())) {
//                    return type;
//                }
//            }
//        }
//        return UNKNOWN;
//    }
//
//    public List<String> getAliases() {
//        return this.aliases;
//    }
//
//    public boolean isDatecs() {
//        return Manufacturer.DATECS.equals(this.manufacturer);
//    }
//
//    public boolean isPax() {
//        return Manufacturer.PAX.equals(this.manufacturer);
//    }
//
//    public boolean isTelpo() {
//        return Manufacturer.TELPO.equals(this.manufacturer);
//    }
//
//    public Manufacturer getBrand() {
//        return this.manufacturer;
//    }
//
//    public Manufacturer getManufacturer() {
//        return this.manufacturer;
//    }
//
//
//}