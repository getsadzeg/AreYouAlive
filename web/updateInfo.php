<?php
include 'connect.php';

        $Id = $_GET["Id"];
        $Name = $_GET["Name"];
        $Status = $_GET["Status"];
        $Message = $_GET["Message"];
        $Longitude = $_GET["Longitude"];
        $Latitude = $_GET["Latitude"];

        $insertQ = 'UPDATE soldiers SET `Name` = "'.$Name.'",`Status` = "'.$Status.'",`Message` = "'.$Message.'",`Longitude` = '.$Longitude.',`Latitude` = '.$Latitude.' WHERE `Id` = '.$Id;
		$status = mysql_query($insertQ);

        $response = array();
		$response["success"] = $status;
		echo json_encode($response);
?>