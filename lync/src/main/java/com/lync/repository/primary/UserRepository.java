package com.lync.repository.primary;

import com.lync.domain.primary.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by breeze on 2017/2/21.
 */

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    @Modifying @Query(value = "update t_user u set u.statu = 0,u.modify_time =?1 where u.id = ?2",nativeQuery=true)
    int deleteUserById(Date modifyTime, Long id);

    @Query(value = "select * from t_user u where u.statu !=0 ORDER BY u.id desc limit ?1,?2",nativeQuery = true)
    List<User> findUserList(Integer index, Integer end);

    @Query(value = "select count(id) from t_user u where u.statu !=0 ORDER BY u.id desc",nativeQuery=true)
    int findUserListTotal();

    User findByUsernameAndStatu(String username,int statu);

    @Modifying @Query(value = "update t_user_role set role_id=?1 where user_id=?2",nativeQuery = true)
    int updateUserRole(Long role_id,Long user_id);

    @Modifying @Query(value = "insert into t_user_role (user_id,role_id) value (?1,?2)",nativeQuery = true)
    int addUserRole(Long user_id,Long role_id);


}
