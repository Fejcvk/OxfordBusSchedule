package schedule.bus.oxford.oxfordbusschedule;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.AutoCompleteTextView;

import java.util.ArrayList;

/**
 * Created by Tomasz on 01/10/2017.
 */


public class busStopInputActivity extends AppCompatActivity {
    BusstopManager busstopmanager;
    AutoCompleteTextView autoCompleteTextView;
    ArrayList<String> autocomplete;
    MultiAutoCompleteTextView multiAutoCompleteTextView;
    CheckBox checkU1;
    CheckBox checkU5;
    CheckBox check8;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        busstopmanager = new BusstopManager(this.getBaseContext());
        autocomplete = busstopmanager.getBusStopNames();
        setContentView(R.layout.input_stop_activity);
        multiAutoCompleteTextView = (MultiAutoCompleteTextView)findViewById(R.id.multiAutoCompleteTextView);
        adapter = new
                ArrayAdapter<>(this,android.R.layout.simple_list_item_1,autocomplete);
        multiAutoCompleteTextView.setAdapter(adapter);
        multiAutoCompleteTextView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
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

    private void updateList() {
        autocomplete = busstopmanager.getBusStopNames();
        adapter.clear();
        adapter.addAll(autocomplete);
        adapter.notifyDataSetChanged();
        multiAutoCompleteTextView.setText(multiAutoCompleteTextView.getText()+"");
        multiAutoCompleteTextView.setSelection(multiAutoCompleteTextView.length());
        multiAutoCompleteTextView.showDropDown();

    }
}
