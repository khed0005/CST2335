package com.example.androidlabs;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherForecast extends AppCompatActivity {

    ProgressBar pbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        ForecastQuery networkThread = new ForecastQuery();
        networkThread.execute( "http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=7e943c97096a9784391a981c4d878b22&mode=xml&units=metric" );

        pbar=(ProgressBar)findViewById(R.id.progressBar);
        pbar.setVisibility(View.VISIBLE);


    }

    private class ForecastQuery extends AsyncTask<String, Integer, String>{

        String windSpeed;
        String min;
        String max;
        String currentTemp;
        Bitmap currentWeather;

        @Override
        protected String doInBackground(String... args) {

            try {
                String myURL = "http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=7e943c97096a9784391a981c4d878b22&mode=xml&units=metric";
                URL url = new URL(myURL);

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inStream = urlConnection.getInputStream();

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput( inStream  , "UTF-8");


                while(xpp.getEventType() != XmlPullParser.END_DOCUMENT)
                {
                    if(xpp.getEventType() == XmlPullParser.START_TAG)
                    {
                        String tagName = xpp.getName(); //get the name of the starting tag: <tagName>
                        if(tagName.equals("temperature"))
                        {
                            String tempValue = xpp.getAttributeValue(null, "value");
                            String tempMin = xpp.getAttributeValue(null, "min");
                            String tempMax = xpp.getAttributeValue(null, "max");
                            Log.e("AsyncTask", "Temperature value: "+ tempValue+" Min temperature: "+tempMin+" Max Temperature: "+tempMax);
                            publishProgress(25,50,75); //tell android to call onProgressUpdate with 1 as parameter
                        }
                        else if(tagName.equals("weather")){
                            String iconName = xpp.getAttributeValue(null, "icon");
                            Log.e("AsyncTask", "Icon Name: "+ iconName);
                            publishProgress(100);
                        }
                    }

                    xpp.next();
                }
            } catch (Exception ex)
            {
                Log.e("Crash!!", ex.getMessage() );
            }
            return "step8";
        }



    }
}
