/**
 *
 */
package com.eadmarket.pangu.util.email.impl;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.StringWriter;
import java.util.Map;
import java.util.Properties;

/**
 * @author liuyongpo@gmail.com
 */
public final class VelocityUtil {

  private final static VelocityEngine INSTANCE = new VelocityEngine();
  static {
    try {
      Properties p = new Properties();

      p.put("file.resource.loader.class",
            "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
      //p.put("output.encoding", "UTF-8");
      //p.put("input.encoding", "UTF-8");

      //Velocity.init(p);

      INSTANCE.init(p);
    } catch (Exception ex) {
      throw new RuntimeException("Failed to init Velocity", ex);
    }
  }

  private VelocityUtil() {
  }

  public static String rend(Map<String, Object> ctx, String template) {
    StringWriter writer = new StringWriter();
    try {
      VelocityContext context = new VelocityContext(ctx);
      INSTANCE.evaluate(context, writer, template, template);
      return writer.toString();
    } catch (Exception ex) {
      throw new RuntimeException("Failed to rend template " + template, ex);
    }
  }

  public static String rendVmFile(Map<String, Object> ctx, String templatePath) {
    StringWriter writer = new StringWriter();
    try {
      INSTANCE.mergeTemplate(templatePath, "UTF-8", new VelocityContext(ctx), writer);
      return writer.toString();
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }

}
