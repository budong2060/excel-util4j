package online.dinghuiye.core.resolution.convert;

import online.dinghuiye.core.resolution.convert.testcase.TestClazz0;
import online.dinghuiye.core.resolution.convert.testcase.TestClazz1;
import online.dinghuiye.core.resolution.convert.testcase.TestClazz2;
import online.dinghuiye.core.resolution.convert.testcase.User;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Strangeen on 2017/8/4.
 */
public class TestConvertKit {

    @Test
    // @ConstValue
    // @DateFormat
    // 复合convertor
    public void testConvert() throws NoSuchFieldException, ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Field field = User.class.getDeclaredField("modifyTime");
        Assert.assertEquals(sdf.parse("2017-12-12"), ConvertKit.convert(null, field, null));
    }

    @Test
    // @CurrentTimeConvertor
    // @DateFormat
    // 复合convertor
    public void testConvert2() throws NoSuchFieldException, ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        Field field = User.class.getDeclaredField("createTime");
        Assert.assertEquals(sdf.format(new Date()), sdf.format(ConvertKit.convert(null, field, null)));
    }

    @Test
    public void testConvertToType() throws NoSuchFieldException, IllegalAccessException, InstantiationException {

        // 基本类型
        Assert.assertEquals(Byte.class, ConvertKit.convertToType(TestClazz0.class.getDeclaredField("b"), "1").getClass());
        Assert.assertEquals(Character.class, ConvertKit.convertToType(TestClazz0.class.getDeclaredField("c"), "1").getClass());
        Assert.assertEquals(Short.class, ConvertKit.convertToType(TestClazz0.class.getDeclaredField("s"), "1").getClass());
        Assert.assertEquals(Integer.class, ConvertKit.convertToType(TestClazz0.class.getDeclaredField("i"), "1").getClass());
        Assert.assertEquals(Long.class, ConvertKit.convertToType(TestClazz0.class.getDeclaredField("l"), "1").getClass());
        Assert.assertEquals(Float.class, ConvertKit.convertToType(TestClazz0.class.getDeclaredField("f"), "1").getClass());
        Assert.assertEquals(Double.class, ConvertKit.convertToType(TestClazz0.class.getDeclaredField("d"), "1").getClass());
        Assert.assertEquals(Boolean.class, ConvertKit.convertToType(TestClazz0.class.getDeclaredField("bl"), "true").getClass());

        // 包装类型
        Field[] fields = TestClazz1.class.getDeclaredFields();
        for (Field field : fields) {
            Assert.assertEquals(field.getType(), ConvertKit.convertToType(field, "1").getClass());
        }

        // 集合，Map类型，自定义类型
        Assert.assertEquals(int[].class, ConvertKit.convertToType(TestClazz2.class.getDeclaredField("is"), new int[0]).getClass());
        Assert.assertEquals(Integer[].class, ConvertKit.convertToType(TestClazz2.class.getDeclaredField("iObjs"), new Integer[0]).getClass());
        Assert.assertEquals(User[].class, ConvertKit.convertToType(TestClazz2.class.getDeclaredField("users"), new User[0]).getClass());

        // 无法检测集合泛型，实际为List<Integer>
        Assert.assertEquals(true, List.class.isAssignableFrom(
                ConvertKit.convertToType(TestClazz2.class.getDeclaredField("iList"),new ArrayList<Integer>()).getClass()));
        Assert.assertEquals(true, List.class.isAssignableFrom(
                ConvertKit.convertToType(TestClazz2.class.getDeclaredField("iList"), new ArrayList<Long>()).getClass()));
        // 无法检测集合泛型，实际为Set<Integer>
        Assert.assertEquals(true, Set.class.isAssignableFrom(
                ConvertKit.convertToType(TestClazz2.class.getDeclaredField("iSet"), new HashSet<Integer>()).getClass()));
        Assert.assertEquals(true, Set.class.isAssignableFrom(
                ConvertKit.convertToType(TestClazz2.class.getDeclaredField("iSet"), new HashSet<Long>()).getClass()));
        // 无法检测集合泛型
        Assert.assertEquals(true, Map.class.isAssignableFrom(
                ConvertKit.convertToType(TestClazz2.class.getDeclaredField("map"), new HashMap<Integer, String>()).getClass()));
        Assert.assertEquals(true, User.class.isAssignableFrom(
                ConvertKit.convertToType(TestClazz2.class.getDeclaredField("user"), new User()).getClass()));
        // 无法检测集合泛型
        Assert.assertEquals(true, List.class.isAssignableFrom(
                ConvertKit.convertToType(TestClazz2.class.getDeclaredField("userList"), new ArrayList<Integer>()).getClass()));

    }
}
