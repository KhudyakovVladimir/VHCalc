package com.example.vhcalcandroid;


import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class MainActivity extends AppCompatActivity implements SoundPool.OnLoadCompleteListener {
    // operation '=' need fixed

    final String LOG_TAG = "myLogs";

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

    SoundPool soundPool;
    int keyboardTap;
    int keyboardOperation;
    int keyboardResult;

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
        MenuItem menuItem = menu.add("about");
        menuItem.setIntent(new Intent(this, About.class));
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        soundPool.setOnLoadCompleteListener(this);
        keyboardTap = soundPool.load(this, R.raw.keyboard_tap, 1);
        keyboardOperation = soundPool.load(this,R.raw.keyboard_operation,1);
        keyboardResult = soundPool.load(this, R.raw.keyboard_result, 1);

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
                soundPool.play(keyboardTap,1,1,0,0,1);
                if(stringBuilder.length() == 0){
                    stringBuilder.append("0.");
                    textView.setText(stringBuilder.toString());
                    soundPool.play(keyboardTap,1,1,0,0,1);
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
                if(stringBuilder.length() > 0)
                    stringBuilder.deleteCharAt(stringBuilder.length()-1);
                textView.setText(stringBuilder.toString());
                if(stringBuilder.length() == 0){
                    textView.setText(stringBuilder.toString());
                }
                else {
                    number1 = Double.parseDouble(stringBuilder.toString());
                    textView.setText(stringBuilder.toString());
                }soundPool.play(keyboardOperation,1,1,0,0,1);
            }
        });

        buttonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonC.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);
                stringBuilder.delete(0,stringBuilder.capacity());
                textView.setText("0");
                textViewOperation.setText("");
                soundPool.play(keyboardOperation,1,1,0,0,1);
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
                textViewMemory.setText("M");
                soundPool.play(keyboardOperation,1,1,0,0,1);
            }
        });

        MR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MR.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);
                number1 = memory;
                BigDecimal bigDecimal = new BigDecimal(String.valueOf(memory));
                //textView.setText(String.valueOf(bigDecimal));

                ///////
                String tempText = String.valueOf(memory);
                String resultText = "";
                if((tempText.charAt(tempText.length()-1) == '0')){
                    resultText = tempText.substring(0,tempText.length() - 2);
                    textView.setText(resultText);
                    textViewMemory.setText("");
                }
                else {
                    textView.setText(tempText);
                }
                soundPool.play(keyboardOperation,1,1,0,0,1);
                ///////
            }
        });

        buttonResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonResult.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);
                removeZeroAfterDot();
                log("=");
                operation = '0';
                flagRepeatOperation = false;
                soundPool.play(keyboardResult,1,1,0,0,1);
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
        log(s);
        soundPool.play(keyboardTap,1,1,0,0,1);
    }

    void log(String s){
        Log.d(LOG_TAG, "button " + s + " -----------");
        Log.d(LOG_TAG, "stringBuilder = " + stringBuilder.toString());
        Log.d(LOG_TAG, "number_1 = " + number1);
        Log.d(LOG_TAG, "number_2 = " + number2);
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
        soundPool.play(keyboardOperation,1,1,0,0,1);
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
