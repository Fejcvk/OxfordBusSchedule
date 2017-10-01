package schedule.bus.oxford.oxfordbusschedule;

<<<<<<< HEAD
=======
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.MultiAutoCompleteTextView;

>>>>>>> dc296c3f9a045957fa68e580c14a537878198c75
/**
 * Created by Tomasz on 01/10/2017.
 */

<<<<<<< HEAD
public class busStopInputActivity {
=======
public class busStopInputActivity extends AppCompatActivity {
    String[] autocomplete = {"hit1","podpowidz","adolf","hitler"};
    MultiAutoCompleteTextView multiAutoCompleteTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_stop_activity);
        multiAutoCompleteTextView = (MultiAutoCompleteTextView)findViewById(R.id.multiAutoCompleteTextView);
        ArrayAdapter adapter = new
                ArrayAdapter(this,android.R.layout.simple_list_item_1,autocomplete);
        multiAutoCompleteTextView.setAdapter(adapter);
        multiAutoCompleteTextView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
    }
>>>>>>> dc296c3f9a045957fa68e580c14a537878198c75
}
