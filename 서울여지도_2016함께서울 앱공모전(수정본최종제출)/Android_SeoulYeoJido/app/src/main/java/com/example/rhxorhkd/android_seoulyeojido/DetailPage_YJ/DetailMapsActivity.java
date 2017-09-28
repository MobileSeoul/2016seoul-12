package com.example.rhxorhkd.android_seoulyeojido.DetailPage_YJ;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.rhxorhkd.android_seoulyeojido.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.R.attr.id;
import static com.example.rhxorhkd.android_seoulyeojido.R.id.map;

public class DetailMapsActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    OkHttpClient client = new OkHttpClient();
    JSONObject jsonobject;
    JSONArray jsonarray;
    Response response;
    Request request;

    JSONArray jsonArray;

    private String showphoto = null;
    private String showtitle = null;
    private String showcheckincount = null;
    private String showreviewcount = null;
    private String showcategorygu = null;

    private TextView showtitletext;
    private TextView showcheckincounttext;
    private TextView showreviewcounttext;
    private TextView showcategorygutext;
    private ImageView showphotoimage;

    private LocationManager locationManager = null;
    private Double longitude;
    private Double latitude;

    private GpsInfo gps;
    private GoogleMap mMap;

    private RelativeLayout rl;
    private CheckBox checkflag;
    private TextView tv1;
    private String markerId;

    private String locationtitle;
    private String lat;
    private String lon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);

        rl = (RelativeLayout) findViewById(R.id.location_detail);
        // rl.setVisibility(View.GONE);
        tv1 = (TextView) findViewById(R.id.location_name);


        //checkflag = (CheckBox) findViewById(R.id.default_flag);
        //checkflag.setOnCheckedChangeListener(this);

        findViewById(R.id.map_back).setOnClickListener(this);

        gps = new GpsInfo(DetailMapsActivity.this);

        latitude = gps.getLatitude();
        longitude = gps.getLongitude();
        Log.d("gps", "latitude: " + latitude);


        Intent intent = getIntent();
        locationtitle = intent.getStringExtra("title");
        lat = intent.getStringExtra("latitude");
        lon = intent.getStringExtra("longitude");
        Log.d("loca", "la: " + lat);

        showtitletext = (TextView) findViewById(R.id.location_name);
        showcategorygutext = (TextView) findViewById(R.id.checkin_categu);
        showcheckincounttext = (TextView) findViewById(R.id.checkin_cnt);
        showreviewcounttext = (TextView) findViewById(R.id.reply_cnt);
        showphotoimage = (ImageView) findViewById(R.id.map_bottom_img);


        firstListGetData1 getData = new firstListGetData1();
        String result = null;
        try {
            result = getData.execute().get();
            JSONObject object = new JSONObject(result);
            jsonArray = object.getJSONArray("location");
        } catch (Exception e) {
            e.printStackTrace();
        }


        showlocainfo1(locationtitle);
        showcheckincounttext.setText(showcheckincount);
        showcategorygutext.setText(showcategorygu);
        showreviewcounttext.setText(showreviewcount);
        showtitletext.setText(showtitle);
        Glide.with(rl.getContext()).load(showphoto).into(showphotoimage);

    }

    public class firstListGetData1 extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {

            Request request = new Request.Builder()
                    .url("http://52.78.32.50:3000/ko/showloca")
                    .build();
            try {
                Response response = client.newCall(request).execute();
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public void showlocainfo1(String title) {
        showtitle = title;
        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject object = jsonArray.getJSONObject(i);
                    String temptitle = object.getString("loca_name");
                    if (temptitle.equals(showtitle)) {
                        showphoto = object.getString("loca_photo");
                        showcheckincount = object.getString("loca_checkincount");
                        showreviewcount = object.getString("loca_reviewcount");
                        showcategorygu = object.getString("loca_categorynum") + "|" + object.getString("loca_guNum");
                        break;
                    } else continue;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        int height = 126;
        int width = 90;
        BitmapDrawable bitmapdraw1 = (BitmapDrawable) getResources().getDrawable(R.drawable.after_check_in);
        BitmapDrawable bitmapdraw2 = (BitmapDrawable) getResources().getDrawable(R.drawable.before_check_in);
        Bitmap b = bitmapdraw1.getBitmap();
        Bitmap noCheck = bitmapdraw2.getBitmap();

        Bitmap noChecksmallMarker = Bitmap.createScaledBitmap(noCheck, width, height, false);
        Bitmap CheckedsmallMarker = Bitmap.createScaledBitmap(b, width, height, false);

        LatLng myLocation = new LatLng(latitude, longitude);
        Log.d("mr", "la: " + latitude);
        Log.d("mr", "lo: " + longitude);

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

        mMap.setMyLocationEnabled(true);

//        mMap.addMarker(new MarkerOptions().title("현재 위치").snippet("innoaus.").position(myLocation));
//        mMap.addCircle(new CircleOptions().center(new LatLng(latitude, longitude)).radius(500).strokeColor(Color.parseColor("#884169e1")).fillColor(Color.parseColor("#5587cefa")));

        LatLng locapoint = new LatLng(Double.parseDouble(lat), Double.parseDouble(lon));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locapoint, 12));
        mMap.addMarker(new MarkerOptions().position(locapoint).title(locationtitle).icon(BitmapDescriptorFactory.fromBitmap(CheckedsmallMarker)));

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
//                Intent intentSubActivity = new Intent(DetailMapsActivity.this, CheckinPopup.class);
//                startActivity(intentSubActivity);

                tv1.setText(marker.getTitle());
                return false;
            }
        });

        mMap.getUiSettings().setRotateGesturesEnabled(false);

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                //rl.setVisibility(View.GONE);
                //finish();
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.map_back:
                finish();
                break;
//            case R.id.default_flag :
//                break;
            default:
                break;
        }
    }


   /* public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (checkflag.isChecked()) {
            //result += checkflag.getText().toString() + ", ";
            //  Toast.makeText(getApplicationContext(), "checkin!", Toast.LENGTH_LONG).show();
        }
        if (!checkflag.isChecked()) {
            //   Toast.makeText(getApplicationContext(), "no Checkin!", Toast.LENGTH_LONG).show();
        }
    }*/
}
