package com.ark.ddd.base;

import cn.hutool.core.util.TypeUtil;
import com.ark.component.dto.PageResponse;
import com.ark.component.web.util.bean.BeanConvertor;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.lang.reflect.Type;
import java.util.List;

@SuppressWarnings("all")
public class BaseAssembler<A, D> {

    private Class<A> aggClazz;
    private Class<D> dtoClazz;

    public BaseAssembler() {
        Type[] arguments = TypeUtil.getTypeArguments(this.getClass());
        aggClazz = (Class<A>) arguments[0];
        dtoClazz = (Class<D>) arguments[1];
    }

    public PageResponse<D> toDTO(IPage<A> page) {
        return BeanConvertor.copyPage(page, dtoClazz);
    }

    public List<D> toDTO(List<A> list) {
        return BeanConvertor.copyList(list, dtoClazz);
    }

    public D toDTO(A category) {
        return BeanConvertor.copy(category, dtoClazz);
    }

}
