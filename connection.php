<?php
define('hostname', 'oursystem.mysql.database.azure.com');
define('user', 'project835@oursystem');
define('password', 'Homesecure835');
define('databaseName', 'projectdb');

$connect = mysqli_connect(hostname, user, password, databaseName);
?>