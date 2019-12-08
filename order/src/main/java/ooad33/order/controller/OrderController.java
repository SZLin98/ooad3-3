package ooad33.order.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/order")// /wx/order

public interface OrderController {

    /**
     * 订单列表
     *
     * @param userId   用户ID
     * @param showType 订单信息
     * @param page     分页页数
     * @param limit     分页大小
     * @return 订单列表
     */
    @GetMapping("/orders")
    @ApiOperation(value = "用户获取订单列表/list", notes = "用户获取订单列表")
    public Object list(Integer userId,
                       @RequestParam(defaultValue = "0") Integer showType,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @RequestParam(defaultValue = "add_time") String sort,
                       @RequestParam(defaultValue = "desc") String order);

    /**
     * 订单详情
     *
     * @param userId  用户ID
     * @param orderId 订单ID
     * @return 订单详情
     */
    @GetMapping("/orders/{id}")
    @ApiOperation(value = "用户获取订单详情/detail", notes = "用户获取订单详情")
    public Object userDetail(Integer userId, @NotNull Integer orderId);

    /**
     * 提交订单
     *
     * @param userId 用户ID
     * @param body   订单信息，{ cartId：xxx, addressId: xxx, couponId: xxx, message: xxx, grouponRulesId: xxx,  grouponLinkId: xxx}
     * @return 提交订单操作结果
     */
    @PostMapping("/orders/{id}/submitResult")
    @ApiOperation(value = "提交订单操作结果/submit", notes = "提交订单操作结果")
    public Object submit(Integer userId, @RequestBody String body);

    /**
     * 取消订单
     *
     * @param userId 用户ID
     * @param body   订单信息，{ orderId：xxx }
     * @return 取消订单操作结果
     */
    @PostMapping("/orders/{id}/cancelResult")
    @ApiOperation(value = "取消订单操作结果/cancel", notes = "取消订单操作结果")
    public Object cancel(Integer userId, @RequestBody String body);



//    /**
//     * 微信付款成功或失败回调接口
//     * <p>
//     *  TODO
//     *  注意，这里pay-notify是示例地址，建议开发者应该设立一个隐蔽的回调地址
//     *
//     * @param request 请求内容
//     * @param response 响应内容
//     * @return 操作结果
//     */
//    @PostMapping("/orders/{id}/prepay")
//    @ApiOperation(value = "付款订单的预支付会话标识/prepay", notes = "付款订单的预支付会话标识")
//    public Object payPrepay(HttpServletRequest request, HttpServletResponse response);

//    /**
//     * 订单申请退款
//     *
//     * @param userId 用户ID
//     * @param body   订单信息，{ orderId：xxx }
//     * @return 订单退款操作结果
//     */
//    @PostMapping("/orders/{id}/refundResult")
//    @ApiOperation(value = "订单退款操作结果/refund", notes = "订单退款操作结果")
//    public Object refund(Integer userId, @RequestBody String body);

    /**
     * 确认收货
     *
     * @param userId 用户ID
     * @param body   订单信息，{ orderId：xxx }
     * @return 订单操作结果
     */
    @PostMapping("/orders/{id}/confirmResult")
    @ApiOperation(value = "确认收货订单操作结果/confirm", notes = "确认收货订单操作结果")
    public Object confirm(Integer userId, @RequestBody String body);

    /**
     * 删除订单
     * @param id
     * @return
     */
    @DeleteMapping("/orders/{id}")
    @ApiOperation(value = "删除一个订单/delete", notes = "删除一个订单")
    public Object delete( @PathVariable Integer id);

    /**
     * 待评价订单商品信息
     *
     * @param userId  用户ID
     * @param orderId 订单ID
     * @param goodsId 商品ID
     * @return 待评价订单商品信息
     */
    @GetMapping("/orders/{id}/waiteValuatedGoods/{id}")
    @ApiOperation(value = "待评价订单商品信息/goods", notes = "待评价订单商品信息")
    public Object goods(Integer userId,
                        @NotNull Integer orderId,
                        @NotNull Integer goodsId);

    /**
     * 评价订单商品
     *
     * @param userId 用户ID
     * @param body   订单信息，{ orderId：xxx }
     * @return 订单操作结果
     */
    @PostMapping("/orders/{id}/commentResult")
    @ApiOperation(value = "评价订单商品操作结果/comment", notes = "评价订单商品操作结果")
    public Object comment(Integer userId, @RequestBody String body);

    /**
     * 查询订单
     *
     * @param userId
     * @param orderSn
     * @param orderStatusArray
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @return
     */
    @GetMapping("/admins/orders")
    @ApiOperation(value="管理员查看订单列表  /list")
    public Object list(Integer userId, String orderSn,
                       @RequestParam(required = false) List<Short> orderStatusArray,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
//                       @Sort @RequestParam(defaultValue = "add_time") String sort,
//                       @Order @RequestParam(defaultValue = "desc") String order);
                       @RequestParam(defaultValue = "add_time") String sort,
                       @RequestParam(defaultValue = "desc") String order);
    /**
     * 订单详情
     *
     * @param id
     * @return
     */

//    @GetMapping("/orders/{id}")
//    @ApiOperation(value="管理员查看订单详情  /detail")
//    public Object adminDetail(@PathVariable Integer id);

    /**
     * 订单退款
     *
     * @param body 订单信息，{ orderId：xxx }
     * @return 订单退款操作结果
     */
    @PostMapping("/orders/{id}/refund")
    @ApiOperation(value="退款订单，管理员修改订单状态  /refund")
    public Object refund(@RequestBody String body, @PathVariable Integer id);

    /**
     * 发货
     *
     * @param body 订单信息，{ orderId：xxx, shipSn: xxx, shipChannel: xxx }
     * @return 订单操作结果
     */
    @PostMapping("/orders/{id}/ship")
    @ApiOperation(value="订单发货，管理员修改订单状态  /ship")
    public Object ship(@RequestBody String body);


}