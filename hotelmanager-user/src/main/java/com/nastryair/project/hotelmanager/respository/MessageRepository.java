package com.nastryair.project.hotelmanager.respository;

import com.nastryair.project.hotelmanager.entity.MessageEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MessageRepository extends PagingAndSortingRepository<MessageEntity, Integer>, JpaSpecificationExecutor {
    @Query("SELECT o FROM MessageEntity o WHERE o.sequence = ?1")
    List<MessageEntity> findMessageBySequence(String sequence, Pageable pageable);

    MessageEntity findByMessageId(String id);

    @Modifying
    @Transactional
    int deleteByMessageId(String messageId);

    @Modifying
    @Transactional
    int deleteAllByRecipient(String recipient);

    @Modifying
    @Transactional
    @Query("update MessageEntity o set o.status = 'READ' where o.messageId = ?1")
    int setRead(String messageId);

    @Modifying
    @Transactional
    @Query("update MessageEntity o set o.status = 'READ' where o.recipient = ?1")
    int setAllRead(String recipient);
}
