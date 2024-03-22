package org.lin.service;

import org.lin.entity.bo.Shop;
import com.baomidou.mybatisplus.extension.service.IService;
import org.lin.entity.dto.ShopWithRel;
import org.lin.entity.req.ShopSave;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lw
 * @since 2024-03-13
 */
public interface IShopService extends IService<Shop> {

    Integer save(ShopSave save);

    ShopWithRel getShopWithRelById(Integer shopId);
}
