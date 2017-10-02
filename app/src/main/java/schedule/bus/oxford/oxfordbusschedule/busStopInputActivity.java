package schedule.bus.oxford.oxfordbusschedule;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.AutoCompleteTextView;

import android.widget.ListView;

import java.util.ArrayList;
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
    ArrayAdapter listViewAdapter;

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
        listViewFillUp(listView);
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
                }else{
                    busstopmanager.removeLane("8");
                }
                updateList();
            }
        });

    }

    private void listViewFillUp(ListView lV) {
        List list = new ArrayList();
        list.add("KUTAS");
        list.add("KUTAS");
        list.add("KUTAS");
        list.add("KUTAS");
        list.add("KUTAS");
        list.add("KUTAS");
        list.add("KUTAS");
        list.add("KUTAS");
        list.add("KUTAS");
        list.add("KUTAS");
        list.add("KUTAS");
        list.add("KUTAS");
        list.add("KUTAS");
        listViewAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        lV.setAdapter(listViewAdapter);
    }
    private void updateList() {
        autocomplete = busstopmanager.getBusStopNames();
        adapter.clear();
        adapter.addAll(autocomplete);
        adapter.notifyDataSetChanged();
        autoCompleteTextView.setText(autoCompleteTextView.getText()+"");
        autoCompleteTextView.setSelection(autoCompleteTextView.length());
        autoCompleteTextView.showDropDown();

    }
}
