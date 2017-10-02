package schedule.bus.oxford.oxfordbusschedule;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by philipp on 01.10.17.
 */

public class GetRequest extends AsyncTask<String, Integer, String> {
    busStopInputActivity callback;
    ArrayList<TimetableEntry> entries = new ArrayList<>();

    public GetRequest(busStopInputActivity callback){
        this.callback = callback;
    }


    protected void onProgressUpdate(Integer... progress) {
    }

    @Override
    protected String doInBackground(String... strings) {
        String page = getPage(strings);
        entries = getTimetableEntries(page);
        return "";
    }

    private ArrayList<TimetableEntry> getTimetableEntries(String page) {
        ArrayList<TimetableEntry> entries = new ArrayList<>();
        //<tr[\s\S]*?<\/tr>
        //<tr class=\\\"rowServiceDeparture\\\"[\s\S]*?<\/tr>
        Pattern p = Pattern.compile("<tr class=\\\\\"rowServiceDeparture\\\\\"[\\s\\S]*?</tr>");
        Matcher m = p.matcher(page);
        int lastMatchPos = 0;
        while (m.find()) {
            String tr = m.group(0);
            Pattern p2 = Pattern.compile(">([a-zA-Z0-9](.*?))<");
            Matcher m2 = p2.matcher(tr);
            m2.find();
            String lane = m2.group(0).replaceAll(">","").replaceAll("<","").trim();
            m2.find();
            String destination = m2.group(0).replaceAll(">","").replaceAll("<","").trim();
            m2.find();
            String time = m2.group(0).replaceAll(">","").replaceAll("<","").trim();
            entries.add(new TimetableEntry(lane, destination, time));

            lastMatchPos = m.end();
        }
        return entries;
    }

    private String getPage(String... strings){
        URL url = null;
        try {
            url = new URL("http://www.buscms.com/api/REST/html/departureboard.aspx?callback=buscms_widget_departureboard_ui_displayStop_Callback&clientid=Nimbus&stopid="+strings[0]+"&format=jsonp&servicenamefilder=&cachebust=123&sourcetype=siri&requestor=Netescape&includeTimestamp=true");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.connect();
            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String content = "", line;
            while ((line = rd.readLine()) != null) {
                content += line + "\n";
            }
            return content;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onPostExecute(String result) {
        callback.updateTimetable(entries);
    }
}