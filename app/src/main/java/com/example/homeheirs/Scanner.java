package com.example.homeheirs;

import android.content.DialogInterface;

import androidx.activity.ComponentActivity;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Scanner extends ComponentActivity {

    String barcode_raw_value;
    String api;
    String api_base = "https://api.barcodelookup.com/v3/products?barcode=";
    String api_key = "4w9q79i04ahvo4tspqzi22e2j4jle2";
    String description;
    String serial_num;

    public Scanner() {
    }

    public void scanCode() {
        ScanOptions options = new ScanOptions();
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLauncher.launch(options);
    }


    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result ->
    {
        if (result.getContents() != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Scanner.this);
            builder.setTitle("Result");
            barcode_raw_value = result.getContents();
            api = api_base + barcode_raw_value + "&formatted=y&key=" + api_key;
            getData();
            builder.setMessage("Successful!");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).show();
        }
    });

    private void getData() {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, api,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray singleProduct = jsonObject.getJSONArray("products");
                            JSONObject product = singleProduct.getJSONObject(0);
                            description = product.getString("description");
                            serial_num = product.getString("mpn");
                            String sample = description;
                        } catch (JSONException e) {
                            Log.e("api", "onResponse: " + e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("api", "onErrorResponse:" + error.getLocalizedMessage());
            }
        });
        queue.add(stringRequest);
    }
}