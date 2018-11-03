package com.nastryair.project.hotelmanager.service;

import com.alibaba.fastjson.JSON;
import com.nastryair.project.hotelmanager.common.RestMessage;
import com.nastryair.project.hotelmanager.common.config.UserCenterConf;
import com.nastryair.project.hotelmanager.common.contant.CodeConstant;
import com.nastryair.project.hotelmanager.dto.MessageDto;
import com.nastryair.project.hotelmanager.entity.MessageEntity;
import com.nastryair.project.hotelmanager.respository.MessageRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.nastryair.project.hotelmanager.common.contant.MessageTypeConstant.getBusinessTypeByType;


@Service
public class MessageService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserCenterConf.MODULE_NAME);
    final
    MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }


    public RestMessage create(MessageDto messageDto) {
        RestMessage restMessage = new RestMessage();
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setSender(messageDto.getSender());
        messageEntity.setSequence(messageDto.getSequence());
        messageEntity.setCode(messageDto.getCode());
        messageEntity.setType(messageDto.getType());
        messageEntity.setTitle(messageDto.getTitle());
        messageEntity.setBusinessType(getBusinessTypeByType(messageDto.getType()));
        messageEntity.setContent(messageDto.getContent());
        messageEntity.setRecipient(messageDto.getRecipient());
        messageEntity.setMessageId(UUID.randomUUID().toString());
        messageEntity.setStatus("NEW");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        messageEntity.setCreateTime(sdf.format(new Date()));
        try {
            messageRepository.save(messageEntity);
            restMessage.setData(messageEntity.getMessageId());
            restMessage.setCode(CodeConstant.SUCCESS.getCode());
        } catch (Exception e) {
            LOGGER.error(ExceptionUtils.getMessage(e));
            restMessage.setCode(CodeConstant.FAIL.getCode());
        }
        return restMessage;
    }


    public RestMessage delete(String messageId) {
        RestMessage restmessage = new RestMessage();
        try {
            MessageEntity messageEntity = messageRepository.findByMessageId(messageId);
            if (messageEntity == null) {
                restmessage.setCode(CodeConstant.RECORD_NOT_FOUND.getCode());
            } else {
                messageRepository.deleteByMessageId(messageId);
                restmessage.setCode(CodeConstant.SUCCESS.getCode());
            }
        } catch (Exception e) {
            LOGGER.error(ExceptionUtils.getMessage(e));
            restmessage.setCode(CodeConstant.FAIL.getCode());
        }
        return restmessage;
    }


    public RestMessage deleteAll(String recipient) {
        RestMessage restMessage = new RestMessage();
        try {
            messageRepository.deleteAllByRecipient(recipient);
            restMessage.setCode(CodeConstant.SUCCESS.getCode());
        } catch (Exception e) {
            LOGGER.error(ExceptionUtils.getMessage(e));
            restMessage.setCode(CodeConstant.FAIL.getCode());
        }
        return restMessage;
    }


    public RestMessage markRead(String messageId) {
        RestMessage restMessage = new RestMessage();
        try {
            MessageEntity messageEntity = messageRepository.findByMessageId(messageId);
            if (messageEntity == null) {
                restMessage.setCode(CodeConstant.RECORD_NOT_FOUND.getCode());
            } else {
                messageRepository.setRead(messageId);
                restMessage.setCode(CodeConstant.SUCCESS.getCode());
            }
        } catch (Exception e) {
            LOGGER.error(ExceptionUtils.getMessage(e));
            restMessage.setCode(CodeConstant.FAIL.getCode());
        }
        return restMessage;
    }


    public RestMessage markReadAll(String recipient) {
        RestMessage restMessage = new RestMessage();
        try {
            messageRepository.setAllRead(recipient);
            restMessage.setCode(CodeConstant.SUCCESS.getCode());
        } catch (Exception e) {
            LOGGER.error(ExceptionUtils.getMessage(e));
            restMessage.setCode(CodeConstant.FAIL.getCode());
        }
        return restMessage;
    }


    public RestMessage list(String type, String businessType, String title, String status, String recipient, String order, String orderColumn, String currentPage, String pageSize) {
        RestMessage message = new RestMessage();
        Page<MessageEntity> page;

        try {
            Sort.Direction direction = "ASC".equals(order.toUpperCase()) ? Sort.Direction.ASC : Sort.Direction.DESC;
            Pageable pageable = PageRequest.of(
                    Integer.valueOf(currentPage),
                    Integer.valueOf(pageSize),
                    Sort.by(direction, orderColumn));
            Specification specification = (Specification) (root, criteriaQuery, criteriaBuilder) -> {
                List<Predicate> predicates = new ArrayList<>();
                if (StringUtils.isNotEmpty(type)) {
                    predicates.add(criteriaBuilder.equal(root.get("type"), type));
                }
                if (StringUtils.isNotEmpty(businessType)) {
                    predicates.add(criteriaBuilder.equal(root.get("businessType"), businessType));
                }
                if (StringUtils.isNotEmpty(title)) {
                    predicates.add(criteriaBuilder.equal(root.get("title"), title));
                }
                if (StringUtils.isNotEmpty(status)) {
                    predicates.add(criteriaBuilder.equal(root.get("status"), status));
                }
                if (StringUtils.isNotEmpty(recipient)) {
                    predicates.add(criteriaBuilder.equal(root.get("recipient"), recipient));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            };

            page = messageRepository.findAll(specification, pageable);
            message.setData(page);
        } catch (Exception e) {
            LOGGER.error(ExceptionUtils.getMessage(e));
            return null;
        }
        return message;
    }


    public RestMessage getBySequence(String sequence) {
        RestMessage message = new RestMessage();
        try {
            Pageable pageable = PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "createTime"));
            ArrayList<MessageEntity> messageEntityArrayList = (ArrayList<MessageEntity>) messageRepository.findMessageBySequence(sequence, pageable);
            if (messageEntityArrayList.size() == 0) {
                message.setCode(CodeConstant.RECORD_NOT_FOUND.getCode());
            } else {
                message.setCode(CodeConstant.SUCCESS.getCode());
                message.setData(messageEntityArrayList.get(0));
            }
        } catch (Exception e) {
            LOGGER.error(ExceptionUtils.getMessage(e));
            message.setCode(CodeConstant.FAIL.getCode());
        }
        return message;
    }


    public RestMessage deleteBatch(String idListJson) {
        RestMessage restMessage = new RestMessage();
        List<String> successList = new ArrayList<>();
        List<String> failList = new ArrayList<>();
        List<MessageEntity> toDeleteList = new ArrayList<>();
        List<String> idList = JSON.parseArray(idListJson, String.class);

        for (String messageId : idList) {
            MessageEntity messageEntity = messageRepository.findByMessageId(messageId);
            if (messageEntity == null) {
                failList.add(messageId);
            } else {
                toDeleteList.add(messageEntity);
                successList.add(messageId);
            }
        }
        restMessage.setCode(CodeConstant.SUCCESS.getCode());
        try {
            messageRepository.deleteAll(toDeleteList);
        } catch (Exception e) {
            LOGGER.error(ExceptionUtils.getMessage(e));
            restMessage.setCode(CodeConstant.FAIL.getCode());
            return restMessage;
        }
        Map<String, Object> map = new HashMap<>(2);
        map.put("successList", successList);
        map.put("failList", failList);
        restMessage.setData(map);
        return restMessage;
    }


    public RestMessage info(String id) {
        RestMessage message = new RestMessage();
        try {
            MessageEntity messageEntity = messageRepository.findByMessageId(id);
            if (messageEntity == null) {
                message.setCode(CodeConstant.RECORD_NOT_FOUND.getCode());
            } else {
                message.setData(messageEntity);
                message.setCode(CodeConstant.SUCCESS.getCode());
            }
        } catch (Exception e) {
            LOGGER.error(ExceptionUtils.getMessage(e));
            message.setCode(CodeConstant.FAIL.getCode());
        }
        return message;
    }
}
