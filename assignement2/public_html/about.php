<?php
require 'fb_settings.php';   // Include fb_settings.php file
?>
<!--TEMPLATE REFERENCE: http://tutorialzine.com/2010/02/html5-css3-website-template/ - Tutorialzine -->
<!DOCTYPE html> 
<html>
  <head>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />   
    <title>Social Media and Mashups | Chart Example</title>
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
    <!--Load the AJAX API-->
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
			<article id="1">
			<h2>About us</h2>
			<div class="line"></div>	
			<p>Second Assignment - Network Applications Frameworks</p>
			<p>This is a project that uses Facebook Graph API, Twitter and Google on some pages of this website</p>
			<div style="padding-left:30px">
			<ul>
			<li>Jun Liu - <a href="mailto:jun.liu@aalto.fi">jun.liu@aalto.fi</a></li>
			<li>Maria Montoya - <a href="mailto:mariamontoya.freire@aalto.fi">mariamontoya.freire@aalto.fi</a></li>
			</ul>
			</div>
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


