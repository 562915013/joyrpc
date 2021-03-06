package io.joyrpc.codec.serialization.hessian2;

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

import io.joyrpc.com.caucho.hessian.io.AbstractSerializerFactory;
import io.joyrpc.com.caucho.hessian.io.Deserializer;
import io.joyrpc.com.caucho.hessian.io.HessianProtocolException;
import io.joyrpc.com.caucho.hessian.io.Serializer;
import io.joyrpc.com.caucho.hessian.io.java8.*;

import java.time.ZoneId;
import java.util.HashMap;

/**
 * 自定义序列化器工厂类
 */
public class Java8SerializerFactory extends AbstractSerializerFactory {

    protected HashMap<String, Serializer> serializers = new HashMap<>();

    /**
     * 构造函数
     */
    public Java8SerializerFactory() {
        ZoneId zoneId = ZoneId.systemDefault();
        serializers.put(java.time.LocalTime.class.getName(), Java8TimeSerializer.of(LocalTimeHandle.class));
        serializers.put(java.time.LocalDate.class.getName(), Java8TimeSerializer.of(LocalDateHandle.class));
        serializers.put(java.time.LocalDateTime.class.getName(), Java8TimeSerializer.of(LocalDateTimeHandle.class));

        serializers.put(java.time.Instant.class.getName(), Java8TimeSerializer.of(InstantHandle.class));
        serializers.put(java.time.Duration.class.getName(), Java8TimeSerializer.of(DurationHandle.class));
        serializers.put(java.time.Period.class.getName(), Java8TimeSerializer.of(PeriodHandle.class));

        serializers.put(java.time.Year.class.getName(), Java8TimeSerializer.of(YearHandle.class));
        serializers.put(java.time.YearMonth.class.getName(), Java8TimeSerializer.of(YearMonthHandle.class));
        serializers.put(java.time.MonthDay.class.getName(), Java8TimeSerializer.of(MonthDayHandle.class));

        serializers.put(java.time.OffsetTime.class.getName(), Java8TimeSerializer.of(OffsetTimeHandle.class));
        serializers.put(java.time.ZoneOffset.class.getName(), Java8TimeSerializer.of(ZoneOffsetHandle.class));
        serializers.put(java.time.OffsetDateTime.class.getName(), Java8TimeSerializer.of(OffsetDateTimeHandle.class));
        serializers.put(java.time.ZonedDateTime.class.getName(), Java8TimeSerializer.of(ZonedDateTimeHandle.class));
        serializers.put(ZoneId.class.getName(), Java8TimeSerializer.of(ZoneIdHandle.class));
        serializers.put(zoneId.getClass().getName(), Java8TimeSerializer.of(ZoneIdHandle.class));
    }

    @Override
    public Serializer getSerializer(final Class cl) throws HessianProtocolException {
        return serializers.get(cl.getName());
    }

    @Override
    public Deserializer getDeserializer(final Class cl) throws HessianProtocolException {
        return null;
    }
}
