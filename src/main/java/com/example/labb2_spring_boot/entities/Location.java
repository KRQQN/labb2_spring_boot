package com.example.labb2_spring_boot.entities;

import com.example.labb2_spring_boot.serializer.Point2dSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.geolatte.geom.G2D;
import org.geolatte.geom.Point;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Getter
@Setter
public class Location {

    @Id
    @Column(name = "location_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @Column(
            name = "location_name",
            nullable = false
    )
    private String name;

    @Column(name = "created_by")
    private String createdBy;

    private Boolean exposed = true;

    @Column(name = "location_description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "fk_category")
    private Category category;

    @Column(nullable = false)
    @JsonSerialize(using = Point2dSerializer.class)
    private Point<G2D> coordinate;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private final LocalDateTime created;

    @UpdateTimestamp
    private LocalDateTime modified;

    public Location() {
        this.created = LocalDateTime.now();
    }


    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Location location = (Location) o;
        return getID() != null && Objects.equals(getID(), location.getID());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
