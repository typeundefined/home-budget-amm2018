package dsr.amm.homebudget.data.spec;

import dsr.amm.homebudget.data.entity.tx.Transaction;
import org.springframework.data.jpa.domain.Specification;

import java.time.OffsetDateTime;

/**
 * Created by knekrasov on 02/12/2019.
 */
public class TxSpecs {
    public static Specification<Transaction> createdAfter(OffsetDateTime t) {
        return (root, query, cb) ->
                cb.greaterThan(root.get("createDate"), t);

    }
}
