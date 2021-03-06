package com.example.demo.controllerInterface;

import com.example.demo.domain.BeSharedItem;
import com.example.demo.domain.Order;
import com.example.demo.domain.ShareItem;
import com.example.demo.domain.ShareRule;
import com.example.demo.domain.po.ShareRulePo;
import org.apache.tomcat.util.http.ResponseUtil;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;


/**
 * @Author xyt
 * @create 2019/12/4 13:00
 */

@RestController
@RequestMapping("/")
public interface ShareController {
    /**
     * 查看分享规则 by id
     * @param id
     * @return shareRules
     */
    @GetMapping("/goods/{id}/shareRules")
    public Object list(HttpServletRequest request, @PathVariable Integer id) throws UnknownHostException;
    /**
     * 新建分享规则
     * @param shareRule
     * @return shareRules
     */
    @PostMapping("/shareRules")
    public Object create(HttpServletRequest request,@RequestBody ShareRulePo shareRule) throws UnknownHostException;
    /**
     * 删除分享规则 by id
     * @param id
     * @return
     */
    @DeleteMapping("/shareRules/{id}")
    public Object delete(HttpServletRequest request,@PathVariable Integer id) throws UnknownHostException;

    /**
     * 修改分享规则 by id
     * @param shareRule
     * @param id
     * @return shareRule
     */
    @PutMapping("/shareRules/{id}")
    public Object update(HttpServletRequest request,@RequestBody ShareRulePo shareRule, @PathVariable Integer id) throws UnknownHostException;

    /**
     * 增加某个用户的被分享表
     * 用户被分享时系统为其创建被分享表，用户id在状态里，分享者id在二维码里
     * @param beSharedItem
     * @return beSharedItem
     */
    @PostMapping("/beSharedItems")
    public Object createSharedItems(HttpServletRequest request,@RequestBody BeSharedItem beSharedItem);

    /**
     *计算返点
     * @param order
     * @return  Integer
     */
    @GetMapping("/rebate")
    public Object calculateRebate(@RequestBody Order order);
}
