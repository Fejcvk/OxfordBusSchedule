package schedule.bus.oxford.oxfordbusschedule;

import java.util.ArrayList;

/**
 * Created by philipp on 01.10.17.
 */

public class Busstop {
    public ArrayList<Integer> id = new ArrayList<>();
    public String stationname;
    public String destination;

    public Busstop(int id, String stationname, String destination) {
        this.id.add(id);
        this.stationname = stationname;
        this.destination = destination;
    }

    public String getInfo() {
        return stationname;
    }
}
