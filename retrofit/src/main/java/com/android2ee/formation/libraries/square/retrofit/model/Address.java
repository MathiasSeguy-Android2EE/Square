
package com.android2ee.formation.libraries.square.retrofit.model;

import java.util.HashMap;
import java.util.Map;

//@Generated("org.jsonschema2pojo")
public class Address {

    private String street;
    private String suite;
    private String city;
    private String zipcode;
    private Geo geo;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     */
    public Address() {
    }

    /**
     * @param geo
     * @param zipcode
     * @param street
     * @param suite
     * @param city
     */
    public Address(String street, String suite, String city, String zipcode, Geo geo) {
        this.street = street;
        this.suite = suite;
        this.city = city;
        this.zipcode = zipcode;
        this.geo = geo;
    }

    /**
     * @return The street
     */
    public String getStreet() {
        return street;
    }

    /**
     * @param street The street
     */
    public void setStreet(String street) {
        this.street = street;
    }

    public Address withStreet(String street) {
        this.street = street;
        return this;
    }

    /**
     * @return The suite
     */
    public String getSuite() {
        return suite;
    }

    /**
     * @param suite The suite
     */
    public void setSuite(String suite) {
        this.suite = suite;
    }

    public Address withSuite(String suite) {
        this.suite = suite;
        return this;
    }

    /**
     * @return The city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city The city
     */
    public void setCity(String city) {
        this.city = city;
    }

    public Address withCity(String city) {
        this.city = city;
        return this;
    }

    /**
     * @return The zipcode
     */
    public String getZipcode() {
        return zipcode;
    }

    /**
     * @param zipcode The zipcode
     */
    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public Address withZipcode(String zipcode) {
        this.zipcode = zipcode;
        return this;
    }

    /**
     * @return The geo
     */
    public Geo getGeo() {
        return geo;
    }

    /**
     * @param geo The geo
     */
    public void setGeo(Geo geo) {
        this.geo = geo;
    }

    public Address withGeo(Geo geo) {
        this.geo = geo;
        return this;
    }


    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public Address withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        String eol="\r\n";
        final StringBuffer sb = new StringBuffer("Address{");
        sb.append("additionalProperties=").append(additionalProperties);
        sb.append(",").append(eol).append(" street='").append(street).append('\'');
        sb.append(",").append(eol).append(" suite='").append(suite).append('\'');
        sb.append(",").append(eol).append(" city='").append(city).append('\'');
        sb.append(",").append(eol).append(" zipcode='").append(zipcode).append('\'');
        sb.append(",").append(eol).append(" geo=").append(geo);
        sb.append('}').append(eol);
        return sb.toString();
    }
}
