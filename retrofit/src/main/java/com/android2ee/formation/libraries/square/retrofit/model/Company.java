
package com.android2ee.formation.libraries.square.retrofit.model;

import java.util.HashMap;
import java.util.Map;

//@Generated("org.jsonschema2pojo")
public class Company {

    private String name;
    private String catchPhrase;
    private String bs;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public Company() {
    }

    /**
     * 
     * @param catchPhrase
     * @param name
     * @param bs
     */
    public Company(String name, String catchPhrase, String bs) {
        this.name = name;
        this.catchPhrase = catchPhrase;
        this.bs = bs;
    }

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    public Company withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * 
     * @return
     *     The catchPhrase
     */
    public String getCatchPhrase() {
        return catchPhrase;
    }

    /**
     * 
     * @param catchPhrase
     *     The catchPhrase
     */
    public void setCatchPhrase(String catchPhrase) {
        this.catchPhrase = catchPhrase;
    }

    public Company withCatchPhrase(String catchPhrase) {
        this.catchPhrase = catchPhrase;
        return this;
    }

    /**
     * 
     * @return
     *     The bs
     */
    public String getBs() {
        return bs;
    }

    /**
     * 
     * @param bs
     *     The bs
     */
    public void setBs(String bs) {
        this.bs = bs;
    }

    public Company withBs(String bs) {
        this.bs = bs;
        return this;
    }


    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public Company withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        String eol="\r\n";
        final StringBuffer sb = new StringBuffer("Company{");
        sb.append("additionalProperties=").append(additionalProperties);
        sb.append(",").append(eol).append(" name='").append(name).append('\'');
        sb.append(",").append(eol).append(" catchPhrase='").append(catchPhrase).append('\'');
        sb.append(",").append(eol).append(" bs='").append(bs).append('\'');
        sb.append('}').append(eol);
        return sb.toString();
    }
}
