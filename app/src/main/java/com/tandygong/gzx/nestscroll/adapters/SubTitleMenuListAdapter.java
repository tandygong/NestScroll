package com.tandygong.gzx.nestscroll.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.tandygong.gzx.nestscroll.R;

import java.util.ArrayList;

/**
 * Created by gzx on 2016/3/11 at 20:42.
 */
public class SubTitleMenuListAdapter extends BaseAdapter {
    private int selectPos;
    private ArrayList<String> dataList;
    private Context context;

    public SubTitleMenuListAdapter(Context context, ArrayList<String> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    public int getSelectPos() {
        return selectPos;
    }

    public void setSelectPos(int selectPos) {
        this.selectPos = selectPos;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return dataList == null ? 0 : dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_tips_titles, null);
            holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvTitle.setText(dataList.get(position));
        if (position == selectPos) {
            holder.tvTitle.setTextColor(Color.parseColor("#ff8570"));
            holder.tvTitle.setBackgroundColor(Color.parseColor("#f6f6f6"));
        } else {
            holder.tvTitle.setTextColor(Color.parseColor("#222222"));
            holder.tvTitle.setBackgroundColor(Color.parseColor("#ffffff"));
        }

        return convertView;
    }

    class ViewHolder {
        TextView tvTitle;
    }
}
