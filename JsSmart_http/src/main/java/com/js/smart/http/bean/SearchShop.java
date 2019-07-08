package com.js.smart.http.bean;



import java.io.Serializable;

/**
 * Created by Js on 2017/9/1.
 */


public class SearchShop implements Serializable{


    /**
     * distance : 6.67
     * geoCoordinate : {"longitude":121.42274647951126,"latitude":31.218732204204578}
     * tshop : {"id":8,"shopName":"盛春商厦店","shopAddress":"上海市长宁区愚园路1391号","businessHour":"早8点晚10点","shopImgPath":"http://img.e-chinacycle.cn/201906/1560577258544.jpg","createdBy":"1135498837289402368","createdTime":"2019-06-15 13:42:29","updatedBy":null,"updatedTime":null,"longitude":"121.422747","latitude":"31.2187319","userid":"1135498837289402368","shopinPhoto":"http://img.e-chinacycle.cn/201906/1560577265319.jpg","shopoutPhoto":"http://img.e-chinacycle.cn/201906/1560577268789.jpg","availableCount":0,"slotsCount":12}
     * memberByString : 8
     */

    private double distance;
    private GeoCoordinate geoCoordinate;
    private Shop tshop;
    private String memberByString;

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public GeoCoordinate getGeoCoordinate() {
        return geoCoordinate;
    }

    public void setGeoCoordinate(GeoCoordinate geoCoordinate) {
        this.geoCoordinate = geoCoordinate;
    }

    public Shop getTshop() {
        return tshop;
    }

    public void setTshop(Shop tshop) {
        this.tshop = tshop;
    }

    public String getMemberByString() {
        return memberByString;
    }

    public void setMemberByString(String memberByString) {
        this.memberByString = memberByString;
    }

    public static class GeoCoordinate implements Serializable{
        /**
         * longitude : 121.42274647951126
         * latitude : 31.218732204204578
         */

        private double longitude;
        private double latitude;

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }
    }

}
