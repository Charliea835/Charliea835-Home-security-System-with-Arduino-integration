<?php
if($_SERVER["REQUEST_METHOD"]=="POST"){
	require 'connection.php';
	updateDetails();
}
function updateDetails()
{
	global $connect;
	$currentEmail = $_POST["currentEmail"];
	$password = $_POST["password"];
	$email = $_POST["email"];
	$query = "Update Register set email='$email',password='$password' where email='$currentEmail';";
	echo 'updated';
	mysqli_query($connect, $query) or die (mysqli_error($connect));
	mysqli_close($connect);
	
}
?>