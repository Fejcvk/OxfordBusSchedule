package schedule.bus.oxford.oxfordbusschedule;

/**
 * Created by philipp on 01.10.17.
 */

class TimetableEntry {
    String lane;
    String destination;
    String time;


    public TimetableEntry(String lane, String destination, String time) {
        this.lane = lane;
        this.destination = destination;
        this.time = time;
    }

    public int getRemainingMinutes() {
        try{
            String[] splitted = time.split(" ");
            int mins = Integer.parseInt(splitted[0]);
            return mins;
        }catch(Exception e){
            return 0;
        }
    }
}
