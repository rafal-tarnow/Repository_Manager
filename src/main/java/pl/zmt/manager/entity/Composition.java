package pl.zmt.manager.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "compositions", catalog = "zmt")
public class Composition {

    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "id_set")
    private Integer set;
    @Column(name = "id_repository")
    private Long repository;

    public Composition(){}

    public Composition(Integer set, Long repository) {
        this.set = set;
        this.repository = repository;
    }

    public Integer getSet() {
        return set;
    }

    public void setSet(Integer set) {
        this.set = set;
    }

    public Long getRepository() {
        return repository;
    }

    public void setRepository(Long repository) {
        this.repository = repository;
    }
}