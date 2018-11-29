package dsr.amm.homebudget.data.dto;

import dsr.amm.homebudget.data.entity.Person;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 * Created by rifia on 11/29/2018.
 */


public class CategoryDTO {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String description;

    @ManyToOne
    private Person owner;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }
}
