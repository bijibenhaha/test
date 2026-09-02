package org.john.worldspring.one.demos.web1;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface UserService extends IService<User> {

    void createUserWithAccount(User user, Account account);

    void createUserWithAccountTransactionalFail(User user, Account account);

    List<User> queryTop10ByCondition();
}
