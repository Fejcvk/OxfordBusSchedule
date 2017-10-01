package schedule.bus.oxford.oxfordbusschedule;

/**
 * Created by philipp on 01.10.17.
 */

public class Busstop {
    public int id;
    public String stationname;
    public String lane;
    public String lastStop;

    public Busstop(int id, String stationname, String lane, String lastStop) {
        this.id = id;
        this.stationname = stationname;
        this.lane = lane;
        this.lastStop = lastStop;
    }

    public String getInfo() {
        return lane+" "+stationname+" (to "+lastStop+")";
    }
}
