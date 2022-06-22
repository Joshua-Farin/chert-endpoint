package dev.empty;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

public class Main {

    public static void main(String[] args) {



        // METHOD java.net.http.HttpClient

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://www.healthit.gov/data/open-api?source=Meaningful-Use-Acceleration-Scorecard.csv")).build();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(Main::parse)
                .join();

    }

    public static String parse(String responseBody)
    {
        ArrayList<String> filteredHospitals = new ArrayList<>();
        JSONArray hospitals = new JSONArray(responseBody);
        for(int i = 0; i < hospitals.length(); i++)
        {
            JSONObject hospital = hospitals.getJSONObject(i);
            String area = hospital.getString("region");
            double pct1 = hospital.getDouble("pct_hospitals_mu_aiu");
            double pct2 = hospital.getDouble("pct_hospitals_mu");

            if(hospital.getInt("period") == 2014)
            {
                String string = String.format(area + " " + pct1 + " " + pct2);
                System.out.println(string);
            }
        }
        return null;
    }
}
