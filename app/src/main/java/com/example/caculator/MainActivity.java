package com.example.caculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayDeque;
import java.util.Deque;

public class MainActivity extends AppCompatActivity {


    private String viewString = "";
    private TextView viewCal;
    private String numberCur = "";
    private TextView numberNow;
    private Deque<String> dqNumber = new ArrayDeque<>(100);
    private Deque<String> dqResult = new ArrayDeque<>(100);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewCal = findViewById(R.id.math_before);
        numberNow = findViewById(R.id.number_now);
    }

    private void addNumberX(String num){
        numberCur = numberCur + num;
        numberNow.setText(numberCur);
    }
    public void addNumber7(View view) {
        addNumberX("7");
    }

    public void addNumber8(View view) {
        addNumberX("8");
    }

    public void addNumber9(View view) {
        addNumberX("9");
    }

    public void addNumber4(View view) {
        addNumberX("4");
    }

    public void addNumber5(View view) {
        addNumberX("5");
    }

    public void addNumber6(View view) {
        addNumberX("6");
    }

    public void addNumber1(View view) {
        addNumberX("1");
    }

    public void addNumber2(View view) {
        addNumberX("2");
    }

    public void addNumber3(View view) {
        addNumberX("3");
    }

    public void addNumber0(View view) {
        addNumberX("0");
    }

    public void addDot(View view) {
        addNumberX(".");
    }

    public void clearOneElement(View view) {
        numberCur = numberCur.substring(0, viewString.length() - 1);
        numberNow.setText(numberCur);
    }

    public void clearAll(View view) {
        viewString = "";
        viewCal.setText(viewString);
        numberCur = "";
        numberNow.setText(numberCur);
    }

    public void calculate(int sz) {

        if (viewString.equals("") ||
                viewString.charAt(sz - 1)=='/' ||
                viewString.charAt(sz - 1)=='*' ||
                viewString.charAt(sz - 1)=='-' ||
                viewString.charAt(sz - 1)=='+' ) {
            viewString = "";
            viewCal.setText(viewString);
            return;
        }
        int j = 0;
        for (int i = 0; i < sz; i++) {
            if ((i!=j)&&(viewString.charAt(i) == '+' || viewString.charAt(i) == '-' ||
                    viewString.charAt(i) == '*' || viewString.charAt(i) == '/')) {
                dqNumber.addLast(viewString.substring(j, i));
                dqNumber.addLast(viewString.substring(i, i + 1));
                j = i + 1;
            }
        }
        dqNumber.addLast(viewString.substring(j, sz));
        Log.d("TAG", "calculate: ");
        while (!dqNumber.isEmpty()) {
            String element = dqNumber.getFirst();
            Log.d("TAG", element);
            dqNumber.removeFirst();
            if (element.equals("*")) {
                Double tmp = Double.parseDouble(dqResult.getLast());
                dqResult.removeLast();
                //Log.d("TAG", tmp.toString());
                //String str = dqNumber.getFirst();
                tmp *= Double.parseDouble(dqNumber.getFirst());
                //Log.d("TAG", str);
                dqNumber.removeFirst();
                dqResult.addLast(tmp.toString());
            } else if (element.equals("/")) {
                Double tmp = Double.parseDouble(dqResult.getLast());
                dqResult.removeLast();
                tmp /= Double.parseDouble(dqNumber.getFirst());
                dqNumber.removeFirst();
                dqResult.addLast(tmp.toString());
            } else {
                dqResult.addLast(element);
            }
        }

        Double result = Double.parseDouble(dqResult.getFirst());
        dqResult.removeFirst();
        while (!dqResult.isEmpty()) {
            String element = dqResult.getFirst();
            dqResult.removeFirst();
            //Log.d("TAG", element);
            if (element.equals("+")) {
                result = result + Double.parseDouble(dqResult.getFirst());
                //Log.d("TAG", "here");
                dqResult.removeFirst();
            } else if (element.equals("-")) {
                result = result - Double.parseDouble(dqResult.getFirst());
                dqResult.removeFirst();
            }
        }
        numberCur = "";

        numberNow.setText(String.valueOf(result));

    }

    private void addCalculation(String cal){
        int sz = viewString.length() -1;
        //if(viewString.charAt(sz) =='+' || viewString.charAt(sz) =='-' || viewString.charAt(sz) =='*' || viewString.charAt(sz) =='/') return;
        if(numberCur.equals("")){
            viewString = viewString + numberCur + cal;
        }else
        viewString = viewString + numberNow.getText().toString() + cal;

            numberCur = "";
            viewCal.setText(viewString);
            calculate(viewString.length() - 1);

    }
    public void calculationDiv(View view) {
        addCalculation("/");
    }

    public void calculatorMul(View view) {
        addCalculation("*");
    }

    public void calculationSub(View view) {
        addCalculation("-");
    }

    public void CalculationAdd(View view) {
        addCalculation("+");
    }



    public void DeleteOneNumber(View view) {
        numberCur = "0";
        numberNow.setText(numberCur);
    }

    public void calculateEquals(View view) {
        viewString = viewString + numberCur;
        viewCal.setText(viewString);
        calculate(viewString.length());
        viewString = "";
        numberCur = numberNow.getText().toString();
        viewCal.setText(viewString);
    }

    public void oppositeNumber(View view) {
        if(numberCur.equals("")){

        }
        else if(numberCur.charAt(0) == '-'){
            numberCur = numberCur.substring(1,numberCur.length());
        }else{
            numberCur = "-" + numberCur;
        }
        numberNow.setText(numberCur);
    }
}