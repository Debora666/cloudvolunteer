package com.scnu.cloudvolunteer.app.config;

import com.scnu.cloudvolunteer.utils.RedisUtil;
import com.scnu.cloudvolunteer.utils.WechatHttp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author zzheng
 * @date 2020/05/27
 * @description：
 *  spring启动完成后执行的相关操作
 *  这里主要先获取accessToken
 */
@Component
@Order(0)
public class InitAppConfig implements CommandLineRunner, EnvironmentAware, DisposableBean {
    private static final Logger logger = LoggerFactory.getLogger(InitAppConfig.class);

    @Autowired
    private ThreadPoolTaskExecutor cloudExecutor;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void run(String... args) {
        freshAccessToken();
    }

    /**
     * 获取或更新微信调用凭证
     * 需要不断更新
     */
    private void freshAccessToken() {
        cloudExecutor.execute(()-> {
            boolean isError = false;
            while (!isError) {
                Map<String, Object> tokenMap;
                while (true) {
                    tokenMap = WechatHttp.getAccessToken();
                    if (!tokenMap.containsKey("errcode")) {
                        break;
                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        isError = true;
                        logger.error("获取微信接口调用凭证错误，code:[{}]", tokenMap.get("errcode"), e);
                    }
                }
                String accessToken = tokenMap.get("access_token").toString();
                logger.info("获取accessToken成功  [{}]", accessToken);
                redisUtil.setValue("accessToken", accessToken);
                try {
                    Integer s = (Integer) tokenMap.get("expires_in");

                    Thread.sleep((900 * s));
                } catch (InterruptedException e) {
                    logger.error("获取accessToken异常，线程死亡 ", e);
                    isError = true;
                }
            }
        });

    }

    @Override
    public void setEnvironment(Environment environment) {}

    @Override
    public void destroy() throws Exception {

    }
}

