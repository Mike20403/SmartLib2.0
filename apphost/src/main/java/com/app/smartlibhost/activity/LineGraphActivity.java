package com.app.smartlibhost.activity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.app.smartlibhost.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LineGraphActivity extends AppCompatActivity implements OnChartValueSelectedListener {
    private LineChart mChart;
    private Toolbar tb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line__graph);
        Anhxa();
        ActionToolbar();

        mChart = findViewById(R.id.combinedChart);
        mChart.getDescription().setEnabled(false);
        mChart.setBackgroundColor(Color.WHITE);
        mChart.setDrawGridBackground(false);
        mChart.setOnChartValueSelectedListener(this);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisMinimum(0f);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(0f);

        final List<String> xLabel = new ArrayList<>();
        xLabel.add("Jan");
        xLabel.add("Feb");
        xLabel.add("Mar");
        xLabel.add("Apr");
        xLabel.add("May");
        xLabel.add("Jun");
        xLabel.add("Jul");
        xLabel.add("Aug");
        xLabel.add("Sep");
        xLabel.add("Oct");
        xLabel.add("Nov");
        xLabel.add("Dec");

        XAxis xAxis = mChart.getXAxis();

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0f);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return xLabel.get((int) value % xLabel.size());
            }
        });

        xAxis.setAxisMaximum(generateDataLine().getXMax() + 0.25f);
        mChart.setData((LineData) generateDataLine());
        mChart.animateX(1000, Easing.EasingOption.EaseInBack);
        mChart.invalidate();
    }

    private void Anhxa() {
        tb = findViewById(R.id.statistic);
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Toast.makeText(this, "Value: " + e.getY() + ", index: " + h.getX() + ", DataSet index: " + h.getDataSetIndex(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected() {
    }

    private LineData generateDataLine() {
        ArrayList<Entry> values1 = new ArrayList<>();

        for (int i = 0; i < 12; i++) {
            values1.add(new Entry(i, (int) (Math.random() * 65) + 40));
        }

        LineDataSet d1 = new LineDataSet(values1, "Lượt mượn (1)");
        setupLineDataSet(d1, ColorTemplate.VORDIPLOM_COLORS[0]);

        ArrayList<Entry> values2 = new ArrayList<>();

        for (int i = 0; i < 12; i++) {
            values2.add(new Entry(i, (values1.get(i).getY()) - getRandomIntInBetween(1, (int) values1.get(i).getY())));
        }

        LineDataSet d2 = new LineDataSet(values2, "Lượt trả (2)");
        setupLineDataSet(d2, Color.RED);

        ArrayList<ILineDataSet> sets = new ArrayList<>();
        sets.add(d1);
        sets.add(d2);

        for (ILineDataSet iSet : sets) {
            LineDataSet set = (LineDataSet) iSet;
            set.setDrawValues(!set.isDrawValuesEnabled());
        }

        for (ILineDataSet iSet : sets) {
            LineDataSet set = (LineDataSet) iSet;
            if (set.isDrawFilledEnabled()) {
                set.setDrawFilled(false);
            } else {
                set.setDrawFilled(true);
            }
        }

        return new LineData(sets);
    }

    private void setupLineDataSet(LineDataSet dataSet, int lineColor) {
        dataSet.setLineWidth(2.5f);
        dataSet.setCircleRadius(4.5f);
        dataSet.setHighLightColor(Color.rgb(244, 117, 117));
        dataSet.setColor(lineColor);
        dataSet.setCircleColor(lineColor);
        dataSet.setDrawValues(false);

        dataSet.setValueTextSize(9f);
        Drawable drawable = ContextCompat.getDrawable(this, lineColor == Color.RED ? R.drawable.fade_red : R.drawable.fade_green);
        dataSet.setFillDrawable(drawable);
    }

    public void ActionToolbar() {
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public int getRandomIntInBetween(int min, int max) {
        Random r = new Random();
        return min + r.nextInt(max - min);
    }
}
