package dsr.amm.homebudget;

import dsr.amm.homebudget.data.dto.DepositTxDTO;
import dsr.amm.homebudget.data.dto.WithdrawalTxDTO;
import dsr.amm.homebudget.data.entity.tx.DepositTx;
import dsr.amm.homebudget.data.entity.tx.WithdrawalTx;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class OrikaMapper extends ConfigurableMapper {
    @Override
    protected void configure(MapperFactory factory) {
        factory.registerClassMap(factory.classMap(WithdrawalTx.class, WithdrawalTxDTO.class).byDefault().toClassMap());
        factory.registerClassMap(factory.classMap(DepositTx.class, DepositTxDTO.class).byDefault().toClassMap());
        super.configure(factory);
    }
}
