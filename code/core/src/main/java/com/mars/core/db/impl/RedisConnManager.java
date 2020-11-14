package com.mars.core.db.impl;


import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisConnManager{
    /***实例化对象**/
    private static RedisConnManager _instance = null;
    /***数据库连接池*/
    private JedisPool _CONN = null;
    /***数据库IP地址*/
    public static String _HOST = "";
    /***数据库端口**/
    public static int _PORT = 0;
    /***数据库密码**/
    public static String _PASSWORD = "";

    static {
        _instance = new RedisConnManager();
    }

    private RedisConnManager() {
    }

    public static RedisConnManager getInstance() {
        return _instance;
    }

    /***初始化DB链接池**/
    //@IgameInitEventListener
    public void init() {
        try {
            _HOST = "127.0.0.1";
            _PASSWORD = "root";
            _PORT = 3679;

            JedisPoolConfig poolConfig = new JedisPoolConfig();
            /** 连接耗尽时是否阻塞, false报异常,TRUE阻塞直到超时, 默认true */
            poolConfig.setBlockWhenExhausted(true);

            /** 设置的逐出策略类名, 默认DefaultEvictionPolicy(当连接超过最大空闲时间,或连接数超过最大空闲连接数) */
            poolConfig.setEvictionPolicyClassName("org.apache.commons.pool2.impl.DefaultEvictionPolicy");
            /**
             * 当从池中获取资源或者将资源还回池中时
             * 是否使用java.util.concurrent.locks.ReentrantLock.ReentrantLock
             * 的公平锁机制,默认为false
             */
            poolConfig.setFairness(false);

            /** 设置是否启用JMX,默认true */
            poolConfig.setJmxEnabled(true);

            /** 设置连接对象是否后进先出,默认true */
            poolConfig.setLifo(true);

            /*** 控制一个pool最多有多少个状态为idle(空闲)的jedis实例;128 */
            poolConfig.setMaxIdle(512);

            /*** 设置最大连接数,默认18个;1024 */
            poolConfig.setMaxTotal(2048);

            /**
             * 获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,
             * 默认-1;3000
             */
            poolConfig.setMaxWaitMillis(1500);

            /** 设置连接最小的逐出间隔时间，默认1800000毫秒 */
            poolConfig.setMinEvictableIdleTimeMillis(60 * 1000);

            /** 设置无连接时池中最小的连接个数,默认连接0 */
            poolConfig.setMinIdle(16);
            /** 每次逐出检查时,逐出连接的个数 */
            poolConfig.setNumTestsPerEvictionRun(32);

            /**
             * 对象空闲多久后逐出, 当空闲时间>该值 且 空闲连接>最大空闲数
             * 时直接逐出,不再根据MinEvictableIdleTimeMillis判断
             */
            poolConfig.setSoftMinEvictableIdleTimeMillis(180 * 1000);

            /** 从池中获取连接时是否测试连接的有效性,默认false */
            poolConfig.setTestOnBorrow(false);
            /** 在连接对象创建时测试连接对象的有效性,默认false */
            poolConfig.setTestOnCreate(false);
            /** 在连接对象返回时，是否测试对象的有效性,默认false */
            poolConfig.setTestOnReturn(false);
            /** 在连接池空闲时是否测试连接对象的有效性,默认false */
            poolConfig.setTestWhileIdle(true);

            /*** 表示idle object evitor两次扫描之间要sleep的毫秒数； */
            poolConfig.setTimeBetweenEvictionRunsMillis(1000 * 60);

            _CONN = new JedisPool(poolConfig, _HOST, _PORT, 3000, _PASSWORD);
            RedisConnManager.getInstance().getConn().info();

        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(1);
        }
    }

    public Jedis getConn() {
        return _CONN.getResource();
    }

}
