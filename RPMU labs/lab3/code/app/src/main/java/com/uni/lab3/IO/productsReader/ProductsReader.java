package com.uni.lab3.IO.productsReader;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.google.gson.Gson;
import com.uni.lab3.R;
import com.uni.lab3.model.Product;

import java.io.BufferedReader;
import java.io.IOException;

public class ProductsReader extends AsyncTask<Void, Void, Product[]> {
    private final AlertDialog progressDialog;
    private final BufferedReader bufferedReader;
    private final ProductsReaderHandlerCallback onStopHandler;

    public ProductsReader(Context context, BufferedReader bufferedReader, ProductsReaderHandlerCallback onStopHandler) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.progress_dialog, null);
        builder.setView(view);
        progressDialog = builder.create();
        this.bufferedReader = bufferedReader;
        this.onStopHandler = onStopHandler;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.show();
    }

    @Override
    protected Product[] doInBackground(Void... arg0) {
        StringBuilder stringBuilder = new StringBuilder();

        try {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            Thread.sleep(1500);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        String text = stringBuilder.toString();
        return new Gson().fromJson(text, Product[].class);
    }

    @Override
    protected void onPostExecute(Product[] result) {
        super.onPostExecute(result);
        onStopHandler.handleProductsAfterLoading(result);
        progressDialog.dismiss();
    }
}
