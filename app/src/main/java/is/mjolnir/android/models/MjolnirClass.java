package is.mjolnir.android.models;

/**
 * Created by traustis on 3/25/15.
 */
public class MjolnirClass {
    
    public String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isRejected() {
        return Timetable.rejectedMap.get(name);
    }

    public void setRejected(boolean rejected) {
        Timetable.needToRefreshRejectedClasses = true;
        Timetable.rejectedMap.put(name, rejected);
    }

    public MjolnirClass(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }


}
