package pl.zmt.manager.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "repozytoria", catalog = "zmt_data")
public class Repo {

    @Id
    @Column(name = "id")
    private long id;
    @Column(name = "indexRepo")
    private String indexRepo;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "localization")
    private String location;

    public Repo() {}

    public Repo(long id, String indexRepo, String name, String description, String location) {
        this.id = id;
        this.indexRepo = indexRepo;
        this.name = name;
        this.description = description;
        this.location = location;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIndexRepo() {
        return indexRepo;
    }

    public void setIndexRepo(String indexRepo) {
        this.indexRepo = indexRepo;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
