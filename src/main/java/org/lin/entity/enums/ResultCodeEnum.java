package org.lin.entity.enums;

import lombok.Getter;
import lombok.ToString;


@Getter
@ToString
public enum ResultCodeEnum {


    SUCCESS(true, 20000, "成功"),
    UNKNOWN_REASON(false, 20001, "未知错误"),

    BAD_SQL_GRAMMAR(false, 21001, "sql语法错误"),
    DB_ROLLBACK_ERROR(false,21012,"数据回滚异常"),
    JSON_PARSE_ERROR(false, 21002, "json解析异常"),
    PARAM_ERROR(false, 21003, "参数不正确"),
    PARAM_NULL(false,21013,"参数不能为空"),

    FILE_UPLOAD_ERROR(false, 21004, "文件上传错误"),
    FILE_DELETE_ERROR(false, 21005, "文件刪除错误"),
    EXCEL_DATA_IMPORT_ERROR(false, 21006, "Excel数据导入错误"),

    FETCH_VIDEO_UPLOADAUTH_ERROR(false, 22004, "获取上传地址和凭证失败"),
    REFRESH_VIDEO_UPLOADAUTH_ERROR(false, 22005, "刷新上传地址和凭证失败"),
    FETCH_PLAYAUTH_ERROR(false, 22006, "获取播放凭证失败"),

    URL_ENCODE_ERROR(false, 23001, "URL编码失败"),
    ILLEGAL_CALLBACK_REQUEST_ERROR(false, 23002, "非法回调请求"),
    FETCH_ACCESSTOKEN_FAILD(false, 23003, "获取accessToken失败"),
    FETCH_USERINFO_ERROR(false, 23004, "获取用户信息失败"),
    LOGIN_ERROR(false, 23005, "用户名或密码不正确"),

    COMMENT_EMPTY(false, 24006, "评论内容必须填写"),


    GATEWAY_ERROR(false, 26000, "服务不能访问"),

    MAIL_ERROR(false,28008,"邮箱发送失败"),
    CODE_ERROR(false, 28000, "验证码错误"),
    CODE_EXPIRED(false,28001, "验证码过期"),
    TOKEN_EXPIRED(false, 28011, "登录信息过期，请重新登录"),
    LOGIN_DISABLED_ERROR(false, 28002, "该用户已被禁用"),
    REGISTER_USERINFO_ERROR(false, 28003, "邮箱已被注册"),
    LOGIN_AUTH(false, 28004, "请先进行登录"),
    LOGIN_ACL(false, 28005, "没有权限"),

    BLOG_NULL(false, 28010,"博文不存在"),
    BLOG_DELETE(false,28020,"博文已被删除"),
    FEEDBACK_NULL(false,28010,"反馈不存在"),
    RESOURCE_NULL(false,28010,"资源不存在"),

    ALBUM_NULL(false,28010,"相册不存在"),
    ALBUM_PERMISSION_ERROR(false,28011,"没有对该相册的编辑权限"),

    COMMENT_NULL(false,28010, "评论不存在"),

    TAG_NULL(false,28010,"分类不存在"),
    LIMIT(false, 41000, "请求过于频繁，稍后再试");

    private final Boolean success;

    private final Integer code;

    private final String message;

    ResultCodeEnum(Boolean success, Integer code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

}