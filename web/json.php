<?php
        include 'connect.php';
        $res = mysql_query("select * from soldiers");
        $response["soldiers"] = array();
        
        while($result=mysql_fetch_array($res)) {
        $tmp = array();
            $tmp["Id"] = $result["Id"];
            $tmp["Name"] = $result["Name"];
            $tmp["Status"] = $result["Status"];
            $tmp["Message"] = $result["Message"];
            $tmp["Longitude"] = $result["Longitude"];
            $tmp["Latitude"] = $result["Latitude"];
        array_push($response["soldiers"], $tmp);
        }
        echo json_encode($response);
?>