<!--TEMPLATE REFERENCE: http://tutorialzine.com/2010/02/html5-css3-website-template/ - Tutorialzine -->
<!DOCTYPE html>
<?php
  // Remember to copy files from the SDK's src/ directory to a
  // directory in your application on the server, such as php-sdk/
  require_once('php-sdk/facebook.php');

  $config = array(
    'appId' => '221804518022167',
    'secret' => 'ce32445f5bed3b42ca1e296a44124adf',
    'fileUpload' => true,
    'allowSignedRequest' => false // optional but should be set to false for non-canvas apps
  );

  $facebook = new Facebook($config);
  $user_id = $facebook->getUser();
?>
<html>
  <head>
      <meta name="viewport" content="initial-scale=1.0, user-scalable=no">
      <meta charset="utf-8">
      <title>Friend location</title>
      <style>
        html, body, #map-canvas {
          clear:both;
          height: 100%;
          margin: 0px;
          padding: 0px;
          margin-top: 10px;
          display: block;
        }
        #panel {
          left: 50%;
          float:left;
          margin-bottom: 7px;
          display: block;
          background-color: #fff;
          padding: 5px;
          border: 1px solid #999;
        }
      </style>
      <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAfv8pNvR9MFRTZyQpLG2MJGeABUfrJ__w&sensor=false"></script>
      <script type="text/javascript">
      var geocoder;
      var map;
      function initialize() {
        geocoder = new google.maps.Geocoder();
        var mapOptions = {
          center: new google.maps.LatLng(60.18, 24.83),
          zoom: 1,
      mapTypeId: google.maps.MapTypeId.ROADMAP
      
        };
        map = new google.maps.Map(document.getElementById("map-canvas"),
            mapOptions);
    var myLatlng = new google.maps.LatLng(60.18, 24.83);
    var marker = new google.maps.Marker({
    position: myLatlng,
    title:"Otaniemi Campus"});
    
    marker.setMap(map);
      }
      google.maps.event.addDomListener(window, 'load', initialize);
    </script>   
   </head>

  <body>
  <?php
    if($user_id) {
      // We have a user ID, so probably a logged in user.
      // If not, we'll get an exception, which we handle below.
      try {
        $user_profile = $facebook->api('/me','GET');
        $friends = $facebook->api('/me/friends','get',array("fields"=>"location,name"));
        //use fql 
        //reference: https://developers.facebook.com/docs/reference/fql
        $params1 = array(
            'method' => 'fql.query',
            //'query' => "SELECT friend_count FROM user WHERE uid=me()",
            'query'=>"SELECT uid2 FROM friend WHERE uid1=me()",
        );
        $result1 = $facebook->api($params1);
        $count=0;
        //initialize variable in javascript
        echo '<script> var friends_location = [];var friends_name=[];var fql=[];</script>';
        foreach ($friends["data"] as $value) {
            if( $count == 5 )    break;
            //Give the value from graph api to variable in javascript
            if(array_key_exists("location", $value)){
              $tmp = $value["location"];
              if(array_key_exists("name", $tmp)){
                echo '<script>friends_location.push("'.$tmp["name"].'");</script>';
              }
            }
            if(array_key_exists("name", $value)){
              echo '<script>friends_name.push("'.$value["name"].'");</script>';
            }
            //echo '<script>friends_location.push("'.$value["location"]["name"].'");</script>';
            
            //Get informtion from fql
            $id=$result1[$count]["uid2"];
            $params2 = array(
              'method' => 'fql.query',
              //'query' => "SELECT friend_count FROM user WHERE uid=me()",
              'query'=>"SELECT current_location FROM user WHERE uid=".$id,
            );
            $result2 = $facebook->api($params2);
            //echo '<pre>'; print_r($result2[0]["current_location"]["city"]); 
            //echo '<pre/>';
            echo '<script>fql.push(["'.$result2[0]["current_location"]["city"].'","'.$result2[0]["current_location"]["state"].
              '","'.$result2[0]["current_location"]["country"].'","'.$result2[0]["current_location"]["latitude"].
              '","'.$result2[0]["current_location"]["longitude"].'"]);</script>';
            $count+=1;    
        }

      } catch(FacebookApiException $e) {
        // If the user is logged out, you can have a
        // user ID even though the access token is invalid.
        // In this case, we'll get an exception, so we'll
        // just ask the user to login again here.
        $login_url = $facebook->getLoginUrl(array(
        'scope' => 'user_about_me,publish_stream,photo_upload,friends_about_me,friends_location'));
        echo 'Please <a href="' . $login_url . '">login.</a>';
        error_log($e->getType());
        error_log($e->getMessage());
      }
    } else {
      // No user, print a link for the user to login
      $login_url = $facebook->getLoginUrl(array(
        'scope' => 'user_about_me,publish_stream,photo_upload,friends_about_me,friends_location'));
      echo 'Please <a href="' . $login_url . '">login to your Facebook account.</a>';
    }
  ?>
  
  <script>console.log(friends_location);
      console.log("fql:"+fql);//for example fql==[["Shanghai","Shanghai","China"],["Chengdu","Sichuan","China"]];
      console.log(friends_name);
      function clicktomark(){
        for (var j = 0; j<friends_location.length; j++){
        markFriend(friends_location[j],friends_name[j],fql[j]); 
        }
      }
      function markFriend(address,name,fql){
        var infowindow = new google.maps.InfoWindow();
        geocoder.geocode( { 'address': address}, function(results, status) {
          if (status == google.maps.GeocoderStatus.OK) {
            var marker = new google.maps.Marker({
              map: map,
              position: results[0].geometry.location
            });
            google.maps.event.addListener(marker, 'click', (function(marker) {
                //add Animation
                //if (marker.getAnimation() != null) {
                //    marker.setAnimation(null);
                //} else {
                //  marker.setAnimation(google.maps.Animation.BOUNCE);
                //}

                //return the mark info
                return function() {
                  var contentString='<div id="content">'+
                  '<h1 id="name" class="firstHeading">'+name+'</h1>'+
                  '<div id="bodyContent">'+
                  '<p>City:'+fql[0]+'</p>'+
                  '<p>State:'+fql[1]+'</p>'+
                  '<p>Country:'+fql[2]+'</p>'+
                  '<p>Latitue:'+fql[3]+'</p>'+
                  '<p>Longtitude:'+fql[4]+'</p>'+
                  '</div>'+
                  '</div>';
                  infowindow.setContent(contentString);
                  infowindow.open(map, marker);
                }
              })(marker));
          } else {
            alert('Geocode was not successful for the following reason: ' + status);
          }
        });
      }
      </script>



    <div id="panel">
      <input type="button" value="Show Friends Location" onclick="clicktomark()">
    </div>
    <div id="map-canvas" style="width: 760px; height: 290px;"/> 


  </body>
</html>
