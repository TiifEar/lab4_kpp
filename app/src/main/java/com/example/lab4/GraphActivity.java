package com.example.lab4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class GraphActivity extends AppCompatActivity {
    private GraphView getGraph;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        getGraph = (GraphView) findViewById(R.id.graph);
        Intent intent = getIntent();

        double getT = Double.parseDouble(intent.getStringExtra("varT"));
        double getC = Double.parseDouble(intent.getStringExtra("varC"));
        double getStep = Double.parseDouble(intent.getStringExtra("varStep"));
        int getX1 = Integer.parseInt(intent.getStringExtra("varX1"));
        int getX2 = Integer.parseInt(intent.getStringExtra("varX2"));
        onDrowGraph(getX1,getX2,getC,getT,getStep);
    }


    private void onDrowGraph(int x1, int x2, double c, double t, double step) {
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
        double y;
        int iterationCount=0;
        for(int i=x1;i<=x2;i+=step){
            iterationCount++;
        }
        for(int i=x1;i<=x2;i+=step){
            y=Math.pow((1/Math.tan(c)),2)+((2*Math.pow(i,2)+5)/(Math.sqrt(c+t)));
            series.appendData(new DataPoint(i,y),true,iterationCount);
        }
        getGraph.addSeries(series);
    }

}