package dsr.amm.homebudget.data.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

public class CategoryNewDTO {

    @NotEmpty
    @Length(min = 1, max = 256)
    private String name;

    @NotEmpty
    @Length(max = 1024)
    private String description;

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
}
