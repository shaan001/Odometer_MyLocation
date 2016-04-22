package com.hybrid.tech.mylocationv4;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.IOException;
import java.util.List;

// Try this:
//layout.setBackground(getResources().getDrawable(R.drawable.ready));
//and for API 16<:
//layout.setBackgroundDrawable(getResources().getDrawable(R.drawable.ready));

public class MainActivity extends Activity {
    RelativeLayout relativeLayout;
    private Location lastLocation = null;
    private double distanceInMe = 0;
    Geocoder geocoder;
    List<Address> list;
    Address address;
    double lat;
    double lng;
    TextView myloc;
    String[] latlng = new String[1];
    TextView stats;
    String allStats = "";
     int threadTimer = 0;
    TextView threadView;
    String distance;
    TextView myDistance;
    boolean flag;
    Location location;


     int totalTime = 0;
    double speed;
    TextView speedView;

    Button myLocation;
    ToggleButton startStop;
    boolean running = false;
    double distanceForSpeed = 0;

    public final static String EXTRA_MESSAGE = "message";
    Intent intent;
    ShareActionProvider shareActionProvider;
    String messageText;
    static boolean onceOnly=true;
    boolean toggleCheck=false;
    double weight=143.5;//(65 kg avg weight)
   TextView actualCalories;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        relativeLayout = (RelativeLayout) findViewById(R.id.main_layout);
        if(savedInstanceState !=null)
        {
            distanceInMe=savedInstanceState.getDouble("distanceInMe");
            running=savedInstanceState.getBoolean("running");
            distanceForSpeed=savedInstanceState.getDouble("distanceForSpeed");
            speed=savedInstanceState.getDouble("speed");
            totalTime=savedInstanceState.getInt("totalTime");
            threadTimer=savedInstanceState.getInt("threadTimer");
            onceOnly=false;
            toggleCheck=savedInstanceState.getBoolean("toggleCheck");

        }
        //   Toast.makeText(this, "Running in main is : "+Boolean.toString(running),Toast.LENGTH_SHORT).show();

                                                                                            //    intent = getIntent();
                                                                                          //     messageText = intent.getStringExtra(EXTRA_MESSAGE);
      //  Toast.makeText(this, "INSIDE MAIN " + messageText, Toast.LENGTH_SHORT).show();

  //new
//  Toast.makeText(this, "theme is " +ThemeActivity.myTheme, Toast.LENGTH_SHORT).show();
   messageText=ThemeActivity2.getMyTheme2();
  //      Toast.makeText(this, "messageText is " + messageText, Toast.LENGTH_SHORT).show();
        //
        if (messageText == null) {
            messageText = "random";
        }
        setMyBackGround(messageText);


        //  myloc=(TextView)findViewById(R.id.myLoc);   //All he views are registered here
        // stats = (TextView) findViewById(R.id.allStats);
        allStats="NOT AVAILABLE";
        threadView = (TextView) findViewById(R.id.myTimer);
        myDistance = (TextView) findViewById(R.id.myDis);
        speedView = (TextView) findViewById(R.id.mySpeed);
        startStop = (ToggleButton) findViewById(R.id.startStop);
        actualCalories=(TextView)findViewById(R.id.actualCalories);


    //    Toast.makeText(MainActivity.this,"thread timer= "+Integer.toString(threadTimer) , Toast.LENGTH_SHORT).show();
   //     Toast.makeText(this,"Toggle Check  is  "+Boolean.toString(toggleCheck),Toast.LENGTH_SHORT).show();
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE); //First we asked for the location service
        geocoder = new Geocoder(this); //GEOCODER is used to convert address back and forth..from latitude longitude to address and reverse

        String provider = LocationManager.GPS_PROVIDER;

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
        location = locationManager.getLastKnownLocation(provider); //getting the last known location if the Location is ON on the device
        lastLocation = location;                                   //else null is assigned

        if(location==null)  //If the location is null...loc OFF then flag is set to false and appropriate message is displayed
        {

            Toast.makeText(this,"Please Turn ON the Location/GPS  ",Toast.LENGTH_SHORT).show();
            flag=false;
        }
        else {   //else flag is set true and latitude and longitude are taken ,we need them here as if we call thread first then
            flag=true;  //then there will be null at lat lng and calling geocoder methods would return an empty list causing bugs
            lat = location.getLatitude();  //ALSO IF ELSE IS USED because without them ..if location is null and if we try to call
            lng = location.getLongitude();     //getLatitude() on a null reference then it causes errors and app crashes
        }
      //   Toast.makeText(this,"Only Once is  "+Boolean.toString(onceOnly),Toast.LENGTH_SHORT).show();

   //    if(onceOnly) {

           oneGiantButLightThread();
        //   Toast.makeText(this,"Only Once is  "+Boolean.toString(onceOnly),Toast.LENGTH_SHORT).show();
   // }
        locationManager.requestLocationUpdates(provider, 0, 0, listener);
      //  locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, listener);
    }



    //LISTENER
    private final LocationListener listener=new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if (running) {
                if(lastLocation==null)   //this is done to avoid app crashing if LOCATION is OFF and then STARTED...as lastLOCATIon would be null
                //so calling .dostanceTo()method would cause and error,,,,lastlocation is set to location as this method only gets called where there is a change so of course it is not null now
                {
                    lastLocation=location;
                }

                flag = true;
                lat = location.getLatitude();   //updating latitudes and longitudes
                lng = location.getLongitude();

                distanceInMe = distanceInMe + location.distanceTo(lastLocation);  //updating distance
                if(location.distanceTo(lastLocation)==0)   //if i am not moving/standing at a place then set distance to 0 so speed becomes ~0
                {
                    distanceForSpeed=0;
                }
                distanceForSpeed=distanceForSpeed+location.distanceTo(lastLocation);  //updating distance for speed
                lastLocation = location;
            }
            // setLocation(location);
        }
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };





    public void oneGiantButLightThread() {

//if(location!=null) {
        final Handler handler = new Handler();   //final as we are accessing the handler from inner class so we have to declare it final
        handler.post(new Runnable() {            //no need for changing global to final..all of this is basic java and android


            @Override
            public void run() {
           //     myloc = (TextView) findViewById(R.id.myLoc);  //IN ANOTHER ACTIVITY

                    //    Toast.makeText(MainActivity.this,Boolean.toString(running)+" INSIDE THE THREAD??",Toast.LENGTH_LONG).show();
                    try {
                        //try catch is used because of geocoder method getLocationFrom()...as the server wont respond sometimes IOException
                        if (flag == false) {  //flag inside onCreate
                            allStats="LOCATION NOT AVAILABLE, PLEASE TURN ON THE GPS IF ITS OFF";   //if loc=OFF/NULL
                        } else {
                            list = geocoder.getFromLocation(lat, lng, 1);  //get the ADDRESS and add it to arraylist<ADDRESS>

                            address = list.get(0);
                            //CALLING various address methods....self explainatory
                            allStats = "Country code: " + address.getCountryCode() + "\nCountry Name: " + address.getCountryName() +
                                    "\nAddress: " + address.getAddressLine(0) + ", " + address.getAddressLine(1) + "\nState: " + address.getAdminArea() +
                                    "\nLocality: " + address.getSubLocality();


                       }
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "Exception " + e, Toast.LENGTH_SHORT).show();
                    }

                    //latlng[0] = String.format("Latitude: %.2f\nLongitude: %.2f", lat, lng);//"Lat "+lat+"\nLng "+lng;
                    //myloc.setText(latlng[0]);

                    //FOR TIME Hours:mins:sec
                    int hours = threadTimer / 3600;
                    int minutes = (threadTimer % 3600) / 60;
                    int secs = threadTimer % 60;
                    String time = String.format("%d:%02d:%02d", hours, minutes, secs);
                    threadView.setText(time);
                if(running) {
                    threadTimer++;
                    totalTime++;
                }
                    myDistance();  //setting distance
                    mySpeed();           //for setting speed



                handler.postDelayed(this, 1000);  //runs thread with 1 sec gap
            }


        });


        //   Toast.makeText(this, "Size  here " + Integer.toString(list.size()), Toast.LENGTH_LONG).show();
        //}
        //     else
//{
        //  myloc.setText("Location Currently Unavailable");
//}


    }

    public void myDistance()
    {
        distance=String.format("%.1f KM",distanceInMe/1000); // /1000 fo km
        myDistance.setText(distance);

        double disd=distanceInMe*0.5;
        int dis=(int)disd;
       double steps= weight/3500*dis;
        actualCalories.setText(String.format("%.2f",steps));
    }

    //new Stuff from here on
    public void mySpeed()
    {
        String speedy;

        /////delete it afterwards
   //     String speedy2="";
     //   String timeNd;
       // TextView tv=(TextView)findViewById(R.id.speedAccToDIM);
        //TextView tv2=(TextView)findViewById(R.id.timeAndDistanceForSpeed);
        ////
       // if(distanceInMe == 0) //delete
        //{
          //  speedy2="0.0 m/s";
        //}
        if (distanceForSpeed==0)
        {
            speedy="0.0 m/s";
        }
        else {
            speed =distanceForSpeed / totalTime;
            speedy = String.format("%.2f m/s", speed);  //multiply by 3.6 for km/hr , 1 m/s * 18/5= 3.6
          //  speedy2=String.format("WRONG SPEED:  %.2f m/s", distanceInMe/totalTime);  //delete
        }
       // timeNd="Time "+Integer.toString(totalTime)+" Distance "+Double.toString(distanceForSpeed); //delete
        speedView.setText(speedy);
        //tv.setText(speedy2);  //delete
        //tv2.setText(timeNd);
    }











    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem menuItem = menu.findItem(R.id.action_share);
        shareActionProvider = (ShareActionProvider) menuItem.getActionProvider();
        setIntent("Yeah Whatever");
        return super.onCreateOptionsMenu(menu);
    }

    private void setIntent(String text)

    {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, text);
        shareActionProvider.setShareIntent(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_reset:
                startStop.setChecked(false);
                running=false;
                totalTime=0;
                threadTimer=0;
                speed=0;
                distanceInMe=0;
                totalTime=0;
                distanceForSpeed=0;
                //  myloc.setText("NO WHERE ");
                //stats.setText("NO STATS FOR YOU ");
                threadView.setText("00:00:00");
                myDistance.setText("0.00 KM");
                speedView.setText("0.00 m/s");
//                Toast.makeText(MainActivity.this,Boolean.toString(running)+"RESET",Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_theme:
                                                                                                // running=false;
                Intent intent = new Intent(this, ThemeActivity2.class);
                 startActivity(intent);
                                                        //  finish();
                return true;
            case R.id.action_exit:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }










    public void setMyBackGround(String messageText)
    {
        switch (messageText)
        {
            case "theme":
                relativeLayout.setBackgroundResource(R.drawable.theme);
                break;
            case "theme2":
                relativeLayout.setBackgroundResource(R.drawable.theme2);
                break;
            case "theme3":
                relativeLayout.setBackgroundResource(R.drawable.theme3);
                break;
            case "theme4":
                relativeLayout.setBackgroundResource(R.drawable.theme4);
                break;
            case "theme5":
                relativeLayout.setBackgroundResource(R.drawable.theme5);
                break;

        }
    }









    public void onToggleClick(View view)
    {
        boolean on = ((ToggleButton) view).isChecked();

        if (on) {
            running=true;
  //          Toast.makeText(MainActivity.this,Boolean.toString(running)+"START ",Toast.LENGTH_LONG).show();
        } else {
            running=false;
            totalTime=0;
            distanceForSpeed=0;
            speedView.setText("0.00 m/s");
          //  Toast.makeText(MainActivity.this,Boolean.toString(running)+"STOP",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putDouble("distanceInMe", distanceInMe);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putDouble("distanceForSpeed", distanceForSpeed);
        savedInstanceState.putInt("totalTime", totalTime);
        savedInstanceState.putInt("threadTimer", threadTimer);
        savedInstanceState.putDouble("speed", speed);

        /*
if(startStop.isChecked())
{
    toggleCheck=true;
    savedInstanceState.putBoolean("toggleCheck",toggleCheck);
}
        else
{
    toggleCheck=false;
    savedInstanceState.putBoolean("toggleCheck",toggleCheck);
}*/
    }

    public void onLocationClicked(View view)
    {
        Intent intent=new Intent(this,MyLocation.class);
        intent.putExtra(MyLocation.EXTRA, allStats);
        startActivity(intent);
    }




    @Override
    protected void onStart() {
        super.onStart();
        messageText=ThemeActivity2.getMyTheme2();
        if (messageText == null) {
            messageText = "random";
        }
       setMyBackGround(messageText);
    }

}
