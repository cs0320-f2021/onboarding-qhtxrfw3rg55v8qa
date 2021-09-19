package edu.brown.cs.student.main;
import java.util.*;

// Used "KNN algorithm in java" from youtube as a source for this class
// link: https://www.youtube.com/watch?v=8kaYD2g9MVQ

// fields that contain attributes of a star after the csv file has been parts
public class Stars implements Comparable<Stars>{
    private int StarID;
    private String ProperName;
    private double x;
    private double y;
    private double z;
    private double distanceFromOrig;

// 5 field constructor
    public Stars(String ProperName, double x, double y, double z) {
        super();
        this.ProperName = ProperName;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    // 2 field constructor using name and x
    public Stars(String ProperName, double x, double y) {
        super();
        this.ProperName = ProperName;
        this.x = x;
        this.y = y;
    }
// 5 field constructor with distance from the original input star
    public Stars(String ProperName, double x, double y, double z, double distanceFromOrig){
        super();
        this.ProperName = ProperName;
        this.x = x;
        this.y = y;
        this.z = z;
        this.distanceFromOrig = distanceFromOrig;

    }
// no arguments constructor
    public Stars() {}

// getter and setter methods for each attribute in the stars class
    public int getStarID() {return StarID;}

    public void setStarID(int StarID) {this.StarID = StarID;}

    public String getProperName() {return ProperName;}

    public void setProperName(String ProperName) {this.ProperName = ProperName;}

    public double getx() {return x;}

    public void setx(int x) {this.x = x;}

    public double gety() {return y;}

    public void sety(int y) {this.y = y;}

    public double getZ() {return z;}

    public void setZ(int z) {this.z = z;}

    public double getDistanceFromOrig() {
        return distanceFromOrig;
    }

    public void setDistanceFromOrig(double distanceFromOrig) {
        this.distanceFromOrig = distanceFromOrig;
    }

    // Use distance formula to calculate the distance between each star in the list; then return the list
    public static List calculateDistance(List<Stars> starList, Stars ts) {
        for(Stars s: starList ) {
            double c = Math.pow(s.x - ts.x, 2);
            double d = Math.pow(s.y - ts.y, 2);
            double e = Math.pow(s.z - ts.z, 2);
            double dist = Math.sqrt(c+d+e);
            s.setDistanceFromOrig(dist);
        }
        return starList;

    }
    public String toString(){
        return "Stars [StarID =" + StarID + "Name = " + ProperName + "x = " + x + "y = " + "z = " + z + "distanceFromOrig = " + distanceFromOrig + "]";
    }

    public int compareTo(Stars o) {
        return (int) ((this.distanceFromOrig-o.distanceFromOrig)* 100);
    }
}
