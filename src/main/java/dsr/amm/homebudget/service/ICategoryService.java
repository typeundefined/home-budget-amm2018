package dsr.amm.homebudget.service;

import dsr.amm.homebudget.data.dto.CategoryDTO;

import java.util.List;

/**
 * @author Trokhin
 *
 */
public interface ICategoryService {

    /**
     *
     * @param categoryDTO - dto, передаваемое в запрос, которое надо создать и поместить в базу данных
     * @return - созданный объект
     */
    public CategoryDTO create(CategoryDTO categoryDTO);

    /**
     *
     * @param categoryDTO - который надо удалить
     * @return - удаленный объект
     */
    public CategoryDTO delete(CategoryDTO categoryDTO);

    /**
     *
     * @return список объектов хранящихся в базе данных
     */
    public List<CategoryDTO> getAll();

    /**
     *
     * @param id - id необходимого нам объекта
     * @return полученный объект
     */
    public CategoryDTO get(Long id);
}
