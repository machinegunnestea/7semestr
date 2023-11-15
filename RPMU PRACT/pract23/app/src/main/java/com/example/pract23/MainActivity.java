package com.example.pract23;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public class ImageAdapter extends BaseAdapter {
        private Context mContext;
        public Integer[] mthumbids = {
                R.drawable.1,
                R.drawable.2,
                R.drawable.3,
                R.drawable.4,
                R.drawable.5,
                R.drawable.6};

        public ImageAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return mthumbids.length;
        }

        public Object getitem(int position) {
            return mthumbids[position];
        }

        public long getitemid(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            ImageView imageView = new ImageView(mContext);
            imageView.setimageresource(mThumblds[position]);
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            imageView.setLayoutParams(new GridView.LayoutParams(120,110));
            return imageView;
        }
    }
}

