package schedule.bus.oxford.oxfordbusschedule;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by philipp on 01.10.17.
 */

public class BusstopManager {
    private ArrayList<Busstop> busstops = new ArrayList<>();
    private ArrayList<String> showLanes = new ArrayList<>();
    private Context context;

    public BusstopManager(Context context){
        busstops = new BusStopImporter(context).getBusstops();
        this.context = context;
    }

    public void update(){
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
                ret.add(busstop.getInfo());
        }
        return ret;
    }

    public ArrayList<Integer> getIdFromInfo(String text) {
        for(Busstop stop : busstops){
            if(stop.getInfo().equals(text)) return stop.id;
        }
        return null;
    }

    public boolean showLane(String lane){
        String[] splitted = lane.split(" ");
        return showLanes.contains(splitted[0]);
    }
}
