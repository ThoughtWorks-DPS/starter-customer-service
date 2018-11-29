package io.twdps.starter.custservice.configuration;

import io.jaegertracing.internal.JaegerTracer;
import io.jaegertracing.internal.propagation.B3TextMapCodec;
import io.opentracing.Tracer;
import io.opentracing.propagation.Format;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TracerConfig {

  private final String serviceName = "custservice";

  @Bean
  public Tracer jaegerTracer() {
    B3TextMapCodec codec = new B3TextMapCodec.Builder().build();
    Tracer tracer = new JaegerTracer.Builder(serviceName)
        .registerInjector(Format.Builtin.HTTP_HEADERS, codec)
        .registerExtractor(Format.Builtin.HTTP_HEADERS, codec)
        .build();
    return tracer;
  }

}
