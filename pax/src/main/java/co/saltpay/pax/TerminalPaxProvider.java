package co.saltpay.pax;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.pax.dal.IDAL;
import com.pax.neptunelite.api.NeptuneLiteUser;

import co.saltpay.pax.interfaces.ITerminal;

public class TerminalPaxProvider implements ITerminal {

    private static IDAL dal;

    @Override
    public void onStart(Activity activity) {
        try {
            long start = System.currentTimeMillis();
            dal = NeptuneLiteUser.getInstance().getDal(activity.getApplicationContext());
            Log.i("Test","get dal cost:"+(System.currentTimeMillis() - start)+" ms");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static IDAL getDal(Context context){
        if(dal == null){
            try {
                dal = NeptuneLiteUser.getInstance().getDal(context);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return dal;
    }

    @Override
    public void onStop() {
    }
}
