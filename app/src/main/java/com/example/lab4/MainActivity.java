package com.example.lab4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

//    editText
    private EditText getInputC;
    private EditText getInputT;
    private EditText getInputX1;
    private EditText getInputX2;
    private EditText getInputStep;
//    button
    private Button getShowGraph;
    private Button getShowTable;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //textView
        getInputC = (EditText) findViewById(R.id.inputC);
        getInputT = (EditText) findViewById(R.id.inputT);
        getInputX1 = (EditText) findViewById(R.id.inputX1);
        getInputX2 = (EditText) findViewById(R.id.inputX2);
        getInputStep = (EditText) findViewById(R.id.inputStep);

        //button
        getShowGraph = (Button) findViewById(R.id.showGraph);
        getShowTable = (Button) findViewById(R.id.showTable);
    }

//    open activity


    public void openGraphActivity(View v){
        Intent intent = new Intent(this, GraphActivity.class);
        intent.putExtra("varT", getInputT.getText().toString());
        intent.putExtra("varC", getInputC.getText().toString());
        intent.putExtra("varStep", getInputStep.getText().toString());
        intent.putExtra("varX1", getInputX1.getText().toString());
        intent.putExtra("varX2", getInputX2.getText().toString());
        startActivity(intent);
    }

    public void openTableActivity(View v){
        Intent intent = new Intent(this, TableActivity.class);
        startActivity(intent);
    }

//    main functiuon
    public void inputsClear(View v){
//        clear editText
        getInputC.setText("");
        getInputT.setText("");
        getInputX1.setText("");
        getInputX2.setText("");
        getInputStep.setText("");
//        disable Battons
        getShowGraph.setEnabled(false);
        getShowTable.setEnabled(false);

        String mess="Поля очищено";
        showResultMessage(mess);
    }

    public void onCalculate(View v){
//        button disable
        getShowGraph.setEnabled(false);
        getShowTable.setEnabled(false);
        String mess="";
//        editText isemptytest
        mess+=emptyTest();

        if (mess.isEmpty())
            try {
//                try convert edittext
                double t=Double.parseDouble(getInputT.getText().toString());
                double c=Double.parseDouble(getInputC.getText().toString());
                int x1=Integer.parseInt(getInputX1.getText().toString());
                int x2=Integer.parseInt(getInputX2.getText().toString());
                double step=Double.parseDouble(getInputStep.getText().toString());
//                information test
                if((c+t)<0)
                    mess="Вираз під коренем не може бути менше нуля";
                else if((c+t)==0)
                    mess="На нуль ділити не можна";
                else if(c==0)
                    mess="ctg від 0 не існує";
                else if(x1>x2)
                    mess="Поміняйте місцями х1 та х2";
                else if(step<=0)
                    mess="крок не коректний";
                else
                {
                    String saveResults="";
//                    calculating and saving area results
                    int iterationCount=0;
                    for(double i=x1;i<=x2;i+=step)
                    {
                        iterationCount++;
                        double result=Math.pow((1/Math.tan(c)),2)+((2*Math.pow(i,2)+5)/(Math.sqrt(c+t)));
                        String formattedDouble = new DecimalFormat("#0.00").format(result);
                        String formattedDouble1 = new DecimalFormat("#0.00").format(i);
                        String line=iterationCount+"\t\t\t|"+formattedDouble1+"\t\t|"+formattedDouble+"|";
                        if(i<x2)
                            saveResults+=line+"\n";
                        else saveResults+=line;
                    }
//                    button enabled
                    getShowGraph.setEnabled(true);
                    getShowTable.setEnabled(true);
                    mess="Операція успішна";
//                    try save info int txt file
                    try {
                        FileOutputStream fileoutput = openFileOutput("save.txt", MODE_PRIVATE);
                        fileoutput.write(saveResults.getBytes());
                        fileoutput.close();
                    }
                    catch (FileNotFoundException e){
                        showResultMessage("файл для запису не знайдено");
                    }
                    catch (IOException e){
                        showResultMessage("Не вдалось записати");
                    }
                }
            }
            catch (Exception e){
                mess = "Не вдалося конвертувати данні";
            }
        showResultMessage(mess);
    }



//    test function
    private String emptyTest(){
        String mess="";
        if(getInputT.getText().toString().isEmpty())
            mess="Т не вказано";
        if(getInputC.getText().toString().isEmpty())
            if (mess.isEmpty())
                mess+="C не вказано";
            else
                mess+="\n C не вказано ";
        if(getInputX1.getText().toString().isEmpty())
            if (mess.isEmpty())
                mess+="X1 не вказано";
            else
                mess+="\n X1 не вказано ";
        if(getInputX2.getText().toString().isEmpty())
            if (mess.isEmpty())
                mess+="X2 не вказано";
            else
                mess+="\n X2 не вказано ";
        if(getInputStep.getText().toString().isEmpty())
            if (mess.isEmpty())
                mess+="Крок не вказано";
            else
                mess+="\n Крок не вказано ";
        return mess;
    }


//    toast function
    private void showResultMessage(String mess){
        LayoutInflater inflater=getLayoutInflater();
        View layout =inflater.inflate(R.layout.messege_toast, (ViewGroup) findViewById(R.id.showMessage));

        TextView message = layout.findViewById(R.id.message);
        message.setText(mess.toString());

        Toast toast = new Toast(getApplicationContext());

        toast.setGravity(Gravity.CENTER, 0,0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
    public void autorInfoToast(View v){
        LayoutInflater inflater=getLayoutInflater();
        View layout =inflater.inflate(R.layout.autor_info_toast, (ViewGroup) findViewById(R.id.authorInfoToast));

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER, 0,0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
}