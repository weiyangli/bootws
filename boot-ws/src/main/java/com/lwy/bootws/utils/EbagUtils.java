package com.lwy.bootws.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 常用功能的工具类，例如计算 MD5, Base64，UUID 等
 */
public final class EbagUtils {
    private static Logger logger = LoggerFactory.getLogger(Utils.class);
    private static final DateFormat DATE_FORMATTER_WITH_WEEKDAY = new SimpleDateFormat("M月d日  E", Locale.SIMPLIFIED_CHINESE);
    private static final DateFormat DATE_FORMATTER = new SimpleDateFormat("M月d日", Locale.SIMPLIFIED_CHINESE);
    private static final DateFormat DATE_FORMATTER_WITHOUT_DAY = new SimpleDateFormat("M月", Locale.SIMPLIFIED_CHINESE);
    private static final DateFormat DATE_FORMATTER_WITH_YEAR_MONTH = new SimpleDateFormat("yy-MM 月", Locale.SIMPLIFIED_CHINESE);
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("M月d日");

    /**
     * BindingResult 中的错误信息很多，对用户不够友好，使用 getBindingMessage()
     * 提取对用户阅读友好的定义验证规则 message.
     *
     * @param result 验证的结果对象
     * @return 验证规则 message
     */
    public static String getBindingMessage(BindingResult result) {
        StringBuilder sb = new StringBuilder();

        for (FieldError error : result.getFieldErrors()) {
            // sb.append(error.getField() + " : " + error.getDefaultMessage() + "\n");
            sb.append(error.getDefaultMessage()).append("\n");
        }

        return sb.toString();
    }

    /**
     * 计算字符串的 MD5.
     *
     * @param text 需要计算 MD5 的字符串
     * @return 返回字符串的 MD5
     */
    public static String md5(String text) {
        return DigestUtils.md5DigestAsHex(text.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 计算文件的 MD5.
     * MD5 包含 16 进制表示的 10 个字符: 0-9, a-z
     *
     * @param file 需要计算 MD5 的文件
     * @return 返回文件的 MD5，如果出错，例如文件不存在则返回 null
     */
    public static String md5(File file) {
        try (InputStream in = new FileInputStream(file)) {
            return DigestUtils.md5DigestAsHex(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 对字符串 text 进行 Base64 编码.
     * Base64 有 64 个字符: 0-9, a-z, A-Z, +, /
     * 等号 = 用于补齐.
     *
     * @param text 要进行编码的字符串
     * @return 返回使用 Base64 编码后的字符串
     */
    public static String base64(String text) {
        return Base64.getEncoder().encodeToString(text.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 解码 Base64 编码的字符串 base64Text.
     *
     * @param base64Text Base64 编码的字符串
     * @return 返回源字符串
     */
    public static String unbase64(String base64Text) {
        return new String(Base64.getDecoder().decode(base64Text), StandardCharsets.UTF_8);
    }

    /**
     * 对字符串 text 进行 URL Safe 的 Base64 编码: +, /, =, 被置换为 -, _, *, 只包含 64 个 URL safe 的字符: 0-9, a-z, A-Z, -, _
     * 注意: Base64.getUrlEncoder() 编码后的 Base64 结果还有 =，不能使用
     * <p>
     * 系统中有一些值使用 BASE64 编码后存储在 COOKIE 中, 当编码后的字符串最后有一个或者两个等号(=)时,
     * 使用 Request.getCookies().getValue() 会丢失等号, 再用 BASE64 解码时产生错误.
     *
     * @param text 要进行编码的字符串
     * @return 返回使用 URL Safe Base64 编码后的字符串
     */
    public static String base64UrlSafe(String text) {
        String base64Text = base64(text);
        base64Text = base64Text.replace('+', '-');
        base64Text = base64Text.replace('/', '_');
        base64Text = base64Text.replace('=', '*');

        return base64Text;
    }

    /**
     * 解码 URL Safe 的 Base64 编码的字符串 urlBase64Text.
     *
     * @param urlBase64Text URL Safe 的 Base64 编码的字符串
     * @return 返回源字符串
     */
    public static String unbase64UrlSafe(String urlBase64Text) {
        urlBase64Text = urlBase64Text.replace('-', '+');
        urlBase64Text = urlBase64Text.replace('_', '/');
        urlBase64Text = urlBase64Text.replace('*', '=');

        return unbase64(urlBase64Text);
    }

    /**
     * 生成 UUID，格式为 1E87E000-92C0-4660-B00D-FF92B37B0A7B
     *
     * @return 返回 UUID
     */
    public static String uuid() {
        return UUID.randomUUID().toString().toUpperCase();
    }

    /**
     * 今天日期的字符串表示，格式为 yyyy-MM-dd
     *
     * @return 返回格式为 yyyy-MM-dd 日期字符串
     */
    public static String today() {
        return DateFormatUtils.format(new Date(), "yyyy-MM-dd");
    }

    /**
     * 输出对象到控制台
     *
     * @param object 要输出的对象
     */
    public static void dump(Object object) {
        System.out.println(JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss.SSS", SerializerFeature.PrettyFormat));
    }

    /**
     * 格式化日期为: M月d日  星期几
     *
     * @param date 日期
     * @return 返回格式化后的日期字符串
     */
    public static String formatDateWithWeekDay(Date date) {
        if (date != null) {
            return DATE_FORMATTER_WITH_WEEKDAY.format(date);
        } else {
            return null;
        }
    }

    /**
     * 格式化日期为: M月d日  星期几
     *
     * @param localDate 日期
     * @return 返回格式化后的日期字符串
     */
    public static String formatLocalDate(LocalDate localDate) {
        if (localDate != null) {
            return localDate.format(DATE_TIME_FORMATTER);
        }
        return null;
    }

    /**
     * 格式化日期为: yy-MM月
     *
     * @param date 日期
     * @return 返回格式化后的日期字符串
     */
    public static String formatDateWithYearAndMonth(Date date) {
        if (date != null) {
            return DATE_FORMATTER_WITH_YEAR_MONTH.format(date);
        }
        return null;
    }

    /**
     * 格式化日期为: M月d日
     *
     * @param date 日期
     * @return 返回格式化后的日期字符串
     */
    public static String formatDate(Date date) {
        if (date != null) {
            return DATE_FORMATTER.format(date);
        } else {
            return null;
        }
    }

    /**
     * 格式化日期为: M月
     *
     * @param date 日期
     * @return 返回格式化后的日期字符串
     */
    public static String formatDateWithoutDay(Date date) {
        if (date != null) {
            return DATE_FORMATTER_WITHOUT_DAY.format(date);
        } else {
            return null;
        }
    }

    /**
     * 获取指定日期月份第一天
     */
    public static Date firstDayOfMonth(Date date) {
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);

        return c.getTime();
    }

    /**
     * 获取指定日期月份最后一天
     */
    public static Date lastDayOfMonth(Date date) {
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);

        return c.getTime();
    }

    /**
     * 如果 id 不为 null 且大于 0 则是有效的 ID (数据库里的 ID 都是从 1 开始)
     *
     * @param id 进行有效性检查的 ID
     * @return id 大于 0 时返回 true，否则返回 false
     */
    public static boolean isValidId(Long id) {
        return id != null && id > 0;
    }

    /**
     * 如果 id 为 null 或者小于等于 0 则无效
     *
     * @param id 进行有效性检查的 ID
     * @return id 为 null 或者小于等于 0 返回 true，否则返回 false
     */
    public static boolean isInvalidId(Long id) {
        return !isValidId(id);
    }

    /**
     * 使用从 classes 目录下相对路径为 path 的文件创建 InputStream
     *
     * @param path 文件相对 classes 目录的路径
     * @return 返回文件 path 对应的 InputStream
     */
    public static InputStream getStreamRelativeToClassesDirectory(String path) {
        return Utils.class.getClassLoader().getResourceAsStream(path);
    }

    /**
     * 把 List<T> 根据 key 进行分组为 Map<K, List<T>>，key 为类 T 的方法引用返回的值，例如用户名，ID 等。
     * 使用案例，把 users 根据用户名和 ID 进行分组，相同用户名的用户作为 map 的 value (List<User>)，并且限制每个 list 大小为 2:
     * List<User> users = new LinkedList<>();
     * Map<String, List<User>> usersMap1 = groupAndLimitMapListValueSize(users, 2, User::getUsername);
     * Map<Long,   List<User>> usersMap2 = groupAndLimitMapListValueSize(users, 2, User::getId);
     *
     * @param list       要进行分组的 list
     * @param size       每组元素的个数
     * @param classifier 分组的 key 的方法引用
     * @param <K>        map 的 key 的类型
     * @param <T>        map 的 value 的类型
     * @return 返回分组后的 map
     */
    public static <K, T> Map<K, List<T>> groupAndLimitMapListValueSize(List<T> list, int size, Function<? super T, ? extends K> classifier) {
        Map<K, List<T>> map = list.stream().collect(Collectors.groupingBy(classifier));

        map.forEach((key, valueList) -> {
            if (valueList.size() > size) {
                map.put(key, valueList.subList(0, size));
            }
        });

        return map;
    }

    /**
     * 使用 refW 和 refH 作为参考值, 对 w 和 h 进行等比缩放, 如果同时 w < refW 和 h < refH 则不进行缩放:
     * Utils.scaleKeepAspectRatio(1000, 200, 1280, 720));  // 输出 [1000, 200]: 宽高都小于, 不进行缩放
     * Utils.scaleKeepAspectRatio(1000, 2000, 1280, 720)); // 输出 [360,  720]: 高的比例大, 高作为参考缩放
     * Utils.scaleKeepAspectRatio(1440, 300, 1280, 720));  // 输出 [1280, 266]: 宽的比例大, 宽作为参考缩放
     * Utils.scaleKeepAspectRatio(2560, 1440, 1280, 720)); // 输出 [1280, 720]: 宽高比例一样, 任意一个作为参考缩放
     *
     * @param w    进行缩放的宽
     * @param h    进行缩放的高
     * @param refW 缩放参考的宽
     * @param refH 缩放参考的高
     * @return 返回缩放后的尺寸
     */
    public static Dimension scaleKeepAspectRatio(int w, int h, int refW, int refH) {
        // 1. 计算宽和高的比例
        // 2. 如果宽或高任意一个大于对应的参考宽或高, 则需要进行缩放
        // 3. 使用等比缩放, 取宽高比例中最大的比例作为缩放比例

        int resultW   = w;
        int resultH   = h;
        double ratioW = ((double) w) / refW; // 宽的比例
        double ratioH = ((double) h) / refH; // 高的比例
        double ratio  = ratioW > ratioH ? ratioW : ratioH; // 缩放比例

        if (w > refW || h > refH) {
            resultW = (int) (w / ratio);
            resultH = (int) (h / ratio);
        }

        return new Dimension(resultW, resultH);
    }

    /**
     * 读取服务器 ID 的环境变量 SERVER_ID，范围是 [0, 1023]
     * @return 返回服务器 ID，如果没有设置环境变量 SERVER_ID 则抛出异常
     */
    public static int getServerId() {
        String id = System.getenv("SERVER_ID");

        // 是整数则为有效的 ID
        if (NumberUtils.isDigits(id)) {
            return NumberUtils.toInt(id);
        } else {
            throw new RuntimeException("请设置正确的环境变量 SERVER_ID，范围是 [0, 1023]");
        }
    }

    public static void main(String[] args) {
        String text = "如果要编码的字节数不能被3整除，最后会多出1个或2个字节.";
        String encrypt = base64(text);
        String encryptUrl = base64UrlSafe(text);
        System.out.println(encrypt);
        System.out.println(encryptUrl);
        System.out.println(unbase64(encrypt));
        System.out.println(unbase64UrlSafe(encryptUrl));
    }

}


