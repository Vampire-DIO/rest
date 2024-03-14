package org.lin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.lin.entity.bo.Menu;
import org.lin.entity.bo.MenuCategoryRel;
import org.lin.entity.bo.Picture;
import org.lin.entity.vo.menu.MenuVO;
import org.lin.enums.MenuStatusEnum;
import org.lin.enums.ResultCodeEnum;
import org.lin.entity.req.MenuQuery;
import org.lin.entity.req.MenuSave;
import org.lin.entity.vo.PageListVO;
import org.lin.exception.BussinessException;
import org.lin.mapper.MenuCategoryRelMapper;
import org.lin.mapper.MenuMapper;
import org.lin.service.IMenuService;
import org.lin.service.IPictureService;
import org.lin.utils.AssertUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lw
 * @since 2024-03-13
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Resource
    private MenuMapper menuMapper;

    @Resource
    private IPictureService pictureService;

    @Resource
    private MenuCategoryRelMapper relMapper;

    @Override
    public MenuVO getMenuVOById(Integer id) {
        Menu menu = getById(id);
        AssertUtils.isFalse(menu == null, ResultCodeEnum.RESOURCE_NULL);

        MenuVO menuVO = new MenuVO();
        List<Picture> pictures = pictureService.list(new LambdaQueryWrapper<Picture>().eq(Picture::getMenuId, id));
        List<String> urls = pictures.stream().map(Picture::getUrl).collect(Collectors.toList());
        BeanUtils.copyProperties(menu,menuVO);
        menuVO.setPics(urls);
        return menuVO;
    }

    @Override
    public PageListVO<MenuVO> queryList(MenuQuery query) {
        long total = menuMapper.queryTotal(query.getMenuCategoryId());
        long totalPage = total % query.getPageSize() == 0 ? total / query.getPageSize() : total / query.getPageSize() + 1;
        List<Menu> list = menuMapper.queryList(query);
        Set<Integer> set = list.stream().map(Menu::getId).collect(Collectors.toSet());
        List<Picture> pictureList = pictureService.list(new LambdaQueryWrapper<Picture>().in(Picture::getMenuId, set));
        Map<Integer, List<String>> map = pictureList.stream().collect(Collectors.groupingBy(Picture::getMenuId,
                Collectors.mapping(Picture::getUrl,Collectors.toList())));

        ArrayList<MenuVO> resList = new ArrayList<>();
        list.forEach(f->{
            MenuVO menuVO = new MenuVO();
            BeanUtils.copyProperties(f,menuVO);
            menuVO.setPics(map.get(menuVO.getId()));
            menuVO.setCategoryId(query.getMenuCategoryId());
            resList.add(menuVO);
        });

        PageListVO<MenuVO> listVO = new PageListVO<>();
        listVO.setCurrentPage(query.getPage());
        listVO.setPageSize(list.size());
        listVO.setTotal(total);
        listVO.setTotalPage(totalPage);
        listVO.setData(resList);
        return listVO;
    }

    @Transactional
    @Override
    public Integer save(MenuSave req) {

        Menu menu = new Menu();
        menu.setName(req.getName());
        menu.setDescription(req.getDescription());
        menu.setSort(Integer.MAX_VALUE);
        menu.setPrice(req.getPrice());
        menu.setSoldNum(0);
        menu.setStatus(MenuStatusEnum.SOLD_OUT);
        menuMapper.insert(menu);

        relMapper.insert(MenuCategoryRel.builder().menuId(menu.getId()).categoryId(req.getMenuCategoryId()).build());
        if (!CollectionUtils.isEmpty(req.getPictures())) {
            List<Picture> list = pictureService.list(new LambdaQueryWrapper<Picture>().in(Picture::getId, req.getPictures()));
            list.forEach(p->{
                p.setMenuId(menu.getId());
            });
            if (!pictureService.updateBatchById(list)) {
                log.error("保存图片失败");
                throw new BussinessException(5000,"保存图片失败");
            }
        }
        return menu.getId();
    }

    @Transactional
    @Override
    public Integer update(MenuSave menu) {
        AssertUtils.isFalse(menu.getId() == null, ResultCodeEnum.ID_NOT_NULL);
        Menu old = getById(menu.getId());
        AssertUtils.isFalse(old == null, ResultCodeEnum.MENU_NOT_EXIST);
        old.setName(StringUtils.isNotEmpty(menu.getName()) ? menu.getName() : old.getName());
        old.setDescription(StringUtils.isNotEmpty(menu.getDescription()) ? menu.getDescription() : old.getDescription());
        old.setPrice(menu.getPrice() == null ? old.getPrice() : menu.getPrice());
        AssertUtils.isTrue(updateById(old), ResultCodeEnum.UPDATE_ERROR);

        QueryWrapper<Picture> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("menu_id", old.getId());
        List<Picture> list = pictureService.list(queryWrapper);
        List<Integer> oldPicIds = list.stream().map(Picture::getId).collect(Collectors.toList());
        oldPicIds.removeAll(menu.getPictures());
        Map<Integer, Picture> map = list.stream().collect(Collectors.toMap(Picture::getId, v -> v));
        List<Picture> deleteList = new ArrayList<>();
        for (Integer id : oldPicIds) {
            deleteList.add(map.get(id));
        }
        pictureService.deleteBatch(deleteList);
        List<Picture> newPictures = pictureService.listByIds(menu.getPictures());
        newPictures.forEach(p -> {
            p.setMenuId(old.getId());
        });
        if (!pictureService.updateBatchById(newPictures)) {
            log.error("保存图片失败");
        }
        updateById(old);
        return menu.getId();
    }
}
