/*
 * Copyright (c) 2019. CK. All rights reserved.
 */

package com.github.fartherp.demo.common.redis;

import com.github.fartherp.framework.core.bean.ServiceLocator;
import org.redisson.api.LocalCachedMapOptions;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RBucket;
import org.redisson.api.RList;
import org.redisson.api.RLocalCachedMap;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RScript;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: CK
 * @date: 2018/8/30
 */
public class RedissonServiceClient {

    // 提供redis默认本地缓存策略（一天更新一次本地缓存）
    private static LocalCachedMapOptions localCachedMapOptions = LocalCachedMapOptions.defaults()
            .evictionPolicy(LocalCachedMapOptions.EvictionPolicy.SOFT)
            .syncStrategy(LocalCachedMapOptions.SyncStrategy.UPDATE)
            .timeToLive(60 * 60 * 24, TimeUnit.SECONDS);

    public static RedissonClient getRedissonClient() {
        return ServiceLocator.getBean("redisson");
    }

    public static String getKey(String db, String key) {
        return db + ":" + key;
    }

    public static <V> RScoredSortedSet<V> getScoredSortedSet(String db, String key) {
        return getRedissonClient().getScoredSortedSet(getKey(db, key));
    }

    public static <K, V> RMap<K, V> getMap(String db, String key) {
        return getRedissonClient().getMap(getKey(db, key));
    }

    public static <K, V> RLocalCachedMap<K, V> getLocalCachedMap(String db, String key) {
        return getRedissonClient().getLocalCachedMap(getKey(db, key), localCachedMapOptions);
    }

    public static <V> RBucket<V> getKV(String db, String key) {
        return getRedissonClient().getBucket(getKey(db, key));
    }

    public static RAtomicLong getAtomicLong(String db, String key) {
        return getRedissonClient().getAtomicLong(getKey(db, key));
    }

    public static <V> RList<V> getList(String db, String key) {
        return getRedissonClient().getList(getKey(db, key));
    }

    public static <V> RSet<V> getSet(String db, String key) {
        return getRedissonClient().getSet(getKey(db, key));
    }

    public static void eval(String script, List<Object> keys, Object... args) {
        evalsha(script, RScript.ReturnType.BOOLEAN, keys, args);
    }

    public static <T> T eval(String script, RScript.ReturnType returnType, List<Object> keys, Object... args) {
        return evalsha(script, returnType, keys, args);
    }

    public static <T> T evalsha(String script, RScript.ReturnType returnType, List<Object> keys, Object... args) {
        try {
            String sha1 = DigestUtils.sha1DigestAsHex(script);
            return getRedissonClient().getScript().evalSha(RScript.Mode.READ_WRITE, sha1, returnType, keys, args);
        } catch (Throwable e) {
            if (ScriptUtils.exceptionContainsNoScriptError(e)) {
                // redis不存在lua脚本
                return getRedissonClient().getScript().eval(RScript.Mode.READ_WRITE, script, returnType, keys, args);
            }
            throw e;
        }
    }

    /**
     * 获取分布式锁（阻塞模式）默认过期时间2秒，等待获取锁时间30毫秒
     *
     * @param db       db
     * @param lockKey  key
     * @param lockCall 业务逻辑
     */
    public static void tryFairLock(String db, String lockKey, LockCall lockCall) throws Throwable {
        // 默认线程睡30毫秒，过期2秒
        tryFairLock(db, lockKey, 2000L, 30L, lockCall);
    }

    /**
     * 获取分布式锁（阻塞模式）
     *
     * @param db         db
     * @param key        key
     * @param expireTime 锁过期时间（毫秒）
     * @param waitTime   等待获取锁时间（毫秒）
     * @param lockCall   业务逻辑
     */
    public static void tryFairLock(String db, String key, long expireTime, long waitTime, LockCall lockCall) throws Throwable {
        // 尝试加锁
        String keys = db + ":" + key;
        RLock rLock = getRedissonClient().getFairLock(keys);
        try {
            boolean flag;
            do {
                // 获取不到锁，阻塞
                flag = rLock.tryLock(waitTime, expireTime, TimeUnit.MILLISECONDS);
            } while (!flag);
            lockCall.accept();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            rLock.unlock();
        }
    }

    /**
     * 获取分布式锁（阻塞模式）默认过期时间2秒，等待获取锁时间30毫秒
     *
     * @param db       db
     * @param lockKey  key
     * @param lockCall 业务逻辑
     * @return boolean
     */
    public static boolean tryFairLockFast(String db, String lockKey, LockCall lockCall) throws Throwable {
        // 默认线程睡30毫秒，过期2秒
        return tryFairLockFast(db, lockKey, 2000L, 30L, lockCall);
    }

    /**
     * 获取分布式锁（非阻塞模式）
     *
     * @param db         db
     * @param key        key
     * @param expireTime 锁过期时间（毫秒）
     * @param waitTime   等待获取锁时间（毫秒）
     * @param lockCall   业务逻辑
     * @return boolean
     */
    public static boolean tryFairLockFast(String db, String key, long expireTime, long waitTime, LockCall lockCall) throws Throwable {
        String keys = db + ":" + key;
        RLock rLock = getRedissonClient().getFairLock(keys);
        boolean flag = false;
        try {
            flag = rLock.tryLock(waitTime, expireTime, TimeUnit.MILLISECONDS);
            if (flag) lockCall.accept();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 非阻塞模式下自动释放锁
            if (flag) rLock.unlock();
        }
        return flag;
    }

    /**
     * 业务逻辑回调
     */
    public interface LockCall {
        void accept() throws Throwable;
    }

    public static class DigestUtils {
        private static final char[] HEX_CHARS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};

        private static final Charset UTF8_CHARSET = Charset.forName("UTF8");

        /**
         * Returns the SHA1 of the provided data
         *
         * @param data The data to calculate, such as the contents of a file
         * @return The human-readable SHA1
         */
        public static String sha1DigestAsHex(String data) {
            byte[] dataBytes = getDigest("SHA").digest(data.getBytes(UTF8_CHARSET));
            return new String(encodeHex(dataBytes));
        }

        private static char[] encodeHex(byte[] data) {
            int l = data.length;
            char[] out = new char[l << 1];
            for (int i = 0, j = 0; i < l; i++) {
                out[j++] = HEX_CHARS[(0xF0 & data[i]) >>> 4];
                out[j++] = HEX_CHARS[0x0F & data[i]];
            }
            return out;
        }

        /**
         * Creates a new {@link MessageDigest} with the given algorithm. Necessary because {@code MessageDigest} is not
         * thread-safe.
         */
        private static MessageDigest getDigest(String algorithm) {
            try {
                return MessageDigest.getInstance(algorithm);
            } catch (NoSuchAlgorithmException ex) {
                throw new IllegalStateException("Could not find MessageDigest with algorithm \"" + algorithm + "\"", ex);
            }
        }
    }

    public static class ScriptUtils {
        /**
         * Checks whether given {@link Throwable} contains a {@code NOSCRIPT} error. {@code NOSCRIPT} is reported if a script
         * was attempted to execute using {@code EVALSHA}.
         *
         * @param e the exception.
         * @return {@literal true} if the exception or one of its causes contains a {@literal NOSCRIPT} error.
         */
        public static boolean exceptionContainsNoScriptError(Throwable e) {

            Throwable current = e;
            while (current != null) {

                String exMessage = current.getMessage();
                if (exMessage != null && exMessage.contains("NOSCRIPT")) {
                    return true;
                }

                current = current.getCause();
            }

            return false;
        }
    }
}
