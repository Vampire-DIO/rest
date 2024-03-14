package org.lin.service.impl;

import org.lin.entity.bo.MenuCategory;
import org.lin.enums.ResultCodeEnum;
import org.lin.entity.req.CategoryQuery;
import org.lin.entity.req.CategorySave;
import org.lin.entity.vo.PageListVO;
import org.lin.mapper.MenuCategoryMapper;
import org.lin.service.IMenuCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lin.service.IShopService;
import org.lin.utils.AssertUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lw
 * @since 2024-03-13
 */
@Service
public class MenuCategoryServiceImpl extends ServiceImpl<MenuCategoryMapper, MenuCategory> implements IMenuCategoryService {

    @Resource
    private IShopService shopService;

    @Resource
    private MenuCategoryMapper menuCategoryMapper;

    @Override
    public Integer save(CategorySave category) {

        AssertUtils.isTrue(shopService.getById(category.getShopId()) != null, ResultCodeEnum.SHOP_NOT_EXIST);

        MenuCategory menuCategory = new MenuCategory();
        menuCategory.setName(category.getName());
        menuCategory.setShopId(category.getShopId());
        menuCategory.setSort(category.getSort());
        save(menuCategory);

        return menuCategory.getId();
    }

    @Override
    public Boolean update(List<CategorySave> category) {
        if (CollectionUtils.isEmpty(category)){
            return true;
        }
        int shopId = category.get(0).getShopId();
        ArrayList<MenuCategory> list = new ArrayList<>();
        for (CategorySave categorySave : category) {
            AssertUtils.isTrue(shopId == categorySave.getShopId(), ResultCodeEnum.UPDATE_ERROR);
            shopId = categorySave.getShopId();
            MenuCategory menuCategory = new MenuCategory();
            menuCategory.setId(categorySave.getId());
            menuCategory.setName(categorySave.getName());
            menuCategory.setShopId(categorySave.getShopId());
            menuCategory.setSort(categorySave.getSort());
            list.add(menuCategory);
        }
        AssertUtils.isTrue(updateBatchById(list), ResultCodeEnum.UPDATE_ERROR);
        return true;
    }

    @Override
    public PageListVO<MenuCategory> queryList(CategoryQuery query) {
        long total = menuCategoryMapper.getTotal(query.getShopId());
        long totalPage = total % query.getPageSize() == 0 ? total / query.getPageSize() : total / query.getPageSize() + 1;
        List<MenuCategory> list = menuCategoryMapper.queryList(query);
        PageListVO<MenuCategory> listVO = new PageListVO<>();
        listVO.setData(list);
        listVO.setCurrentPage(query.getPage());
        listVO.setPageSize(list.size());
        listVO.setTotal(total);
        listVO.setTotalPage(totalPage);
        return listVO;
    }
}
