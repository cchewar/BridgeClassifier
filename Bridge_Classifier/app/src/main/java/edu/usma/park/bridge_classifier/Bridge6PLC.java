package edu.usma.park.bridge_classifier;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Bridge6PLC extends AppCompatActivity {

    String warnings;
    double factorsProduct;

    double t1;
    double t2;
    double w1;
    double w2;

    double plc;
    EditText plcEditText;

    Button classifyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bridge6_plc);

        plcEditText= (EditText)findViewById(R.id.plc);

        classifyButton = (Button)findViewById(R.id.classify);
        classifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                classify();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bridge6_plc, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.about) {
            Intent i = new Intent(getBaseContext(), About.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    public void getValues(){
        try {
            plc = Double.parseDouble(plcEditText.getText().toString());
        }
        catch (NumberFormatException e){
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;
            CharSequence toastText = "Invalid numerals in fields";
            Toast toast = Toast.makeText(context, toastText, duration);
            toast.show();
        }
    }

    public void classify() {
        getValues();

        Intent intent = getIntent();
        warnings = intent.getStringExtra("warnings");
        factorsProduct = intent.getDoubleExtra("factorsProduct", 0);
        t1 = intent.getDoubleExtra("t1", 0);
        t2 = intent.getDoubleExtra("t2", 0);
        w1 = intent.getDoubleExtra("w1", 0);
        w2 = intent.getDoubleExtra("w2", 0);

        if (plc <= 0) {
            Context context = getApplicationContext();
            CharSequence toastText = "Enter valid PLC value";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, toastText, duration);
            toast.show();
        } else {

            //step 3
            double factor_T1 = plc * factorsProduct;
            double factor_T2 = 0.90 * factor_T1;
            //Complete the rest of step 3 in the next activity


            //Pass into and start next activity (Figure 10 Bridge Classification)
            t1 = Math.min(factor_T1, t1);
            t2 = Math.min(factor_T2, t2);
            //w1 and w2 have not changed since in this activity

            Intent i = new Intent(getBaseContext(), Bridge6BridgeClass.class);
            i.putExtra("t1", t1);
            i.putExtra("t2", t2);
            i.putExtra("w1", w1);
            i.putExtra("w2", w2);
            i.putExtra("warnings", warnings);
            startActivity(i);
        }
    }
}
