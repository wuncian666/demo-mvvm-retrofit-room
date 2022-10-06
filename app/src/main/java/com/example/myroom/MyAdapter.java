package com.example.myroom;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myroom.database.DataBase;
import com.example.myroom.retrofit.AwsInfoResponse;
import com.example.myroom.viewmodel.MainActivityViewModel;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private final Activity activity;

    private List<AwsInfoResponse> myData;

    private OnItemClickListener onItemClickListener;

    public MyAdapter(Activity activity, List<AwsInfoResponse> myData) {
        this.activity = activity;
        this.myData = myData;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void refreshView(List<AwsInfoResponse> myData) {
        this.myData = myData;
        notifyDataSetChanged();
    }

    public void deleteData(int position) {
        // DataBase.getInstance(activity).getDataDao().deleteData(myData.get(position).getId());
        notifyItemRemoved(position);
        // TODO: refresh view ...

    }

    // change view holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, null);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvTitle.setText(myData.get(position).getGatewayUuid());

        holder.view.setOnClickListener(v -> {
            if (myData.get(position) == null) return;

            onItemClickListener.onItemClick(myData.get(position));
            Toast.makeText(v.getContext(), myData.get(position).getRootCAName(), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return myData == null ? 0 : myData.size();// return data size
    }

    public interface OnItemClickListener {
        void onItemClick(AwsInfoResponse myData);
    }


    // view holder 儲存 view reference
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // get view from on create view holder
            tvTitle = itemView.findViewById(android.R.id.text1);
            view = itemView;
        }
    }
}
