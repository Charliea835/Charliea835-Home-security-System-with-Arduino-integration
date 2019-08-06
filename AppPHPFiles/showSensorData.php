<?php

#this file gets the data from the sensor and sends it as JSON to the client
if($_SERVER["REQUEST_METHOD"]=="POST"){
	include 'connection.php';
	showData();
}
function showData()
{
	global $connect;
	
	$query = " Select Value FROM SensorData; ";
	
	$result = mysqli_query($connect, $query); //connect to database and execute query
	$number_of_rows = mysqli_num_rows($result);
	
	$temp_array  = array();
	
	if($number_of_rows > 0) {
		while ($row = mysqli_fetch_assoc($result)) {
			$temp_array[] = $row;
		}
	}
	
	header('Content-Type: application/json');
	echo json_encode(array("sensor"=>$temp_array));
	mysqli_close($connect);
	
}
?>