package com.nastryair.project.hotelmanager.hotel.service;

import com.nastryair.project.hotelmanager.hotel.common.RestMessage;
import com.nastryair.project.hotelmanager.hotel.common.contant.CodeConstant;
import com.nastryair.project.hotelmanager.hotel.common.exception.BusinessException;
import com.nastryair.project.hotelmanager.hotel.entity.Hotel;
import com.nastryair.project.hotelmanager.hotel.repository.HotelRepository;
import com.nastryair.project.hotelmanager.hotel.util.ExceptionUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.Predicate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.nastryair.project.hotelmanager.hotel.common.config.HotelConf.MODULE_NAME;
import static com.nastryair.project.hotelmanager.hotel.util.FileUploadUtil.uploadImgToPath;

@Service
public class HotelService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MODULE_NAME);

    private final HotelRepository hotelRepository;

    @Autowired
    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public RestMessage getList(String title, String priceRange, String star, String theme, String order, String orderColumn, String currentPage, String pageSize) {
        RestMessage message = new RestMessage();

        Sort.Direction direction = "ASC".equals(order.toUpperCase()) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(
                Integer.valueOf(currentPage),
                Integer.valueOf(pageSize),
                Sort.by(direction, orderColumn));
        Page page;
        Specification specification = (Specification) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotEmpty(title)) {
                predicates.add(criteriaBuilder.like(root.get("title"), "%" + title + "%"));
            }
            if (StringUtils.isNotEmpty(priceRange)) {
                predicates.add(criteriaBuilder.equal(root.get("priceRange"), priceRange));
            }
            if (StringUtils.isNotEmpty(star)) {
                predicates.add(criteriaBuilder.equal(root.get("starRated"), star));
            }
            if (StringUtils.isNotEmpty(theme)) {
                predicates.add(criteriaBuilder.equal(root.get("theme"), theme));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        page = hotelRepository.findAll(specification, pageable);
        message.setData(page);
        message.setCode(CodeConstant.SUCCESS.getCode());
        return message;
    }

    public RestMessage getOne(String hotelId) {
        RestMessage message = new RestMessage();
        Hotel hotel = hotelRepository.findByHotelId(hotelId);
        if (hotel != null) {
            message.setData(hotel);
            message.setCode(CodeConstant.SUCCESS.getCode());
        } else {
            message.setCode(CodeConstant.RECORD_NOT_FOUND.getCode());
        }
        return message;
    }

    public RestMessage add(Hotel hotel) {
        RestMessage message = new RestMessage();

        Hotel sameHotel = hotelRepository.findByName(hotel.getName());
        if (sameHotel == null) {
            hotel.setHotelId(UUID.randomUUID().toString());
            hotel.setStatus("enable");
//                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                hotel.setCreateDate(new Date());
            message.setData(hotelRepository.save(hotel));
            message.setCode(CodeConstant.SUCCESS.getCode());
        } else {
            message.setCode(CodeConstant.RECORD_ALREADY_EXISTS.getCode());
        }

        return message;
    }

    public RestMessage uploadFile(MultipartFile file) {
        RestMessage message = new RestMessage();
        String imgPath = "";
        try {
            imgPath = uploadImgToPath(file, "categoryImg");
        } catch (Exception e) {
            throw new BusinessException(CodeConstant.FAIL.getCode(), "上传失败");
        }
        message.setData(imgPath);
        message.setCode(CodeConstant.SUCCESS.getCode());
        return message;
    }
}
