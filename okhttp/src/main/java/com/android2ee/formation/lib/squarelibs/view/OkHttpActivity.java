package com.android2ee.formation.lib.squarelibs.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.android2ee.formation.lib.squarelibs.R;
import com.android2ee.formation.lib.squarelibs.tool.MyJsonObject;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.GzipSink;
import okio.Okio;

/**
 * Doc:
 * https://github.com/square/okhttp
 * https://github.com/square/okhttp/wiki/Connections
 * http://www.101apps.co.za/index.php/articles/android-cloud-connections-using-the-okhttp-library.html
 * https://github.com/square/okhttp/wiki/Recipes
 */
public class OkHttpActivity extends ActionBarActivity {
	TextView txvResult;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ok_http);
		txvResult = (TextView) findViewById(R.id.txvResult);
		txvResult.setMovementMethod(new ScrollingMovementMethod());
		getSupportActionBar().setSubtitle("OkHttp");
		new MyAsyncOkHttp().execute();
	}

	private class MyAsyncOkHttp extends AsyncTask<String, String, String> {

		/***************************************************************/
		/************                 The Client                 *******/
		/***************************************************************/
		OkHttpClient client = null;

		private OkHttpClient getClient() {
			if (client == null) {
				//Assigning a CacheDirectory
				File myCacheDir = new File(getCacheDir(), "OkHttpCache");
				//you should create it...
				int cacheSize = 1024 * 1024;
				Cache cacheDir = new Cache(myCacheDir, cacheSize);
				client = new OkHttpClient.Builder()
						.cache(cacheDir)
						.addInterceptor(getInterceptor())
						.addInterceptor(new GzipRequestInterceptor())
						.build();
			}
			//now it's using the cach
			return client;
		}

		/***************************************************************/
		/************                        GET                 *******/
		/***************************************************************/
		String urlGet = "http://jsonplaceholder.typicode.com/posts/1";

		private String getAStuff() throws IOException {
			Request request = new Request.Builder()
					.url(urlGet)
					.get()
					.build();
			//the synchronous way (Here it's ok we are in an AsyncTask already)
			Response response = getClient().newCall(request).execute();
			//And the Asyn way for the fun
			getClient().newCall(request).enqueue(new Callback() {
				@Override
				public void onFailure(Call call, IOException e) {
					//fuck, we failed, log the call and the exception
				}

				@Override
				public void onResponse(Call call, Response response) throws IOException {
					//yep, we succeeded do your stuff
				}
			});
			int httpStatusCode=response.code();
			String ret = response.body().string();
			//You can also have:
			//Reader reader=response.body().charStream();
			//InputStream stream=response.body().byteStream();
			//byte[] bytes=response.body().bytes();
			//But the best way, now you understand the OkIo
			//because no allocation, no more buffering
			//Source src=response.body().source();
			//you should always close the body to enhance recycling mechanism
			response.body().close();
			return ret;
		}
		/***************************************************************/
		/************                JsonAdapter            *******/
		/**************************************************************/


		/**
		 * Get Json object using Moshi adapter
		 *
		 * @return
		 * @throws IOException
		 */
		public String getJson() throws IOException {
			MyJsonObject jsonObj = new MyJsonObject();
			jsonObj.id = 42;
			jsonObj.userId = 48;
			jsonObj.title = "My Title";
			jsonObj.body = "Move your body, move it, shake it";

			Moshi moshi = new Moshi.Builder().build();
			JsonAdapter<MyJsonObject> adapter = moshi.adapter(MyJsonObject.class);
			return adapter.toJson(jsonObj);
			//And then use fromJson/toJson method and be happy
			// MyObject myObj = adapter.fromJson(Okio.Source);
		}
		/***************************************************************/
		/************                        POST                 *******/
		/***************************************************************/
		String urlPost = "http://jsonplaceholder.typicode.com/posts";
		//        String json="{\n" +
//                "    \"title\": 'foo',\n" +
//                "    \"body\": 'bar',\n" +
//                "    \"userId\": 1\n" +
//                "  }";
		private final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

		private String postAStuff() throws IOException {
			OkHttpClient client = new OkHttpClient();

//            RequestBody body = RequestBody.create(JSON, file);
//            RequestBody body = RequestBody.create(JSON, string);
//            RequestBody body = RequestBody.create(JSON, byte[]);
			RequestBody body = RequestBody.create(JSON, getJson());
			Request request = new Request.Builder()
					.url(urlPost)
					.post(body)
					.build();
			Call postCall = getClient().newCall(request);
			Response response = postCall.execute();
			//or making it in the MainThread you use
//            postCall.enqueue(new Callback() {
//                                 @Override
//                                 public void onFailure(Request request, IOException e) {
//                                     //oh, shit, i failed
//                                 }
//
//                                 @Override
//                                 public void onResponse(Response response) throws IOException {
//                                    //yes, I succeed
//                                 }
//                             }
//
//            );
			//you have your response code
			int httpStatusCode = response.code();
			//your responce body
			String ret = response.body().string();
			//and a lot of others stuff...
			//you should always close the body to enhance recycling mechanism
			response.body().close();
			return ret;
		}

/***************************************************************/
		/************                 GET    PICTURE             *******/
		/***************************************************************/
		String urlGetPicture = "http://jsonplaceholder.typicode.com/photos/1";

		public Bitmap urlGetPicture() throws IOException {
			Request request = new Request.Builder()
					.url(urlGetPicture)
					.get()
					.build();
			Call postCall = getClient().newCall(request);
			Response response = postCall.execute();
			if (response.code() == 200) {
				ResponseBody in = response.body();
				InputStream is = in.byteStream();
				Bitmap bitmap = BitmapFactory.decodeStream(is);
				is.close();
				response.body().close();
				//now do a stuff, for exemple store it
				return bitmap;
			}
			return null;
		}


		/**
		 * How to save a Bitmap on the disk
		 * @param fileName
		 * @param bitmap
		 * @param ctx
		 * @throws IOException
		 */
		private void savePicture(String fileName,Bitmap bitmap, Context ctx) throws IOException {
			//Second save the picture
			//--------------------------
			//Find the external storage directory
			File filesDir = ctx.getCacheDir();
			//Retrieve the name of the subfolder where your store your picture
			//(You have set it in your string ressources)
			String pictureFolderName = "Pictures";
			//then create the subfolder
			File pictureDir = new File(filesDir, pictureFolderName);
			//Check if this subfolder exists
			if (!pictureDir.exists()) {
				//if it doesn't create it
				pictureDir.mkdirs();
			}
			//Define the file to store your picture in
			File filePicture = new File(pictureDir, fileName);
			//Open an OutputStream on that file
			FileOutputStream fos = new FileOutputStream(filePicture);
			//Write in that stream your bitmap in png with the max quality (100 is max, 0 is min quality)
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
			//The close properly your stream
			fos.flush();
			fos.close();

		}
		/***************************************************************/
		/************                        Interceptors                 *******/
		/***************************************************************/
		public Interceptor getInterceptor() {
			return new LoggingInterceptor();
		}

		class LoggingInterceptor implements Interceptor {
			//Code pasted from okHttp webSite itself
			@Override
			public Response intercept(Chain chain) throws IOException {
				Request request = chain.request();
				long t1 = System.nanoTime();
				Log.e("Interceptor Sample", String.format("Sending request %s on %s%n%s",
						request.url(), chain.connection(), request.headers()));

				Response response = chain.proceed(request);

				long t2 = System.nanoTime();
				Log.e("Interceptor Sample", String.format("Received response for %s in %.1fms%n%s",
						response.request().url(), (t2 - t1) / 1e6d, response.headers()));

				return response;
			}
		}

		/**
		 * This interceptor compresses the HTTP request body. Many webservers can't handle this!
		 */
		final class GzipRequestInterceptor implements Interceptor {
			@Override
			public Response intercept(Chain chain) throws IOException {
				Request originalRequest = chain.request();
				if (originalRequest.body() == null || originalRequest.header("Content-Encoding") != null) {
					return chain.proceed(originalRequest);
				}

				Request compressedRequest = originalRequest.newBuilder()
						.header("Content-Encoding", "gzip")
						.method(originalRequest.method(), gzip(originalRequest.body()))
						.build();
				return chain.proceed(compressedRequest);
			}

			private RequestBody gzip(final RequestBody body) {
				return new RequestBody() {
					@Override
					public MediaType contentType() {
						return body.contentType();
					}

					@Override
					public long contentLength() {
						return -1; // We don't know the compressed length in advance!
					}

					@Override
					public void writeTo(BufferedSink sink) throws IOException {
						BufferedSink gzipSink = Okio.buffer(new GzipSink(sink));
						body.writeTo(gzipSink);
						gzipSink.close();
					}
				};
			}
		}


		@Override
		protected String doInBackground(String... params) {
			try {
				//so do the post
				StringBuilder strB = new StringBuilder();
				//get a stuff
				Log.e("OkHttpActivity", "DIB getAstuff");
				strB.append("\r\n Getting a Stuff\r\n\r\n");
				strB.append(getAStuff());
				publishProgress(strB.toString());
				//post a stuff
				strB.append("\r\n Posting a Stuff\r\n\r\n");
				Log.e("OkHttpActivity", "DIB postAStuff");
				strB.append(postAStuff());
				publishProgress(strB.toString());
				//add interceptor
				strB.append("\r\n Adding interceptors a Stuff\r\n\r\n");
				Log.e("OkHttpActivity", "DIB addInterceptor");
				//interceptors can only be added at the construction of the client
				//addInterceptor(); can not be called any more
				//get a stuff again
				strB.append("\r\n Getting a Stuff (again, see logs to understand)\r\n\r\n");
				Log.e("OkHttpActivity", "DIB getAStuff");
				strB.append(getAStuff());
				publishProgress(strB.toString());
				return strB.toString();
			} catch (IOException e) {
				Log.e("OkHttpActivity", "An error occurs ", e);
			}
			return null;
		}

		@Override
		protected void onPostExecute(String s) {
			super.onPostExecute(s);
		}

		@Override
		protected void onProgressUpdate(String... values) {
			txvResult.setText(values[0]);
			super.onProgressUpdate(values);
		}
	}

	/***********************************************************
	 * Managing Menu
	 **********************************************************/

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_moshi, menu);
		menu.findItem(R.id.show_okhttp).setVisible(false);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent startActivity = null;
		switch (item.getItemId()) {
			case R.id.show_moshi:
				startActivity = new Intent(this, MoshiActivity.class);
				break;
			case R.id.show_okhttp:
				break;
			case R.id.show_okio:
				startActivity = new Intent(this, OkioActivity.class);
				break;
		}
		if (startActivity != null) {
			startActivity(startActivity);
			finish();
		}
		return super.onOptionsItemSelected(item);
	}
}
