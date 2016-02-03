
package com.android2ee.formation.libraries.square.retrofit.model;

import java.util.HashMap;
import java.util.Map;

//@Generated("org.jsonschema2pojo")
public class Geo {

    private String lat;
    private String lng;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public Geo() {
    }

    /**
     * 
     * @param lng
     * @param lat
     */
    public Geo(String lat, String lng) {
        this.lat = lat;
        this.lng = lng;
    }

    /**
     * 
     * @return
     *     The lat
     */
    public String getLat() {
        return lat;
    }

    /**
     * 
     * @param lat
     *     The lat
     */
    public void setLat(String lat) {
        this.lat = lat;
    }

    public Geo withLat(String lat) {
        this.lat = lat;
        return this;
    }

    /**
     * 
     * @return
     *     The lng
     */
    public String getLng() {
        return lng;
    }

    /**
     * 
     * @param lng
     *     The lng
     */
    public void setLng(String lng) {
        this.lng = lng;
    }

    public Geo withLng(String lng) {
        this.lng = lng;
        return this;
    }

    @Override
    public String toString() {
        String eol="\r\n";
        final StringBuffer sb = new StringBuffer("Geo{");
        sb.append("additionalProperties=").append(additionalProperties);
        sb.append(",").append(eol).append(" lat='").append(lat).append('\'');
        sb.append(",").append(eol).append(" lng='").append(lng).append('\'');
        sb.append('}').append(eol);
        return sb.toString();
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public Geo withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}
