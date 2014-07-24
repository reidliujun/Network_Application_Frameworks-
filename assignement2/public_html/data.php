<?php

//CHART REFERENCE: https://developers.google.com/chart/interactive/docs/php_example 

// This is just an example of reading server side data and sending it to the client.
// It reads a json formatted text file and outputs it.

$string = file_get_contents("data.json");
echo $string;
?>
