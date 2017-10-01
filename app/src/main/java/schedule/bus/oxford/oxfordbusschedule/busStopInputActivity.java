package schedule.bus.oxford.oxfordbusschedule;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tomasz on 01/10/2017.
 */


public class busStopInputActivity extends AppCompatActivity {
    BusstopManager busstopmanager;
    String[] autocomplete;
    AutoCompleteTextView autoCompleteTextView;
    ListView listView;
    ArrayAdapter adapter;
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
    }
    void listViewFillUp(ListView lV)
    {
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
        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,list);
        lV.setAdapter(adapter);
    }
}
