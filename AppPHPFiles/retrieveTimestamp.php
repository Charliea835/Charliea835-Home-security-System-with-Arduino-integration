<?php

#this PHP file queries the database for the alarm data
if($_SERVER["REQUEST_METHOD"]=="POST"){
	include 'connection.php';
	showData();
}
function showData()
{
	global $connect;
	
	$query = " Select time FROM Alarm order by time desc; ";
	
	$result = mysqli_query($connect, $query);
	$number_of_rows = mysqli_num_rows($result);
	
	$temp_array  = array();
	
	if($number_of_rows > 0) { //while number of rows is greater than 1, store result
		while ($row = mysqli_fetch_assoc($result)) {
			$temp_array[] = $row;
		}
	}
	//define header type as JSON
	header('Content-Type: application/json');
	echo json_encode(array("Alarm"=>$temp_array));
	mysqli_close($connect);
	
}
?>