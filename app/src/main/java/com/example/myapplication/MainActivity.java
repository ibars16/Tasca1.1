package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Button calcular;
    EditText cantidad;
    TextView seconds;
    TextView minutes;
    TextView hours;
    TextView years;
    TextView listResults;
    Spinner spinner;
    String[] dias;
    Double[] resultados;
    String[] formatResults;
    String lang;
    String mesageError;
    String[] units;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lang = Locale.getDefault().getLanguage();
        this.getUnits(lang);
        this.getMesageError();
        this.llenarSpinner();

        calcular = (Button)findViewById(R.id.button);
        cantidad  = (EditText)findViewById(R.id.number);
        seconds = (TextView) findViewById(R.id.seconds);
        minutes = (TextView) findViewById(R.id.minutes);
        hours = (TextView) findViewById(R.id.hours);
        years = (TextView) findViewById(R.id.years);
        listResults = (TextView) findViewById(R.id.results);
        listResults.setVisibility(View.INVISIBLE);

        calcular.setOnClickListener(
                new View.OnClickListener()
                {
                    @SuppressLint("SetTextI18n")
                    public void onClick(View view) {
                        if (cantidad.getText().toString().length() != 0) {
                            if (cantidad.getText().toString().charAt(0) != '.') {
                                Double numero = Double.parseDouble(cantidad.getText().toString());
                                String unit = spinner.getSelectedItem().toString().substring(0, 1);
                                if (unit.equals("S")) {
                                    resultados = calcularSegundos(numero);
                                } else if (unit.equals("M")) {
                                    resultados = calcularMinutos(numero);
                                } else if (unit.equals("H")) {
                                    resultados = calcularHoras(numero);
                                } else {
                                    resultados = calcularAnos(numero);
                                }
                                formatResults(resultados);
                                listResults.setVisibility(View.VISIBLE);
                                if (resultados != null) {
                                    seconds.setText(formatResults[0] + " " + units[0]);
                                    minutes.setText(formatResults[1] + " " + units[1]);
                                    hours.setText(formatResults[3] + " " + units[3]);
                                    years.setText(formatResults[2] + " " + units[2]);
                                }
                            } else {
                                Toast.makeText(MainActivity.this, mesageError, Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(MainActivity.this, mesageError, Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    private Double[] calcularSegundos(Double numero){
        Double seconds = numero;
        Double minutes = numero / 60;
        Double hours =  numero / 3600;
        Double years = numero * (3.169 * (Math.pow(10,-8)));

        return new Double[]{seconds, minutes, hours, years};
    }

    private Double[] calcularMinutos(Double numero){
        Double seconds = numero * 60;
        Double minutes = numero;
        Double hours = numero * (1.667 * (Math.pow(10,-2)));
        Double years = numero * (1.901 * (Math.pow(10,-6)));

        return new Double[]{seconds, minutes, hours, years};
    }

    private Double[] calcularHoras(Double numero){
        Double seconds = numero * 3600;
        Double minutes = numero * 60;
        Double hours =  numero;
        Double years = numero * (1.141 * (Math.pow(10,-4)));

        return new Double[]{seconds, minutes, hours, years};
    }

    private Double[] calcularAnos(Double numero){
        Double seconds = numero * (3.156 * (Math.pow(10,7)));
        Double minutes = numero * (5.260 * (Math.pow(10,5)));
        Double hours = numero * (8.766 * (Math.pow(10,3)));
        Double years = numero;

        return new Double[]{seconds, minutes, hours, years};
    }

    @SuppressLint("DefaultLocale")
    private void formatResults(Double[] results){
            DecimalFormat df = new DecimalFormat("#.00");
            this.formatResults = new String[5];
            for(int i = 0; i < results.length; i++){
                this.formatResults[i] = String.format("%.2f",results[i]);
            }
    }

    private void llenarSpinner(){
        spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, this.units);
        spinner.setAdapter(arrayAdapter);
    }

    private void getUnits(String lang){
        if (lang.equals("es")) this.units = new String[]{"Segundos", "Minutos", "Horas", "Años"};
        else if (lang.equals("en"))  this.units = new String[]{"Seconds", "Minutes", "Hours", "Years"};
        else this.units = new String[]{"Segons", "Minuts", "Hores", "Anys"};
    }

    private void getMesageError(){
        if (lang.equals("es")) this.mesageError = "Tienes que escribir un número";
        else if (lang.equals("en"))  this.mesageError = "You have to write a number";
        else this.mesageError = "Has d'introduir un número";
    }



}