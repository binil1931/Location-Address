package com.binil.surva;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentProviderOperation;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mylibrary.arun.WebServices;

public class MainActivity extends Activity {

	String current_location = null;
	TextView textView1;
	
	AppLocationService appLocationService; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.survey);

		appLocationService = new AppLocationService(MainActivity.this);
		textView1 = (TextView) findViewById(R.id.textView1);
				
		Location gpsLocation = appLocationService
				.getLocation(LocationManager.NETWORK_PROVIDER);
		
	    Log.e("tag ", "wroking" + gpsLocation);
		if (gpsLocation != null) {
			double latitude = gpsLocation.getLatitude();
			double longitude = gpsLocation.getLongitude();

			Log.e("Tag ", "latitude" + latitude);
			Log.e("Tag ", "longitude" + longitude);
			
			showAddress(latitude,longitude);
		} 

	}

	//==========================================================================================================================================
	//getting address from lat lng 
	//========================================================================================================================================== 
	protected String showAddress(double lat, double lng) 
	{
		String address_name = "No Location";
		try
		{
			Geocoder geocoder;
			List<Address> addresses;
			geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
			addresses = geocoder.getFromLocation(lat, lng, 1);

			String address = addresses.get(0).getAddressLine(0);
			String city = addresses.get(0).getAddressLine(1);
			String country = addresses.get(0).getAddressLine(2);

			address_name = address+", "+city+", "+country;

			Log.e("TAG", "address_name  "+address_name );
			textView1.setText("Address : "+ address + "\n" + "City  : " + city +"\n Country  : "+ country);

		}
		catch (Throwable e) 
		{
			Log.d("address error: \n", ""+e);
			//Toast.makeText( Service_location.this,"Sorry could get your address.",Toast.LENGTH_SHORT ).show();
		}
		return address_name;
	}
	//==========================================================================================================================================
	//END getting address from lat lng
	//==========================================================================================================================================


}
