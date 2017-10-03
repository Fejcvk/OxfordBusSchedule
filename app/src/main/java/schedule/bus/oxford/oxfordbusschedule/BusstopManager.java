package schedule.bus.oxford.oxfordbusschedule;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by philipp on 01.10.17.
 */

public class BusstopManager {
    private ArrayList<busStop> busStops = new ArrayList<>();
    private ArrayList<String> showLanes = new ArrayList<>();
    private Context context;

    public BusstopManager(Context context){
        busStops = new BusStopImporter(context).getBusStops();
        this.context = context;
    }

    public void update(){
        busStops.clear();
        busStops = new BusStopImporter(context).getBusStops();
    }

    public void addLane(String lane){
        showLanes.add(lane);
    }

    public void removeLane(String lane){
        showLanes.remove(lane);
    }

    public ArrayList<String> getBusStopNames(){
        ArrayList<String> ret = new ArrayList<>();
        for(busStop busStop : busStops){
                ret.add(busStop.getInfo());
        }
        return ret;
    }

    public ArrayList<Integer> getIdFromInfo(String text) {
        for(busStop stop : busStops){
            if(stop.getInfo().equals(text)) return stop.id;
        }
        return null;
    }

    public boolean showLane(String lane){
        String[] splitted = lane.split(" ");
        return showLanes.contains(splitted[0]);
    }
}
