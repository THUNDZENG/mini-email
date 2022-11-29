# mini-email

迷你又实用的 Java 邮件发送类库，2行代码即可发送邮件。支持 QQ邮箱、QQ企业邮箱、新浪邮箱、网易163邮箱以及中国移动139邮箱。

[![maven-central](https://img.shields.io/maven-central/v/io.github.thundzeng/mini-email.svg?style=flat-square)](https://mvnrepository.com/artifact/io.github.thundzeng/mini-email)
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg?style=flat-square)](https://www.apache.org/licenses/LICENSE-2.0.html)

## 前言

- 建议使用jdk1.8+
- 若此项目你也在使用，请给一个 Star 鼓励下作者噢~
- 若对项目有任何疑问，可在 Issues 上新增 Issue，作者看到会第一时间查看和回复噢~
- 若你对项目有更好的功能或建设性代码，可从 master 分支创建新分支，对代码更改完成后向 feature 分支发起 pull requests。欢迎大家提交代码噢~
- 国内访问github受限，所以下面贴上 gitee 和 github 对应项目地址，大家各自选择。请注意：最新代码会维护在 gitee ，github 代码同步会延迟。

  - gitee 项目地址：[点击跳转](https://gitee.com/thundzeng/mini-email)
  - gtihub 项目地址：[点击跳转](https://github.com/THUNDZENG/mini-email)

## 特别的地方

- 非常简洁的邮件发送API，2行代码即可发送邮件
- 支持自定义发件人昵称、支持邮件抄送、密抄和附件发送
- 支持发送 HTML 邮件
- 支持自定义扩展邮件发送
- 封装合理，代码易读易维护

## 使用

### 方式一：集成 starter

若是 SpringBoot 项目，推荐使用 [mini-email-spring-boot-starter](https://gitee.com/thundzeng/mini-email-spring-boot-starter) 进行自动装配，方便快速。

### 方式二：自行集成（单独封装成singleton util类）

**maven坐标**

```xml
<dependency>
    <groupId>io.github.thundzeng</groupId>
    <artifactId>mini-email</artifactId>
    <version>2.1.0</version>
</dependency>
```

**代码示例**

```java
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

```

## 问题建议

- 我的邮箱：`thundzeng@qq.com`
