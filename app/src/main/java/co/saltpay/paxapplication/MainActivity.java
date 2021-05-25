package co.saltpay.paxapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import co.saltpay.pax.Manufacturer;
import co.saltpay.pax.PrintTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String receiptStr = "<html>\n" +
                "\n" +
                "<head></head>\n" +
                "\n" +
                "<body>\n" +
                "    <header>\n" +
                "        <img class=\"center\" src=\"https://i.ibb.co/fqXJwfj/salt-small.png\"/>\n" +
                "        <div>\n" +
                "            <p class=\"large bold center\">Transaction summary</p>\n" +
                "        </div>\n" +
                "        <div>\n" +
                "            <p class=\"xsmall center\">Date</p>\n" +
                "        </div>\n" +
                "        <div>\n" +
                "            <p class=\"large bold center\">\n" +
                "              Terminal ID\n" +
                "            </p>\n" +
                "        </div>\n" +
                "        <div><p></p></div>\n" +
                "        \n" +
                "        <div>\n" +
                "         <p class=\"xlarge bold center\">Helelo</p>\n" +
                "        </div>\n" +
                "    </header>\n" +
                "    <main>\n" +
                "        <div>\n" +
                "            <p class=\"center bold\">Test</p>\n" +
                "        </div>\n" +
                "        <div>\n" +
                "            <p class=\"center\">MID: Test</p>\n" +
                "        </div>\n" +
                "        <div>\n" +
                "            <p class=\"center\">POS: Test</p>\n" +
                "        </div>\n" +
                "    </main>\n" +
                "    <footer>\n" +
                "        <div>\n" +
                "            <p class=\"center bold\">Test</p>\n" +
                "        </div>\n" +
                "        <div>\n" +
                "            <p class=\"center\">MID: Test</p>\n" +
                "        </div>\n" +
                "        <div>\n" +
                "            <p class=\"center\">POS: Test</p>\n" +
                "        </div>\n" +
                "    </footer>\n" +
                "</body>\n" +
                "\n" +
                "</html>";
        Button mButton = (Button) findViewById(R.id.button);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PrintTask printTask = PrintTask.printTaskOf(Manufacturer.PAX, receiptStr, getApplicationContext());

                if (printTask != null) {
                    new Thread(printTask).start();
                }
            }
        });

    }

}