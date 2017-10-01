package schedule.bus.oxford.oxfordbusschedule;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by philipp on 01.10.17.
 */

public class BusStopImporter extends AppCompatActivity{
    private ArrayList<Busstop> busstops = new ArrayList<>();

    public BusStopImporter(Context context){
        init(context);
    }


    public ArrayList<Busstop> getBusstops() {
        return busstops;
    }

    public void init(Context context) {
        AssetManager assetManager = context.getAssets();

        try {
            InputStream csvStream = assetManager.open("stops.csv");
            InputStreamReader csvStreamReader = new InputStreamReader(csvStream);
            BufferedReader reader=new BufferedReader(csvStreamReader);
            String line = null;
            while((line = reader.readLine()) != null) {
                System.out.println(line);
                String[] splitted = line.split(";");
                busstops.add(new Busstop(Integer.parseInt(splitted[0]), splitted[1], splitted[2], splitted[3]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[] getBusStopNames(){
        ArrayList<String> b = new ArrayList<>();
        for(Busstop busstop : busstops){
            b.add(busstop.getInfo());
        }
        String[] ret = new String[b.size()];
        ret = b.toArray(ret);
        return ret;
    }
}
