package dsr.amm.homebudget.service;

import dsr.amm.homebudget.OrikaMapper;
import dsr.amm.homebudget.data.dto.CurrencyDTO;
import dsr.amm.homebudget.data.entity.Currency;
import dsr.amm.homebudget.data.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

// Currency service
@Service
public class CurrencyService {

    @Autowired
    private CurrencyRepository repository;

    @Autowired
    private OrikaMapper mapper;

    // Get currency method
    @Transactional
    public List<CurrencyDTO> getCurrencies() {
        Iterable<Currency> t = repository.findAll();
        return mapper.mapAsList(t, CurrencyDTO.class);
    }

    // Create currency method
    @Transactional
    public void create(CurrencyDTO curr) {
       repository.save(mapper.map(curr, Currency.class));
    }

    // Delete currency method
    @Transactional
    public void delete(CurrencyDTO curr) {
        repository.delete(mapper.map(curr, Currency.class));
    }
}