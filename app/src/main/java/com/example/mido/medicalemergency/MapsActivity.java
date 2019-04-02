package com.example.mido.medicalemergency;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mido.medicalemergency.Models.Hospital;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    DatabaseReference reference;
    String type;
    List<Hospital> hospitals = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        type = getIntent().getAction();


    }

    void getData() {
//        KProgressHUD.create(this)
//                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
//                .setLabel("Please wait")
//                .setDetailsLabel("gettingLocations")
//                .setCancellable(true)
//                .setAnimationSpeed(2)
//                .setDimAmount(0.5f)
//                .show();
        reference = FirebaseDatabase.getInstance().getReference();
        reference.keepSynced(true);
        reference.child(type).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                hospitals = new ArrayList<>();
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    hospitals.add(childSnapshot.getValue(Hospital.class));
                }
//                KProgressHUD.create(MapsActivity.this).dismiss();
                addHospitalsMarks();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
//                KProgressHUD.create(MapsActivity.this).dismiss();

            }
        });
    }

    private void addHospitalsMarks() {
        int mark = 0;
        switch (type) {
            case Consts.HOSPITALS:
                mark = R.drawable.ic_hospital_bold;
                break;
            case Consts.CLINICS:
                mark = R.drawable.ic_clinic_medical_bold;
                break;
            case Consts.ANALYTICS:
                mark = R.drawable.ic_analytics_bold;
                break;

        }
        for (Hospital hospital : hospitals) {
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(hospital.getLatitude(), hospital.getLongitude()))
                    .icon(bitmapDescriptorFromVector(this, mark))
                    .title("Your Location"));
        }
    }


    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        animateCameraToLocation(new LatLng(31.119304, 30.933885));
        getUserLocation();
        mMap.setOnMarkerClickListener(this);
        getData();

    }

    private void getUserLocation() {
        SmartLocation.with(this).location().oneFix().start(new OnLocationUpdatedListener() {
            @Override
            public void onLocationUpdated(Location location) {
                if (location != null) {
//                    animateCameraToLocation(new LatLng(location.getLatitude(), location.getLongitude()));
                }
            }
        });
    }

    private void animateCameraToLocation(LatLng latLng) {
        mMap.addMarker(new MarkerOptions().position(latLng)
                .icon(bitmapDescriptorFromVector(this, R.drawable.ic_street_view))
                .title("Your Location"));
        CameraPosition position = new CameraPosition.Builder()
                .target(latLng)
                .zoom(16).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(position));
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, @DrawableRes int vectorDrawableResourceId) {
        Drawable vectorDrawable = ResourcesCompat.getDrawable(getResources(), vectorDrawableResourceId, null);
        assert vectorDrawable != null;
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Hospital hospital = getClickedHospital(marker);
        showHospitalDialog(hospital);
        return false;
    }

    private void showHospitalDialog(final Hospital hospital) {
        if (hospital == null)
            return;

        DialogPlus dialog = DialogPlus.newDialog(this)
                .setGravity(Gravity.BOTTOM)
                .setContentHolder(new ViewHolder(R.layout.hospital_dialog))
                .create();
        TextView hospitalNameTextView = (TextView)dialog.findViewById(R.id.hospitalName);
        TextView hospitalPhone1TextView = (TextView)dialog.findViewById(R.id.phone_number_1);
        TextView hospitalPhone2TextView = (TextView)dialog.findViewById(R.id.phone_number_2);
        ImageView hospitalImage = (ImageView)dialog.findViewById(R.id.hospitalImage);
        TextView openDirectionsButton = (TextView)dialog.findViewById(R.id.directionsButton);
        hospitalNameTextView.setText(hospital.getName());
        hospitalPhone1TextView.setText(hospital.getPhone1());
        hospitalPhone2TextView.setText(hospital.getPhone2());
        GlideApp.with(this)
                .load(hospital.getImage())
                .into(hospitalImage);
        openDirectionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGoogleMap(hospital.getLatitude(), hospital.getLongitude());
            }
        });
        dialog.show();


    }

    private void openGoogleMap(double latitude, double longitude) {
        Uri gmmIntentUri = Uri.parse("google.streetview:cbll="+latitude+","+longitude);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");

        startActivity(mapIntent);

    }

    private Hospital getClickedHospital(Marker marker) {
        for (Hospital hospital : hospitals) {
            if (marker.getPosition().longitude == hospital.getLongitude() &&
                    marker.getPosition().latitude == hospital.getLatitude()) {

                return hospital;
            }
        }
        return null;
    }
}
