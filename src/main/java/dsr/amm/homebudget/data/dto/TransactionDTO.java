package dsr.amm.homebudget.data.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

/**
 * Created by knekrasov on 10/23/2018.
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @Type(value = WithdrawalTxDTO.class, name = "withdrawal"),
        @Type(value = DepositTxDTO.class, name = "deposit"),
})
public abstract class TransactionDTO {
    private Long id;

    private String type;

    @NotNull
    private String reason;

    private OffsetDateTime createDate;

    // [KN] Not required when de-serializing
    private BigDecimal newValue;

    private CategoryDTO category;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public OffsetDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(OffsetDateTime createDate) {
        this.createDate = createDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getNewValue() {
        return newValue;
    }

    public void setNewValue(BigDecimal newValue) {
        this.newValue = newValue;
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }
}
