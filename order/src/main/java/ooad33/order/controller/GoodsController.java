package ooad33.order.controller;

import io.swagger.annotations.ApiOperation;
import ooad33.order.domain.Product;
import org.apache.tomcat.util.http.ResponseUtil;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * @Author 
 * @create 
 */

@RestController
@RequestMapping("/")
public interface GoodsController {

    @GetMapping("product/{id}")
    public Product findProductById(Integer productId);
    
}
