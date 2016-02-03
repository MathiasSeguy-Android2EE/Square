/**
 * <ul>
 * <li>ErrorHandlingCall</li>
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Mathias Seguy - Android2EE on 25/01/2016.
 * based on https://github.com/square/retrofit/blob/master/samples/src/main/java/com/example/retrofit/ErrorHandlingCallAdapter.java
 */
public class ErrorHandlingCall<T> implements ErrorHandlingCallIntf<T> {
    /**
     * The real call beyound the this call
     */
    private final Call<T> call;

    /**
     * The call back to use to guive more granularity to the error handling to the client
     */
    private ErrorHandlingCallBack<T> errorHandlingCallBack;

    /***********************************************************
     * Constructor
     **********************************************************/
    /**
     * Used by the ErrorHandlingCallAdapterFactory
     *
     * @param call
     */
    ErrorHandlingCall(Call<T> call) {
        this.call = call;
    }

    /**
     * Used by clone
     *
     * @param errorHandlingCallBack
     * @param call
     */
    private ErrorHandlingCall(ErrorHandlingCallBack<T> errorHandlingCallBack, Call<T> call) {
        this.errorHandlingCallBack = errorHandlingCallBack;
        this.call = call.clone();
    }

    /**
     * Mandatory
     * To be called before execute or enqueue
     *
     * @param callBack
     */
    @Override
    public void initializeCallBack(ErrorHandlingCallBack callBack) {
        errorHandlingCallBack = callBack;
    }

    /***********************************************************
     *   implements interface Call<T>
     **********************************************************/

    /**
     * Synchronously send the request and return its response.
     *
     * @throws IOException      if a problem occurred talking to the server.
     * @throws RuntimeException (and subclasses) if an unexpected error occurs creating the request
     *                          or decoding the response.
     */
    @Override
    public Response<T> execute() throws IOException {
        if (errorHandlingCallBack == null) {
            throw new IllegalStateException("You have to call initializeCallBack(ErrorHandlingCallBack callBack) before execute");
        }
        //then analyse the response and do your expected work
        Response<T> response = call.execute();
        int code = response.code();
        if (code >= 200 && code < 300) {
            //it's ok
            return response;
        }
        //It's not ok anymore, return the response but make the errorCallBack
        else if (code == 401) {
            errorHandlingCallBack.unauthenticated(response);
        } else if (code >= 400 && code < 500) {
            errorHandlingCallBack.clientError(response);
        } else if (code >= 500 && code < 600) {
            errorHandlingCallBack.serverError(response);
        } else {
            errorHandlingCallBack.unexpectedError(new RuntimeException("Unexpected response " + response));
        }
        return response;
    }

    /**
     * Asynchronously send the request and notify {@code callback} of its response or if an error
     * occurred talking to the server, creating the request, or processing the response.
     *
     */
    @Override
    public void enqueue() {
        if (errorHandlingCallBack == null) {
            throw new IllegalStateException("You have to call initializeCallBack(ErrorHandlingCallBack callBack) before enqueue");
        }
        //do the job with th real call object
        call.enqueue(new Callback<T>() {
            /**
             * Invoked for a received HTTP response.
             * <p/>
             * Note: An HTTP response may still indicate an application-level failure such as a 404 or 500.
             * Call {@link Response#isSuccess()} to determine if the response indicates success.
             *
             * @param response
             */
            @Override
            public void onResponse(Response<T> response) {
                int code = response.code();
                if (code >= 200 && code < 300) {
                    errorHandlingCallBack.success(response);
                } else if (code == 401) {
                    errorHandlingCallBack.unauthenticated(response);
                } else if (code >= 400 && code < 500) {
                    errorHandlingCallBack.clientError(response);
                } else if (code >= 500 && code < 600) {
                    errorHandlingCallBack.serverError(response);
                } else {
                    errorHandlingCallBack.unexpectedError(new RuntimeException("Unexpected response " + response));
                }
            }

            /**
             * Invoked when a network exception occurred talking to the server or when an unexpected
             * exception occurred creating the request or processing the response.
             *
             * @param t
             */
            @Override
            public void onFailure(Throwable t) {
                if (t instanceof IOException) {
                    errorHandlingCallBack.networkError((IOException) t);
                } else {
                    errorHandlingCallBack.unexpectedError(t);
                }
            }
        });
    }

    /**
     * Returns true if this call has been either {@linkplain #execute() executed} or {@linkplain
     * #enqueue() enqueued}. It is an error to execute or enqueue a call more than once.
     */
    @Override
    public boolean isExecuted() {
        return call.isExecuted();
    }

    /**
     * Cancel this call. An attempt will be made to cancel in-flight calls, and if the call has not
     * yet been executed it never will be.
     */
    @Override
    public void cancel() {
        call.cancel();
    }

    /**
     * True if {@link #cancel()} was called.
     */
    @Override
    public boolean isCanceled() {
        return call.isCanceled();
    }

    /**
     * Create a new, identical call to this one which can be enqueued or executed even if this call
     * has already been.
     */
    @Override
    public ErrorHandlingCallIntf<T> clone() {
        if (errorHandlingCallBack == null) {
            throw new IllegalStateException("You have to call initializeCallBack(ErrorHandlingCallBack callBack) before clone()");
        }
        return new ErrorHandlingCall<>(errorHandlingCallBack, call);
    }
}
