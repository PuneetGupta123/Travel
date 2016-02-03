package com.abhishek.travel;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.abhishek.travel.R;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContentFragment2 extends Fragment {


    String TAG="ContentFragment2";//TODO:Change the name of tabs
    String [] values =
            {"USA","India","Dubai","Australia","United Kingdom","Spain","Russia",};
    Button calculate;
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
        Spinner spinner = (Spinner)view.findViewById(R.id.spinner1);
        calculate=(Button)view.findViewById(R.id.calculate);
        ArrayAdapter<String> LTRadapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, values);
        LTRadapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(LTRadapter);
        Spinner spinner2 = (Spinner)view.findViewById(R.id.spinner2);
        ArrayAdapter<String> LTRadapter2 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, values);
        LTRadapter2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner2.setAdapter(LTRadapter2);
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO:Spinner se currency code nikal ke bhej
                calculateExchangeRate("","");
            }
        });
        return view;
    }

    public void calculateExchangeRate(final String from , final String to)
    {
        MyVolley.init(getActivity().getBaseContext());
        RequestQueue queue = MyVolley.getRequestQueue();
        StringRequest myReq = new StringRequest(Request.Method.GET,"http://api.fixer.io/latest"
                , reqSuccessListenerFoodList(), reqErrorListenerFoodList()) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("server_token", "maMItAGDV5HHqKAG3nLfLTlhMOnQscAONF_aeFcu");
                return headers;
            }

            protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("base", from);
                params.put("symbols",to);


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

}
