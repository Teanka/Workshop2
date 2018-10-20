package pl.coderslab.model;

import java.util.Date;

public class Solution {

    private int id;
    private Date created;
    private Date updated;
    private String description;
    private Exercise exercise;
    private User user;

    public Solution(){}

    public Solution(Date created, Date updated, String description, Exercise exercise, User user) {
        this.created = created;
        this.updated = updated;
        this.description = description;
        this.exercise = exercise;
        this.user = user;
    }


}
