package org.lin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.lin.entity.bo.*;
import org.lin.entity.req.OrderQuery;
import org.lin.entity.req.OrderSave;
import org.lin.entity.req.OrderUpdate;
import org.lin.entity.vo.PageListVO;
import org.lin.enums.MenuStatusEnum;
import org.lin.enums.OrderStatusEnum;
import org.lin.mapper.OrderMapper;
import org.lin.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lin.utils.AssertUtils;
import org.lin.utils.ThreadLocalUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author lw
 * @since 2024-01-24
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    @Resource
    private IMenuService menuService;

    @Resource
    private IOrderMenuRelService orderMenuRelService;

    @Resource
    private IShopService shopService;
    @Resource
    private IShopUserRelService userRelService;

    @Transactional
    @Override
    public Integer save(OrderSave save) {
        AssertUtils.isFalse(CollectionUtils.isEmpty(save.getMenuIds()), 400, "请选择菜品");
        Order order = new Order();
        order.setShopId(save.getShopId());
        synchronized (this) {
            Map<Integer, Menu> map = menuService.listByIds(save.getMenuIds()).stream().collect(Collectors.toMap(Menu::getId, menu -> menu));
            save.getMenuIds().forEach(menuId -> {
                if (!map.containsKey(menuId)) {
                    throw new RuntimeException("菜品不存在");
                }
                Menu menu = map.get(menuId);
                if (menu.getStatus() == MenuStatusEnum.SOLD_OUT) {
                    throw new RuntimeException(menu.getName() + " 已售罄");
                }
                menu.setSoldNum(menu.getSoldNum() + 1);
            });
            order.setCreatorId(ThreadLocalUtil.getUser().getId());
            order.setStatus(OrderStatusEnum.WAIT_RECEIVE);
            save(order);

            orderMenuRelService.saveBatch(save.getMenuIds().stream().map(menuId -> {
                OrderMenuRel orderMenuRel = new OrderMenuRel();
                orderMenuRel.setOrderId(order.getId());
                orderMenuRel.setMenuId(menuId);
                return orderMenuRel;
            }).collect(Collectors.toList()));

            menuService.updateBatchById(map.values());
        }

        return order.getId();
    }

    @Override
    public Boolean cancel(Integer id) {
        Order order = getById(id);
        AssertUtils.notNull(order, 400, "订单不存在");
        AssertUtils.isTrue(Objects.equals(ThreadLocalUtil.getUser().getId(), order.getCreatorId()), 400, "无权限"); ;
        order.setStatus(OrderStatusEnum.CANCEL_WAIT_ACK);
        updateById(order);
        return true;
    }

    @Override
    public Boolean update(OrderUpdate update) {
        Integer userId = ThreadLocalUtil.getUser().getId();
        Order order = getById(update.getId());
        AssertUtils.notNull(order, 400, "订单不存在");

        Shop shop = shopService.getById(order.getShopId());
        AssertUtils.notNull(shop, 400, "店铺不存在");
        AtomicBoolean res = new AtomicBoolean(false);
        userRelService.list(new LambdaQueryWrapper<ShopUserRel>().eq(ShopUserRel::getShopId, order.getShopId())).forEach(shopUserRel -> {
            if (shopUserRel.getUserId().equals(userId)) {
                order.setStatus(OrderStatusEnum.getByCode(update.getStatus()));
                updateById(order);
                res.set(true);
            }
        });
        AssertUtils.isTrue(res.get(), 400, "无权限");
        return res.get();
    }

    @Override
    public PageListVO<Order> list(OrderQuery query) {
        Integer userId = ThreadLocalUtil.getUser().getId();
        Shop shop = shopService.getById(query.getShopId());
        AssertUtils.notNull(shop, 400, "店铺不存在");
        Set<Integer> set = userRelService.list(new LambdaQueryWrapper<ShopUserRel>().eq(ShopUserRel::getShopId, query.getShopId()))
                .stream().map(ShopUserRel::getUserId).collect(Collectors.toSet());
        AssertUtils.isTrue(set.contains(userId), 400, "无权限");

        Page<Order> page = page(new Page<>(query.getPage(), query.getPageSize()), new LambdaQueryWrapper<Order>().eq(Order::getShopId, query.getShopId())
                .eq(Order::getStatus, OrderStatusEnum.getByCode(query.getStatus())));

        PageListVO<Order> res = new PageListVO<>();
        res.setData(page.getRecords());
        res.setTotal(page.getTotal());
        res.setCurrentPage(query.getPage());
        res.setPageSize(query.getPageSize());
        return res;
    }
}
