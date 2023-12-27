package com.ark.center.product.infra.inventory.gateway.cache;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * Lua脚本管理器
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class LuaScriptManager implements InitializingBean {

    private final Map<String, RedisScript<Long>> scriptHolder = new HashMap<>(10);

    public static String SCRIPT_SAVE_STOCK = "save_stock.lua";
    public static String SCRIPT_UPDATE_STOCK = "update_stock.lua";
    public static String SCRIPT_DECR_STOCK = "decr_stock.lua";

    @Override
    public void afterPropertiesSet() throws Exception {
        load();
    }

    private final ResourcePatternResolver resourcePatternResolver;

    public void load() throws IOException {
        // 搜索符合模式的资源文件
        Resource[] resources = resourcePatternResolver.getResources("classpath*:lua/*.lua");
        for (Resource resource : resources) {
            RedisScript<Long> script = RedisScript.of(resource.getContentAsString(Charset.defaultCharset()), Long.class);
            String filename = resource.getFilename();
            log.info("Lua script [{}] loaded successfully, sha1 = [{}], content = \n[{}]",
                    filename, script.getSha1(), script.getScriptAsString());
            scriptHolder.put(resource.getFilename(), script);
        }
    }

    public RedisScript<Long> find(String scriptName) {
        return scriptHolder.get(scriptName);
    }

}
