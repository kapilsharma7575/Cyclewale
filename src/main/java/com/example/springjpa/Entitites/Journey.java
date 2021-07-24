package com.example.springjpa.Entitites;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "journey")
public class Journey
{
    @Id
    @Column(nullable = false, unique = true, length = 45)
    long userId;

    @Column(unique = true, nullable = false)
    long cycleId;

    @Column(nullable = false)
    String origin;

    @Column(nullable = false)
    String destination;

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public long getCycleId() {
        return cycleId;
    }

    public void setCycleId(long cycleId) {
        this.cycleId = cycleId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String toJSON() {
        return
                "{\"origin\": \"" + origin + "\"," +
                        "\"destination\": \"" + destination + "\"," +
                        "\"cycleId\":" + cycleId + "," +
                        "\"userId\": " + userId  +
                        '}';
    }
}