package org.lin.service.impl;

import cn.hutool.core.stream.CollectorUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.lin.entity.bo.*;
import org.lin.entity.dto.OrderWithMenu;
import org.lin.entity.dto.ShopWithRel;
import org.lin.entity.req.MenuOrder;
import org.lin.entity.req.OrderQuery;
import org.lin.entity.req.OrderSave;
import org.lin.entity.req.OrderUpdate;
import org.lin.entity.vo.PageListVO;
import org.lin.entity.vo.order.OrderListVO;
import org.lin.entity.vo.order.OrderVO;
import org.lin.enums.MenuStatusEnum;
import org.lin.enums.OrderStatusEnum;
import org.lin.exception.BussinessException;
import org.lin.mapper.OrderMapper;
import org.lin.mapper.OrderMenuRelMapper;
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

    @Resource
    private OrderMapper orderMapper;


    @Resource
    private OrderMenuRelMapper orderMenuRelMapper;

    @Transactional
    @Override
    public Integer save(OrderSave save) {
        AssertUtils.isFalse(CollectionUtils.isEmpty(save.getMenuIds()), 400, "请选择菜品");
        Order order = new Order();
        order.setShopId(save.getShopId());
        synchronized (this) {
            List<Integer> menuIds = save.getMenuIds().stream().map(MenuOrder::getId).collect(Collectors.toList());
            Map<Integer, Integer> menuQuantityMap = save.getMenuIds().stream().collect(Collectors.toMap(MenuOrder::getId, MenuOrder::getQuantity));
            Map<Integer, Menu> map = menuService.listByIds(menuIds).stream().collect(Collectors.toMap(Menu::getId, menu -> menu));

            menuIds.forEach(menuId -> {
                if (!map.containsKey(menuId)) {
                    throw new BussinessException(20000,"商品不存在");
                }
                Menu menu = map.get(menuId);
                if (menu.getStatus() == MenuStatusEnum.SOLD_OUT) {
                    throw new BussinessException(4022,"商品: " + menu.getName() + " 已售罄");
                }
                menu.setSoldNum(menu.getSoldNum() + menuQuantityMap.get(menuId));
            });
            order.setCreatorId(ThreadLocalUtil.getUser().getId());
            order.setStatus(OrderStatusEnum.WAIT_RECEIVE);
            save(order);

            orderMenuRelService.saveBatch(save.getMenuIds().stream().map(MenuOrder::getId).collect(Collectors.toList()).stream().map(menuId -> {
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
    public PageListVO<OrderWithMenu> list(OrderQuery query) {
        Integer userId = ThreadLocalUtil.getUser().getId();
        Shop shop = shopService.getById(query.getShopId());
        AssertUtils.notNull(shop, 400, "店铺不存在");
        Set<Integer> set = userRelService.list(new LambdaQueryWrapper<ShopUserRel>().eq(ShopUserRel::getShopId, query.getShopId()))
                .stream().map(ShopUserRel::getUserId).collect(Collectors.toSet());
        AssertUtils.isTrue(set.contains(userId), 400, "无权限");

        List<OrderWithMenu> records = orderMenuRelMapper.getOrderWithMenuList(query);
        Long total = orderMapper.totalCount(query);
        Long totalPage = total % query.getPageSize() == 0 ? total / query.getPageSize() : total / query.getPageSize() + 1;

        PageListVO<OrderWithMenu> res = new PageListVO<>();
        res.setData(records  );
        res.setTotal(total);
        res.setCurrentPage(query.getPage());
        res.setPageSize(records.size());
        res.setTotalPage(totalPage);
        return res;
    }

    @Override
    public OrderWithMenu get(Integer id) {

        OrderWithMenu orderWithMenu = orderMenuRelService.getOrderWithMenu(id);

        AssertUtils.notNull(orderWithMenu, 5000,"订单不存在");
        ShopWithRel shop = shopService.getShopWithRelById(orderWithMenu.getShopId());
        AssertUtils.isFalse(CollectionUtils.isEmpty(shop.getUserIds()),4321, "商铺权限异常");
        Integer userId = ThreadLocalUtil.getUser().getId();
        AssertUtils.isTrue(Objects.equals(userId, orderWithMenu.getCreatorId()) || shop.getUserIds().contains(userId), 4120,"无权限");

        return orderWithMenu;
    }
}
