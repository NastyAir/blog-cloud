-- ----------------------------
-- Records of hotel
-- ----------------------------
INSERT INTO `hotel` (id,address,city,city_id,district,district_id,latitude,longitude,name,picture,price,price_range,province,province_id,star_rated,status,theme,user_id,hotel_id,create_by,create_date,description,update_by,update_date) VALUES (1, '德源镇面具街001号', '成都', '0123', '郫都区', '01234', '120', '50', '测试大酒店0', '/upload/hotel/1.jpg', '100', '0', '四川', '012', '3', 'enable', '0', '1', 'a047800d-714d-4f14-b2fb-862627e412bc', NULL, NULL, NULL, NULL, NULL),(2, '德源镇面具街002号', '成都', '0123', '郫都区', '01234', '120', '50', '测试大酒店1', '/upload/hotel/1.jpg', '250', '1', '四川', '012', '4', 'enable', '1', '1', 'a047800d-714d-4f14-b2fb-862627e412bc', NULL, NULL, NULL, NULL, NULL),(3, '德源镇面具街003号', '成都', '0123', '郫都区', '01234', '120', '50', '测试大酒店2', '/upload/hotel/1.jpg', '350', '2', '四川', '012', '5', 'enable', '2', '1', 'a047800d-714d-4f14-b2fb-862627e412bc', NULL, NULL, NULL, NULL, NULL),(4, '德源镇面具街004号', '成都', '0123', '郫都区', '01234', '120', '50', '测试大酒店3', '/upload/hotel/1.jpg', '450', '3', '四川', '012', '5', 'enable', '3', '1', 'a047800d-714d-4f14-b2fb-862627e412bc', NULL, NULL, NULL, NULL, NULL);


-- ----------------------------
-- Records of room
-- ----------------------------
INSERT INTO `room` (id,cid,create_by,create_date,description,name,position,room_id,state,uid,update_by,update_date) VALUES (4, '1', NULL, NULL, '描述信息', '11-1', '1111', 'b101d421-087a-4127-8baa-edc021b43710', 'enable', NULL, NULL, NULL),(5, '2', NULL, NULL, '描述信息', '22', '22-1', 'c071ea88-0ca8-4711-870b-6d082aa09805', 'enable', NULL, NULL, NULL),(12, '2', NULL, NULL, '描述信息', '11', '11--1', '191e4d8d-eb55-4a85-81d3-4486e92495ac', 'enable', NULL, NULL, NULL),(13, '2', NULL, NULL, '描述信息', '12', '22---1', 'ba2cde1c-e5d0-40d9-978a-143625a5f1ae', 'enable', NULL, NULL, NULL),(14, '2', NULL, NULL, '描述信息', '11111', '2222', 'b6e0e89e-99a5-4eff-98cf-1ab203ba48e6', 'enable', NULL, NULL, NULL);

