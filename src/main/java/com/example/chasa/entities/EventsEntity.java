package com.example.chasa.entities;

import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "events", schema = "chasa")
public class EventsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_event", nullable = false)
    private int idEvent;

    @NotNull
    @Basic
    @Column(name = "date_time_event", nullable = false)
    private Timestamp dateTimeEvent;

    @NotNull
    @Range(min=0,max= 999)
    @Basic
    @Column(name = "max_num_people", nullable = false)
    private int maxNumPeople;


    @Range(min=0,max= 99999)
    @Basic
    @Column(name = "price", nullable = false, precision = 0)
    private double price;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_address", referencedColumnName = "id_address", nullable = false)
    private AddressesEntity addressesByIdAddress;

    @ManyToOne
    @JoinColumn(name = "id_license", referencedColumnName = "id_license")
    private LicensesEntity licensesByIdLicense;

    @ManyToOne
    @JoinColumn(name = "id_dive_site", referencedColumnName = "id_dive_site")
    private DiveSitesEntity diveSitesByIdDiveSite;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_event_category", referencedColumnName = "id_event_category", nullable = false)
    private EventCategoriesEntity eventCategoriesByIdEventCategory;

    @OneToMany(mappedBy = "eventsByIdEvent")
    private List<UserEventsEntity> userEventsByIdEvent;

    public int getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(int idEvent) {
        this.idEvent = idEvent;
    }

    public Timestamp getDateTimeEvent() {
        return dateTimeEvent;
    }

    public void setDateTimeEvent(Timestamp dateTimeEvent) {
        this.dateTimeEvent = dateTimeEvent;
    }

    public int getMaxNumPeople() {
        return maxNumPeople;
    }

    public void setMaxNumPeople(int maxNumPeople) {
        this.maxNumPeople = maxNumPeople;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public AddressesEntity getIdAddress() {
        return addressesByIdAddress;
    }

    public void setIdAddress(AddressesEntity addressesByIdAddress) {
        this.addressesByIdAddress = addressesByIdAddress;
    }

    public LicensesEntity getIdLicense() {
        return licensesByIdLicense;
    }

    public void setIdLicense(LicensesEntity licensesByIdLicense) {
        this.licensesByIdLicense = licensesByIdLicense;
    }

    public DiveSitesEntity getIdDiveSite() {
        return diveSitesByIdDiveSite;
    }

    public void setIdDiveSite(DiveSitesEntity idDiveSite) {
        this.diveSitesByIdDiveSite = idDiveSite;
    }

    public EventCategoriesEntity getIdEventCategory() {
        return eventCategoriesByIdEventCategory;
    }

    public void setIdEventCategory(EventCategoriesEntity eventCategoriesByIdEventCategory) {
        this.eventCategoriesByIdEventCategory = eventCategoriesByIdEventCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventsEntity that = (EventsEntity) o;

        if (idEvent != that.idEvent) return false;
        if (maxNumPeople != that.maxNumPeople) return false;
        if (Double.compare(that.price, price) != 0) return false;
        if (addressesByIdAddress != that.addressesByIdAddress) return false;
        if (eventCategoriesByIdEventCategory != that.eventCategoriesByIdEventCategory) return false;
        if (dateTimeEvent != null ? !dateTimeEvent.equals(that.dateTimeEvent) : that.dateTimeEvent != null)
            return false;
        if (licensesByIdLicense != null ? !licensesByIdLicense.equals(that.licensesByIdLicense) : that.licensesByIdLicense != null) return false;
        if (diveSitesByIdDiveSite != null ? !diveSitesByIdDiveSite.equals(that.diveSitesByIdDiveSite) : that.diveSitesByIdDiveSite != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = idEvent;
        result = 31 * result + (dateTimeEvent != null ? dateTimeEvent.hashCode() : 0);
        result = 31 * result + maxNumPeople;
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    public AddressesEntity getAddressesByIdAddress() {
        return addressesByIdAddress;
    }

    public void setAddressesByIdAddress(AddressesEntity addressesByIdAddress) {
        this.addressesByIdAddress = addressesByIdAddress;
    }

    public LicensesEntity getLicensesByIdLicense() {
        return licensesByIdLicense;
    }

    public void setLicensesByIdLicense(LicensesEntity licensesByIdLicense) {
        this.licensesByIdLicense = licensesByIdLicense;
    }

    public DiveSitesEntity getDiveSitesByIdDiveSite() {
        return diveSitesByIdDiveSite;
    }

    public void setDiveSitesByIdDiveSite(DiveSitesEntity diveSitesByIdDiveSite) {
        this.diveSitesByIdDiveSite = diveSitesByIdDiveSite;
    }

    public EventCategoriesEntity getEventCategoriesByIdEventCategory() {
        return eventCategoriesByIdEventCategory;
    }

    public void setEventCategoriesByIdEventCategory(EventCategoriesEntity eventCategoriesByIdEventCategory) {
        this.eventCategoriesByIdEventCategory = eventCategoriesByIdEventCategory;
    }

    public Collection<UserEventsEntity> getUserEventsByIdEvent() {
        return userEventsByIdEvent;
    }

    public void setUserEventsByIdEvent(List<UserEventsEntity> userEventsByIdEvent) {
        this.userEventsByIdEvent = userEventsByIdEvent;
    }

}
