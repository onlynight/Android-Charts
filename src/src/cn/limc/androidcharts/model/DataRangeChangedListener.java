//
// DataRangeChangedListener.java
// cn.limc.androidcharts.model
//
// Created by limc on 2015-8-31.
//
// Copyright 2015 Shanghai Okasan-Huada Computer System Co. Ltd., All rights reserved.
//
package cn.limc.androidcharts.model;

/**
 * DataRangeChangedListener
 * Description: <br>
 *   <p>add description here </p>
 * Tags: <br>
 *   <p> </p>
 *
 * @author limc
 * @version v1.0 
 * 
 * History: <br>
 * 2015-8-31 limc create v1.0 <br>
 *
 */
public interface DataRangeChangedListener {
    void onRangeChanged(DataRange dataRange ,double minValue, double maxValue);
}