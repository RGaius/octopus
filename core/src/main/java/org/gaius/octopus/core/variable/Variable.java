package org.gaius.octopus.core.variable;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 变量
 *
 * @author gaius.zhao
 * @date 2025/10/10
 */
@Data
public abstract class Variable {
    
    private String id;
    
    private String name;
    
    private String description = "";
    
    private List<String> selector = new ArrayList<>();
    
    public Variable() {
        this.id = UUID.randomUUID().toString();
    }
}
