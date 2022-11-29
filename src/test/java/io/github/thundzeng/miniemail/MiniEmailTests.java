package io.github.thundzeng.miniemail;

import io.github.thundzeng.miniemail.config.MailConfig;
import io.github.thundzeng.miniemail.constant.EmailContentTypeEnum;
import io.github.thundzeng.miniemail.constant.SmtpEnum;
import io.github.thundzeng.miniemail.core.MiniEmail;
import io.github.thundzeng.miniemail.core.MiniEmailFactory;
import io.github.thundzeng.miniemail.core.MiniEmailFactoryBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.mail.MessagingException;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

public class MiniEmailTests {
    // 该邮箱修改为你需要测试的收件邮箱地址
    private static final String TO_EMAIL = "thundzeng@qq.com";
    // 发送邮件给多个收件人
    private static final String[] TO_EMAILS = new String[]{"thundzeng@qq.com", "1245725331@qq.com"};

    MiniEmailFactory miniEmailFactory;

    @Before
    public void before() {
        // 使用入参创建（不推荐）
//        miniEmailFactory = new MiniEmailFactoryBuilder().build(true, "123456@sina.com", "xxxxxx", SmtpEnum.SMTP_SINA);
        // 使用配置类创建（推荐）
        miniEmailFactory = new MiniEmailFactoryBuilder().build(MailConfig.config("13760324479@139.com", "Changeme_test_888")
                .setMailDebug(false)
                .setSenderNickname("天雷盖地虎")
                .setMailSmtpHost(SmtpEnum.SMTP_139));
    }

    /**
     * 快速发送文本邮件，2行代码搞定
     */
    @Test
    public void testSendText() throws MessagingException {
        MiniEmail miniEmail = miniEmailFactory.init();
        miniEmail.addCarbonCopy(TO_EMAILS).send(TO_EMAILS, "信件内容123456");
    }

    /**
     * 快速发送Html邮件，2行代码搞定
     */
    @Test
    public void testSendHtml() {
        MiniEmail miniEmail = miniEmailFactory.init();
        miniEmail.send(TO_EMAIL, "HTML邮件主题", EmailContentTypeEnum.HTML, "<h1 style='color:red;'>信件内容HTML123456</h1>");
    }

    /**
     * 复杂组合发送（附件+抄送+密送）
     *
     * @throws MalformedURLException
     */
    @Test
    public void testSendAttachFileAndURL() throws MalformedURLException, MessagingException, UnsupportedEncodingException {
        File file = new File("D:\\Documents\\Pictures\\表情\\bug改完了吗.jpg");

        Assert.assertTrue("图片不存在", file.exists());

        URL url = new URL("https://avatars.githubusercontent.com/u/26403930?s=460&u=1a90eb155a8dbb56385be72a90fdd2911a068409&v=4");
        long start = System.currentTimeMillis();

        MiniEmail miniEmail = miniEmailFactory.init();
        miniEmail
                .addCarbonCopy(new String[]{TO_EMAIL})
                .addBlindCarbonCopy(TO_EMAILS)
                .addAttachment(file, "666.jpg")
                .addAttachment(url, "THUNDZENG的头像.jpg")
                .send("thundzeng@163.com", "致我们失去的青春", EmailContentTypeEnum.HTML, "<h2 style='color:blue;'>好久不见，老同学。</h2><br /><h2 style='color:red;'>请查看附件内容</h2>");
        System.out.println("send finish,use time = " + (System.currentTimeMillis() - start) / 1000);
    }
}
