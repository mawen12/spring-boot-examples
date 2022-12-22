package com.mawen.samples.spring3.service;

/**
 * 计算服务
 */
public interface CalculatingService {

    /**
     * 累加求和
     * @param values 多个累加值
     * @return 累加结果
     */
    Integer sum(Integer... values);

}
