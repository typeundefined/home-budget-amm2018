package dsr.amm.homebudget.data.spec;

import dsr.amm.homebudget.data.entity.tx.Transaction;
import org.springframework.data.jpa.domain.Specification;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * Created by knekrasov on 02/12/2019.
 */
public class TxSpecs {
    public static Specification<Transaction> createdAfter(OffsetDateTime t) {
        return (root, query, cb) ->
                cb.greaterThan(root.get("createDate"), t);
    }

    public static Specification<Transaction> createdBefore(OffsetDateTime t) {
        return (root, query, cb) ->
                cb.lessThanOrEqualTo(root.get("createDate"), t);
    }

    public static Specification<Transaction> hasCategory(List<String> categoryNames) {
        return (root, query, cb) ->
                root.get("category").get("name").in(categoryNames);
    }
}
