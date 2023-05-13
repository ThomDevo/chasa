package com.example.chasa.entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "event_categories", schema = "chasa")
@NamedQueries({
        @NamedQuery(name = "EventCategory.findAll",query = "SELECT ec FROM EventCategoriesEntity ec "),
        @NamedQuery(name = "EventCategory.findById",query = "SELECT ec FROM EventCategoriesEntity ec WHERE ec.idEventCategory = :idEventCategory")
})
public class EventCategoriesEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_event_category", nullable = false)
    private int idEventCategory;

    @Basic
    @Column(name = "event_category_label", nullable = false, length = 255)
    private String eventCategoryLabel;

    @OneToMany(mappedBy = "eventCategoriesByIdEventCategory")
    private List<EventsEntity> eventsByIdEventCategory;

    public int getIdEventCategory() {
        return idEventCategory;
    }

    public void setIdEventCategory(int idEventCategory) {
        this.idEventCategory = idEventCategory;
    }

    public String getEventCategoryLabel() {
        return eventCategoryLabel;
    }

    public void setEventCategoryLabel(String eventCategoryLabel) {
        this.eventCategoryLabel = eventCategoryLabel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventCategoriesEntity that = (EventCategoriesEntity) o;

        if (idEventCategory != that.idEventCategory) return false;
        if (eventCategoryLabel != null ? !eventCategoryLabel.equals(that.eventCategoryLabel) : that.eventCategoryLabel != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idEventCategory;
        result = 31 * result + (eventCategoryLabel != null ? eventCategoryLabel.hashCode() : 0);
        return result;
    }

    public Collection<EventsEntity> getEventsByIdEventCategory() {
        return eventsByIdEventCategory;
    }

    public void setEventsByIdEventCategory(List<EventsEntity> eventsByIdEventCategory) {
        this.eventsByIdEventCategory = eventsByIdEventCategory;
    }

}
