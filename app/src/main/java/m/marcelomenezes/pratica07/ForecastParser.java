package m.marcelomenezes.pratica07;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class ForecastParser {
    private static String getReadableDateString(long time) {
        SimpleDateFormat shortenedDateFormat = new SimpleDateFormat("EEE  MMM  dd");
        return shortenedDateFormat.format(time);
    }

    private static String formatHighLows(double high, double low) {
        long roundedHigh = Math.round(high);
        long roundedLow = Math.round(low);
        String highLowStr = roundedHigh + "/" + roundedLow;
        return highLowStr;
    }

    public static String[] getDataFromJson(String forecastJsonStr, int numDays) throws JSONException {
        final String OWM_LIST = "list";
        final String OWM_WEATHER = "weather";
        final String OWM_TEMPERATURE = "temp";
        final String OWM_MAX = "max";
        final String OWM_MIN = "min";
        final String OWM_DESCRIPTION = "main";
        JSONObject forecastJson = new JSONObject(forecastJsonStr);
        JSONArray weatherArray = forecastJson.getJSONArray(OWM_LIST);
        GregorianCalendar cal = new GregorianCalendar();
        String[] resultStrs = new String[numDays];
        for (int i = 0; i < weatherArray.length(); i++) {
            //  Get  the  JSON  object  representing  the  day
             JSONObject  dayForecast= weatherArray.getJSONObject(i);
             JSONObject weatherObject  = dayForecast.getJSONArray(OWM_WEATHER).getJSONObject(0);
             String  description  = weatherObject.getString(OWM_DESCRIPTION);
             JSONObject  temperatureObject  = dayForecast.getJSONObject(OWM_TEMPERATURE);
             double  high  = temperatureObject.getDouble(OWM_MAX);
             double  low  = temperatureObject.getDouble(OWM_MIN);
             String  highAndLow  =  formatHighLows(high,  low);
             String  date  = getReadableDateString(cal.getTimeInMillis());
             resultStrs[i]  =  date  +  "  -"  +  description  +  "  -"  +  highAndLow;
             cal.add(GregorianCalendar.DATE, 1);
             }
             return  resultStrs;
    }
}

