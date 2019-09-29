package id.ac.ui.cs.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView timeStamp;
    Button btnStart, btnPause, btnReset, btnSaveLap, btnExit;
    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L ;
    Handler handler = new Handler();
    int Seconds, Minutes, MilliSeconds ;
    ListView history;
    String[] ListElements = new String[] {  };
    List<String> ListElementsArrayList ;
    ArrayAdapter<String> adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timeStamp = findViewById(R.id.timeStamp);
        btnStart = findViewById(R.id.btnStart);
        btnPause = findViewById(R.id.btnPause);
        btnReset = findViewById(R.id.btnReset);
        btnSaveLap = findViewById(R.id.btnSaveLap);
        btnExit = findViewById(R.id.btnExit);
        history = findViewById(R.id.history);
        ListElementsArrayList = new ArrayList<>(Arrays.asList(ListElements));
        adapter = new ArrayAdapter<>(MainActivity.this,
            android.R.layout.simple_list_item_1,
            ListElementsArrayList
        );
        history.setAdapter(adapter);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartTime = SystemClock.uptimeMillis();
                handler.postDelayed(runnable, 0);
                btnReset.setEnabled(false);
            }
        });
        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimeBuff += MillisecondTime;
                handler.removeCallbacks(runnable);
                btnReset.setEnabled(true);
            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MillisecondTime = 0L ;
                StartTime = 0L ;
                TimeBuff = 0L ;
                UpdateTime = 0L ;
                Seconds = 0 ;
                Minutes = 0 ;
                MilliSeconds = 0 ;
                timeStamp.setText("00:00:00");
                ListElementsArrayList.clear();
                adapter.notifyDataSetChanged();
            }
        });
        btnSaveLap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            ListElementsArrayList.add(timeStamp.getText().toString());
            adapter.notifyDataSetChanged();
            }
        });
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onBackPressed(){
        Toast.makeText(this, "Please click exit button", Toast.LENGTH_SHORT).show();
    }

    public Runnable runnable = new Runnable() {
        public void run() {
            MillisecondTime = SystemClock.uptimeMillis() - StartTime;
            UpdateTime = TimeBuff + MillisecondTime;
            Seconds = (int) (UpdateTime / 1000);
            Minutes = Seconds / 60;
            Seconds = Seconds % 60;
            MilliSeconds = (int) (UpdateTime % 1000);
            timeStamp.setText(String.format("%d:%s:%s", Minutes, String.format("%02d", Seconds), String.format("%03d", MilliSeconds)));
            handler.postDelayed(this, 0);
        }

    };
}
