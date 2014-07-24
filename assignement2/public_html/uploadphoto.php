<?php
require 'fb_settings.php';   // Include fb_settings.php file
?>
<!--TEMPLATE REFERENCE: http://tutorialzine.com/2010/02/html5-css3-website-template/ - Tutorialzine 
    CODE REFERENCE:     http://daipratt.co.uk/facebook-api-upload-photo/ -->
<!DOCTYPE html>
<html>
  <head>
     <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>Social Media and Mashups | NAF Assignment 2</title>
        <link rel="stylesheet" type="text/css" href="styles.css" />
        <!-- Internet Explorer HTML5 enabling code: -->
        <!--[if IE]>
        <script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
        <style type="text/css">
        .clear {
          zoom: 1;
          display: block;
        }
        </style>
        <![endif]-->
  </head>
  <body>
  <div id="wrapper">
		<header>
		<hgroup><h1 class="site-title">Social Media and Mashups</h1></hgroup>
		</header>
		<nav>
			<ul class="main-nav">
			<li><a href="index.php">Home</a></li>
			<li><a href="about.php">About Us</a></li>
			<li><a href="contactus.php">Contact Us</a>
			</ul>
			<ul class="second-nav">
				<?php if ($user_id): ?> 
				<li><a href="<?php echo $logoutUrl; ?>">Log out</a></li>
				<?php else: ?>
				<li><a href="<?php echo $login_url; ?>">Login</a></li>
				<?php endif ?>
				<li><a href="#">Sign Up</a></li>
			</ul>
		</nav>
    	<section class="content"> <!-- Defining the #page section with the section tag -->
		<article id="article1"> <!-- The new article tag. The id is supplied so it can be scrolled into view. -->
<?php
  // Remember to copy files from the SDK's src/ directory to a
  // directory in your application on the server, such as php-sdk/
  $photo = $_GET['name'];	
  $facebook = new Facebook($config);
  if($user_id) {
      // We have a user ID, so probably a logged in user.
      // If not, we'll get an exception, which we handle below.
      try {
			//At the time of writing it is necessary to enable upload support in the Facebook SDK, you do this with the line:
		$facebook->setFileUploadSupport(true);
		//Create an album
		$album_details = array(
        'message'=> 'Album of images',
        'name'=> 'NAF2'
		);
		$create_album = $facebook->api('/me/albums', 'post', $album_details);
  
		//Get album ID of the album you've just created
		$album_uid = $create_album['id'];
		//Upload a photo to album of ID...
		$photo_details = array(
		'message'=> 'Landscape Pictures'
		);
		$file=realpath($photo); //Example image file
		$photo_details['image'] = '@' . realpath($photo);
		$upload_photo = $facebook->api('/'.$album_uid.'/photos', 'post', $photo_details);
        //post a link
		echo '<p>The photo appears now on your wall.</p>';
		echo '<p><b>Name:</b> ' . $file . '</p>';
        echo '<p><b>Album ID:</b> ' . $create_album['id'] . '</p>';
		
   } catch(FacebookApiException $e) {
        echo "Error:".$e;
      }
    } else {
      echo "Please go back and login again";
    }
?>
<br>
<br>
<a href="test.php">Click to come back!</a>
</article>
	</section>
		<aside class="sidebar">
		<?php if ($user_id): ?> 
		<h3>Welcome!</h3>
		<p><?php echo $fbname; ?></p>
		<img src="https://graph.facebook.com/<?php echo $fbid; ?>/picture?type=large" border="1"/>
		<?php endif ?>
		<ul class="widget-sidebar">
			<li class="widget widget_categories">
			<h3>Categories</h3>
			<ul>
			<li><a href="contactus.php">Google Maps Integration</a></li>
			<ul class="children">
			<li><a href="test.php">Twitter Integration</a></li>
			<li><a href="test.php">Facebook Integration</a>
			<ul class="children">
			<li><a href="">Facebook Like Button</a></li>
			<li><a href="test.php">Facebook Comments Plugin</a></li>
			<li><a href="upload.php">Upload photos to your wall</a></li>
			</ul>
			</li>
			</li>
			</ul>
		</aside>
		<footer>
			<div class="footer-left">Copyright 2014 - Group 01 | NAF</div>
			<div class="footer-right"><a href="about.html">About Us</a> | <a href="#" class="up">Go UP</a></div>
	  </footer>
</div>
  </body>
</html>


