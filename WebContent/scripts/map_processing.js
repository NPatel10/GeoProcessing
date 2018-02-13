//alert("hello");
var operation = 0;
var mouseX;
var mouseY;
var posx;
var posy;
var set1 = 0;
var set2 = 0;
var contentS = "";
var contentD = "";
$(document).mousemove( function(e) {
	mouseX = e.pageX; 
	mouseY = e.pageY;
});

var sourceLayer = new ol.layer.Vector();
var destinationLayer = new ol.layer.Vector();
var routeLayer = new ol.layer.Vector();
var routeTrafficLayer = new ol.layer.Vector();
var directionLayer = new ol.layer.Vector();
$(document).ready(function(){
	map.addLayer(sourceLayer);
	map.addLayer(destinationLayer);
	map.addLayer(routeLayer);
	map.addLayer(routeTrafficLayer);
	map.addLayer(directionLayer);
	// to get source
	$("#get_sorce").on("click", function(){
		operation = 1;
	}); 
	// to get destination
	$("#get_destination").on("click", function(){
		operation = 2;
	});

	// to get route id for traffic update.
	$("#get_route_id").on("click", function(){
		operation = 3;
	});

	$("#map").mousedown(function(){
		posx = mouseX;
		posy = mouseY;
	});

	$("#map").on("mouseup", function(){
		if(posx != mouseX || posy != mouseY){
			return;
		}
		var coordinate = $("#location").text();
		var coordXY = coordinate.split(", ");
		if(operation == 1){
			$("#source").val(coordXY[0] + "," + coordXY[1]);
			var sourceFeature = getPointFeature(coordXY[0], coordXY[1]);
			map.removeLayer(sourceLayer);
			sourceLayer = getPointLayer(sourceFeature);
			map.addLayer(sourceLayer);
			set1 = 1;
		}
		else if(operation == 2){
			$("#destination").val(coordXY[0] + "," + coordXY[1]);
			var destinationFeature = getPointFeature(coordXY[0], coordXY[1]);
			map.removeLayer(destinationLayer);
			destinationLayer = getPointLayer(destinationFeature);
			map.addLayer(destinationLayer);
			set2 = 1;
		}
		else if(operation == 3){
			var routeFeature = getPointFeature(coordXY[0], coordXY[1]);
			map.removeLayer(routeTrafficLayer);
			routeTrafficLayer = getPointLayer(routeFeature);
			map.addLayer(routeTrafficLayer);
			$.ajax({
				method: "GET",
				url: "TrafficEdgeId.jsp",
				data: {
					opType: 0,
					x: coordXY[0],
					y: coordXY[1]
				},
				success: function(result){
					var re = result.split("*");
					var coords = re[1].split(" ");
					$("#route_id").val(re[0]);
					var pointArray = getPointArray(coords);
					map.removeLayer(routeTrafficLayer);
					routeTrafficLayer = getRouteLayer(pointArray);
					map.addLayer(routeTrafficLayer);
				}
			});
			operation = 0;
		}
	});

	$("#updateTraffic").on("click", function(){
		if($("#route_id").val() != ""){
			$.ajax({
				method: "GET",
				url: "TrafficEdgeId.jsp",
				data: {
					opType: 1,
					id : $("#route_id").val(),
					val : $("#traffic_level").val()
				},
				success: function(result){
					if(result != 1){
						return;
					}
					if(set1==0 || set2==0 || set1!=set2)
						return;
					findTheRoute(set1);
				}
			});
		}
	});

	$("#findRoute").click(function(){
		if(set1 == 1 && set2 == 1){
			findTheRoute(1);  
			operation = -1;
		}else if(set1 == 2 && set2 == 2){
			findTheRoute(2);
			operation = -1;
		}
	});

	$(".form-control").on("keyup", function(){
		var search_box = $(this).attr("id");
		var searchQuery = $(this).val();
		var startIndex = 0;
		if(search_box == "source"){
			if(searchQuery == contentS){
				return;
			}
			if(searchQuery == "") {
				$("#source_search_group").hide();
				return;
			}
			contentS = searchQuery;
		}
		else if(search_box == "destination"){
			if(searchQuery == contentD){
				return;
			}
			if(searchQuery == "") {
				$("#destination_search_group").hide();
				return;
			}
			contentD = searchQuery;
			startIndex = 5;
		}
		else{
			return;
		}
		$.ajax({
			method: "GET",
			url: "searchString.jsp",
			data: {
				s: searchQuery
			},
			success: function(result) {
				var names = result.split("*");
				if(search_box == "source"){
					$("#source_search_group").show();
					$("#source_search_group").find("*").hide();
				}else{
					$("#destination_search_group").show();
					$("#destination_search_group").find("*").hide();
				}
				for(var i = startIndex; i<names.length+startIndex; i++){
					$("#s"+i).show();
					$("#s"+i).text(names[i-startIndex]);
				}
			}
		});
	});

	$(".source-search-result").on("click", function(){
		$("#source_search_group").hide();
		$("#source").val($(this).text());
		set1 = 2;
	});  
	$(".destination-search-result").on("click", function(){
		$("#destination_search_group").hide();
		$("#destination").val($(this).text());
		set2 = 2;
	});
});
var findTheRoute = function(opType){
	var s = $("#source").val();
	var d = $("#destination").val();
	$.ajax({
		method: "GET",
		url: "RouteFinder.jsp",
		data: {
			source: s,
			destination: d,
			opType: opType
		},
		success: function(result){
			var coords = result.split(" ");
			var limit = parseInt(coords[0]);
			var pointArray = getPointArray(coords, limit);

			map.removeLayer(routeLayer);
			routeLayer = getRouteLayer(pointArray);
			map.addLayer(routeLayer);
			
			// find the Directions
			var dirPointArray = getDirPointArray(coords, limit);
			alert(dirPointArray);
			
			map.removeLayer(directionLayer);
			directionLayer = getDirectionLayer(dirPointArray);
			map.addLayer(directionLayer);
		}
	});
}

function getDirPointArray(coords, limit){
	var dirPoints = new Array();
	for(var i = limit+1; i<coords.length; i+=3){
		var p = new Array(parseFloat(coords[i]), parseFloat(coords[i+1]), coords[i+2]);
		dirPoints.push(p);
	}
	return dirPoints;
}

function getPointArray(coord, limit){
	var pnts = new Array();
	for(var i = 1; i<=limit; i+=2){
		var p = new Array(parseFloat(coord[i]), parseFloat(coord[i+1]));
		pnts.push(p);
	}
	return pnts;
} 
var getRouteLayer = function(pnt){
	var featureLine = new ol.Feature({
		geometry: new ol.geom.LineString(pnt),	
	});
	var sourceLine = new ol.source.Vector({
		features: [featureLine]
	})
	var vectorLine = new ol.layer.Vector({
		source: sourceLine,
		style: new ol.style.Style({
			fill: new ol.style.Fill({
				color: [255, 0, 0, 1]
			}),
			stroke : new ol.style.Stroke({
				color : [255,0,0,1],
				width: 2
			})
		})
	});
	return vectorLine;
}

var getDirectionLayer = function(pointArray){
	

    // Create random point features
    var i, lat, lon, geom, feature, features = [], style, rnd;
    for(i=0; i< pointArray.length; i++) {
        lat = pointArray[i][0];
        lon = pointArray[i][1];

        geom = new ol.geom.Point(
            ol.proj.transform([lon, lat], 'EPSG:2024', 'EPSG:2024')
        );

        rnd = Math.random();
        feature = new ol.Feature({
            geometry: geom,
            radius: rnd * 30,
            dir: pointArray[i][2] 
        });
        features.push(feature);

        style = computeFeatureStyle(feature);
        feature.setStyle(style);
    }    

    // Source and vector layer
    var vectorSource = new ol.source.Vector({
        features: features
    });

    var vectorLayer = new ol.layer.Vector({
        source: vectorSource
    });
    return vectorLayer;
}

//Crate a style instance given feature's properties name and radius.
function computeFeatureStyle(feature) {
    return new ol.style.Style({
        image: new ol.style.Circle({
            radius: feature.get('radius'),
            fill: new ol.style.Fill({
                color: 'rgba(100,50,200,0.5)'
            }),
            stroke: new ol.style.Stroke({
                color: 'rgba(120,30,100,0.8)',
                width: 3
            })
        }),
        text: new ol.style.Text({
            font: '12px helvetica,sans-serif',
            text: feature.get('dir'),
            //rotation: 360 * rnd * Math.PI / 180,
            fill: new ol.style.Fill({
                color: '#000'
            }),
            stroke: new ol.style.Stroke({
                color: '#fff',
                width: 2
            })
        })
    });
}