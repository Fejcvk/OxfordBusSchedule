package schedule.bus.oxford.oxfordbusschedule;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by philipp on 01.10.17.
 */

public class BusstopManager {
    private ArrayList<Busstop> busstops = new ArrayList<>();
    private ArrayList<String> showLanes = new ArrayList<>();

    public BusstopManager(Context context){
        showLanes.add("U1");
        busstops = new BusStopImporter(context).getBusstops();
    }

    public void addLane(String lane){
        showLanes.add(lane);
    }

    public void removeLane(String lane){
        showLanes.remove(lane);
    }

    public String[] getBusStopNames(){
        ArrayList<String> b = new ArrayList<>();
        for(Busstop busstop : busstops){
            if(showLanes.contains(busstop.lane))
                b.add(busstop.getInfo());
        }
        String[] ret = new String[b.size()];
        ret = b.toArray(ret);
        return ret;
    }

}
