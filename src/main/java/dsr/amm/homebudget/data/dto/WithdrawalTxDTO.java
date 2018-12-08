package dsr.amm.homebudget.data.dto;

import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class WithdrawalTxDTO extends TransactionDTO {
    public WithdrawalTxDTO() {
        super();
        setType("withdrawal");
    }

    @Positive
    private BigDecimal amount;

    private CurrencyIdDTO currency;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public CurrencyIdDTO getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyIdDTO currency) {
        this.currency = currency;
    }
}
