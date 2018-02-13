
var format = "image/png";

// for texas
//var bounds = [-106.734184265137, 25.8476467132568,
//	-93.4293441772461, 36.5561485290527];
//var geoserverURL = "http://localhost:8080/geoserver/Project/wms";
//var mapLayerName = "Project:map";
//var cityLayerName = "Project:county_point_texas_gcs_sp2";


// for india
var bounds = [68.5625381469727, 7.94741249084473,
    93.2261657714844, 35.5688667297363];
var geoserverURL = "http://localhost:8080/geoserver/Project/wms";
var mapLayerName = "Project:osm_roads";
var cityLayerName = "Project:county_point_texas_gcs_sp2";



// used to display mouse position on the map on screen
var mousePositionControl = new ol.control.MousePosition({
	className: 'custom-mouse-position',
	target: document.getElementById('location'),
	coordinateFormat: ol.coordinate.createStringXY(5),
	undefinedHTML: '&nbsp;'
});

// the main map layer
var untiled = new ol.layer.Image({
	source: new ol.source.ImageWMS({
		ratio: 1,
		url: geoserverURL,
		params: {'FORMAT': format,
			'VERSION': '1.1.1',  
			STYLES: '',
			LAYERS: mapLayerName,
		}
	})
}); 
var cityPoints = new ol.layer.Image({
	source: new ol.source.ImageWMS({
		ratio: 1,
		url: geoserverURL,
		params: {'FORMAT': format,
			'VERSION': '1.1.1',  
			STYLES: '',
			LAYERS: cityLayerName,
		}
	})
});

// Projection of the map
var projection = new ol.proj.Projection({
	code: 'EPSG:2024',
	units: 'm',
	axisOrientation: 'neu',
	global: false
});

// Map object  which have all the properties of the map
var map = new ol.Map({
	controls: ol.control.defaults({
		attribution: false
	}).extend([mousePositionControl]),
	target: 'map',
	layers: [
		untiled
		],
		view: new ol.View({
			projection: projection
		})
}); 

// to display map in the window
map.getView().fit(bounds, map.getSize());      

//map.addLayer(cityPoints);

// styles 
var iconStyle = new ol.style.Style({
	image: new ol.style.Circle({
		radius: 3,
		fill: new ol.style.Fill({
			color: [255, 0, 0, 1]
		}),
		stroke: new ol.style.Stroke({
			color: [255, 0, 0, 1],
			width: 1.5
		})
	}),
	zIndex: 1
});

var routeStyle = new ol.style.Style({
	fill: new ol.style.Fill({
		color: [255, 0, 0, 1]
	}),
	stroke : new ol.style.Stroke({
		color : [255,0,0,1],
		width: 2
	})
});


// functions


function getPointFeature(x, y){
	var pointFeature = new ol.Feature({ geometry: new ol.geom.Point([x,y])});
	pointFeature.setStyle(iconStyle);
	return pointFeature;
}

function getPointLayer(pointFeature){
	return new ol.layer.Vector({
		source: new ol.source.Vector({
			features: [pointFeature]
		})
	});
}



