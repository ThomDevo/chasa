package com.example.chasa.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "user_events", schema = "chasa")
@NamedQueries(value = {
        @NamedQuery(name = "UserEvent.SelectAllFutureEvents", query = "SELECT ue from UserEventsEntity ue WHERE ue.eventsByIdEvent.dateTimeEvent > CURRENT_TIMESTAMP AND (lower(ue.eventsByIdEvent.addressesByIdAddress.street) LIKE CONCAT('%', :researchEvent,'%') OR lower(ue.eventsByIdEvent.eventCategoriesByIdEventCategory.eventCategoryLabel) LIKE CONCAT('%', :researchEvent,'%') OR lower(ue.eventsByIdEvent.addressesByIdAddress.street) LIKE CONCAT('%', :researchEvent,'%') OR lower(ue.eventsByIdEvent.addressesByIdAddress.idCity.cityLabel) LIKE CONCAT('%', :researchEvent,'%') OR lower(ue.eventsByIdEvent.eventCategoriesByIdEventCategory.eventCategoryLabel) LIKE CONCAT('%', :researchEvent,'%')) Group BY ue.eventsByIdEvent.idEvent ORDER BY ue.eventsByIdEvent.dateTimeEvent ASC "),
        @NamedQuery(name = "UserEvent.SelectAllFutureEventsStatusTrue", query = "SELECT ue from UserEventsEntity ue WHERE ue.eventsByIdEvent.dateTimeEvent > CURRENT_TIMESTAMP AND ue.userEventStatus = TRUE AND (lower(ue.eventsByIdEvent.addressesByIdAddress.street) LIKE CONCAT('%', :researchEvent,'%') OR lower(ue.eventsByIdEvent.eventCategoriesByIdEventCategory.eventCategoryLabel) LIKE CONCAT('%', :researchEvent,'%') OR lower(ue.eventsByIdEvent.addressesByIdAddress.street) LIKE CONCAT('%', :researchEvent,'%') OR lower(ue.eventsByIdEvent.addressesByIdAddress.idCity.cityLabel) LIKE CONCAT('%', :researchEvent,'%') OR lower(ue.eventsByIdEvent.eventCategoriesByIdEventCategory.eventCategoryLabel) LIKE CONCAT('%', :researchEvent,'%')) ORDER BY ue.eventsByIdEvent.dateTimeEvent ASC "),
        @NamedQuery(name = "UserEvent.SelectAllMyFutureEvents", query = "SELECT ue from UserEventsEntity ue WHERE ue.eventsByIdEvent.dateTimeEvent > CURRENT_TIMESTAMP AND ue.usersByIdUser.idUser = :idUser AND (lower(ue.eventsByIdEvent.addressesByIdAddress.street) LIKE CONCAT('%', :researchEvent,'%') OR lower(ue.eventsByIdEvent.eventCategoriesByIdEventCategory.eventCategoryLabel) LIKE CONCAT('%', :researchEvent,'%') OR lower(ue.eventsByIdEvent.addressesByIdAddress.street) LIKE CONCAT('%', :researchEvent,'%') OR lower(ue.eventsByIdEvent.addressesByIdAddress.idCity.cityLabel) LIKE CONCAT('%', :researchEvent,'%') OR lower(ue.eventsByIdEvent.eventCategoriesByIdEventCategory.eventCategoryLabel) LIKE CONCAT('%', :researchEvent,'%')) ORDER BY ue.eventsByIdEvent.dateTimeEvent ASC "),
        @NamedQuery(name="UserEvent.SelectCountUserEventById", query="SELECT COUNT(ue) from UserEventsEntity ue where ue.eventsByIdEvent.idEvent = :idUserEvent"),
        @NamedQuery(name="UserEvent.SelectListUserEventById", query="SELECT ue from UserEventsEntity ue where ue.eventsByIdEvent.idEvent = :idUserEvent  AND (lower(ue.usersByIdUser.lastName) LIKE CONCAT('%', :researchUser,'%') OR lower(ue.usersByIdUser.firstName) LIKE CONCAT('%', :researchUser,'%') OR lower(ue.usersByIdUser.lifrasNumber) LIKE CONCAT('%', :researchUser,'%') OR lower(ue.usersByIdUser.userPhone) LIKE CONCAT('%', :researchUser,'%') OR lower(ue.usersByIdUser.email) LIKE CONCAT('%', :researchUser,'%')) ORDER BY ue.usersByIdUser.lastName ASC")
})
public class UserEventsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_user_event", nullable = false)
    private int idUserEvent;

    @NotNull
    @Basic
    @Column(name = "date_time_event_subscription", nullable = false)
    private Date dateTimeEventSubscription;

    @Basic
    @Column(name = "user_event_status", nullable = false)
    private boolean userEventStatus;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id_user", nullable = false)
    private UsersEntity usersByIdUser;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_event", referencedColumnName = "id_event", nullable = false)
    private EventsEntity eventsByIdEvent;

    public int getIdUserEvent() {
        return idUserEvent;
    }

    public void setIdUserEvent(int idUserEvent) {
        this.idUserEvent = idUserEvent;
    }

    public Date getDateTimeEventSubscription() {
        return dateTimeEventSubscription;
    }

    public void setDateTimeEventSubscription(Date dateTimeEventSubscription) {
        this.dateTimeEventSubscription = dateTimeEventSubscription;
    }

    public boolean getUserEventStatus() {
        return userEventStatus;
    }

    public void setUserEventStatus(boolean userEventStatus) {
        this.userEventStatus = userEventStatus;
    }

    public UsersEntity getIdUser() {
        return usersByIdUser;
    }

    public void setIdUser(UsersEntity usersByIdUser) {
        this.usersByIdUser = usersByIdUser;
    }

    public EventsEntity getIdEvent() {
        return eventsByIdEvent;
    }

    public void setIdEvent(EventsEntity eventsByIdEvent) {
        this.eventsByIdEvent = eventsByIdEvent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEventsEntity that = (UserEventsEntity) o;

        if (idUserEvent != that.idUserEvent) return false;
        //if (userEventStatus != that.userEventStatus) return false;
        //if (usersByIdUser != that.usersByIdUser) return false;
        //if (eventsByIdEvent != that.eventsByIdEvent) return false;
        //if (dateTimeEventSubscription != null ? !dateTimeEventSubscription.equals(that.dateTimeEventSubscription) : that.dateTimeEventSubscription != null)
            //return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idUserEvent;
        result = 31 * result + (dateTimeEventSubscription != null ? dateTimeEventSubscription.hashCode() : 0);
        //result = 31 * result + (int) userEventStatus;
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
