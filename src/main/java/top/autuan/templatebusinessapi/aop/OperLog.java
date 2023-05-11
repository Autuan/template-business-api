package top.autuan.templatebusinessapi.aop;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 日志注解
 *
 * @author Autuan.Yu
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface OperLog {
    /**
     * 标题
     */
    String title() default "";

    /**
     * 备注
     */
    String remark() default "";

    /**
     * 是否保存请求的参数
     */
    boolean isSaveRequestData() default true;

    /**
     * 异步保存到数据库
     */
    boolean isAsyncSaveToDB() default false;
}
