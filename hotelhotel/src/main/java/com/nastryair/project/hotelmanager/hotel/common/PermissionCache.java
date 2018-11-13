package com.nastryair.project.hotelmanager.hotel.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by QWL on 2018/4/13.
 */
public class PermissionCache {
    private static Map<String, List<String>> permissionMap = new HashMap<>();

    public static Map<String, List<String>> getPermissionMap() {
        return permissionMap;
    }

    public static List<String> getPermissionList(String userId) {
        return permissionMap.get(userId);
    }

    public static void putPermissionMap(String userId, List<String> permissionList) {
        permissionMap.put(userId, permissionList);
    }

    public static void clearPermissionMap() {
        permissionMap.clear();
    }
}
