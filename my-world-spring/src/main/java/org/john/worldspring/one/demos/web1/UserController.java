package org.john.worldspring.one.demos.web1;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
/**
 * 题目1【SpringBoot项目搭建+CRUD】
 * 需求：实现用户管理简单接口
 * 实体User：id、username、age、phone、createTime
 * 1. 使用SpringBoot + MyBatis‑Plus搭建，使用MP内置BaseMapper、IService完成CRUD；
 * 2. 写接口：新增用户、根据id查询用户、分页查询用户列表（MP分页插件，必须配置分页拦截器）；
 * 3. 增加全局异常处理器 @RestControllerAdvice，统一捕获异常返回标准JSON；
 * 4. 增加参数校验：新增用户时username不能为空，age范围1‑120，参数非法返回错误提示。
 */


@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public R<User> add(@Valid @RequestBody User user) {
        if (!userService.save(user)) {
            return R.fail("新增用户失败");
        }
        return R.ok(user);
    }

    @GetMapping("/{id}")
    public R<User> get(@PathVariable long id) {
        User user = userService.getById(id);
        if (user == null) {
            return R.fail("用户不存在");
        }
        return R.ok(user);
    }

    @GetMapping("/page")
    public R<List<User>> page(
            @RequestParam(defaultValue = "1") int current,
            @RequestParam(defaultValue = "10") int size) {
        Page<User> page = userService.page(new Page<>(current, size));
        return R.ok(page.getRecords());
    }

    /**
     * 测试事务回滚（✅ 正常版）：通过代理调用，事务生效
     */
    @PostMapping("/with-account")
    public R<String> addWithAccount(@Valid @RequestBody User user) {
        Account account = new Account();
        account.setBalance(BigDecimal.ZERO);
        account.setCreateTime(LocalDateTime.now());

        userService.createUserWithAccount(user, account);

        return R.ok("用户和账户新增成功");
    }

    /**
     * 测试事务失效（❌ this.xxx 版）：内部调用绕过代理，事务不生效
     */
    @PostMapping("/with-account-fail")
    public R<String> addWithAccountFail(@Valid @RequestBody User user) {
        Account account = new Account();
        account.setBalance(BigDecimal.ZERO);
        account.setCreateTime(LocalDateTime.now());

        userService.createUserWithAccountTransactionalFail(user, account);

        return R.ok("用户和账户新增成功");
    }

    /**
     * LambdaQueryWrapper 查询：年龄>20，姓名不为空，按创建时间倒序，取前10条
     */
    @GetMapping("/top10")
    public R<List<User>> top10() {
        List<User> users = userService.queryTop10ByCondition();
        return R.ok(users);
    }
}
