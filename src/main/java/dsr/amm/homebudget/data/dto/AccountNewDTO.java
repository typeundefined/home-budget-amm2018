package dsr.amm.homebudget.data.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Created by knekrasov on 10/22/2018.
 */
public class AccountNewDTO {
    @NotEmpty
    @Length(max = 256)
    private String name;

    @Length(max = 10240)
    private String description;

    @NotNull
    private CurrencyIdDTO currency;

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

    public CurrencyIdDTO getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyIdDTO currency) {
        this.currency = currency;
    }
}
