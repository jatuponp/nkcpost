package com.nkc.nkcpost;
import com.nkc.nkcpost.R;
import com.nkc.nkcpost.AppController;
import com.nkc.nkcpost.model.Mail;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
/**
 * Created by Jumpon-pc on 4/10/2558.
 */
public class CustomListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Mail> mailItems;
    //ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public CustomListAdapter(Activity activity, List<Mail> mailItems) {
        this.activity = activity;
        this.mailItems = mailItems;
    }

    @Override
    public int getCount() {
        return mailItems.size();
    }

    @Override
    public Object getItem(int location) {
        return mailItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row, null);

        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView number = (TextView) convertView.findViewById(R.id.number);
        TextView emsid = (TextView) convertView.findViewById(R.id.emsid);
        TextView datetime = (TextView) convertView.findViewById(R.id.datetime);

        // getting movie data for the row
        Mail m = mailItems.get(position);

        // Number
        number.setText(m.getNumber());

        // title
        title.setText(m.getTitle());

        // number
        emsid.setText("ID: " + m.getEmsid());

        // release year
        datetime.setText("Date: " + m.getDatetime());

        return convertView;
    }
}
