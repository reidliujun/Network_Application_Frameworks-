<!--REFERENCE: http://www.krizna.com/general/login-with-facebook-using-php/ -->
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
  if($user_id) {

      // We have a user ID, so probably a logged in user.
      // If not, we'll get an exception, which we handle below.
      try {
        //post a link
		$facebook->destroySession();  // to destroy facebook sesssion
		header("Location: " ."index.php");        
		
   } catch(FacebookApiException $e) {
        echo "Error".$e;
      }
    } else {
      echo "Go back and login again";
    }
?>