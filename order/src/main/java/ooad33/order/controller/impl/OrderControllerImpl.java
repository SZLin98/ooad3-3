package ooad33.order.controller.impl;

import ooad33.order.controller.OrderController;
import ooad33.order.domain.Order;
import ooad33.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author lsz
 * @create 2019/12/6 20:07
 */
@RestController
public class OrderControllerImpl implements OrderController {

    @Autowired
    OrderService orderService;

    /**
     * 用户获取订单列表/list
     */
    @Override
    public Object list(Integer userId, Integer showType, Integer page, Integer limit, String sort, String order) {

        orderService.findOrdersByUserId(userId,showType,page,limit,sort,order);

        return null;
    }

    @Override
    public Object userDetail(Integer userId, @NotNull Integer orderId) {
        return null;
    }

    @Override
    public Object submit(Integer userId, String body) {
        return null;
    }

    @Override
    public Object cancel(Integer userId, String body) {
        return null;
    }

    @Override
    public Object confirm(Integer userId, String body) {
        return null;
    }


    /**
     * 删除一个订单
     * @param body   订单信息，{ orderId：xxx }
     * @return
     */
    @Override
    public Object delete(Integer id) {
        orderService.deletOrderById(id);
        return null;
    }

    @Override
    public Object goods(Integer userId, @NotNull Integer orderId, @NotNull Integer goodsId) {
        return null;
    }

    @Override
    public Object comment(Integer userId, String body) {
        return null;
    }

    @Override
    public Object list(Integer userId, String orderSn, List<Short> orderStatusArray, Integer page, Integer limit, String sort, String order) {
        return null;
    }

//    @Override
//    public Object adminDetail(Integer id) {
//        return null;
//    }

    @Override
    public Object refund(String body, Integer id) {
        return null;
    }

    /**
     * 发货
     *
     * @param body 订单信息，{ orderId：xxx, shipSn: xxx, shipChannel: xxx }
     * @return 订单操作结果
     */
    @Override
    public Object ship(String body) {
        return null;
    }


}
