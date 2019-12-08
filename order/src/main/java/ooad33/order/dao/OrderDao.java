package ooad33.order.dao;

import ooad33.order.controller.GoodsController;
import ooad33.order.domain.Order;
import ooad33.order.domain.OrderItem;
import ooad33.order.domain.Product;
import ooad33.order.domain.po.OrderItemPo;
import ooad33.order.domain.po.OrderPo;
import ooad33.order.mapper.OrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * @Author lsz
 * @create 2019/12/7 20:06
 */
@Repository
public class OrderDao {

    static final Logger logger= LoggerFactory.getLogger(OrderDao.class);

    @Resource
    OrderMapper orderMapper;

    @Autowired
    GoodsController goodsController;

    public ArrayList<OrderPo> findOrdersByUserId(Integer userId, Integer showType, Integer page, Integer limit){

        ArrayList<OrderPo> orderPos=new ArrayList<>();

        ArrayList<Order> orders=orderMapper.findOrdersByUserId(userId,showType,page,limit);
        for(Order order:orders){
            ArrayList<OrderItem> orderItems=orderMapper.findOrderItemsByOrderId(order.getId());
            ArrayList<OrderItemPo> orderItemPos=new ArrayList<>();

            for(OrderItem orderItem:orderItems){
                Product product= goodsController.findProductById(orderItem.getProductId());
                OrderItemPo orderItemPo=new OrderItemPo(orderItem,product);

                logger.debug(orderItemPo.getProducts().toString());

                orderItemPos.add(orderItemPo);
            }

            OrderPo orderPo=new OrderPo(order,orderItemPos);
            orderPos.add(orderPo);
        }

        return orderPos;
    }


    public void deleteOrderById(Integer id){
        orderMapper.deleteOrderById(id);

        orderMapper.deleteOrderItemByOrderId(id);
    }

}
