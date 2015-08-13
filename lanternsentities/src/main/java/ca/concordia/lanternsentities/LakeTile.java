package ca.concordia.lanternsentities;

import java.util.Arrays;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;

import ca.concordia.lanternsentities.enums.Colour;

/**
 * Lake Tile entity
 *
 * @version 1.0
 */
@XmlRootElement
public class LakeTile {

    /**
     * Each Lake Tile has 4 sides.
     */
    public static final int TOTAL_SIDES = 4;

    private TileSide[] sides;
    private boolean platform;
    /** says original index of the current first tile*/
    private int direction;

    private String id;

    /**
     * Each Lake tile has four sides, each side can have a colour, there are 7 different colours.
     *
     * @param colours  There are 7 possible colours.
     * @param platform Says if tile has a platform.
     * @throws IllegalArgumentException if colours size does not match {@link #TOTAL_SIDES}.
     */
    public void init(Colour[] colours, boolean platform) {
        this.platform = platform;
        this.sides = new TileSide[TOTAL_SIDES];

        // populate sides
        if (colours != null && colours.length == TOTAL_SIDES) {
            for (int i = 0; i < TOTAL_SIDES; i++) {
                TileSide side = new TileSide();
                side.init(colours[i]);
                sides[i] = side;
            }
        } else {
            throw new IllegalArgumentException("Invalid colours size!");
        }

        this.id = this.toShortString();
        this.direction = 0;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = result + sidesHashCode();
        result = prime * result + (platform ? 1231 : 1237);
        return result;
    }

    public int sidesHashCode() {
        final int prime = 31;
        int result = 1;
        for (TileSide side : sides) {
            result = prime * result + side.hashCode();
        }
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
        LakeTile other = (LakeTile) obj;
        if (platform != other.platform)
            return false;

        TileSide firstSide = this.getSides()[0];
        boolean matchColours = false;
        for (int i = 0; i < other.getSides().length; i++) {
            if (firstSide.getColour() == other.getSides()[i].getColour()) {
                LakeTile copy;
                try {
                    copy = (LakeTile) other.clone();
                    copy.setOrientation(i);
                    if (matchAll(copy)) {
                        matchColours = true;
                        break;
                    }
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
            }
        }

        return matchColours;
    }

    private boolean matchAll(LakeTile copy) {
        boolean matchAll = true;
        for (int i = 0; i < this.sides.length; i++) {
            if (this.sides[i].getColour() != copy.getSides()[i].getColour()) {
                matchAll = false;
                break;
            }
        }
        return matchAll;
    }

    @Override
    public String toString() {
        return "LakeTile [sides=" + Arrays.toString(sides) + ", platform=" + platform + ", id=" + this.getId() + "]";
    }

    public String toShortString() {
        StringBuffer sb = new StringBuffer();
        for (TileSide side : sides) {
            sb.append(side.getColour().key);
        }
        if (platform) {
            sb.append("$");
        } else {
            sb.append("_");
        }
        return sb.toString();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        LakeTile clone = new LakeTile();
        Colour[] colours = new Colour[TOTAL_SIDES];
        for (int i = 0; i < colours.length; i++) {
            colours[i] = this.sides[i].getColour();
        }
        clone.init(colours, this.platform);

        return clone;
    }

    public TileSide[] getSides() {
        return sides;
    }

    public void setSides(TileSide[] sides) {
        this.sides = sides;
    }

    @XmlElement(required = true, nillable = true)
    public boolean isPlatform() {
        return platform;
    }

    public void setPlatform(boolean platform) {
        this.platform = platform;
    }

    @XmlID
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @XmlElement
    public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	/**
     * Allows for the rotation of a lake Tile.
     * <p>
     * Moves the tileSide[orientation] to the tileSide[0] and rotates the tile accordingly.
     * <p>
     * Assumes that tiles have default orientation with Side[0] facing Player ID 0 and rest of sides increase in count going
     * clockwise.
     *
     * @param orientation tileSide position that will get moved to 0th position.
     */
    public void setOrientation(int orientation) {

        try {
            // Moves the orientation index to a new array 0 index and circles around the TileSide array to "rotate" the tile
            // sides.
            TileSide[] orientedSides = Arrays.copyOfRange(this.sides, orientation, TOTAL_SIDES + orientation);
            for (int i = 0, j = TOTAL_SIDES - orientation; i != orientation && j != TOTAL_SIDES; ++i, ++j) {
                orientedSides[j] = this.sides[i];
            }
            this.sides = orientedSides;
            this.direction = (this.direction + orientation) % TOTAL_SIDES;
        } catch (IndexOutOfBoundsException e) {
            throw new IndexOutOfBoundsException("Cannot Enter an orientation greater than Number of sides");
        }

    }

	/**
	 * Writes a TileStack into an array of 3 strings, in the following format:
	 * <ul>
	 * 	<li>If the TileStack is null:
	 * 		<ul>
	 * 			<li><code>[&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;]</code></li>
	 * 			<li><code>[&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;]</code></li>
	 * 			<li><code>[&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;]</code></li>
	 * 		</ul>
	 * 	</li>
	 * 	<li>If the TileStack is not null:
	 * 		<ul>
	 * 			<li><code>[&nbsp;&nbsp;N&nbsp;&nbsp;]</code></li>
	 * 			<li><code>[W&nbsp;p&nbsp;E]</code></li>
	 * 			<li><code>[&nbsp;&nbsp;S&nbsp;&nbsp;]</code></li>
	 * 		</ul>
	 * 		Where the {N,E,S,W} will be the {@link Colour#key} on the side North, East, 
	 * South or West. Ant the {p} will be "*" if the tile has a platform of "." if it doesn't.  
	 * 	</li>
	 * </ul>
	 * @param lt LakeTile
	 * @return	The 3 lines array.
	 */
	public static String[] get3Lines(LakeTile lt) {
		String[] lines = new String[3];
		if (lt == null) {
			lines[0] = "[     ]";
			lines[1] = "[     ]";
			lines[2] = "[     ]";
		} else {
			TileSide[] ss = lt.getSides();
			char plat = lt.isPlatform() ? '$' : '_';
			lines[0] = "[  " + ss[3].getColour().key + "  ]";
			lines[1] = "[" + ss[2].getColour().key + " " + plat + " " + ss[0].getColour().key + "]";
			lines[2] = "[  " + ss[1].getColour().key + "  ]";
		}
		return lines;
	}

}
