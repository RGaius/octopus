package org.gaius.octopus.core.pojo.vo;

import lombok.Data;

/**
 * @author zhaobo
 * @program octopus
 * @description 接口响应对象
 * @date 2024/6/29
 */
@Data
public class InterfaceResponseVO<T> {
    
    private boolean success;
    
    private T data;
    
    
    public static InterfaceResponseVO<Object> success(Object data) {
        InterfaceResponseVO<Object> response = new InterfaceResponseVO<>();
        response.setSuccess(true);
        response.setData(data);
        return response;
    }
    
    public static InterfaceResponseVO<String> fail(String data) {
        InterfaceResponseVO<String> response = new InterfaceResponseVO<>();
        response.setSuccess(false);
        response.setData(data);
        return response;
    }
}
