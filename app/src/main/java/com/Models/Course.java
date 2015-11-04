package com.Models;

/**
 * Model for the courses- a list of these makes a schedule
 */
public class Course {
    private long course_id;
    private String course_name;
    private String course_room;
    private String course_building;

    public long getCourse_id() {
        return course_id;
    }

    public void setCourse_id(long course_id) {
        this.course_id = course_id;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getCourse_room() {
        return course_room;
    }

    public void setCourse_room(String course_room) {
        this.course_room = course_room;
    }

    public String getCourse_building() {
        return course_building;
    }

    public void setCourse_building(String course_building) {
        this.course_building = course_building;
    }
}
