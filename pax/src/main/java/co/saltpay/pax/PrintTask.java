package co.saltpay.pax;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import co.saltpay.pax.models.receipt.PrintableReceipt;
import co.saltpay.pax.shared.PrintError;

public abstract class PrintTask implements Runnable {
    private String receiptStr;
    protected Context context;

    public PrintTask(Context context, String receiptStr) {
        this.context = context;
        this.receiptStr = receiptStr;
    }

    @Override
    public void run() {
        PrintError printResult = PrintError.Unexpected;
        try {
            // Download receipt
            if (receiptStr != null) {
                PrintableReceipt receipt = new PrintableReceipt(receiptStr, context);
                Bitmap bitmap = receipt.toBitMap();

                if (bitmap == null) {
                    Log.d("PRINT", "Printing bitmap page is empty");
                } else {
                    printResult = printBitmap(bitmap);
                    Log.d("PRINT", printResult.toString());
                }
            } else {
                Log.d("PRINT", "Cannot print null receipts");
            }
        } catch (IllegalStateException | InstantiationException | IllegalArgumentException ie) {
            Log.e("PRINT","Error printing receipt: " + ie.getMessage());
        } finally {
            if (printResult == PrintError.None) {
//                EventHandler.getInstance().printSuccess();
            } else {
//                EventHandler.getInstance().printError(printResult);
            }
        }
    }

    protected abstract PrintError printBitmap(Bitmap bitmap);

    public static PrintTask printTaskOf(Manufacturer manufacturer, String receiptStr, Context context) {
        switch (manufacturer) {
            case PAX:
                return new PAXPrintTask(context, receiptStr);
            case TELPO:
//                return new TelpoPrintTask(receiptStr);
            default:
//                EventHandler.getInstance().printError(PrintError.NotSupported);
                return null;

        }
    }

}