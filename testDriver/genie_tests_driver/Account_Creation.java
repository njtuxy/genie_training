package genie_tests_driver;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class Account_Creation {

	private int index;

	public void setIndexInRedis() {
		String REDIS_HOST = "winsvr-qa-4.dev.kabam.com";
		int REDIS_PORT = 6379;

		JedisPool pool = new JedisPool(new JedisPoolConfig(), REDIS_HOST, REDIS_PORT);
		Jedis jedis = pool.getResource();

		try {
			jedis.incr("index");
			index = Integer.parseInt(jedis.get("index"));
		} finally {
			// ... it's important to return the Jedis instance to the pool once you've finished using it
			pool.returnResource(jedis);
		}
		// ... when closing your application:
		pool.destroy();
	}

	public String generate_unique_email_address() {
		setIndexInRedis();
		return "automationtestaccount" + index + "@kabamautomation.com";
	}

	public String generate_unique_user_name() {
		return "Math" + index;

	}

}
