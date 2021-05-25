package co.saltpay.pax.Pax;

import android.util.Log;

public abstract class PaxBase implements Runnable {
    private String childName = "";

    public PaxBase() {

    }

    public void logTrue(String method) {
        childName = getClass().getSimpleName() + ".";
        String trueLog = childName + method;
        Log.i("IPPITest", trueLog);
        //clear();
//        FloatView.appendLog("true:"+trueLog + "\n");
    }

    public void logErr(String method, String errString) {
        childName = getClass().getSimpleName() + ".";
        String errorLog = childName + method + "   errorMessage：" + errString;
        Log.e("IPPITest", errorLog);
        //clear();
//        FloatView.appendLog("error:"+errorLog + "\n");
    }

    @Override
    public void run() {

    }
}
