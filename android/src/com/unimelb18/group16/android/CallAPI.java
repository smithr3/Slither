package com.unimelb18.group16.android;

import android.os.AsyncTask;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class CallAPI extends AsyncTask<String, String, String> {

    public CallAPI() {
        //set context variables if required
    }

    @Override
    protected void onPostExecute(String result) {
        ResponseData.ParseResponse(result);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    public String readFullyAsString(InputStream inputStream, String encoding) throws IOException {
        return readFully(inputStream).toString(encoding);
    }

    private ByteArrayOutputStream readFully(InputStream inputStream) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length = 0;
        while ((length = inputStream.read(buffer)) != -1) {
            baos.write(buffer, 0, length);
        }
        return baos;
    }

    @Override
    protected String doInBackground(String... params) {
        String urlString = params[0]; // URL to call
        String data = params[1]; //data to post
        OutputStream out = null;

        String response = "";

        try {


            HttpURLConnection urlConnection = (HttpURLConnection) new URL(urlString).openConnection();



            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            //urlConnection.setDoOutput(true);//enable to write data to output stream
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");//whatever you want
            urlConnection.setRequestProperty("Content-Length", "" + data.getBytes().length);


            urlConnection.setUseCaches(false);//set true to enable Cache for the req


            out = new BufferedOutputStream(urlConnection.getOutputStream());

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
            writer.write(data);
            writer.flush();
            writer.close();
            out.close();

            urlConnection.connect();

            response = readFullyAsString(urlConnection.getInputStream(), "UTF-8");
            urlConnection.disconnect();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return response;
    }
}