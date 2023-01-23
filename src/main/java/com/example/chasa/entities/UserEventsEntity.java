package com.example.chasa.entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "user_events", schema = "chasa", catalog = "")
public class UserEventsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_user_event", nullable = false)
    private int idUserEvent;
    @Basic
    @Column(name = "date_time_event_subscription", nullable = false)
    private Timestamp dateTimeEventSubscription;
    @Basic
    @Column(name = "user_event_status", nullable = false)
    private byte userEventStatus;
    @Basic
    @Column(name = "id_user", nullable = false)
    private int idUser;
    @Basic
    @Column(name = "id_event", nullable = false)
    private int idEvent;
    @ManyToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id_user", nullable = false)
    private UsersEntity usersByIdUser;
    @ManyToOne
    @JoinColumn(name = "id_event", referencedColumnName = "id_event", nullable = false)
    private EventsEntity eventsByIdEvent;

    public int getIdUserEvent() {
        return idUserEvent;
    }

    public void setIdUserEvent(int idUserEvent) {
        this.idUserEvent = idUserEvent;
    }

    public Timestamp getDateTimeEventSubscription() {
        return dateTimeEventSubscription;
    }

    public void setDateTimeEventSubscription(Timestamp dateTimeEventSubscription) {
        this.dateTimeEventSubscription = dateTimeEventSubscription;
    }

    public byte getUserEventStatus() {
        return userEventStatus;
    }

    public void setUserEventStatus(byte userEventStatus) {
        this.userEventStatus = userEventStatus;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(int idEvent) {
        this.idEvent = idEvent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEventsEntity that = (UserEventsEntity) o;

        if (idUserEvent != that.idUserEvent) return false;
        if (userEventStatus != that.userEventStatus) return false;
        if (idUser != that.idUser) return false;
        if (idEvent != that.idEvent) return false;
        if (dateTimeEventSubscription != null ? !dateTimeEventSubscription.equals(that.dateTimeEventSubscription) : that.dateTimeEventSubscription != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idUserEvent;
        result = 31 * result + (dateTimeEventSubscription != null ? dateTimeEventSubscription.hashCode() : 0);
        result = 31 * result + (int) userEventStatus;
        result = 31 * result + idUser;
        result = 31 * result + idEvent;
        return result;
    }

    public UsersEntity getUsersByIdUser() {
        return usersByIdUser;
    }

    public void setUsersByIdUser(UsersEntity usersByIdUser) {
        this.usersByIdUser = usersByIdUser;
    }

    public EventsEntity getEventsByIdEvent() {
        return eventsByIdEvent;
    }

    public void setEventsByIdEvent(EventsEntity eventsByIdEvent) {
        this.eventsByIdEvent = eventsByIdEvent;
    }
}
