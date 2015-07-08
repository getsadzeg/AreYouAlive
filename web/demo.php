<?php
mysql_connect("localhost","dbname","dbpass") or  die(mysql_error());
mysql_select_db("dbname");
$sql=mysql_query("select * from soldiers");
while($row=mysql_fetch_assoc($sql))
$output[]=$row;
print(json_encode($output));
mysql_close();
?>
