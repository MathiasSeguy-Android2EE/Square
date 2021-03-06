/**
 * <ul>
 * <li>MyJsonObject</li>
 * <li>com.android2ee.formation.lib.squarelibs.tool</li>
 * <li>26/06/2015</li>
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

/**
 * Created by Mathias Seguy - Android2EE on 26/06/2015.
 */
public class MyJsonObject {
    public int userId;
    public int id;
    public  String title;
    public String body;

    public MyJsonObject() {
        userId=1121;
        id=3212;
        title="Dummy title";
        body="oh, so cuute the body";
    }

    @Override
    public String toString() {
        String eol= System.getProperty("line.separator");
        final StringBuffer sb = new StringBuffer("MyJsonObject{");
        sb.append(eol).append("body='").append(body).append('\'');
        sb.append(",").append(eol).append("userId=").append(userId);
        sb.append(",").append(eol).append(" id=").append(id);
        sb.append(",").append(eol).append(" title='").append(title).append('\'').append(eol);
        sb.append('}');
        return sb.toString();
    }
}
