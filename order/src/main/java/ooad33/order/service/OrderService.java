package ooad33.order.service;

import ooad33.order.dao.OrderDao;
import ooad33.order.domain.Order;
import ooad33.order.domain.po.OrderPo;
import ooad33.order.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * @Author lsz
 * @create 2019/12/6 20:54
 */
@Service
public class OrderService {

    @Resource
    OrderMapper orderMapper;

    @Autowired
    OrderDao orderDao;

    public ArrayList<OrderPo> findOrdersByUserId(Integer userId, Integer showType, Integer page, Integer limit, String sort, String order){
        return orderDao.findOrdersByUserId(userId,showType,page,limit);
    }

    public void addOrder(Order order){
        orderMapper.addOrder(order);
    }


    public void deletOrderById(Integer id) {
        orderMapper.deleteOrderById(id);
    }


}
