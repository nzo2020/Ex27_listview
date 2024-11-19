package com.example.ex27_listview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class result_activity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView lVFirstTwenty;
    TextView firstTerm, difOrMul, qOrd, index, sumOfTerms;
    double firstTerm1, difference, multiplier;
    boolean type;
    String[] terms = new String[20];
    Intent intentBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        firstTerm = findViewById(R.id.firstTerm);
        difOrMul = findViewById(R.id.difOrMul);
        qOrd = findViewById(R.id.qOrd);
        index = findViewById(R.id.index);
        sumOfTerms = findViewById(R.id.sumOfTerms);
        lVFirstTwenty = findViewById(R.id.listV);
        intentBack = getIntent();


        Intent intent = getIntent();
        type = intent.getBooleanExtra("type", false);
        firstTerm1 = intent.getDoubleExtra("firstTerm", 0);

        firstTerm.setText(String.valueOf(firstTerm1));

        if (type) {
            difference = intent.getDoubleExtra("difference", 0);
            qOrd.setText("d = ");
            difOrMul.setText(String.valueOf(difference));
            generateArithmeticSeries();
        } else {
            multiplier = intent.getDoubleExtra("multiplier", 0);
            qOrd.setText("q = ");
            difOrMul.setText(String.valueOf(multiplier));
            generateGeometricSeries();
        }

        ArrayAdapter<String> adp = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,terms);
        lVFirstTwenty.setAdapter(adp);
        lVFirstTwenty.setOnItemClickListener(this);
    }


    public static String differentView(double term) {
        if (term % 1 == 0 && term < 10000 && term > -10000) {
            return String.valueOf((int) term);
        }

        if (term >= 10000 || term <= -10000) {
            int exponent = 0;
            double coefficient = term;

            while (Math.abs(coefficient) >= 10000) {
                coefficient /= 10;
                exponent++;
            }

            return String.format("%d * 10^%d", (int) coefficient, exponent);
        }

        int exponent = 0;
        double coefficient = term;

        if (Math.abs(term) >= 1) {
            while (Math.abs(coefficient) >= 10) {
                coefficient /= 10;
                exponent++;
            }
        } else {
            while (Math.abs(coefficient) < 1) {
                coefficient *= 10;
                exponent--;
            }
        }

        return String.format("%.3f * 10^%d", coefficient, exponent);
    }


    public void generateArithmeticSeries() {
        double term;
        for (int i = 0; i < 20; i++) {
            term = firstTerm1 + i * difference;
            terms[i] = differentView(term);
        }

    }

    public void generateGeometricSeries() {
        double term;
        for (int i = 0; i < 20; i++) {
            term = firstTerm1 * Math.pow(multiplier, i);
            terms[i] = differentView(term);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
        index.setText(String.valueOf(pos + 1));

        String sum;
        if (type) {
            sum = differentView(sumArithmetic(pos + 1));
        } else {
            sum = differentView(sumGeometric(pos + 1));
        }

        sumOfTerms.setText(String.valueOf(sum));
    }

    public double sumArithmetic(int n) {
        return n * (2 * firstTerm1 + (n - 1) * difference) / 2;
    }

    public double sumGeometric(int n) {
        return firstTerm1 * (Math.pow(multiplier, n) - 1) / (multiplier - 1);
    }


    public void back(View view) {
        setResult(RESULT_OK, intentBack);
        finish();
    }
}