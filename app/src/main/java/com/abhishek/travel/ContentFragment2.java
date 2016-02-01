package com.abhishek.travel;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.abhishek.travel.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContentFragment2 extends Fragment {


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



            String [] values1 =
                    {"My country","USA","INDIA","DUBAI","AUSTRALIA","U.K.","SPAIN","RUSSIA",};
            Spinner spinner = (Spinner)view.findViewById(R.id.spinner1);
            ArrayAdapter<String> LTRadapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, values1);
            LTRadapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
            spinner.setAdapter(LTRadapter);



        String [] values2 =
                {"Visiting country","USA","INDIA","DUBAI","AUSTRALIA","U.K.","SPAIN","RUSSIA",};
        Spinner spinner2 = (Spinner)view.findViewById(R.id.spinner2);
        ArrayAdapter<String> LTRadapter2 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, values2);
        LTRadapter2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner2.setAdapter(LTRadapter2);



        return view;

    }

}
