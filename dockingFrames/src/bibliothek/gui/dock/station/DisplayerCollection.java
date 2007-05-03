package bibliothek.gui.dock.station;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import bibliothek.gui.DockController;
import bibliothek.gui.dock.DockStation;
import bibliothek.gui.dock.Dockable;
import bibliothek.gui.dock.DockableDisplayer;
import bibliothek.gui.dock.title.DockTitle;

/**
 * A set of {@link DockableDisplayer DockableDisplayers}. Clients may
 * {@link #fetch(Dockable, DockTitle) fetch} a new displayer at any time,
 * and the can {@link #release(DockableDisplayer) release} a displayer which
 * is no longer used. The collection ensures that various properties of the
 * displayers are set in the proper order.
 * @author Benjamin Sigg
 *
 */
public class DisplayerCollection implements Iterable<DockableDisplayer>{
    /** the station for which displayers are created */
    private DockStation station;
    
    /** the current controller, all displayer should know this controller */
    private DockController controller;
    
    /** a factory used to create new displayers */
    private DisplayerFactory factory;
    
    /** the set of displayers that are fetched but not released */
    private List<DockableDisplayer> displayers = new ArrayList<DockableDisplayer>();
    
    /**
     * Creates a new collection
     * @param station the station for which {@link DockableDisplayer} will be created
     * @param factory the factory that is initially used to create displayers
     */
    public DisplayerCollection( DockStation station, DisplayerFactory factory ){
        if( station == null )
            throw new IllegalArgumentException( "Station must not be null" );
        
        if( factory == null )
            throw new IllegalArgumentException( "Factory must not be null" );
        
        this.station = station;
        this.factory = factory;
    }
    
    public Iterator<DockableDisplayer> iterator() {
        return displayers.iterator();
    }
    
    /**
     * Creates a new {@link DockableDisplayer} using the {@link #setFactory(DisplayerFactory) factory}
     * of this collection. This method also sets the {@link DockableDisplayer#setTitle(DockTitle) title},
     * {@link DockableDisplayer#setStation(DockStation) station}
     * and the {@link DockableDisplayer#setController(DockController) controller} property of
     * the displayer.<br>
     * If the displayer is no longer needed, then it should be {@link #release(DockableDisplayer) released}
     * @param dockable the Dockable which will be shown on the displayer.
     * @param title the title which will be shown on the displayer, might be <code>null</code>
     * @return the new displayer
     */
    public DockableDisplayer fetch( Dockable dockable, DockTitle title ){
        DockableDisplayer displayer = factory.create( station, dockable, title );
        displayer.setTitle( title );
        displayer.setStation( station );
        displayer.setController( controller );
        displayers.add( displayer );
        return displayer;
    }
    
    /**
     * Releases a displayer that was created by this collection.
     * @param displayer the displayer to release
     */
    public void release( DockableDisplayer displayer ){
        displayers.remove( displayer );
        displayer.setTitle( null );
        displayer.setStation( null );
        displayer.setController( null );
    }
    
    /**
     * Sets the factory that will create new {@link DockableDisplayer} when
     * needed.
     * @param factory the new factory, not <code>null</code>
     */
    public void setFactory( DisplayerFactory factory ){
        if( factory == null )
            throw new IllegalArgumentException( "Factory must not be null" );
        
        this.factory = factory;
    }
    
    /**
     * Sets the current {@link DockController}, that controller will be made
     * known to all {@link DockableDisplayer} created by this collection.
     * @param controller the new controller, can be <code>null</code>
     */
    public void setController( DockController controller ){
        if( this.controller != controller ){
            this.controller = controller;
            for( DockableDisplayer displayer : displayers )
                displayer.setController( controller );
        }
    }
}