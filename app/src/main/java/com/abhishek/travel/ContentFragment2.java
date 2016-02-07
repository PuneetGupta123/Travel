package com.abhishek.travel;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.abhishek.travel.R;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.api.client.json.GenericJson;
import com.kinvey.android.AsyncAppData;
import com.kinvey.android.AsyncCustomEndpoints;
import com.kinvey.android.callback.KinveyListCallback;
import com.kinvey.java.Query;
import com.kinvey.java.core.KinveyClientCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContentFragment2 extends Fragment {


    HashMap<String,String> map;
    String TAG="ContentFragment2";//TODO:Change the name of tabs
    String [] values =
            {"Australia","Bulgaria","Brazil","Canada","Switzerland","China" ,"Czech Republic","Denmark","Britain" ,"Hong Kong",
                    "Croatia" ,"Hungary","Indonesia","Israel" ,"India" ,"Japan","South Korea","Mexico" ,"Malaysia", "Norway",
                    "New Zealand" ,"Philippines" ,"Polish","Italy" ,"Russia", "Sweden" ,"Singapore" ,"Thailand" ,"Turkey"
                    ,"USA" ,"South Africa"};

    Button calculate;
    String fromCurrency,toCurrency;
    EditText amount;
    Double enteredAmount;
    public static ContentFragment2 newInstance(int pageIndex) {
        ContentFragment2 contentFragment2 = new ContentFragment2();
        Bundle args = new Bundle();
        args.putInt("pageIndex", pageIndex);
        contentFragment2.setArguments(args);
        return contentFragment2;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_layout_2, container, false);
        final Spinner spinner = (Spinner)view.findViewById(R.id.spinner1);
        calculate=(Button)view.findViewById(R.id.calculate);
        amount=(EditText)view.findViewById(R.id.amount);
        ArrayAdapter<String> LTRadapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, values);
        LTRadapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(LTRadapter);
        final Spinner spinner2 = (Spinner) view.findViewById(R.id.spinner2);
        ArrayAdapter<String> LTRadapter2 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, values);
        LTRadapter2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner2.setAdapter(LTRadapter2);
        fillHashMap();
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(new String("").equals(amount.getText().toString()))
                {
                    Log.d(TAG,"in if");
                    Snackbar.make(view,"Enter the Amount", Snackbar.LENGTH_LONG).show();
                    AsyncCustomEndpoints endpoints = LoginScreen.mKinveyClient.customEndpoints();
                    endpoints.callEndpoint("test", new GenericJson(), new KinveyClientCallback<GenericJson>() {
                        @Override
                        public void onSuccess(GenericJson result) {
                            //endpoint code run successfully
                            Log.d(TAG,result.toString());
                        }
                        @Override
                        public void onFailure(Throwable error) {
                            //something went wrong!
                            Log.d(TAG,error.toString());
                        }
                    });
                }
                else
                {
                    enteredAmount=Double.valueOf( amount.getText().toString());
                    fromCurrency = (String) getKeyFromValue(spinner.getSelectedItem());
                    toCurrency = (String) getKeyFromValue(spinner2.getSelectedItem());
                    Log.d(TAG, fromCurrency + " " + toCurrency);
                    calculateExchangeRate(fromCurrency, toCurrency);
                }
            }
        });
        return view;
    }

    public void calculateExchangeRate(final String from , final String to)
    {
        MyVolley.init(getActivity().getBaseContext());
        RequestQueue queue = MyVolley.getRequestQueue();
        StringRequest myReq = new StringRequest(Request.Method.GET,"http://api.fixer.io/latest?base="+from+"&symbols="+to
                 , reqSuccessListenerFoodList(), reqErrorListenerFoodList()) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                return headers;
            }

            protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };
        myReq.setRetryPolicy(new DefaultRetryPolicy(25000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(myReq);
    }
    private Response.Listener<String> reqSuccessListenerFoodList() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG,"in volley success");
                Log.d(TAG, "Response" + response);

                try
                {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject rates=jsonObject.getJSONObject("rates");
                    double rate = (double)rates.get(toCurrency);
                    double calculatedAmount=rate*enteredAmount;
                    checkIfMoneyAvailable(toCurrency,calculatedAmount);
                    Log.d(TAG,String.valueOf(rate));

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        };
    }

    private Response.ErrorListener reqErrorListenerFoodList() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG,"in volley error");
                Log.d(TAG, error.toString());
            }
        };
    }

    public void checkIfMoneyAvailable(String currency, Double value)
    {
        CurrencyStore currencyStore = new CurrencyStore();
        Query myQuery = LoginScreen.mKinveyClient.query();
        myQuery.equals("currency",currency);
        AsyncAppData<CurrencyStore> myEvents = LoginScreen.mKinveyClient.appData("currencyStore", CurrencyStore.class);
        myEvents.get(myQuery, new KinveyListCallback<CurrencyStore>() {
            @Override
            public void onSuccess(CurrencyStore[] results) {
                if(results.length==0)
                {

                }
                else
                {

                }
                Log.v("TAG", "received "+ results.length + " events");
            }
            @Override
            public void onFailure(Throwable error) {
                Log.e("TAG", "failed to fetchByFilterCriteria", error);
            }
        });
    }

    void fillHashMap()
    {
        map=new HashMap<>();

        map.put("AUD", "Australia");

        map.put("BGN", "Bulgaria");

        map.put("BRL","Brazil");

        map.put("CAD","Canada");

        map.put("CHF","Switzerland");

        map.put("CNY","China");

        map.put("CZK","Czech Republic");

        map.put("DKK","Denmark");

        map.put("GBP","Britain");

        map.put("HKD","Hong Kong");

        map.put("HRK","Croatia");

        map.put("HUF","Hungary");

        map.put("IDR","Indonesia");

        map.put("ILS","Israel");

        map.put("INR","India");

        map.put("JPY","Japan");

        map.put("KRW","South Korea");

        map.put("MXN","Mexico");

        map.put("MYR","Malaysia");

        map.put("NOK","Norway");

        map.put("NZD","New Zealand");

        map.put("PHP","Philippines");

        map.put("PLN","Polish");

        map.put("RON","Italy");

        map.put("RUB","Russia");

        map.put("SEK","Swedish");

        map.put("SGD","Singapore");

        map.put("THB","Thailand");

        map.put("TRY","Turkey");

        map.put("USD","USA");

        map.put("ZAR","South Africa");

    }

    public  Object getKeyFromValue( Object value) {
        for (Object o : map.keySet()) {
            if (map.get(o).equals(value)) {
                return o;
            }
        }
        return null;
    }
}
