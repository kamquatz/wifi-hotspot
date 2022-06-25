/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.surfcode.models;

/**
 *
 * @author dennis
 */
public class Device {
    private int id;
    private String phone;
    private int device;
    private int connections;
    private String expiry_time;
    private String created_at;
    private String updated_at;
    
    //default constructor
    public Device(){
    }
    //constructor using fields
    public Device(int id, String phone, int device, int connections, String expiry_time, String created_at, String updated_at){
        super();
        this.id = id;
        this.phone = phone;
        this.device = device;
        this.connections = connections;
        this.expiry_time = expiry_time;
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
     * @return the device
     */
    public int getDevice() {
        return device;
    }

    /**
     * @param device the device to set
     */
    public void setDevice(int device) {
        this.device = device;
    }

    /**
     * @return the connections
     */
    public int getConnections() {
        return connections;
    }

    /**
     * @param connections the connections to set
     */
    public void setConnections(int connections) {
        this.connections = connections;
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
}
