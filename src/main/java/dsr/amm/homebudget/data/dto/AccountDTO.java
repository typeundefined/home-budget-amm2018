package dsr.amm.homebudget.data.dto;

import dsr.amm.homebudget.data.entity.Currency;
import dsr.amm.homebudget.data.entity.Person;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

/**
 * Created by knekrasov on 10/15/2018.
 */

// Account data transfer object
public class AccountDTO {

    private Double amount;

    private Long id;

    private String name;

    private String description;

    private Currency currency;

    private Person owner;

    private OffsetDateTime createDate;

    private BigDecimal currentValue;


    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(BigDecimal currentValue) {
        this.currentValue = currentValue;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
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

    public OffsetDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(OffsetDateTime createDate) {
        this.createDate = createDate;
    }
}
