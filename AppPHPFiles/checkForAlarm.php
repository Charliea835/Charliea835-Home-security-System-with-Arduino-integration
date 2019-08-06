
<?php
if($_SERVER["REQUEST_METHOD"]=="POST"){
	require 'connection.php';
	checkForAlarm();
}
function checkForAlarm()
{
	global $connect;
	$query = "Select time from Alarm order by time desc;";
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

#this php file checks the table alarm for a record and if there is alert the user by getting the server to send a response