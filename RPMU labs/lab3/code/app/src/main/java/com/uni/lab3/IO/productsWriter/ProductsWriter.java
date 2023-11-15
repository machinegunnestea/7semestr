package com.uni.lab3.IO.productsWriter;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.uni.lab3.model.Product;

import java.io.BufferedWriter;
import java.io.IOException;

public class ProductsWriter extends AsyncTask<Void, Void, Boolean> {
    private final ProgressDialog progressDialog;
    private final BufferedWriter bufferedWriter;
    private final Product[] products;
    private final ProductsWriterHandlerCallback onStopHandler;

    public ProductsWriter(Context context, BufferedWriter bufferedWriter, Product[] products, ProductsWriterHandlerCallback onStopHandler) {
        progressDialog = new ProgressDialog(context);
        this.bufferedWriter = bufferedWriter;
        this.products = products;
        this.onStopHandler = onStopHandler;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Data is saving");
        progressDialog.show();
    }

    @Override
    protected Boolean doInBackground(Void... arg0) {
        try {
            bufferedWriter.write(new Gson().toJson(products));
            bufferedWriter.flush();
            Thread.sleep(1500);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        onStopHandler.handleResultAfterSaving(result);
        progressDialog.dismiss();
    }
}
