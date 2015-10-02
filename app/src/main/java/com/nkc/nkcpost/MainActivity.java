package com.nkc.nkcpost;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.renderscript.Allocation;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_power) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        Drawable myDrawable; //Drawable you want to display
        String title;

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        myDrawable = getResources().getDrawable(R.mipmap.ic_email_white_24dp, getApplicationContext().getTheme());
                    } else {
                        myDrawable = getResources().getDrawable(R.mipmap.ic_email_white_24dp);
                    }
                    title = "New";
                    break;
                case 1:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        myDrawable = getResources().getDrawable(R.mipmap.ic_history_white_24dp, getApplicationContext().getTheme());
                    } else {
                        myDrawable = getResources().getDrawable(R.mipmap.ic_history_white_24dp);
                    }
                    title = "History";
                    break;
                case 2:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        myDrawable = getResources().getDrawable(R.mipmap.ic_info_outline_white_24dp, getApplicationContext().getTheme());
                    } else {
                        myDrawable = getResources().getDrawable(R.mipmap.ic_info_outline_white_24dp);
                    }
                    title = "About";
                    break;
            }
            SpannableString sb = new SpannableString("   " + title); // space added before text for convenience
            //try {
                myDrawable.setBounds(5, 5, myDrawable.getIntrinsicWidth(), myDrawable.getIntrinsicHeight());
                ImageSpan span = new ImageSpan(myDrawable, DynamicDrawableSpan.ALIGN_BOTTOM);
                sb.setSpan(span, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            //} catch (Exception e) {
                // TODO: handle exception
           // }

            return sb;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            switch (getArguments().getInt(ARG_SECTION_NUMBER)){
                case 1:
                    View rootView = inflater.inflate(R.layout.fragment_main, container, false);
                    TextView textView = (TextView) rootView.findViewById(R.id.section_label);
                    textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
                    return rootView;
                case 2:
                    View rootView1 = inflater.inflate(R.layout.fragment_main, container, false);
                    return rootView1;
                case 3:
                    View rootView2 = inflater.inflate(R.layout.fragment_about, container, false);
                    return rootView2;
            }
            return null;
        }
    }
}
