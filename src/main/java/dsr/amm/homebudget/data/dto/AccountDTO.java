package dsr.amm.homebudget.data.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

/**
 * Created by knekrasov on 10/15/2018.
 */
public class AccountDTO {
    private Long id;

    private String name;

    private String description;

    private CurrencyDTO currency;

    private OffsetDateTime createDate;

    private BigDecimal currentValue;


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

    public CurrencyDTO getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyDTO currency) {
        this.currency = currency;
    }

    public OffsetDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(OffsetDateTime createDate) {
        this.createDate = createDate;
    }

    public BigDecimal getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(BigDecimal currentValue) {
        this.currentValue = currentValue;
    }
}
