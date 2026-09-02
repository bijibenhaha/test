package org.john.worldspring.one.demos.web1;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private AccountMapper accountMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createUserWithAccount(User user, Account account) {
        save(user);
        int i = 1 / 0;
        account.setUserId(user.getId());
        accountMapper.insert(account);
    }

    public void createUserWithAccountTransactionalFail(User user, Account account) {
        this.createUserWithAccount(user, account);
    }

    @Override
    public List<User> queryTop10ByCondition() {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<User>()
                .gt(User::getAge, 20)                           // 年龄大于20
                .isNotNull(User::getUsername)                    // 姓名不为空
                .orderByDesc(User::getCreateTime);               // 按创建时间倒序

        Page<User> page = new Page<>(1, 10);
        Page<User> result = baseMapper.selectPage(page, wrapper);
        return result.getRecords();
    }
}
