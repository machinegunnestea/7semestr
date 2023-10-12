package com.example.secondllrrv20;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CarGridAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Car> carList;

    public CarGridAdapter(Context context, ArrayList<Car> carList) {
        this.context = context;
        this.carList = carList;
    }

    @Override
    public int getCount() {
        return carList.size();
    }

    @Override
    public Object getItem(int position) {
        return carList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.grid_item_car, parent, false);
        }

        Car car = carList.get(position);

        TextView modelTextView = convertView.findViewById(R.id.model_text_view);
        TextView yearTextView = convertView.findViewById(R.id.year_text_view);
        TextView engineTextView = convertView.findViewById(R.id.engine_text_view);

        modelTextView.setText(car.getModel());
        yearTextView.setText(String.valueOf(car.getYear()));
        engineTextView.setText(String.valueOf(car.getEngine()));

        return convertView;
    }
}