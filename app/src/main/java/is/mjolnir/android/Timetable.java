package is.mjolnir.android;

//
//  TimetableDataModel.swift
//  Mjölnir
//
//  Created by Stefán Geir Sigfússon on 13.2.2015.
//  Copyright (c) 2015 Stefán Geir. All rights reserved.
//


import java.util.Arrays;
import java.util.List;

public class Timetable {


    public static SingleClass nogistelpur = new SingleClass("Nogi stelpur");
    public static SingleClass nogi201 = new SingleClass("Nogi 201");
    public static SingleClass nogi301 = new SingleClass("Nogi 301");
    public static SingleClass mma101 = new SingleClass("MMA 101");
    public static SingleClass mma101unglingar = new SingleClass("MMA 101 Unglingar");
    public static SingleClass mma201 = new SingleClass("MMA 201");
    public static SingleClass mma201unglingar = new SingleClass("MMA 201 Unglingar");
    public static SingleClass mmact = new SingleClass("MMA CT");
    public static SingleClass mjolnir101 = new SingleClass("Mjölnir 101");
    public static SingleClass bjj301 = new SingleClass("BJJ 301");
    public static SingleClass bjj201 = new SingleClass("BJJ 201");
    public static SingleClass bjjct = new SingleClass("BJJ CT");
    public static SingleClass bjjstelpur = new SingleClass("BJJ stelpur");
    public static SingleClass openmat = new SingleClass("Open Mat");
    public static SingleClass vikingathrek = new SingleClass("Víkingaþrek");
    public static SingleClass extraIntenseVikingathrek = new SingleClass("Erfiðara Víkingaþrek (90 mín)");
    public static SingleClass vikingathrek101 = new SingleClass("Víkingaþrek 101");
    public static SingleClass vikingathrekunglingar = new SingleClass("Víkingaþrek Unglingar");
    public static SingleClass box101 = new SingleClass("Box 101");
    public static SingleClass box201 = new SingleClass("Box 201");
    public static SingleClass box301 = new SingleClass("Box 301");
    public static SingleClass sparr = new SingleClass("Sparr");
    public static SingleClass kickbox101 = new SingleClass("Kickbox 101");
    public static SingleClass kickbox201 = new SingleClass("Kickbox 201");
    public static SingleClass kickbox301 = new SingleClass("Kickbox 301");
    public static SingleClass born101 = new SingleClass("Börn 101");
    public static SingleClass born201 = new SingleClass("Börn 201");
    public static SingleClass yoga101 = new SingleClass("Mjölnisyoga 101");
    public static SingleClass yoga201 = new SingleClass("Mjölnisyoga 201");
    public static SingleClass godaafl = new SingleClass("Goðaafl");


    public static List<SingleClass> getAllClasses() {
        return Arrays.asList(nogistelpur, nogi201, nogi301, mma101, mma101unglingar, mma201, mma201unglingar, mmact, mjolnir101, bjj301, bjj201,
                bjjct, bjjstelpur, openmat, vikingathrek, extraIntenseVikingathrek, vikingathrek101, vikingathrekunglingar, box101, box201, box301,
                sparr, kickbox101, kickbox201, kickbox301, born101, born201, yoga101, yoga201, godaafl);
    }


    public static List<SingleClass> getClassesForRoom(int roomNumber) {
        return getAllClasses();
    }


    public static List<CT> getClassesForWeekday(final int theWeekday, final int theRoom) {
        if (theWeekday < 1 || theWeekday > 7) {
            return null;
        }
        List<CT> classes = null;

        switch(theRoom) {
            case 1:
                switch(theWeekday) {
                    case 1:
                    case 3:
                        classes = Arrays.asList(new CT("12:10",nogi201.name), new CT("16:30", born201.name), new CT("17:15", mma201unglingar.name), new CT("18:00", bjj301.name), new CT("19:00", bjj201.name), new CT("20:00", kickbox201.name));
                        break;
                    case 2:
                    case 4:
                        classes = Arrays.asList(new CT("08:00",bjj201.name), new CT("12:10",bjj201.name), new CT("17:00",nogi301.name), new CT("18:00",mmact.name), new CT("20:00",mjolnir101.name));
                        break;
                    case 5:
                        classes = Arrays.asList(new CT("12:10", nogi201.name), new CT("16:30",born201.name), new CT("17:15",mma201unglingar.name), new CT("18:00",bjj201.name));
                        break;
                    case 6:
                        classes = Arrays.asList(new CT("11:10",bjjct.name), new CT("12:10",bjj201.name), new CT("13:00",openmat.name));
                        break;
                    case 7:
                        classes = Arrays.asList(new CT("12:10",openmat.name), new CT("13:00", openmat.name));
                        break;
                    default:
                        classes = Arrays.asList(new CT("error","error"));
                        break;
                }
                break;
            case 2:
                switch(theWeekday) {
                    case 1:
                   //     classes = Arrays.asList(new CT("06:40",vikingathrek.name), new CT("12:10",vikingathrek.name), new CT("16:30",vikingathrek.name), new CT("17:15",vikingathrek.name), new CT("18:00",box301.name), new CT("19:00",kickbox301.name), new CT("20:00",vikingathrek.name));
                   //     break;
                    case 3:
                        classes = Arrays.asList(new CT("06:40", vikingathrek.name), new CT("12:10",vikingathrek.name), new CT("16:30",vikingathrek.name), new CT("17:15",vikingathrek.name), new CT("18:00",box301.name), new CT("19:00",mma201.name), new CT("20:00",vikingathrek.name));
                        break;
                    case 2:
                    case 4:
                        classes = Arrays.asList(new CT("08:00",vikingathrek.name), new CT("12:10",vikingathrek.name), new CT("16:30",born101.name), new CT("17:15",vikingathrek.name), new CT("18:00",vikingathrek.name), new CT("19:00",vikingathrek101.name), new CT("20:00",kickbox101.name));
                        break;
                    case 5:
                        classes = Arrays.asList(new CT("06:40",vikingathrek.name), new CT("12:10",vikingathrek.name), new CT("16:30",vikingathrek.name), new CT("17:15",vikingathrek.name), new CT("18:00",box201.name));
                        break;
                    case 6:
                        classes = Arrays.asList(new CT("11:10",vikingathrek.name), new CT("12:10",vikingathrek.name), new CT("13:00",vikingathrekunglingar.name));
                        break;
                    case 7:
                        classes = Arrays.asList(new CT("10:30",extraIntenseVikingathrek.name), new CT("12:10",vikingathrek.name), new CT("13:00",sparr.name));
                        break;
                    default:
                        classes = Arrays.asList(new CT("error","error"));
                        break;

                }
                break;
            case 3:
                switch (theWeekday) {
                    case 1:
                        classes = Arrays.asList(new CT("12:10",yoga201.name), new CT("17:15",godaafl.name), new CT("18:00",mma101.name), new CT("19:00",box201.name), new CT("20:00",box101.name));
                        break;
                    case 2:
                        classes = Arrays.asList(new CT("12:10",yoga101.name), new CT("16:30",godaafl.name), new CT("17:15",mma201unglingar.name), new CT("18:00",mma101unglingar.name), new CT("19:00",bjjstelpur.name), new CT("20:00",nogi201.name));
                        break;
                    case 3:
                        classes = Arrays.asList(new CT("12:10", mjolnir101.name), new CT("17:15",godaafl.name), new CT("18:00",kickbox301.name), new CT("19:00",box201.name), new CT("20:00",box101.name));
                        break;
                    case 4:
                        classes = Arrays.asList(new CT("12:10",yoga101.name), new CT("16:30",godaafl.name), new CT("17:15",mma201unglingar.name), new CT("18:00",mma101unglingar.name), new CT("19:00",nogistelpur.name), new CT("20:00",nogi201.name));
                        break;
                    case 5:
                        classes = Arrays.asList(new CT("12:10",mjolnir101.name), new CT("17:15",godaafl.name), new CT("18:00",kickbox201.name));
                        break;
                    case 6:
                        classes = Arrays.asList(new CT("11:10",yoga201.name), new CT("13:10",yoga101.name));
                        break;
                    case 7:
                        classes = Arrays.asList(new CT("13:10",yoga101.name));
                        break;
                    default:
                        classes = Arrays.asList(new CT("error","error"));
                        break;
                }
                break;
            default:
                break;
        }


    /*
        var removeTheseClasses = [String]()

        if let shownClassesDictionary = NSUserDefaults.standardUserDefaults().dictionaryForKey(keyForShownClassesDictionaryString) as? [String:Bool] {

        for (startTime, new SingleClass) in classes {
            if let shown = shownClassesDictionary[new SingleClass] {
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

}

