package io.github.thundzeng.miniemail.core;

import io.github.thundzeng.miniemail.constant.EmailTypeEnum;

/**
 * 邮件工厂接口
 *
 * @author thundzeng
 */
public interface MiniEmailFactory {

    /**
     * 初始化邮件信息
     *
     * @param subject       主题。
     * @param fromName      发件人姓名。
     * @param emailTypeEnum 邮件类型。 {@link EmailTypeEnum}
     * @return MiniEmail
     */
    MiniEmail init(String subject, String fromName, EmailTypeEnum emailTypeEnum);

    /**
     * 初始化邮件信息简易版
     *
     * @param emailTypeEnum 邮件类型。 {@link EmailTypeEnum}
     * @return MiniEmail
     */
    MiniEmail init(EmailTypeEnum emailTypeEnum);

}
