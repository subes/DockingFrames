package bibliothek.gui.dock.extension.css.intern;

import java.awt.Color;

import bibliothek.gui.dock.extension.css.CssSelector;
import bibliothek.gui.dock.extension.css.intern.range.Range;
import bibliothek.gui.dock.extension.css.type.ColorType;

public class TestCssRules {
	public static TestCssScheme getNoAnimationScheme(){
		TestCssScheme scheme = new TestCssScheme();
		scheme.addRule( getColor( "white" ) );
		scheme.addRule( getColor( "black" ) );
		return scheme;
	}
	
	public static TestCssScheme getAnimatedColorScheme(){
		TestCssScheme scheme = new TestCssScheme();
		scheme.addRule( getAnimatedColor( "white", Color.WHITE ) );
		scheme.addRule( getAnimatedColor( "black", Color.BLACK ) );
		scheme.addRule( getAnimatedColor( "red", Color.RED ) );
		scheme.addRule( getAnimatedColor( "green", Color.GREEN ) );
		scheme.addRule( getAnimatedColor( "blue", Color.BLUE ) );
		return scheme;
	}
	
	public static TestCssScheme getAnimatedRangeScheme(){
		TestCssScheme scheme = new TestCssScheme();
		scheme.setConverter( Range.class, Range.TYPE );
		scheme.addRule( getAnimatedRange( "alpha", 0, 0 ) );
		scheme.addRule( getAnimatedRange( "beta",  1000, 0 ) );
		scheme.addRule( getAnimatedRange( "gamma", 0, 1000 ) );
		scheme.addRule( getAnimatedRange( "delta", 1000, 1000 ) );
		return scheme;
	}
	
	private static CssSelector selector( String identifier ){
		return DefaultCssSelector.selector().any().identifier( identifier ).build();
	}
	
	private static DefaultCssRule getColor( String color ){
		DefaultCssRule rule = new DefaultCssRule( selector( color ) );
		rule.setProperty( "color", color );
		return rule;
	}
	
	private static DefaultCssRule getAnimatedColor( String color, Color value ){
		DefaultCssRule rule = new DefaultCssRule( selector( color ) );
		rule.setProperty( "color", ColorType.convert( value ) );
		rule.setProperty( "color-animation", "linear" );
		rule.setProperty( "color-animation-duration", "10000" );
		return rule;
	}
	
	private static DefaultCssRule getAnimatedRange( String name, int min, int max ){
		DefaultCssRule rule = new DefaultCssRule( selector( name ) );
		rule.setProperty( "range", name );
		rule.setProperty( "range-min", String.valueOf( min ) );
		rule.setProperty( "range-max", String.valueOf( max ) );
		rule.setProperty( "range-animation", "linear" );
		rule.setProperty( "range-animation-duration", "10000" );
		return rule;
	}
}
