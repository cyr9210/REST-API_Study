package me.yrchoi.hateostest;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Hello {

    private String prefix;

    private String name;

    @Override
    public String toString() {
        return prefix + " " + name;
    }
}
