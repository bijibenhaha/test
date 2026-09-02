package org.john.worldspring.one.demos.web1;

import com.baomidou.mybatisplus.extension.service.IService;

public interface UserService extends IService<User> {

    void createUserWithAccount(User user, Account account);

    void createUserWithAccountWrapper(User user, Account account);
}
