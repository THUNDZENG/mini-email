package io.github.thundzeng.miniemail.core;

/**
 * 邮件工厂接口
 *
 * @author thundzeng
 */
public interface MiniEmailFactory {

    /**
     * 初始化邮件信息
     *
     * @param nickName 发件人定制昵称。
     * @param clazz    开放邮件实现，只要继承 BaseMiniEmail，即可传入使用
     * @return MiniEmail
     * @since 1.3.0
     */
    MiniEmail init(String nickName, Class<? extends BaseMiniEmail> clazz);

    /**
     * 初始化邮件信息
     *
     * @param nickName 发件人定制昵称
     * @return MiniEmail
     * @since 1.3.0
     */
    MiniEmail init(String nickName);

    /**
     * 初始化邮件信息简易版
     *
     * @return MiniEmail
     * @since 1.3.0
     */
    MiniEmail init();

}
