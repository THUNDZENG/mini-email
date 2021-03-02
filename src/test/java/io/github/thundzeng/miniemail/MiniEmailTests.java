package io.github.thundzeng.miniemail;

import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.error.PebbleException;
import com.mitchellbosecke.pebble.template.PebbleTemplate;
import io.github.thundzeng.miniemail.constant.EmailTypeEnum;
import io.github.thundzeng.miniemail.constant.SmtpEnum;
import io.github.thundzeng.miniemail.core.MiniEmail;
import io.github.thundzeng.miniemail.core.MiniEmailFactory;
import io.github.thundzeng.miniemail.core.MiniEmailFactoryBuilder;
import jetbrick.template.JetEngine;
import jetbrick.template.JetTemplate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class MiniEmailTests {
    // 该邮箱修改为你需要测试的收件邮箱地址
    private static final String TO_EMAIL = "thundzeng@qq.com";
    // 发送邮件给多个收件人
    private static final String[] TO_EMAILS = new String[]{"xxxx@qq.com", "xxxx@sina.com"};

    MiniEmailFactory miniEmailFactory;

    @Before
    public void before() {
        // 创建一次就可以了
//        miniEmailFactory = new MiniEmailFactoryBuilder().build(true, "xxxx@qq.com", "123456abc", SmtpEnum.SMTP_QQ);
//        miniEmailFactory = new MiniEmailFactoryBuilder().build("xxxx@qq.com", "123456abc", SmtpEnum.SMTP_QQ);
        miniEmailFactory = new MiniEmailFactoryBuilder().build("xxxx@163.com", "123456abc", SmtpEnum.SMTP_163);
    }

    /**
     * 快速发送文本邮件，2行代码搞定
     */
    @Test
    public void testSendText() {
        MiniEmail miniEmail = miniEmailFactory.init(EmailTypeEnum.TEXT);
        miniEmail.send(TO_EMAIL, "信件内容123456");
    }

    /**
     * 快速发送Html邮件，2行代码搞定
     */
    @Test
    public void testSendHtml() {
        MiniEmail miniEmail = miniEmailFactory.init("HTML邮件主题", "Jay Chou", EmailTypeEnum.HTML);
        miniEmail.addCarbonCopy(new String[]{TO_EMAIL}).send(TO_EMAIL, "<h1 style='color:red;'>信件内容HTML123456</h1>");
    }

    /**
     * 添加附件，单个收件人发送
     *
     * @throws MalformedURLException
     */
    @Test
    public void testSendAttachFileAndURL() throws MalformedURLException {
        File file = new File("E:\\test-image\\Lighthouse.jpg");

        Assert.assertTrue("图片不存在", file.exists());

        URL url = new URL("https://avatars.githubusercontent.com/u/26403930?s=460&u=1a90eb155a8dbb56385be72a90fdd2911a068409&v=4");
        MiniEmail miniEmail = miniEmailFactory.init("HTML邮件主题", "Jay Chou", EmailTypeEnum.HTML);
        miniEmail
                .addAttachment(file, "灯塔.jpg")
                .addAttachment(url, "THUNDZENG的头像.jpg")
                .send(TO_EMAIL, "<h1 style='color:red;'>请查看附件内容</h1>");
    }

    /**
     * 添加附件，多个收件人、多个抄送收件人发送
     *
     * @throws MalformedURLException
     */
    @Test
    public void testMultiSendAttachFileAndURL() throws MalformedURLException {
        File file = new File("E:\\test-image\\Lighthouse.jpg");

        Assert.assertTrue("图片不存在", file.exists());

        URL url = new URL("https://avatars.githubusercontent.com/u/26403930?s=460&u=1a90eb155a8dbb56385be72a90fdd2911a068409&v=4");
        MiniEmail miniEmail = miniEmailFactory.init("HTML邮件主题", "Jay Chou", EmailTypeEnum.HTML);
        miniEmail.addCarbonCopy(TO_EMAILS)
//                .addAttachment(file, "灯塔.jpg")
                .addAttachment(url, "THUNDZENG的头像.jpg")
                .send(TO_EMAILS, "<h1 style='color:red;'>请查看附件内容</h1>");
    }

    /**
     * Pebble 邮件模板发送
     */
    @Test
    public void testPebble() throws IOException, PebbleException {
        PebbleEngine engine = new PebbleEngine.Builder().build();
        PebbleTemplate compiledTemplate = engine.getTemplate("hello.html");

        Map<String, Object> context = new HashMap<String, Object>();
        context.put("username", "程序员、攻城狮们");
        context.put("adminEmail", "admin@qq.com");

        Writer writer = new StringWriter();
        compiledTemplate.evaluate(writer, context);

        String output = writer.toString();
        System.out.println(output);

        MiniEmail miniEmail = miniEmailFactory.init("一封来自测试发送的 Pebble 模板邮件", "Jay Chou", EmailTypeEnum.HTML);
        miniEmail.send(TO_EMAIL, output);
    }

    /**
     * jetx 邮件模板发送
     */
    @Test
    public void testJetx() {
        JetEngine engine = JetEngine.create();
        JetTemplate template = engine.getTemplate("/hello.jetx");

        Map<String, Object> context = new HashMap<String, Object>();
        context.put("username", "程序员、攻城狮们");
        context.put("adminEmail", "admin@qq.com");
        context.put("url", "<a href='http://biezhi.me'>https://biezhi.me/active/asdkjajdasjdkaweoi</a>");

        StringWriter writer = new StringWriter();
        template.render(context, writer);
        String output = writer.toString();
        System.out.println(output);

        MiniEmail miniEmail = miniEmailFactory.init("一封来自测试发送的 Jetx 模板邮件", "Jay Chou", EmailTypeEnum.HTML);
        miniEmail.send(TO_EMAIL, output);
    }

}
