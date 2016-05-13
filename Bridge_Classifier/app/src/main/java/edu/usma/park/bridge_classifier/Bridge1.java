package edu.usma.park.bridge_classifier;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

//Timber Stringer Bridge with Timber Deck
public class Bridge1 extends AppCompatActivity {

    double val_L;
    EditText spanLengthEditText;
    double val_br;
    EditText roadwayWidthEditText;
    //double val_NL; - listed in the parameters but unused in the formula
    //EditText numberLanesEditText;
    double val_Ns;
    EditText numberStringsEditText;
    double val_Ss;
    EditText stringerSpacingEditText;
    double val_Nb;
    EditText numberBracesEditText;
    //double val_Sb; - listed in the parameters but unused in the formula
    //EditText braceSpacingEditText;
    String deckType;
    Spinner deckTypeDropDown;
    double val_td;
    EditText deckThicknessEditText;
    double val_b;
    EditText stringerWidthEditText;
    double val_d;
    EditText stringerDepthEditText;
    String stringerType;
    Spinner stringerTypeDropDown;

    Context context;
    CharSequence toastText;
    int duration = Toast.LENGTH_SHORT;
    Toast toast;

    Button classifyButton;

    double t1;
    double t2;
    double w1;
    double w2;
    String warnings = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bridge1);

        //Initializing EditTexts
        spanLengthEditText = (EditText)findViewById(R.id.spanLength);
        roadwayWidthEditText = (EditText)findViewById(R.id.roadwayWidth);
        numberStringsEditText = (EditText)findViewById(R.id.numberStrings);
        stringerSpacingEditText = (EditText)findViewById(R.id.stringerSpacing);
        numberBracesEditText = (EditText)findViewById(R.id.numberBraces);
        deckThicknessEditText = (EditText)findViewById(R.id.deckThickness);
        stringerWidthEditText = (EditText)findViewById(R.id.stringerWidth);
        stringerDepthEditText = (EditText)findViewById(R.id.stringerDepth);

        //Initializing DeckType Spinner
        deckTypeDropDown = (Spinner) findViewById(R.id.deckType);
        String[] deckTypes = new String[]{
                "SELECT",
                "Single-layer",
                "Multilayer",
                "Laminated"};
        ArrayAdapter<String> deckTypeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, deckTypes);
        deckTypeDropDown.setAdapter(deckTypeAdapter);

        deckTypeDropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) deckType = "empty";
                else if (position == 1) deckType = "Single-layer";
                else if (position == 2) deckType = "Multilayer";
                else if (position == 3) deckType = "Laminated";
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        //Initializing StringerType Spinner
        stringerTypeDropDown = (Spinner) findViewById(R.id.stringerType);
        String[] stringerTypes = new String[]{
                "SELECT",
                "Rectangular",
                "Round"};
        ArrayAdapter<String> stringerTypeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, stringerTypes);
        stringerTypeDropDown.setAdapter(stringerTypeAdapter);

        stringerTypeDropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) stringerType = "empty";
                else if (position == 1) stringerType = "Rectangular";
                else if (position == 2) stringerType = "Round";
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

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
        getMenuInflater().inflate(R.menu.menu_bridge1, menu);
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
            val_L = Double.parseDouble(spanLengthEditText.getText().toString());
            val_br = Double.parseDouble(roadwayWidthEditText.getText().toString());
            val_Ns = Double.parseDouble(numberStringsEditText.getText().toString());
            val_Ss = Double.parseDouble(stringerSpacingEditText.getText().toString());
            val_Nb = Double.parseDouble(numberBracesEditText.getText().toString());
            //Sb unused
            //deck type accounted for in onCreate spinner method
            val_td = Double.parseDouble(deckThicknessEditText.getText().toString());
            val_b = Double.parseDouble(stringerWidthEditText.getText().toString());
            val_d = Double.parseDouble(stringerDepthEditText.getText().toString());
        }
        catch (NumberFormatException e){
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
        if(val_L <= 0){
            context = getApplicationContext();
            toastText = "Enter valid Span Length";
            toast = Toast.makeText(context, toastText, duration);
            toast.show();
        }
        else if(val_br <= 0){
            context = getApplicationContext();
            toastText = "Enter valid Roadway Width";
            toast = Toast.makeText(context, toastText, duration);
            toast.show();
        }
        else if(val_Ns <= 0){
            context = getApplicationContext();
            toastText = "Enter valid Number of Stringers";
            toast = Toast.makeText(context, toastText, duration);
            toast.show();
        }
        else if(val_Ss <= 0){
            context = getApplicationContext();
            toastText = "Enter valid Stringer Spacing";
            toast = Toast.makeText(context, toastText, duration);
            toast.show();
        }
        else if(val_Nb <= 0){
            context = getApplicationContext();
            toastText = "Enter valid Number of Braces";
            toast = Toast.makeText(context, toastText, duration);
            toast.show();
        }
        else if(deckType.equals("empty")){
            context = getApplicationContext();
            toastText = "Select Deck Type";
            toast = Toast.makeText(context, toastText, duration);
            toast.show();
        }
        else if(val_td <= 0){
            context = getApplicationContext();
            toastText = "Enter valid Deck Thickness";
            toast = Toast.makeText(context, toastText, duration);
            toast.show();
        }
        else if(val_b <= 0){
            context = getApplicationContext();
            toastText = "Enter valid Deck Width";
            toast = Toast.makeText(context, toastText, duration);
            toast.show();
        }
        else if(val_d <= 0){
            context = getApplicationContext();
            toastText = "Enter valid Deck Depth";
            toast = Toast.makeText(context, toastText, duration);
            toast.show();
        }
        else if(stringerType.equals("empty")){
            context = getApplicationContext();
            toastText = "Select Stringer Type";
            toast = Toast.makeText(context, toastText, duration);
            toast.show();
        }
        //if all values are valid, continue to calculations
        else{
            //clear warnings of outdated messages
            warnings = "";

            //step 1
            //val_Table2 holds returned values from lookups into table 2
            //index 0 -> m
            //index 1 -> v
            //index 2 -> Lm
            double[] val_Table2;
            if (stringerType.equals("Rectangular")) val_Table2 = rectangularTable2();
            else val_Table2 = roundTable2();

            double val_m = 0.73 * val_Table2[0];

            //step 2
            //Omit this step for timber stringer

            //step 3
            double val_mDL = 0.0000434 * val_L * val_L * ((val_b * val_d) + (val_td * val_Ss));

            //step 4
            double val_mLL = val_m - val_mDL;

            //step 5
            double val_N1 = 60.0 / val_Ss + 1;

            //step 6
            double val_N2 = val_N1 + 1;
            //N2 is initially set to N1 + 1 to ensure that it is always greater than N1 unless br >= 18
            //if br < 18, N2 should not be considered at all. N1 and N2 are only used for min(N1, N2)
            //therefore, if N2 > N1, N1 will always be utilized
            //if br >= 18, N2 is calculated to compare with N1
            if (val_br >= 18) val_N2 = 0.375 * val_Ns;

            //step 7
            double val_MLL1 = val_N1 * val_mLL;

            //step 8
            double val_MLL2 = Math.min(val_N1, val_N2) * val_mLL;

            //step 9
            //Lookups from Table 4: Moment Classification
            double moment_T1 = table4_T(val_MLL1);
            double moment_T2 = table4_T(val_MLL2);
            double moment_W1 = table4_W(val_MLL1);
            double moment_W2 = table4_W(val_MLL2);

            //step 10
            double val_v = 0.63 * val_Table2[1];

            //step 11
            double val_vDL = 0.000174 * val_L * ((val_b * val_d) + (val_td * val_Ss));

            //step 12
            double val_vLL = val_v - val_vDL;

            //step 13
            double val_VLL = 5.33 * val_vLL * (Math.min(val_N1, val_N2) / (Math.min(val_N1, val_N2) + 1));

            //step 14
            //Lookups from Table 5: Shear Classification
            double shear_T1 = table5_T(val_VLL);
            double shear_T2 = shear_T1;
            double shear_W1 = table5_W(val_VLL);
            double shear_W2 = shear_W1;

            //step 15
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

            //step 16
            //Deck Classification
            //If deck is single layer:
            double val_teff = val_td;
            if (deckType.equals("Multiplayer")) val_teff -= 2;
            else if (deckType.equals("Laminated")) val_Ss *= 0.75;
            //This is completed in the next activity

            //step 17
            if (val_Nb < 3 && val_d >= (2 * val_b)) warnings += "\nMore braces required. Add braces.";

            //Find minimum class values
            t1 = Math.min(Math.min(moment_T1, shear_T1), width_T1);
            t2 = Math.min(Math.min(moment_T2, shear_T2), width_T2);
            w1 = Math.min(Math.min(moment_W1, shear_W1), width_W1);
            w2 = Math.min(Math.min(moment_W2, shear_W2), width_W2);

            //Pass into and start next activity (Step 16)
            Intent i = new Intent(getBaseContext(), Bridge1DeckClassification.class);
            i.putExtra("t1", t1);
            i.putExtra("t2", t2);
            i.putExtra("w1", w1);
            i.putExtra("w2", w2);
            i.putExtra("warnings", warnings);
            //Need these to determine Deck Classification
            i.putExtra("Ss", val_Ss);
            i.putExtra("teff", val_teff);
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

//TABLE 2 -------------------------------------------------------------------------------------
    public double[] rectangularTable2() {
        //if stringer size falls between table values, floor value to nearest table value

        //if stringer size is too small, return 0
        if (val_b < 4 || val_d < 6){
            warnings += "\nStringers unsuitable: dimensions too small.";
            return new double[]  {0.0,0.0,0.0};
        }
        //if stringer dimensions larger than listed, use given formula
        else if(val_b > 20 || val_d > 26) {
            //if stringer exceeds maximum span length
            if (val_L > 1.19 * val_d) {
                warnings += "\nSpan length exceeds maximum length.";
                return new double[]{0.0, 0.0, 0.0};
            }
            else {
                if (val_d > val_b * 2.0) {
                    warnings += "\nA minimum of three lateral braces is required on stringers.";
                }
                return new double[]{
                    val_b * val_d * val_d / 30.0,
                    val_b * val_d / 10.0,
                    1.19 * val_d
                };
            }
        }
        //if not the two above edge cases, use lookup
        else if(val_b < 6){
            if(val_d < 8.0) return new double[] {4.80,2.40,7.14};
            else if(val_d < 10) return new double[] {8.53,3.20,9.50};
            else if(val_d < 12) {
                warnings += "\nA minimum of three lateral braces is required on stringers.";
                return new double[] {13.33, 4.00, 11.90};
            }
            else if(val_d < 14) {
                warnings += "\nA minimum of three lateral braces is required on stringers.";
                return new double[]{19.20, 4.80, 14.30};
            }
        }
        else if(val_b < 8){
            if(val_d < 10) return new double[] {12.80, 4.40, 9.50};
            else if(val_d < 12) return new double[] {20.00, 6.00, 11.90};
            else if(val_d < 14) return new double[] {28.80, 7.20, 14.30};
            else if(val_d < 16) {
                warnings += "\nA minimum of three lateral braces is required on stringers.";
                return new double[] {39.20, 4.80, 16.70};
            }
            else if(val_d < 18) {
                    warnings += "\nA minimum of three lateral braces is required on stringers.";
                    return new double[]{51.20, 9.60, 19.10};
            }
            else if(val_d < 20) {
                warnings += "\nA minimum of three lateral braces is required on stringers.";
                return new double[]{64.80, 10.80, 21.50};
            }
        }
        else if(val_b < 10){
            if(val_d < 10) return new double[] {17.07, 6.40, 11.90};
            else if(val_d < 12) return new double[] {28.70, 8.00, 11.90};
            else if(val_d < 14) return new double[] {38.40, 9.60, 14.30};
            else if(val_d < 16) return new double[] {52.30, 11.20, 16.70};
            else if(val_d < 18) return new double[] {68.30, 12.80, 19.10};
            else if(val_d < 20) {
                warnings += "\nA minimum of three lateral braces is required on stringers.";
                return new double[]  {86.40, 14.40, 21.50};
            }
            else if(val_d < 22) {
                warnings += "\nA minimum of three lateral braces is required on stringers.";
                return new double[] {106.70, 16.40, 23.80};
            }
            else if(val_d < 24) {
                warnings += "\nA minimum of three lateral braces is required on stringers.";
                return new double[]{129.10, 17.60, 26.20};
            }
            else if(val_d < 26) {
                warnings += "\nA minimum of three lateral braces is required on stringers.";
                return new double[]{153.60, 19.20, 28.60};
            }
        }
        else if(val_b < 12){
            if(val_d < 12) return new double[] {33.40, 10.00, 11.90};
            else if(val_d < 14) return new double[] {48.00, 12.00, 14.30};
            else if(val_d < 16) return new double[] {65.30, 14.00, 16.70};
            else if(val_d < 18) return new double[] {85.30, 16.00, 19.10};
            else if(val_d < 20) return new double[] {108.00, 18.00, 21.50};
            else if(val_d < 22){
                warnings += "\nA minimum of three lateral braces is required on stringers.";
                return new double[]  {133.30, 20.00, 23.80};
            }
            else if(val_d < 24){
                warnings += "\nA minimum of three lateral braces is required on stringers.";
                return new double[] {161.30, 22.00, 26.20};
            }
            else if(val_d < 26) return new double[] {192.00, 24.00, 28.60};
        }
        else if(val_b < 14){
            if(val_d < 14) return new double[] {57.60, 14.40, 14.30};
            else if(val_d < 16) return new double[] {78.40, 16.80, 16.70};
            else if(val_d < 18) return new double[] {102.40, 19.20, 19.10};
            else if(val_d < 20) return new double[] {129.60, 21.60, 21.50};
            else if(val_d < 22) return new double[] {160.00, 24.00, 23.80};
            else if(val_d < 24) return new double[] {193.60, 26.40, 26.20};
            else if(val_d < 26) return new double[] {230.00, 28.80, 28.60};
        }
        else if(val_b < 16){
            if(val_d < 16) return new double[] {81.50, 19.60, 16.70};
            else if(val_d < 18) return new double[] {119.50, 22.40, 19.10};
            else if(val_d < 20) return new double[] {151.20, 25.20, 21.50};
            else if(val_d < 22) return new double[] {186.70, 28.00, 23.80};
            else if(val_d < 24) return new double[] {226.00, 30.80, 26.20};
            else if(val_d < 26) return new double[] {269.00, 33.60, 28.60};
        }
        else if(val_b < 18){
            if(val_d < 18) return new double[] {136.50, 25.60, 19.10};
            else if(val_d < 20) return new double[] {172.80, 28.80, 21.50};
            else if(val_d < 22) return new double[] {213.00, 32.00, 23.80};
            else if(val_d < 24) return new double[] {258.00, 35.20, 26.20};
            else if(val_d < 26) return new double[] {307.00, 38.40, 28.60};
        }
        else if(val_b < 20){
            if(val_d < 20) return new double[] {194.40, 32.40, 21.50};
            else if(val_d < 22) return new double[] {240.00, 36.00, 23.80};
            else if(val_d < 24) return new double[] {290.00, 39.60, 26.20};
            else if(val_d < 26) return new double[] {346.00, 43.20, 28.60};
        }
        //if we get down here, we have a bug
        return new double[] {-1.0, -1.0, -1.0};
    }

    public double[] roundTable2(){
        //if stringer size is too small, return 0
        if (val_b < 8){
            warnings += "\nStringers unsuitable: dimensions too small.";
            return new double[] {0.0,0.0,0.0};
        }
        //if stringer dimensions larger than listed, use given formula
        else if(val_b > 37){
            warnings += "\nStringer dimensions larger than expected. No tabular values available.";
            return new double[] {0.0,0.0,0.0};
        }
        //if not the two above edge cases, use lookup
        else if(val_d < 9) return new double[]  {10.05, 5.70, 9.50};
        else if(val_d < 10) return new double[] {14.31, 7.20, 10.70};
        else if(val_d < 11) return new double[] {19.63, 8.80, 11.90};
        else if(val_d < 12) return new double[] {26.10, 10.60, 13.10};
        else if(val_d < 13) return new double[] {33.90, 12.70, 14.30};
        else if(val_d < 14) return new double[] {43.10, 15.00, 15.50};
        else if(val_d < 15) return new double[] {53.90, 17.40, 16.70};
        else if(val_d < 16) return new double[] {67.50, 20.20, 17.80};
        else if(val_d < 17) return new double[] {80.40, 22.60, 19.10};
        else if(val_d < 18) return new double[] {98.20, 26.00, 20.20};
        else if(val_d < 19) return new double[] {114.50, 28.60, 21.50};
        else if(val_d < 20) return new double[] {137.10, 32.40, 22.60};
        else if(val_d < 21) return new double[] {157.10, 35.40, 23.80};
        else if(val_d < 22) return new double[] {185.20, 39.60, 24.90};
        else if(val_d < 23) return new double[] {209.00, 42.70, 26.20};
        else if(val_d < 24) return new double[] {243.00, 47.60, 27.30};
        else if(val_d < 25) return new double[] {271.00, 50.80, 28.60};
        else if(val_d < 26) return new double[] {312.00, 56.20, 29.70};
        else if(val_d < 27) return new double[] {351.00, 60.80, 30.90};
        else if(val_d < 28) return new double[] {393.00, 65.60, 32.10};
        else if(val_d < 29) return new double[] {439.00, 70.50, 33.30};
        else if(val_d < 30) return new double[] {487.00, 75.60, 34.50};
        else if(val_d < 31) return new double[] {540.00, 81.00, 35.70};
        else if(val_d < 32) return new double[] {595.00, 86.40, 36.80};
        else if(val_d < 33) return new double[] {655.00, 92.10, 38.00};
        else if(val_d < 34) return new double[] {718.00, 98.00, 39.20};
        else if(val_d < 35) return new double[] {786.00, 104.00, 40.40};
        else if(val_d < 36) return new double[] {857.00, 110.20, 41.60};
        else if(val_d < 37) return new double[] {933.00, 116.60, 42.80};
        //if we get down here, we have a bug
        return new double[] {-1.0, -1.0, -1.0};
    }

//TABLE 4 -------------------------------------------------------------------------------------
    //Table 4, T value lookups
    public double table4_T(double moment){
        if(val_L < 4) {
            //moment less than minimum listed
            if (moment < 2.64) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 4.88) return 4;
            else if (moment < 5.44) return 8;
            else if (moment < 7.12) return 12;
            else if (moment < 8.88) return 16;
            else if (moment < 10.64) return 20;
            else if (moment < 10.88) return 24;
            else if (moment < 13.36) return 30;
            else if (moment < 15.36) return 40;
            else if (moment < 17.12) return 50;
            else if (moment < 18.64) return 60;
            else if (moment < 20.00) return 70;
            else if (moment < 21.20) return 80;
            else if (moment < 22.20) return 90;
            else if (moment < 24.00) return 100;
            else if (moment < 25.00) return 120;
            else return 150;
        }
        else if(val_L <= 6) {
            //moment less than minimum listed
            if (moment < 6.00) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 11.04) return 4;
            else if (moment < 12.00) return 8;
            else if (moment < 15.96) return 12;
            else if (moment < 20.00) return 16;
            else if (moment < 24.00) return 20;
            else if (moment < 24.50) return 24;
            else if (moment < 30.00) return 30;
            else if (moment < 34.60) return 40;
            else if (moment < 38.50) return 50;
            else if (moment < 42.00) return 60;
            else if (moment < 45.00) return 70;
            else if (moment < 47.60) return 80;
            else if (moment < 50.00) return 90;
            else if (moment < 54.00) return 100;
            else if (moment < 56.30) return 120;
            else return 150;
        }
        else if(val_L <= 8) {
            //moment less than minimum listed
            if (moment < 9.92) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 19.04) return 4;
            else if (moment < 21.03) return 8;
            else if (moment < 28.50) return 12;
            else if (moment < 35.50) return 16;
            else if (moment < 42.70) return 20;
            else if (moment < 43.70) return 24;
            else if (moment < 53.30) return 30;
            else if (moment < 61.60) return 40;
            else if (moment < 68.60) return 50;
            else if (moment < 74.70) return 60;
            else if (moment < 80.00) return 70;
            else if (moment < 84.60) return 80;
            else if (moment < 89.00) return 90;
            else if (moment < 96.00) return 100;
            else if (moment < 100.00) return 120;
            else return 150;
        }
        else if(val_L <= 10) {
            //moment less than minimum listed
            if (moment < 14.00) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 27.00) return 4;
            else if (moment < 33.00) return 8;
            else if (moment < 44.00) return 12;
            else if (moment < 55.00) return 16;
            else if (moment < 66.00) return 20;
            else if (moment < 68.20) return 24;
            else if (moment < 83.40) return 30;
            else if (moment < 96.20) return 40;
            else if (moment < 107.20) return 50;
            else if (moment < 116.60) return 60;
            else if (moment < 125.00) return 70;
            else if (moment < 132.40) return 80;
            else if (moment < 138.80) return 90;
            else if (moment < 150.00) return 100;
            else if (moment < 156.20) return 120;
            else return 150;
        }
        else if(val_L <= 12) {
            //moment less than minimum listed
            if (moment < 18.00) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 35.00) return 4;
            else if (moment < 44.90) return 8;
            else if (moment < 60.00) return 12;
            else if (moment < 74.90) return 16;
            else if (moment < 90.00) return 20;
            else if (moment < 97.40) return 24;
            else if (moment < 120.00) return 30;
            else if (moment < 138.50) return 40;
            else if (moment < 154.30) return 50;
            else if (moment < 168.00) return 60;
            else if (moment < 180.00) return 70;
            else if (moment < 190.60) return 80;
            else if (moment < 199.90) return 90;
            else if (moment < 216.00) return 100;
            else if (moment < 225.00) return 120;
            else return 150;
        }
        else if(val_L <= 14) {
            //moment less than minimum listed
            if (moment < 22.10) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 43.10) return 4;
            else if (moment < 57.10) return 8;
            else if (moment < 75.90) return 12;
            else if (moment < 94.90) return 16;
            else if (moment < 114.00) return 20;
            else if (moment < 127.40) return 24;
            else if (moment < 158.90) return 30;
            else if (moment < 187.60) return 40;
            else if (moment < 210.00) return 50;
            else if (moment < 229.00) return 60;
            else if (moment < 245.00) return 70;
            else if (moment < 259.00) return 80;
            else if (moment < 272.00) return 90;
            else if (moment < 294.00) return 100;
            else if (moment < 306.00) return 120;
            else return 150;
        }
        else if(val_L <= 16) {
            //moment less than minimum listed
            if (moment < 25.90) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 50.90) return 4;
            else if (moment < 69.10) return 8;
            else if (moment < 91.80) return 12;
            else if (moment < 114.90) return 16;
            else if (moment < 137.90) return 20;
            else if (moment < 157.40) return 24;
            else if (moment < 200.00) return 30;
            else if (moment < 237.00) return 40;
            else if (moment < 270.00) return 50;
            else if (moment < 298.00) return 60;
            else if (moment < 320.00) return 70;
            else if (moment < 339.00) return 80;
            else if (moment < 356.00) return 90;
            else if (moment < 384.00) return 100;
            else if (moment < 400.00) return 120;
            else return 150;
        }
        else if(val_L <= 18) {
            //moment less than minimum listed
            if (moment < 25.90) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 59.00) return 4;
            else if (moment < 81.00) return 8;
            else if (moment < 108.00) return 12;
            else if (moment < 135.00) return 16;
            else if (moment < 162.00) return 20;
            else if (moment < 187.60) return 24;
            else if (moment < 240.00) return 30;
            else if (moment < 288.00) return 40;
            else if (moment < 330.00) return 50;
            else if (moment < 368.00) return 60;
            else if (moment < 400.00) return 70;
            else if (moment < 427.00) return 80;
            else if (moment < 450.00) return 90;
            else if (moment < 486.00) return 100;
            else if (moment < 506.00) return 120;
            else return 150;
        }
        else if(val_L <= 20) {
            //moment less than minimum listed
            if (moment < 34.00) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 66.80) return 4;
            else if (moment < 92.80) return 8;
            else if (moment < 124.00) return 12;
            else if (moment < 154.80) return 16;
            else if (moment < 186.00) return 20;
            else if (moment < 218.00) return 24;
            else if (moment < 280.00) return 30;
            else if (moment < 338.00) return 40;
            else if (moment < 390.00) return 50;
            else if (moment < 438.00) return 60;
            else if (moment < 480.00) return 70;
            else if (moment < 518.00) return 80;
            else if (moment < 550.00) return 90;
            else if (moment < 600.00) return 100;
            else if (moment < 625.00) return 120;
            else return 150;
        }
        else if(val_L <= 25) {
            //moment less than minimum listed
            if (moment < 44.00) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 87.00) return 4;
            else if (moment < 123.00) return 8;
            else if (moment < 164.00) return 12;
            else if (moment < 205.00) return 16;
            else if (moment < 246.00) return 20;
            else if (moment < 293.00) return 24;
            else if (moment < 380.00) return 30;
            else if (moment < 463.00) return 40;
            else if (moment < 540.00) return 50;
            else if (moment < 613.00) return 60;
            else if (moment < 680.00) return 70;
            else if (moment < 743.00) return 80;
            else if (moment < 800.00) return 90;
            else if (moment < 900.00) return 100;
            else if (moment < 975.00) return 120;
            else return 150;
        }
        else if(val_L <= 30) {
            //moment less than minimum listed
            if (moment < 54.00) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 106.80) return 4;
            else if (moment < 153.00) return 8;
            else if (moment < 204.00) return 12;
            else if (moment < 255.00) return 16;
            else if (moment < 306.00) return 20;
            else if (moment < 367.00) return 24;
            else if (moment < 480.00) return 30;
            else if (moment < 587.00) return 40;
            else if (moment < 690.00) return 50;
            else if (moment < 787.00) return 60;
            else if (moment < 880.00) return 70;
            else if (moment < 967.00) return 80;
            else if (moment < 1050.00) return 90;
            else if (moment < 1200.00) return 100;
            else if (moment < 1350.00) return 120;
            else return 150;
        }
        else if(val_L <= 35) {
            //moment less than minimum listed
            if (moment < 63.70) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 126.70) return 4;
            else if (moment < 182.70) return 8;
            else if (moment < 244.0) return 12;
            else if (moment < 305.00) return 16;
            else if (moment < 366.00) return 20;
            else if (moment < 442.00) return 24;
            else if (moment < 580.00) return 30;
            else if (moment < 713.00) return 40;
            else if (moment < 840.00) return 50;
            else if (moment < 963.00) return 60;
            else if (moment < 1080.00) return 70;
            else if (moment < 1193.00) return 80;
            else if (moment < 1300.00) return 90;
            else if (moment < 1500.00) return 100;
            else if (moment < 1725.00) return 120;
            else return 150;
        }
        else if(val_L <= 40) {
            //moment less than minimum listed
            if (moment < 73.80) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 147.20) return 4;
            else if (moment < 213.00) return 8;
            else if (moment < 284.00) return 12;
            else if (moment < 355.00) return 16;
            else if (moment < 426.00) return 20;
            else if (moment < 518.00) return 24;
            else if (moment < 680.00) return 30;
            else if (moment < 838.00) return 40;
            else if (moment < 990.00) return 50;
            else if (moment < 1138.00) return 60;
            else if (moment < 1280.00) return 70;
            else if (moment < 1418.00) return 80;
            else if (moment < 1550.00) return 90;
            else if (moment < 1800.00) return 100;
            else if (moment < 2100.00) return 120;
            else return 150;
        }
        else if(val_L <= 45) {
            //moment less than minimum listed
            if (moment < 83.70) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 167.40) return 4;
            else if (moment < 243.00) return 8;
            else if (moment < 324.00) return 12;
            else if (moment < 405.00) return 16;
            else if (moment < 486.00) return 20;
            else if (moment < 592.00) return 24;
            else if (moment < 780.00) return 30;
            else if (moment < 962.00) return 40;
            else if (moment < 1140.00) return 50;
            else if (moment < 1312.00) return 60;
            else if (moment < 1480.00) return 70;
            else if (moment < 1643.00) return 80;
            else if (moment < 1800.00) return 90;
            else if (moment < 2100.00) return 100;
            else if (moment < 2478.00) return 120;
            else return 150;
        }
        else if(val_L <= 50) {
            //moment less than minimum listed
            if (moment < 94.00) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 187.00) return 4;
            else if (moment < 273.00) return 8;
            else if (moment < 364.00) return 12;
            else if (moment < 455.00) return 16;
            else if (moment < 546.00) return 20;
            else if (moment < 667.00) return 24;
            else if (moment < 880.00) return 30;
            else if (moment < 1087.00) return 40;
            else if (moment < 1290.00) return 50;
            else if (moment < 1478.00) return 60;
            else if (moment < 1680.00) return 70;
            else if (moment < 1867.00) return 80;
            else if (moment < 2050.00) return 90;
            else if (moment < 2400.00) return 100;
            else if (moment < 2850.00) return 120;
            else return 150;
        }
        else if(val_L <= 55) {
            //moment less than minimum listed
            if (moment < 103.40) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 207.00) return 4;
            else if (moment < 303.00) return 8;
            else if (moment < 404.00) return 12;
            else if (moment < 505.00) return 16;
            else if (moment < 606.00) return 20;
            else if (moment < 743.00) return 24;
            else if (moment < 980.00) return 30;
            else if (moment < 1212.00) return 40;
            else if (moment < 1440.00) return 50;
            else if (moment < 1662.00) return 60;
            else if (moment < 1880.00) return 70;
            else if (moment < 2090.00) return 80;
            else if (moment < 2300.00) return 90;
            else if (moment < 2700.00) return 100;
            else if (moment < 3230.00) return 120;
            else return 150;
        }
        else if(val_L <= 60) {
            //moment less than minimum listed
            if (moment < 114.00) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 227.00) return 4;
            else if (moment < 332.00) return 8;
            else if (moment < 444.00) return 12;
            else if (moment < 554.00) return 16;
            else if (moment < 666.00) return 20;
            else if (moment < 817.00) return 24;
            else if (moment < 1080.00) return 30;
            else if (moment < 1338.00) return 40;
            else if (moment < 1590.00) return 50;
            else if (moment < 1837.00) return 60;
            else if (moment < 2080.00) return 70;
            else if (moment < 2320.00) return 80;
            else if (moment < 2550.00) return 90;
            else if (moment < 3000.00) return 100;
            else if (moment < 3600.00) return 120;
            else return 150;
        }
        else if(val_L <= 70) {
            //moment less than minimum listed
            if (moment < 134.40) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 267.00) return 4;
            else if (moment < 393.00) return 8;
            else if (moment < 524.00) return 12;
            else if (moment < 655.00) return 16;
            else if (moment < 785.00) return 20;
            else if (moment < 967.00) return 24;
            else if (moment < 1280.00) return 30;
            else if (moment < 1588.00) return 40;
            else if (moment < 1890.00) return 50;
            else if (moment < 2190.00) return 60;
            else if (moment < 2480.00) return 70;
            else if (moment < 2770.00) return 80;
            else if (moment < 3050.00) return 90;
            else if (moment < 3600.00) return 100;
            else if (moment < 4350.00) return 120;
            else return 150;
        }
        else if(val_L <= 80) {

            //moment less than minimum listed
            if (moment < 153.60) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 307.00) return 4;
            else if (moment < 453.00) return 8;
            else if (moment < 603.00) return 12;
            else if (moment < 755.00) return 16;
            else if (moment < 906.00) return 20;
            else if (moment < 1117.00) return 24;
            else if (moment < 1480.00) return 30;
            else if (moment < 1837.00) return 40;
            else if (moment < 2190.00) return 50;
            else if (moment < 2540.00) return 60;
            else if (moment < 2880.00) return 70;
            else if (moment < 3220.00) return 80;
            else if (moment < 3550.00) return 90;
            else if (moment < 4200.00) return 100;
            else if (moment < 5100.00) return 120;
            else return 150;
        }
        else if(val_L <= 90) {
            //moment less than minimum listed
            if (moment < 174.60) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 347.00) return 4;
            else if (moment < 513.00) return 8;
            else if (moment < 684.00) return 12;
            else if (moment < 855.00) return 16;
            else if (moment < 1026.00) return 20;
            else if (moment < 1267.00) return 24;
            else if (moment < 1679.00) return 30;
            else if (moment < 2090.00) return 40;
            else if (moment < 2490.00) return 50;
            else if (moment < 2890.00) return 60;
            else if (moment < 3280.00) return 70;
            else if (moment < 3670.00) return 80;
            else if (moment < 4050.00) return 90;
            else if (moment < 4800.00) return 100;
            else if (moment < 5850.00) return 120;
            else return 150;
        }
        else if(val_L <= 100) {
            //moment less than minimum listed
            if (moment < 194.00) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 386.00) return 4;
            else if (moment < 572.00) return 8;
            else if (moment < 764.00) return 12;
            else if (moment < 954.00) return 16;
            else if (moment < 1146.00) return 20;
            else if (moment < 1418.00) return 24;
            else if (moment < 1880.00) return 30;
            else if (moment < 2340.00) return 40;
            else if (moment < 2790.00) return 50;
            else if (moment < 3240.00) return 60;
            else if (moment < 3680.00) return 70;
            else if (moment < 4120.00) return 80;
            else if (moment < 4550.00) return 90;
            else if (moment < 5400.00) return 100;
            else if (moment < 6600.00) return 120;
            else return 150;
        }
        else if(val_L <= 110) {
            //moment less than minimum listed
            if (moment < 213) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 427) return 4;
            else if (moment < 634) return 8;
            else if (moment < 845) return 12;
            else if (moment < 1054) return 16;
            else if (moment < 1265) return 20;
            else if (moment < 1566) return 24;
            else if (moment < 2080) return 30;
            else if (moment < 2590) return 40;
            else if (moment < 3090) return 50;
            else if (moment < 3590) return 60;
            else if (moment < 4080) return 70;
            else if (moment < 4570) return 80;
            else if (moment < 5050) return 90;
            else if (moment < 6000) return 100;
            else if (moment < 7350) return 120;
            else return 150;
        }
        else if(val_L <= 120) {
            //moment less than minimum listed
            if (moment < 233) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 468) return 4;
            else if (moment < 694) return 8;
            else if (moment < 924) return 12;
            else if (moment < 1154) return 16;
            else if (moment < 1385) return 20;
            else if (moment < 1718) return 24;
            else if (moment < 2280) return 30;
            else if (moment < 2840) return 40;
            else if (moment < 3390) return 50;
            else if (moment < 3940) return 60;
            else if (moment < 4480) return 70;
            else if (moment < 5020) return 80;
            else if (moment < 5550) return 90;
            else if (moment < 6600) return 100;
            else if (moment < 8100) return 120;
            else return 150;
        }
        else if(val_L <= 130) {
            //moment less than minimum listed
            if (moment < 255) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 507) return 4;
            else if (moment < 754) return 8;
            else if (moment < 1004) return 12;
            else if (moment < 1256) return 16;
            else if (moment < 1505) return 20;
            else if (moment < 1867) return 24;
            else if (moment < 2480) return 30;
            else if (moment < 3090) return 40;
            else if (moment < 3690) return 50;
            else if (moment < 4290) return 60;
            else if (moment < 4880) return 70;
            else if (moment < 5470) return 80;
            else if (moment < 6050) return 90;
            else if (moment < 7200) return 100;
            else if (moment < 8850) return 120;
            else return 150;
        }
        else if(val_L <= 140) {
            //moment less than minimum listed
            if (moment < 274) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 546) return 4;
            else if (moment < 812) return 8;
            else if (moment < 1084) return 12;
            else if (moment < 1355) return 16;
            else if (moment < 1627) return 20;
            else if (moment < 2020) return 24;
            else if (moment < 2680) return 30;
            else if (moment < 3340) return 40;
            else if (moment < 4000) return 50;
            else if (moment < 4640) return 60;
            else if (moment < 5280) return 70;
            else if (moment < 5920) return 80;
            else if (moment < 6550) return 90;
            else if (moment < 7800) return 100;
            else if (moment < 9600) return 120;
            else return 150;
        }
        else if(val_L <= 150) {
            //moment less than minimum listed
            if (moment < 294) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 588) return 4;
            else if (moment < 873) return 8;
            else if (moment < 1164) return 12;
            else if (moment < 1455) return 16;
            else if (moment < 1746) return 20;
            else if (moment < 2170) return 24;
            else if (moment < 2880) return 30;
            else if (moment < 3590) return 40;
            else if (moment < 4290) return 50;
            else if (moment < 4990) return 60;
            else if (moment < 5680) return 70;
            else if (moment < 6370) return 80;
            else if (moment < 7050) return 90;
            else if (moment < 8400) return 100;
            else if (moment < 10350) return 120;
            else return 150;
        }
        else if(val_L <= 160) {
            //moment less than minimum listed
            if (moment < 314) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 627) return 4;
            else if (moment < 934) return 8;
            else if (moment < 1245) return 12;
            else if (moment < 1555) return 16;
            else if (moment < 1866) return 20;
            else if (moment < 2310) return 24;
            else if (moment < 3080) return 30;
            else if (moment < 3840) return 40;
            else if (moment < 4590) return 50;
            else if (moment < 5340) return 60;
            else if (moment < 6080) return 70;
            else if (moment < 6820) return 80;
            else if (moment < 7550) return 90;
            else if (moment < 9000) return 100;
            else if (moment < 11100) return 120;
            else return 150;
        }
        else if(val_L <= 170) {
            //moment less than minimum listed
            if (moment < 333) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 666) return 4;
            else if (moment < 993) return 8;
            else if (moment < 1323) return 12;
            else if (moment < 1656) return 16;
            else if (moment < 1986) return 20;
            else if (moment < 2470) return 24;
            else if (moment < 3280) return 30;
            else if (moment < 4090) return 40;
            else if (moment < 4890) return 50;
            else if (moment < 5690) return 60;
            else if (moment < 6480) return 70;
            else if (moment < 7270) return 80;
            else if (moment < 8050) return 90;
            else if (moment < 9600) return 100;
            else if (moment < 11850) return 120;
            else return 150;
        }
        else if(val_L <= 180) {
            //moment less than minimum listed
            if (moment < 353) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 706) return 4;
            else if (moment < 1051) return 8;
            else if (moment < 1404) return 12;
            else if (moment < 1753) return 16;
            else if (moment < 2110) return 20;
            else if (moment < 2620) return 24;
            else if (moment < 3480) return 30;
            else if (moment < 4340) return 40;
            else if (moment < 5190) return 50;
            else if (moment < 6040) return 60;
            else if (moment < 6880) return 70;
            else if (moment < 7720) return 80;
            else if (moment < 8550) return 90;
            else if (moment < 10200) return 100;
            else if (moment < 12600) return 120;
            else return 150;
        }
        else if(val_L <= 190) {
            //moment less than minimum listed
            if (moment < 391) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 775) return 4;
            else if (moment < 1136) return 8;
            else if (moment < 1516) return 12;
            else if (moment < 1896) return 16;
            else if (moment < 2280) return 20;
            else if (moment < 2790) return 24;
            else if (moment < 3680) return 30;
            else if (moment < 4590) return 40;
            else if (moment < 5490) return 50;
            else if (moment < 6390) return 60;
            else if (moment < 7280) return 70;
            else if (moment < 8170) return 80;
            else if (moment < 9050) return 90;
            else if (moment < 10800) return 100;
            else if (moment < 13350) return 120;
            else return 150;
        }
        else if(val_L <= 200) {
            //moment less than minimum listed
            if (moment < 428) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 852) return 4;
            else if (moment < 1248) return 8;
            else if (moment < 1664) return 12;
            else if (moment < 2080) return 16;
            else if (moment < 2500) return 20;
            else if (moment < 3070) return 24;
            else if (moment < 4050) return 30;
            else if (moment < 5020) return 40;
            else if (moment < 5970) return 50;
            else if (moment < 6900) return 60;
            else if (moment < 7810) return 70;
            else if (moment < 8700) return 80;
            else if (moment < 9570) return 90;
            else if (moment < 11400) return 100;
            else if (moment < 14100) return 120;
            else return 150;
        }
        else if(val_L <= 210) {
            //moment less than minimum listed
            if (moment < 466) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 924) return 4;
            else if (moment < 1361) return 8;
            else if (moment < 1814) return 12;
            else if (moment < 2270) return 16;
            else if (moment < 2720) return 20;
            else if (moment < 3350) return 24;
            else if (moment < 4430) return 30;
            else if (moment < 5490) return 40;
            else if (moment < 6530) return 50;
            else if (moment < 7550) return 60;
            else if (moment < 8550) return 70;
            else if (moment < 9530) return 80;
            else if (moment < 10500) return 90;
            else if (moment < 12380) return 100;
            else if (moment < 14910) return 120;
            else return 150;
        }
        else if(val_L <= 220) {
            //moment less than minimum listed
            if (moment < 502) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 1003) return 4;
            else if (moment < 1474) return 8;
            else if (moment < 1967) return 12;
            else if (moment < 2460) return 16;
            else if (moment < 2950) return 20;
            else if (moment < 3630) return 24;
            else if (moment < 4800) return 30;
            else if (moment < 5950) return 40;
            else if (moment < 7090) return 50;
            else if (moment < 8200) return 60;
            else if (moment < 9300) return 70;
            else if (moment < 10380) return 80;
            else if (moment < 11440) return 90;
            else if (moment < 13500) return 100;
            else if (moment < 16320) return 120;
            else return 150;
        }
        else if(val_L <= 230) {
            //moment less than minimum listed
            if (moment < 538) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 1076) return 4;
            else if (moment < 1587) return 8;
            else if (moment < 2120) return 12;
            else if (moment < 2650) return 16;
            else if (moment < 3170) return 20;
            else if (moment < 3910) return 24;
            else if (moment < 5180) return 30;
            else if (moment < 6430) return 40;
            else if (moment < 7650) return 50;
            else if (moment < 8860) return 60;
            else if (moment < 10060) return 70;
            else if (moment < 11220) return 80;
            else if (moment < 12380) return 90;
            else if (moment < 14630) return 100;
            else if (moment < 17720) return 120;
            else return 150;
        }
        else if(val_L <= 240) {
            //moment less than minimum listed
            if (moment < 586) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 1162) return 4;
            else if (moment < 1704) return 8;
            else if (moment < 2270) return 12;
            else if (moment < 2840) return 16;
            else if (moment < 3400) return 20;
            else if (moment < 4200) return 24;
            else if (moment < 5560) return 30;
            else if (moment < 6900) return 40;
            else if (moment < 8220) return 50;
            else if (moment < 9530) return 60;
            else if (moment < 10810) return 70;
            else if (moment < 12080) return 80;
            else if (moment < 13330) return 90;
            else if (moment < 15760) return 100;
            else if (moment < 19140) return 120;
            else return 150;
        }
        else if(val_L <= 250) {
            //moment less than minimum listed
            if (moment < 645) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 1285) return 4;
            else if (moment < 1855) return 8;
            else if (moment < 2480) return 12;
            else if (moment < 3100) return 16;
            else if (moment < 3720) return 20;
            else if (moment < 4510) return 24;
            else if (moment < 5940) return 30;
            else if (moment < 7380) return 40;
            else if (moment < 8800) return 50;
            else if (moment < 10200) return 60;
            else if (moment < 11580) return 70;
            else if (moment < 12940) return 80;
            else if (moment < 14280) return 90;
            else if (moment < 16910) return 100;
            else if (moment < 20600) return 120;
            else return 150;
        }
        else if(val_L <= 260) {
            //moment less than minimum listed
            if (moment < 707) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 1404) return 4;
            else if (moment < 2040) return 8;
            else if (moment < 2710) return 12;
            else if (moment < 3400) return 16;
            else if (moment < 4070) return 20;
            else if (moment < 4960) return 24;
            else if (moment < 6520) return 30;
            else if (moment < 8040) return 40;
            else if (moment < 9510) return 50;
            else if (moment < 10940) return 60;
            else if (moment < 12340) return 70;
            else if (moment < 13800) return 80;
            else if (moment < 15230) return 90;
            else if (moment < 18050) return 100;
            else if (moment <22000 ) return 120;
            else return 150;
        }
        else if(val_L <= 270) {
            //moment less than minimum listed
            if (moment < 767) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 1523) return 4;
            else if (moment < 2220) return 8;
            else if (moment < 2950) return 12;
            else if (moment < 3690) return 16;
            else if (moment < 4430) return 20;
            else if (moment < 5410) return 24;
            else if (moment < 7120) return 30;
            else if (moment < 8790) return 40;
            else if (moment < 10410) return 50;
            else if (moment < 11990) return 60;
            else if (moment < 13520) return 70;
            else if (moment < 15010) return 80;
            else if (moment < 16450) return 90;
            else if (moment < 19200) return 100;
            else if (moment < 23400) return 120;
            else return 150;
        }
        else if(val_L <= 280) {
            //moment less than minimum listed
            if (moment < 823) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 1641) return 4;
            else if (moment < 2400) return 8;
            else if (moment < 3200) return 12;
            else if (moment < 3990) return 16;
            else if (moment < 4790) return 20;
            else if (moment < 5860) return 24;
            else if (moment < 7720) return 30;
            else if (moment < 9540) return 40;
            else if (moment < 11310) return 50;
            else if (moment < 13040) return 60;
            else if (moment < 14720) return 70;
            else if (moment < 16360) return 80;
            else if (moment < 17950) return 90;
            else if (moment < 21000) return 100;
            else if (moment < 24700) return 120;
            else return 150;
        }
        else if(val_L <= 290) {
            //moment less than minimum listed
            if (moment < 887) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 1763) return 4;
            else if (moment < 2580) return 8;
            else if (moment < 3430) return 12;
            else if (moment < 4290) return 16;
            else if (moment < 5160) return 20;
            else if (moment < 6310) return 24;
            else if (moment < 8320) return 30;
            else if (moment < 10290) return 40;
            else if (moment < 12210) return 50;
            else if (moment < 14090) return 60;
            else if (moment < 15920) return 70;
            else if (moment < 17710) return 80;
            else if (moment < 19450) return 90;
            else if (moment < 22800) return 100;
            else if (moment < 27200) return 120;
            else return 150;
        }
        else if(val_L <= 300) {
            //moment less than minimum listed
            if (moment < 948) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 1884) return 4;
            else if (moment < 2750) return 8;
            else if (moment < 3680) return 12;
            else if (moment < 4600) return 16;
            else if (moment < 5510) return 20;
            else if (moment < 6760) return 24;
            else if (moment < 8920) return 30;
            else if (moment < 11040) return 40;
            else if (moment < 13110) return 50;
            else if (moment < 15140) return 60;
            else if (moment < 17120) return 70;
            else if (moment < 19060) return 80;
            else if (moment < 21000) return 90;
            else if (moment < 24600) return 100;
            else if (moment < 29400) return 120;
            else return 150;
        }
        //if we get down here, we have a bug
        return -1;
    }

    //Table 4, W Value lookups
    public double table4_W(double moment) {
        //if the span length falls between two values, it is rounded up to the longer length value
        //if moment value falls between two MLC values, it is floored to the more conservative value

        if(val_L <= 4) {
            //moment less than minimum listed
            if (moment < 4.96) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 10.96) return 4;
            else if (moment < 16.00) return 8;
            else if (moment < 20.00) return 12;
            else if (moment < 22.00) return 16;
            else if (moment < 24.00) return 20;
            else if (moment < 26.70) return 24;
            else if (moment < 34.00) return 30;
            else if (moment < 40.00) return 40;
            else if (moment < 46.00) return 50;
            else if (moment < 51.00) return 60;
            else if (moment < 56.00) return 70;
            else if (moment < 60.00) return 80;
            else if (moment < 64.00) return 90;
            else if (moment < 72.00) return 100;
            else if (moment < 84.00) return 120;
            else return 150;
        }
        else if(val_L <= 6) {
            //moment less than minimum listed
            if (moment < 7.44) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 16.44) return 4;
            else if (moment < 24.00) return 8;
            else if (moment < 30.00) return 12;
            else if (moment < 33.00) return 16;
            else if (moment < 36.00) return 20;
            else if (moment < 40.40) return 24;
            else if (moment < 51.00) return 30;
            else if (moment < 60.00) return 40;
            else if (moment < 69.00) return 50;
            else if (moment < 76.40) return 60;
            else if (moment < 84.00) return 70;
            else if (moment < 90.00) return 80;
            else if (moment < 96.00) return 90;
            else if (moment < 108.00) return 100;
            else if (moment < 126.00) return 120;
            else return 150;
        }
        else if(val_L <= 8) {
            //moment less than minimum listed
            if (moment < 9.92) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 21.90) return 4;
            else if (moment < 32.00) return 8;
            else if (moment < 40.00) return 12;
            else if (moment < 44.00) return 16;
            else if (moment < 48.00) return 20;
            else if (moment < 53.90) return 24;
            else if (moment < 68.00) return 30;
            else if (moment < 80.00) return 40;
            else if (moment < 92.00) return 50;
            else if (moment < 101.90) return 60;
            else if (moment < 112.00) return 70;
            else if (moment < 120.00) return 80;
            else if (moment < 128.00) return 90;
            else if (moment < 144.00) return 100;
            else if (moment < 168.00) return 120;
            else return 150;
        }
        else if(val_L <= 10) {
            //moment less than minimum listed
            if (moment < 12.40) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 27.40) return 4;
            else if (moment < 40.00) return 8;
            else if (moment < 50.00) return 12;
            else if (moment < 55.00) return 16;
            else if (moment < 64.00) return 20;
            else if (moment < 70.40) return 24;
            else if (moment < 85.00) return 30;
            else if (moment < 100.00) return 40;
            else if (moment < 115.00) return 50;
            else if (moment < 127.40) return 60;
            else if (moment < 140.00) return 70;
            else if (moment < 151.80) return 80;
            else if (moment < 160.00) return 90;
            else if (moment < 180.00) return 100;
            else if (moment < 210.00) return 120;
            else return 150;
        }
        else if(val_L <= 12) {
            //moment less than minimum listed
            if (moment < 14.88) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 32.90) return 4;
            else if (moment < 48.00) return 8;
            else if (moment < 60.00) return 12;
            else if (moment < 70.80) return 16;
            else if (moment < 83.30) return 20;
            else if (moment < 91.70) return 24;
            else if (moment < 108.30) return 30;
            else if (moment < 125.00) return 40;
            else if (moment < 138.00) return 50;
            else if (moment < 157.90) return 60;
            else if (moment < 180.50) return 70;
            else if (moment < 203.00) return 80;
            else if (moment < 203.00) return 90;
            else if (moment < 243.00) return 100;
            else if (moment < 253.00) return 120;
            else return 150;
        }
        else if(val_L <= 14) {
            //moment less than minimum listed
            if (moment < 17.92) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 38.30) return 4;
            else if (moment < 56.00) return 8;
            else if (moment < 70.00) return 12;
            else if (moment < 87.40) return 16;
            else if (moment < 102.80) return 20;
            else if (moment < 113.10) return 24;
            else if (moment < 133.80) return 30;
            else if (moment < 154.30) return 40;
            else if (moment < 170.00) return 50;
            else if (moment < 198.20) return 60;
            else if (moment < 227.00) return 70;
            else if (moment < 225.00) return 80;
            else if (moment < 259.00) return 90;
            else if (moment < 311.00) return 100;
            else if (moment < 331.00) return 120;
            else return 150;
        }
        else if(val_L <= 16) {
            //moment less than minimum listed
            if (moment < 21.40) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 43.60) return 4;
            else if (moment < 64.00) return 8;
            else if (moment < 80.00) return 12;
            else if (moment < 104.00) return 16;
            else if (moment < 122.60) return 20;
            else if (moment < 134.70) return 24;
            else if (moment < 159.40) return 30;
            else if (moment < 183.70) return 40;
            else if (moment < 205.00) return 50;
            else if (moment < 239.00) return 60;
            else if (moment < 273.00) return 70;
            else if (moment < 308.00) return 80;
            else if (moment < 317.00) return 90;
            else if (moment < 380.00) return 100;
            else if (moment < 410.00) return 120;
            else return 150;
        }
        else if(val_L <= 18) {
            //moment less than minimum listed
            if (moment < 25.60) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 49.30) return 4;
            else if (moment < 72.00) return 8;
            else if (moment < 92.50) return 12;
            else if (moment < 121.00) return 16;
            else if (moment < 142.20) return 20;
            else if (moment < 156.60) return 24;
            else if (moment < 185.00) return 30;
            else if (moment < 213.00) return 40;
            else if (moment < 240.00) return 50;
            else if (moment < 280.00) return 60;
            else if (moment < 320.00) return 70;
            else if (moment < 360.00) return 80;
            else if (moment < 375.00) return 90;
            else if (moment < 450.00) return 100;
            else if (moment < 491.00) return 120;
            else return 150;
        }
        else if(val_L <= 20) {
            //moment less than minimum listed
            if (moment < 30.00) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 54.80) return 4;
            else if (moment < 80.80) return 8;
            else if (moment < 105.20) return 12;
            else if (moment < 137.60) return 16;
            else if (moment < 162.00) return 20;
            else if (moment < 178.00) return 24;
            else if (moment < 210.00) return 30;
            else if (moment < 243.00) return 40;
            else if (moment < 276.00) return 50;
            else if (moment < 322.00) return 60;
            else if (moment < 368.00) return 70;
            else if (moment < 414.00) return 80;
            else if (moment < 434.00) return 90;
            else if (moment < 520.00) return 100;
            else if (moment < 572.00) return 120;
            else return 150;
        }
        else if(val_L <= 25) {
            //moment less than minimum listed
            if (moment < 41.00) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 71.00) return 4;
            else if (moment < 112.50) return 8;
            else if (moment < 144.00) return 12;
            else if (moment < 188.50) return 16;
            else if (moment < 223.00) return 20;
            else if (moment < 246.00) return 24;
            else if (moment < 277.00) return 30;
            else if (moment < 320.00) return 40;
            else if (moment < 365.00) return 50;
            else if (moment < 426.00) return 60;
            else if (moment < 486.00) return 70;
            else if (moment < 547.00) return 80;
            else if (moment < 581.00) return 90;
            else if (moment < 697.00) return 100;
            else if (moment < 777.00) return 120;
            else return 150;
        }
        else if(val_L <= 30) {
            //moment less than minimum listed
            if (moment < 52.20) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 93.60) return 4;
            else if (moment < 145.20) return 8;
            else if (moment < 184.20) return 12;
            else if (moment < 241.00) return 16;
            else if (moment < 285.00) return 20;
            else if (moment < 316.00) return 24;
            else if (moment < 359.00) return 30;
            else if (moment < 415.00) return 40;
            else if (moment < 474.00) return 50;
            else if (moment < 557.00) return 60;
            else if (moment < 636.00) return 70;
            else if (moment < 716.00) return 80;
            else if (moment < 765.00) return 90;
            else if (moment < 918.00) return 100;
            else if (moment < 1032.00) return 120;
            else return 150;
        }
        else if(val_L <= 35) {
            //moment less than minimum listed
            if (moment < 63.70) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 116.20) return 4;
            else if (moment < 180.60) return 8;
            else if (moment < 229.00) return 12;
            else if (moment < 299.00) return 16;
            else if (moment < 353.00) return 20;
            else if (moment < 398.00) return 24;
            else if (moment < 442.00) return 30;
            else if (moment < 511.00) return 40;
            else if (moment < 584.00) return 50;
            else if (moment < 688.00) return 60;
            else if (moment < 786.00) return 70;
            else if (moment < 884.00) return 80;
            else if (moment < 953.00) return 90;
            else if (moment < 1143.00) return 100;
            else if (moment < 1297.00) return 120;
            else return 150;
        }
        else if(val_L <= 40) {
            //moment less than minimum listed
            if (moment < 75.20) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 138.40) return 4;
            else if (moment < 218.00) return 8;
            else if (moment < 275.00) return 12;
            else if (moment < 359.00) return 16;
            else if (moment < 422.00) return 20;
            else if (moment < 482.00) return 24;
            else if (moment < 553.00) return 30;
            else if (moment < 656.00) return 40;
            else if (moment < 740.00) return 50;
            else if (moment < 856.00) return 60;
            else if (moment < 936.00) return 70;
            else if (moment < 1053.00) return 80;
            else if (moment < 1140.00) return 90;
            else if (moment < 1368.00) return 100;
            else if (moment < 1562.00) return 120;
            else return 150;
        }
        else if(val_L <= 45) {
            //moment less than minimum listed
            if (moment < 86.40) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 161.10) return 4;
            else if (moment < 256.00) return 8;
            else if (moment < 321.00) return 12;
            else if (moment < 419.00) return 16;
            else if (moment < 492.00) return 20;
            else if (moment < 567.00) return 24;
            else if (moment < 671.00) return 30;
            else if (moment < 800.00) return 40;
            else if (moment < 914.00) return 50;
            else if (moment < 1057.00) return 60;
            else if (moment < 1103.00) return 70;
            else if (moment < 1242.00) return 80;
            else if (moment < 1328.00) return 90;
            else if (moment < 1593.00) return 100;
            else if (moment < 1827.00) return 120;
            else return 150;
        }
        else if(val_L <= 50) {
            //moment less than minimum listed
            if (moment < 97.00) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 183.00) return 4;
            else if (moment < 293.00) return 8;
            else if (moment < 367.00) return 12;
            else if (moment < 479.00) return 16;
            else if (moment < 562.00) return 20;
            else if (moment < 652.00) return 24;
            else if (moment < 788.00) return 30;
            else if (moment < 945.00) return 40;
            else if (moment < 1089.00) return 50;
            else if (moment < 1257.00) return 60;
            else if (moment < 1332.00) return 70;
            else if (moment < 1499.00) return 80;
            else if (moment < 1543.00) return 90;
            else if (moment < 1851.00) return 100;
            else if (moment < 2092.00) return 120;
            else return 150;
        }
        else if(val_L <= 55) {
            //moment less than minimum listed
            if (moment < 108.90) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 206.00) return 4;
            else if (moment < 331.00) return 8;
            else if (moment < 414.00) return 12;
            else if (moment < 539.00) return 16;
            else if (moment < 633.00) return 20;
            else if (moment < 737.00) return 24;
            else if (moment < 905.00) return 30;
            else if (moment < 1090.00) return 40;
            else if (moment < 1263.00) return 50;
            else if (moment < 1458.00) return 60;
            else if (moment < 1561.00) return 70;
            else if (moment < 1757.00) return 80;
            else if (moment < 1828.00) return 90;
            else if (moment < 2195.00) return 100;
            else if (moment < 2405.00) return 120;
            else return 150;
        }
        else if(val_L <= 60) {
            //moment less than minimum listed
            if (moment < 120.00) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 228.00) return 4;
            else if (moment < 368.00) return 8;
            else if (moment < 460.00) return 12;
            else if (moment < 599.00) return 16;
            else if (moment < 702.00) return 20;
            else if (moment < 822.00) return 24;
            else if (moment < 1022.00) return 30;
            else if (moment < 1235.00) return 40;
            else if (moment < 1438.00) return 50;
            else if (moment < 1658.00) return 60;
            else if (moment < 1790.00) return 70;
            else if (moment < 2010.00) return 80;
            else if (moment < 2110.00) return 90;
            else if (moment < 2540.00) return 100;
            else if (moment < 2830.00) return 120;
            else return 150;
        }
        else if(val_L <= 70) {
            //moment less than minimum listed
            if (moment < 142.80) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 273.00) return 4;
            else if (moment < 444.00) return 8;
            else if (moment < 552.00) return 12;
            else if (moment < 718.00) return 16;
            else if (moment < 843.00) return 20;
            else if (moment < 991.00) return 24;
            else if (moment < 1257.00) return 30;
            else if (moment < 1525.00) return 40;
            else if (moment < 1786.00) return 50;
            else if (moment < 2060.00) return 60;
            else if (moment < 2250.00) return 70;
            else if (moment < 2530.00) return 80;
            else if (moment < 2690.00) return 90;
            else if (moment < 3230.00) return 100;
            else if (moment < 3670.00) return 120;
            else return 150;
        }
        else if(val_L <= 80) {
            //moment less than minimum listed
            if (moment < 164.80) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 318.00) return 4;
            else if (moment < 518.00) return 8;
            else if (moment < 645.00) return 12;
            else if (moment < 838.00) return 16;
            else if (moment < 982.00) return 20;
            else if (moment < 1162.00) return 24;
            else if (moment < 1493.00) return 30;
            else if (moment < 1814.00) return 40;
            else if (moment < 2140.00) return 50;
            else if (moment < 2460.00) return 60;
            else if (moment < 2710.00) return 70;
            else if (moment < 3050.00) return 80;
            else if (moment < 3260.00) return 90;
            else if (moment < 3910.00) return 100;
            else if (moment < 4250.00) return 120;
            else return 150;
        }
        else if(val_L <= 90) {
            //moment less than minimum listed
            if (moment < 187.20) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 364.00) return 4;
            else if (moment < 592.00) return 8;
            else if (moment < 736.00) return 12;
            else if (moment < 958.00) return 16;
            else if (moment < 1121.00) return 20;
            else if (moment < 1130.00) return 24;
            else if (moment < 1728.00) return 30;
            else if (moment < 2100.00) return 40;
            else if (moment < 2490.00) return 50;
            else if (moment < 2870.00) return 60;
            else if (moment < 3170.00) return 70;
            else if (moment < 3560.00) return 80;
            else if (moment < 3830.00) return 90;
            else if (moment < 4600.00) return 100;
            else if (moment < 5560.00) return 120;
            else return 150;
        }
        else if(val_L <= 100) {
            //moment less than minimum listed
            if (moment < 210.00) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 408.00) return 4;
            else if (moment < 668.00) return 8;
            else if (moment < 830.00) return 12;
            else if (moment < 1078.00) return 16;
            else if (moment < 1262.00) return 20;
            else if (moment < 1500.00) return 24;
            else if (moment < 1962.00) return 30;
            else if (moment < 2390.00) return 40;
            else if (moment < 2840.00) return 50;
            else if (moment < 3270.00) return 60;
            else if (moment < 3630.00) return 70;
            else if (moment < 4080.00) return 80;
            else if (moment < 4410.00) return 90;
            else if (moment < 5290.00) return 100;
            else if (moment < 6210.00) return 120;
            else return 150;
        }
        else if(val_L <= 110) {
            //moment less than minimum listed
            if (moment < 223) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 453) return 4;
            else if (moment < 744) return 8;
            else if (moment < 922) return 12;
            else if (moment < 1199) return 16;
            else if (moment < 1401) return 20;
            else if (moment < 1670) return 24;
            else if (moment < 2200) return 30;
            else if (moment < 2680) return 40;
            else if (moment < 3190) return 50;
            else if (moment < 3670) return 60;
            else if (moment < 4090) return 70;
            else if (moment < 4600) return 80;
            else if (moment < 4980) return 90;
            else if (moment < 5980) return 100;
            else if (moment < 7060) return 120;
            else return 150;
        }
        else if(val_L <= 120) {
            //moment less than minimum listed
            if (moment < 254) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 499) return 4;
            else if (moment < 818) return 8;
            else if (moment < 1015) return 12;
            else if (moment < 1318) return 16;
            else if (moment < 1543) return 20;
            else if (moment < 1841) return 24;
            else if (moment < 2430) return 30;
            else if (moment < 2970) return 40;
            else if (moment < 3450) return 50;
            else if (moment < 4070) return 60;
            else if (moment < 4550) return 70;
            else if (moment < 5110) return 80;
            else if (moment < 5560) return 90;
            else if (moment < 6670) return 100;
            else if (moment < 7910) return 120;
            else return 150;
        }
        else if(val_L <= 130) {
            //moment less than minimum listed
            if (moment < 278) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 543) return 4;
            else if (moment < 892) return 8;
            else if (moment < 1108) return 12;
            else if (moment < 1438) return 16;
            else if (moment < 1682) return 20;
            else if (moment < 2010) return 24;
            else if (moment < 2670) return 30;
            else if (moment < 3260) return 40;
            else if (moment < 3880) return 50;
            else if (moment < 4470) return 60;
            else if (moment < 5010) return 70;
            else if (moment < 5630) return 80;
            else if (moment < 6130) return 90;
            else if (moment < 7360) return 100;
            else if (moment < 8760) return 120;
            else return 150;
        }
        else if(val_L <= 140) {
            //moment less than minimum listed
            if (moment < 270) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 588) return 4;
            else if (moment < 969) return 8;
            else if (moment < 1198) return 12;
            else if (moment < 1557) return 16;
            else if (moment < 1823) return 20;
            else if (moment < 2180) return 24;
            else if (moment < 2900) return 30;
            else if (moment < 3550) return 40;
            else if (moment < 4230) return 50;
            else if (moment < 4880) return 60;
            else if (moment < 5460) return 70;
            else if (moment < 6150) return 80;
            else if (moment < 6710) return 90;
            else if (moment < 8050) return 100;
            else if (moment < 9600) return 120;
            else return 150;
        }
        else if(val_L <= 150) {
            //moment less than minimum listed
            if (moment < 321) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 633) return 4;
            else if (moment < 1044) return 8;
            else if (moment < 1293) return 12;
            else if (moment < 1677) return 16;
            else if (moment < 1962) return 20;
            else if (moment < 2350) return 24;
            else if (moment < 3140) return 30;
            else if (moment < 3840) return 40;
            else if (moment < 4580) return 50;
            else if (moment < 5280) return 60;
            else if (moment < 5930) return 70;
            else if (moment < 6670) return 80;
            else if (moment < 7280) return 90;
            else if (moment < 8740) return 100;
            else if (moment < 10450) return 120;
            else return 150;
        }
        else if(val_L <= 160) {
            //moment less than minimum listed
            if (moment < 346) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 678) return 4;
            else if (moment < 1117) return 8;
            else if (moment < 1386) return 12;
            else if (moment < 1798) return 16;
            else if (moment < 2100) return 20;
            else if (moment < 2520) return 24;
            else if (moment < 3370) return 30;
            else if (moment < 4130) return 40;
            else if (moment < 4930) return 50;
            else if (moment < 5680) return 60;
            else if (moment < 6380) return 70;
            else if (moment < 7180) return 80;
            else if (moment < 7860) return 90;
            else if (moment < 9430) return 100;
            else if (moment < 11300) return 120;
            else return 150;
        }
        else if(val_L <= 170) {
            //moment less than minimum listed
            if (moment < 367) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 724) return 4;
            else if (moment < 1193) return 8;
            else if (moment < 1476) return 12;
            else if (moment < 1918) return 16;
            else if (moment < 2240) return 20;
            else if (moment < 2690) return 24;
            else if (moment < 3610) return 30;
            else if (moment < 4420) return 40;
            else if (moment < 5280) return 50;
            else if (moment < 6080) return 60;
            else if (moment < 6840) return 70;
            else if (moment < 7700) return 80;
            else if (moment < 8430) return 90;
            else if (moment < 10120) return 100;
            else if (moment < 12150) return 120;
            else return 150;
        }
        else if(val_L <= 180) {
            //moment less than minimum listed
            if (moment < 389) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 767) return 4;
            else if (moment < 1267) return 8;
            else if (moment < 1570) return 12;
            else if (moment < 2040) return 16;
            else if (moment < 2380) return 20;
            else if (moment < 2860) return 24;
            else if (moment < 3840) return 30;
            else if (moment < 4710) return 40;
            else if (moment < 5630) return 50;
            else if (moment < 6490) return 60;
            else if (moment < 7300) return 70;
            else if (moment < 8220) return 80;
            else if (moment < 9000) return 90;
            else if (moment < 10810) return 100;
            else if (moment < 13000) return 120;
            else return 150;
        }
        else if(val_L <= 190) {
            //moment less than minimum listed
            if (moment < 414) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 813) return 4;
            else if (moment < 1341) return 8;
            else if (moment < 1661) return 12;
            else if (moment < 2160) return 16;
            else if (moment < 2520) return 20;
            else if (moment < 3030) return 24;
            else if (moment < 4080) return 30;
            else if (moment < 5000) return 40;
            else if (moment < 5990) return 50;
            else if (moment < 6890) return 60;
            else if (moment < 7760) return 70;
            else if (moment < 8730) return 80;
            else if (moment < 9580) return 90;
            else if (moment < 11500) return 100;
            else if (moment < 13850) return 120;
            else return 150;
        }
        else if(val_L <= 200) {
            //moment less than minimum listed
            if (moment < 448) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 880) return 4;
            else if (moment < 1416) return 8;
            else if (moment < 1752) return 12;
            else if (moment < 2280) return 16;
            else if (moment < 2660) return 20;
            else if (moment < 3200) return 24;
            else if (moment < 4310) return 30;
            else if (moment < 5290) return 40;
            else if (moment < 6330) return 50;
            else if (moment < 7290) return 60;
            else if (moment < 8820) return 70;
            else if (moment < 9250) return 80;
            else if (moment < 10160) return 90;
            else if (moment < 12180) return 100;
            else if (moment < 14700) return 120;
            else return 150;
        }
        else if(val_L <= 210) {
            //moment less than minimum listed
            if (moment < 491) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 966) return 4;
            else if (moment < 1491) return 8;
            else if (moment < 1848) return 12;
            else if (moment < 2400) return 16;
            else if (moment < 2800) return 20;
            else if (moment < 3370) return 24;
            else if (moment < 4550) return 30;
            else if (moment < 5580) return 40;
            else if (moment < 6680) return 50;
            else if (moment < 7690) return 60;
            else if (moment < 8680) return 70;
            else if (moment < 9770) return 80;
            else if (moment < 10730) return 90;
            else if (moment < 12870) return 100;
            else if (moment < 15550) return 120;
            else return 150;
        }
        else if(val_L <= 220) {
            //moment less than minimum listed
            if (moment < 532) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 1052) return 4;
            else if (moment < 1593) return 8;
            else if (moment < 1958) return 12;
            else if (moment < 2540) return 16;
            else if (moment < 2970) return 20;
            else if (moment < 3590) return 24;
            else if (moment < 4780) return 30;
            else if (moment < 5870) return 40;
            else if (moment < 7030) return 50;
            else if (moment < 8100) return 60;
            else if (moment < 9140) return 70;
            else if (moment < 10290) return 80;
            else if (moment < 11300) return 90;
            else if (moment < 13570) return 100;
            else if (moment < 16400) return 120;
            else return 150;
        }
        else if(val_L <= 230) {
            //moment less than minimum listed
            if (moment < 579) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 1136) return 4;
            else if (moment < 1734) return 8;
            else if (moment < 2130) return 12;
            else if (moment < 2770) return 16;
            else if (moment < 3240) return 20;
            else if (moment < 3910) return 24;
            else if (moment < 5140) return 30;
            else if (moment < 6370) return 40;
            else if (moment < 7410) return 50;
            else if (moment < 8500) return 60;
            else if (moment < 9600) return 70;
            else if (moment < 10810) return 80;
            else if (moment < 11880) return 90;
            else if (moment < 14260) return 100;
            else if (moment < 17250) return 120;
            else return 150;
        }
        else if(val_L <= 240) {
            //moment less than minimum listed
            if (moment < 619) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 1224) return 4;
            else if (moment < 1877) return 8;
            else if (moment < 2390) return 12;
            else if (moment < 3000) return 16;
            else if (moment < 3500) return 20;
            else if (moment < 4240) return 24;
            else if (moment < 5590) return 30;
            else if (moment < 6930) return 40;
            else if (moment < 8070) return 50;
            else if (moment < 9260) return 60;
            else if (moment < 10180) return 70;
            else if (moment < 11450) return 80;
            else if (moment < 12450) return 90;
            else if (moment < 14940) return 100;
            else if (moment < 18100) return 120;
            else return 150;
        }
        else if(val_L <= 250) {
            //moment less than minimum listed
            if (moment < 655) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 1310) return 4;
            else if (moment < 2020) return 8;
            else if (moment < 2490) return 12;
            else if (moment < 3230) return 16;
            else if (moment < 3700) return 20;
            else if (moment < 4570) return 24;
            else if (moment < 6040) return 30;
            else if (moment < 7480) return 40;
            else if (moment < 8740) return 50;
            else if (moment < 10030) return 60;
            else if (moment < 11060) return 70;
            else if (moment < 12450) return 80;
            else if (moment < 13480) return 90;
            else if (moment < 16170) return 100;
            else if (moment < 19300) return 120;
            else return 150;
        }
        else if(val_L <= 260) {
            //moment less than minimum listed
            if (moment < 733) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 1414) return 4;
            else if (moment < 2160) return 8;
            else if (moment < 2660) return 12;
            else if (moment < 3460) return 16;
            else if (moment < 4040) return 20;
            else if (moment < 4890) return 24;
            else if (moment < 6490) return 30;
            else if (moment < 8030) return 40;
            else if (moment < 9410) return 50;
            else if (moment < 10800) return 60;
            else if (moment < 11940) return 70;
            else if (moment < 13440) return 80;
            else if (moment < 14580) return 90;
            else if (moment < 17940) return 100;
            else if (moment < 20900) return 120;
            else return 150;
        }
        else if(val_L <= 270) {
            //moment less than minimum listed
            if (moment < 799) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 1550) return 4;
            else if (moment < 2310) return 8;
            else if (moment < 2840) return 12;
            else if (moment < 3690) return 16;
            else if (moment < 4310) return 20;
            else if (moment < 5220) return 24;
            else if (moment < 6940) return 30;
            else if (moment < 8590) return 40;
            else if (moment < 10050) return 50;
            else if (moment < 11570) return 60;
            else if (moment < 12830) return 70;
            else if (moment < 14430) return 80;
            else if (moment < 15690) return 90;
            else if (moment < 18820) return 100;
            else if (moment < 22500) return 120;
            else return 150;
        }
        else if(val_L <= 280) {
            //moment less than minimum listed
            if (moment < 868) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 1686) return 4;
            else if (moment < 2450) return 8;
            else if (moment < 3020) return 12;
            else if (moment < 3920) return 16;
            else if (moment < 4580) return 20;
            else if (moment < 5550) return 24;
            else if (moment < 7400) return 30;
            else if (moment < 9150) return 40;
            else if (moment < 10760) return 50;
            else if (moment < 12350) return 60;
            else if (moment < 13720) return 70;
            else if (moment < 15440) return 80;
            else if (moment < 16800) return 90;
            else if (moment < 20200) return 100;
            else if (moment < 24200) return 120;
            else return 150;
        }
        else if(val_L <= 290) {
            //moment less than minimum listed
            if (moment < 934) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 1821) return 4;
            else if (moment < 2660) return 8;
            else if (moment < 3290) return 12;
            else if (moment < 4270) return 16;
            else if (moment < 4990) return 20;
            else if (moment < 6020) return 24;
            else if (moment < 7850) return 30;
            else if (moment < 9710) return 40;
            else if (moment < 11430) return 50;
            else if (moment < 13130) return 60;
            else if (moment < 14610) return 70;
            else if (moment < 16440) return 80;
            else if (moment < 17910) return 90;
            else if (moment < 21500) return 100;
            else if (moment < 25800) return 120;
            else return 150;
        }
        else if(val_L <= 300) {
            //moment less than minimum listed
            if (moment < 1002) {
                warnings += "\nMoment values too low.";
                return 0;
            }
            else if (moment < 1956) return 4;
            else if (moment < 2890) return 8;
            else if (moment < 3570) return 12;
            else if (moment < 4630) return 16;
            else if (moment < 5410) return 20;
            else if (moment < 6530) return 24;
            else if (moment < 8310) return 30;
            else if (moment < 10270) return 40;
            else if (moment < 12110) return 50;
            else if (moment < 13910) return 60;
            else if (moment < 15500) return 70;
            else if (moment < 17440) return 80;
            else if (moment < 19030) return 90;
            else if (moment < 22800) return 100;
            else if (moment < 27500) return 120;
            else return 150;
        }

        return -1;
    }
//TABLE 5 -------------------------------------------------------------------------------------
    //Table 5, T Value lookups
    public double table5_T(double shear){
        //if the span length falls between two values, it is rounded up to the longer length value
        //if moment value falls between two MLC values, it is floored to the more conservative value

        if(val_L < 4) {
            //shear less than minimum listed
            if (shear < 2.66) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 4.92) return 4;
            else if (shear < 5.34) return 8;
            else if (shear < 7.12) return 12;
            else if (shear < 8.88) return 16;
            else if (shear < 11.06) return 20;
            else if (shear < 10.92) return 24;
            else if (shear < 13.34) return 30;
            else if (shear < 15.38) return 40;
            else if (shear < 17.14) return 50;
            else if (shear < 18.66) return 60;
            else if (shear < 20.00) return 70;
            else if (shear < 21.18) return 80;
            else if (shear < 22.22) return 90;
            else if (shear < 24.00) return 100;
            else if (shear < 25.00) return 120;
            else return 150;
        }
        else if(val_L <= 6) {
            //shear less than minimum listed
            if (shear < 4.00) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 7.38) return 4;
            else if (shear < 8.00) return 8;
            else if (shear < 10.66) return 12;
            else if (shear < 13.34) return 16;
            else if (shear < 16.00) return 20;
            else if (shear < 16.36) return 24;
            else if (shear < 20.00) return 30;
            else if (shear < 23.08) return 40;
            else if (shear < 25.72) return 50;
            else if (shear < 28.00) return 60;
            else if (shear < 30.00) return 70;
            else if (shear < 31.76) return 80;
            else if (shear < 33.34) return 90;
            else if (shear < 36.00) return 100;
            else if (shear < 37.50) return 120;
            else return 150;
        }
        else if(val_L <= 8) {
            //shear less than minimum listed
            if (shear < 5.00) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 9.50) return 4;
            else if (shear < 10.66) return 8;
            else if (shear < 14.22) return 12;
            else if (shear < 17.78) return 16;
            else if (shear < 21.34) return 20;
            else if (shear < 21.82) return 24;
            else if (shear < 26.66) return 30;
            else if (shear < 30.76) return 40;
            else if (shear < 34.28) return 50;
            else if (shear < 37.34) return 60;
            else if (shear < 40.00) return 70;
            else if (shear < 42.36) return 80;
            else if (shear < 44.44) return 90;
            else if (shear < 48.00) return 100;
            else if (shear < 50.00) return 120;
            else return 150;
        }
        else if(val_L <= 10) {
            //shear less than minimum listed
            if (shear < 5.60) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 10.80) return 4;
            else if (shear < 13.20) return 8;
            else if (shear < 17.60) return 12;
            else if (shear < 22.00) return 16;
            else if (shear < 26.40) return 20;
            else if (shear < 27.28) return 24;
            else if (shear < 33.34) return 30;
            else if (shear < 38.46) return 40;
            else if (shear < 42.86) return 50;
            else if (shear < 46.66) return 60;
            else if (shear < 50.00) return 70;
            else if (shear < 52.94) return 80;
            else if (shear < 55.56) return 90;
            else if (shear < 60.00) return 100;
            else if (shear < 62.50) return 120;
            else return 150;
        }
        else if(val_L <= 12) {
            //shear less than minimum listed
            if (shear < 6.00) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 11.60) return 4;
            else if (shear < 15.00) return 8;
            else if (shear < 20.00) return 12;
            else if (shear < 25.00) return 16;
            else if (shear < 30.00) return 20;
            else if (shear < 32.50) return 24;
            else if (shear < 40.00) return 30;
            else if (shear < 46.16) return 40;
            else if (shear < 51.44) return 50;
            else if (shear < 56.00) return 60;
            else if (shear < 60.00) return 70;
            else if (shear < 63.52) return 80;
            else if (shear < 66.66) return 90;
            else if (shear < 72.00) return 100;
            else if (shear < 75.00) return 120;
            else return 150;
        }
        else if(val_L <= 14) {
            //shear less than minimum listed
            if (shear < 6.28) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 12.28) return 4;
            else if (shear < 16.28) return 8;
            else if (shear < 21.72) return 12;
            else if (shear < 27.14) return 16;
            else if (shear < 32.56) return 20;
            else if (shear < 36.44) return 24;
            else if (shear < 45.72) return 30;
            else if (shear < 53.56) return 40;
            else if (shear < 60.00) return 50;
            else if (shear < 65.34) return 60;
            else if (shear < 70.00) return 70;
            else if (shear < 74.12) return 80;
            else if (shear < 77.78) return 90;
            else if (shear < 84.00) return 100;
            else if (shear < 87.50) return 120;
            else return 150;
        }
        else if(val_L <= 16) {
            //shear less than minimum listed
            if (shear < 6.50) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 12.76) return 4;
            else if (shear < 17.24) return 8;
            else if (shear < 23.00) return 12;
            else if (shear < 28.76) return 16;
            else if (shear < 34.50) return 20;
            else if (shear < 39.38) return 24;
            else if (shear < 50.00) return 30;
            else if (shear < 59.38) return 40;
            else if (shear < 67.50) return 50;
            else if (shear < 74.38) return 60;
            else if (shear < 80.00) return 70;
            else if (shear < 84.70) return 80;
            else if (shear < 88.88) return 90;
            else if (shear < 96.00) return 100;
            else if (shear < 100.00) return 120;
            else return 150;
        }
        else if(val_L <= 18) {
            //shear less than minimum listed
            if (shear < 6.66) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 1.12) return 4;
            else if (shear < 18.00) return 8;
            else if (shear < 24.00) return 12;
            else if (shear < 30.00) return 16;
            else if (shear < 36.00) return 20;
            else if (shear < 41.66) return 24;
            else if (shear < 53.34) return 30;
            else if (shear < 63.88) return 40;
            else if (shear < 73.34) return 50;
            else if (shear < 81.66) return 60;
            else if (shear < 88.88) return 70;
            else if (shear < 95.00) return 80;
            else if (shear < 100.00) return 90;
            else if (shear < 108.00) return 100;
            else if (shear < 112.50) return 120;
            else return 150;
        }
        else if(val_L <= 20) {
            //shear less than minimum listed
            if (shear < 6.80) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 13.40) return 4;
            else if (shear < 18.60) return 8;
            else if (shear < 24.80) return 12;
            else if (shear < 31.00) return 16;
            else if (shear < 37.20) return 20;
            else if (shear < 43.50) return 24;
            else if (shear < 56.00) return 30;
            else if (shear < 67.50) return 40;
            else if (shear < 78.00) return 50;
            else if (shear < 87.50) return 60;
            else if (shear < 96.00) return 70;
            else if (shear < 103.50) return 80;
            else if (shear < 110.00) return 90;
            else if (shear < 120.00) return 100;
            else if (shear < 125.00) return 120;
            else return 150;
        }
        else if(val_L <= 25) {
            //shear less than minimum listed
            if (shear < 7.04) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 13.92) return 4;
            else if (shear < 19.68) return 8;
            else if (shear < 26.24) return 12;
            else if (shear < 32.80) return 16;
            else if (shear < 39.36) return 20;
            else if (shear < 46.80) return 24;
            else if (shear < 60.80) return 30;
            else if (shear < 74.00) return 40;
            else if (shear < 86.40) return 50;
            else if (shear < 98.00) return 60;
            else if (shear < 108.80) return 70;
            else if (shear < 118.80) return 80;
            else if (shear < 128.00) return 90;
            else if (shear < 144.00) return 100;
            else if (shear < 156.00) return 120;
            else return 150;
        }
        else if(val_L <= 30) {
            //shear less than minimum listed
            if (shear < 7.20) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 14.26) return 4;
            else if (shear < 20.40) return 8;
            else if (shear < 27.20) return 12;
            else if (shear < 34.00) return 16;
            else if (shear < 40.80) return 20;
            else if (shear < 49.00) return 24;
            else if (shear < 64.00) return 30;
            else if (shear < 78.34) return 40;
            else if (shear < 92.00) return 50;
            else if (shear < 105.00) return 60;
            else if (shear < 117.34) return 70;
            else if (shear < 129.00) return 80;
            else if (shear < 140.00) return 90;
            else if (shear < 160.00) return 100;
            else if (shear < 180.00) return 120;
            else return 150;
        }
        else if(val_L <= 35) {
            //shear less than minimum listed
            if (shear < 7.32) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 14.52) return 4;
            else if (shear < 20.92) return 8;
            else if (shear < 27.88) return 12;
            else if (shear < 34.86) return 16;
            else if (shear < 41.84) return 20;
            else if (shear < 50.56) return 24;
            else if (shear < 66.28) return 30;
            else if (shear < 81.44) return 40;
            else if (shear < 96.00) return 50;
            else if (shear < 110.00) return 60;
            else if (shear < 123.44) return 70;
            else if (shear < 136.28) return 80;
            else if (shear < 148.56) return 90;
            else if (shear < 171.42) return 100;
            else if (shear < 197.14) return 120;
            else return 150;
        }
        else if(val_L <= 40) {
            //shear less than minimum listed
            if (shear < 7.40) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 14.70) return 4;
            else if (shear < 21.30) return 8;
            else if (shear < 28.40) return 12;
            else if (shear < 35.50) return 16;
            else if (shear < 42.60) return 20;
            else if (shear < 51.76) return 24;
            else if (shear < 68.00) return 30;
            else if (shear < 83.76) return 40;
            else if (shear < 99.00) return 50;
            else if (shear < 113.76) return 60;
            else if (shear < 128.00) return 70;
            else if (shear < 141.76) return 80;
            else if (shear < 155.00) return 90;
            else if (shear < 180.00) return 100;
            else if (shear < 210.00) return 120;
            else return 150;
        }
        else if(val_L <= 45) {
            //shear less than minimum listed
            if (shear < 7.46) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 14.84) return 4;
            else if (shear < 21.60) return 8;
            else if (shear < 28.80) return 12;
            else if (shear < 36.00) return 16;
            else if (shear < 43.20) return 20;
            else if (shear < 52.66) return 24;
            else if (shear < 69.34) return 30;
            else if (shear < 85.56) return 40;
            else if (shear < 101.34) return 50;
            else if (shear < 116.66) return 60;
            else if (shear < 131.56) return 70;
            else if (shear < 146.00) return 80;
            else if (shear < 160.00) return 90;
            else if (shear < 186.66) return 100;
            else if (shear < 220.00) return 120;
            else return 150;
        }
        else if(val_L <= 50) {
            //shear less than minimum listed
            if (shear < 7.52) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 14.96) return 4;
            else if (shear < 21.84) return 8;
            else if (shear < 19.12) return 12;
            else if (shear < 36.40) return 16;
            else if (shear < 43.68) return 20;
            else if (shear < 53.40) return 24;
            else if (shear < 70.40) return 30;
            else if (shear < 87.00) return 40;
            else if (shear < 103.20) return 50;
            else if (shear < 119.00) return 60;
            else if (shear < 134.40) return 70;
            else if (shear < 149.40) return 80;
            else if (shear < 164.00) return 90;
            else if (shear < 192.00) return 100;
            else if (shear < 228.00) return 120;
            else return 150;
        }
        else if(val_L <= 55) {
            //shear less than minimum listed
            if (shear < 7.56) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 15.06) return 4;
            else if (shear < 22.04) return 8;
            else if (shear < 29.38) return 12;
            else if (shear < 36.72) return 16;
            else if (shear < 44.08) return 20;
            else if (shear < 54.00) return 24;
            else if (shear < 71.28) return 30;
            else if (shear < 88.18) return 40;
            else if (shear < 104.72) return 50;
            else if (shear < 120.92) return 60;
            else if (shear < 136.72) return 70;
            else if (shear < 152.18) return 80;
            else if (shear < 167.28) return 90;
            else if (shear < 196.36) return 100;
            else if (shear < 234.60) return 120;
            else return 150;
        }
        else if(val_L <= 60) {
            //shear less than minimum listed
            if (shear < 7.60) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 15.14) return 4;
            else if (shear < 22.20) return 8;
            else if (shear < 29.60) return 12;
            else if (shear < 37.00) return 16;
            else if (shear < 44.40) return 20;
            else if (shear < 54.50) return 24;
            else if (shear < 72.00) return 30;
            else if (shear < 89.16) return 40;
            else if (shear < 106.00) return 50;
            else if (shear < 122.50) return 60;
            else if (shear < 138.66) return 70;
            else if (shear < 154.50) return 80;
            else if (shear < 170.00) return 90;
            else if (shear < 200.00) return 100;
            else if (shear < 240.00) return 120;
            else return 150;
        }
        else if(val_L <= 70) {
            //shear less than minimum listed
            if (shear < 7.66) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 15.26) return 4;
            else if (shear < 22.46) return 8;
            else if (shear < 29.94) return 12;
            else if (shear < 37.44) return 16;
            else if (shear < 44.92) return 20;
            else if (shear < 55.28) return 24;
            else if (shear < 73.14) return 30;
            else if (shear < 90.72) return 40;
            else if (shear < 108.00) return 50;
            else if (shear < 125.00) return 60;
            else if (shear < 141.72) return 70;
            else if (shear < 158.14) return 80;
            else if (shear < 174.28) return 90;
            else if (shear < 205.80) return 100;
            else if (shear < 248.60) return 120;
            else return 150;
        }
        else if(val_L <= 80) {
            //shear less than minimum listed
            if (shear < 7.70) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 15.36) return 4;
            else if (shear < 22.64) return 8;
            else if (shear < 30.20) return 12;
            else if (shear < 37.76) return 16;
            else if (shear < 45.30) return 20;
            else if (shear < 55.88) return 24;
            else if (shear < 74.00) return 30;
            else if (shear < 91.88) return 40;
            else if (shear < 109.50) return 50;
            else if (shear < 126.88) return 60;
            else if (shear < 144.00) return 70;
            else if (shear < 160.88) return 80;
            else if (shear < 177.50) return 90;
            else if (shear < 210.00) return 100;
            else if (shear < 255.00) return 120;
            else return 150;
        }
        else if(val_L <= 90) {
            //shear less than minimum listed
            if (shear < 7.74) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 15.42) return 4;
            else if (shear < 22.80) return 8;
            else if (shear < 30.40) return 12;
            else if (shear < 38.00) return 16;
            else if (shear < 45.60) return 20;
            else if (shear < 56.34) return 24;
            else if (shear < 74.66) return 30;
            else if (shear < 92.78) return 40;
            else if (shear < 110.66) return 50;
            else if (shear < 128.34) return 60;
            else if (shear < 145.78) return 70;
            else if (shear < 163.00) return 80;
            else if (shear < 180.00) return 90;
            else if (shear < 213.40) return 100;
            else if (shear < 260.00) return 120;
            else return 150;
        }
        else if(val_L <= 100) {
            //shear less than minimum listed
            if (shear < 7.76) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 15.48) return 4;
            else if (shear < 22.92) return 8;
            else if (shear < 30.56) return 12;
            else if (shear < 38.20) return 16;
            else if (shear < 45.84) return 20;
            else if (shear < 56.70) return 24;
            else if (shear < 75.20) return 30;
            else if (shear < 93.50) return 40;
            else if (shear < 111.20) return 50;
            else if (shear < 129.50) return 60;
            else if (shear < 174.20) return 70;
            else if (shear < 164.70) return 80;
            else if (shear < 182.00) return 90;
            else if (shear < 216.00) return 100;
            else if (shear < 264.00) return 120;
            else return 150;
        }
        else if(val_L <= 110) {
            //shear less than minimum listed
            if (shear < 7.88) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 15.66) return 4;
            else if (shear < 23.04) return 8;
            else if (shear < 30.70) return 12;
            else if (shear < 38.38) return 16;
            else if (shear < 46.06) return 20;
            else if (shear < 57.00) return 24;
            else if (shear < 75.64) return 30;
            else if (shear < 94.08) return 40;
            else if (shear < 112.36) return 50;
            else if (shear < 130.46) return 60;
            else if (shear < 148.36) return 70;
            else if (shear < 166.08) return 80;
            else if (shear < 183.64) return 90;
            else if (shear < 218.20) return 100;
            else if (shear < 267.20) return 120;
            else return 150;
        }
        else if(val_L <= 120) {
            //shear less than minimum listed
            if (shear < 8.54) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 16.94) return 4;
            else if (shear < 24.40) return 8;
            else if (shear < 32.54) return 12;
            else if (shear < 40.66) return 16;
            else if (shear < 48.80) return 20;
            else if (shear < 59.10) return 24;
            else if (shear < 77.78) return 30;
            else if (shear < 96.16) return 40;
            else if (shear < 114.28) return 50;
            else if (shear < 132.22) return 60;
            else if (shear < 150.00) return 70;
            else if (shear < 167.64) return 80;
            else if (shear < 185.18) return 90;
            else if (shear < 220.00) return 100;
            else if (shear < 270.00) return 120;
            else return 150;
        }
        else if(val_L <= 130) {
            //shear less than minimum listed
            if (shear < 9.12) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 18.10) return 4;
            else if (shear < 26.20) return 8;
            else if (shear < 34.96) return 12;
            else if (shear < 43.70) return 16;
            else if (shear < 52.44) return 20;
            else if (shear < 63.70) return 24;
            else if (shear < 83.70) return 30;
            else if (shear < 103.08) return 40;
            else if (shear < 121.84) return 50;
            else if (shear < 140.00) return 60;
            else if (shear < 157.70) return 70;
            else if (shear < 175.12) return 80;
            else if (shear < 192.30) return 90;
            else if (shear < 226.20) return 100;
            else if (shear < 274.00) return 120;
            else return 150;
        }
        else if(val_L <= 140) {
            //shear less than minimum listed
            if (shear < 9.60) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 19.08) return 4;
            else if (shear < 27.78) return 8;
            else if (shear < 37.02) return 12;
            else if (shear < 46.28) return 16;
            else if (shear <55.54 ) return 20;
            else if (shear < 67.72) return 24;
            else if (shear < 89.14) return 30;
            else if (shear < 110.00) return 40;
            else if (shear < 130.28) return 50;
            else if (shear < 150.00) return 60;
            else if (shear < 169.14) return 70;
            else if (shear < 187.72) return 80;
            else if (shear < 205.80) return 90;
            else if (shear < 240.00) return 100;
            else if (shear < 285.80) return 120;
            else return 150;
        }
        else if(val_L <= 150) {
            //shear less than minimum listed
            if (shear < 10.02) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 19.94) return 4;
            else if (shear < 29.12) return 8;
            else if (shear < 38.82) return 12;
            else if (shear < 48.54) return 16;
            else if (shear < 58.24) return 20;
            else if (shear < 71.20) return 24;
            else if (shear < 93.86) return 30;
            else if (shear < 116.00) return 40;
            else if (shear < 137.60) return 50;
            else if (shear < 158.66) return 60;
            else if (shear < 179.20) return 70;
            else if (shear < 199.20) return 80;
            else if (shear < 218.60) return 90;
            else if (shear < 256.00) return 100;
            else if (shear < 304.00) return 120;
            else return 150;
        }
        else if(val_L <= 160) {
            //shear less than minimum listed
            if (shear < 10.40) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 20.70) return 4;
            else if (shear < 30.30) return 8;
            else if (shear < 40.40) return 12;
            else if (shear < 50.50) return 16;
            else if (shear < 60.60) return 20;
            else if (shear < 74.26) return 24;
            else if (shear < 98.00) return 30;
            else if (shear < 121.26) return 40;
            else if (shear < 144.00) return 50;
            else if (shear < 166.26) return 60;
            else if (shear < 187.78) return 70;
            else if (shear < 209.20) return 80;
            else if (shear < 230.00) return 90;
            else if (shear < 270.00) return 100;
            else if (shear < 322.60) return 120;
            else return 150;
        }
        else if(val_L <= 170) {
            //shear less than minimum listed
            if (shear < 10.72) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 21.36) return 4;
            else if (shear < 31.34) return 8;
            else if (shear < 41.78) return 12;
            else if (shear < 52.25) return 16;
            else if (shear < 62.68) return 20;
            else if (shear < 76.94) return 24;
            else if (shear < 101.64) return 30;
            else if (shear < 125.88) return 40;
            else if (shear < 149.64) return 50;
            else if (shear < 172.94) return 60;
            else if (shear < 195.54) return 70;
            else if (shear < 218.20) return 80;
            else if (shear < 240.00) return 90;
            else if (shear < 282.40) return 100;
            else if (shear < 338.80) return 120;
            else return 150;
        }
        else if(val_L <= 180) {
            //shear less than minimum listed
            if (shear < 11.02) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 21.96) return 4;
            else if (shear < 32.26) return 8;
            else if (shear < 43.02) return 12;
            else if (shear < 53.78) return 16;
            else if (shear < 64.54) return 20;
            else if (shear < 79.34) return 24;
            else if (shear < 104.88) return 30;
            else if (shear < 130.00) return 40;
            else if (shear < 154.66) return 50;
            else if (shear < 178.88) return 60;
            else if (shear < 202.40) return 70;
            else if (shear < 226.00) return 80;
            else if (shear < 248.80) return 90;
            else if (shear < 293.40) return 100;
            else if (shear < 353.40) return 120;
            else return 150;
        }
        else if(val_L <= 190) {
            //shear less than minimum listed
            if (shear < 11.28) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 22.48) return 4;
            else if (shear < 33.10) return 8;
            else if (shear < 44.12) return 12;
            else if (shear < 55.16) return 16;
            else if (shear < 66.18) return 20;
            else if (shear < 81.48) return 24;
            else if (shear < 107.78) return 30;
            else if (shear < 133.68) return 40;
            else if (shear < 159.16) return 50;
            else if (shear < 184.20) return 60;
            else if (shear < 208.60) return 70;
            else if (shear < 233.00) return 80;
            else if (shear < 256.80) return 90;
            else if (shear < 303.20) return 100;
            else if (shear < 366.40) return 120;
            else return 150;
        }
        else if(val_L <= 200) {
            //shear less than minimum listed
            if (shear < 11.52) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 22.96) return 4;
            else if (shear < 33.84) return 8;
            else if (shear < 45.12) return 12;
            else if (shear < 56.40) return 16;
            else if (shear < 67.68) return 20;
            else if (shear < 83.40) return 24;
            else if (shear < 110.40) return 30;
            else if (shear < 137.00) return 40;
            else if (shear < 163.20) return 50;
            else if (shear < 189.00) return 60;
            else if (shear < 214.20) return 70;
            else if (shear < 239.40) return 80;
            else if (shear < 264.00) return 90;
            else if (shear < 312.00) return 100;
            else if (shear < 378.00) return 120;
            else return 150;
        }
        else if(val_L <= 210) {
            //shear less than minimum listed
            if (shear < 11.74) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 23.40) return 4;
            else if (shear < 34.52) return 8;
            else if (shear < 46.02) return 12;
            else if (shear < 57.52) return 16;
            else if (shear < 69.02) return 20;
            else if (shear < 85.14) return 24;
            else if (shear < 112.76) return 30;
            else if (shear < 140.00) return 40;
            else if (shear < 166.86) return 50;
            else if (shear < 193.34) return 60;
            else if (shear < 219.20) return 70;
            else if (shear < 245.20) return 80;
            else if (shear < 370.40) return 90;
            else if (shear < 320.00) return 100;
            else if (shear < 388.60) return 120;
            else return 150;
        }
        else if(val_L <= 220) {
            //shear less than minimum listed
            if (shear < 12.10) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 24.06) return 4;
            else if (shear < 35.16) return 8;
            else if (shear < 46.86) return 12;
            else if (shear < 58.58) return 16;
            else if (shear < 70.30) return 20;
            else if (shear < 86.72) return 24;
            else if (shear < 114.90) return 30;
            else if (shear < 142.72) return 40;
            else if (shear < 170.18) return 50;
            else if (shear < 197.28) return 60;
            else if (shear < 224.00) return 70;
            else if (shear < 250.40) return 80;
            else if (shear < 276.40) return 90;
            else if (shear < 327.20) return 100;
            else if (shear < 398.20) return 120;
            else return 150;
        }
        else if(val_L <= 230) {
            //shear less than minimum listed
            if (shear < 12.62) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 25.10) return 4;
            else if (shear < 36.46) return 8;
            else if (shear < 48.62) return 12;
            else if (shear < 60.78) return 16;
            else if (shear < 72.94) return 20;
            else if (shear < 88.94) return 24;
            else if (shear < 117.40) return 30;
            else if (shear < 145.48) return 40;
            else if (shear < 173.30) return 50;
            else if (shear < 200.80) return 60;
            else if (shear < 228.20) return 70;
            else if (shear < 255.20) return 80;
            else if (shear < 281.80) return 90;
            else if (shear < 334.00) return 100;
            else if (shear < 407.00) return 120;
            else return 150;
        }
        else if(val_L <= 240) {
            //shear less than minimum listed
            if (shear < 13.10) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 26.04) return 4;
            else if (shear < 37.94) return 8;
            else if (shear < 50.60) return 12;
            else if (shear < 63.24) return 16;
            else if (shear < 75.90) return 20;
            else if (shear < 92.62) return 24;
            else if (shear < 122.00) return 30;
            else if (shear < 150.62) return 40;
            else if (shear < 178.58) return 50;
            else if (shear < 206.20) return 60;
            else if (shear < 233.40) return 70;
            else if (shear < 260.20) return 80;
            else if (shear < 287.00) return 90;
            else if (shear < 340.00) return 100;
            else if (shear < 415.00) return 120;
            else return 150;
        }
        else if(val_L <= 250) {
            //shear less than minimum listed
            if (shear < 13.54) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 26.92) return 4;
            else if (shear < 39.32) return 8;
            else if (shear < 52.42) return 12;
            else if (shear < 65.52) return 16;
            else if (shear < 78.62) return 20;
            else if (shear < 96.12) return 24;
            else if (shear < 126.72) return 30;
            else if (shear < 156.60) return 40;
            else if (shear < 185.76) return 50;
            else if (shear < 214.20) return 60;
            else if (shear < 242.00) return 70;
            else if (shear < 269.00) return 80;
            else if (shear < 295.40) return 90;
            else if (shear < 348.00) return 100;
            else if (shear < 422.60) return 120;
            else return 150;
        }
        else if(val_L <= 260) {

            //shear less than minimum listed
            if (shear < 13.94) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 27.74) return 4;
            else if (shear < 40.56) return 8;
            else if (shear < 54.10) return 12;
            else if (shear < 67.62) return 16;
            else if (shear < 81.14) return 20;
            else if (shear < 99.34) return 24;
            else if (shear < 131.08) return 30;
            else if (shear < 162.12) return 40;
            else if (shear < 192.46) return 50;
            else if (shear < 222.20) return 60;
            else if (shear < 251.00) return 70;
            else if (shear < 279.40) return 80;
            else if (shear < 307.00) return 90;
            else if (shear < 360.00) return 100;
            else if (shear < 432.60) return 120;
            else return 150;
        }
        else if(val_L <= 270) {
            //shear less than minimum listed
            if (shear < 14.32) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 28.48) return 4;
            else if (shear < 41.74) return 8;
            else if (shear < 55.64) return 12;
            else if (shear < 69.56) return 16;
            else if (shear < 83.46) return 20;
            else if (shear < 102.34) return 24;
            else if (shear < 135.12) return 30;
            else if (shear < 167.22) return 40;
            else if (shear < 198.66) return 50;
            else if (shear < 229.40) return 60;
            else if (shear < 259.60) return 70;
            else if (shear < 289.00) return 80;
            else if (shear < 317.80) return 90;
            else if (shear < 373.40) return 100;
            else if (shear < 446.80) return 120;
            else return 150;
        }
        else if(val_L <= 280) {
            //shear less than minimum listed
            if (shear < 14.66) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 29.18) return 4;
            else if (shear < 42.82) return 8;
            else if (shear < 57.08) return 12;
            else if (shear < 71.36) return 16;
            else if (shear < 85.62) return 20;
            else if (shear < 105.10) return 24;
            else if (shear < 138.86) return 30;
            else if (shear < 171.96) return 40;
            else if (shear < 204.40) return 50;
            else if (shear < 236.20) return 60;
            else if (shear < 267.40) return 70;
            else if (shear < 298.00) return 80;
            else if (shear < 327.80) return 90;
            else if (shear < 385.80) return 100;
            else if (shear < 462.80) return 120;
            else return 150;
        }
        else if(val_L <= 290) {
            //shear less than minimum listed
            if (shear < 14.98) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 29.84) return 4;
            else if (shear < 43.82) return 8;
            else if (shear < 58.42) return 12;
            else if (shear < 73.04) return 16;
            else if (shear < 87.64) return 20;
            else if (shear < 107.68) return 24;
            else if (shear < 142.34) return 30;
            else if (shear < 176.38) return 40;
            else if (shear < 209.80) return 50;
            else if (shear < 242.60) return 60;
            else if (shear < 274.80) return 70;
            else if (shear < 306.40) return 80;
            else if (shear < 337.20) return 90;
            else if (shear < 397.20) return 100;
            else if (shear < 478.00) return 120;
            else return 150;
        }
        else if(val_L <= 300) {
            //shear less than minimum listed
            if (shear < 15.28) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 30.44) return 4;
            else if (shear < 44.76) return 8;
            else if (shear < 59.68) return 12;
            else if (shear < 74.60) return 16;
            else if (shear < 89.52) return 20;
            else if (shear < 110.10) return 24;
            else if (shear < 145.60) return 30;
            else if (shear < 180.50) return 40;
            else if (shear < 214.80) return 50;
            else if (shear < 248.60) return 60;
            else if (shear < 281.60) return 70;
            else if (shear < 314.20) return 80;
            else if (shear < 346.00) return 90;
            else if (shear < 408.00) return 100;
            else if (shear < 492.00) return 120;
            else return 150;
        }

        return -1;
    }

    //Table 5, W Value lookups
    public double table5_W(double shear) {
        //if the span length falls between two values, it is rounded up to the longer length value
        //if moment value falls between two MLC values, it is floored to the more conservative value

        if(val_L <= 4) {
            //shear less than minimum listed
            if (shear < 5.00) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 11.00) return 4;
            else if (shear < 16.00) return 8;
            else if (shear < 20.00) return 12;
            else if (shear < 22.00) return 16;
            else if (shear < 24.00) return 20;
            else if (shear < 27.00) return 24;
            else if (shear < 34.00) return 30;
            else if (shear < 40.00) return 40;
            else if (shear < 46.00) return 50;
            else if (shear < 51.00) return 60;
            else if (shear < 56.00) return 70;
            else if (shear < 60.00) return 80;
            else if (shear < 64.00) return 90;
            else if (shear < 72.00) return 100;
            else if (shear < 84.00) return 120;
            else return 150;
        }
        else if(val_L <= 6) {
            //shear less than minimum listed
            if (shear < 5.00) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 11.00) return 4;
            else if (shear < 16.00) return 8;
            else if (shear < 20.00) return 12;
            else if (shear < 22.66) return 16;
            else if (shear < 26.66) return 20;
            else if (shear < 29.34) return 24;
            else if (shear < 34.66) return 30;
            else if (shear < 40.00) return 40;
            else if (shear < 46.00) return 50;
            else if (shear < 51.00) return 60;
            else if (shear < 56.00) return 70;
            else if (shear < 63.00) return 80;
            else if (shear < 64.00) return 90;
            else if (shear < 72.00) return 100;
            else if (shear < 84.00) return 120;
            else return 150;
        }
        else if(val_L <= 8) {
            //shear less than minimum listed
            if (shear < 5.26) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 11.00) return 4;
            else if (shear < 16.00) return 8;
            else if (shear < 20.00) return 12;
            else if (shear < 25.50) return 16;
            else if (shear < 30.00) return 20;
            else if (shear < 33.00) return 24;
            else if (shear < 39.00) return 30;
            else if (shear < 45.00) return 40;
            else if (shear < 49.50) return 50;
            else if (shear < 57.76) return 60;
            else if (shear < 66.00) return 70;
            else if (shear < 74.26) return 80;
            else if (shear < 75.00) return 90;
            else if (shear < 90.00) return 100;
            else if (shear < 94.50) return 120;
            else return 150;
        }
        else if(val_L <= 10) {
            //shear less than minimum listed
            if (shear < 5.60) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 11.00) return 4;
            else if (shear < 16.00) return 8;
            else if (shear < 20.80) return 12;
            else if (shear < 27.20) return 16;
            else if (shear < 32.00) return 20;
            else if (shear < 35.20) return 24;
            else if (shear < 41.60) return 30;
            else if (shear < 48.00) return 40;
            else if (shear < 54.00) return 50;
            else if (shear < 63.00) return 60;
            else if (shear < 72.00) return 70;
            else if (shear < 81.00) return 80;
            else if (shear < 84.00) return 90;
            else if (shear < 100.80) return 100;
            else if (shear < 109.20) return 120;
            else return 150;
        }
        else if(val_L <= 12) {
            //shear less than minimum listed
            if (shear < 5.84) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 11.00) return 4;
            else if (shear < 16.66) return 8;
            else if (shear < 21.66) return 12;
            else if (shear < 28.34) return 16;
            else if (shear < 33.34) return 20;
            else if (shear < 36.66) return 24;
            else if (shear < 43.34) return 30;
            else if (shear < 50.00) return 40;
            else if (shear < 57.00) return 50;
            else if (shear < 66.50) return 60;
            else if (shear < 76.00) return 70;
            else if (shear < 85.50) return 80;
            else if (shear < 90.00) return 90;
            else if (shear < 108.00) return 100;
            else if (shear < 119.00) return 120;
            else return 150;
        }
        else if(val_L <= 14) {
            //shear less than minimum listed
            if (shear < 6.28) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 11.00) return 4;
            else if (shear < 17.14) return 8;
            else if (shear < 22.28) return 12;
            else if (shear < 29.14) return 16;
            else if (shear < 34.28) return 20;
            else if (shear < 37.72) return 24;
            else if (shear < 44.58) return 30;
            else if (shear < 51.42) return 40;
            else if (shear < 59.14) return 50;
            else if (shear < 69.00) return 60;
            else if (shear < 78.86) return 70;
            else if (shear < 88.72) return 80;
            else if (shear < 94.28) return 90;
            else if (shear < 113.14) return 100;
            else if (shear < 126.00) return 120;
            else return 150;
        }
        else if(val_L <= 16) {

            //shear less than minimum listed
            if (shear < 6.62) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 11.26) return 4;
            else if (shear < 18.26) return 8;
            else if (shear < 23.50) return 12;
            else if (shear < 30.76) return 16;
            else if (shear < 36.26) return 20;
            else if (shear < 40.00) return 24;
            else if (shear < 45.50) return 30;
            else if (shear < 52.50) return 40;
            else if (shear < 60.76) return 50;
            else if (shear < 70.88) return 60;
            else if (shear < 81.00) return 70;
            else if (shear < 91.12) return 80;
            else if (shear < 97.50) return 90;
            else if (shear < 77.00) return 100;
            else if (shear < 131.26) return 120;
            else return 150;
        }
        else if(val_L <= 18) {
            //shear less than minimum listed
            if (shear < 6.88) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 12.00) return 4;
            else if (shear < 19.12) return 8;
            else if (shear < 24.44) return 12;
            else if (shear < 32.00) return 16;
            else if (shear < 37.78) return 20;
            else if (shear < 41.78) return 24;
            else if (shear < 47.78) return 30;
            else if (shear < 55.12) return 40;
            else if (shear < 62.88) return 50;
            else if (shear < 73.50) return 60;
            else if (shear < 84.00) return 70;
            else if (shear < 94.50) return 80;
            else if (shear < 100.00) return 90;
            else if (shear < 120.00) return 100;
            else if (shear < 135.34) return 120;
            else return 150;
        }
        else if(val_L <= 20) {
            //shear less than minimum listed
            if (shear < 7.10) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 12.60) return 4;
            else if (shear < 19.80) return 8;
            else if (shear < 25.20) return 12;
            else if (shear < 33.00) return 16;
            else if (shear < 39.00) return 20;
            else if (shear < 43.20) return 24;
            else if (shear < 49.60) return 30;
            else if (shear < 57.20) return 40;
            else if (shear < 65.40) return 50;
            else if (shear < 76.66) return 60;
            else if (shear < 87.60) return 70;
            else if (shear < 98.56) return 80;
            else if (shear < 105.00) return 90;
            else if (shear < 126.00) return 100;
            else if (shear < 140.80) return 120;
            else return 150;
        }
        else if(val_L <= 25) {
            //shear less than minimum listed
            if (shear < 7.48) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 13.68) return 4;
            else if (shear < 21.04) return 8;
            else if (shear < 26.56) return 12;
            else if (shear < 34.80) return 16;
            else if (shear < 41.20) return 20;
            else if (shear < 45.76) return 24;
            else if (shear < 53.44) return 30;
            else if (shear < 63.20) return 40;
            else if (shear < 71.04) return 50;
            else if (shear < 82.32) return 60;
            else if (shear < 94.08) return 70;
            else if (shear < 105.84) return 80;
            else if (shear < 114.00) return 90;
            else if (shear < 136.80) return 100;
            else if (shear < 155.04) return 120;
            else return 150;
        }
        else if(val_L <= 30) {
            //shear less than minimum listed
            if (shear < 7.74) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 14.40) return 4;
            else if (shear < 21.86) return 8;
            else if (shear < 27.46) return 12;
            else if (shear < 36.00) return 16;
            else if (shear < 42.66) return 20;
            else if (shear < 47.46) return 24;
            else if (shear < 57.86) return 30;
            else if (shear < 69.34) return 40;
            else if (shear < 79.86) return 50;
            else if (shear < 91.94) return 60;
            else if (shear < 98.40) return 70;
            else if (shear < 110.70) return 80;
            else if (shear < 120.04) return 90;
            else if (shear < 144.04) return 100;
            else if (shear < 165.96) return 120;
            else return 150;
        }
        else if(val_L <= 35) {
            //shear less than minimum listed
            if (shear < 7.92) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 14.92) return 4;
            else if (shear < 22.46) return 8;
            else if (shear < 28.12) return 12;
            else if (shear < 36.86) return 16;
            else if (shear < 43.72) return 20;
            else if (shear < 46.68) return 24;
            else if (shear < 61.02) return 30;
            else if (shear < 73.72) return 40;
            else if (shear < 84.18) return 50;
            else if (shear < 98.80) return 60;
            else if (shear < 105.52) return 70;
            else if (shear < 119.82) return 80;
            else if (shear < 129.14) return 90;
            else if (shear < 154.98) return 100;
            else if (shear < 171.32) return 120;
            else return 150;
        }
        else if(val_L <= 40) {

            //shear less than minimum listed
            if (shear < 8.06) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 15.30) return 4;
            else if (shear < 22.90) return 8;
            else if (shear < 28.60) return 12;
            else if (shear < 37.50) return 16;
            else if (shear < 44.50) return 20;
            else if (shear < 49.60) return 24;
            else if (shear < 63.40) return 30;
            else if (shear < 77.00) return 40;
            else if (shear < 90.90) return 50;
            else if (shear < 103.96) return 60;
            else if (shear < 113.20) return 70;
            else if (shear < 127.36) return 80;
            else if (shear < 138.00) return 90;
            else if (shear < 165.00) return 100;
            else if (shear < 178.90) return 120;
            else return 150;
        }
        else if(val_L <= 45) {
            //shear less than minimum listed
            if (shear < 8.16) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 15.60) return 4;
            else if (shear < 23.24) return 8;
            else if (shear < 28.98) return 12;
            else if (shear < 38.00) return 16;
            else if (shear < 45.12) return 20;
            else if (shear < 50.32) return 24;
            else if (shear < 65.24) return 30;
            else if (shear < 80.62) return 40;
            else if (shear < 94.58) return 50;
            else if (shear < 107.96) return 60;
            else if (shear < 118.40) return 70;
            else if (shear < 133.20) return 80;
            else if (shear < 144.88) return 90;
            else if (shear < 173.86) return 100;
            else if (shear < 191.52) return 120;
            else return 150;
        }
        else if(val_L <= 50) {
            //shear less than minimum listed
            if (shear < 8.24) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 15.84) return 4;
            else if (shear < 23.52) return 8;
            else if (shear < 29.28) return 12;
            else if (shear < 38.40) return 16;
            else if (shear < 45.60) return 20;
            else if (shear < 51.20) return 24;
            else if (shear < 66.72) return 30;
            else if (shear < 84.16) return 40;
            else if (shear < 97.52) return 50;
            else if (shear < 111.16) return 60;
            else if (shear < 122.56) return 70;
            else if (shear < 137.88) return 80;
            else if (shear < 150.40) return 90;
            else if (shear < 180.48) return 100;
            else if (shear < 202.40) return 120;
            else return 150;
        }
        else if(val_L <= 55) {
            //shear less than minimum listed
            if (shear < 8.30) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 16.04) return 4;
            else if (shear < 23.74) return 8;
            else if (shear < 29.52) return 12;
            else if (shear < 38.72) return 16;
            else if (shear < 46.00) return 20;
            else if (shear < 52.72) return 24;
            else if (shear < 68.84) return 30;
            else if (shear < 87.06) return 40;
            else if (shear < 99.92) return 50;
            else if (shear < 113.78) return 60;
            else if (shear < 125.96) return 70;
            else if (shear < 141.70) return 80;
            else if (shear < 154.90) return 90;
            else if (shear < 185.88) return 100;
            else if (shear < 210.80) return 120;
            else return 150;
        }
        else if(val_L <= 60) {
            //shear less than minimum listed
            if (shear < 8.36) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 16.20) return 4;
            else if (shear < 24.26) return 8;
            else if (shear < 29.74) return 12;
            else if (shear < 39.00) return 16;
            else if (shear < 46.34) return 20;
            else if (shear < 54.00) return 24;
            else if (shear < 70.94) return 30;
            else if (shear < 89.46) return 40;
            else if (shear < 102.86) return 50;
            else if (shear < 116.44) return 60;
            else if (shear < 128.80) return 70;
            else if (shear < 144.90) return 80;
            else if (shear < 158.66) return 90;
            else if (shear < 190.40) return 100;
            else if (shear < 218.00) return 120;
            else return 150;
        }
        else if(val_L <= 70) {
            //shear less than minimum listed
            if (shear < 8.46) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 16.46) return 4;
            else if (shear < 25.08) return 8;
            else if (shear < 30.68) return 12;
            else if (shear < 39.94) return 16;
            else if (shear < 46.92) return 20;
            else if (shear < 56.00) return 24;
            else if (shear < 74.22) return 30;
            else if (shear < 93.26) return 40;
            else if (shear < 108.18) return 50;
            else if (shear < 122.80) return 60;
            else if (shear < 133.26) return 70;
            else if (shear < 149.92) return 80;
            else if (shear < 164.58) return 90;
            else if (shear < 197.48) return 100;
            else if (shear < 229.40) return 120;
            else return 150;
        }
        else if(val_L <= 80) {
            //shear less than minimum listed
            if (shear < 8.52) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 16.66) return 4;
            else if (shear < 25.70) return 8;
            else if (shear < 31.48) return 12;
            else if (shear < 40.96) return 16;
            else if (shear < 48.06) return 20;
            else if (shear < 57.50) return 24;
            else if (shear < 76.70) return 30;
            else if (shear < 96.10) return 40;
            else if (shear < 112.16) return 50;
            else if (shear < 127.58) return 60;
            else if (shear < 139.40) return 70;
            else if (shear < 156.82) return 80;
            else if (shear < 169.38) return 90;
            else if (shear < 203.20) return 100;
            else if (shear < 243.20) return 120;
            else return 150;
        }
        else if(val_L <= 90) {
            //shear less than minimum listed
            if (shear < 8.58) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 16.80) return 4;
            else if (shear < 26.18) return 8;
            else if (shear < 32.08) return 12;
            else if (shear < 41.74) return 16;
            else if (shear < 48.94) return 20;
            else if (shear < 58.66) return 24;
            else if (shear < 78.62) return 30;
            else if (shear < 98.32) return 40;
            else if (shear < 115.24) return 50;
            else if (shear < 131.28) return 60;
            else if (shear < 144.36) return 70;
            else if (shear < 162.40) return 80;
            else if (shear < 176.12) return 90;
            else if (shear < 211.40) return 100;
            else if (shear < 254.00) return 120;
            else return 150;
        }
        else if(val_L <= 100) {
            //shear less than minimum listed
            if (shear < 8.62) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 16.92) return 4;
            else if (shear < 26.56) return 8;
            else if (shear < 32.58) return 12;
            else if (shear < 45.26) return 16;
            else if (shear < 49.64) return 20;
            else if (shear < 59.60) return 24;
            else if (shear < 80.16) return 30;
            else if (shear < 100.08) return 40;
            else if (shear < 117.72) return 50;
            else if (shear < 134.26) return 60;
            else if (shear < 148.32) return 70;
            else if (shear < 166.86) return 80;
            else if (shear < 181.50) return 90;
            else if (shear < 217.80) return 100;
            else if (shear < 252.60) return 120;
            else return 150;
        }
        else if(val_L <= 110) {

            //shear less than minimum listed
            if (shear < 8.66) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 17.02) return 4;
            else if (shear < 26.88) return 8;
            else if (shear < 33.00) return 12;
            else if (shear < 42.88) return 16;
            else if (shear < 50.22) return 20;
            else if (shear < 60.36) return 24;
            else if (shear < 81.42) return 30;
            else if (shear < 101.52) return 40;
            else if (shear < 119.74) return 50;
            else if (shear < 136.70) return 60;
            else if (shear < 151.56) return 70;
            else if (shear < 170.50) return 80;
            else if (shear < 185.90) return 90;
            else if (shear < 223.00) return 100;
            else if (shear < 269.60) return 120;
            else return 150;
        }
        else if(val_L <= 120) {
            //shear less than minimum listed
            if (shear < 9.04) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 17.50) return 4;
            else if (shear < 27.14) return 8;
            else if (shear < 33.30) return 12;
            else if (shear < 43.30) return 16;
            else if (shear < 50.70) return 20;
            else if (shear < 61.00) return 24;
            else if (shear < 82.46) return 30;
            else if (shear < 102.74) return 40;
            else if (shear < 121.42) return 50;
            else if (shear < 138.72) return 60;
            else if (shear < 154.26) return 70;
            else if (shear < 173.54) return 80;
            else if (shear < 189.58) return 90;
            else if (shear < 227.60) return 100;
            else if (shear < 275.40) return 120;
            else return 150;
        }
        else if(val_L <= 130) {
            //shear less than minimum listed
            if (shear < 9.66) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 18.56) return 4;
            else if (shear < 27.54) return 8;
            else if (shear < 33.78) return 12;
            else if (shear < 43.90) return 16;
            else if (shear < 51.42) return 20;
            else if (shear < 61.90) return 24;
            else if (shear < 83.36) return 30;
            else if (shear < 103.76) return 40;
            else if (shear < 122.86) return 50;
            else if (shear < 140.44) return 60;
            else if (shear < 156.56) return 70;
            else if (shear < 176.12) return 80;
            else if (shear < 192.70) return 90;
            else if (shear < 231.20) return 100;
            else if (shear < 280.40) return 120;
            else return 150;
        }
        else if(val_L <= 140) {
            //shear less than minimum listed
            if (shear < 10.26) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 19.80) return 4;
            else if (shear < 28.42) return 8;
            else if (shear < 34.82) return 12;
            else if (shear < 45.26) return 16;
            else if (shear < 53.02) return 20;
            else if (shear < 63.82) return 24;
            else if (shear < 85.72) return 30;
            else if (shear < 106.92) return 40;
            else if (shear < 124.82) return 50;
            else if (shear < 142.70) return 60;
            else if (shear < 158.52) return 70;
            else if (shear < 178.32) return 80;
            else if (shear < 195.36) return 90;
            else if (shear < 234.40) return 100;
            else if (shear < 285.80) return 120;
            else return 150;
        }
        else if(val_L <= 150) {
            //shear less than minimum listed
            if (shear < 10.78) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 20.88) return 4;
            else if (shear < 30.26) return 8;
            else if (shear < 37.10) return 12;
            else if (shear < 48.24) return 16;
            else if (shear < 56.56) return 20;
            else if (shear < 67.84) return 24;
            else if (shear < 88.48) return 30;
            else if (shear < 110.58) return 40;
            else if (shear < 127.14) return 50;
            else if (shear < 147.76) return 60;
            else if (shear < 163.42) return 70;
            else if (shear < 183.84) return 80;
            else if (shear < 200.00) return 90;
            else if (shear < 240.00) return 100;
            else if (shear < 289.60) return 120;
            else return 150;
        }
        else if(val_L <= 160) {
            //shear less than minimum listed
            if (shear < 11.22) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 21.82) return 4;
            else if (shear < 32.08) return 8;
            else if (shear < 39.34) return 12;
            else if (shear < 51.16) return 16;
            else if (shear < 59.96) return 20;
            else if (shear < 71.96) return 24;
            else if (shear < 93.50) return 30;
            else if (shear < 116.80) return 40;
            else if (shear < 134.36) return 50;
            else if (shear < 153.30) return 60;
            else if (shear < 168.70) return 70;
            else if (shear < 189.78) return 80;
            else if (shear < 207.00) return 90;
            else if (shear < 248.40) return 100;
            else if (shear < 299.60) return 120;
            else return 150;
        }
        else if(val_L <= 170) {

            //shear less than minimum listed
            if (shear < 11.62) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 22.66) return 4;
            else if (shear < 33.72) return 8;
            else if (shear < 41.38) return 12;
            else if (shear < 53.78) return 16;
            else if (shear < 63.02) return 20;
            else if (shear < 74.72) return 24;
            else if (shear < 98.72) return 30;
            else if (shear < 123.20) return 40;
            else if (shear < 141.98) return 50;
            else if (shear < 161.98) return 60;
            else if (shear < 175.90) return 70;
            else if (shear < 197.70) return 80;
            else if (shear < 213.80) return 90;
            else if (shear < 256.40) return 100;
            else if (shear < 309.60) return 120;
            else return 150;
        }
        else if(val_L <= 180) {
            //shear less than minimum listed
            if (shear < 11.98) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 23.40) return 4;
            else if (shear < 35.18) return 8;
            else if (shear < 43.18) return 12;
            else if (shear < 56.14) return 16;
            else if (shear < 65.74) return 20;
            else if (shear < 79.06) return 24;
            else if (shear < 103.68) return 30;
            else if (shear < 129.24) return 40;
            else if (shear < 149.48) return 50;
            else if (shear < 170.62) return 60;
            else if (shear < 185.24) return 70;
            else if (shear < 208.40) return 80;
            else if (shear < 224.40) return 90;
            else if (shear < 269.20) return 100;
            else if (shear < 320.60) return 120;
            else return 150;
        }
        else if(val_L <= 190) {
            //shear less than minimum listed
            if (shear < 12.30) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 24.06) return 4;
            else if (shear < 36.48) return 8;
            else if (shear < 44.82) return 12;
            else if (shear < 58.24) return 16;
            else if (shear < 67.34) return 20;
            else if (shear < 82.06) return 24;
            else if (shear < 108.12) return 30;
            else if (shear < 134.66) return 40;
            else if (shear < 156.34) return 50;
            else if (shear < 178.62) return 60;
            else if (shear < 194.86) return 70;
            else if (shear < 219.20) return 80;
            else if (shear < 235.80) return 90;
            else if (shear < 283.00) return 100;
            else if (shear < 336.40) return 120;
            else return 150;
        }
        else if(val_L <= 200) {
            //shear less than minimum listed
            if (shear < 12.58) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 24.66) return 4;
            else if (shear < 37.66) return 8;
            else if (shear < 46.28) return 12;
            else if (shear < 60.12) return 16;
            else if (shear < 70.36) return 20;
            else if (shear < 84.76) return 24;
            else if (shear < 112.12) return 30;
            else if (shear < 139.52) return 40;
            else if (shear < 162.52) return 50;
            else if (shear < 185.78) return 60;
            else if (shear < 203.60) return 70;
            else if (shear < 229.00) return 80;
            else if (shear < 247.00) return 90;
            else if (shear < 296.40) return 100;
            else if (shear < 352.60) return 120;
            else return 150;
        }
        else if(val_L <= 210) {
            //shear less than minimum listed
            if (shear < 12.84) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 25.20) return 4;
            else if (shear < 38.72) return 8;
            else if (shear < 47.60) return 12;
            else if (shear < 61.82) return 16;
            else if (shear < 72.34) return 20;
            else if (shear < 87.20) return 24;
            else if (shear < 115.74) return 30;
            else if (shear < 143.92) return 40;
            else if (shear < 168.12) return 50;
            else if (shear < 192.26) return 60;
            else if (shear < 211.40) return 70;
            else if (shear < 237.80) return 80;
            else if (shear < 257.20) return 90;
            else if (shear < 308.60) return 100;
            else if (shear < 368.20) return 120;
            else return 150;
        }
        else if(val_L <= 220) {
            //shear less than minimum listed
            if (shear < 13.08) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 25.68) return 4;
            else if (shear < 39.70) return 8;
            else if (shear < 48.80) return 12;
            else if (shear < 63.38) return 16;
            else if (shear < 74.14) return 20;
            else if (shear < 89.42) return 24;
            else if (shear < 119.02) return 30;
            else if (shear < 147.92) return 40;
            else if (shear < 173.20) return 50;
            else if (shear < 198.16) return 60;
            else if (shear < 218.40) return 70;
            else if (shear < 245.80) return 80;
            else if (shear < 266.40) return 90;
            else if (shear < 319.60) return 100;
            else if (shear < 382.40) return 120;
            else return 150;
        }
        else if(val_L <= 230) {
            //shear less than minimum listed
            if (shear < 13.40) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 26.20) return 4;
            else if (shear < 40.58) return 8;
            else if (shear < 49.88) return 12;
            else if (shear < 64.80) return 16;
            else if (shear < 75.80) return 20;
            else if (shear < 91.44) return 24;
            else if (shear < 122.02) return 30;
            else if (shear < 151.58) return 40;
            else if (shear < 177.84) return 50;
            else if (shear < 203.60) return 60;
            else if (shear < 225.00) return 70;
            else if (shear < 253.20) return 80;
            else if (shear < 274.80) return 90;
            else if (shear < 329.80) return 100;
            else if (shear < 395.54) return 120;
            else return 150;
        }
        else if(val_L <= 240) {
            //shear less than minimum listed
            if (shear < 13.92) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 27.06) return 4;
            else if (shear < 41.38) return 8;
            else if (shear < 50.90) return 12;
            else if (shear < 66.10) return 16;
            else if (shear < 77.30) return 20;
            else if (shear < 93.30) return 24;
            else if (shear < 124.76) return 30;
            else if (shear < 154.94) return 40;
            else if (shear < 182.10) return 50;
            else if (shear < 208.40) return 60;
            else if (shear < 231.00) return 70;
            else if (shear < 259.80) return 80;
            else if (shear < 282.60) return 90;
            else if (shear < 339.00) return 100;
            else if (shear < 407.20) return 120;
            else return 150;
        }
        else if(val_L <= 250) {
            //shear less than minimum listed
            if (shear < 14.44) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 28.08) return 4;
            else if (shear < 42.12) return 8;
            else if (shear < 51.82) return 12;
            else if (shear < 67.30) return 16;
            else if (shear < 78.68) return 20;
            else if (shear < 95.00) return 24;
            else if (shear < 127.30) return 30;
            else if (shear < 158.02) return 40;
            else if (shear < 186.02) return 50;
            else if (shear < 213.00) return 60;
            else if (shear < 236.40) return 70;
            else if (shear < 266.00) return 80;
            else if (shear < 289.60) return 90;
            else if (shear < 347.60) return 100;
            else if (shear < 418.20) return 120;
            else return 150;
        }
        else if(val_L <= 260) {
            //shear less than minimum listed
            if (shear < 14.94) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 29.08) return 4;
            else if (shear < 43.00) return 8;
            else if (shear < 52.86) return 12;
            else if (shear < 68.64) return 16;
            else if (shear < 80.28) return 20;
            else if (shear < 96.96) return 24;
            else if (shear < 129.64) return 30;
            else if (shear < 160.86) return 40;
            else if (shear < 189.64) return 50;
            else if (shear < 217.20) return 60;
            else if (shear < 241.40) return 70;
            else if (shear < 271.60) return 80;
            else if (shear < 296.20) return 90;
            else if (shear < 355.40) return 100;
            else if (shear < 428.80) return 120;
            else return 150;
        }
        else if(val_L <= 270) {
            //shear less than minimum listed
            if (shear < 15.38) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 30.00) return 4;
            else if (shear < 44.30) return 8;
            else if (shear < 54.44) return 12;
            else if (shear < 70.72) return 16;
            else if (shear < 82.72) return 20;
            else if (shear < 99.82) return 24;
            else if (shear < 132.42) return 30;
            else if (shear < 164.38) return 40;
            else if (shear < 192.98) return 50;
            else if (shear < 221.20) return 60;
            else if (shear < 246.20) return 70;
            else if (shear < 277.00) return 80;
            else if (shear < 302.20) return 90;
            else if (shear < 362.80) return 100;
            else if (shear < 437.60) return 120;
            else return 150;
        }
        else if(val_L <= 280) {
            //shear less than minimum listed
            if (shear < 15.80) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 30.86) return 4;
            else if (shear < 45.82) return 8;
            else if (shear < 56.32) return 12;
            else if (shear < 73.16) return 16;
            else if (shear < 85.58) return 20;
            else if (shear < 103.20) return 24;
            else if (shear < 135.40) return 30;
            else if (shear < 168.22) return 40;
            else if (shear < 197.20) return 50;
            else if (shear < 226.00) return 60;
            else if (shear < 250.60) return 70;
            else if (shear < 281.80) return 80;
            else if (shear < 307.80) return 90;
            else if (shear < 369.40) return 100;
            else if (shear < 446.20) return 120;
            else return 150;
        }
        else if(val_L <= 290) {
            //shear less than minimum listed
            if (shear < 16.18) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 31.66) return 4;
            else if (shear < 47.34) return 8;
            else if (shear < 58.20) return 12;
            else if (shear < 75.60) return 16;
            else if (shear < 88.42) return 20;
            else if (shear < 106.68) return 24;
            else if (shear < 139.62) return 30;
            else if (shear < 173.46) return 40;
            else if (shear < 201.84) return 50;
            else if (shear < 231.20) return 60;
            else if (shear < 256.20) return 70;
            else if (shear < 288.20) return 80;
            else if (shear < 313.60) return 90;
            else if (shear < 376.40) return 100;
            else if (shear < 454.20) return 120;
            else return 150;
        }
        else if(val_L <= 300) {
            //shear less than minimum listed
            if (shear < 16.54) {
                warnings += "\nShear values too low.";
                return 0;
            }
            else if (shear < 32.40) return 4;
            else if (shear < 48.76) return 8;
            else if (shear < 59.96) return 12;
            else if (shear < 77.88) return 16;
            else if (shear < 91.08) return 20;
            else if (shear < 109.92) return 24;
            else if (shear < 144.08) return 30;
            else if (shear < 178.96) return 40;
            else if (shear < 207.74) return 50;
            else if (shear < 237.80) return 60;
            else if (shear < 262.00) return 70;
            else if (shear < 294.80) return 80;
            else if (shear < 321.20) return 90;
            else if (shear < 385.40) return 100;
            else if (shear < 463.00) return 120;
            else return 150;
        }

        return -1;
    }

}
