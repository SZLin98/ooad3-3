package com.example.demo.vo;
import com.example.demo.domain.*;
import java.util.List;

public class CommentVo {
    /**
     * 评论信息
     */
    private  Comment comment;

    /**
     * 规格信息（包括组合商品）
     */
    private ProductVo productVo;

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public ProductVo getProductVo() {
        return productVo;
    }

    public void setProductVo(ProductVo productVo) {
        this.productVo = productVo;
    }
}
