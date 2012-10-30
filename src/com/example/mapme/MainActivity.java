package com.example.mapme;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;

import android.widget.TextView;
import android.widget.Toast;

import android.location.Location;
import android.location.Criteria;
import android.location.LocationListener;
import android.location.LocationManager;
import android.content.Context;

import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends MapActivity {
	private MapView mapview;
	private LocationManager myLocationManager;
	private LocationListener myLocationListener;
	private TextView myLatitude, myLongitude;
	private MapController myMapController;
	private String provider;

	private void setCenterLocation(GeoPoint centerGeoPoints) {
		myMapController.animateTo(centerGeoPoints);
		myLongitude
				.setText("Longitude: "
						+ String.valueOf((float) centerGeoPoints
								.getLongitudeE6() / 1000000));
		myLatitude
				.setText("Latitude: "
						+ String.valueOf((float) centerGeoPoints
								.getLatitudeE6() / 1000000));
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initMyMap();
		initMyLocation();
	}

	private void initMyLocation() {
		Criteria criteria = new Criteria();
		myLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		provider = myLocationManager.getBestProvider(criteria, false);
		Location location = myLocationManager.getLastKnownLocation(provider);
		System.out.println(location);
		if (location != null) {
			System.out.println("Provider " + provider + " has been selected.");
			onLocationChanged(location);
		} else {
			myLatitude.setText("Location not available");
			myLongitude.setText("Location not available");
		}

	}

	private void initMyMap() {
		setContentView(R.layout.activity_main);
		mapview = (MapView) findViewById(R.id.map_view);
		myLatitude = (TextView) findViewById(R.id.latitude);
		myLongitude = (TextView) findViewById(R.id.longitude);
		mapview.setSatellite(true);
		mapview.setBuiltInZoomControls(true);

	}

	public void onLocationChanged(Location location) {
		int lat = (int) (location.getLatitude());
		int lng = (int) (location.getLongitude());
		myLatitude.setText(String.valueOf(lat));
		myLongitude.setText(String.valueOf(lng));
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public void onProviderEnabled(String provider) {
		Toast.makeText(this, "Enabled new provider " + provider,
				Toast.LENGTH_SHORT).show();

	}
}
