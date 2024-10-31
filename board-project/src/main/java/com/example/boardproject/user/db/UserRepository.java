package com.example.boardproject.user.db;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    // アカウントの存在の有無をtrue, falseに返す
    // SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM UserEntity u WHERE u.account = :?t
    boolean existsByAccount(String account);

    // ログインする時、アカウントとパスワードでユーザーの情報を返す
    // select * from user where account = :? and password = :?
    UserEntity findByAccountAndPassword(String account, String password);
}
