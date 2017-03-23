/*
 * MAColoredSlipStickChart.java
 * Android-Charts
 *
 * Created by limc on 2015.
 *
 * Copyright 2011 limc.cn All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.limc.androidcharts.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;

import java.util.List;

import cn.limc.androidcharts.entity.DateValueEntity;
import cn.limc.androidcharts.entity.LineEntity;

/**
 * <p>
 * en
 * </p>
 * <p>
 * jp
 * </p>
 * <p>
 * cn
 * </p>
 * 
 * @author limc
 * @version v1.0 2014/01/21 10:55:40
 * 
 */
public class MAColoredSlipStickChart extends ColoredSlipStickChart {

	/**
	 * <p>
	 * data to draw lines
	 * </p>
	 * <p>
	 * ラインを書く用データ
	 * </p>
	 * <p>
	 * 绘制线条用的数据
	 * </p>
	 */
	private List<LineEntity<DateValueEntity>> linesData;

	/**
	 * <p>
	 * Constructor of MASlipStickChart
	 * </p>
	 * <p>
	 * MASlipStickChart类对象的构造函数
	 * </p>
	 * <p>
	 * MASlipStickChartのコンストラクター
	 * </p>
	 *
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public MAColoredSlipStickChart(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	/**
	 * <p>
	 * Constructor of MASlipStickChart
	 * </p>
	 * <p>
	 * MASlipStickChart类对象的构造函数
	 * </p>
	 * <p>
	 * MASlipStickChartのコンストラクター
	 * </p>
	 *
	 * @param context
	 * @param attrs
	 */
	public MAColoredSlipStickChart(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	/**
	 * <p>
	 * Constructor of MASlipStickChart
	 * </p>
	 * <p>
	 * MASlipStickChart类对象的构造函数
	 * </p>
	 * <p>
	 * MASlipStickChartのコンストラクター
	 * </p>
	 *
	 * @param context
	 */
	public MAColoredSlipStickChart(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void calcDataValueRange() {
		super.calcDataValueRange();

		double maxValue = this.maxValue;
		double minValue = this.minValue;
		// 逐条输出MA线
		for (int i = 0; i < this.linesData.size(); i++) {
			LineEntity<DateValueEntity> line = this.linesData.get(i);
			if (line != null && line.getLineData().size() > 0) {
				// 判断显示为方柱或显示为线条
				for (int j = getDisplayFrom(); j < getDisplayTo(); j++) {
					DateValueEntity lineData = line.getLineData().get(j);
                    if (isNoneDisplayValue(lineData.getValue())){

                    }else {
                        if (lineData.getValue() < minValue) {
                            minValue = lineData.getValue();
                        }

                        if (lineData.getValue() > maxValue) {
                            maxValue = lineData.getValue();
                        }
                    }
				}
			}
		}
		this.maxValue = maxValue;
		this.minValue = minValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * <p>Called when is going to draw this chart<p> <p>チャートを書く前、メソッドを呼ぶ<p>
	 * <p>绘制图表时调用<p>
	 * 
	 * @param canvas
	 * 
	 * @see android.view.View#onDraw(android.graphics.Canvas)
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}
	
	@Override
	public void drawData(Canvas canvas){
		super.drawData(canvas);
		if (getDisplayNumber() > displayStickAsLineNumber){

		}else{
			drawLines(canvas);
		}
	}

	/**
	 * <p>
	 * draw lines
	 * </p>
	 * <p>
	 * ラインを書く
	 * </p>
	 * <p>
	 * 绘制线条
	 * </p>
	 * 
	 * @param canvas
	 */
	protected void drawLines(Canvas canvas) {
		if (null == linesData) {
			return;
		}
		if (linesData.size() <= 0) {
			return;
		}
		// distance between two points
		float lineLength = dataQuadrant.getPaddingWidth() / getDisplayNumber() - stickSpacing;
		// start point‘s X
		float startX;

		// draw MA lines
		for (int i = 0; i < linesData.size(); i++) {
			LineEntity<DateValueEntity> line = (LineEntity<DateValueEntity>) linesData
					.get(i);
			if (line == null) {
				continue;
			}
			if (line.isDisplay() == false) {
				continue;
			}
			List<DateValueEntity> lineData = line.getLineData();
			if (lineData == null) {
				continue;
			}

			Paint mPaint = new Paint();
			mPaint.setColor(line.getLineColor());
			mPaint.setAntiAlias(true);
			// set start point’s X
			startX = dataQuadrant.getPaddingStartX() + lineLength / 2;
			// start point
			PointF ptFirst = null;
			for (int j = super.getDisplayFrom(); j < super.getDisplayFrom()
					+ super.getDisplayNumber(); j++) {
				float value = lineData.get(j).getValue();
                if(isNoneDisplayValue(value)){
                }else{
                    // calculate Y
                    float valueY = (float) ((1f - (value - minValue)
                            / (maxValue - minValue)) * dataQuadrant.getPaddingHeight())
                            + dataQuadrant.getPaddingStartY();

                    // if is not last point connect to previous point
                    if (j > super.getDisplayFrom() && ptFirst != null) {
                        canvas.drawLine(ptFirst.x, ptFirst.y, startX, valueY,
                                mPaint);
                    }
                    // reset
                    ptFirst = new PointF(startX, valueY);
                }
				startX = startX + stickSpacing + lineLength;
			}
		}
	}

	/**
	 * @return the linesData
	 */
	public List<LineEntity<DateValueEntity>> getLinesData() {
		return linesData;
	}

	/**
	 * @param linesData
	 *            the linesData to set
	 */
	public void setLineData(List<LineEntity<DateValueEntity>> linesData) {
		this.linesData = linesData;
	}
}
