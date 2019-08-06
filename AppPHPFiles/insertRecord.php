<?php

#this php file inserts data that the user posts from there device to the database
if($_SERVER["REQUEST_METHOD"]=="POST"){
	require 'connection.php';
	checkIfDatabaseIsCreated();
	createUser();
}
function createUser() #function to create the user
{
	global $connect;
	
	$firstname = $_POST["firstname"];	
	$lastname = $_POST["lastname"];
	$password = $_POST["password"];		#retrieve data by POST
	$email = $_POST["email"];
	
	$to=isset($_POST["email"])? $_POST["email"] : ''; #specify address to send to
	$subject="Secure your home Registration!";			
	$body="Hi $firstname, \n\nCongratulations on registering to secure your home. \n\n We hope you feel safe and secure using the app. \n\n regards, \n\n Secure your home team.";
	$headers="From: noreply@secure.com";

    if(mail($to,$subject,$body,$headers)){ #send email to whatever address was registered
    echo 'success';
	$query = "Insert into Register(firstname,lastname,email,password) values ('$firstname','$lastname','$email','$password');";
	mysqli_query($connect, $query) or die (mysqli_error($connect));
	mysqli_close($connect);
    }
}



function checkIfDatabaseIsCreated(){ #check to see if register table has been created or not
	global $connect;
	$query1 = "create table if not exists Register(id INT AUTO_INCREMENT PRIMARY KEY,firstname VARCHAR(20),lastname VARCHAR(20),email VARCHAR(30),password VARCHAR(20));";
	mysqli_query($connect, $query1) or die (mysqli_error($connect));
}
?>