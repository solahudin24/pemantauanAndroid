<?php
	if($_SERVER['REQUEST_METHOD']=='POST'){

 include '../koneksi.php';
 
 $con = koneksi_db();
 
 $nis = $_POST['nis'];
 $longitude = $_POST['longitude'];
 $lat = $_POST['lat'];
 $batre = $_POST['batre'];

 $Sql_Query = "update tb_siswa SET longitude = '$longitude', lat = '$lat' , baterai = '$batre', update_time = CURRENT_TIMESTAMP WHERE nis = '$nis';";
 
 $res = mysqli_query($con,$Sql_Query);
 
 if($res){
 
 echo "Sukses";
 }
 else{
 echo "Invalid Query Please Try Again";
 }
 
 }else{
 echo "Check Again";
 }
mysqli_close($con);

?>