package com.surfcode.models;

/**
 *
 * @author dennis
 */
public class Login {
    private int id;
    private int customerId;
    private int stationId;
    private int accountId;
    private String created_at;
    private String updated_at;
    
    //default constructor
    public Login(){
    }
    //constructor using fields
    public Login(int id, int customerId, int stationId, int accountId, String created_at, String updated_at){
        super();
        this.id = id;
        this.customerId = customerId;
        this.stationId = stationId;
        this.accountId = accountId;
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
     * @return the customerId
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * @param customerId the customerId to set
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * @return the stationId
     */
    public int getStationId() {
        return stationId;
    }

    /**
     * @param stationId the stationId to set
     */
    public void setStationId(int stationId) {
        this.stationId = stationId;
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
