package com.example.rhxorhkd.android_seoulyeojido.DetailPage_YJ;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.rhxorhkd.android_seoulyeojido.R;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.tsengvn.typekit.Typekit;
import com.tsengvn.typekit.TypekitContextWrapper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CheckinmapActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {
    private DatabaseReference ref;
    private FirebaseAuth auth;
    private FirebaseDatabase db;
    String uid;
    JSONArray locationarray;
    JSONObject object;
    ArrayList<String> markercheck = new ArrayList<>();
    private Marker m;
    RelativeLayout relativeLayout;

    OkHttpClient client = new OkHttpClient();
    JSONObject jsonobject;
    JSONArray jsonarray;
    Response response;
    Request request;

    JSONArray jsonArray;
    private GoogleMap mMap;
    private GpsInfo gps;

    private LocationManager locationManager = null;
    private Double longitude;
    private Double latitude;


    private RelativeLayout rl;
    private TextView tv1;
    private String markerId;

    private long now;
    private Date date;
    private String time;
    private String guNumber;
    private String lat;
    private String lon;
    private String showphoto = null;
    private String showtitle = null;
    private String showcheckincount = null;
    private String showreviewcount = null;
    private String showcategorygu = null;

    private TextView showtitletext;
    private TextView showcheckincounttext;
//    private TextView showreviewcounttext;
    private TextView showcategorygutext;
    private ImageView showphotoimage;
    SupportMapFragment mapFragment;
    RelativeLayout locationdetail;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client2;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Checkinmap Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // Activity가 처음으로 시작하거나 다시 시작한 상태이다. 따라서 GPS의 사용가능 여부를 검사한다.
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (!gpsEnabled) {
            // GPS가 사용가능하지 않다면 사용자에게 요청하는 다이얼로그를 띄우고 처리한다.
            // android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS 인텐트로 GPS 설정을 유도한다.
        }

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client2.connect();
        AppIndex.AppIndexApi.start(client2, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client2, getIndexApiAction());
        client2.disconnect();
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        // onStart에서 설정을 하므로 onRestart에서는 특별히 할게 없어진다.
    }

    public class firstListGetData extends AsyncTask<String, Void, String> {
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkinmap);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        locationdetail = (RelativeLayout) findViewById(R.id.location_detail);
        showtitletext = (TextView) findViewById(R.id.location_name);
        Log.d("list", "categorygu : " + showcategorygu);
        showcategorygutext = (TextView) findViewById(R.id.checkin_categu);
        showcheckincounttext = (TextView) findViewById(R.id.checkin_cnt);
       // showreviewcounttext = (TextView) findViewById(R.id.reply_cnt);
        showphotoimage = (ImageView) findViewById(R.id.map_bottom_img);
        //글꼴 라이브러리
        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "NotoSans-Regular.ttf"));

        rl = (RelativeLayout) findViewById(R.id.location_detail);
        rl.setVisibility(View.GONE);
        tv1 = (TextView) findViewById(R.id.location_name);


        gps = new GpsInfo(CheckinmapActivity.this);
// GPS 사용유무 가져오기
        if (gps.isGetLocation()) {

        }
        latitude = gps.getLatitude();
        longitude = gps.getLongitude();
        Log.d("gps", "latitude : " + latitude);


        //checkinListGetData getData = new checkinListGetData();

        // 시스템으로부터 현재시간(ms) 가져오기
        now = System.currentTimeMillis();
        // Data 객체에 시간을 저장한다.
        date = new Date(now);

        time = new SimpleDateFormat("yyyyMMdd").format(new Date(System.currentTimeMillis()));
       // Toast.makeText(this, time, Toast.LENGTH_SHORT).show();

        LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE); //gps 사용 유/무 파악하려고
        if (!locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) { //gps 꺼져있을 시 앱이 수행할 코드
            Log.d("list", "gps 연결 X");
            new AlertDialog.Builder(CheckinmapActivity.this)
                    .setMessage("GPS가 꺼져있습니다. \n 'Google 위치 서비스' 를 체크해주세요")
                    .setPositiveButton("설정", new DialogInterface.OnClickListener() { //설정 버튼 누를때
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS); //설정페이지 이동
                            startActivity(intent);
                        }
                    }).setNegativeButton("취소", null).show();

        }

        //네트워크 확인
        ConnectivityManager manager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        // wifi 또는 모바일 네트워크 어느 하나라도 연결이 되어있다면,
        if (wifi.isConnected() || mobile.isConnected()) {
            // Log.i("연결됨" , "연결이 되었습니다.);
            //         setContentView(R.layout.activity_logo);
        } else {
            new AlertDialog.Builder(CheckinmapActivity.this)
                    .setMessage("인터넷 연결을 체크해주세요")
                    .setPositiveButton("설정", new DialogInterface.OnClickListener() { //설정 버튼 누를때
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS); //설정페이지 이동
                            startActivity(intent);
                        }
                    }).setNegativeButton("취소", null).show();
            //Log.i("연결 안 됨" , "연결이 다시 한번 확인해주세요);
        }



        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                //Toast.makeText(CheckinmapActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(getApplicationContext(), CheckinmapActivity.class));
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                Toast.makeText(CheckinmapActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };


        new TedPermission(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("위치 권한을 사용을 허용해 주세요.\n\n [설정] > [권한]")
                .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                .check();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client2 = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
//Manifest.permission.READ_CONTACTS,
//.setDeniedMessage("If you reject permission,you can not use this service \n\nPlease turn on permissions at [Setting] > [Permission]")


    }//onCreate()







//
//    private boolean chkPermission() {
//        //안드로이드 버전 체크
//        if(Build.VERSION.SDK_INT< Build.VERSION_CODES.M)
//            return true;
//        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
//            return true;
//        //해당 권한의 요청
//        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_CODE_GPS);
//        return false;
//    }

    private static final int PERMISSION_REQUEST_CODE_GPS = 1;
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(requestCode != PERMISSION_REQUEST_CODE_GPS)
            return;
        if(grantResults.length == 0) return;
        if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //권한을 획득하면 추가 루틴 발생
            return;
        } else {
            //권한이 획득되지 못했을땐 설명과 함께 권한을 얻어옴
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION))
                Toast.makeText(getApplicationContext(),"GPS Needed",Toast.LENGTH_LONG).show();
                //showGPSCheckDialog(true);
            else
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_CODE_GPS);
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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

        final Bitmap noChecksmallMarker = Bitmap.createScaledBitmap(noCheck, width, height, false);
        final Bitmap CheckedsmallMarker = Bitmap.createScaledBitmap(b, width, height, false);

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
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 15));
//        mMap.addMarker(new MarkerOptions()
//                .title("현재 위치")
//                .snippet("innoaus.")
//                .position(myLocation));

        mMap.addCircle(new CircleOptions()
                .center(new LatLng(latitude, longitude))
                .radius(500)
                .strokeColor(Color.parseColor("#884169e1"))
                .fillColor(Color.parseColor("#5587cefa")));


        firstListGetData getData = new firstListGetData();
        String result = null;
        try {
            result = getData.execute().get();
            JSONObject object = new JSONObject(result);
            jsonArray = object.getJSONArray("location");
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject object1 = jsonArray.getJSONObject(i);
                    String lat = object1.getString("loca_lat");
                    String lon = object1.getString("loca_lon");
                    String title = object1.getString("loca_name");
                    LatLng temp = new LatLng(Double.parseDouble(lat), Double.parseDouble(lon));

                    m = mMap.addMarker(new MarkerOptions().position(temp).title(title).icon(BitmapDescriptorFactory.fromBitmap(noChecksmallMarker)));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


        //유저 체크인시 플로팅버튼 색칠
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        ref = db.getReference("member");

        FirebaseUser user = auth.getCurrentUser();
        uid = user.getUid();
        Log.d("list", "uid-->" + uid);

        ref.child(uid + "/checkin").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot data) {
                for (DataSnapshot post : data.getChildren()) {

                    Marker checked = mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(Double.valueOf("" + post.child("lat").getValue()).doubleValue(), Double.valueOf("" + post.child("lon").getValue()).doubleValue()))
                            .title("" + post.getKey())
                            .icon(BitmapDescriptorFactory.fromBitmap(CheckedsmallMarker))
                    );
                    markercheck.add(post.getKey());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                marker.setIcon(BitmapDescriptorFactory.fromBitmap(CheckedsmallMarker));
                tv1.setText(marker.getTitle());
                showlocainfo(marker.getTitle());
                showcheckincounttext.setText(showcheckincount);
                showcategorygutext.setText(showcategorygu);
              //  showreviewcounttext.setText(showreviewcount);
                showtitletext.setText(showtitle);
                Glide.with(rl.getContext()).load(showphoto).into(showphotoimage);

                rl.setVisibility(View.VISIBLE);

                markerId = marker.getId().toString();

                String results = null;
                Log.d("list", "showtitle : " + showtitle);
                try {
                    checkinsuccess checkinsuccess = new checkinsuccess();
                    results = checkinsuccess.execute(showtitle, latitude.toString(), longitude.toString()).get();
                    Log.d("list", "result : " + results);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // if(m.getId)
                String num = marker.getId().substring(1);
                Log.d("what", "<---" + num);
                if (!markercheck.contains(showtitle)) {
                    marker.setIcon(BitmapDescriptorFactory.fromBitmap(noChecksmallMarker));
                    Intent intentSubActivity = new Intent(CheckinmapActivity.this, CheckinPopup.class);
                    intentSubActivity.putExtra("position", marker.getPosition());
                    intentSubActivity.putExtra("title", marker.getTitle());
                    intentSubActivity.putExtra("time", time);
                    intentSubActivity.putExtra("result", results);

                    startActivity(intentSubActivity);
                }
                return false;
            }
        });

        relativeLayout = (RelativeLayout) findViewById(R.id.location_detail);
        mMap.getUiSettings().setRotateGesturesEnabled(false);
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                //Intent intent = new Intent(getApplicationContext(),DetailActivity.class);
                //intent.putExtra("name",showtitle);
                //startActivity(intent);
            }
        });

        locationdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                intent.putExtra("name", showtitle);
                startActivity(intent);
            }
        });
    }


    public class showCheckin extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            JSONObject json = new JSONObject();
            try {
                json.put("user_id", params[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            RequestBody posData = RequestBody.create(JSON, json.toString());
            request = new Request.Builder()
                    .url("http://52.78.32.50:3000/ko/showCheckin")
                    .post(posData)
                    .build();
            try {
                response = client.newCall(request).execute();
                return response.body().string();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }


    public class checkinsuccess extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            JSONObject json = new JSONObject();
            try {
                json.put("loca_name", params[0]);
                json.put("loca_lat", params[1]);
                json.put("loca_lon", params[2]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            RequestBody posData = RequestBody.create(JSON, json.toString());
            request = new Request.Builder()
                    .url("http://52.78.32.50:3000/ko/checkin")
                    .post(posData)
                    .build();
            try {
                response = client.newCall(request).execute();
                return response.body().string();
            } catch (Exception e) {
                e.printStackTrace();
            }
            //post gu num
            return null;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.default_flag :
//                break;
            default:
                break;
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }


    public void showlocainfo(String title) {
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

}
