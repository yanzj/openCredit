package org.fisco.bcos.web3sdk.config;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

/**
 * FISCO-BCOS Config that loaded by java, not spring.
 * Because Web3sdkUtils.main is a pure java process, no spring ApplicationContext.
 *
 * @author matthewliu
 * @version 1.0
 * @since 2019/1/28
 */
@Slf4j
@Data
@PropertySource(value = "classpath:fisco.properties", encoding = "UTF-8")
public class FiscoConfig {
    @Value("${version:}")
    private String version;

    @Value("${orgid:}")
    private String orgId;

    @Value("${nodes:}")
    private String nodes;

    @Value("${account:}")
    private String account;

    @Value("${topic-controller.address:}")
    private String topicControllerAddress;

    @Value("${web3sdk.timeout:10000}")
    private Integer web3sdkTimeout;

    @Value("${web3sdk.core-pool-size:100}")
    private Integer web3sdkCorePoolSize;

    @Value("${web3sdk.max-pool-size:200}")
    private Integer web3sdkMaxPoolSize;

    @Value("${web3sdk.queue-capacity:1000}")
    private Integer web3sdkQueueSize;

    @Value("${web3sdk.keep-alive-seconds:60}")
    private Integer web3sdkKeepAliveSeconds;

    @Value("${ca-crt-path:./ca.crt}")
    private String CaCrtPath;

    @Value("${node-crt-path:./node.crt}")
    private String NodeCrtPath;

    @Value("${node-key-path:./node.key}")
    private String NodeKeyPath;

    /**
     * load configuration without spring
     *
     * @return true if success, else false
     */
    public boolean load() {
        if (!FiscoConfig.class.isAnnotationPresent(PropertySource.class)) {
            log.error("set configuration file name use @PropertySource");
            return false;
        }

        PropertySource propertySource = FiscoConfig.class.getAnnotation(PropertySource.class);
        String[] files = propertySource.value();
        if (!files[0].startsWith("classpath:")) {
            log.error("configuration file must be in classpath");
            return false;
        }
        log.info("load properties from file: {}", files[0]);

        // be careful the path
        String file = "/" + files[0].replace("classpath:", "");
        try (InputStream inputStream = FiscoConfig.class.getResourceAsStream(file)) {
            Properties properties = new Properties();
            properties.load(inputStream);

            Field[] fields = FiscoConfig.class.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Value.class)) {
                    Value value = field.getAnnotation(Value.class);

                    //String.split can not support this regex
                    Pattern pattern = Pattern.compile("\\$\\{(\\S+):(\\S*)}");
                    Matcher matcher = pattern.matcher(value.value());
                    String k = "";
                    String v = "";
                    if (matcher.find()) {
                        if (matcher.groupCount() >= 1) {

                            k = matcher.group(1);
                        }
                        if (matcher.groupCount() >= 2) {
                            v = matcher.group(2);
                        }
                    }

                    if (properties.containsKey(k)) {
                        v = properties.getProperty(k);
                    }
                    field.setAccessible(true);
                    Object obj = field.getType().getConstructor(String.class).newInstance(v);
                    field.set(this, obj);
                }
            }
        } catch (Exception e) {
            log.error("load properties failed", e);
            return false;
        }

        log.info("read from fisco.properties: {}", this);
        return true;
    }
}
