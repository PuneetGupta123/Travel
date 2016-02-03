package com.abhishek.travel;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;



import com.abhishek.travel.R;


class ContentFragmentAdapter extends FragmentPagerAdapter {
    Integer itemCount = 1;
    Context context;

    public ContentFragmentAdapter(FragmentManager fragmentManager, Context context, int itemCount) {
        super(fragmentManager);
        this.itemCount=itemCount;
        this.context =context;
    }

    @Override
    public int getCount() {
        return itemCount;
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {

        if(position == 0) // if the position is 0 we are returning the First tab
        {
            return new ContentFragment();
        }
        else
        {
            return new ContentFragment2();
        }
        // return ContentFragment.newInstance(position);
        }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        return context.getString(R.string.tab) + " " + String.valueOf(position + 1);
    }

}
