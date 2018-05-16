package m.marcelomenezes.pratica07;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ForecastTask extends AsyncTask< String,
        Void,
        String[] > {
    private final String LOG_TAG = ForecastTask.class.getSimpleName();
    private String[] forecast = null;
    private ForecastListener listener = null;
    private final String APPID = "052154b99b4fd354d3f18dd47dc4246a";
    public ForecastTask(ForecastListener listener) {
        this.listener = listener;
    }

    public  interface  ForecastListener  {
        public  void  showForecast(String  []  forecast);
    }

    @Override
    protected String[] doInBackground(String...params) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        int noOfDays = 7;
        String locationString = params[0];
        String forecastJson = null;
        try {
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("http");
            builder.authority("api.openweathermap.org");
            builder.appendPath("data/2.5/forecast/daily");
            builder.appendQueryParameter("q", locationString);
            builder.appendQueryParameter("mode", "json");
            builder.appendQueryParameter("units", "metric");
            builder.appendQueryParameter("cnt", "" + noOfDays);
            builder.appendQueryParameter("appid", APPID);
            URL url = new URL(builder.build().toString());
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                forecastJson = null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            if (buffer.length() == 0) {
                forecastJson = null;
            } else {
                forecastJson = buffer.toString();
            }
            forecast = ForecastParser.getDataFromJson(forecastJson, noOfDays);
        } catch(IOException e) {
            Log.e(LOG_TAG, "Error  ", e);
        } catch(JSONException e) {
            Log.e(LOG_TAG, "JSON  Error  ", e);
        } finally {
            if (urlConnection != null) urlConnection.disconnect();
            if (reader != null) {
                try {
                    reader.close();
                } catch(final IOException e) {
                    Log.e(LOG_TAG, "Error  closing  stream", e);
                }
            }
        }
        return forecast;
    }
    @Override
    protected void onPostExecute(String[] resultStrs) {
        for (String s: resultStrs) Log.v(LOG_TAG, "Forecast  entry:  " + s);
        listener.showForecast(resultStrs);
    }
}
