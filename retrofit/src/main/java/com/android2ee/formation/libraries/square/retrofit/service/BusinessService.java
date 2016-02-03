/**
 * <ul>
 * <li>BusinessService</li>
 * <li>com.android2ee.formation.lib.squarelibs.service</li>
 * <li>21/01/2016</li>
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

package com.android2ee.formation.libraries.square.retrofit.service;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;

import com.android2ee.formation.libraries.square.retrofit.MainActivity;
import com.android2ee.formation.libraries.square.retrofit.com.RetrofitBuilder;
import com.android2ee.formation.libraries.square.retrofit.com.WebServerIntf;
import com.android2ee.formation.libraries.square.retrofit.com.calladapter.ErrorHandlingCall;
import com.android2ee.formation.libraries.square.retrofit.com.calladapter.ErrorHandlingCallBack;
import com.android2ee.formation.libraries.square.retrofit.model.Photo;
import com.android2ee.formation.libraries.square.retrofit.model.Post;
import com.android2ee.formation.libraries.square.retrofit.model.User;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;
import okhttp3.ResponseBody;
import okio.BufferedSource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Mathias Seguy - Android2EE on 21/01/2016.
 * This class aims to manage the HttpCalls
 * EventBus should be better for communication Service->Activity
 * But it's a tutorial on Retrofit...
 */
public class BusinessService {
    /**
     * The main Activity
     */
    MainActivity activity;

    /**
     * Retrofit Interface to make
     */
    WebServerIntf webService, webServiceComplex, webServiceAuthenticated;
    /***********************************************************
     * Retrofit usage
     **********************************************************/
    //Retrofit GET calls
    Call<Post> getPostOneCall = null;
    Call<List<Post>> getPostListCall = null;
    Call<Post> getPostByIdCall = null;
    Call<List<User>> getUsersListCall;
    Call<User> getUserByIdCall;
    Call<Photo> getPhotoByIdCall;

    /***********************************************************
     * Managing life cycle
     **********************************************************/
    /**
     * To be call when the activity go in its onStart method
     */
    public void initialize(MainActivity activity) {
        this.activity = activity;
        //Create your service
        webService = RetrofitBuilder.getSimpleClient();
        webServiceComplex = RetrofitBuilder.getComplexClient(activity);

        //the runnable to be post in the UI thread to update picture

    }

    /**
     * To be call when the activity go in its onStop method
     */
    public void release() {
        activity = null;
        //you have to cancel your calls in OnStop
        getPostOneCall.cancel();
        getPostByIdCall.cancel();
        getPostListCall.cancel();
        getUsersListCall.cancel();
        getUserByIdCall.cancel();
        getPhotoWithQueryCall.cancel();
        updateUserWithFormCall.cancel();
        getSampleCall.cancel();
        postSampleCall.cancel();
        putSampleCall.cancel();
        deleteSampleCall.cancel();
        authentificateUserCall.cancel();
        getPostOneWithErrorCall.cancel();
    }
    /***********************************************************
     *  LoadingData : GET call
     **********************************************************/
    /**
     * Samples to make Get call
     */
    public void loadData() {
        //Make the Rest Call
        //OkIo, OkHttp and Moshi are behind
        //Make a synchronous call : but you can do it here because security exception will raise
        getPostOneCall = webService.getPostOne();

        //!\you can clone your Call before making the request
        //so you need to make an async call
        Log.e("BusinessService", "First call");
        
        getPostOneCall.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Response<Post> response) {
                Log.e("BusinessService", "First call is back");
                if (activity != null) activity.updateTxvGetData(response.body().toString());
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("BusinessService", "First call failed");
                if (activity != null)
                    activity.updateTxvGetData("Exception occurs with .getPostOne()");
                t.printStackTrace();
            }
        });
        //Making an asynchronous call
        getPostListCall = webServiceComplex.getPostsList();
        Log.e("BusinessService", "Second call");

        getPostListCall.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Response<List<Post>> response) {
                Log.e("BusinessService", "Second call is back with " + response.body().size() + " posts");
//                for (Post post : response.body()) {
//   Log.e("BusinessService", "Response obtained " + post + " run in UIThread " + Thread.currentThread());
//                }

            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("BusinessService", "Second call failed with getPostsList()");
                t.printStackTrace();

            }
        });
        //Make the call in an async way
        getPostByIdCall = webServiceComplex.getPostById(1);
        Log.e("BusinessService", "Third call");
        getPostByIdCall.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Response<Post> response) {
                Log.e("BusinessService", "Third call is back");
                if (activity != null) activity.updateTxvGetData(response.body().toString());
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("BusinessService", "Third call failed");
                if (activity != null)
                    activity.updateTxvGetData("Exception occurs with getPostOne(1)");
                t.printStackTrace();
            }
        });
        getUsersListCall = webServiceComplex.getUserList();
        Log.e("BusinessService", "fourth call");
        if (activity != null) activity.updateTxvGetUsers("Waiting for response");
        getUsersListCall.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Response<List<User>> response) {
                Log.e("BusinessService", "fourth call is back");
                if (activity != null)
                    activity.updateTxvGetUsers("Reposnbe obtained :\r\n run in UIThread " + Thread.currentThread() + "\r\n");

                Log.e("BusinessService", "fourth call is back with " + response.body().size() + " users");
//                for (User user : response.body()) {
//   Log.e("BusinessService", "Response obtained " + user);
//                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("BusinessService", "fourth call failed");
                if (activity != null)
                    activity.updateTxvGetUsers("Exception occurs with getUserList()");
                t.printStackTrace();
            }
        });
        //load only one user
        getUserByIdCall = webServiceComplex.getUserById(2);
        Log.e("BusinessService", "fifth call");
        getUserByIdCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Response<User> response) {
                Log.e("BusinessService", "fifth call is back");
                if (activity != null) {
                    activity.updateTxvGetUsers("Responce obtained :\r\n run in UIThread " + Thread.currentThread() + "\r\n");
                    activity.updateTxvGetUsers(response.body().toString());
                }
                Log.e("BusinessService", "Analyzing a response Object");
                Log.e("BusinessService", "Analyzing a response.code()=" + response.code());
                Log.e("BusinessService", "Analyzing a response.message()=" + response.message());
                Log.e("BusinessService", "Analyzing a response.isSuccess()=" + response.isSuccess());
                Log.e("BusinessService", "Analyzing a response.headers()=" + response.headers());
                Log.e("BusinessService", "Analyzing a response.errorBody()=" + response.errorBody());
                Log.e("BusinessService", "Analyzing a response.raw()=" + response.raw());
                //This will log this information
                //Analyzing a response Object
                //Analyzing a response.code()=200
                //Analyzing a response.message()=OK
                //Analyzing a response.isSuccess()=true
                //Analyzing a response.headers()=Connection: keep-alive
                //                Content-Type: application/json; charset=utf-8
                //                Server: Cowboy
                //                X-Powered-By: Express
                //                Vary: Origin
                //                Access-Control-Allow-Credentials: true
                //                Cache-Control: no-cache
                //                Pragma: no-cache
                //                Expires: -1
                //                X-Content-Type-Options: nosniff
                //                Etag: W/"1fd-jhjIa9s91vj8ZufojwdSzA"
                //                Date: Thu, 21 Jan 2016 15:11:30 GMT
                //                Via: 1.1 vegur
                //                OkHttp-Sent-Millis: 1453389092618
                //                OkHttp-Received-Millis: 1453389096044
                //Analyzing a response.errorBody()=null
                //Analyzing a response.raw()=Response{protocol=http/1.1, code=200, message=OK, url=http://jsonplaceholder.typicode.com/users/2}
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("BusinessService", "fifth call failed");
                if (activity != null)
                    activity.updateTxvGetUsers("Exception occurs with getUserById(2)");
                t.printStackTrace();
            }
        });

    }


    /***********************************************************
     * GET/POST/PUT/DELETE sample
     **********************************************************/

    Call<Post> getSampleCall = null;
    Call<Post> postSampleCall= null;
    Call<User> putSampleCall = null;
    Call<User> deleteSampleCall = null;
    public void getPostPutDeleteSample(){
        //get sample
        getSampleCall=webServiceComplex.getPostOne();
        getSampleCall.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Response<Post> response) {
                Log.e("BusinessService", "getSampleCall response.headers()=" + response.headers());
            }
            @Override
            public void onFailure(Throwable t) {
                Log.e("BusinessService", "getSampleCall failed" ,t);
            }
        });
        //post sample
        postSampleCall=webServiceComplex.addNewPost(new Post());
        postSampleCall.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Response<Post> response) {
                Log.e("BusinessService", "postSampleCall response.headers()=" + response.headers());
            }
            @Override
            public void onFailure(Throwable t) {
                Log.e("BusinessService", "postSampleCall failed" ,t);
            }
        });
        //put sample
        putSampleCall=webServiceComplex.updateUserOne(new User());
        putSampleCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Response<User> response) {
                Log.e("BusinessService", "putSampleCall response.headers()=" + response.headers());
            }
            @Override
            public void onFailure(Throwable t) {
                Log.e("BusinessService", "postSampleCall failed" ,t);
            }
        });
        //delete sample
        deleteSampleCall=webServiceComplex.deleteUserById(1);
        deleteSampleCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Response<User> response) {
                Log.e("BusinessService", "deleteSampleCall response.headers()=" + response.headers());
            }
            @Override
            public void onFailure(Throwable t) {
                Log.e("BusinessService", "postSampleCall failed" ,t);
            }
        });
    }

    /***********************************************************
     *  Using @Query and Querymap @Field and @FormUrlEncoded
     **********************************************************/

    Call<Photo> getPhotoWithQueryCall;
    Call<User> updateUserWithFormCall;
    /**
     * This method will add queries but those queries are ignored on server side
     * <p/>
     * It's used just to see the request sent
     * The result is :http://jsonplaceholder.typicode.com/photos?data=3&parameter2=value2&parameter1=value1&parameter3=value3
     */
    public void loadDataWithQuery() {
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("parameter1", "value1");
        options.put("parameter2", "value2");
        options.put("parameter3", "value3");
        getPhotoWithQueryCall = webServiceComplex.getPhotoWithQuery(3, options);
        getPhotoWithQueryCall.enqueue(new Callback<Photo>() {
            @Override
            public void onResponse(Response<Photo> response) {
                Log.e("BusinessService", "loadDataWithQuery succeed");
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("BusinessService", "loadDataWithQuery failed", t);
            }
        });
    }

    /**
     * Show how to use the annotation FormUrlEncoded et Field
     */
    public void loadDataUrlEncoded() {
        updateUserWithFormCall = webServiceComplex.updateUserWithForm(3, "NameValue", 499);
        updateUserWithFormCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Response<User> response) {
                Log.e("BusinessService", "loadDataUrlEncoded succeed");
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("BusinessService", "loadDataUrlEncoded failed", t);
            }
        });
    }
    /***********************************************************
     *  Basic Authentification
     **********************************************************/
    /**
     * The call for authentification
     */
    Call<User>authentificateUserCall;
    /**
     * The boolean to know if the user is authentificated
     */
    User currentUser;

    /**
     * Should be called first when using authentificated request
     * @param ctx
     * @param name
     * @param password
     */
    public void initializeAuthentificatedCommunication(Context ctx,String name, String password){
        webServiceAuthenticated=RetrofitBuilder.getAuthenticatedClient(ctx,name,password);
    }
    /**
     */
    public void login(){
        //You service add credentials in the header, so just call login
        authentificateUserCall=webServiceAuthenticated.login();
        authentificateUserCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Response<User> response) {
                if(response.body()!=null){
                    //ok your user is authentificated
                    currentUser=response.body();
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }
    /***********************************************************
     *  Use a CustomCall Object
     *  Here: ErrorHandlingCall
     *  ErrorHandlingCall<Post> getPostOneWithError()
     **********************************************************/
    /**
     * The call that handles errors
     */
    ErrorHandlingCall<Post> getPostOneWithErrorCall;
    /**
     * The callBack that manages the errors when they appear
     */
    ErrorHandlingCallBack errorHandlingCallBack=null;

    /**
     * Load a stuff with an errorHandlingCall
     */
    public void loadWithErrorHandlingCall(){
        //first initialize your error handling callback
        if(errorHandlingCallBack==null){
            errorHandlingCallBack= instanciateErrorHandlingCallBack();
        }
        //then instanciate
        getPostOneWithErrorCall=webServiceComplex.getPostOneWithError();
        //initialize your errorCallBack
        getPostOneWithErrorCall.initializeCallBack(errorHandlingCallBack);
        //make your call
        getPostOneWithErrorCall.enqueue();
    }

    private ErrorHandlingCallBack instanciateErrorHandlingCallBack(){
        return new ErrorHandlingCallBack() {
            @Override
            public void success(Response response) {
                Log.e("BusinessService", "Reponse is Success" + response.body());
            }

            @Override
            public void unauthenticated(Response response) {
                Log.e("BusinessService", "UNAUTHENTICATED !!!");

            }

            @Override
            public void clientError(Response response) {
                Log.e("BusinessService", "CLIENT ERROR " + response.code() + " :" + response.message());
            }

            @Override
            public void serverError(Response response) {
                Log.e("BusinessService", "Server ERROR " + response.code() + " :" + response.message());
            }

            @Override
            public void networkError(IOException e) {
                Log.e("BusinessService", "IOException ", e);
            }

            @Override
            public void unexpectedError(Throwable t) {
                Log.e("BusinessService", "Death Metal Error without roses ", t);
            }
        };
    }
    /***********************************************************
     *  Loading a Picture (needs 3 methods)
     **********************************************************/
    /**
     * Get a Picture (entrance method)
     */
    public void getAPicture() {
        //load a picture
        getPhotoByIdCall = webServiceComplex.getPhotoById(3);
        getPhotoByIdCall.enqueue(new Callback<Photo>() {
            @Override
            public void onResponse(Response<Photo> response) {
                loadPhoto(response.body());
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }
    /**
     * The runnable to be post on UiThread to update the imageView
     */
    UpdatePhotoRunnable updatePhotoRunnable;

    /***
     * Load a picture
     *
     * @param photo
     */
    public void loadPhoto(Photo photo) {
        if (this != null) {
            //first open the object and find the URL of the picture
            try {
                if (activity != null) urlGetPicture(photo.getUrl());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Find the picture depending on the url
     *
     * @param urlGetPicture
     * @throws IOException
     */
    private void urlGetPicture(String urlGetPicture) throws IOException {
        if (activity != null) {
            Log.e("Service", "urlGetPicture Current thread :" + Thread.currentThread());
            Request request = new Request.Builder()
                    .url(urlGetPicture)
                    .build();
            okhttp3.Call postCall = RetrofitBuilder.getOkHttpClient(activity).newCall(request);
            postCall.enqueue(new okhttp3.Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    //!\does not belong to the main thread :(
                    e.printStackTrace();
                }

                @Override
                public void onResponse(okhttp3.Response response) throws IOException {
                    Log.e("Service", "urlGetPicture:onResponse Current thread :" + Thread.currentThread());
                    //!\does not belong to the main thread :(
                    if (response.code() == 200) {
                        ResponseBody in = response.body();
                        BufferedSource is = in.source();
                        Bitmap bitmap = BitmapFactory.decodeStream(is.inputStream());
                        is.close();
                        response.body().close();
                        //and then update the imageView
                        //but as you're not in the main thread there is some boiler code to implement
                        launchUpdatePhotoRunnable(bitmap);
                    }
                }
            });
        }
    }

    /**
     * This method runs on Thread[OkHttp http://placehold.it/600/24f355,5,main]
     * not the UiThread...
     * Create and launch the runnable in the UI thread
     *
     * @param bitmap
     */
    private void launchUpdatePhotoRunnable(Bitmap bitmap) {
        updatePhotoRunnable = new UpdatePhotoRunnable(bitmap);
        if (activity != null) activity.runOnUiThread(updatePhotoRunnable);
    }

    /**
     * The runnable to post the drawable in the UI thread to update the imageView
     * It's not the best, we should have used EventBus or Glide or Picasso
     * (not Volley not enough documentation)
     */
    public class UpdatePhotoRunnable implements Runnable {
        /**
         * The bitmap to give to the imageView
         */
        Bitmap bitmap;

        /**
         * constructor
         *
         * @param bitmap
         */
        public UpdatePhotoRunnable(Bitmap bitmap) {
            this.bitmap = bitmap;
        }

        /**
         * Starts executing the active part of the class' code. This method is
         * called when a thread is started that has been created with a class which
         * implements {@code Runnable}.
         */
        @Override
        public void run() {
            updatePhoto(bitmap);
        }
    }

    /**
     * This method runs on UiThread
     * Update the picture now you have the Bitmap to display
     *
     * @param bitmap
     */
    private void updatePhoto(Bitmap bitmap) {
        Log.e("Service", "updatePhoto Current thread :" + Thread.currentThread());
        if (activity != null) {
            activity.updateImvPhoto(new BitmapDrawable(activity.getResources(), bitmap));
        }
    }


}
