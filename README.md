# mini-email

迷你，但实用的 Java 邮件发送类库。支持 QQ邮箱、QQ企业邮箱、新浪邮箱、网易163邮箱。

[![maven-central](https://img.shields.io/maven-central/v/io.github.thundzeng/mini-email.svg?style=flat-square)](https://mvnrepository.com/artifact/io.github.thundzeng/mini-email)
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg?style=flat-square)](https://www.apache.org/licenses/LICENSE-2.0.html)


## 前言

- 若此项目你也在使用，请给一个 Star 鼓励下作者噢~
- 若对项目有任何疑问，可在 Issues 上新增 Issue，作者看到会第一时间查看和回复噢~

## 特别的地方

- 非常简洁的邮件发送API，2行代码即可发送邮件
- 支持自定义发件人昵称、支持邮件抄送，支持附件发送
- 支持发送 HTML 邮件
- 支持扩展邮件发送类型
- 支持邮件模板
- 封装合理，代码易读易维护

## 使用

**maven坐标（请使用以下版本）**

```xml
<dependency>
    <groupId>io.github.thundzeng</groupId>
    <artifactId>mini-email</artifactId>
    <version>1.2.1</version>
</dependency>
```

**代码示例（使用时建议单独封装成util类）**

```java
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

```

### 邮件模版示例（可选。若要使用需要单独引入依赖）

**Pebble**
```xml
<!-- 引入依赖 -->
<dependency>
    <groupId>com.mitchellbosecke</groupId>
    <artifactId>pebble</artifactId>
    <version>2.2.0</version>
</dependency>
```
```html
<!-- 在resources文件夹下，新建 hello.html 文件，将以下测试内容复制至文件中 -->
<div>
    <p>一件事无论太晚，或是太早，都不会阻拦你成为你想成为的那个人。</p>
    <p>这个过程没有时间的期限，只要你想，随时都可以开始，要改变或者保持现状都无所谓，做事情本不应有所束缚。</p>
    <p>我们可以办好一件事，却也可以把它搞砸。</p>
    <p>亲爱的<b>{{username}}</b>，保重身体！</p>
    <p>PS：如有疑问请联系管理员邮箱：<b>{{ adminEmail }}</b></p>
    <p>-----------------------</p>
    <p></p>
    <p>(这是一封自动发送的email，请勿回复。)</p>
</div>
```

**Jetx**
```xml
<!-- 引入依赖 -->
<dependency>
    <groupId>com.github.subchen</groupId>
    <artifactId>jetbrick-template</artifactId>
    <version>2.1.2</version>
</dependency>
```
```html
<!-- 在resources文件夹下，新建 hello.jext 文件，将以下测试内容复制至文件中 -->
<div>
    <p>一件事无论太晚，或是太早，都不会阻拦你成为你想成为的那个人。</p>
    <p>这个过程没有时间的期限，只要你想，随时都可以开始，要改变或者保持现状都无所谓，做事情本不应有所束缚。</p>
    <p>我们可以办好一件事，却也可以把它搞砸。</p>
    <p>亲爱的<b>${username}</b>，保重身体！</p>
    <p>PS：如有疑问请联系管理员邮箱：<b>${ adminEmail }</b></p>
    <p>-----------------------</p>
    <p></p>
    <p>(这是一封自动发送的email，请勿回复。)</p>
</div>

```

## 问题建议

- 我的邮箱：`thundzeng@qq.com`
