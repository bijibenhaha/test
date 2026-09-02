package org.john.worldspring.one.demos.web1;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private AccountMapper accountMapper;

    /**
     * 【事务生效版】直接调用，经过 AOP 代理，事务生效
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createUserWithAccount(User user, Account account) {
        save(user);
        // 模拟异常
        int i = 1 / 0;
        account.setUserId(user.getId());
        accountMapper.insert(account);
    }

    /**
     * 【事务失效版】通过 this 内部调用，绕过了 AOP 代理，@Transactional 被无视
     */
    @Override

    public void createUserWithAccountWrapper(User user, Account account) {
        // 等效于直接调用了原始对象的 createUserWithAccount()，没有经过代理
            this.createUserWithAccount(user, account);
    }
}
