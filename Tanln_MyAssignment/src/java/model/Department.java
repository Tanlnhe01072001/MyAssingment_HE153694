/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author apc
 */
public class Department {
    private int did;
    private String dname;
    private ArrayList<Lecturer> lecturers = new ArrayList<>();

    public int getDid() {
        return did;
    }

    public String getDname() {
        return dname;
    }

    public ArrayList<Lecturer> getLecturers() {
        return lecturers;
    }
    
}
