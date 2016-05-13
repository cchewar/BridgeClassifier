package edu.usma.park.bridge_classifier;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Bridge6BridgeClass extends AppCompatActivity {

    String warnings;
    double factorsProduct;

    double t1;
    double t2;
    double w1;
    double w2;

    //w1 and w2 refer to the classifications determined from figure 12
    double w1Class;
    EditText w1ClassEditText;
    double w2Class;
    EditText w2ClassEditText;

    Button classifyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bridge6_bridge_class);

        w1ClassEditText= (EditText)findViewById(R.id.w1Class);
        w2ClassEditText= (EditText)findViewById(R.id.w2Class);

        TextView textViewt1 = (TextView) findViewById(R.id.t1);
        textViewt1.setText("Use T1 value: " + Double.toString(t1));

        TextView textViewt2 = (TextView) findViewById(R.id.t2);
        textViewt2.setText("Use T2 value: " + Double.toString(t2));

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
        getMenuInflater().inflate(R.menu.menu_bridge6_bridge_class, menu);
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
            w1Class = Double.parseDouble(w1ClassEditText.getText().toString());
            w2Class = Double.parseDouble(w2ClassEditText.getText().toString());
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

        if (w1Class <= 0) {
            Context context = getApplicationContext();
            CharSequence toastText = "Enter valid W1 value";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, toastText, duration);
            toast.show();
        }
        else if (w2Class <= 0) {
            Context context = getApplicationContext();
            CharSequence toastText = "Enter valid W2 value";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, toastText, duration);
            toast.show();
        }
        else{


            //Pass into and start next activity (Final)
            //t1 and t2 have not changed since in this activity
            w1 = Math.min(w1, w1Class);
            w2 = Math.min(w2, w2Class);

            Intent i = new Intent(getBaseContext(), Bridge6Final.class);
            i.putExtra("t1", t1);
            i.putExtra("t2", t2);
            i.putExtra("w1", w1);
            i.putExtra("w2", w2);
            i.putExtra("warnings", warnings);
            startActivity(i);
        }
    }


}
