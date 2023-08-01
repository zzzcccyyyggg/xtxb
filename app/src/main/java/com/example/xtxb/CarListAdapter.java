package com.example.xtxb;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CarListAdapter extends RecyclerView.Adapter<CarListAdapter.CarViewHolder> {

    private List<Car> carList;
    private OnItemClickListener itemClickListener;

    public CarListAdapter(List<Car> carList, OnItemClickListener itemClickListener) {
        this.carList = carList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.car_list_item, parent, false);
        return new CarViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        Car car = carList.get(position);
        holder.bind(car);
    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    public class CarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView licensePlateTextView;
        TextView ownerTextView;
        TextView parkingTimeTextView;
        TextView state;

        public CarViewHolder(View itemView) {
            super(itemView);
            licensePlateTextView = itemView.findViewById(R.id.licensePlateTextView);
            ownerTextView = itemView.findViewById(R.id.ownerTextView);
            parkingTimeTextView = itemView.findViewById(R.id.parkingTimeTextView);
            state = itemView.findViewById(R.id.state);

            itemView.setOnClickListener(this);
        }

        public void bind(Car car) {
            licensePlateTextView.setText(car.getLicensePlate());
            ownerTextView.setText(car.getOwner());
            parkingTimeTextView.setText(car.getParkingTime());
            state.setText(car.getState());
        }

        @Override
        public void onClick(View v) {
            if (itemClickListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Car car = carList.get(position);
                    itemClickListener.onItemClick(car);
                }
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Car car);
    }
}
