package org.lin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lin.entity.bo.Menu;
import org.lin.entity.req.MenuQuery;
import org.lin.entity.req.MenuSave;
import org.lin.entity.vo.PageListVO;
import org.lin.exception.BussinessException;
import org.lin.mybatis.MenuMapper;
import org.lin.service.IMenuService;
import org.lin.service.IPictureService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
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
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Resource
    private MenuMapper menuMapper;

    @Resource
    private IPictureService pictureService;

    @Override
    public PageListVO<Menu> queryList(MenuQuery query) {
        List<Menu> list = menuMapper.queryList(query);
        PageListVO<Menu> listVO = new PageListVO<>();
        listVO.setCurrentPage(query.getPage());
        listVO.setPageSize(list.size());
        listVO.setData(list);
        return listVO;
    }

    @Transactional
    @Override
    public Integer save(MenuSave req) {

        Menu menu = new Menu();
        menu.setName(req.getName());
        menu.setDescription(req.getDescription());
        menu.setSort(Integer.MAX_VALUE);
        menuMapper.insert(menu);

        List<MultipartFile> pictures = req.getPictures();
        if (!CollectionUtils.isEmpty(pictures)) {
            boolean pic = pictureService.insertMany(pictures, menu.getId());
            if (!pic) {
                throw new BussinessException(500, "保存失败");
            }
        }

        return menu.getId();
    }
}
