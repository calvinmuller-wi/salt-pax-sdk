package co.saltpay.pax.shared;

public enum PrintError {
    None,
    Unexpected,
    InvalidArgument,
    CantConnectToPrinter,
    NotSupported,
    NoPermission,
    PrinterDisabled,
    NotWhitelisted,
    Busy,
    OutOfPaper,
    DataPacketInvalid,
    PrinterHasProblems, //Me too printer, me too.
    PrinterOverheating,
    PrintingUnfinished,
    FontNotPresent,
    FontFormatError,
    TooLong,
    BatteryTooLow,
    PaperCutterError,
    PaperCutterJam,
    CoverOpen,
    UnsupportedEncoding
}
