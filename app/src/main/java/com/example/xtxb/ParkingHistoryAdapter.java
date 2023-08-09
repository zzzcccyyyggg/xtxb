package com.example.xtxb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ParkingHistoryAdapter extends ArrayAdapter<ParkingHistory> {
    private int resourceId;

    public ParkingHistoryAdapter(@NonNull Context context, int resource, @NonNull List<ParkingHistory> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ParkingHistory history = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);

        TextView parkingNameTextView = view.findViewById(R.id.parkingName);
        TextView parkingAddressTextView = view.findViewById(R.id.parkingAddress);
        TextView parkingDurationTextView = view.findViewById(R.id.parkingDuration);

        parkingNameTextView.setText(history.getParkingName());
        parkingAddressTextView.setText(history.getParkingAddress());
        parkingDurationTextView.setText(history.getParkingDuration());

        return view;
    }
}
