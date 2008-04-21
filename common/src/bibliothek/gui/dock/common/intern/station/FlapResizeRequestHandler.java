/*
 * Bibliothek - DockingFrames
 * Library built on Java/Swing, allows the user to "drag and drop"
 * panels containing any Swing-Component the developer likes to add.
 * 
 * Copyright (C) 2007 Benjamin Sigg
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 * 
 * Benjamin Sigg
 * benjamin_sigg@gmx.ch
 * CH - Switzerland
 */
package bibliothek.gui.dock.common.intern.station;

import java.awt.Dimension;

import bibliothek.gui.Dockable;
import bibliothek.gui.dock.FlapDockStation;
import bibliothek.gui.dock.FlapDockStation.Direction;
import bibliothek.gui.dock.common.intern.CDockable;

/**
 * A handler that can change the size of a {@link CDockable} such that it
 * has its {@link CDockable#getAndClearResizeRequest() preferred size} if
 * its parent is a {@link FlapDockStation}
 * @author Benjamin Sigg
 *
 */
public class FlapResizeRequestHandler extends AbstractResizeRequestHandler{
    /** the station whose children will be resized */
    private FlapDockStation station;
    
    /**
     * Creates a new handler.
     * @param station the station whose children will be resized
     */
    public FlapResizeRequestHandler( FlapDockStation station ){
        this.station = station;
    }
    
    public void handleResizeRequest() {
        boolean horizontal = station.getDirection() == Direction.SOUTH || station.getDirection() == Direction.NORTH;
        
        for( int i = 0, n = station.getDockableCount(); i<n; i++ ){
            Dockable dockable = station.getDockable( i );
            Dimension size = getAndClearResizeRequest( dockable );
            if( size != null ){
                if( horizontal )
                    station.setWindowSize( dockable, size.width );
                else
                    station.setWindowSize( dockable, size.height );
            }
        }
    }
}