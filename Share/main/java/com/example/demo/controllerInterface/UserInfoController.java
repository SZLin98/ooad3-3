package com.example.demo.controllerInterface;
import com.example.demo.domain.*;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

/**
 * AdminController
 *
 * @author YangHong
 * @date 2019-12-03
 */
@RestController
@RequestMapping("userInfoService")
public interface UserInfoController {
 /**
  * 查看用户是否合法By userId
  * @param id
  * @return shareRules
  */
 @GetMapping("/user/")
 public Object isValid(@PathVariable Integer id);
}
