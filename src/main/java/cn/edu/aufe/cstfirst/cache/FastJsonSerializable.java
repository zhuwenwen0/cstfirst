
package cn.edu.aufe.cstfirst.cache;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.Charset;

/**
 * @author zhuwenwen
 * @date 2018/10/31 15:19
 */
public class FastJsonSerializable<T> implements RedisSerializer<T> {

    private static final Charset DEFAULT_CHARSET= Charset.forName("UTF-8");
    private Class<T> clazz;

    public FastJsonSerializable (Class<T> clazz){
        super();
        this.clazz=clazz;
    }

    /**
     *  序列化
     *
     * @param t 所需要序列化的对象
     * @return 字节数组
     * @throws SerializationException
     */
    @Override
    public byte[] serialize(T t) throws SerializationException {
        if (null == t) {
            return new byte[0];
        }
        return JSON.toJSONString(t, SerializerFeature.WriteClassName).getBytes(DEFAULT_CHARSET);
    }

    /**
     * 反序列化
     *
     * @param bytes 字节数组
     * @return t对象
     * @throws SerializationException
     */
    @Override
    public T deserialize(byte[] bytes) throws SerializationException {

        if (null == bytes || bytes.length <= 0) {
            return null;
        }
        String str = new String(bytes, DEFAULT_CHARSET);
        return (T) JSON.parseObject(str, clazz);
    }
}
