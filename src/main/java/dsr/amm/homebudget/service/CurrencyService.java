package dsr.amm.homebudget.service;

import dsr.amm.homebudget.OrikaMapper;
import dsr.amm.homebudget.data.dto.CurrencyDTO;
import dsr.amm.homebudget.data.entity.Currency;
import dsr.amm.homebudget.data.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CurrencyService {
    @Autowired
    private CurrencyRepository repository;

    @Autowired
    private OrikaMapper mapper;

    @Transactional
    public List<CurrencyDTO> getCurrencies() {
        Iterable<Currency> t = repository.findAll();
        return mapper.mapAsList(t, CurrencyDTO.class);
    }

    @Transactional
    public void create(CurrencyDTO curr) {
       repository.save(mapper.map(curr, Currency.class));
    }
}