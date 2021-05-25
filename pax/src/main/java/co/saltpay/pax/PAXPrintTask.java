package co.saltpay.pax;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.SparseArray;

import com.pax.dal.IPrinter;
import com.pax.dal.exceptions.EPrintDevException;
import com.pax.dal.exceptions.PrinterDevException;

import co.saltpay.pax.shared.PrintError;

public final class PAXPrintTask extends PrintTask {

	private SparseArray<EPrintDevException> printExceptions;

	PAXPrintTask(Context context, String receiptStr) {
		super(context, receiptStr);
		printExceptions = getFormattedPrintExceptions();

	}

	private SparseArray<EPrintDevException> getFormattedPrintExceptions() {

		SparseArray<EPrintDevException> exceptions = new SparseArray<>();
		exceptions.put(97, EPrintDevException.DEVICES_ERR_UNEXPECTED);
		exceptions.put(98, EPrintDevException.DEVICES_ERR_INVALID_ARGUMENT);
		exceptions.put(99, EPrintDevException.DEVICES_ERR_CONNECT);
		exceptions.put(100, EPrintDevException.DEVICES_ERR_NO_SUPPORT);
		exceptions.put(101, EPrintDevException.DEVICES_ERR_NO_PERMISSION);
		exceptions.put(102, EPrintDevException.ERROR_DISABLED);
		exceptions.put(103, EPrintDevException.ERROR_NOT_IN_WHITELIST);
		exceptions.put(1, EPrintDevException.PRINTER_ERR_BUSY);
		exceptions.put(2, EPrintDevException.PRINTER_ERR_OUT_OF_PAPER);
		exceptions.put(3, EPrintDevException.PRINTER_ERR_DATA_PACKET_ERROR);
		exceptions.put(4, EPrintDevException.PRINTER_ERR_PRINTER_PROBLEMS);
		exceptions.put(5, EPrintDevException.PRINTER_ERR_PRINTER_OVER_HEATING);
		exceptions.put(6, EPrintDevException.PRINTER_ERR_PRINT_UNFINISHED);
		exceptions.put(7, EPrintDevException.PRINTER_ERR_LACK_OF_FONT);
		exceptions.put(8, EPrintDevException.PRINTER_ERR_TOO_LONG);
		exceptions.put(9, EPrintDevException.PRINTER_ERR_VOLTAGE_TOO_LOW);
		exceptions.put(10, EPrintDevException.PRINTER_ERR_CUT_PAPER);
		exceptions.put(11, EPrintDevException.PRINTER_ERR_COVER_OPEN);
		exceptions.put(12, EPrintDevException.PRINTER_ERR_CUTTER_JAM);
		exceptions.put(13, EPrintDevException.PRINTER_ERR_UNSUPPORTED_ENCODING);
		exceptions.put(14, EPrintDevException.PRINTER_ERR_FONT_FORMAT_ERR);
		exceptions.put(15, EPrintDevException.PRINTER_ERR_FONT_NOT_EXIST);

		return exceptions;
	}

	@Override
	protected PrintError printBitmap(Bitmap bitmap) {
		IPrinter printer = TerminalPaxProvider.getDal(context).getPrinter();
		try {
			printer.init();
			printer.printBitmap(bitmap);
			printer.step(100);
			int res = printer.start();
			PrintError error = printErrorFromPrintResultInteger(res);

			switch (error) {
				case None:
					return PrintError.None;
				case OutOfPaper:
					return PrintError.OutOfPaper;
				case BatteryTooLow:
					return PrintError.BatteryTooLow;
				default:
					return error;
			}
		} catch (PrinterDevException e) {
			Log.e("PRINT", "Exception during print receipt : " + e.getMessage());
			return printErrorFromPrintResultInteger(e.getErrCode());
		}
	}

	private PrintError printErrorFromPrintResultInteger(int result) {
		if (result == 0) {
			return PrintError.None;
		} else {
			EPrintDevException exception = printExceptions.get(result);

			if(exception == null) {
				return PrintError.Unexpected;
			} else {
				return printErrorFromPaxException(exception);
			}
		}
	}

	PrintError printErrorFromPaxException(EPrintDevException exception) {
		switch(exception) {
			case DEVICES_ERR_INVALID_ARGUMENT:
				return PrintError.InvalidArgument;
			case DEVICES_ERR_CONNECT:
				return PrintError.CantConnectToPrinter;
			case DEVICES_ERR_NO_SUPPORT:
				return PrintError.NotSupported;
			case DEVICES_ERR_NO_PERMISSION:
				return PrintError.NoPermission;
			case ERROR_DISABLED:
				return PrintError.PrinterDisabled;
			case ERROR_NOT_IN_WHITELIST:
				return PrintError.NotWhitelisted;
			case PRINTER_ERR_BUSY:
				return PrintError.Busy;
			case PRINTER_ERR_OUT_OF_PAPER:
				return PrintError.OutOfPaper;
			case PRINTER_ERR_DATA_PACKET_ERROR:
				return PrintError.DataPacketInvalid;
			case PRINTER_ERR_PRINTER_PROBLEMS:
				return PrintError.PrinterHasProblems;
			case PRINTER_ERR_PRINTER_OVER_HEATING:
				return PrintError.PrinterOverheating;
			case PRINTER_ERR_PRINT_UNFINISHED:
				return PrintError.PrintingUnfinished;
			case PRINTER_ERR_LACK_OF_FONT:
			case PRINTER_ERR_FONT_NOT_EXIST:
				return PrintError.FontNotPresent;
			case PRINTER_ERR_FONT_FORMAT_ERR:
				return PrintError.FontFormatError;
			case PRINTER_ERR_TOO_LONG:
				return PrintError.TooLong;
			case PRINTER_ERR_VOLTAGE_TOO_LOW:
				return PrintError.BatteryTooLow;
			case PRINTER_ERR_CUT_PAPER:
				return PrintError.PaperCutterError;
			case PRINTER_ERR_COVER_OPEN:
				return PrintError.CoverOpen;
			case PRINTER_ERR_CUTTER_JAM:
				return PrintError.PaperCutterJam;
			case PRINTER_ERR_UNSUPPORTED_ENCODING:
				return PrintError.UnsupportedEncoding;
			case DEVICES_ERR_UNEXPECTED:
			default:
				return PrintError.Unexpected;
		}
	}
}