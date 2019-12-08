package ooad33.order.domain.po;

import ooad33.order.domain.Order;
import ooad33.order.domain.OrderItem;

import java.util.ArrayList;

/**
 * @Author lsz
 * @create 2019/12/7 20:03
 */
public class OrderPo{

    Order order;

    ArrayList<OrderItemPo> orderItemPos;

    /*******************************************
     *      生成的代码
     *******************************************/

    public OrderPo(Order order, ArrayList<OrderItemPo> orderItemPos) {
        this.order = order;
        this.orderItemPos = orderItemPos;
    }
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public ArrayList<OrderItemPo> getOrderItemPos() {
        return orderItemPos;
    }

    public void setOrderItemPos(ArrayList<OrderItemPo> orderItemPos) {
        this.orderItemPos = orderItemPos;
    }
}
