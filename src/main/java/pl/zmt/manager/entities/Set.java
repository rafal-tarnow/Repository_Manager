package pl.zmt.manager.entities;

import javax.persistence.*;

@Entity
@Table(name = "sets", catalog = "zmt_data")
public class Set {

    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "set_set", joinColumns = @JoinColumn(name = "parent_id"), inverseJoinColumns = @JoinColumn(name = "child_id"))
    private java.util.Set<Set> sets;

    public Set() {
    }

    public Set(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public java.util.Set<Set> getSets() {
        return sets;
    }

    public void setSets(java.util.Set<Set> sets) {
        this.sets = sets;
    }
}
