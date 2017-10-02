package schedule.bus.oxford.oxfordbusschedule;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.AutoCompleteTextView;

import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Tomasz on 01/10/2017.
 */


public class busStopInputActivity extends AppCompatActivity {
    BusstopManager busstopmanager;
    AutoCompleteTextView autoCompleteTextView;
    ListView listView;

    ArrayList<String> autocomplete;
    CheckBox checkU1;
    CheckBox checkU5;
    CheckBox check8;
    ArrayAdapter<String> adapter;
    ArrayList<TimetableEntry> entries;
    ArrayList<Integer> selectedBusstopIds;
    int counter = 0;

    ProgressDialog progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        busstopmanager = new BusstopManager(this.getBaseContext());
        autocomplete = busstopmanager.getBusStopNames();
        setContentView(R.layout.input_stop_activity);
        autoCompleteTextView = (AutoCompleteTextView)findViewById(R.id.autoCompleteTextView);
        adapter = new
                ArrayAdapter(this,android.R.layout.simple_list_item_1,autocomplete);
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setThreshold(1);
        listView = (ListView)findViewById(R.id.busListView);
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setThreshold(1);
        checkU1 = (CheckBox)findViewById(R.id.checkboxU1);
        checkU5 = (CheckBox)findViewById(R.id.checkboxU5);
        check8 = (CheckBox)findViewById(R.id.checkbox8);
        checkU1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    busstopmanager.addLane("U1");
                    busstopmanager.addLane("U1X");
                    busstopmanager.addLane("NU1");
                }else{
                    busstopmanager.removeLane("U1");
                    busstopmanager.removeLane("U1X");
                    busstopmanager.removeLane("NU1");
                }

                updateList();
            }
        });

        checkU5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    busstopmanager.addLane("U5");
                    busstopmanager.addLane("NU5");
                }else{
                    busstopmanager.removeLane("U5");
                    busstopmanager.removeLane("NU5");
                }
                updateList();
            }
        });

        check8.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    busstopmanager.addLane("8");
                    busstopmanager.addLane("X8");
                }else{
                    busstopmanager.removeLane("8");
                    busstopmanager.removeLane("X8");
                }
                updateList();
            }
        });

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos,
                                    long id) {
                selectedBusstopIds = busstopmanager.getIdFromInfo(parent.getItemAtPosition(pos)+"");
                updateList();
            }
        });

        final SwipeRefreshLayout mySwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        busstopmanager.update();
                        updateList();
                        mySwipeRefreshLayout.setRefreshing(false);
                    }
                }
        );



    }

    private void parseNextStops(ArrayList<Integer> ids){
        entries = new ArrayList<>();
//        progress = new ProgressDialog(this);
//        progress.setTitle("Loading");
//        progress.setMessage("Wait while loading...");
//        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
//        progress.show();
//        progress.dismiss();
        if(ids != null){
            for(Integer id : ids){
                try {
                    if(counter==0){
                        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressbar);
                        progressBar.setVisibility(View.VISIBLE);
                    }
                    counter++;
                    getPage((int)id);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void getPage(int id) throws IOException {
        new GetRequest(this, entries).execute(id+"");
    }

    public void updateTimetable(ArrayList<TimetableEntry> entry){
        ListView lv = (ListView)findViewById(R.id.busListView);
        listViewFillUp(lv, entry);
    }

    private void listViewFillUp(ListView lV, ArrayList<TimetableEntry> entries) {
        Collections.sort(entries, new Comparator<TimetableEntry>() {
            @Override
            public int compare(TimetableEntry o1, TimetableEntry o2) {
                if(o1.getRemainingMinutes() == o2.getRemainingMinutes()){
                    return 0;
                }else if(o1.getRemainingMinutes() < o2.getRemainingMinutes()){
                    return -1;
                }else{
                    return 1;
                }
            }
        });

        List list = new ArrayList();

        for(TimetableEntry entry : entries){
            if(busstopmanager.showLane(entry.lane) && !list.contains(entry.lane+" "+entry.destination+" "+entry.time))
                list.add(entry.lane+" "+entry.destination+" "+entry.time);
        }

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        lV.setAdapter(adapter);
        counter--;
        if(counter == 0){
            ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressbar);
            progressBar.setVisibility(View.INVISIBLE);
        }

    }
    private void updateList() {
        parseNextStops(selectedBusstopIds);
//        autocomplete = busstopmanager.getBusStopNames();
//        adapter.clear();
//        adapter.addAll(autocomplete);
//        adapter.notifyDataSetChanged();
//        autoCompleteTextView.setText(autoCompleteTextView.getText()+"");
//        autoCompleteTextView.setSelection(autoCompleteTextView.length());
//        autoCompleteTextView.showDropDown();

    }
}
