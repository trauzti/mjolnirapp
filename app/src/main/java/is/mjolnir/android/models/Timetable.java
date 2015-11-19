package is.mjolnir.android.models;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import is.mjolnir.android.models.timetable.Time;
import is.mjolnir.android.models.timetable.TimetableResponse;

public class Timetable {

    public static final String TAG = "Timetable";

    public static boolean needToRefreshRejectedClasses = false;

    public static TimetableResponse timetableResponse = null;

    public static List<MjolnirClass> allClasses = null;

    public static List<MjolnirClass> getAllClasses() {
        if (allClasses != null) {
            return allClasses;
        }
        allClasses = new ArrayList<>();

        HashMap<String, Boolean> classMap = new HashMap<>();

        for (Time time: timetableResponse.mon) {
            String title = time.title;
            if (!classMap.containsKey(title)) {
                allClasses.add(new MjolnirClass(time.title));
                classMap.put(time.title, true);
            }
        }
        for (Time time: timetableResponse.tue) {
            String title = time.title;
            if (!classMap.containsKey(title)) {
                allClasses.add(new MjolnirClass(time.title));
                classMap.put(time.title, true);
            }
        }
        for (Time time: timetableResponse.wed) {
            String title = time.title;
            if (!classMap.containsKey(title)) {
                allClasses.add(new MjolnirClass(time.title));
                classMap.put(time.title, true);
            }
        }
        for (Time time: timetableResponse.thu) {
            String title = time.title;
            if (!classMap.containsKey(title)) {
                allClasses.add(new MjolnirClass(time.title));
                classMap.put(time.title, true);
            }
        }
        for (Time time: timetableResponse.fri) {
            String title = time.title;
            if (!classMap.containsKey(title)) {
                allClasses.add(new MjolnirClass(time.title));
                classMap.put(time.title, true);
            }
        }
        for (Time time: timetableResponse.sat) {
            String title = time.title;
            if (!classMap.containsKey(title)) {
                allClasses.add(new MjolnirClass(time.title));
                classMap.put(time.title, true);
            }
        }
        for (Time time: timetableResponse.sun) {
            String title = time.title;
            if (!classMap.containsKey(title)) {
                allClasses.add(new MjolnirClass(time.title));
                classMap.put(time.title, true);
            }
        }
        return allClasses;
    }


    private static HashMap<Integer, List<ClassAndTime>> memoizedClasses = new HashMap<>();

    public static List<ClassAndTime> getClassesForRoom(ArrayList<Time> times, int roomNumber) {
        List<ClassAndTime> classes = new ArrayList<ClassAndTime>();
        for (Time time : times) {
            if (time.room.contains(Integer.toString(roomNumber))) {
                classes.add(new ClassAndTime(time.time, time.title));
            }
        }
        return classes;
    }


    public static List<ClassAndTime> getClassesForWeekday(final int theWeekday, final int theRoom) {

        List<ClassAndTime> classes = new ArrayList<ClassAndTime>(); // prevent: Attempt to invoke interface method 'java.util.Iterator java.util.List.iterator()' on a null object reference
        if (timetableResponse == null) {
            Log.d(TAG, "timetableResponse == null");
        }

        if (theWeekday < Calendar.SUNDAY || theWeekday > Calendar.SATURDAY) {
            return classes;
        }

        int lookupNumber = theWeekday * 10 + theRoom;
        if (memoizedClasses.containsKey(lookupNumber)) {
            //Log.d("Timetable", "Using memoization!!!");
            return memoizedClasses.get(lookupNumber);
        }

        ArrayList<Time> times = null;

        switch(theWeekday) {
            case Calendar.MONDAY:
                times = timetableResponse.mon;
                break;
            case Calendar.TUESDAY:
                times = timetableResponse.tue;
                break;
            case Calendar.WEDNESDAY:
                times = timetableResponse.wed;
                break;
            case Calendar.THURSDAY:
                times = timetableResponse.thu;
                break;
            case Calendar.FRIDAY:
                times = timetableResponse.fri;
                break;
            case Calendar.SATURDAY:
                times = timetableResponse.sat;
                break;
            case Calendar.SUNDAY:
                times = timetableResponse.sun;
                break;
            default:
                times = new ArrayList<>();
                break;
        }

        classes = getClassesForRoom(times, theRoom);

        memoizedClasses.put(lookupNumber, classes);
        return classes;
    }

    public static Map<String, Boolean> rejectedMap;

    public static void ensureRejectedMapNotNull(Context context) {
        if (rejectedMap == null || rejectedMap.size() == 0) {
            loadRejectedClasses(context);
        }
    }

    public static void loadRejectedClasses(Context context) {
        if (rejectedMap == null) {
            rejectedMap = new HashMap<String, Boolean>();
        }

        SharedPreferences keyValues = context.getSharedPreferences("mjolnir", Context.MODE_PRIVATE);

        if (timetableResponse == null) {
            Log.d(TAG, "cannot fill in rejectdMap because timetableResponse == null");
            return;
        }

        for (Time time: timetableResponse.mon) {
            boolean rejected = keyValues.getBoolean(time.title, false);
            rejectedMap.put(time.title, rejected);
        }
        for (Time time: timetableResponse.tue) {
            boolean rejected = keyValues.getBoolean(time.title, false);
            rejectedMap.put(time.title, rejected);
        }
        for (Time time: timetableResponse.wed) {
            boolean rejected = keyValues.getBoolean(time.title, false);
            rejectedMap.put(time.title, rejected);
        }
        for (Time time: timetableResponse.thu) {
            boolean rejected = keyValues.getBoolean(time.title, false);
            rejectedMap.put(time.title, rejected);
        }
        for (Time time: timetableResponse.fri) {
            boolean rejected = keyValues.getBoolean(time.title, false);
            rejectedMap.put(time.title, rejected);
        }
        for (Time time: timetableResponse.sat) {
            boolean rejected = keyValues.getBoolean(time.title, false);
            rejectedMap.put(time.title, rejected);
        }
        for (Time time: timetableResponse.sun) {
            boolean rejected = keyValues.getBoolean(time.title, false);
            rejectedMap.put(time.title, rejected);
        }
    }


    public static void saveRejectedClasses(final Context context) {

        new Thread() {
            @Override
            public void run() {
                SharedPreferences keyValues = context.getSharedPreferences("mjolnir", Context.MODE_PRIVATE);
                SharedPreferences.Editor keyValuesEditor = keyValues.edit();

                for (String s : rejectedMap.keySet()) {
                    keyValuesEditor.putBoolean(s, rejectedMap.get(s));
                }

                keyValuesEditor.commit();
            }
        }.start();
    }

}

