<?php
 #this php file will check the users details entered against the details in the database and echo a response based on if theyre right or not
if($_SERVER['REQUEST_METHOD']=='POST'){
$email = $_POST['email'];
$password = $_POST['password'];
require_once('connection.php'); #require connection.php to connect to database 
$sql = "SELECT * FROM Register WHERE email = '$email' AND password='$password'";
$result = mysqli_query($connect,$sql);
$check = mysqli_fetch_array($result);
if(isset($check)){
echo 'success';
}else{
echo 'login failed';
}
}
?>