/**
 * <ul>
 * <li>WebServerIntf</li>
 * <li>com.android2ee.formation.lib.squarelibs.retrofit</li>
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

import com.android2ee.formation.libraries.square.retrofit.com.calladapter.ErrorHandlingCall;
import com.android2ee.formation.libraries.square.retrofit.model.Photo;
import com.android2ee.formation.libraries.square.retrofit.model.Post;
import com.android2ee.formation.libraries.square.retrofit.model.User;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;


/**
 * Created by Mathias Seguy - Android2EE on 23/06/2015.
 * This class aims to define the webservice calls
 * based on "http://jsonplaceholder.typicode.com"
 */
public interface WebServerIntf {
    /***********************************************************
     * Get Method (this is the way to declare them using Retrofit 2.0)
     **********************************************************/
    @GET("posts/1")
    Call<Post> getPostOne();
    @GET("posts/{id}")
    Call<Post> getPostById(@Path("id") int id);
    @GET("posts")
    Call<List<Post>> getPostsList();
    @GET("users")
    Call<List<User>> getUserList();
    @GET("users/{id}")
    Call<User> getUserById(@Path("id") int id);
    @GET("photos/{id}")
    Call<Photo> getPhotoById(@Path("id") int id);
    @GET("photos/1")
    Call<Photo> getPhotoWithQuery(@Query("data") int id,@QueryMap Map<String,String>option);
    //you can also just define the url and skip every think
    @GET
    Call<Post> getByDirectUrlRequest(@Url String url);

    /***********************************************************
     *  Post/Put/Get
     **********************************************************/

    @POST("posts")
    Call<Post> addNewPost(@Body Post post);
    @PUT("users/1")
    Call<User> updateUserOne(@Body User user);
    @DELETE("users/{id}")
    Call<User> deleteUserById(@Path("id")int id);
    /***********************************************************
     *  MultiPart : a request with n bobies
     **********************************************************/
    @Multipart
    @PUT("photos")
    Call<Photo> newPhoto(@Part("photo") Photo photoObject,@Part("content") byte[] picture);
    /***********************************************************
     *  Form URL : adding parameter to the url ?param=toto
     **********************************************************/
    @FormUrlEncoded
    @POST("users/{id}/edit")
    Call<User> updateUserWithForm(@Path("id")int id, @Field("name")String name,@Field("points")int point);

    /***********************************************************
     * Using a callAdapter
     **********************************************************/
    //this method should crash
    @GET("posts/trois")
    ErrorHandlingCall<Post> getPostOneWithError();
    /***********************************************************
     *  Authentification
     *********************************************************/
    @GET("users/authent")
    Call<User>login();

}
