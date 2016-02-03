
package com.android2ee.formation.libraries.square.retrofit.model;

import java.util.HashMap;
import java.util.Map;

//@Generated("org.jsonschema2pojo")
public class User {

    private int id;
    private String name;
    private String username;
    private String email;
    private Address address;
    private String phone;
    private String website;
    private Company company;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public User() {
    }

    /**
     * 
     * @param id
     * @param phone
     * @param username
     * @param website
     * @param address
     * @param email
     * @param company
     * @param name
     */
    public User(int id, String name, String username, String email, Address address, String phone, String website, Company company) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.website = website;
        this.company = company;
    }

    /**
     * 
     * @return
     *     The id
     */
    public int getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(int id) {
        this.id = id;
    }

    public User withId(int id) {
        this.id = id;
        return this;
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

    public User withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * 
     * @return
     *     The username
     */
    public String getUsername() {
        return username;
    }

    /**
     * 
     * @param username
     *     The username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    public User withUsername(String username) {
        this.username = username;
        return this;
    }

    /**
     * 
     * @return
     *     The email
     */
    public String getEmail() {
        return email;
    }

    /**
     * 
     * @param email
     *     The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    public User withEmail(String email) {
        this.email = email;
        return this;
    }

    /**
     * 
     * @return
     *     The address
     */
    public Address getAddress() {
        return address;
    }

    /**
     * 
     * @param address
     *     The address
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    public User withAddress(Address address) {
        this.address = address;
        return this;
    }

    /**
     * 
     * @return
     *     The phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 
     * @param phone
     *     The phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public User withPhone(String phone) {
        this.phone = phone;
        return this;
    }

    /**
     * 
     * @return
     *     The website
     */
    public String getWebsite() {
        return website;
    }

    /**
     * 
     * @param website
     *     The website
     */
    public void setWebsite(String website) {
        this.website = website;
    }

    public User withWebsite(String website) {
        this.website = website;
        return this;
    }

    /**
     * 
     * @return
     *     The company
     */
    public Company getCompany() {
        return company;
    }

    /**
     * 
     * @param company
     *     The company
     */
    public void setCompany(Company company) {
        this.company = company;
    }

    public User withCompany(Company company) {
        this.company = company;
        return this;
    }

    @Override
    public String toString() {
        String eol="\r\n";
        final StringBuffer sb = new StringBuffer("User{");
        sb.append("additionalProperties=").append(additionalProperties);
        sb.append(",").append(eol).append(" id=").append(id);
        sb.append(",").append(eol).append(" name='").append(name).append('\'');
        sb.append(",").append(eol).append(" username='").append(username).append('\'');
        sb.append(",").append(eol).append(" email='").append(email).append('\'');
        sb.append(",").append(eol).append(" address=").append(address);
        sb.append(",").append(eol).append(" phone='").append(phone).append('\'');
        sb.append(",").append(eol).append(" website='").append(website).append('\'');
        sb.append(",").append(eol).append(" company=").append(company);
        sb.append('}').append(eol).append(eol);
        return sb.toString();
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public User withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}
