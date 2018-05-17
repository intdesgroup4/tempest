package WeatherApp.Map;

import javafx.embed.swing.SwingNode;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.cache.FileBasedLocalCache;
import org.jxmapviewer.input.CenterMapListener;
import org.jxmapviewer.input.PanKeyListener;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCursor;
import org.jxmapviewer.painter.CompoundPainter;
import org.jxmapviewer.painter.Painter;
import org.jxmapviewer.viewer.*;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MapCreator {
    static public void createAndSetMapViewer(final SwingNode swingNode, DefaultWaypoint waypoint) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Create a TileFactoryInfo for OpenStreetMap
                TileFactoryInfo info = new OSMTileFactoryInfo();
                DefaultTileFactory tileFactory = new DefaultTileFactory(info);

                // Setup local file cache
                File cacheDir = new File("mapViewCache");
                tileFactory.setLocalCache(new FileBasedLocalCache(cacheDir, false));

                // Setup JXMapViewer
                final JXMapViewer mapViewer = new JXMapViewer();
                mapViewer.setTileFactory(tileFactory);





                // Set the focus
                mapViewer.setZoom(7);
                mapViewer.setAddressLocation(waypoint.getPosition());



                // Add interactions
                MouseInputListener mia = new PanMouseInputListener(mapViewer);
                mapViewer.addMouseListener(mia);
                mapViewer.addMouseMotionListener(mia);

                mapViewer.addMouseListener(new CenterMapListener(mapViewer));

                mapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCursor(mapViewer));

                mapViewer.addKeyListener(new PanKeyListener(mapViewer));

                //Waypoint stuff bois
                //
                Set<Waypoint> waypoints = new HashSet<>();
                waypoints.add(waypoint);

                //Test area selection
                PointAdapter pa = new PointAdapter(mapViewer,waypoint);
                mapViewer.addMouseListener(pa);
                mapViewer.addMouseListener(pa);


                // Create a waypoint painter that takes all the waypoints
                WaypointPainter<Waypoint> waypointPainter = new WaypointPainter<Waypoint>();
                waypointPainter.setWaypoints(waypoints);

                // Create a compound painter that uses both the route-painter and the waypoint-painter
                List<org.jxmapviewer.painter.Painter<JXMapViewer>> painters = new ArrayList<Painter<JXMapViewer>>();
                painters.add(waypointPainter);

                CompoundPainter<JXMapViewer> painter = new CompoundPainter<JXMapViewer>(painters);
                mapViewer.setOverlayPainter(painter);


                swingNode.setContent(mapViewer);

                mapViewer.repaint();
            }
        });
        swingNode.requestFocus();
    }
}
