package com.example.chasa.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "events", schema = "chasa", catalog = "")
public class EventsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_event", nullable = false)
    private int idEvent;
    @Basic
    @Column(name = "date_time_event", nullable = false)
    private Timestamp dateTimeEvent;
    @Basic
    @Column(name = "max_num_people", nullable = false)
    private int maxNumPeople;
    @Basic
    @Column(name = "price", nullable = false, precision = 0)
    private double price;
    @Basic
    @Column(name = "id_address", nullable = false)
    private int idAddress;
    @Basic
    @Column(name = "id_license", nullable = true)
    private Integer idLicense;
    @Basic
    @Column(name = "id_dive_site", nullable = true)
    private Integer idDiveSite;
    @Basic
    @Column(name = "id_event_category", nullable = false)
    private int idEventCategory;
    @ManyToOne
    @JoinColumn(name = "id_address", referencedColumnName = "id_address", nullable = false)
    private AddressesEntity addressesByIdAddress;
    @ManyToOne
    @JoinColumn(name = "id_license", referencedColumnName = "id_license")
    private LicensesEntity licensesByIdLicense;
    @ManyToOne
    @JoinColumn(name = "id_dive_site", referencedColumnName = "id_dive_site")
    private DiveSitesEntity diveSitesByIdDiveSite;
    @ManyToOne
    @JoinColumn(name = "id_event_category", referencedColumnName = "id_event_category", nullable = false)
    private EventCategoriesEntity eventCategoriesByIdEventCategory;
    @OneToMany(mappedBy = "eventsByIdEvent")
    private Collection<UserEventsEntity> userEventsByIdEvent;

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

    public int getIdAddress() {
        return idAddress;
    }

    public void setIdAddress(int idAddress) {
        this.idAddress = idAddress;
    }

    public Integer getIdLicense() {
        return idLicense;
    }

    public void setIdLicense(Integer idLicense) {
        this.idLicense = idLicense;
    }

    public Integer getIdDiveSite() {
        return idDiveSite;
    }

    public void setIdDiveSite(Integer idDiveSite) {
        this.idDiveSite = idDiveSite;
    }

    public int getIdEventCategory() {
        return idEventCategory;
    }

    public void setIdEventCategory(int idEventCategory) {
        this.idEventCategory = idEventCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventsEntity that = (EventsEntity) o;

        if (idEvent != that.idEvent) return false;
        if (maxNumPeople != that.maxNumPeople) return false;
        if (Double.compare(that.price, price) != 0) return false;
        if (idAddress != that.idAddress) return false;
        if (idEventCategory != that.idEventCategory) return false;
        if (dateTimeEvent != null ? !dateTimeEvent.equals(that.dateTimeEvent) : that.dateTimeEvent != null)
            return false;
        if (idLicense != null ? !idLicense.equals(that.idLicense) : that.idLicense != null) return false;
        if (idDiveSite != null ? !idDiveSite.equals(that.idDiveSite) : that.idDiveSite != null) return false;

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
        result = 31 * result + idAddress;
        result = 31 * result + (idLicense != null ? idLicense.hashCode() : 0);
        result = 31 * result + (idDiveSite != null ? idDiveSite.hashCode() : 0);
        result = 31 * result + idEventCategory;
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

    public void setUserEventsByIdEvent(Collection<UserEventsEntity> userEventsByIdEvent) {
        this.userEventsByIdEvent = userEventsByIdEvent;
    }
}
