package top.autuan.templatebusinessapi.aop;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.autuan.templatebusinesssupport.base.entity.LogOper;
import top.autuan.templatebusinesssupport.base.service.LogOperService;
import top.autuan.web.Result;
import top.autuan.web.enums.BaseEnum;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * OperLogAspect 操作日志切面
 *
 * @author: Autuan.Yu
 * @create: 2020/09/30 16:34
 * @copyright: Toplist
 */
@Aspect
@Slf4j
@Component
public class OperLogAspect {
    /**
     * 因为目前只提供android版本的使用; 暂不定义枚举类
     */
    private static final Integer ANDROID_OPER_TYPE = 2;
    private byte[] KEY = {-106,
            -56,
            -49,
            56,
            -73,
            74,
            -104,
            59,
            -65,
            115,
            -51,
            79,
            -24,
            -22,
            -128,
            -53
    };
    private SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, KEY);
    @Autowired
    private LogOperService logOperService;

    /**
     * 切入点配置
     *
     * @author Autuan.Yu
     */
    @Pointcut("@annotation(top.autuan.templatebusinessapi.aop.OperLog)")
    public void aspectLocation() {

    }

    /**
     * 保存日志
     *
     * @param
     * @return
     * @author Autuan.Yu
     */
    @AfterReturning(pointcut = "aspectLocation()", returning = "jsonResult")
    public void afterReturning(JoinPoint joinPoint, Object jsonResult) {
        saveLog(joinPoint, null, jsonResult);
    }

    @AfterThrowing(value = "aspectLocation()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
        saveLog(joinPoint, e, null);
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private OperLog getAnnotationLog(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null) {
            return method.getAnnotation(OperLog.class);
        }
        return null;
    }

    /**
     * 保存日志
     *
     * @param joinPoint  切点
     * @param exception  异常
     * @param jsonResult 返回结果 ： ResponseResult
     */
    private void saveLog(final JoinPoint joinPoint, final Exception exception, Object jsonResult) {
        try {
            // 获得注解
            OperLog log = getAnnotationLog(joinPoint);
            if (log == null) {
                return;
            }
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            Long logMemberId  = null;
            if (StpUtil.isLogin()) {
             logMemberId = StpUtil.getLoginIdAsLong();
            }

            String simpleName = joinPoint.getTarget().getClass().getSimpleName();
            String methodName = joinPoint.getSignature().getName();
            // 基类获取
            Object[] args = joinPoint.getArgs();
            String operParam = null;
            if (log.isSaveRequestData() && ArrayUtil.isNotEmpty(args) ) {
                // 获取参数的信息，传入到数据库中。
                Object arg = args[0];
                String param = JSONUtil.toJsonStr(arg);
                JSONObject jsonObject = JSONUtil.parseObj(param);

                // 密码加密
                if(jsonObject.containsKey("password")) {
                    String password = jsonObject.getStr("password");
                    if(StrUtil.isNotBlank(password)) {
                        String encryptPassword = aes.encryptHex(password);
                        jsonObject.set("password", encryptPassword);
                    }
                }

                if(jsonObject.containsKey("payPassword")) {
                    String payPassword = jsonObject.getStr("payPassword");
                    if(StrUtil.isNotBlank(payPassword)) {
                        String encryptPayPassword = aes.encryptHex(payPassword);
                        jsonObject.set("payPassword", encryptPayPassword);
                    }
                }

                operParam = StringUtils.substring(jsonObject.toString(), 0, 2000);
            }

            String remoteAddr = request.getRemoteAddr();
            String ip = NetUtil.getMultistageReverseProxyIp(remoteAddr);

//            String machine = request.getHeader(HeaderEnum.MACHINE.getNAME());
//            String version = request.getHeader(HeaderEnum.VERSION.getNAME());

            LogOper logOper = LogOper.builder()
                    .title(log.title())
                    .method(simpleName + "#" + methodName)
                    .operUrl(request.getRequestURI())
                    .operIp(ip)
                    .operParam(operParam)
                    .jsonResult(StringUtils.substring(JSONUtil.toJsonStr(jsonResult), 0, 2000))
                    .remark(log.remark())
//                    .machineCode(machine)
//                    .version(version)
                    .operTime(LocalDateTime.now())
                    .operUserId(logMemberId)
                    .operatorType(ANDROID_OPER_TYPE)
                    .build();

            if (exception != null) {
                logOper.setStatus(1);
                logOper.setErrorMsg(StringUtils.substring(exception.getMessage(), 0, 2000));
            } else if (jsonResult instanceof Result) {
                Result result = (Result) jsonResult;
                if (!BaseEnum.SUCCESS.getCode().equals(result.getCode())) {
                    logOper.setStatus(1);
                    logOper.setErrorMsg(StringUtils.substring(result.getMsg(), 0, 2000));
                }
            }

            if (log.isAsyncSaveToDB()) {
                // todo 异步保存没有充分测试  注意 shutdown 的情况
                logOperService.saveLogAsync(logOper);
            } else {
                logOperService.saveLog(logOper);
            }
        } catch (Exception e) {
            log.error("OperLogAspect -> saveLog -> error ", e);
        }
    }

}
