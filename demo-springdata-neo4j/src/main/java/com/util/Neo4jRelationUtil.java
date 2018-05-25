/*
 * Copyright (C) 2018 org.citic.iiot, Inc. All Rights Reserved.
 */


package com.util;

/**
 * Simple to Introduction
 * className: Neo4jRelationUtil
 *
 * @author zhangzhen
 * @version 2018/3/23 11:06
 */
public class Neo4jRelationUtil {

    /* ********************************************
     * *****************节点Node******************
     ***********************************************/
    /**
     * Master
     */
    public static final String MASTER = "Master";
    /**
     * 应用
     */
    public static final String APPLICATION = "Application";

    /**
     * 连接器node
     */
    public static final String CONNECTOR = "Connector";

    /**
     * messageuser节点
     */
    public static final String MESSAGEUSER = "MessageUser";

    /**
     * Device节点
     */
    public static final String DEVICE = "Device";

    /**
     * 用户
     */
    public static final String L_USER="User";

    /* *********************************************
     * ***************** 关系Relation***************
     ***********************************************/

    /**
     * 应用与团队关系
     */
    public static final String R_APP_GROUP = "APPLICATION_GROUP";
    /**
     * 团队与团队关系
     */
    public static final String R_GROUP_GROUP = "GROUP_GROUP";
    /**
     * 团队与用户关系
     */
    public static final String R_GROUP_USER = "GROUP_USER";
    /**
     * 应用与用户关系
     */
    public static final String R_APP_USER = "APPLICATION_USER";
    /**
     * 用户注册应用关系
     */
    public static final String R_APP_REGISTER = "APPLICATION_REGISTER";
    /**
     * 用户默认英勇关系
     */
    public static final String R_APP_DEFAULT = "APPLICATION_DEFAULT";
    /**
     * 用户与角色关系
     */
    public static final String R_USER_ROLE = "USER_ROLE";
    /**
     * 资源与资源关系
     */
    public static final String R_RESOURCES_RESOURCES = "RESOURCES_RESOURCES";
    /**
     * 应用与角色关系
     */
    public static final String R_APP_ROLE = "APPLICATION_ROLE";
    /**
     * 应用与资源关系
     */
    public static final String R_APP_RESOURCE = "APPLICATION_RESOURCE";

    /**
     * 应用与设备关系
     */
    public static final String R_APP_DEVICE = "APPLICATION_DEVICE";

    /**
     * 团队与设备关系
     */
    public static final String R_GROUP_DEVICE = "GROUP_DEVICE";

    /**
     * 用户与设备关系
     */
    public static final String R_USER_DEVICE = "USER_DEVICE";

    /**
     * Master与应用关系
     */
    public static final String R_MASTER_APP = "MASTER_APPLICATION";
    /**
     * 角色与资源关系
     */
    public static final String R_ROLE_RESOURCES= "ROLE_RESOURCES";

    /**
     * CONNECTOR Messageuser关系
     */
    public static final String R_CONNECTOR_MESSAGE_USER = "CONNECTOR_MESSAGEUSER";

    /**
     * 连接器与设备关系
     */
    public static final String R_CONNECTOR_DEVICE = "CONNECTOR_DEVICE";

    /**
     * 团队与连接器关系
     */
    public static final String R_GROUP_CONNECTOR = "GROUP_CONNECTOR";

    /* *********************************************
    * ***************** 常量 **********************
    ***********************************************/

    /**
     * 设备到云标志
     */
    public static final String PUB = "1";

    /**
     * 云到设备标志
     */
    public static final String SUB = "2";

    /**
     * 是超级用户
     */
    public static final Integer IS_SUPER = 1;

    /**
     * 普通用户
     */
    public static final Integer NO_SUPER = 0;

    /**
     * 消息用户状态 当前为使用中
     */
    public static final Integer LIVE_STATUS = 1;

    /**
     * 连接器通道状态 开启
     */
    public static final Integer LIVE_SUB_OR_PUB_STATE = 1;

    /**
     * 连接器通道状态 关闭
     */
    public static final Integer CLOSE_SUB_OR_PUB_STATE = 0;

    /**
     * appid 前缀
     */
    public static final String PREFIX_APPID = "a_";

    /**
     * sub topic 前缀
     */
    public static final String PREFIX_SUB_CHANNEL = "s_";

    /**
     * pub topic 前缀
     */
    public static final String PREFIX_PUB_CHANNEL = "p_";

    /**
     * 分隔符
     */
    public static final String SEPARATE = "/";
}

