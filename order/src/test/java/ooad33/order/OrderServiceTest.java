package ooad33.order;

import ooad33.order.domain.Order;
import ooad33.order.domain.po.OrderPo;
import ooad33.order.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * @Author lsz
 * @create 2019/12/7 16:27
 */
@SpringBootTest(classes = OrderApplication.class)
public class OrderServiceTest {

    @Autowired
    OrderService orderService;

    @Test
    void addOrderTest(){
        Order order=new Order();
        order.setUserId(1);
        order.setBeSharedItemIds(1);
        order.setOrderSn("123");
        order.setStatusCode((short) 3);
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
        order.setEndTime(LocalDateTime.MAX);
        order.setPayTime(LocalDateTime.MAX);
        order.setParentId(2);
        order.setAddress("xmu");
        order.setGmtCreate(LocalDateTime.now());
        order.setGmtModified(LocalDateTime.MAX);
        order.setBeDeleted(false);

        orderService.addOrder(order);
    }

    @Test
    void deleteOrderByIdTest(){
        ArrayList<OrderPo> orderPos=orderService.findOrdersByUserId(1,1,1,10,"1","1");

    }

}
