package is.mjolnir.android;

//
//  TimetableDataModel.swift
//  Mjölnir
//
//  Created by Stefán Geir Sigfússon on 13.2.2015.
//  Copyright (c) 2015 Stefán Geir. All rights reserved.
//


import android.content.Context;
import android.content.SharedPreferences;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Timetable {

    public static boolean needToRefreshRejectedClasses = false;


    public static MjolnirClass nogistelpur = new MjolnirClass("Nogi stelpur");
    public static MjolnirClass nogi201 = new MjolnirClass("Nogi 201");
    public static MjolnirClass nogi301 = new MjolnirClass("Nogi 301");
    public static MjolnirClass mma101 = new MjolnirClass("MMA 101");
    public static MjolnirClass mma101unglingar = new MjolnirClass("MMA 101 Unglingar");
    public static MjolnirClass mma201 = new MjolnirClass("MMA 201");
    public static MjolnirClass mma201unglingar = new MjolnirClass("MMA 201 Unglingar");
    public static MjolnirClass mmact = new MjolnirClass("MMA CT");
    public static MjolnirClass mjolnir101 = new MjolnirClass("Mjölnir 101");
    public static MjolnirClass bjj301 = new MjolnirClass("BJJ 301");
    public static MjolnirClass bjj201 = new MjolnirClass("BJJ 201");
    public static MjolnirClass bjjct = new MjolnirClass("BJJ CT");
    public static MjolnirClass bjjstelpur = new MjolnirClass("BJJ stelpur");
    public static MjolnirClass openmat = new MjolnirClass("Open Mat");
    public static MjolnirClass vikingathrek = new MjolnirClass("Víkingaþrek");
    public static MjolnirClass extraIntenseVikingathrek = new MjolnirClass("Erfiðara Víkingaþrek (90 mín)");
    public static MjolnirClass vikingathrek101 = new MjolnirClass("Víkingaþrek 101");
    public static MjolnirClass vikingathrekunglingar = new MjolnirClass("Víkingaþrek Unglingar");
    public static MjolnirClass box101 = new MjolnirClass("Box 101");
    public static MjolnirClass box201 = new MjolnirClass("Box 201");
    public static MjolnirClass box301 = new MjolnirClass("Box 301");
    public static MjolnirClass sparr = new MjolnirClass("Sparr");
    public static MjolnirClass kickbox101 = new MjolnirClass("Kickbox 101");
    public static MjolnirClass kickbox201 = new MjolnirClass("Kickbox 201");
    public static MjolnirClass kickbox301 = new MjolnirClass("Kickbox 301");
    public static MjolnirClass born101 = new MjolnirClass("Börn 101");
    public static MjolnirClass born201 = new MjolnirClass("Börn 201");
    public static MjolnirClass yoga101 = new MjolnirClass("Mjölnisyoga 101");
    public static MjolnirClass yoga201 = new MjolnirClass("Mjölnisyoga 201");
    public static MjolnirClass godaafl = new MjolnirClass("Goðaafl");


    public static List<MjolnirClass> getAllClasses() {
        return Arrays.asList(nogistelpur, nogi201, nogi301, mma101, mma101unglingar, mma201, mma201unglingar, mmact, mjolnir101, bjj301, bjj201,
                bjjct, bjjstelpur, openmat, vikingathrek, extraIntenseVikingathrek, vikingathrek101, vikingathrekunglingar, box101, box201, box301,
                sparr, kickbox101, kickbox201, kickbox301, born101, born201, yoga101, yoga201, godaafl);
    }


    /*
    public static List<MjolnirClass> getClassesForRoom(int roomNumber) {
        return getAllClasses();
    }
    */

    public static List<ClassAndTime> getClassesForWeekday(final int theWeekday, final int theRoom) {
        if (theWeekday < 1 || theWeekday > 7) {
            return null;
        }
        List<ClassAndTime> classes = null;

        switch(theRoom) {
            case 1:
                switch(theWeekday) {
                    case 1:
                    case 3:
                        classes = Arrays.asList(new ClassAndTime("12:10",nogi201.name), new ClassAndTime("16:30", born201.name), new ClassAndTime("17:15", mma201unglingar.name), new ClassAndTime("18:00", bjj301.name), new ClassAndTime("19:00", bjj201.name), new ClassAndTime("20:00", kickbox201.name));
                        break;
                    case 2:
                    case 4:
                        classes = Arrays.asList(new ClassAndTime("08:00",bjj201.name), new ClassAndTime("12:10",bjj201.name), new ClassAndTime("17:00",nogi301.name), new ClassAndTime("18:00",mmact.name), new ClassAndTime("20:00",mjolnir101.name));
                        break;
                    case 5:
                        classes = Arrays.asList(new ClassAndTime("12:10", nogi201.name), new ClassAndTime("16:30",born201.name), new ClassAndTime("17:15",mma201unglingar.name), new ClassAndTime("18:00",bjj201.name));
                        break;
                    case 6:
                        classes = Arrays.asList(new ClassAndTime("11:10",bjjct.name), new ClassAndTime("12:10",bjj201.name), new ClassAndTime("13:00",openmat.name));
                        break;
                    case 7:
                        classes = Arrays.asList(new ClassAndTime("12:10",openmat.name), new ClassAndTime("13:00", openmat.name));
                        break;
                    default:
                        classes = Arrays.asList(new ClassAndTime("error","error"));
                        break;
                }
                break;
            case 2:
                switch(theWeekday) {
                    case 1:
                   //     classes = Arrays.asList(new ClassAndTime("06:40",vikingathrek.name), new ClassAndTime("12:10",vikingathrek.name), new ClassAndTime("16:30",vikingathrek.name), new ClassAndTime("17:15",vikingathrek.name), new ClassAndTime("18:00",box301.name), new ClassAndTime("19:00",kickbox301.name), new ClassAndTime("20:00",vikingathrek.name));
                   //     break;
                    case 3:
                        classes = Arrays.asList(new ClassAndTime("06:40", vikingathrek.name), new ClassAndTime("12:10",vikingathrek.name), new ClassAndTime("16:30",vikingathrek.name), new ClassAndTime("17:15",vikingathrek.name), new ClassAndTime("18:00",box301.name), new ClassAndTime("19:00",mma201.name), new ClassAndTime("20:00",vikingathrek.name));
                        break;
                    case 2:
                    case 4:
                        classes = Arrays.asList(new ClassAndTime("08:00",vikingathrek.name), new ClassAndTime("12:10",vikingathrek.name), new ClassAndTime("16:30",born101.name), new ClassAndTime("17:15",vikingathrek.name), new ClassAndTime("18:00",vikingathrek.name), new ClassAndTime("19:00",vikingathrek101.name), new ClassAndTime("20:00",kickbox101.name));
                        break;
                    case 5:
                        classes = Arrays.asList(new ClassAndTime("06:40",vikingathrek.name), new ClassAndTime("12:10",vikingathrek.name), new ClassAndTime("16:30",vikingathrek.name), new ClassAndTime("17:15",vikingathrek.name), new ClassAndTime("18:00",box201.name));
                        break;
                    case 6:
                        classes = Arrays.asList(new ClassAndTime("11:10",vikingathrek.name), new ClassAndTime("12:10",vikingathrek.name), new ClassAndTime("13:00",vikingathrekunglingar.name));
                        break;
                    case 7:
                        classes = Arrays.asList(new ClassAndTime("10:30",extraIntenseVikingathrek.name), new ClassAndTime("12:10",vikingathrek.name), new ClassAndTime("13:00",sparr.name));
                        break;
                    default:
                        classes = Arrays.asList(new ClassAndTime("error","error"));
                        break;

                }
                break;
            case 3:
                switch (theWeekday) {
                    case 1:
                        classes = Arrays.asList(new ClassAndTime("12:10",yoga201.name), new ClassAndTime("17:15",godaafl.name), new ClassAndTime("18:00",mma101.name), new ClassAndTime("19:00",box201.name), new ClassAndTime("20:00",box101.name));
                        break;
                    case 2:
                        classes = Arrays.asList(new ClassAndTime("12:10",yoga101.name), new ClassAndTime("16:30",godaafl.name), new ClassAndTime("17:15",mma201unglingar.name), new ClassAndTime("18:00",mma101unglingar.name), new ClassAndTime("19:00",bjjstelpur.name), new ClassAndTime("20:00",nogi201.name));
                        break;
                    case 3:
                        classes = Arrays.asList(new ClassAndTime("12:10", mjolnir101.name), new ClassAndTime("17:15",godaafl.name), new ClassAndTime("18:00",kickbox301.name), new ClassAndTime("19:00",box201.name), new ClassAndTime("20:00",box101.name));
                        break;
                    case 4:
                        classes = Arrays.asList(new ClassAndTime("12:10",yoga101.name), new ClassAndTime("16:30",godaafl.name), new ClassAndTime("17:15",mma201unglingar.name), new ClassAndTime("18:00",mma101unglingar.name), new ClassAndTime("19:00",nogistelpur.name), new ClassAndTime("20:00",nogi201.name));
                        break;
                    case 5:
                        classes = Arrays.asList(new ClassAndTime("12:10",mjolnir101.name), new ClassAndTime("17:15",godaafl.name), new ClassAndTime("18:00",kickbox201.name));
                        break;
                    case 6:
                        classes = Arrays.asList(new ClassAndTime("11:10",yoga201.name), new ClassAndTime("13:10",yoga101.name));
                        break;
                    case 7:
                        classes = Arrays.asList(new ClassAndTime("13:10",yoga101.name));
                        break;
                    default:
                        classes = Arrays.asList(new ClassAndTime("error","error"));
                        break;
                }
                break;
            default:
                break;
        }


    /*
        var removeTheseClasses = [String]()

        if let shownClassesDictionary = NSUserDefaults.standardUserDefaults().dictionaryForKey(keyForShownClassesDictionaryString) as? [String:Bool] {

        for (startTime, new MjolnirClass) in classes {
            if let shown = shownClassesDictionary[new MjolnirClass] {
                if !shown {
                    removeTheseClasses.append(startTime)
                }
            }
        }

        for startTime in removeTheseClasses {
            classes.removeValueForKey(startTime)
        }

    }*/
        return classes;
    }

    public static Map<String, Boolean> rejectedMap = new HashMap<String, Boolean>();

    public static void loadRejectedClasses(Context context) {

        SharedPreferences keyValues = context.getSharedPreferences("mjolnir", Context.MODE_PRIVATE);

        for (MjolnirClass mjolnirClass : getAllClasses()) {

            boolean rejected = keyValues.getBoolean(mjolnirClass.name, false);
            rejectedMap.put(mjolnirClass.name, rejected);
        }
    }

    // NOTE! Not saved to shared preferences
    public static void rejectClass(String name) {
        rejectedMap.put(name, true);
    }

    // TODO: Save this from CustomizeClasses
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

