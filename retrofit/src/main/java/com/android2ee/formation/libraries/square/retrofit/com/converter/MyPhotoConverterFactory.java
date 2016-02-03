/**
 * <ul>
 * <li>MyConverter</li>
 * <li>com.android2ee.formation.libraries.square.retrofit.com.converter</li>
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

package com.android2ee.formation.libraries.square.retrofit.com.converter;

import com.android2ee.formation.libraries.square.retrofit.model.Photo;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by Mathias Seguy - Android2EE on 25/01/2016.
 */
public class MyPhotoConverterFactory extends Converter.Factory {

    public static MyPhotoConverterFactory create(){
        return new MyPhotoConverterFactory();
    }


    /**
     * Returns a {@link Converter} for converting {@code type} to an HTTP request body, or null if
     * {@code type} cannot be handled by this factory. This is used to create converters for types
     * specified by {@link Body @Body}, {@link Part @Part}, and {@link PartMap @PartMap}
     * values.
     *
     * @param type
     * @param annotations
     * @param retrofit
     */
    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        //If it's a Photo instance then convert
        if(type==Photo.class){
            return MyPhotoRequestConverter.INSTANCE;
        }
        //else use the Chain of responsability ppatern and return null
        //the api will look at the next converter
        return null;
    }

    /**
     * Returns a {@link Converter} for converting an HTTP response body to {@code type}, or null if
     * {@code type} cannot be handled by this factory. This is used to create converters for
     * response types such as {@code SimpleResponse} from a {@code Call<SimpleResponse>}
     * declaration.
     *
     * @param type
     * @param annotations
     * @param retrofit
     */
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        //If it's a Photo instance then convert
        if(type==Photo.class){
            return MyPhotoResponseConverter.INSTANCE;
        }
        //else use the Chain of responsability pattern and return null
        //the api will look at the next converter
        return null;
    }
}
