package schedule.bus.oxford.oxfordbusschedule;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.MultiAutoCompleteTextView;

/**
 * Created by Tomasz on 01/10/2017.
 */


public class busStopInputActivity extends AppCompatActivity {
    String[] autocomplete;
    MultiAutoCompleteTextView multiAutoCompleteTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        autocomplete = new BusStopImporter(this.getBaseContext()).getBusStopNames();
        setContentView(R.layout.input_stop_activity);
        multiAutoCompleteTextView = (MultiAutoCompleteTextView)findViewById(R.id.multiAutoCompleteTextView);
        ArrayAdapter adapter = new
                ArrayAdapter(this,android.R.layout.simple_list_item_1,autocomplete);
        multiAutoCompleteTextView.setAdapter(adapter);
        multiAutoCompleteTextView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
    }
}
