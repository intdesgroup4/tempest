package WeatherApp;

import org.jxmapviewer.viewer.GeoPosition;

public class FieldCapsule {
	private GeoPosition coords;
	private String name;
	
	public FieldCapsule(GeoPosition crds, String nme) {
		setCoords(crds);
		name = nme;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public GeoPosition getCoords() {
		return coords;
	}

	public void setCoords(GeoPosition coords) {
		this.coords = coords;
	}

	
}
