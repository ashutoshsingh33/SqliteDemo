package com.examapp.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.examapp.R;

import java.util.ArrayList;

public class SelectAdapter extends RecyclerView.Adapter<SelectAdapter.CountryViewHolder> {

    private Activity mActivity;
    private LayoutInflater mLayoutInflater;
    private int i = 0;
    private IResultCallBack mIResultCallBack;
    private ArrayList<String> data = new ArrayList<>();

    public SelectAdapter(Activity activity, ArrayList<String> data, IResultCallBack iResultCallBack) {
        mActivity = activity;
        mLayoutInflater = LayoutInflater.from(activity);
        mIResultCallBack = iResultCallBack;
        this.data = data;
    }

    @Override
    public CountryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_select, parent, false);
        return new CountryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CountryViewHolder holder, int position) {
        holder.textName.setText(data.get(position));
        holder.textName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIResultCallBack.getResult(holder.textName.getText().toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class CountryViewHolder extends RecyclerView.ViewHolder {

        TextView textName;

        private CountryViewHolder(View itemView) {
            super(itemView);
            textName = (TextView) itemView.findViewById(R.id.text_name);
        }
    }

    public interface IResultCallBack {
        void getResult(String selectedItem);
    }
}
