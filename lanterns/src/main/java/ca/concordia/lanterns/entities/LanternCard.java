package ca.concordia.lanterns.entities;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import ca.concordia.lanterns.entities.enums.Colour;

/** Lantern Card entity
 * <P> Various attributes of the Lantern Cards and related behaviors.
 * @version 1.0
 *
 */
@XmlRootElement
public class LanternCard {
	private Colour colour;

	/**
	 * 
	 * @param colour There are 7 possible lantern colours.
	 */
	public void init(Colour colour) {
		this.colour = colour;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((colour == null) ? 0 : colour.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LanternCard other = (LanternCard) obj;
		if (colour != other.colour)
			return false;
		return true;
	}

	@XmlAttribute
	public Colour getColour() {
		return colour;
	}

	public void setColour(Colour colour) {
		this.colour = colour;
	}
}
