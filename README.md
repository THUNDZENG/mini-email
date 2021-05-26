# mini-email

迷你又实用的 Java 邮件发送类库，2行代码即可发送邮件。支持 QQ邮箱、QQ企业邮箱、新浪邮箱、网易163邮箱。

[![maven-central](https://img.shields.io/maven-central/v/io.github.thundzeng/mini-email.svg?style=flat-square)](https://mvnrepository.com/artifact/io.github.thundzeng/mini-email)
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg?style=flat-square)](https://www.apache.org/licenses/LICENSE-2.0.html)


## 前言

- 若此项目你也在使用，请给一个 Star 鼓励下作者噢~
- 若对项目有任何疑问，可在 Issues 上新增 Issue，作者看到会第一时间查看和回复噢~
- 若你对项目有更好的功能或建设性代码，可 clone develop 分支代码进行更改，更改完成后 push 到 develop 分支，并向 feature 分支发起 pull requests。欢迎大家提交代码噢~

## 特别的地方

- 非常简洁的邮件发送API，2行代码即可发送邮件
- 支持自定义发件人昵称、支持邮件抄送、密抄和附件发送
- 支持发送 HTML 邮件
- 支持自定义扩展邮件发送
- 封装合理，代码易读易维护

## 使用

**maven坐标**

```xml
<dependency>
    <groupId>io.github.thundzeng</groupId>
    <artifactId>mini-email</artifactId>
    <version>1.3.0</version>
</dependency>
```

**代码示例（使用时可单独封装成util类）**

```java
public class MiniEmailTests {
    // 该邮箱修改为你需要测试的收件邮箱地址
    private static final String TO_EMAIL = "thundzeng@163.com";
    // 发送邮件给多个收件人
    private static final String[] TO_EMAILS = new String[]{"thundzeng@qq.com", "1245725331@qq.com"};

    MiniEmailFactory miniEmailFactory;

    @Before
    public void before() {
        // 创建一次就可以了
        miniEmailFactory = new MiniEmailFactoryBuilder().build(true, "123456@sina.com", "xxxxxx", SmtpEnum.SMTP_SINA);
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

```

## 问题建议

- 我的邮箱：`thundzeng@qq.com`
