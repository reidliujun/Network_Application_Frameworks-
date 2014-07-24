<?php
require 'fb_settings.php';   // Include fb_settings.php file
?>
<!--TEMPLATE REFERENCE: http://tutorialzine.com/2010/02/html5-css3-website-template/ - Tutorialzine -->
<!DOCTYPE html> 
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />   
        <title>Social Media and Mashups | Contact Us</title>
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
	   <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAfv8pNvR9MFRTZyQpLG2MJGeABUfrJ__w&sensor=false"></script>
	   <script type="text/javascript">
      function initialize() {
        var mapOptions = {
          center: new google.maps.LatLng(60.18, 24.83),
          zoom: 13,
		  mapTypeId: google.maps.MapTypeId.ROADMAP
		  
        };
        var map = new google.maps.Map(document.getElementById("map-canvas"),
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
				<article style="width:948px; margin:0 0;">     
				<h2>Contact Us</h2>
				<div class="line"></div>			
				<form class="contact_form" action="#" method="post">
				<ul>
					<li>
						<label for="name">Name:</label>
						<input type="text"  placeholder="Name" required />
					</li>
					<li>
						<label for="email">Email:</label>
						<input type="email" name="email" placeholder="mail@naf.cse.hut.fi" required />
					</li>
					<li>
						<label for="Mensaje">Message:</label>
						<textarea name="Mensaje" cols="40" rows="6" required ></textarea>
					</li>
					<li>
					 <button class="submit" type="submit">Send</button>
					</li>
				</ul>
				</form>        
				<div id="map-canvas"/>				
				</article>     
			</section>
       <footer>
		<div class="footer-left">Copyright 2014 - Group 01 | NAF</div>
		<div class="footer-right"><a href="about.html">About Us</a> | <a href="#" class="up">Go UP</a></div>
	  </footer>
</div>
    </body>
</html>
