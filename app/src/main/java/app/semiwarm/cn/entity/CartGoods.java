package app.semiwarm.cn.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 购物车商品
 * Created by alibct on 2017/5/25.
 */
@Entity
public class CartGoods {
    @Id
    private Long id; // 主键
    private Long goodsId; // 商品id
    private String goodsBanner; // 图片
    private String goodsTitle; // 标题
    private String goodsSpecParam; // 规格参数
    private String goodsPrice; // 价格
    private Integer goodsCount; // 数量

    @Generated(hash = 1719107769)
    public CartGoods(Long id, Long goodsId, String goodsBanner, String goodsTitle,
            String goodsSpecParam, String goodsPrice, Integer goodsCount) {
        this.id = id;
        this.goodsId = goodsId;
        this.goodsBanner = goodsBanner;
        this.goodsTitle = goodsTitle;
        this.goodsSpecParam = goodsSpecParam;
        this.goodsPrice = goodsPrice;
        this.goodsCount = goodsCount;
    }

    @Generated(hash = 1612149435)
    public CartGoods() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsBanner() {
        return goodsBanner;
    }

    public void setGoodsBanner(String goodsBanner) {
        this.goodsBanner = goodsBanner;
    }

    public String getGoodsTitle() {
        return goodsTitle;
    }

    public void setGoodsTitle(String goodsTitle) {
        this.goodsTitle = goodsTitle;
    }

    public String getGoodsSpecParam() {
        return goodsSpecParam;
    }

    public void setGoodsSpecParam(String goodsSpecParam) {
        this.goodsSpecParam = goodsSpecParam;
    }

    public String getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public Integer getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(Integer goodsCount) {
        this.goodsCount = goodsCount;
    }

    @Override
    public String toString() {
        return "CartGoods{" +
                "id=" + id +
                ", goodsId=" + goodsId +
                ", goodsBanner='" + goodsBanner + '\'' +
                ", goodsTitle='" + goodsTitle + '\'' +
                ", goodsSpecParam='" + goodsSpecParam + '\'' +
                ", goodsPrice='" + goodsPrice + '\'' +
                ", goodsCount=" + goodsCount +
                '}';
    }
}
