package com.investor.entity.po;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 股票表
 * </p>
 *
 * @author yjx
 * @since 2026-01-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("stock")
public class Stock implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 股票代码
     */
    private String code;

    /**
     * 股票名称
     */
    private String name;

    /**
     * 当前价格
     */
    private BigDecimal currentPrice;

    /**
     * 今日开盘价
     */
    private BigDecimal openPrice;

    /**
     * 今日最高价
     */
    private BigDecimal highPrice;

    /**
     * 今日最低价
     */
    private BigDecimal lowPrice;

    /**
     * 昨日收盘价
     */
    private BigDecimal closePrice;

    /**
     * 成交量
     */
    private Long volume;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 逻辑删除
     */
    private Integer deleted;


}
