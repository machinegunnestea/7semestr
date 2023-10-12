package com.example.secondllrrv20;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CarListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Car> carList;
    private ArrayList<Car> originalCarList;

    public CarListAdapter(Context context, ArrayList<Car> carList) {
        this.context = context;
        this.carList = carList;
        this.originalCarList = new ArrayList<>(carList);
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
            convertView = inflater.inflate(R.layout.list_item_car, parent, false);
        }

        Car car = carList.get(position);

        TextView brandTextView = convertView.findViewById(R.id.brand_text_view);
        TextView modelTextView = convertView.findViewById(R.id.model_text_view);
        TextView yearTextView = convertView.findViewById(R.id.year_text_view);
        TextView engineTextView = convertView.findViewById(R.id.engine_text_view);

        brandTextView.setText(car.getBrand());
        modelTextView.setText(car.getModel());
        yearTextView.setText(String.valueOf(car.getYear()));
        engineTextView.setText(String.valueOf(car.getEngine()));

        return convertView;
    }

    public void updateList(ArrayList<Car> filteredList) {
        carList.clear();
        carList.addAll(filteredList);
        notifyDataSetChanged();
    }

    public void resetList() {
        carList.clear();
        carList.addAll(originalCarList);
        notifyDataSetChanged();
    }
}