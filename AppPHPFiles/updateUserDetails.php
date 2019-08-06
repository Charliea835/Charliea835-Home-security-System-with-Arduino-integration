<?php
#this php file will update the users information based on the email that they provide from their nadroid device
if($_SERVER["REQUEST_METHOD"]=="POST"){
	require 'connection.php';
	updateDetails();
}
function updateDetails() #function to update details
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