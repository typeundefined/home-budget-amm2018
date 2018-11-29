package dsr.amm.homebudget.data.dto;


/**
 * @author Trokhin
 *
 * Поле для ownerId пока не мапится, но оно пока и не нужно, его легко убрать если оно станет точно ненужным, а пока пусть будет.
 * Так же из-за невозможности его замапить, все создаваемые категории получаются с ownerId = null.
 */
public class CategoryDTO {
    private Long id;
    private String name;
    private String description;

    private Long ownerId;

    //id
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    //name
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    //description
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    //ownerId
    public Long getOwnerId() {
        return ownerId;
    }
    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }
}