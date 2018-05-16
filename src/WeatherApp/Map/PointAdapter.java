package WeatherApp.Map;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class PointAdapter extends MouseAdapter
{
    private JXMapViewer viewer;

    private DefaultWaypoint waypoint;

    private Point2D pos = new Point2D.Double();

    public PointAdapter(JXMapViewer viewer, DefaultWaypoint waypoint)
    {
        this.viewer = viewer;
        this.waypoint = waypoint;
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
        if (e.getButton() != MouseEvent.BUTTON1)
            return;
        pos.setLocation(e.getX(), e.getY());
        Rectangle2D bounds = viewer.getViewportBounds();
        Point2D pt = new Point2D.Double(bounds.getX() + pos.getX(), bounds.getY() + pos.getY());
        GeoPosition position = viewer.getTileFactory().pixelToGeo(pt, viewer.getZoom());

        waypoint.setPosition(position);
        viewer.repaint();
    }

}