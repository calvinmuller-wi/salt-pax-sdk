package co.saltpay.pax.shared;


public class DatecsTerminalTypeCalculator {

    private static final String HIPRO_SN_REGEX = "^(?:7\\d{1,11}|(?:25|11)\\d{1,10})$";
    private static final String HIFIVE_SN_REGEX = "^(?:12|19)\\d{1,10}$";
    private static final String HILITE_SN_REGEX = "^(?:9|6)\\d{1,11}$";
    private static final String HIPLUS_SN_REGEX = "^16\\d{1,11}$";


//    Map<TerminalType, String> regexMap;
//
//    public DatecsTerminalTypeCalculator() {
//        regexMap = new HashMap<>();
//        regexMap.put(HIPRO, HIPRO_SN_REGEX);
//        regexMap.put(HIFIVE, HIFIVE_SN_REGEX);
//        regexMap.put(HILITE, HILITE_SN_REGEX);
//        regexMap.put(HIPLUS, HIPLUS_SN_REGEX);
//    }
//
//    public TerminalType calculateFromSN(String serialNumber) {
//        for (Map.Entry<TerminalType, String> entry : regexMap.entrySet()) {
//            if (serialNumber.matches(entry.getValue())) {
//                return entry.getKey();
//            }
//        }
//        return UNKNOWN;
//    }
//
//    public String getRegex(TerminalType terminalType) {
//        return regexMap.get(terminalType);
//    }
}