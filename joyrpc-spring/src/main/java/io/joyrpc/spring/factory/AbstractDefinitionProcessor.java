package io.joyrpc.spring.factory;

/*-
 * #%L
 * joyrpc
 * %%
 * Copyright (C) 2019 joyrpc.io
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import io.joyrpc.config.MethodConfig;
import io.joyrpc.spring.annotation.Method;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.Map;

/**
 * 处理含有provider注解bean定义的处理类
 */
public abstract class AbstractDefinitionProcessor implements ServiceBeanDefinitionProcessor {


    /**
     * 构建配置
     *
     * @param methods
     * @param env
     * @return
     */
    public Map<String, MethodConfig> build(final Method[] methods, final Environment env) {
        if (methods == null || methods.length == 0) {
            return new HashMap<>();
        }

        Map<String, MethodConfig> methodConfigs = new HashMap<>(methods.length);
        for (int i = 0; i < methods.length; i++) {
            Method m = methods[i];
            MethodConfig config = new MethodConfig();
            config.setName(env.resolvePlaceholders(m.name()));
            config.setRetries(m.retries());
            config.setValidation(m.validation());
            config.setConcurrency(m.concurrency());
            config.setCache(m.cache());
            config.setCacheProvider(env.resolvePlaceholders(m.cacheProvider()));
            config.setCacheKeyGenerator(env.resolvePlaceholders(m.cacheKeyGenerator()));
            config.setCacheExpireTime(m.cacheExpireTime());
            config.setCacheCapacity(m.cacheCapacity());
            config.setCompress(env.resolvePlaceholders(m.compress()));
            config.setDstParam(m.dstParam());
            config.setParameters(buildParameters(m.parameters(), env));
            methodConfigs.put(m.name(), config);
        }
        return methodConfigs;
    }

    /**
     * 构造参数
     *
     * @param parameters
     * @param env
     * @return
     */
    protected Map<String, String> buildParameters(final String[] parameters, final Environment env) {
        if (parameters.length > 0) {
            Map<String, String> p = new HashMap<>(parameters.length / 2);
            for (int l = 0; l < parameters.length; l = l + 2) {
                p.put(env.resolvePlaceholders(parameters[l]), env.resolvePlaceholders(parameters[l + 1]));
            }
            return p;
        }
        return null;
    }

}