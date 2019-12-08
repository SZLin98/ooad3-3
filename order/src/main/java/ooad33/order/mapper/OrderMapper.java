package ooad33.order.mapper;

import ooad33.order.domain.Order;
import ooad33.order.domain.OrderItem;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;

import java.util.ArrayList;

/**
 * @Author lsz
 * @create 2019/12/6 20:52
 */
@Mapper
public interface OrderMapper {
    /**
     * 根据页数查找
     * @param userId
     * @param showType
     * @param page
     * @param limit
     * @return
     */
    ArrayList<Order> findOrdersByUserId(@Param("userId") Integer userId,
                                        @Param("showType")Integer showType,
                                        @Param("page")Integer page,
                                        @Param("limit")Integer limit);

    /**
     * 根据order的id查看他的oderItem
     * @param OrderId
     * @return
     */
    ArrayList<OrderItem> findOrderItemsByOrderId(Integer OrderId);

    /**
     * 根据order的id查找order
     * @param id
     * @return
     */
    Order findOrderById(Integer id);

    /**
     * 增加order
     * @param order
     */
    void addOrder(Order order);

    /**
     * 增加orderItem
     * @param orderItems
     */
    void addOrderItems(ArrayList<OrderItem> orderItems);

    /**
     * 根据订单id逻辑删除订单
     * @param id
     */
    void deleteOrderById(Integer id);

    /**
     * 根据订单id逻辑删除orderItem
     * @param id
     */
    void deleteOrderItemByOrderId(Integer id);

    /**
     * 把订单发货，填入快递号和快递方式
     * @param orderId
     * @param shipSn
     * @param shipChannel
     */
    void updateOrderByShip(@Param("orderId") Integer orderId,@Param("shipSn") String shipSn,
                           @Param("shipChannel") String shipChannel);
}
