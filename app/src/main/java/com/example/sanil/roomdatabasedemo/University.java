package com.example.sanil.roomdatabasedemo;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by sanil on 04-06-2018.
 */

@Entity
public class University {

    @PrimaryKey(autoGenerate = true)
    private int slNo;
    private String name;

    @Embedded(prefix = "clg")
    College college;

    public int getSlNo() {
        return slNo;
    }

    public void setSlNo(int slNo) {
        this.slNo = slNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public College getCollege() {
        return college;
    }

    public void setCollege(College college) {
        this.college = college;
    }
}

