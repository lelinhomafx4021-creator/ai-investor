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
 * 交易记录表
 * </p>
 *
 * @author yjx
 * @since 2026-01-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("trade_record")
public class TradeRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 股票代码
     */
    private String stockCode;
    /**
     * 交易类型:BUY/SELL
     */
    private String type;
    /**
     * 交易数量
     */
    private Integer quantity;
    /**
     * 成交价格
     */
    private BigDecimal price;
    /**
     * 成交金额
     */
    private BigDecimal amount;
    /**
     * 状态:SUCCESS/FAILED/PENDING
     */
    private String status;
    /**
     * 备注
     */
    private String remark;
    /**
     * 交易时间
     */
    private LocalDateTime createTime;


}
