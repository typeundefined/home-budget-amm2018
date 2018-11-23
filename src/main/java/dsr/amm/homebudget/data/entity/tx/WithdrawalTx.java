package dsr.amm.homebudget.data.entity.tx;

import dsr.amm.homebudget.data.entity.Currency;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.math.BigDecimal;

/**
 * Created by knekrasov on 10/15/2018.
 */
@Entity
public class WithdrawalTx extends Transaction {
    private BigDecimal amount;

    @OneToOne
    private Currency currency;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
