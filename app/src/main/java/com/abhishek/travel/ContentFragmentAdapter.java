package com.abhishek.travel;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;



import com.abhishek.travel.R;


class ContentFragmentAdapter extends FragmentPagerAdapter {
    private static int NUM_ITEMS = 1;
    private final Context c;

    public ContentFragmentAdapter(FragmentManager fragmentManager, Context context, int item_count) {
        super(fragmentManager);
        NUM_ITEMS = item_count;
        c = context;
    }

    // Returns total number of pages
    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {

        if(position == 0) // if the position is 0 we are returning the First tab
        {
            ContentFragment2 tab1 = new ContentFragment2();
            return tab1;

        }
        else
        {
            ContentFragment tab2 = new ContentFragment();
            return tab2;


        }

       // return ContentFragment.newInstance(position);
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        return c.getString(R.string.tab) + " " + String.valueOf(position + 1);
    }

}
