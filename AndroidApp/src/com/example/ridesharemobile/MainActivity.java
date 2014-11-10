package com.example.ridesharemobile;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Scanner;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.support.v7.app.ActionBarActivity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
	
	static String web_service = "http://localhost:22281/api/user";
	static String url_string = "http://10.0.2.2/api/user";
	
	//static String url_string = "http://10.0.2.2:22281/api/user";
	
	static String userId = "";
	static String firstName = "";
	static String lastName = "";
	
	
	static String stockSymbol = "";
	static String stockDaysLow = "";
	static String stockDaysHigh = "";
	static String stockChange = "";
	static String full_result = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        new MyAsyncTask().execute();
        
    } 
    private class MyAsyncTask extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			
			DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
			HttpPost httppost =  new HttpPost(url_string);
			
			httppost.setHeader("Content-type", "application/json");
			InputStream inputStrem = null;
			String result = null;
			
			
			// this code i copied from a blog... but gives the error http.internal.http.HttpURLConnectionImpl:http://localhost:22281/api/user
			//JSONObject serviceResult = requestWebService(url_string);
			// *********
			try {
				
				// error fired here as well org.apache.http.conn.HttpHostConnectException: Connection to http://localhost:22281 refused
				HttpResponse response = httpclient.execute(httppost);
				HttpEntity entity = response.getEntity();
				inputStrem = entity.getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(inputStrem, "UTF-8"), 8);
				
				StringBuilder stringBuilder = new StringBuilder();
				String line = null;
				
				while((line = reader.readLine())!= null){
					stringBuilder.append(line + "\n");
				}
				result = stringBuilder.toString();
	
			}
			catch (Exception e)
			{
				e.printStackTrace();				
			}
			
			finally {
				try {
					if((inputStrem != null)) inputStrem.close();
				}
				catch (Exception e)
				{
					e.printStackTrace();
					
				}
			}
			
			JSONObject jsonObject;
			
			try {
				
				//result = result.substring(11);
				//result = result.substring(0, result.length()-2);

				// Log.v("JSONParser RESULT ", result);
				jsonObject = new JSONObject(result);
				JSONObject queryJSONObject = jsonObject.getJSONObject("first_name");
				full_result = queryJSONObject.getString("first_name");
				JSONObject resultsJSONObject = queryJSONObject.getJSONObject("results");
				// Get the JSON object named quote inside of the results object
				JSONObject quoteJSONObject = resultsJSONObject.getJSONObject("quote");
				// Get the JSON Strings in the quote object
				stockSymbol = result; //quoteJSONObject.getString("symbol");
				stockDaysLow = quoteJSONObject.getString("DaysLow");
				stockDaysHigh = quoteJSONObject.getString("DaysHigh");
				stockChange = quoteJSONObject.getString("Change");

//				result = result.substring(1);
//				result = result.substring(0, result.length() - 1 );
//				
//				Log.v("JSON result", result);
//				jsonObject = new JSONObject(result);
//				
//				//JSONObject userNameObj = jsonObject.getJSONObject("first_name");
//				firstName = jsonObject.getString("first_name");
//				
//				JSONArray array = jsonObject.names();
				
			}
			catch (Exception e)
			{
				e.printStackTrace();
				
			}
			return result;
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			//return null;
			
			TextView text1 = (TextView) findViewById(R.id.text1);
			text1.setText("first name " +  full_result);
			
			TextView text2 = (TextView) findViewById(R.id.text2);
			text2.setText("first name " + stockSymbol );
			
			
			
			 
		}

    }
    
    // ***********
    public static JSONObject requestWebService(String serviceUrl) {
        //disableConnectionReuseIfNecessary();
     
        HttpURLConnection urlConnection = null;
        try {
            // create connection
            URL urlToRequest = new URL(serviceUrl);
            urlConnection = (HttpURLConnection) 
                urlToRequest.openConnection();
           // urlConnection.setConnectTimeout(CONNECTION_TIMEOUT);
            //urlConnection.setReadTimeout(DATARETRIEVAL_TIMEOUT);
             
            // handle issues
            int statusCode = urlConnection.getResponseCode();
            if (statusCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                // handle unauthorized (if service requires user login)
            } else if (statusCode != HttpURLConnection.HTTP_OK) {
                // handle any other errors, like 404, 500,..
            }
             
            // create JSON object from content
            InputStream in = new BufferedInputStream(
                urlConnection.getInputStream());
            return new JSONObject(getResponseText(in));
             
        } catch (MalformedURLException e) {
            // URL is invalid
        } catch (SocketTimeoutException e) {
            // data retrieval or connection timed out
        } catch (IOException e) {
            // could not read response body 
            // (could not create input stream)
        } catch (JSONException e) {
            // response body is no valid JSON string
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }       
         
        return null;
    }
     
    /**
     * required in order to prevent issues in earlier Android version.
     */
    private static void disableConnectionReuseIfNecessary() {
        // see HttpURLConnection API doc
        if (Integer.parseInt(Build.VERSION.SDK) 
                < Build.VERSION_CODES.FROYO) {
            System.setProperty("http.keepAlive", "false");
        }
    }
     
    private static String getResponseText(InputStream inStream) {
        // very nice trick from 
        // http://weblogs.java.net/blog/pat/archive/2004/10/stupid_scanner_1.html
        return new Scanner(inStream).useDelimiter("\\A").next();
    }
    // ************
    
    
 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
