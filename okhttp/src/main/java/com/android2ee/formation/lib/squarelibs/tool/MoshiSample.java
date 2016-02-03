/**
 * <ul>
 * <li>ForecastRestYahooSax</li>
 * <li>com.android2ee.formation.restservice.sax.forecastyahoo</li>
 * <li>28 mai 2014</li>
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

package com.android2ee.formation.lib.squarelibs.tool;

import android.content.Context;
import android.util.Log;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonReader;
import com.squareup.moshi.JsonWriter;
import com.squareup.moshi.Moshi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

/**
 * Created by Mathias Seguy - Android2EE on 16/04/2015.
 * This explains Moshi which is a simple JSon writer/reader for streaming
 */
public class MoshiSample {
    /***************************************************************/
    /************                WriteJson            *******/
    /**************************************************************/
    /***
     * Write a Json : create file and write
     *
     * @param ctx
     */
    public void writeJson(Context ctx) {
        Log.e("MoshiSample", "writeJson called");
        //open
        File myFile = new File(ctx.getCacheDir(), "myJsonFile");
        try {
            //then write
            if (!myFile.exists()) {
                myFile.createNewFile();
            }
            BufferedSink okioBufferSink = Okio.buffer(Okio.sink(myFile));
            //do the Moshi stuff:
            JsonWriter jsonW = JsonWriter.of(okioBufferSink);
            jsonW.setIndent("    ");
            writeJson(jsonW);
            //you have to close the JsonWrtiter too (esle nothing will happen)
            jsonW.close();
            //don't forget to close, else nothing appears
            okioBufferSink.close();
            //check the file by reading it using OkIo
            BufferedSource okioBufferSrce = Okio.buffer(Okio.source(myFile));
            Log.e("MoshiSample","writeJson returns "+okioBufferSrce.readUtf8());
            Log.e("MoshiSample", "writeJson file read...");
            okioBufferSrce.close();

            Log.e("MoshiSample", "writeJson over");
        } catch (FileNotFoundException e) {
            Log.e("MoshiSample", "Fuck FileNotFoundException occurs", e);
        } catch (IOException e) {
            Log.e("MoshiSample", "Fuck IOException occurs", e);
        }
    }

    /**
     * Write Json in the JSonWriter gave as parameter
     *
     * @param jsonW
     */
    private void writeJson(JsonWriter jsonW) {
        try {
            jsonW.beginObject();
            jsonW.name("Attribute1").value("Has a Value");
            jsonW.name("Attribute2").value(12);
            jsonW.name("Attribute3").value(true);
            jsonW.name("AttributeObject")
                    .beginObject()
                    .name("Attribute1").value("SubObject")
                    .name("Attribute2").value("Only string to parse easier")
                    .name("Attribute3").value("It's a tutorial... I take it cool")
                    .endObject();
            jsonW.name("Array")
                    .beginArray()
                    .value("item1")
                    .value("item2")
                    .value("item3")
                    .endArray();
            jsonW.name("ArrayWithName")
                    .beginArray()
                    .beginObject().name("item1").value("value1").endObject()
                    .beginObject().name("item2").value("value2").endObject()
                    .beginObject().name("item3").value("value3").endObject()
                    .endArray();
            jsonW.endObject();
        } catch (IOException e) {
            Log.e("MoshiSample", "Fuck IOException occurs", e);
        }

    }
    /***************************************************************/
    /************                ReadJSon            *******/
    /**************************************************************/
    public String readJson(Context ctx) {
        Log.e("MoshiSample", "readJson called");
        //open
        File myFile = new File(ctx.getCacheDir(), "myJsonFile");
        JsonReader reader=null;
        StringBuilder strBuild=new StringBuilder();
        String eol= System.getProperty("line.separator");
        try {
            //then write
            if (!myFile.exists()) {
                Log.e("MoshiSample", "readJson: file doesn't exist ");
                myFile.createNewFile();
            }else{
                Log.e("MoshiSample", "readJson: File exists ");
            }
            //check the file by reading it using OkIo
            BufferedSource okioBufferSrce = Okio.buffer(Okio.source(myFile));
            strBuild.append("File content :" + okioBufferSrce.readUtf8()).append(eol);
            strBuild.append("file read... now trying to parse JSon\r\n").append(eol);
            okioBufferSrce.close();

            //Build the source :
            BufferedSource source = Okio.buffer(Okio.source(myFile));
            //Then read th JSon File
            reader = JsonReader.of(source);
            reader.beginObject();
            while (reader.hasNext()) {
                strBuild.append("readJson: in the loop found peek :" + reader.peek()).append(eol);
                switch (reader.nextName()) {
                    case "Attribute1":
                        strBuild.append("readJson: in the loop found Attribute1 :" + reader.nextString()).append(eol);
                        break;
                    case "Attribute2":
                        strBuild.append("readJson: in the loop found Attribute2 :" + reader.nextInt()).append(eol);
                        break;
                    case "Attribute3":
                        strBuild.append("readJson: in the loop found Attribute3 :" + reader.nextBoolean()).append(eol);
                        break;
                    case "AttributeObject":
                        //Parse an object (same as here)
                        reader.beginObject();
                        strBuild.append("readJson: in the loop found " + reader.nextName() + " :" + reader.nextString()).append(eol);
                        strBuild.append("readJson: in the loop found " + reader.nextName() + " :" + reader.nextString()).append(eol);
                        strBuild.append("readJson: in the loop found " + reader.nextName() + " :" + reader.nextString()).append(eol);
                        reader.endObject();
                        break;
                    case "Array":
                        strBuild.append("readJson: in the loop found a simple array with only values").append(eol);
//                        strBuffReadJson.append("Array :");
                        reader.beginArray();
                        while (reader.hasNext()) {
                            strBuild.append("readJson: in the loop found new item:" + reader.nextString()).append(eol);
                        }
                        reader.endArray();
                        break;
                    case "ArrayWithName":
                        strBuild.append("readJson: in the loop found an array with only name/values pairs").append(eol);
                        reader.beginArray();
                        while (reader.hasNext()) {
                            reader.beginObject();
                            strBuild.append("readJson: in the loop found " + reader.nextName() + " :" + reader.nextString()).append(eol);
                            reader.endObject();
                        }
                        reader.endArray();
                        break;
                    //others cases
                    default:
                        break;
                }
            }
            reader.endObject();
//            reader.close();
//            strBuffReadJson.append("end of File ou pas ?no?");
            reader.close();
            okioBufferSrce.close();
//            Log.e("MoshiSample", "readJson strBuffReadJson value:" + strBuffReadJson.toString());
        } catch (FileNotFoundException e) {
            Log.e("MainActivity", "Fuck FileNotFoundException occurs", e);
        } catch (IOException e) {
            Log.e("MainActivity", "Fuck IOException occurs", e);
        } catch (Exception e) {
            Log.e("MainActivity", "Fuck Exception occurs", e);
        } finally {
            Log.e("MoshiSample", "readJson over ehoeho !!!" + strBuild.toString());
            return strBuild.toString();
//            return  strBuffReadJson.toString();
        }
    }
    /***************************************************************/
    /************                JsonAdapter            *******/
    /**************************************************************/

    public String usingAdapter(Context ctx) {
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<MyJsonObject> adapter = moshi.adapter(MyJsonObject.class);
        MyJsonObject myObj=null;
        //write it
        //open
        File myFile = new File(ctx.getCacheDir(), "myJsonObjectFile");
        try {
            //then write
            if (!myFile.exists()) {
                myFile.createNewFile();
            }
            BufferedSink okioBufferSink = Okio.buffer(Okio.sink(myFile));
            adapter.toJson(okioBufferSink, new MyJsonObject());
            //don't forget to close, else nothing appears
            okioBufferSink.close();
            //then read :
            BufferedSource okioBufferSource = Okio.buffer(Okio.source(myFile));
            myObj = adapter.fromJson(okioBufferSource);
        } catch (FileNotFoundException e) {
            Log.e("MainActivity", "Fuck FileNotFoundException occurs", e);
        } catch (IOException e) {
            Log.e("MainActivity", "Fuck IOException occurs", e);
        }finally {
            return myObj==null?"null":myObj.toString();
        }
    }
}
