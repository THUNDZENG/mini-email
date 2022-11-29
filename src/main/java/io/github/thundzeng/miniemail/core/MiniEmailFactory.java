package io.github.thundzeng.miniemail.core;

/**
 * 邮件工厂接口
 *
 * @author thundzeng
 */
public interface MiniEmailFactory {

    /**
     * 初始化邮件信息简易版
     *
     * @return MiniEmail
     * @since 1.3.0
     */
    MiniEmail init();

}
