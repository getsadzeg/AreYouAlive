<!DOCTYPE html>
<html>
<head>
	<title>Title</title>
	<meta charset="utf-8">
	<script src="//code.jquery.com/jquery-1.11.3.min.js"></script>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap-theme.min.css">
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
	<meta http-equiv="refresh" content="30; url=http://areyoualive.org">
<script src="//code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
<?/*<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAMh3UMv5-23fywKYNFb40RojM7kkd8DhE&amp;sensor=false"></script>*/?>
<script src="https://maps.googleapis.com/maps/api/js?v=3.exp"></script>
	<link rel="stylesheet" href="styles.css" type="text/css">
</head>

<body>
		<div class="container">
			<table class="table table-bordered table-hover">
			<tr>
	<th><i class="glyphicon glyphicon-tag bold"> Id</th>
	<th><i class="glyphicon glyphicon-user bold"> Name</th>
    <th><i class="glyphicon glyphicon glyphicon-heart bold"> Status</th>
    <th><i class="glyphicon glyphicon-envelope bold"></i> Message</th>
    <th><i class="glyphicon glyphicon-triangle-top bold"> Longitude</th>
    <th><i class="glyphicon glyphicon-triangle-right bold"> Latitude</th>
  </tr>
 <?php
 	include 'connect.php';
 	$query = "select * from soldiers";
 	$get_info = mysql_query($query);
	$i = 0;
 	while ($row=mysql_fetch_array($get_info)) {
	    $id = $row["Id"];
	    $name = $row["Name"];
	    $status = $row["Status"];
	    $message = $row["Message"];
	    $longitude = $row["Longitude"];
	    $latitude = $row["Latitude"];

	    $locations[$i]['id'] = $row["Id"];
	    $locations[$i]['Status'] = $row["Name"];
	    $locations[$i]['Name'] = $row["Name"];
	    $locations[$i]['Message'] = $row["Message"];
	    $locations[$i]['Longitude'] = $row["Longitude"];
	    $locations[$i]['Latitude'] = $row["Latitude"];

	   $i++;

 ?>
  <tr>
    <td><?php echo $id; ?></td>
    <td><?php echo $name; ?></td>
    <td><?php echo $status; ?></td>
    <td><?php echo $message; ?></td>
    <td><?php echo $longitude; ?></td>
    <td><?php echo $latitude; ?></td>
  </tr>
  <?php
  	}

  ?>
			</table>
			<div>
	<div id="map" style="width: 1000px; height: 550px;"></div>

  <script type="text/javascript">


    var mapMarkers = <?=json_encode($locations)?>;

    (function (window) {

    if (typeof google !== "undefined") {
        var map;
        var MAPTYPE_ID = 'custom';


        function initialize() {

            if (window.adminMap) {
                return false;
            }

            var map;
            var bounds = new google.maps.LatLngBounds();
            var mapOptions = {
                mapTypeId: 'roadmap'
            };

            // Display a map on the page
            map = new google.maps.Map(document.getElementById("map"), mapOptions);
            map.setTilt(45);

            // Display multiple markers on a map
            var infoWindow = new google.maps.InfoWindow(), marker, i;

            // Loop through our array of markers & place each one on the map

            for (i = 0; i < mapMarkers.length; i++) {
                var position = new google.maps.LatLng(mapMarkers[i]['Latitude'], mapMarkers[i]['Longitude']);
                bounds.extend(position);
                marker = new google.maps.Marker({
                    position: position,
                    map: map,
                    title: mapMarkers[i]['Message']
                });

                // Allow each marker to have an info window
                google.maps.event.addListener(marker, 'click', (function (marker, i) {
                    return function () {
                        infoWindow.setContent('<h6>' + mapMarkers[i]['Name'] + '</h6>' + '<p>' + mapMarkers[i]['Message'] + '</p>');
                        infoWindow.open(map, marker);
                    }
                })(marker, i));

                // Automatically center the map fitting all markers on the screen
                map.fitBounds(bounds);
            }

            // Override our map zoom level once our fitBounds function runs (Make sure it only runs once)
            var boundsListener = google.maps.event.addListener((map), 'bounds_changed', function (event) {
                this.setZoom(13);
                google.maps.event.removeListener(boundsListener);
            });


        }

        google.maps.event.addDomListener(window, 'load', initialize);
    }
})(window);



  </script>

		</div>
</body>
</html>
