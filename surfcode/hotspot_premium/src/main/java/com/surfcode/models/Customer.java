package com.surfcode.models;

/**
 *
 * @author dennis
 */
public class Customer {
    private int id;
    private String phone;
    private String code;
    private int amount;
    private int days;
    private String expiry_time;
    private String created_at;
    private String updated_at;
    private boolean active;
    
    //default constructor
    public Customer(){
        active = false;
    }
    //constructor using fields
    public Customer(int id, String phone, String code, int amount, int days, String expiry_time, String created_at, String updated_at, boolean active){
        super();
        this.id = id;
        this.phone = phone;
        this.code = code;
        this.amount = amount;
        this.days = days;
        this.expiry_time = expiry_time;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.active = active;
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
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the amount
     */
    public int getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * @return the days
     */
    public int getDays() {
        return days;
    }

    /**
     * @param days the days to set
     */
    public void setDays(int days) {
        this.days = days;
    }

    /**
     * @return the expiry_time
     */
    public String getExpiry_time() {
        return expiry_time;
    }

    /**
     * @param expiry_time the expiry_time to set
     */
    public void setExpiry_time(String expiry_time) {
        this.expiry_time = expiry_time;
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

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the isActive
     */
    public boolean isActive() {
        return active;
    }

    /**
     * @param isActive the isActive to set
     */
    public void setActive(boolean isActive) {
        this.active = isActive;
    }
}
