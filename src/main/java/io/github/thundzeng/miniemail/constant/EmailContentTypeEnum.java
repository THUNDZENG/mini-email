package io.github.thundzeng.miniemail.constant;

/**
 * 邮件支持的发送类型
 *
 * @author thundzeng
 */
public enum EmailContentTypeEnum {
    /**
     * 文本类型
     */
    TEXT("text/plain; charset=utf-8"),
    /**
     * h5类型
     */
    HTML("text/html; charset=utf-8"),
    ;
    private String contentType;

    EmailContentTypeEnum(String contentType) {
        this.contentType=contentType;
    }

    public String getContentType() {
        return contentType;
    }
}
