package com.example.laptop_apik.parkir;

/**
 * Created by Laptop_Apik on 7/13/2019.
 */

class SpotDetails {

    private String name;
    private String status;

    public SpotDetails(){}

    public SpotDetails(String name, String status) {
        this.name = name;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}



