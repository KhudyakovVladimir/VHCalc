package com.example.vhcalcandroid;


import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MainActivity extends AppCompatActivity implements SoundPool.OnLoadCompleteListener {
    // MR logic need to fix

    final String LOG_TAG = "myLogs";

    static int codeForMenu = 0;

    static int count = 0;

    int layoutMain = R.layout.blue_theme;
    int layoutMain2 = R.layout.wood_theme;
    //static int layoutMain3 = R.layout.activity_main_3;
    int layoutMain3 = R.layout.black_theme;

    int[] layouts = {layoutMain3, layoutMain2, layoutMain};
    int currentLayout = layouts[count];

    int theme = R.style.AppTheme;
    int themeWood = R.style.AppThemeWood;
    int themePaint = R.style.AppThemePaint;

    int[] themes = {themePaint, themeWood, theme};
    int currentTheme = themes[count];

    ConstraintLayout constraintLayout;
    TextView textView;
    TextView textViewOperation;
    TextView textViewMemory;

    Button button0;
    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button button5;
    Button button6;
    Button button7;
    Button button8;
    Button button9;
    Button buttonPlus;
    Button buttonMinus;
    Button buttonDivide;
    Button multiply;
    Button buttonDot;
    Button buttonC;
    Button buttonDel;
    Button M;
    Button MR;
    Button buttonResult;

    double number1 = 0;
    double number2 = 0;
    double memory = 0;
    double result = 0;

    static char operation = '0';

    boolean flagRepeatOperation = false;
    boolean flagSound = true;

    //sounds////////////////
    SoundPool soundPool;

    int keyboardTap;
    int keyboardOperation;
    int keyboardResult;

    int woodTap = R.raw.keyboard_tap;
    int woodOperation = R.raw.keyboard_operation;
    int woodResult = R.raw.keyboard_result;

    int blackTap = R.raw.keyboard_black_tap;
    int blackOperation = R.raw.keyboard_black_operation;
    int blackResult = R.raw.keyboard_black_result;

    int blueTap = R.raw.keyboard_black_tap;
    int blueOperation = R.raw.keyboard_black_operation;
    int blueResult = R.raw.keyboard_black_result;

    int[] taps = {blackTap, woodTap, blueTap};
    int[] operations = {blackOperation, woodOperation, blueOperation};
    int[] results = {blackResult, woodResult, blueResult};
    //sounds////////////////

    static StringBuilder stringBuilder = new StringBuilder();

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        switch (codeForMenu){
            case 0 : {
                getMenuInflater().inflate(R.menu.sound_off, menu);
                break;
            }
            case 1 : {
                getMenuInflater().inflate(R.menu.sound_on, menu);
                break;
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.sound_on : {
                codeForMenu = 1;
                flagSound = false;
                invalidateOptionsMenu();
                break;
            }
            case R.id.sound_off : {
                codeForMenu = 0;
                flagSound = true;
                invalidateOptionsMenu();
                break;
            }
            case R.id.about : {
                Toast.makeText(this, "Written by Khudyakov Vladimir @2020", Toast.LENGTH_LONG).show();
                break;
            }
            case R.id.style : {
                count++;
                if(count > layouts.length - 1){
                    count = 0;
                }
                currentLayout = layouts[count];
                currentTheme = themes[count];
                recreate();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(currentTheme);
        super.onCreate(savedInstanceState);
        setContentView(currentLayout);

        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        soundPool.setOnLoadCompleteListener(this);

        keyboardTap = soundPool.load(this, taps[count], 1);
        keyboardOperation = soundPool.load(this, operations[count],1);
        keyboardResult = soundPool.load(this, results[count], 1);

        constraintLayout = findViewById(R.id.constraintLayout);

        textView = findViewById(R.id.textView);
        textViewOperation = findViewById(R.id.textViewOperation);
        textViewMemory = findViewById(R.id.textViewMemory);

        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        button6 = findViewById(R.id.button6);
        button7 = findViewById(R.id.button7);
        button8 = findViewById(R.id.button8);
        button9 = findViewById(R.id.button9);
        buttonPlus = findViewById(R.id.buttonPlus);
        buttonMinus = findViewById(R.id.buttonMinus);
        buttonDivide = findViewById(R.id.buttonDivide);
        multiply = findViewById(R.id.multiply);
        buttonDot = findViewById(R.id.buttonDot);
        buttonC = findViewById(R.id.buttonC);
        buttonDel = findViewById(R.id.buttonDel);
        M = findViewById(R.id.M);
        MR = findViewById(R.id.MR);
        buttonResult = findViewById(R.id.buttonResult);

        textView.setText("0");

        flagRepeatOperation = true;

        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button0.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);

                if(operation == '/'){
                    textView.setText("error");
                }
                workInsideNumbers("0");
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button1.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);
                workInsideNumbers("1");
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button2.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);
                workInsideNumbers("2");
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button3.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);
                workInsideNumbers("3");
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button4.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);
                workInsideNumbers("4");
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button5.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);
                workInsideNumbers("5");
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button6.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);
                workInsideNumbers("6");
            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button7.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);
                workInsideNumbers("7");
            }
        });

        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button8.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);
                workInsideNumbers("8");
            }
        });

        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button9.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);
                workInsideNumbers("9");
            }
        });

        buttonDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonDot.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);
                if(flagSound){
                    soundPool.play(keyboardTap,1,1,0,0,1);
                }
                if(stringBuilder.length() == 0){
                    stringBuilder.append("0.");
                    textView.setText(stringBuilder.toString());
                    if(flagSound){
                        soundPool.play(keyboardTap,1,1,0,0,1);
                    }
                }
                if(stringBuilder.toString().contains(".")){
                }
                else {
                    stringBuilder.append(".");
                    textView.setText(stringBuilder.toString());
                    number1 = Double.parseDouble(stringBuilder.toString());
                }
            }
        });

        buttonDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonDel.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);
                int i = stringBuilder.length();
                if(stringBuilder.length() > 0){
                    stringBuilder.deleteCharAt(stringBuilder.length()-1);
                    textView.setText(stringBuilder.toString());
                    result = Double.parseDouble(stringBuilder.toString());
                }
                if(stringBuilder.length() == 0){
                    textView.setText(stringBuilder.toString());
                    result = Double.parseDouble(stringBuilder.toString());
                }
                else {
                    number1 = Double.parseDouble(stringBuilder.toString());
                    textView.setText(stringBuilder.toString());
                    result = Double.parseDouble(stringBuilder.toString());
                }
                if(flagSound){
                    soundPool.play(keyboardOperation,1,1,0,0,1);
                }

                log("del");
            }
        });

        buttonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonC.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);
                stringBuilder.delete(0,stringBuilder.capacity());
                textView.setText("0");
                textViewOperation.setText("");
                result = 0.0;
                number1 = 0.0;
                number2 = 0.0;
                operation = '0';
                log("C");
                if(flagSound){
                    soundPool.play(keyboardOperation,1,1,0,0,1);
                }
            }
        });

        buttonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonPlus.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);
                workInsideOperation('+');
                log("+");
            }
        });

        buttonMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonMinus.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);
                workInsideOperation('-');
                log("-");
            }
        });

        multiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multiply.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);
                workInsideOperation('*');
                log("*");
            }
        });

        buttonDivide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonDivide.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);
                workInsideOperation('/');
                log("/");
            }
        });

        M.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                M.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);
                String s = (String) textView.getText();
                memory = Double.parseDouble(s);
                workInsideOperation(' ');
                textViewMemory.setText("M");
                log("M");
                if(flagSound){
                    soundPool.play(keyboardOperation,1,1,0,0,1);
                }
            }
        });

        MR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MR.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);
                //number1 = memory;
                //number2 = memory;
                result = memory;

                BigDecimal bigDecimal = new BigDecimal(String.valueOf(memory));

                ///////
                String tempText = String.valueOf(bigDecimal);
                String resultText = "";
                if((tempText.charAt(tempText.length()-1) == '0')){
                    resultText = tempText.substring(0,tempText.length() - 2);
                    textView.setText(resultText);
                    textViewMemory.setText("");
                }
                else {
                    textView.setText(tempText);
                }
                ///////
                if(flagSound){
                    soundPool.play(keyboardOperation,1,1,0,0,1);
                }

                log("MR");
                memory = 0.0;

            }
        });

        buttonResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonResult.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);
                removeZeroAfterDot();

                operation = '0';
                flagRepeatOperation = false;
                if(flagSound){
                    soundPool.play(keyboardResult,1,1,0,0,1);
                }
                log("=");
            }
        });
    }

    double getResult(char operation){
        //Log.d(LOG_TAG, "getResult ----------------");
        //Log.d(LOG_TAG, "operation = " + operation);
        double result = 0;
        BigDecimal bigDecimal1 = new BigDecimal(String.valueOf(number1));
        BigDecimal bigDecimal2 = new BigDecimal(String.valueOf(number2));
        switch (operation){
            case '0': {result = Double.parseDouble(bigDecimal1.toString());break;}
            case '+' : result = Double.parseDouble(bigDecimal2.add(bigDecimal1).toString());break;
            case '-': result = Double.parseDouble(String.valueOf(bigDecimal2.subtract(bigDecimal1)));break;
            case '*': result = Double.parseDouble(bigDecimal2.multiply(bigDecimal1).toString());break;
            case '/':
                try {
                    result = Double.parseDouble(bigDecimal2.divide(bigDecimal1,7, RoundingMode.HALF_UP).toString());break;
                }catch (ArithmeticException aE){
                    textView.setText("error");
                    stringBuilder.delete(0,stringBuilder.length());
                    break;
                }
        }
        //Log.d(LOG_TAG, "result = " + result);
        //Log.d(LOG_TAG, "operation = " + operation);
        return result;
    }

    void workInsideNumbers(String s){
        stringBuilder.append(s);
        textView.setText(stringBuilder.toString());
        number1 = Double.parseDouble(stringBuilder.toString());
        result = getResult(operation);
        if(flagSound){
            soundPool.play(keyboardTap,1,1,0,0,1);
        }
        log(s);
    }

    void log(String s){
        Log.d(LOG_TAG, "button " + s + " -----------");
        Log.d(LOG_TAG, "stringBuilder = " + stringBuilder.toString());
        Log.d(LOG_TAG, "number_1 = " + number1);
        Log.d(LOG_TAG, "number_2 = " + number2);
        Log.d(LOG_TAG,"memory = " + memory);
        Log.d(LOG_TAG, "result = " + result);
        Log.d(LOG_TAG, "operation = " + operation);
        Log.d(LOG_TAG, "flagRepeatOperation = " + flagRepeatOperation);
    }

    void workInsideOperation(char s){
        flagRepeatOperation = true;

        if(flagRepeatOperation){
            number2 = result;
        }
        else {
            flagRepeatOperation = false;
            number2 = number1;
        }

        stringBuilder.delete(0,stringBuilder.length());
        operation = s;
        textViewOperation.setText(String.valueOf(s));
        if(flagSound){
            soundPool.play(keyboardOperation,1,1,0,0,1);
        }
    }

    void removeZeroAfterDot(){
        //if result include zero after dot,this fix it
        String tempText = String.valueOf(result);
        String resultText = "";
        if((tempText.charAt(tempText.length()-1) == '0')){
            resultText = tempText.substring(0,tempText.length() - 2);
            textView.setText(resultText);
            textViewOperation.setText("");
        }
        else {
            textView.setText(tempText);
        }
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
    }
}
