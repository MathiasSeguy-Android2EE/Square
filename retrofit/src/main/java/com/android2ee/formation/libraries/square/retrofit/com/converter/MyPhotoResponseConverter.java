/**
 * <ul>
 * <li>MyPhotoResponseFactory</li>
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

import android.util.Log;

import com.android2ee.formation.libraries.square.retrofit.model.Photo;
import com.squareup.moshi.JsonReader;

import java.io.IOException;

import okhttp3.ResponseBody;
import okio.BufferedSource;
import retrofit2.Converter;

/**
 * Created by Mathias Seguy - Android2EE on 25/01/2016.
 * Be sure to use OkIo object (Source) to read in
 */
public class MyPhotoResponseConverter implements Converter<ResponseBody, Photo> {
    //define an instance to retrieve the converter only once
    static final MyPhotoResponseConverter INSTANCE = new MyPhotoResponseConverter();

    //default empty constructor
    private MyPhotoResponseConverter() {
    }

    //The real conversion from teh server response to the Photo object
    @Override
    public Photo convert(ResponseBody value) throws IOException {
        return readJson(value.source());
    }

    /**
     * Read the Object from the source
     * @param source
     * @return The photo object
     * @throws IOException
     */
    public Photo readJson(BufferedSource source) throws IOException {
        Log.e("MyPhotoResponseConverter","Converted using my own converter");
        if(source==null){
            throw new IOException();
        }
        Photo photo =new Photo();
        //Then read th JSon File
        JsonReader reader = JsonReader.of(source);
        reader.beginObject();
        while (reader.hasNext()) {
            switch (reader.nextName()) {
                case "albumId":
                    photo.setAlbumId(reader.nextInt());
                    break;
                case "id":
                    photo.setId(reader.nextInt());
                    break;
                case "title":
                    photo.setTitle(reader.nextString());
                    break;
                case "url":
                    photo.setUrl(reader.nextString());
                    break;
                case "thumbnailUrl":
                    photo.setThumbnailUrl(reader.nextString());
                    break;
                default:
                    break;
            }
        }
        reader.endObject();
        reader.close();
        source.close();
        return photo;
    }
}
