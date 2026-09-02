package org.john.worldspring.one.demos.web1;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@TableName("t_user")
@Data
public class User {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @NotBlank(message = "username不能为空")
    @Size(max = 32, message = "username长度不能超过32")
    private String username;

    @NotNull(message = "age不能为空")
    @Min(value = 1, message = "age范围1-120")
    @Max(value = 120, message = "age范围1-120")
    private Integer age;

    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "phone格式不正确")
    private String phone;

    private LocalDateTime createTime;

    @TableLogic
    private int isDeleted;

    public User() {
    }


}
