package com.surfcode.models;

/**
 *
 * @author dennis
 */
public class Station {
    private int id;
    private String site;
    private String name;
    private int last_user = 0;
    private String created_at;
    private String updated_at;
    
    //default constructor
    public Station(){
    }
    //constructor using fields
    public Station(int id, String site, String name, int last_user, String created_at, String updated_at){
        super();
        this.id = id;
        this.site = site;
        this.name = name;
        this.last_user = last_user;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the last_user
     */
    public int getLast_user() {
        return last_user;
    }

    /**
     * @param last_user the last_user to set
     */
    public void setLast_user(int last_user) {
        this.last_user = last_user;
    }

    /**
     * @return the created_at
     */
    public String getCreated_at() {
        return created_at;
    }

    /**
     * @param created_at the created_at to set
     */
    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    /**
     * @return the updated_at
     */
    public String getUpdated_at() {
        return updated_at;
    }

    /**
     * @param updated_at the updated_at to set
     */
    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
    
}
