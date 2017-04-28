package com.lync.repository.primary;

import com.lync.domain.primary.Chatlog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by breeze on 2017/3/6.
 */
@Repository
public interface ChatLogRepository extends JpaRepository<Chatlog,Long> {
    @Query(value = "select * from t_chatlog t where instr(CONCAT(t.`to`,','),?1)>0  order by time desc limit ?2,?3",nativeQuery = true)
    List<Chatlog> findChatLogList(String username,Integer index, Integer end);

    @Query(value = "select count(id) from t_chatlog t where instr(CONCAT(t.`to`,','),?1)>0 ",nativeQuery = true)
    int findChatLogListTotal(String username);

}
