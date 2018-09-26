package lammer.florian.weatherapi;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class getWeather extends AsyncTask<Void, Void, Void>{

    String apiURL = "";
    String data = "";
    String dataParsed = "";
    String APIkey = ""; //Get API Key from: https://openweathermap.org/

    public getWeather(String enteredCity) {
        String city = enteredCity;
        apiURL = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + APIkey;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        try {
            //Legt ein neues URL Element mit der api URL an
            URL url = new URL(apiURL);
            //Stellt die Verbindung her
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            //Holt den Input von der URL
            InputStream inputStream = httpURLConnection.getInputStream();
            //Bereitet das Auslesen des Inputs vor
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            //temporärer Auslesestring für jede Linie im Input
            String line = "";

            //Schleife die so lange die nächste Linie im Input ausliest bis keine mehr vorhanden ist
            while (line != null){
                line = bufferedReader.readLine();   //Liest die aktuelle Linie aus
                data = data + line;                 //Legt die aktuelle Linie in den data String ab der schon alle vorherigen Linien enthält
            }

            //Data parsen für die schönere Darstellung
            JSONObject JO = new JSONObject(data);

            dataParsed =
                    "Weather: " + JO.getJSONArray("weather").getJSONObject(Integer.parseInt("0")).get("description") + "\n" + "\n" +
                    //"Weather: " + JO.getJSONObject("weather").getJSONObject("0").get("description") + "\n" +
                    "Temperature: " + Math.round(Double.parseDouble(String.valueOf(JO.getJSONObject("main").get("temp")))-273.15) + "°C" + "\n" +
                    "Humidity: " + JO.getJSONObject("main").get("humidity") + "%" + "\n" +
                    "Airpressure: " + JO.getJSONObject("main").get("pressure") + "hPa" + "\n" +
                    "Windspeed: " + JO.getJSONObject("wind").get("speed") + "m/s" + "\n" + "\n" +
                    "City: " + JO.get("name") + "\n" +
                    "Latitude: " + JO.getJSONObject("coord").get("lat") + "\n" +
                    "Longitude: " + JO.getJSONObject("coord").get("lon") + "\n";
                    //"Windangle: " + JO.getJSONObject("wind").get("deg") + "\n";


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;
    }


    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        MainActivity.showJSONtextView.setText(this.dataParsed);
    }
}
