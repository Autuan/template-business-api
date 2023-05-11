package top.autuan.templatebusinessapi.config.exception;

import cn.dev33.satoken.exception.NotLoginException;
import cn.hutool.core.collection.CollUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.autuan.web.Result;
import top.autuan.web.enums.BaseEnum;
import top.autuan.web.exception.BusinessException;

import java.util.HashMap;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class ProjectExceptionHandler {
    private final HashMap<String,BaseEnum> errorMsg = new HashMap<>(2);
    public ProjectExceptionHandler(){
        errorMsg.put("java.io.IOException: Broken pipe", BaseEnum.FAIL);
    }

    @ExceptionHandler({BusinessException.class})
    public Result<Object> handleBusiness(BusinessException e) {
        return Result.fail(e.getCode(), e.getMessage());
    }

    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
    public Result<Object> handleMediaType(HttpMediaTypeNotSupportedException e){
        log.warn("handler HttpMediaTypeNotSupportedException",e);
        return Result.fail(BaseEnum.CONTENT_TYPE_ERROR);
    }
    @ExceptionHandler({NoClassDefFoundError.class})
    public Result<Object> handleNoClassDef(NoClassDefFoundError e){
        log.error("handler NoClassDefFoundError",e);
        return Result.fail(BaseEnum.SERVER_UPGRADE);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result<Object> handleHttp(HttpRequestMethodNotSupportedException e) {
        return Result.fail(BaseEnum.NOT_SUPPORT);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class,
            ServletRequestBindingException.class,
            HttpMessageNotReadableException.class
    })
    public Result<Object> handleValidate(Exception e) {
        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException methodE = (MethodArgumentNotValidException) e;
            List<ObjectError> allErrors = methodE.getAllErrors();
            // 只需要返回第一个即可
            if (CollUtil.isNotEmpty(allErrors)) {

                return Result.fail(allErrors.get(0).getDefaultMessage());
            }
        }
        if (e instanceof HttpMessageNotReadableException) {
            return Result.fail(BaseEnum.JSON_XML_FORMAT_ERROR);
        }
        return Result.fail(BaseEnum.JSON_XML_FORMAT_ERROR);
    }

    @ExceptionHandler({NotLoginException.class})
    public Result<Object> handleNotLogin(NotLoginException e){
        // todo enums
        return Result.fail("500013","请重新登录");
    }

    @ExceptionHandler({Exception.class})
    public Result<Object> handleOther(Exception e) {
        log.error("handler exception ", e);
        return Result.fail(BaseEnum.FAIL);
    }
}
