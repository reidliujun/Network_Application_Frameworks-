<?php
  // Remember to copy files from the SDK's src/ directory to a
  // directory in your application on the server, such as php-sdk/
  require 'php-sdk/facebook.php';

  $config = array(
    'appId' => '221804518022167',
    'secret' => 'ce32445f5bed3b42ca1e296a44124adf',
    'fileUpload' => true,
    'allowSignedRequest' => false // optional but should be set to false for non-canvas apps
  );

  $facebook = new Facebook($config);
  $user_id = $facebook->getUser();
  
   if($user_id) {
      // We have a user ID, so probably a logged in user.
      // If not, we'll get an exception, which we handle below.
      try {
        $user_profile = $facebook->api('/me','GET');
		$fbname = $user_profile['name'];    
		$fbid=$user_profile['id'];
		$friends = $facebook->api('/me/friends');        
      } catch(FacebookApiException $e) {
        error_log($e->getType());
        error_log($e->getMessage());
      }
  }
  else
  {
      $login_url = $facebook->getLoginUrl(array(
        'scope' => 'publish_stream,photo_upload'));
      //  echo 'Please <a href="' . $login_url . '">login.</a>';
  }  
  if ($user_id) {
  $logoutUrl = $facebook->getLogoutUrl(array(
		 'next' => 'http://group01.naf.cs.hut.fi/logout.php',  // Logout URL full path
		));
  }
?>
