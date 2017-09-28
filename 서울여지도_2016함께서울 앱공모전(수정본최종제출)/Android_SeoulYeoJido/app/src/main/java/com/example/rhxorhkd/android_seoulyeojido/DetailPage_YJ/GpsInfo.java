package com.example.rhxorhkd.android_seoulyeojido.DetailPage_YJ;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by hanyoojin on 2016. 10. 26..
 */

public class GpsInfo extends Service implements LocationListener {

    private final Context mContext;
    boolean isGPSEnabled = false; // 현재 GPS 사용유무
    boolean isNetworkEnabled = false; // 네트워크 사용유무
    boolean isGetLocation = false; // GPS 상태값

    Location location;
    double lat;
    double lon;

    protected LocationManager locationManager;

    public GpsInfo(Context context) {
        this.mContext = context;
        //getLocation();
        initLocationService(context);
    }





    /**
     * Sets up location service after permissions is granted
     */
    @TargetApi(23)
    private void initLocationService(Context context) {


        if (Build.VERSION.SDK_INT >= 23 &&
                ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        try {
            this.lon = 0.0;
            this.lat = 0.0;
            this.locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

            // Get GPS and network status
            this.isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            this.isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

          //  if (forceNetwork) isGPSEnabled = false;

            if (!isNetworkEnabled && !isGPSEnabled) {
                // cannot get location
               // this.locationServiceAvailable = false;
            }
            //else
            {
                //this.locationServiceAvailable = true;
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 60000, this);

                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10, 60000, this);
                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        //updateCoordinates();
                        lat = location.getLatitude();
                        lon = location.getLongitude();
                    }
                }//end if

                if (isGPSEnabled) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 60000, this);

                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        //updateCoordinates();
                        lat = location.getLatitude();
                        lon = location.getLongitude();
                    }
                }
            }
        } catch (Exception ex) {
            Log.d("Error:","Error creating location service: "+ ex.getMessage());

        }
    }



//    public Location getLocation() {
//        if (Build.VERSION.SDK_INT >= 23 &&
//                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
//                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            return;
//        }
//
//        try {
//            locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
//            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
//            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
//
//            if (!isGPSEnabled && !isNetworkEnabled) {
//                //GPS와 네트워크사용이 가능하지 않을때 소스구현
//            } else {
////수정
//                location = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
//                if (location != null){
//                    lat = location.getLatitude();
//                    lon = location.getLongitude();
//
//                    Toast.makeText(getApplicationContext(), "Last Known Location : " + "Latitude : " + lat +"\nLongitude : " + lon, Toast.LENGTH_LONG).show();
//                }
////네트워크 정보로부터 위치값 가져오기
////                this.isGetLocation = true;
////                if (isNetworkEnabled) {
////                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10, 60000, this);
////                    if (locationManager != null) {
////                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
////                        if (location != null) {
////                            lat = location.getLatitude();
////                            lon = location.getLongitude();
////                        }
////                    }
////                }
//                if (isGPSEnabled) {
//                    if (location == null) {
////GPS정보로 위치값 가져오기
//                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 60000, this);
//
//                        if (locationManager != null) {
//                            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//                            if (location != null) {
//                                lat = location.getLatitude();
//                                lon = location.getLongitude();
//                            }
//                        }
//                    }
//                }
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return location;
//    }

    public void stopUsingGPS() {
        if (locationManager != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.removeUpdates(GpsInfo.this);
        }
    }

    public double getLatitude(){
        if(location != null){
            lat = location.getLatitude();
        }
        return lat;
    }

    public double getLongitude(){
        if(location != null){
            lon = location.getLongitude();
        }
        return lon;
    }

    public boolean isGetLocation(){
        return this.isGetLocation;
    }



    @Override
    public void onLocationChanged(Location location) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

}