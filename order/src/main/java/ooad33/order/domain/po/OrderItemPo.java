package ooad33.order.domain.po;

import ooad33.order.domain.OrderItem;
import ooad33.order.domain.Product;

import java.util.ArrayList;

/**
 * @Author lsz
 * @create 2019/12/7 20:49
 */
public class OrderItemPo {
    OrderItem orderItem;
    Product product;

    /********************************
     * 生成的代码
     *********************************/

    public OrderItemPo(OrderItem orderItem, Product products) {
        this.orderItem = orderItem;
        this.product = products;
    }

    public OrderItem getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
    }

    public Product getProducts() {
        return product;
    }

    public void setProducts(Product products) {
        this.product = products;
    }
}
