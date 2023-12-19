package com.example.labb2_spring_boot.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

import java.util.*;

@Entity
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long ID;

    @Size(max = 255)
    @Column(
            name = "category_name",
            unique = true,
            nullable = false
    )
    private String name;

    @Size(max = 255)
    @Column(name = "category_description")
    private String description;

    private String emoji;

    @JsonIgnore
    @OneToMany(mappedBy = "category")
    @Column(name = "locations")
    private List<Location> locations = new ArrayList<>();

    public void addLocation(Location l) {
        this.locations.add(l);
        l.setCategory(this);
    }

    public void removeLocation(Location l) {
        this.locations.remove(l);
        l.setCategory(this);
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Category category = (Category) o;
        return getID() != null && Objects.equals(getID(), category.getID());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
