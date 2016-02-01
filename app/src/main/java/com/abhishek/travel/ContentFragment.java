package com.abhishek.travel;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.abhishek.travel.R;

public class ContentFragment extends Fragment {
    // Store instance variables

    // newInstance constructor for creating fragment with arguments
    public static ContentFragment newInstance(int pageIndex) {
        ContentFragment contentFragment = new ContentFragment();
        Bundle args = new Bundle();
        args.putInt("pageIndex", pageIndex);
        contentFragment.setArguments(args);
        return contentFragment;
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_layout, container, false);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            int pageIndex = bundle.getInt("pageIndex", 0);

            if(pageIndex==0)
            {
                TextView tvSection = (TextView) view.findViewById(R.id.tvSection);
                tvSection.setText(getString(R.string.page) + " " + String.valueOf(pageIndex + 1));

            }


        }
        return view;

    }
}
