package ooad33.order;

import ooad33.order.domain.Order;
import ooad33.order.domain.OrderItem;
import ooad33.order.mapper.OrderMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author lsz
 * @create 2019/12/7 11:10
 */
@SpringBootTest(classes = OrderApplication.class)
public class OrderMapperTest {

    private static final Logger logger = LoggerFactory.getLogger(OrderMapper.class);

    Order order;

    @Autowired
    OrderMapper orderMapper;

        @BeforeEach
    void setUp(){
        order=new Order();
        order.setUserId(1);
        order.setBeSharedItemIds(1);
        order.setOrderSn("123");
        order.setStatusCode((short)3);
        order.setConsignee("lsz");
        order.setMobile("123456");
        order.setMessage("I am a test");
        order.setGoodPrice(BigDecimal.valueOf(12));
        order.setCouponPrice(BigDecimal.ONE);
        order.setRebatePrice(BigDecimal.ONE);
        order.setIntegralPrice(BigDecimal.ONE);
        order.setShipSn("555");
        order.setShipChannel("1");
        order.setShipTime(LocalDateTime.now());
        order.setConfirmTime(LocalDateTime.now());
        order.setEndTime(LocalDateTime.now());
        order.setPayTime(LocalDateTime.now());
        order.setParentId(2);
        order.setAddress("xmu");
        order.setGmtCreate(LocalDateTime.now());
        order.setGmtModified(LocalDateTime.now());
        order.setBeDeleted(false);

//        orderMapper.addOrder(order);
}

    @Test
    void addOrderTest(){
        orderMapper.addOrder(order);
    }

    @Test
    void addOrderItemTest(){
        OrderItem orderItem1=new OrderItem();
        orderItem1.setOrderId(1);
        OrderItem orderItem2=new OrderItem();
        orderItem2.setOrderId(2);
        ArrayList<OrderItem> orderItems=new ArrayList<>();
        orderItems.add(orderItem1);
        orderItems.add(orderItem2);
        for(int i=0;i<5;i++){
            orderMapper.addOrderItems(orderItems);
        }
        logger.debug(orderItem1.toString());
    }

    @Test
    void findOrderByUserIdTest(){
        ArrayList<Order> orders=orderMapper.findOrdersByUserId(1,1,1,10);
        for(Order order:orders){
            assertEquals(order.getUserId(),1);
            logger.debug(order.toString());
        }
    }

    @Test
    void findOrderItemsByOrderId(){
        ArrayList<OrderItem> orderItems=orderMapper.findOrderItemsByOrderId(1);
        for(OrderItem orderItem:orderItems){
            assertEquals(orderItem.getOrderId(),1);
            logger.debug(orderItem.toString());
        }
    }

    @Test
    void updateOrderShip(){
        orderMapper.updateOrderByShip(1,"456","shun feng");
        Order order=orderMapper.findOrderById(1);
        assertEquals("456",order.getShipSn());
        assertEquals("shun feng",order.getShipChannel());
    }

}
