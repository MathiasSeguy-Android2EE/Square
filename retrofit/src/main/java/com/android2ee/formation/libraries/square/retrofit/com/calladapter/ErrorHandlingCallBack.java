/**
 * <ul>
 * <li>ErrorHandlingCallBack</li>
 * <li>com.android2ee.formation.libraries.square.retrofit.com.calladapter</li>
 * <li>25/01/2016</li>
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

package com.android2ee.formation.libraries.square.retrofit.com.calladapter;

import java.io.IOException;

import retrofit2.Response;

/**
 * Created by Mathias Seguy - Android2EE on 25/01/2016.
 * based on https://github.com/square/retrofit/blob/master/samples/src/main/java/com/example/retrofit/ErrorHandlingCallAdapter.java
 */
public interface ErrorHandlingCallBack<T>{
    /** Called for [200, 300) responses. */
    void success(Response<T> response);
    /** Called for 401 responses. */
    void unauthenticated(Response<?> response);
    /** Called for [400, 500) responses, except 401. */
    void clientError(Response<?> response);
    /** Called for [500, 600) response. */
    void serverError(Response<?> response);
    /** Called for network errors while making the call. */
    void networkError(IOException e);
    /** Called for unexpected errors while making the call. */
    void unexpectedError(Throwable t);
}
