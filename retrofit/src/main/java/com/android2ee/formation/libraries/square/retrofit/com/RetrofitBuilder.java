/**
 * <ul>
 * <li>RetrofitCaller</li>
 * <li>com.android2ee.formation.libraries.square.retrofit</li>
 * <li>23/06/2015</li>
 * <p/>
 * <li>======================================================</li>
 * <p/>
 * <li>Projet : Mathias Seguy Project</li>
 * <li>Produit par MSE.</li>
 * <p/>
 * /**
 * <ul>
 * Android Tutorial, An <strong>Android2EE</strong>'s project.</br>
 * Produced by <strong>Dr. Mathias SEGUY</strong>.</br>
 * Delivered by <strong>http://android2ee.com/</strong></br>
 * Belongs to <strong>Mathias Seguy</strong></br>
 * ***************************************************************************************************************</br>
 * This code is free for any usage but can't be distribute.</br>
 * The distribution is reserved to the site <strong>http://android2ee.com</strong>.</br>
 * The intelectual property belongs to <strong>Mathias Seguy</strong>.</br>
 * <em>http://mathias-seguy.developpez.com/</em></br> </br>
 * <p/>
 * *****************************************************************************************************************</br>
 * Ce code est libre de toute utilisation mais n'est pas distribuable.</br>
 * Sa distribution est reservée au site <strong>http://android2ee.com</strong>.</br>
 * Sa propriété intellectuelle appartient à <strong>Mathias Seguy</strong>.</br>
 * <em>http://mathias-seguy.developpez.com/</em></br> </br>
 * *****************************************************************************************************************</br>
 */

package com.android2ee.formation.libraries.square.retrofit.com;


import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Base64;

import com.android2ee.formation.libraries.square.retrofit.com.calladapter.ErrorHandlingCallAdapterFactory;
import com.android2ee.formation.libraries.square.retrofit.com.converter.MyPhotoConverterFactory;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.MoshiConverterFactory;
import retrofit2.Retrofit;

/**
 * Created by Mathias Seguy - Android2EE on 23/06/2015.
 * This class Show you how to create your webServices : simple classes that can make REST request over http
 */
public class RetrofitBuilder {

    public static final String BASE_URL = "http://jsonplaceholder.typicode.com/";

    /***********************************************************
     * Simple Retrofit WebServer
     **********************************************************/

    public static WebServerIntf getSimpleClient() {
        //Using Default HttpClient
        Retrofit ra = new Retrofit.Builder()
                //you need to add your root url
                .baseUrl(BASE_URL)
                        //You need to add a converter if you want your Json to be automagicly convert into the object
                .addConverterFactory(MoshiConverterFactory.create())
                .build();
        WebServerIntf webServer = ra.create(WebServerIntf.class);
        return webServer;
    }

    /***********************************************************
     * complex/Complete Retrofit WebServer
     **********************************************************/
    public static WebServerIntf getComplexClient(Context ctx) {
        //get the OkHttp client
        OkHttpClient client = getOkHttpClient(ctx);

        //now it's using the cach
        //Using my HttpClient
        Retrofit raCustom = new Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)
                        //add your own converter first (declaration order matters)
                        //the responsability chain design pattern is behind
                .addConverterFactory(new MyPhotoConverterFactory())
                        //You need to add a converter if you want your Json to be automagicly convert into the object
                .addConverterFactory(MoshiConverterFactory.create())
                        //then add your own CallAdapter
                .addCallAdapterFactory(new ErrorHandlingCallAdapterFactory())
                .build();
        WebServerIntf webServer = raCustom.create(WebServerIntf.class);
        return webServer;
    }

    @NonNull
    public static OkHttpClient getOkHttpClient(Context ctx) {
        //define the OkHttp Client with its cach!
        //Assigning a CacheDirectory
        File myCacheDir = new File(ctx.getCacheDir(), "OkHttpCache");
        //you should create it...
        int cacheSize = 1024 * 1024;
        Cache cacheDir = new Cache(myCacheDir, cacheSize);
        Interceptor customLoggingInterceptor = new CustomLoggingInterceptor();
        HttpLoggingInterceptor httpLogInterceptor = new HttpLoggingInterceptor();
        httpLogInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        return new OkHttpClient.Builder()
                //add a cach
                .cache(cacheDir)
                        //add interceptor (here to log the request)
                .addInterceptor(customLoggingInterceptor)
                .addInterceptor(httpLogInterceptor)
                .build();


    }
    /***********************************************************
     * Retrofit WebServer with Basic Authentication
     **********************************************************/
    /**
     * Basic credentials send to the server at each request
     */
    private static String basicCredential = null;

    /**
     * Retrieve an authenticated Retrofit webserver
     *
     * @param ctx
     * @param name
     * @param password
     * @return
     */
    public static WebServerIntf getAuthenticatedClient(Context ctx, String name, String password) {
        String credentials = name + ":" + password;
        basicCredential =
                "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
        //get the OkHttp client
        OkHttpClient client = getOkAuthenticatedHttpClient(ctx);

        //now it's using the cach
        //Using my HttpClient
        Retrofit raCustom = new Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)
                        //add your own converter first (declaration order matters)
                        //the responsability chain design pattern is behind
                .addConverterFactory(new MyPhotoConverterFactory())
                        //You need to add a converter if you want your Json to be automagicly convert into the object
                .addConverterFactory(MoshiConverterFactory.create())
                        //then add your own CallAdapter
                .addCallAdapterFactory(new ErrorHandlingCallAdapterFactory())
                .build();
        WebServerIntf webServer = raCustom.create(WebServerIntf.class);
        return webServer;
    }

    @NonNull
    public static OkHttpClient getOkAuthenticatedHttpClient(Context ctx) {
        //define the OkHttp Client with its cach!
        //Assigning a CacheDirectory
        File myCacheDir = new File(ctx.getCacheDir(), "OkHttpCache");
        //you should create it...
        int cacheSize = 1024 * 1024;
        Cache cacheDir = new Cache(myCacheDir, cacheSize);
        Interceptor customLoggingInterceptor = new CustomLoggingInterceptor();
        HttpLoggingInterceptor httpLogInterceptor = new HttpLoggingInterceptor();
        httpLogInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        return new OkHttpClient.Builder()
                //add a cach
                .cache(cacheDir)
                        //add interceptor (here to log the request)
                .addInterceptor(customLoggingInterceptor)
                .addInterceptor(httpLogInterceptor)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();

                        Request.Builder requestBuilder = original.newBuilder()
                                .header("Authorization", basicCredential)
                                .header("Accept", "applicaton/json")
                                .method(original.method(), original.body());

                        Request request = requestBuilder.build();
                        return chain.proceed(request);
                    }
                })
                .build();


    }


}
