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

public class Bridge1DeckClassification extends AppCompatActivity {

    String warnings;
    double val_teff;
    double val_Ss;

    double t1;
    double t2;
    double w1;
    double w2;

    EditText deckClassEditText;
    TextView textViewSs;
    TextView textViewteff;

    double val_deckClass;
    Button classifyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bridge1_deck_classification);

        deckClassEditText= (EditText)findViewById(R.id.deckClass);

        classifyButton = (Button)findViewById(R.id.classify);
        classifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                classify();
            }
        });

        textViewSs = (TextView)findViewById(R.id.Ss);
        textViewSs.setText("Use Ss value: " + Double.toString(val_Ss));

        textViewteff = (TextView)findViewById(R.id.teff);
        textViewteff.setText("Use t(eff) value: " + Double.toString(val_teff));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bridge1_deck_classification, menu);
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
            val_deckClass = Double.parseDouble(deckClassEditText.getText().toString());
        }
        catch (NumberFormatException e){
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;
            CharSequence toastText = "Invalid numerals in fields";
            Toast toast = Toast.makeText(context, toastText, duration);
            toast.show();
        }

    }

    public void classify(){
        getValues();

        Intent intent = getIntent();
        warnings = intent.getStringExtra("warnings");
        val_teff = intent.getDoubleExtra("teff", 0);
        val_Ss = intent.getDoubleExtra("Ss", 0);
        t1 = intent.getDoubleExtra("t1", 0);
        t2 = intent.getDoubleExtra("t2", 0);
        w1 = intent.getDoubleExtra("w1", 0);
        w2 = intent.getDoubleExtra("w2", 0);

        if(val_deckClass <= 0){
            Context context = getApplicationContext();
            CharSequence toastText = "Enter valid Deck Class";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, toastText, duration);
            toast.show();
        }
        else {
            t1 = Math.min(val_deckClass, t1);
            t2 = Math.min(val_deckClass, t2);
            w1 = Math.min(val_deckClass, w1);
            w2 = Math.min(val_deckClass, w2);

            Intent i = new Intent(getBaseContext(), Bridge1Final.class);
            i.putExtra("t1", t1);
            i.putExtra("t2", t2);
            i.putExtra("w1", w1);
            i.putExtra("w2", w2);
            i.putExtra("warnings", warnings);
            startActivity(i);
        }
    }


}
