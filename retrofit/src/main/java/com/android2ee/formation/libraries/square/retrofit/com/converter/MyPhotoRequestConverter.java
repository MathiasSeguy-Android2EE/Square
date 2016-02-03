/**
 * <ul>
 * <li>MyPhotoConverter</li>
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
import com.squareup.moshi.JsonWriter;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;
import retrofit2.Converter;

/**
 * Created by Mathias Seguy - Android2EE on 25/01/2016.
 * This is a way to implements a RequestBodyConverter
 * Be sure to use OkIo object (Sink) to write in
 */
public class MyPhotoRequestConverter<T> implements Converter<Photo, RequestBody> {
    //define an instance to retrieve the converter only once
    static final MyPhotoRequestConverter<Photo> INSTANCE = new MyPhotoRequestConverter<>();
    //the Media Type (Mime type)
    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");
    //the value to write (should be a Photo Object)
    private Photo photo;

    //default empty constructor
    private MyPhotoRequestConverter() {
    }

    //The real conversion from the Photo to it's json representation
    @Override
    public RequestBody convert(Photo value) throws IOException {
        //ensure the right object is passed to you
        if (value instanceof Photo) {
            //store it to write it latter
            photo = (Photo) value;
            //return your requestBody
            return new RequestBody() {
                //Override its main methods
                @Override
                public MediaType contentType() {
                    //yep this is the type
                    return MEDIA_TYPE;
                }

                @Override
                public void writeTo(BufferedSink sink) throws IOException {
                    //write the object in thus mlethod using the Sink gave as parameter
                    writeRequest(sink);
                }
            };
        } else {
            throw new IllegalArgumentException();
        }

    }

    /**
     * Write the Object in the sink
     *
     * @param sink
     */
    private void writeRequest(BufferedSink sink) throws IOException {
        //do the Moshi stuff:
        JsonWriter jsonW = JsonWriter.of(sink);
        jsonW.setIndent("    ");
        writeJson(jsonW);
        //you have to close the JsonWrtiter too (esle nothing will happen)
        jsonW.close();
        //don't forget to close, else nothing appears
        sink.close();
    }

    /**
     * Write Json in the JSonWriter gave as parameter
     *
     * @param jsonW
     */
    private void writeJson(JsonWriter jsonW) throws IOException {
        jsonW.beginObject();
        jsonW.name("albumId").value(photo.getAlbumId());
        jsonW.name("id").value(photo.getId());
        jsonW.name("title").value(photo.getTitle());
        jsonW.name("url").value(photo.getUrl());
        jsonW.name("thumbnailUrl").value(photo.getThumbnailUrl());
        jsonW.endObject();
    }
}

