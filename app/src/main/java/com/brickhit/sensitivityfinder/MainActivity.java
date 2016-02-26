package com.brickhit.sensitivityfinder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import java.text.DecimalFormat;


public class MainActivity extends AppCompatActivity {

    //Variables used
    String editValue;
    String editValue2;
    String editValue3;
    double lowNum = 0.00;
    double highNum = 0.00;
    double avgNum;
    double multiplierFirst =1.5;
    double multiplierSecond = 1.2;
    double multiplierThrid = 1.05;
    double dividerFirst = 0.5;
    double dividerSecond = 0.8;
    double dividerThrid = 0.95;
    String finishNumber;
    EditText editText;
    EditText editText2;
    EditText editText3;
    TextView textView;
    RadioButton radioButton;
    RadioButton radioButton2;
    int tries = 0;
    DecimalFormat threeZeros = new DecimalFormat("#0.00");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Getting values for variables
        editText = (EditText) findViewById(R.id.editText);
        editText2 = (EditText) findViewById(R.id.editText2);
        editText3 = (EditText) findViewById(R.id.editText3);
        radioButton = (RadioButton) findViewById(R.id.radioButton);
        radioButton2 = (RadioButton) findViewById(R.id.radioButton2);

        //Disabliing Buttons Continue and Finish
        final Button continueButton = (Button) findViewById(R.id.ContinueButton);
        final Button resetButton = (Button) findViewById(R.id.ResetButton);
        final Button finishButton = (Button) findViewById(R.id.FinishButton);
        final Button startButton = (Button) findViewById(R.id.StartButton);

        continueButton.setEnabled(false);
        finishButton.setEnabled(false);

        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //Changing text on editText2
        textView = (TextView) findViewById(R.id.textView2);
        textView.setText(R.string.sens_number);


        //StartButton Click Action

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                try {
                    editValue2 = editText2.getText().toString();
                    avgNum = Double.parseDouble(editValue2);
                } catch (NumberFormatException e) {
                    avgNum = 0;
                }

                if (avgNum <= 0) {

                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                    alertDialog.setTitle("Error");
                    alertDialog.setMessage("Average Number can't be lower or equal to zero!");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();

                        }
                    });
                    alertDialog.show();

                }

                if (tries == 0 && avgNum > 0) {
                    textView.setText(R.string.average_number);
                    lowNum = avgNum * dividerFirst;
                    highNum = avgNum * multiplierFirst;
                    editText2.setEnabled(false);
                    startButton.setEnabled(false);
                    continueButton.setEnabled(true);
                    finishButton.setEnabled(true);

                }
                editValue = threeZeros.format(lowNum);
                editText.setText(editValue);
                ;
                editValue3 = threeZeros.format(highNum);
                editText3.setText(editValue3);
                if (avgNum != 0) {
                    tries++;
                }


            }


        });



        continueButton.setOnClickListener(new View.OnClickListener(){

            @Override
        public void onClick(View view){

                if(radioButton.isChecked() || radioButton2.isChecked() ){
                    if(tries < 5 && radioButton.isChecked()){

                        lowNum = lowNum*dividerSecond;
                        highNum = avgNum;
                        avgNum = (lowNum + highNum)/2;


                    }
                    else if(tries < 5 && radioButton2.isChecked()){

                        highNum = highNum*multiplierSecond;
                        lowNum = avgNum;
                        avgNum = (lowNum + highNum)/2;

                    }

                    else if(tries >=5 && radioButton.isChecked()){

                        lowNum = lowNum*dividerThrid;
                        highNum = avgNum;
                        avgNum = (lowNum + highNum)/2;


                    }

                    else if(tries >=5 && radioButton2.isChecked()){

                        highNum = highNum*multiplierThrid;
                        lowNum = avgNum;
                        avgNum = (lowNum + highNum)/2;


                    }





                }
                else {

                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                    alertDialog.setTitle("Error");
                    alertDialog.setMessage("One of sensitivities must be choosen!");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();

                        }
                    });
                    alertDialog.show();




                }


                editValue = threeZeros.format(lowNum);
                editText.setText(editValue);

                editValue2 = threeZeros.format(avgNum);
                editText2.setText(editValue2);

                editValue3 = threeZeros.format(highNum);
                editText3.setText(editValue3);
                tries++;

            }



        });




        finishButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                finishNumber = threeZeros.format(avgNum);
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle("Finished");
                alertDialog.setMessage("Your Perfect sensitivity is: " + finishNumber);
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                });
                alertDialog.show();




            }



        });



        resetButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                startActivity(new Intent(MainActivity.this, MainActivity.class));

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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_donate) {
          //  return true;

            Intent intent = new Intent(getApplicationContext(),SettingsActivity.class);
            startActivity(intent);

        }
        if (id == R.id.action_author){

            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle("Created by");
            alertDialog.setMessage("Sebastian Waluś, "  + "based on method created by Impulsive" + System.getProperty("line.separator") + "All rights reserved");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();

                }
            });
            alertDialog.show();


        }

        return super.onOptionsItemSelected(item);


    }
}
