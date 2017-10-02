package schedule.bus.oxford.oxfordbusschedule;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by philipp on 01.10.17.
 */

public class BusstopManager {
    private ArrayList<Busstop> busstops = new ArrayList<>();
    private ArrayList<String> showLanes = new ArrayList<>();

    public BusstopManager(Context context){
        busstops = new BusStopImporter(context).getBusstops();
    }

    public void addLane(String lane){
        showLanes.add(lane);
    }

    public void removeLane(String lane){
        showLanes.remove(lane);
    }

    public ArrayList<String> getBusStopNames(){
        ArrayList<String> ret = new ArrayList<>();
        for(Busstop busstop : busstops){
            if(showLanes.contains(busstop.lane))
                ret.add(busstop.getInfo());
        }
        return ret;
    }

    public int getIdFromInfo(String text) {
        for(Busstop stop : busstops){
            if(stop.getInfo().equals(text)) return stop.id;
        }
        return 0;
    }
}
