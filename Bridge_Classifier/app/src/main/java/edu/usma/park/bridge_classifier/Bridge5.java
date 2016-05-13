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

//Reinforced Concrete-Slab Bridge With Asphalt Wearing Surface
public class Bridge5 extends AppCompatActivity {

    double val_L;
    EditText spanLengthEditText;
    double val_bd;
    EditText slabWidthEditText;
    double val_br;
    EditText roadwayWidthEditText;

    Context context;
    CharSequence toastText;
    int duration = Toast.LENGTH_SHORT;
    Toast toast;

    Button classifyButton;

    String warnings = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bridge5);

        spanLengthEditText = (EditText)findViewById(R.id.spanLength);
        slabWidthEditText = (EditText)findViewById(R.id.slabWidth);
        roadwayWidthEditText = (EditText)findViewById(R.id.roadwayWidth);

        classifyButton =(Button)findViewById(R.id.classify);
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
        getMenuInflater().inflate(R.menu.menu_bridge5, menu);
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

    public void getValues() {
        try {
            val_L = Double.parseDouble(spanLengthEditText.getText().toString());
            val_bd = Double.parseDouble(slabWidthEditText.getText().toString());
            val_br = Double.parseDouble(roadwayWidthEditText.getText().toString());

        } catch (NumberFormatException e) {
            context = getApplicationContext();
            toastText = "Invalid numerals in fields";
            toast = Toast.makeText(context, toastText, duration);
            toast.show();
        }
    }

    public void classify(){
        //get entered values
        getValues();

        //check for invalid or empty values
        if (val_L <= 0) {
            context = getApplicationContext();
            toastText = "Enter valid Span Length";
            toast = Toast.makeText(context, toastText, duration);
            toast.show();
        } else if (val_bd <= 0) {
            context = getApplicationContext();
            toastText = "Enter valid Concrete Slab Width";
            toast = Toast.makeText(context, toastText, duration);
            toast.show();
        } else if (val_br <= 0) {
            context = getApplicationContext();
            toastText = "Enter valid Roadway Width";
            toast = Toast.makeText(context, toastText, duration);
            toast.show();
        }
        else{
            //clear warnings of outdated messages
            warnings = "";

            //Steps 1 through 5 completed in next activity as part of Moment Classification

            //step 6
            //Lookups from Table 1: Width classification
            //val_table1 holds returned values from lookups into table 1
            //index 0 -> T1
            //index 1 -> T2
            //index 2 -> W1
            //index 3 -> W2
            double[] val_Table1 = table1();
            double width_T1 = val_Table1[0];
            double width_T2 = val_Table1[1];
            double width_W1 = val_Table1[2];
            double width_W2 = val_Table1[3];

            //Pass into and start next activity (Moment Classification)
            Intent i = new Intent(getBaseContext(), Bridge5MomentClassification.class);
            i.putExtra("t1", width_T1);
            i.putExtra("t2", width_T2);
            i.putExtra("w1", width_W1);
            i.putExtra("w2", width_W2);
            i.putExtra("warnings", warnings);
            //Need these to determine moment classification
            i.putExtra("val_L", val_L);
            i.putExtra("val_bd", val_bd);
            i.putExtra("val_br", val_br);
            startActivity(i);
        }
    }

//-----TABLE LOOKUPS FUNCTIONS ----------------------------------------------------------------

    //TABLE 1 -------------------------------------------------------------------------------------
    public double[] table1(){
        warnings += "\nMinimum overhead clearance for all classes is 15'.";
        if(val_br < 9.00){
            warnings += "\nWidth value too small.";
        }
        else if(val_br < 11.00) return new double[] {12,12,0,0};
        else if(val_br < 13.166) return new double[] {30,30,0,0};
        else if(val_br < 14.75) return new double[] {60,60,0,0};
        else if(val_br < 16.416) return new double[] {100,100,0,0};
        else if(val_br < 18.00) return new double[] {150,150,0,0};
        else if(val_br < 24.00) return new double[] {150,150,30,30};
        else if(val_br < 27.00) return new double[] {150,150,60,60};
        else if(val_br < 32.00) return new double[] {150,150,100,100};
        else return new double[] {150,150,150,150};

        //If we get down here, we have a bug
        return new double[] {-1, -1, -1 ,1};
    }
}

