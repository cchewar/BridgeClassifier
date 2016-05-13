package edu.usma.park.bridge_classifier;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class Bridge4Final extends AppCompatActivity {

    double t1;
    double t2;
    double w1;
    double w2;
    String warnings;

    TextView textViewt1;
    TextView textViewt2;
    TextView textVieww1;
    TextView textVieww2;
    TextView textViewWarnings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bridge4_final);

        Intent intent = getIntent();
        t1 = intent.getDoubleExtra("t1", 0);
        t2 = intent.getDoubleExtra("t2", 0);
        w1 = intent.getDoubleExtra("w1", 0);
        w2 = intent.getDoubleExtra("w2", 0);
        warnings = intent.getStringExtra("warnings");

        textViewt1 = (TextView)findViewById(R.id.t1);
        textViewt1.setText(Double.toString(t1));

        textViewt2 = (TextView)findViewById(R.id.t2);
        textViewt2.setText( Double.toString(t2));

        textVieww1 = (TextView)findViewById(R.id.w1);
        textVieww1.setText(Double.toString(w1));

        textVieww2 = (TextView)findViewById(R.id.w2);
        textVieww2.setText(Double.toString(w2));

        textViewWarnings = (TextView)findViewById(R.id.warnings);
        textViewWarnings.setText(warnings);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bridge4_final, menu);
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
}