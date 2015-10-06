package com.nkc.nkcpost;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.nkc.nkcpost.helper.SQLiteHandler;
import com.nkc.nkcpost.helper.SessionManager;
import com.nkc.nkcpost.model.Mail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private static final String TAG = MainActivity.class.getSimpleName();
    private SQLiteHandler db;
    private SessionManager session;


    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        session = new SessionManager(getApplicationContext());
        if(!session.isLoggedIn()){
            logoutUser();
        }

        db = new SQLiteHandler(getApplicationContext());

        //HashMap<String, String> user = db.getUserDetails();

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }

    /**
     * Logging out the user. Will set isLoggedIn flag to false in shared
     * preferences Clears the user data from sqlite users table
     * */
    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
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
            logoutUser();
            //return true;
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
            //getInbox("5470890004657","0");
            //return PlaceholderFragment.newInstance(position, "5470890004657", "0");
            String state = "0";
            String userid = "";
            switch (position){
                case 0:
                    state = "0";
                    return PlaceholderFragment.newInstance(state);
                case 1:
                    state = "1";
                    return PlaceholderFragment.newInstance(state);
                case 2:
                    return new AboutFragment();
            }
            return null;
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
                    return "New";
                case 1:
                    return  "History";
                case 2:
                    return "About";
            }

            return null;
        }

        public int getIcon(int position){
            switch (position) {
                case 0:
                    return R.mipmap.ic_email_white_24dp;
                case 1:
                    return  R.mipmap.ic_history_white_24dp;
                case 2:
                    return R.mipmap.ic_info_outline_white_24dp;
            }

            return R.mipmap.ic_email_white_24dp;
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
        private static final String ARG_USERID = "userid";
        private static final String ARG_STATUS = "status";
        private static final String KEY_USERID = "userid";
        private static final String KEY_STATUS = "status";

        private List<Mail> mailList = new ArrayList<Mail>();
        private ListView listView;
        private CustomListAdapter adapter;
        private TextView _user_name;

        private ProgressDialog pDialog;
        private SQLiteHandler db1;
        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(String status) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putString(ARG_STATUS, status);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            _user_name = (TextView) rootView.findViewById(R.id.user_name);

            db1 = new SQLiteHandler(getActivity());

            // Fetching user details from sqlite
            HashMap<String, String> user = db1.getUserDetails();
            _user_name.setText("Name: " + user.get("name"));

            listView = (ListView) rootView.findViewById(R.id.list);
            adapter = new CustomListAdapter(getActivity(), mailList);
            listView.setAdapter(adapter);

            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Loading ...");
            pDialog.show();

            JSONObject obj = new JSONObject();
            try {
                obj.put("userid", user.get("uid"));
                obj.put("status", getArguments().getString(ARG_STATUS));
            }catch (JSONException e){
                System.out.print(e.getMessage());
            }

            JsonArrayRequest mailReq = new JsonArrayRequest(Method.POST, AppConfig.URL_INBOX, obj,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            Log.d(TAG, response.toString());
                            hidePDialog();
                            mailList.clear();

                            for (int i = 0; i < response.length(); i++){
                                try {
                                    JSONObject obj = response.getJSONObject(i);
                                    Mail mail = new Mail();
                                    mail.setNumber(obj.getString("number"));
                                    mail.setTitle(obj.getString("title"));
                                    mail.setEmsid(obj.getString("emsid"));
                                    mail.setDatetime(obj.getString("datetime"));

                                    mailList.add(mail);

                                } catch (JSONException e){
                                    e.printStackTrace();
                                }
                            }

                            adapter.notifyDataSetChanged();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d(TAG, "Error: " + error.getMessage());
                            Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
                            hidePDialog();
                        }

                    }
            ){
                @Override
                protected Map<String, String> getParams(){
                    // Posting parameters to getInbox url
                    Map<String, String> params = new HashMap<String, String>();
                    params.put(ARG_USERID, getArguments().getString(ARG_USERID));
                    params.put(ARG_STATUS, getArguments().getString(ARG_STATUS));
                    return params;
                }

            };

            AppController.getInstance().addToRequestQueue(mailReq, "getInbox");

            return rootView;
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            hidePDialog();
        }

        private void hidePDialog() {
            if (pDialog != null) {
                pDialog.dismiss();
                pDialog = null;
            }
        }

    }
}
