# mini-email

迷你又实用的 Java 邮件发送类库，2行代码即可发送邮件。支持 QQ邮箱、QQ企业邮箱、新浪邮箱、网易163邮箱以及中国移动139邮箱。

[![maven-central](https://img.shields.io/maven-central/v/io.github.thundzeng/mini-email.svg?style=flat-square)](https://mvnrepository.com/artifact/io.github.thundzeng/mini-email)
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg?style=flat-square)](https://www.apache.org/licenses/LICENSE-2.0.html)

## 前言

- 建议使用jdk1.8+
- 若此项目你也在使用，请给一个 Star 鼓励下作者噢~
- 若对项目有任何疑问，可在 Issues 上新增 Issue，作者看到会第一时间查看和回复噢~
- 若你对项目有更好的功能或建设性代码，可从 fork mini-email 的 master 分支到本地仓库，对代码更改完成后，再向 mini-email 的 master 分支发起 pr。欢迎大家提交代码噢~
- 国内访问github受限，所以下面贴上 gitee 和 github 对应项目地址，大家各自选择。请注意：最新代码会维护在 gitee ，github 代码同步会延迟。

  - gitee 项目地址：[点击跳转](https://gitee.com/thundzeng/mini-email)
  - github 项目地址：[点击跳转](https://github.com/THUNDZENG/mini-email)

## 特别的地方

- 非常简洁的邮件发送API，2行代码即可发送邮件
- 支持自定义发件人昵称、支持邮件抄送、密抄和附件发送
- 支持发送 HTML 邮件
- 支持自定义扩展邮件发送
- 封装合理，代码易读易维护

## 使用方式

### 方式一：集成 starter

若是 SpringBoot 项目，推荐使用 [mini-email-spring-boot-starter](https://gitee.com/thundzeng/mini-email-spring-boot-starter) 进行自动装配，方便快速。

### 方式二：自行集成（单独封装成singleton util类）

**maven坐标**

```xml
<dependency>
    <groupId>io.github.thundzeng</groupId>
    <artifactId>mini-email</artifactId>
    <version>2.1.1</version>
</dependency>
```

**代码示例**

```java
public class MiniEmailTests {
    // 该邮箱修改为你需要测试的收件邮箱地址
    private static final String TO_EMAIL = "13760324079@163.com";
    // 发送邮件给多个收件人
    private static final String[] TO_EMAILS = new String[]{"13760324079@139.com", "1245725331@qq.com", "xxx@qq.com"};

    MiniEmailFactory miniEmailFactory;

    @Before
    public void before() {
        // 使用入参创建（不推荐）
//        miniEmailFactory = new MiniEmailFactoryBuilder().build(true, "thundzeng@qq.com", "xxxxxx", SmtpEnum.SMTP_QQ);
        // 使用配置类创建（推荐）
        miniEmailFactory = new MiniEmailFactoryBuilder().build(MailConfig.config("thundzeng@qq.com", "xxxxxx")
                .setMailDebug(true)
                .setSenderNickname("天雷盖地虎")
                .setMailSmtpHost(SmtpEnum.SMTP_QQ)
        );
    }

    /**
     * 快速发送文本邮件，2行代码搞定
     */
    @Test
    public void testSendText() {
        MiniEmail miniEmail = miniEmailFactory.init();
        List<String> sendSuccessToList = miniEmail
                .addCarbonCopy(new String[]{TO_EMAIL})
                .send(TO_EMAILS, "这是来自 mini-email 的测试信文本内容");
        System.out.println("send success to = " + sendSuccessToList);
    }

    /**
     * 快速发送Html邮件，2行代码搞定
     */
    @Test
    public void testSendHtml() {
        MiniEmail miniEmail = miniEmailFactory.init();
        List<String> sendSuccessToList = miniEmail
                .addBlindCarbonCopy(new String[]{TO_EMAIL})
                .send(TO_EMAILS, "HTML邮件主题", EmailContentTypeEnum.HTML, "<h1 style='color:red;'>这是来自 mini-email 的测试 HTML 内容</h1>");
        System.out.println("send success to = " + sendSuccessToList);
    }

    /**
     * 复杂组合发送（附件+抄送+密送）
     */
    @Test
    public void testSendAttachFileAndURL() throws MalformedURLException {
        File file = new File("D:\\Documents\\Pictures\\表情\\bug改完了吗.jpg");

        Assert.assertTrue("图片不存在", file.exists());

        URL url = new URL("https://avatars.githubusercontent.com/u/26403930?s=460&u=1a90eb155a8dbb56385be72a90fdd2911a068409&v=4");
        long start = System.currentTimeMillis();

        MiniEmail miniEmail = miniEmailFactory.init();
        List<String> sendSuccessToList = miniEmail
                .addCarbonCopy(new String[]{"1846316024@qq.com"})
                .addBlindCarbonCopy(new String[]{TO_EMAIL})
                .addAttachment(file, "666.jpg")
                .addAttachment(url, "THUNDZENG的头像.jpg")
                .send(TO_EMAILS, "致我们失去的青春", EmailContentTypeEnum.HTML, "<h2 style='color:blue;'>这是来自 mini-email 的测试 HTML 内容</h2><br /><h2 style='color:red;'>请查看附件内容</h2>");
        System.out.println("send finish,use time = " + (System.currentTimeMillis() - start) / 1000);

        System.out.println("send success to = " + sendSuccessToList);
    }
}
```

## 问题建议

- 我的邮箱：`thundzeng@qq.com`
- 我的QQ（添加时备注mini-email）：`1846316024`
