package com.example.xtxb;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ParkingRequestAdapter extends RecyclerView.Adapter<ParkingRequestAdapter.RequestViewHolder> {

    private List<ParkingRequest> requestList;
    private OnItemClickListener itemClickListener;

    public ParkingRequestAdapter(List<ParkingRequest> requestList, OnItemClickListener itemClickListener) {
        this.requestList = requestList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.request_list_item, parent, false);
        return new RequestViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestViewHolder holder, int position) {
        ParkingRequest request = requestList.get(position);
        holder.bind(request);
    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }

    public class RequestViewHolder extends RecyclerView.ViewHolder {

        private TextView applicantTextView;
        private TextView licensePlateTextView;
        private TextView requestTimeTextView;

        public RequestViewHolder(View itemView) {
            super(itemView);
            applicantTextView = itemView.findViewById(R.id.applicantTextView);
            licensePlateTextView = itemView.findViewById(R.id.licensePlateTextView);
            requestTimeTextView = itemView.findViewById(R.id.requestTimeTextView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && itemClickListener != null) {
                        itemClickListener.onItemClick(requestList.get(position));
                    }
                }
            });
        }

        public void bind(ParkingRequest request) {
            applicantTextView.setText("申请人：" + request.getApplicantName());
            licensePlateTextView.setText("车牌号：" + request.getLicensePlate());
            requestTimeTextView.setText("申请时间：" + request.getRequestTime());
        }
    }

    // 点击事件的接口
    public interface OnItemClickListener {
        void onItemClick(ParkingRequest request);
    }
}
