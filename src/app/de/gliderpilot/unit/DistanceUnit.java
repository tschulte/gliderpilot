/*
 * Created on Oct 4, 2003
 * 
 * Copyright 2003 Tobias Schulte
 */
package de.gliderpilot.unit;

import java.text.NumberFormat;

/**
 * Represents a Distance Unit. 
 * <p>
 * The class is designed so that there's never more than one
 * <code>DistanceUnit</code> instance for any given Distance Unit. 
 * Therefore, there's
 * no public constructor. You obtain a <code>DistanceUnit</code> instance using
 * one of the constants.
 *
 * @author tobias
 */
public class DistanceUnit {
	
	public static final DistanceUnit METER = new DistanceUnit("m", 0, 1);
	public static final DistanceUnit KILOMETER = new DistanceUnit("km", 3, 1000);
	public static final DistanceUnit foot = new DistanceUnit("ft", 3, 0.3);
	public static final DistanceUnit NAUTICAL_MILE = new DistanceUnit("nm", 3, 1852);
	public static final DistanceUnit STATUTE_MILE = new DistanceUnit("mile", 3, 1600);
	
	
	private double amountOfMeter;
	private int defaultFractionDigits;
	private String symbol;
	private NumberFormat format;
	
	private DistanceUnit(String symbol, int defaultFractionDigits, double amountOfMeter) {
		this.symbol = symbol;
		this.defaultFractionDigits = defaultFractionDigits;
		this.amountOfMeter = amountOfMeter;
	}

	int getDefaultFractionDigits() {
		return defaultFractionDigits;
	}
	
	double getConversionFactor(DistanceUnit destUnit) {
		// the conversionFactor is the amount of meters fit to one source unit
		// divided through the amount of meters fit to one dest unit
		return amountOfMeter / destUnit.amountOfMeter;
	}
	
	public String toString() {
		return symbol;
	}

	public NumberFormat getDefaultFormat() {
		if (format == null) {
			format = NumberFormat.getInstance();
			format.setMaximumFractionDigits(defaultFractionDigits);
		}
		return format;
	}

}
