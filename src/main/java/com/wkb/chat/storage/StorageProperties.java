package com.wkb.chat.storage;

import org.springframework.stereotype.Service;

@Service
public class StorageProperties {

    /**
     * Folder location for storing files
     */
    private String location = "txt";

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
