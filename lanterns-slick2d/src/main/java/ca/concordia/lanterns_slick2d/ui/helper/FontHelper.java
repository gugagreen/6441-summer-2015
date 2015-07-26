package ca.concordia.lanterns_slick2d.ui.helper;

import java.awt.Color;
import java.awt.Font;

import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

public class FontHelper {
	
	public static final String ARIAL = "Arial";
	public static final int DEFAULT_SIZE = 16;
	
	public static UnicodeFont getDefaultFont() {
		return getNewFont(ARIAL, DEFAULT_SIZE);
	}
	
	public static UnicodeFont getNewFont(String fontName , int fontSize){
        return getNewFont(fontName, fontSize, Font.PLAIN, Color.WHITE);
    }
	
	public static UnicodeFont getNewFont(String fontName , int fontSize, int fontStyle, Color color){
        UnicodeFont returnFont = new UnicodeFont(new Font(fontName , fontStyle, fontSize));
        returnFont.addAsciiGlyphs();
        returnFont.getEffects().add(new ColorEffect(color));
        return (returnFont);
    }
}
