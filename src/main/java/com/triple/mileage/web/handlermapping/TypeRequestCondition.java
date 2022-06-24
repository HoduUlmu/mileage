package com.triple.mileage.web.handlermapping;

import com.triple.mileage.constant.TypeEnum;
import lombok.Getter;
import org.springframework.web.servlet.mvc.condition.RequestCondition;

import javax.servlet.http.HttpServletRequest;

@Getter
public class TypeRequestCondition implements RequestCondition<TypeRequestCondition> {

    private final TypeEnum type;

    public TypeRequestCondition(TypeEnum type) {
        this.type = type;
    }

    @Override
    public TypeRequestCondition combine(TypeRequestCondition other) {
        return new TypeRequestCondition(other.type);
    }

    @Override
    public TypeRequestCondition getMatchingCondition(HttpServletRequest request) {
        TypeEnum type = this.getType(request);
        if (this.type.equals(type)) {
            return this;
        }
        return null;
    }

    @Override
    public int compareTo(TypeRequestCondition other, HttpServletRequest request) {
        return other.type.getOrder() - this.type.getOrder();
    }


    private TypeEnum getType(HttpServletRequest request) {
        return (TypeEnum) request.getAttribute("type");
    }
}
