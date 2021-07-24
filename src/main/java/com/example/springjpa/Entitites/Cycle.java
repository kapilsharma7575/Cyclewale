package com.example.springjpa.Entitites;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "cycles")
public class Cycle {

    @Id
    @Column(nullable = false, unique = true, length = 45)
    private Long cycleNo;

    @Column(nullable = false, length = 45)
    private String cycleStand;

    @Column
    private boolean taken;

    public boolean isTaken() {
        return taken;
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }

    public Long getCycleNo() {
        return cycleNo;
    }

    public void setCycleNo(Long cycleNo) {
        this.cycleNo = cycleNo;
    }

    public String getCycleStand() {
        return cycleStand;
    }

    public void setCycleStand(String cycleStand) {
        this.cycleStand = cycleStand;
    }

}