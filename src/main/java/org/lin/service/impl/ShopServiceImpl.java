package org.lin.service.impl;

import org.lin.entity.bo.Shop;
import org.lin.entity.bo.ShopUserRel;
import org.lin.entity.bo.User;
import org.lin.entity.dto.ShopWithRel;
import org.lin.enums.ShopStatusEnum;
import org.lin.entity.req.ShopSave;
import org.lin.mapper.ShopMapper;
import org.lin.mapper.ShopUserRelMapper;
import org.lin.service.IShopService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lin.utils.AssertUtils;
import org.lin.utils.ThreadLocalUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lw
 * @since 2024-03-13
 */
@Service
public class ShopServiceImpl extends ServiceImpl<ShopMapper, Shop> implements IShopService {

    @Resource
    private ShopUserRelMapper shopUserRelMapper;


    @Override
    public Integer save(ShopSave save) {
        Shop shop = new Shop();
        shop.setName(save.getName());
        shop.setStatus(ShopStatusEnum.CLOSE);
        save(shop);

        User user = ThreadLocalUtil.getUser();
        ShopUserRel rel = new ShopUserRel();
        rel.setShopId(shop.getId());
        rel.setUserId(user.getId());
        shopUserRelMapper.insert(rel);

        return shop.getId();
    }

    @Override
    public ShopWithRel getShopWithRelById(Integer shopId) {
        AssertUtils.notNull(shopId, 4033, "商铺id为空");
        return shopUserRelMapper.getShopWithRelById(shopId);
    }
}
