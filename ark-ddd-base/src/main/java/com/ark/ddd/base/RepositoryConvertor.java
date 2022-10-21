package com.ark.ddd.base;

import cn.hutool.core.util.TypeUtil;
import com.ark.component.web.util.bean.BeanConvertor;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.lang.reflect.Type;
import java.util.List;

@SuppressWarnings("all")
public class RepositoryConvertor<A, D> {

    private Class<A> aggClazz;
    private Class<D> dataObjectClazz;

    public RepositoryConvertor() {
        Type[] arguments = TypeUtil.getTypeArguments(this.getClass());
        aggClazz = (Class<A>) arguments[0];
        dataObjectClazz = (Class<D>) arguments[1];
    }

//    public PageResponse<A> toAggregate(IPage<D> dataObject) {
//        IPage<A> convert = dataObject.convert(item -> BeanConvertor.copy(item, aggClazz));
//        return BeanConvertor.copyPage(convert, aggClazz);
//    }


    public IPage<A> toAggregate(IPage<D> dataObject) {
        return dataObject.convert(item -> BeanConvertor.copy(item, aggClazz));
    }

    public A toAggregate(D dataObject) {
        return BeanConvertor.copy(dataObject, aggClazz);
    }

    public List<A> toAggregate(List<D> dataObject) {
        return BeanConvertor.copyList(dataObject, aggClazz);
    }

    public D fromAggregate(A aggregate) {
        return BeanConvertor.copy(aggregate, dataObjectClazz);
    }

}
