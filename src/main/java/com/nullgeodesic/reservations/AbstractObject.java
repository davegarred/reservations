package com.nullgeodesic.reservations;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

public abstract class AbstractObject {

	@Override
	public String toString() {
		return reflectionToString(this);
	}

	@Override
	public int hashCode() {
		return reflectionHashCode(this, false);
	}

	@Override
	public boolean equals(Object that) {
		return reflectionEquals(this, that, false);
	}

}
