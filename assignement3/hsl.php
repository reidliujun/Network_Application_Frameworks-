<?php
// this is a server side file to deal with the request sent from applicaiton. 
// it covers how to get the GET request parameter, how to parse json content and how to get certain value in json

if ($_GET["token"]!=="null" && isset($_GET["token"]))
{
    $token=$_GET["token"];
        $url_token = 'https://graph.facebook.com/me?access_token=' . $token;
        $obj_token=file_get_contents($url_token);
        $user_info=json_decode($obj_token,true);
        $token_id=$user_info["id"];
        if (!empty($token_id))
        {
                if(isset($_GET["loc1"],$_GET["loc2"]))
                {
                    $loc1=$_GET['loc1'];
                    $loc2=$_GET['loc2'];
                    echo json_encode(getRoute($loc1,$loc2));
                }
        }
}
else
{
        echo "NO";
}

function getCodeLocation($location)
{
   $json = file_get_contents('http://api.reittiopas.fi/hsl/prod/?request=geocode&user=ljljlj&pass=ljljlj&format=json&key='.$location);
   $obj = json_decode($json,TRUE);
   $code= $obj[0]["coords"];
   return $code;
}

function getRoute($loc_1,$loc_2)
{
    $code1=getCodeLocation($loc_1);
    $code2=getCodeLocation($loc_2);
    $response= file_get_contents('http://api.reittiopas.fi/hsl/prod/?request=route&user=ljljlj&pass=ljljlj&format=json&from='.$code1.'&to='.$code2);
    $obj_resp= json_decode($response,TRUE);
    return $obj_resp;
}
?>