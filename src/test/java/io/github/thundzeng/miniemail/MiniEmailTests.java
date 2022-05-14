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
    private static final String TO_EMAIL = "thundzeng@163.com";
    // 发送邮件给多个收件人
    private static final String[] TO_EMAILS = new String[]{"thundzeng@qq.com", "1245725331@qq.com"};

    MiniEmailFactory miniEmailFactory;

    @Before
    public void before() {
        // 创建一次就可以了
//        miniEmailFactory = new MiniEmailFactoryBuilder().build(true, "123456@sina.com", "xxxxxx", SmtpEnum.SMTP_SINA);

        MailConfig config = MailConfig.config("123456@sina.com", "xxxxxx")
                .setMailSmtpAuth(Boolean.TRUE)
                .setMailSmtpSslEnable(Boolean.TRUE)
                .setMailTransportProtocol("smtp")
                .setMailSmtpTimeout(10000L)
                .setMailSmtpPort(465)
                .setMailSmtpHost(SmtpEnum.SMTP_SINA).setMailDebug(true);
        miniEmailFactory = new MiniEmailFactoryBuilder().build(config);
    }

    /**
     * 快速发送文本邮件，2行代码搞定
     */
    @Test
    public void testSendText() throws MessagingException {
        MiniEmail miniEmail = miniEmailFactory.init();
        miniEmail.addCarbonCopy(TO_EMAILS).send(TO_EMAIL, "信件内容123456");
    }

    /**
     * 快速发送Html邮件，2行代码搞定
     */
    @Test
    public void testSendHtml() {
        MiniEmail miniEmail = miniEmailFactory.init("Jay Chou");
        miniEmail.send(TO_EMAILS, "HTML邮件主题", EmailContentTypeEnum.HTML, "<h1 style='color:red;'>信件内容HTML123456</h1>");
    }

    /**
     * 复杂组合发送（附件+抄送+密送）
     *
     * @throws MalformedURLException
     */
    @Test
    public void testSendAttachFileAndURL() throws MalformedURLException, MessagingException, UnsupportedEncodingException {
        File file = new File("C:\\Users\\thundzeng\\Pictures\\1.png");

        Assert.assertTrue("图片不存在", file.exists());

        URL url = new URL("https://avatars.githubusercontent.com/u/26403930?s=460&u=1a90eb155a8dbb56385be72a90fdd2911a068409&v=4");
        long start = System.currentTimeMillis();
        MiniEmail miniEmail = miniEmailFactory.init("Jay Chou");
        miniEmail
                .addCarbonCopy(new String[]{TO_EMAIL})
                .addBlindCarbonCopy(TO_EMAILS)
                .addAttachment(file, "666.jpg")
                .addAttachment(url, "THUNDZENG的头像.jpg")
                .send("thundzeng@163.com", "HTML邮件主题", EmailContentTypeEnum.HTML, "<h2 style='color:blue;'>这是一封测试邮件。</h2><br /><h2 style='color:red;'>请查看附件内容</h2>");
        System.out.println("send finish,use time = " + (System.currentTimeMillis() - start) / 1000);
    }

}
