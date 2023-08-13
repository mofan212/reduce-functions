package indi.mofan.util;

/**
 * @author mofan
 * @date 2023/8/13 17:12
 */
public class CastUtils {

    @SuppressWarnings("unchecked")
    public static <T> T cast(Object object) {
        return (T) object;
    }
}