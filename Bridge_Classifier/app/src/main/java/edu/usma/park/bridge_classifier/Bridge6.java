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

//Masonry Arch Bridge
public class Bridge6 extends AppCompatActivity {

    double val_br;
    EditText roadwayWidthEditText;

    Context context;
    CharSequence toastText;
    int duration = Toast.LENGTH_SHORT;
    Toast toast;

    Button classifyButton;

    String warnings = "";

    //These variables hold factor values for arch classification
    double profileFactors;
    EditText profileFactorsEditText;
    double materialFactors;
    EditText materialFactorsEditText;
    double jointFactors;
    EditText jointFactorsEditText;
    double deformations;
    EditText deformationsEditText;
    double crackFactors;
    EditText crackFactorsEditText;
    double abutmentSizeFactors;
    EditText abutmentSizeFactorsEditText;
    double abutmentFaultFactors;
    EditText abutmentFaultFactorsEditText;
    //factorsProduct is the product of the above factor values
    double factorsProduct;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bridge6);

        roadwayWidthEditText = (EditText)findViewById(R.id.roadwayWidth);
        profileFactorsEditText = (EditText)findViewById(R.id.profileFactors);
        materialFactorsEditText = (EditText)findViewById(R.id.materialFactors);
        jointFactorsEditText= (EditText)findViewById(R.id.jointFactors);
        deformationsEditText= (EditText)findViewById(R.id.deformations);
        crackFactorsEditText= (EditText)findViewById(R.id.crackFactors);
        abutmentSizeFactorsEditText= (EditText)findViewById(R.id.abutmentSizeFactors);
        abutmentFaultFactorsEditText= (EditText)findViewById(R.id.abutmentFaultFactors);

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
        getMenuInflater().inflate(R.menu.menu_bridge6, menu);
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
            val_br = Double.parseDouble(roadwayWidthEditText.getText().toString());
            profileFactors = Double.parseDouble(profileFactorsEditText.getText().toString());
            materialFactors = Double.parseDouble(materialFactorsEditText.getText().toString());
            jointFactors = Double.parseDouble(jointFactorsEditText.getText().toString());
            deformations = Double.parseDouble(deformationsEditText.getText().toString());
            crackFactors = Double.parseDouble(crackFactorsEditText.getText().toString());
            abutmentSizeFactors = Double.parseDouble(abutmentSizeFactorsEditText.getText().toString());
            abutmentFaultFactors = Double.parseDouble(abutmentFaultFactorsEditText.getText().toString());

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
        if (val_br <= 0) {
            context = getApplicationContext();
            toastText = "Enter valid Roadway Width";
            toast = Toast.makeText(context, toastText, duration);
            toast.show();
        }
        else if (profileFactors <= 0) {
            context = getApplicationContext();
            toastText = "Enter valid Profile Factor Value";
            toast = Toast.makeText(context, toastText, duration);
            toast.show();
        }
        else if (materialFactors <= 0) {
            context = getApplicationContext();
            toastText = "Enter valid Material Factor Value";
            toast = Toast.makeText(context, toastText, duration);
            toast.show();
        }
        else if (jointFactors <= 0) {
            context = getApplicationContext();
            toastText = "Enter valid Joint Factor Value";
            toast = Toast.makeText(context, toastText, duration);
            toast.show();
        }
        else if (deformations <= 0) {
            context = getApplicationContext();
            toastText = "Enter valid Deformation Value";
            toast = Toast.makeText(context, toastText, duration);
            toast.show();
        }
        else if (crackFactors <= 0) {
            context = getApplicationContext();
            toastText = "Enter valid Crack Factor Value";
            toast = Toast.makeText(context, toastText, duration);
            toast.show();
        }
        else if (abutmentSizeFactors <= 0) {
            context = getApplicationContext();
            toastText = "Enter valid Abutment Size Factor Value";
            toast = Toast.makeText(context, toastText, duration);
            toast.show();
        }
        else if (abutmentFaultFactors <= 0) {
            context = getApplicationContext();
            toastText = "Enter valid Abutment Fault Factor Value";
            toast = Toast.makeText(context, toastText, duration);
            toast.show();
        }
        else {

            //clear warnings of outdated messages
            warnings = "";

            //step 1
            //This will be completed in the next activity

            //step 2
            //part a is unused programmatically
            //parts b through h are completed when their EditTexts are completed

            //step 3
            //This will be completed in a future activity
            factorsProduct = profileFactors * materialFactors * jointFactors * deformations * crackFactors * abutmentSizeFactors * abutmentFaultFactors;

            //step 4
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

            //Pass into and start next activity (PLC value)
            Intent i = new Intent(getBaseContext(), Bridge6PLC.class);
            i.putExtra("t1", width_T1);
            i.putExtra("t2", width_T2);
            i.putExtra("w1", width_W1);
            i.putExtra("w2", width_W2);
            i.putExtra("warnings", warnings);
            //Need this to determine Arch Factor Classification
            i.putExtra("factorsProduct", factorsProduct);
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
