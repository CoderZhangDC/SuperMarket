package com.server.dao;

public interface RoleDao {

    //根据角色名查找角色id
    int queryRoleIdByName(String role);
}
