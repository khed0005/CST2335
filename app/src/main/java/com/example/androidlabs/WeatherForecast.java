package com.example.androidlabs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherForecast extends AppCompatActivity {

    ProgressBar progressbar;
    ImageView imageView;
    TextView uvText, minTemperature, maxTemperature, currentTemperature;
    public static final String weatherURL = "http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=7e943c97096a9784391a981c4d878b22&mode=xml&units=metric";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        ForecastQuery networkThread = new ForecastQuery();
        networkThread.execute(weatherURL);

        imageView =(ImageView)findViewById(R.id.imageView);
        uvText= (TextView)findViewById(R.id.uvRating);
        minTemperature=(TextView)findViewById(R.id.minTemp);
        maxTemperature=(TextView)findViewById(R.id.maxTemp);
        currentTemperature=(TextView)findViewById(R.id.currentTemp);


        progressbar = (ProgressBar) findViewById(R.id.progressBar);
        progressbar.setVisibility(View.VISIBLE);


    }

    private class ForecastQuery extends AsyncTask<String, Integer, String> {

        String windSpeed;
        String tempMin;
        String tempMax;
        String tempValue;
        Bitmap weatherBitmap;
        float uv;
        HttpURLConnection connection;


        @Override
        protected String doInBackground(String... args) {

            try {
                URL url = new URL(weatherURL);

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inStream = urlConnection.getInputStream();

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(inStream, "UTF-8");

                while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                    if (xpp.getEventType() == XmlPullParser.START_TAG) {
                        String tagName = xpp.getName(); //get the name of the starting tag: <tagName>
                        if (tagName.equals("temperature")) {
                            tempValue = xpp.getAttributeValue(null, "value");
                            tempMin = xpp.getAttributeValue(null, "min");
                            tempMax = xpp.getAttributeValue(null, "max");

                            publishProgress(25, 50, 75); //tell android to call onProgressUpdate with 1 as parameter
                            Thread.sleep(750);
                            Log.e("AsyncTask", "Temperature value: " + tempValue + " Min temperature: " + tempMin + " Max Temperature: " + tempMax);

                        } else if (tagName.equals("weather")) {
                            String iconName = xpp.getAttributeValue(null, "icon");
                            Log.e("AsyncTask", "Icon Name: " + iconName);

                            try {
                                if (!fileExistance(iconName + ".png")) {
                                    URL secondURL = new URL("http://openweathermap.org/img/w/" + iconName + ".png");
                                    connection = (HttpURLConnection) secondURL.openConnection();
                                    connection.connect();

                                    int responseCode = connection.getResponseCode();
                                    if (responseCode == 200) {
                                        weatherBitmap = BitmapFactory.decodeStream(connection.getInputStream());
                                    }
                                    FileOutputStream outputStream = openFileOutput(iconName + ".png", Context.MODE_PRIVATE);
                                    weatherBitmap.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                                    outputStream.flush();
                                    outputStream.close();
                                    Log.e("Image Not Found", "Need to Download");
                                } else {

                                    FileInputStream fis = null;
                                    try {
                                        File file = getBaseContext().getFileStreamPath(iconName + ".png");
                                        fis = new FileInputStream(file);
                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                    weatherBitmap = BitmapFactory.decodeStream(fis);
                                    Log.e("Found", "The imageg is already downloaded");
                                }
                                publishProgress(95);
                                Thread.sleep(750);
                            } catch (Exception ex) {
                                Log.e("Crash", ex.getMessage());
                            }
                        }
                    }
                    xpp.next();
                }

                URL uvURL = new URL("http://api.openweathermap.org/data/2.5/uvi?appid=7e943c97096a9784391a981c4d878b22&lat=45.348945&lon=-75.759389");
                HttpURLConnection uvConnection = (HttpURLConnection) uvURL.openConnection();
                inStream = uvConnection.getInputStream();

                BufferedReader reader = new BufferedReader( new InputStreamReader(inStream, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();

                String line = null;

                while(( line = reader.readLine()) != null){
                    sb.append( line + "\n");
                }
                String result = sb.toString();
                JSONObject jObject = new JSONObject( result);
                uv = Float.valueOf(jObject.getString("value"));
                Log.e( "UV is: ", ""+ uv);
                Thread.sleep(750);

            }catch( Exception ex){
                Log.e("Crash!!!", ex.getMessage());
            }
                return "step8";
            }

        public boolean fileExistance(String fname) {
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();
        }
        @Override
        protected void onProgressUpdate(Integer...values){

            progressbar.setVisibility(View.VISIBLE);
            progressbar.setProgress(values[0]);
        }
        @Override
        protected void onPostExecute(String s){

            imageView.setImageBitmap(weatherBitmap);
            uvText.setText("UV Rating: "+uv);
            minTemperature.setText("Minimum Temperature: "+tempMin);
            maxTemperature.setText("Maximum Temperature: "+tempMax);
            currentTemperature.setText("Current temperatute: "+tempValue);

        }

    }
}
