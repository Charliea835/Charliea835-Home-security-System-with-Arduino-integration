<html>

    <!--- This file is hosted on a free webhosting service called 000webhost--->

 <head>
  <title>PHP-email</title>

  	<script type="text/javascript">
            // this function is used to redirect browser to login page after email has been sent
  	        function newpage(){
  	            window.location.assign("http://localhost:8080/HomeSecuritySystemUpdated/HomeSecurityLogin.html")
  	            }

	</script>

 </head>

 <body>

 <?php

   	require 'PHPMailer/PHPMailerAutoload.php'; //PHPMailer is used to send the email

 	$mail = new PHPMailer();
 	$emailAddress = "";
 	$emailUser = "";


 	define('hostname', 'oursystem.mysql.database.azure.com');
    define('user', 'project835@oursystem');
    define('password', 'Homesecure835');
    define('databaseName', 'projectdb');

    //connect to database using defined credentials
    $connect = mysqli_connect(hostname, user, password, databaseName);

 	//if connection fails, print error
 	if(! $connect ){
            die('Could not connect: ' . mysqli_error());
         }
         echo 'Connected successfully';



// sql statement to select email and firstname info from register table
$sql = "SELECT email, firstname FROM register";

//execute query
$result = $connect->query($sql);

if ($result->num_rows > 0) {

    // loop until you reach end of rows, the value of the last rown will be stored
    while($row = $result->fetch_assoc()) {

        $emailAddress = $row["email"];
        $emailUser = $row["firstname"];

    }

} else {
    echo "0 results";
}

$connect->close(); //close connection to db


    //set mailer details
 	$mail->Host = "smtp.gmail.com";
 	$mail->SMTPSecure = "ssl";
 	$mail->Port = 465;
 	$mail->SMTPAuth = true;

 	//google account login credentials, email will be sent from here
 	$mail->Username = 'wacwebproject@gmail.com';
 	$mail->Password = 'S3cureYourHome@LIT';

 	$mail->setFrom('wacwebproject@gmail.com', 'WACHomeSecurity');

 	//send to email address pulled from database
 	$mail->addAddress($emailAddress);
 	$mail->Subject = 'Home Security registration';

 	//include user's name in email body
 	$mail->Body =
 	'Hi ' . $emailUser . ',

 	Congratulations on registering to secure your home.

 	We hope you feel safe and secure using the app.

 	regards,

 	Secure your home team. ';

 	if ($mail->send())
 	    echo 'mail sent';

  ?>

  </body>

 <body onload="newpage()">


 </body>
</html>

