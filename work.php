<?php
    $con = mysqli_connect("localhost", "ghtjd8264", "sw15*cs25", "ghtjd8264");
    mysqli_set_charset($con,"utf8");
    $result = mysqli_query($con, "SELECT * FROM WORK;");
    $response = array();

    while($row = mysqli_fetch_array($result)) {
        array_push($response,array("count"=>$row[0],"employee_name"=>$row[1],"work_start"=>$row[2],"work_end"=>$row[3]));
    }

    echo json_encode(array("response"=>$response));
    mysqli_close($con)

?>