package org.apache.gobblin.source.extractor;

import com.google.gson.JsonElement;

public interface IWatermark {

	/**
	   * Convert this {@link Watermark} into a {@link JsonElement}.
	   * @return a {@link JsonElement} representing this {@link Watermark}.
	   */
	JsonElement toJson();

	/**
	   * This method must return a value from [0, 100]. The value should correspond to a percent completion. Given two
	   * {@link Watermark} values, where the lowWatermark is the starting point, and the highWatermark is the goal, what
	   * is the percent completion of this {@link Watermark}.
	   *
	   * @param lowWatermark is the starting {@link Watermark} for the percent completion calculation. So if this.equals(lowWatermark) is true, this method should return 0.
	   * @param highWatermark is the end value {@link Watermark} for the percent completion calculation. So if this.equals(highWatermark) is true, this method should return 100.
	   * @return a value from [0, 100] representing the percentage completion of this {@link Watermark}.
	   */
	short calculatePercentCompletion(IWatermark lowWatermark, IWatermark highWatermark);

}