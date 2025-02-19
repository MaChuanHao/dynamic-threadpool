package cn.hippo4j.starter.handler.web;

import cn.hippo4j.common.config.ApplicationContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.context.WebServerApplicationContext;
import org.springframework.boot.web.server.WebServer;
import org.springframework.context.ApplicationContext;

import java.util.concurrent.Executor;

/**
 * Abstract web thread pool service.
 *
 * @author chen.ma
 * @date 2022/1/19 21:20
 */
@Slf4j
public abstract class AbstractWebThreadPoolService implements WebThreadPoolService {

    /**
     * Thread pool executor.
     */
    protected volatile Executor executor;

    /**
     * Get web thread pool by server.
     *
     * @return
     */
    protected abstract Executor getWebThreadPoolByServer(WebServer webServer);

    @Override
    public Executor getWebThreadPool() {
        if (executor == null) {
            synchronized (AbstractWebThreadPoolService.class) {
                if (executor == null) {
                    ApplicationContext applicationContext = ApplicationContextHolder.getInstance();
                    WebServer webServer = ((WebServerApplicationContext) applicationContext).getWebServer();
                    executor = getWebThreadPoolByServer(webServer);
                }
            }
        }

        return executor;
    }

}
