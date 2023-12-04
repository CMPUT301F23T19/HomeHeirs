package com.example.homeheirs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.journeyapps.barcodescanner.CaptureActivity;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * The Scanner class is responsible for handling barcode scanning functionality
 * using the ZXing library and making API requests to retrieve product information.
 */
public class Scanner {

    // Base URL for the barcode lookup API
    private String apiBase = "https://api.barcodelookup.com/v3/products?barcode=";
    // API key for accessing the barcode lookup service
    private String apiKey = "4w9q79i04ahvo4tspqzi22e2j4jle2";
    private Context context;
    String description;
    String serial_num;
    private OnScanActivityListener listener;

    /**
     * Constructs a Scanner object with a given context.
     *
     * @param context The context in which the Scanner is created.
     */
    public Scanner(Context context) {
        this.context = context;
        this.listener = (OnScanActivityListener) context;
    }

    /**
     * Constructs a Scanner object with a given context and fragment.
     *
     * @param context  The context in which the Scanner is created.
     * @param fragment The fragment associated with the Scanner.
     */
    public Scanner(Context context, Fragment fragment) {
        this.context = context;
        this.listener = (OnScanActivityListener) fragment;
    }

    /**
     * Initiates the barcode scanning process with the provided barcode raw value.
     *
     * @param barcodeRawValue The raw value obtained from the scanned barcode.
     */
    public void scanCode(String barcodeRawValue) {
        String api = apiBase + barcodeRawValue + "&formatted=y&key=" + apiKey;
        getData(api);
    }

    /**
     * Interface to be implemented by activities or fragments listening for scan results.
     */
    public interface OnScanActivityListener {
        /**
         * Called when a scan result is obtained, providing the product description and serial number.
         *
         * @param prod_description The description of the scanned product.
         * @param prod_serial_num  The serial number of the scanned product.
         */
        void onScanResult(String prod_description, String prod_serial_num);
    }

    /**
     * Retrieves product information from the barcode lookup API.
     *
     * @param api The API URL for retrieving product information based on barcode.
     */
    private void getData(String api) {
        RequestQueue queue = Volley.newRequestQueue(context);
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
                            listener.onScanResult(description, serial_num);
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

    /**
     * Initiates the barcode scanning process using the provided ScanOptions and ActivityResultLauncher.
     *
     * @param barLauncher The ActivityResultLauncher for launching the barcode scanning activity.
     */
    public void startScan(ActivityResultLauncher<ScanOptions> barLauncher) {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Volume up to flash on");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureActivity.class);
        barLauncher.launch(options);
    }
}

