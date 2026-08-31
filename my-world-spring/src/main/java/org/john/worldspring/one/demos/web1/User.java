package org.john.worldspring.one.demos.web1;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@TableName("t_user")
public class User {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @NotBlank(message = "username不能为空")
    @Size(max = 32, message = "username长度不能超过32")
    @TableField("username")
    private String username;



    @NotNull(message = "age不能为空")
    @Min(value = 1, message = "age范围1-120")
    @Max(value = 120, message = "age范围1-120")
    @TableField("age")
    private Integer age;

    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "phone格式不正确")
    @TableField("phone")
    private String phone;

    @TableField("create_time")
    private LocalDateTime createTime;

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}
