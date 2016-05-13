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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

//Steel Stringer Bridge with Timber Deck
public class Bridge2 extends AppCompatActivity {

    double val_L;
    EditText spanLengthEditText;
    double val_br;
    EditText roadwayWidthEditText;
    double val_Ns;
    EditText numberStringsEditText;
    double val_Ss;
    EditText stringerSpacingEditText;
    double val_Nb;
    EditText numberBracesEditText;
    double val_Sb;
    EditText braceSpacingEditText;
    String deckType;
    Spinner deckTypeDropDown;
    double val_td;
    EditText deckThicknessEditText;
    double val_b;
    EditText stringerWidthEditText;
    double val_d;
    EditText stringerDepthEditText;
    //web thickness and flange thickness not used in lookup

    //val_m, val_v, val_Lm, and val_Lc will hold the values from the table3 lookup
    double val_Ws;
    double val_m;
    double val_v;
    double val_Lm;
    double val_Lc;
    //val_Table3 holds the values returned by the table3 lookup to be assigned to val_m, val_v, val_Lm, and val_Lc
    //index 0 -> val_Ws
    //index 1 -> val_m
    //index 2 -> val_v
    //index 3 -> val_Lm
    //index 4 -> val_Lc
    double[] val_Table3;

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
        setContentView(R.layout.activity_bridge2);

        spanLengthEditText = (EditText)findViewById(R.id.spanLength);
        roadwayWidthEditText = (EditText)findViewById(R.id.roadwayWidth);
        numberStringsEditText = (EditText)findViewById(R.id.numberStrings);
        stringerSpacingEditText = (EditText)findViewById(R.id.stringerSpacing);
        numberBracesEditText = (EditText)findViewById(R.id.numberBraces);
        braceSpacingEditText = (EditText)findViewById(R.id.braceSpacing);
        deckThicknessEditText = (EditText)findViewById(R.id.deckThickness);
        stringerWidthEditText = (EditText)findViewById(R.id.stringerWidth);
        stringerDepthEditText = (EditText)findViewById(R.id.stringerDepth);

        final ListView table3ListView = (ListView)findViewById(R.id.table3);
        //Values are d x b
        String[] table3List = new String[]{
                "39.250 x 11.750",
                "36.750 x 11.750",
                "36.750 x 16.625",
                "36.500 x 12.125",
                "36.375 x 12.125",
                "36.125 x 12.000",
                "36.000 x 12.000",
                "35.875 x 16.500",
                "35.875 x 12.000",
                "35.375 x 11.750 (Ws = 150)",
                "35.375 x 11.750 (Ws = 201)",
                "33.250 x 15.750",
                "33.250 x 11.500",
                "33.125 x 11.500",
                "33.000 x 15.575",
                "31.500 x 11.750",
                "30.125 x 10.500",
                "30.000 x 10.500",
                "29.875 x 10.500",
                "29.500 x 11.750",
                "27.500 x 11.750",
                "27.125 x 11.750",
                "26.875 x 10.000",
                "25.500 x 11.750",
                "24.250 x 9.000",
                "24.125 x 9.000",
                "24.000 x 12.000",
                "24.000 x 8.000",
                "24.000 x 7.875",
                "24.000 x 7.000",
                "23.875 x 9.000",
                "23.625 x 11.750",
                "23.625 x 8.500",
                "22.000 x 7.000",
                "21.625 x 11.750 ",
                "21.625 x 7.875",
                "21.250 x 8.250",
                "21.125 x 8.250",
                "21.000 x 8.250",
                "20.000 x 7.125",
                "20.000 x 6.500",
                "19.625 x 11.750",
                "18.250 x 7.500",
                "18.250 x 7.000",
                "18.125 x 7.500",
                "18.000 x 8.000",
                "18.000 x 7.500",
                "18.000 x 6.000",
                "17.750 x 11.750",
                "17.750 x 6.875",
                "17.750 x 6.625",
                "16.750 x 11.750",
                "16.750 x 6.500",
                "16.250 x 7.125",
                "16.125 x 7.000",
                "16.000 x 8.500",
                "16.000 x 7.000",
                "16.000 x 6.000",
                "15.875 x 7.000",
                "15.750 x 11.750",
                "15.750 x 6.125",
                "15.750 x 5.375",
                "15.000 x 11.750",
                "15.000 x 5.875",
                "15.000 x 5.500",
                "14.250 x 11.750",
                "14.250 x 5.375",
                "14.125 x 5.625",
                "14.000 x 8.000",
                "14.000 x 6.000",
                "14.000 x 6.750",
                "13.875 x 6.750",
                "13.375 x 11.750",
                "13.375 x 5.375",
                "13.000 x 5.000",
                "12.625 x 5.125",
                "12.250 x 6.625",
                "12.000 x 8.000",
                "12.000 x 6.500",
                "12.000 x 5.500",
                "12.000 x 5.000",
                "11.250 x 4.750",
                "11.000 x 11.00",
                "10.625 x 4.750",
                "10.125 x 5.570",
                "10.000 x 6.000",
                "10.000 x 5.000",
                "10.000 x 4.625",
                "9.875 x 5.750",
                "9.250 x 9.500",
                "9.500 x 4.500",
                "9.000 x 7.000",
                "8.000 x 6.000",
                "8.000 x 5.000",
                "8.000 x 8.000",
                "7.875 x 7.875",
                "7.125 x 7.125",
                "6.250 x 6.250",
        };
        ArrayAdapter<String> table3Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, table3List);
        table3ListView.setAdapter(table3Adapter);

        table3ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position ==0) val_Table3 = new double[] {211,1770,450,100,12.4};
                else if(position ==1)val_Table3 = new double[] {206,1656,425,95,12.4};
                else if(position ==2)val_Table3 = new double[] {300,2486,520,95,17.6};
                else if(position ==3)val_Table3 = new double[] {194,1492,431,93,12.8};
                else if(position ==4)val_Table3 = new double[] {182,1397,406,93,12.8};
                else if(position ==5)val_Table3 = new double[] {170,1302,381,92,12.7};
                else if(position ==6)val_Table3 = new double[] {160,1217,365,92,12.7};
                else if(position ==7)val_Table3 = new double[] {230,1879,421,91,17.4};
                else if(position ==8)val_Table3 = new double[] {150,1131,350,91,12.7};
                else if(position ==9)val_Table3 = new double[] {201,1545,402,90,12.4};
                else if(position ==10)val_Table3 = new double[] {196,1433,377,85,12.4};
                else if(position ==11)val_Table3 = new double[] {220,1661,392,85,16.6};
                else if(position ==12)val_Table3 = new double[] {141,1005,313,85,12.1};
                else if(position ==13)val_Table3 = new double[] {130,911,300,85,12.1};
                else if(position ==14)val_Table3 = new double[] {200,1506,362,84,16.6};
                else if(position ==15)val_Table3 = new double[] {180,1327,327,80,12.4};
                else if(position ==16)val_Table3 = new double[] {124,797,273,77,11.1};
                else if(position ==17)val_Table3 = new double[] {116,738,263,76,11.1};
                else if(position ==18)val_Table3 = new double[] {108,673,255,76,11.1};
                else if(position ==19)val_Table3 = new double[] {175,1156,304,75,12.4};
                else if(position ==20)val_Table3 = new double[] {171,1059,282,70,12.4};
                else if(position ==21)val_Table3 = new double[] {102,599,217,69,10.6};
                else if(position ==22)val_Table3 = new double[] {94,546,205,68,10.6};
                else if(position ==23)val_Table3 = new double[] {157,915,237,65,12.4};
                else if(position ==24)val_Table3 = new double[] {94,497,191,62,9.5};
                else if(position ==25)val_Table3 = new double[] {84,442,174,61,9.5};
                else if(position ==26)val_Table3 = new double[] {100,560,173,61,12.7};
                else if(position ==27)val_Table3 = new double[] {120,564,286,61,8.4};
                else if(position ==28)val_Table3 = new double[] {106,527,224,61,8.3};
                else if(position ==29)val_Table3 = new double[] {80,391,183,61,7.4};
                else if(position ==30)val_Table3 = new double[] {76,394,163,61,9.5};
                else if(position ==31)val_Table3 = new double[] {153,828,217,60,12.4};
                else if(position ==32)val_Table3 = new double[] {134,634,283,60,9.0};
                else if(position ==33)val_Table3 = new double[] {75,308,168,56,7.4};
                else if(position ==34)val_Table3 = new double[] {139,699,198,55,12.4};
                else if(position ==35)val_Table3 = new double[] {112,495,238,55,8.3};
                else if(position ==36)val_Table3 = new double[] {73,338,148,54,8.7};
                else if(position ==37)val_Table3 = new double[] {68,315,140,54,8.7};
                else if(position ==38)val_Table3 = new double[] {62,284,130,53,8.7};
                else if(position ==39)val_Table3 = new double[] {85,337,195,51,7.5};
                else if(position ==40)val_Table3 = new double[] {65,245,132,51,6.9};
                else if(position ==41)val_Table3 = new double[] {134,621,177,50,12.4};
                else if(position ==42)val_Table3 = new double[] {60,243,115,46,7.9};
                else if(position ==43)val_Table3 = new double[] {86,326,184,46,7.4};
                else if(position ==44)val_Table3 = new double[] {55,220,108,46,7.9};
                else if(position ==45)val_Table3 = new double[] {80,292,133,46,8.4};
                else if(position ==46)val_Table3 = new double[] {50,200,99,46,7.9};
                else if(position ==47)val_Table3 = new double[] {55,199,126,46,6.3};
                else if(position ==48)val_Table3 = new double[] {122,648,145,45,12.4};
                else if(position ==49)val_Table3 = new double[] {62,238,100,45,7.3};
                else if(position ==50)val_Table3 = new double[] {77,2381,163,45,7.0};
                else if(position ==51)val_Table3 = new double[] {112,450,136,42,12.4};
                else if(position ==52)val_Table3 = new double[] {70,238,146,42,6.9};
                else if(position ==53)val_Table3 = new double[] {50,181,94,41,7.5};
                else if(position ==54)val_Table3 = new double[] {45,163,85,41,7.4};
                else if(position ==55)val_Table3 = new double[] {64,234,106,40,9.0};
                else if(position ==56)val_Table3 = new double[] {40,145,75,40,7.4};
                else if(position ==57)val_Table3 = new double[] {50,155,105,40,6.3};
                else if(position ==58)val_Table3 = new double[] {36,127,74,40,7.4};
                else if(position ==59)val_Table3 = new double[] {110,345,127,40,12.4};
                else if(position ==60)val_Table3 = new double[] {62,200,129,40,6.5};
                else if(position ==61)val_Table3 = new double[] {45,150,104,40,5.7};
                else if(position ==62)val_Table3 = new double[] {103,369,121,38,12.4};
                else if(position ==63)val_Table3 = new double[] {56,173,110,38,6.2};
                else if(position ==64)val_Table3 = new double[] {43,132,93,38,5.8};
                else if(position ==65)val_Table3 = new double[] {101,344,144,36,12.4};
                else if(position ==66)val_Table3 = new double[] {40,119,83,36,5.7};
                else if(position ==67)val_Table3 = new double[] {51,150,104,36,5.9};
                else if(position ==68)val_Table3 = new double[] {70,204,87,35,8.4};
                else if(position ==69)val_Table3 = new double[] {57,153,101,35,6.3};
                else if(position ==70)val_Table3 = new double[] {34,121,78,35,7.1};
                else if(position ==71)val_Table3 = new double[] {30,109,61,35,7.1};
                else if(position ==72)val_Table3 = new double[] {92,297,96,34,12.4};
                else if(position ==73)val_Table3 = new double[] {46,126,99,34,5.7};
                else if(position ==74)val_Table3 = new double[] {35,85,72,33,5.3};
                else if(position ==75)val_Table3 = new double[] {41,108,104,32,5.4};
                else if(position ==76)val_Table3 = new double[] {36,103,56,31,7.0};
                else if(position ==77)val_Table3 = new double[] {65,182,73,30,8.4};
                else if(position ==78)val_Table3 = new double[] {27,76,44,30,6.9};
                else if(position ==79)val_Table3 = new double[] {50,113,120,30,5.8};
                else if(position ==80)val_Table3 = new double[] {32,81,62,30,5.3};
                else if(position ==81)val_Table3 = new double[] {34,81,72,28,5.0};
                else if(position ==82)val_Table3 = new double[] {76,202,67,28,11.6};
                else if(position ==83)val_Table3 = new double[] {29,67,48,27,5.0};
                else if(position ==84)val_Table3 = new double[] {25,59,38,25,6.1};
                else if(position ==85)val_Table3 = new double[] {40,92,53,25,6.3};
                else if(position ==86)val_Table3 = new double[] {35,65,88,25,5.3};
                else if(position ==87)val_Table3 = new double[] {25,55,46,25,4.9};
                else if(position ==88)val_Table3 = new double[] {21,48,36,25,6.1};
                else if(position ==89)val_Table3 = new double[] {59,132,56,23,10.0};
                else if(position ==90)val_Table3 = new double[] {25,51,43,24,4.8};
                else if(position ==91)val_Table3 = new double[] {50,103,45,23,7.4};
                else if(position ==92)val_Table3 = new double[] {3565,34,20,6.3,};
                else if(position ==93)val_Table3 = new double[] {28,49,35,20,5.3};
                else if(position ==94)val_Table3 = new double[] {31,61,33,20,8.4};
                else if(position ==95)val_Table3 = new double[] {44,81,40,20,8.3};
                else if(position ==96)val_Table3 = new double[] {35,58,37,18,7.5};
                else if(position ==97)val_Table3 = new double[] {31,45,31,16,6.6};

                String selection = (String) table3ListView.getItemAtPosition(position);

                context = getApplicationContext();
                toastText = selection + "  Dimensions Selected.";
                toast = Toast.makeText(context, toastText, duration);
                toast.show();

                TextView dimensionsTextView = (TextView)findViewById(R.id.dimensions);
                dimensionsTextView.setText(selection + "  Dimensions Selected.");

                val_Ws = val_Table3[0];
                val_m = val_Table3[1];
                val_v = val_Table3[2];
                val_Lm = val_Table3[3];
                val_Lc = val_Table3[4];

            }

        });


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
        getMenuInflater().inflate(R.menu.menu_bridge2, menu);
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
            val_Sb = Double.parseDouble(braceSpacingEditText.getText().toString());
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


    public void classify() {
        //get entered values
        getValues();

        //check for invalid or empty values
        if (val_m <= 0) {
            context = getApplicationContext();
            toastText = "Select valid Dimensions";
            toast = Toast.makeText(context, toastText, duration);
            toast.show();
        }
        else if (val_L <= 0) {
            context = getApplicationContext();
            toastText = "Enter valid Span Length";
            toast = Toast.makeText(context, toastText, duration);
            toast.show();
        } else if (val_br <= 0) {
            context = getApplicationContext();
            toastText = "Enter valid Roadway Width";
            toast = Toast.makeText(context, toastText, duration);
            toast.show();
        } else if (val_Ns <= 0) {
            context = getApplicationContext();
            toastText = "Enter valid Number of Stringers";
            toast = Toast.makeText(context, toastText, duration);
            toast.show();
        } else if (val_Ss <= 0) {
            context = getApplicationContext();
            toastText = "Enter valid Stringer Spacing";
            toast = Toast.makeText(context, toastText, duration);
            toast.show();
        } else if (deckType.equals("empty")) {
            context = getApplicationContext();
            toastText = "Select Deck Type";
            toast = Toast.makeText(context, toastText, duration);
            toast.show();
        } else if (val_td <= 0) {
            context = getApplicationContext();
            toastText = "Enter valid Deck Thickness";
            toast = Toast.makeText(context, toastText, duration);
            toast.show();
        } else if (val_b <= 0) {
            context = getApplicationContext();
            toastText = "Enter valid Deck Width";
            toast = Toast.makeText(context, toastText, duration);
            toast.show();
        } else if (val_d <= 0) {
            context = getApplicationContext();
            toastText = "Enter valid Deck Depth";
            toast = Toast.makeText(context, toastText, duration);
            toast.show();
        }

        //if all values are valid, continue to calculations
        else {
            //clear warnings of outdated messages
            warnings = "";

            //step 1
            val_m *= 0.83;

            //step 2
            //Set by table 3 lookup

            //step 3
            double val_mDL = 0.00013 * val_L * val_L * (val_Ws + (0.347 * val_td * val_Ss));

            //step 4
            double val_mLL = (val_m - val_mDL) / 1.15;

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

            //Do not perform steps 10-14 for Steel Stringer Bridge
            //step 10
            //step 11
            //step 12
            //step 13
            //step 14


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
            //This is completed in a future activity

            //step 17
            if (val_Nb < (val_L / val_Lc + 1)) warnings += "\nMore braces required. Add braces.";

            //Pass into and start next activity (Step 16)
            Intent i = new Intent(getBaseContext(), Bridge2DeckClassification.class);
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
}
