<?php
	if($_SERVER['REQUEST_METHOD']=='POST'){

 include '../koneksi.php';
 
 $con = koneksi_db();
 
 $nis = $_POST['nis'];
 $password = $_POST['password'];
 
 $Sql_Query = "select * from tb_siswa where nis = '$nis' and password = '$password' ";
 
 $check = mysqli_fetch_array(mysqli_query($con,$Sql_Query));
 
 if(isset($check)){
 
 echo "Data Matched";
 }
 else{
 echo "Invalid Username or Password Please Try Again";
 }
 
 }else{
 echo "Check Again";
 }
mysqli_close($con);

?>