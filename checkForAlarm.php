<?php
if($_SERVER["REQUEST_METHOD"]=="POST"){
	require 'connection.php';
	checkForAlarm();
}
function checkForAlarm()
{
	global $connect;
	$query = "Select time from Alarm;";
	$result = mysqli_query($connect,$query);
	$check = mysqli_fetch_array($result);
	if(isset($check)){
	echo 'activated';
	}else{
	echo 'nothing';
	}
	mysqli_query($connect, $query) or die (mysqli_error($connect));
	mysqli_close($connect);
	
}
?>