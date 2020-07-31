package com.lzi.example_volley_library.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lzi.example_volley_library.R;
import com.lzi.example_volley_library.entities.User;

import java.util.List;

public class UserAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater layoutInflater;
    private List<User> userList;

    public UserAdapter(Activity activity, List<User> userList) {
        this.activity = activity;
        this.userList = userList;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public User getItem(int position) {
        return userList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (layoutInflater == null)
            layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = layoutInflater.inflate(R.layout.activity_user_items,null);

        TextView tv_name = convertView.findViewById(R.id.tv_name);
        TextView tv_email = convertView.findViewById(R.id.tv_email);
        TextView tv_phone = convertView.findViewById(R.id.tv_phone);

        // getting model data for the row
        User user = userList.get(position);

        tv_name.setText(user.getName());
        tv_email.setText(user.getEmail());
        tv_phone.setText(user.getPhone());

        return convertView;
    }
}
