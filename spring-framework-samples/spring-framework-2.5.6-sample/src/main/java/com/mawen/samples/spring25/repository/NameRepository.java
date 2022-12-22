package com.mawen.samples.spring25.repository;

import com.mawen.samples.spring25.annotation.StringRepository;

import java.util.Arrays;
import java.util.List;

/**
 * 名称仓储，Bean 名称为 "chineseNameRepository"
 */
@StringRepository("chineseNameRepository")
public class NameRepository {

    public List<String> findAll() {
        return Arrays.asList("张三", "李四", "王五");
    }
}
