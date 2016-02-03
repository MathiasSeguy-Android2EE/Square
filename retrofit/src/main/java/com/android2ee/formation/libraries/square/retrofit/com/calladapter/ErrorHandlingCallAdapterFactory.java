/**
 * <ul>
 * <li>ErrorHandlingCallAdapterFactory</li>
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

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.CallAdapter.Factory;
import retrofit2.Retrofit;

/**
 * Created by Mathias Seguy - Android2EE on 25/01/2016.
 * based on https://github.com/square/retrofit/blob/master/samples/src/main/java/com/example/retrofit/ErrorHandlingCallAdapter.java
 */
public class ErrorHandlingCallAdapterFactory implements Factory{
    /**
     * Returns a call adapter for interface methods that return {@code returnType}, or null if it
     * cannot be handled by this factory.
     *
     * @param returnType
     * @param annotations
     * @param retrofit
     */
    @Override
    public CallAdapter<?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
       //tricky stuff, not proud od, but works really
        if(!returnType.toString().contains("ErrorHandlingCall")){
            //This is not handled by you, so return null and enjoy the responsability chain design pattern
            return null;
        }
        //this case is yours, do your job:
        //get the type (you use it below as the return parameter type of responseType
        final Type responseType = ((ParameterizedType) returnType).getActualTypeArguments()[0];
        return new CallAdapter<ErrorHandlingCall<?>>() {
            @Override public Type responseType() {
                return responseType;
            }

            @Override public <R> ErrorHandlingCall<R> adapt(Call<R> call) {
                return new ErrorHandlingCall<>(call);
            }
        };
    }

}
