package dsr.amm.homebudget

import dsr.amm.homebudget.data.dto.DepositTxDTO
import dsr.amm.homebudget.data.dto.WithdrawalTxDTO
import dsr.amm.homebudget.data.entity.tx.DepositTx
import dsr.amm.homebudget.data.entity.tx.WithdrawalTx
import ma.glasnost.orika.MapperFactory
import ma.glasnost.orika.impl.ConfigurableMapper
import org.springframework.stereotype.Component

@Component
class OrikaMapper : ConfigurableMapper() {
    override fun configure(factory: MapperFactory) {
        factory.registerClassMap(factory.classMap(DepositTx::class.java, DepositTxDTO::class.java).byDefault().toClassMap())
        factory.registerClassMap(factory.classMap(WithdrawalTx::class.java, WithdrawalTxDTO::class.java).byDefault().toClassMap())
        super.configure(factory)
    }
}
