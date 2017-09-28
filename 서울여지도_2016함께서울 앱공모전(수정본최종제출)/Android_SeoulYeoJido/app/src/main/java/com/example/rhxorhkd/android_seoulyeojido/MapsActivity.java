package com.example.rhxorhkd.android_seoulyeojido;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.rhxorhkd.android_seoulyeojido.DetailPage_YJ.DetailActivity;
import com.example.rhxorhkd.android_seoulyeojido.Model.VisitedItem;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener{

    private GoogleMap mMap;
    private RelativeLayout rl;
    private ImageView map_bottom_img;
    private TextView title, checkin_cnt, location_name, marker_cate_gu;

    private FirebaseDatabase db;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        rl = (RelativeLayout) findViewById(R.id.location_detail);
        rl.setVisibility(View.GONE);
        title = (TextView)findViewById(R.id.location_name);
        checkin_cnt = (TextView)findViewById(R.id.checkin_cnt);
        marker_cate_gu = (TextView)findViewById(R.id.marker_cate_gu);

        map_bottom_img = (ImageView)findViewById(R.id.map_bottom_img);

        findViewById(R.id.map_back).setOnClickListener(this);
        RelativeLayout rl = (RelativeLayout)findViewById(R.id.location_detail);
        rl.setOnClickListener(this);

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
        BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.after_check_in);
        Bitmap b = bitmapdraw.getBitmap();
        final Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);

        db = FirebaseDatabase.getInstance();
        ref = db.getReference("member");

        Intent intent = getIntent();
            String uid = intent.getStringExtra("uid");

            ref.child(uid+"/checkin").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot data) {
                    for (DataSnapshot post: data.getChildren()) {

                        mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(Double.valueOf(""+post.child("lat").getValue()).doubleValue(), Double.valueOf(""+post.child("lon").getValue()).doubleValue()))
                                .title(""+post.getKey())
                                .icon(BitmapDescriptorFactory.fromBitmap(smallMarker))
                        );
                    }

                }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.53501414281699,126.98524095118046), 12));

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(final Marker marker) {
                title.setText(marker.getTitle());

                Intent i = getIntent();
                String uid = i.getStringExtra("uid");
                ref.child(uid+"/checkin").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot data) {
                        if(data.child(marker.getTitle()).exists()){
                            Glide.with(MapsActivity.this).load(""+data.child(marker.getTitle()).child("img").getValue()).into(map_bottom_img);

                            marker_cate_gu.setText(""+data.child(marker.getTitle()).child("category").getValue()+" · 영등포");
//                            marker_cate_gu.setText(""+data.child(marker.getTitle()).child("category").getValue()+"·"+Integer.parseInt(""+data.child(marker.getTitle()).child("guNumber").getValue()));
                            ref.addValueEventListener(new ValueEventListener() {
                                int chk_cnt = 0;
                                @Override
                                public void onDataChange(DataSnapshot cnt) {
                                    for (DataSnapshot post: cnt.getChildren()) {
                                        if(post.child("checkin") != null){
                                            if(post.child("checkin").child(marker.getTitle()).exists()){
                                                chk_cnt++;
                                            }
                                        }
                                    }
                                    checkin_cnt.setText(""+chk_cnt);
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

//
//
//

                rl.setVisibility(View.VISIBLE);
                return false;
            }
        });
        mMap.getUiSettings().setRotateGesturesEnabled(false);

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                rl.setVisibility(View.GONE);
            }
        });
    }

    public String getGuName(int guNumber){
        return "";
    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.map_back :
                finish();
                break;
            case  R.id.location_detail :
                Intent intent = new Intent(MapsActivity.this, DetailActivity.class);
                intent.putExtra("name", title.getText().toString());
                startActivity(intent);

                break;
            default: break;
        }

    }
}
