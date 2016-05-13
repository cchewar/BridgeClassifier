package edu.usma.park.bridge_classifier;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button1 =(Button)findViewById(R.id.bridge1); //Timber Stringer bridge w/ Timber Deck
        Button button2 =(Button)findViewById(R.id.bridge2); //Steel Stringer bridge w/ Timber Deck
        Button button3 =(Button)findViewById(R.id.bridge3); //Steel Stringer bridge w/ Concrete Deck
        Button button4 =(Button)findViewById(R.id.bridge4); //Reinforced Concrete T-Beam With Asphalt Wearing Surface
        Button button5 =(Button)findViewById(R.id.bridge5); //Reinforced Concrete-Slab Bridge With Asphalt Wearing Surface
        Button button6 =(Button)findViewById(R.id.bridge6); //Masonry Arch Bridge

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), Bridge1.class);
                startActivity(i);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), Bridge2.class);
                startActivity(i);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), Bridge3.class);
                startActivity(i);
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), Bridge4.class);
                startActivity(i);
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), Bridge5.class);
                startActivity(i);
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), Bridge6.class);
                startActivity(i);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
