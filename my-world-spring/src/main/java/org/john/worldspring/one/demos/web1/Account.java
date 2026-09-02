package org.john.worldspring.one.demos.web1;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@TableName("t_account")
@Data
public class Account {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long userId;

    private BigDecimal balance;

    private LocalDateTime createTime;

    @TableLogic
    private int isDeleted;

    // 这个要写，mp要用
    public Account() {}


}
