package com.ark.center.product.infra.product.builder;

import lombok.Getter;
import lombok.Setter;

import java.util.StringJoiner;

@Getter
@Setter
public class GoodsBuildProfiles {

    private Boolean withPictures = true;
    private Boolean withSaleInfo = true;

    @Override
    public String toString() {
        return new StringJoiner(", ")
                .add("withPictures=" + withPictures)
                .toString();
    }

}
