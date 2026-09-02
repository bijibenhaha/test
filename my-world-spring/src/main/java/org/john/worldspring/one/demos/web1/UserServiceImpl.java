package org.john.worldspring.one.demos.web1;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private AccountMapper accountMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createUserWithAccount(User user, Account account) {
        // 1. 新增用户
        save(user);

        // 2. 模拟异常，验证是否回滚
        int i = 1 / 0;

        // 3. 新增账户记录
        account.setUserId(user.getId());
        accountMapper.insert(account);
    }
}
