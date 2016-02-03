/**
 * <ul>
 * <li>Data</li>
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

package com.android2ee.formation.libraries.square.retrofit.model;

/**
 * Created by Mathias Seguy - Android2EE on 23/06/2015.
 */
public class Post {
    String userId;
    int id;
    String title;
    String body;

    public Post() {
        userId="fake user id";
        id=194567;
        title="Jake Wharton is awesome";
        body="His droidCon conf :https://www.youtube.com/watch?v=KIAoQbAu3eA " +
                "and its slides https://speakerdeck.com/jakewharton/simple-http-with-retrofit-2-droidcon-nyc-2015";

    }

    @Override
    public String toString() {
        String eol="\r\n";
        final StringBuffer sb = new StringBuffer("Data{");
        sb.append("body='").append(body).append('\'');
        sb.append(",").append(eol).append(" userId='").append(userId).append('\'');
        sb.append(",").append(eol).append(" id=").append(id);
        sb.append(",").append(eol).append(" title='").append(title).append('\'');
        sb.append('}').append(eol).append(eol);
        return sb.toString();
    }
}
