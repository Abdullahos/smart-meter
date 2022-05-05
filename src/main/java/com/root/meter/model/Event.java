package com.root.meter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

//{ "meterId", 1, "event": "5","type":"Tampering","date":"Wed Feb 23 22:37:02 2022"}
@Entity
public class Event {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @ManyToOne
    @JsonIgnore
    private Meter meterId;
    private Long event;
    /**ElementCollection create table for skills(not entity) and add employee id column to it.
     * FetchType.EAGER to retrieve the skills with getting employee info without specific ask for it.
    @CollectionTable
    @Enumerated(EnumType.STRING)
    private EventsTypes type;
    */
    private String type;
    private LocalDateTime date;

    public Long getId() {
        return id;
    }

    public Meter getMeterId() {
        return meterId;
    }

    public void setMeterId(Meter meterId) {
        this.meterId = meterId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEvent() {
        return event;
    }

    public void setEvent(Long event) {
        this.event = event;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
