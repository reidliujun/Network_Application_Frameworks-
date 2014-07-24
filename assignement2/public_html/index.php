<?php
require 'fb_settings.php';   // Include fb_settings.php file
?>
<!DOCTYPE HTML>
<html>
<head>
<title></title>
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

<!-- start html block -->
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
	
<section class="content">
		<article class="post">
		<h2>How to integrate Facebook and Twitter in a blog ?</h2>
		<div class="line"></div>
		<p style="color:#191970; font-family:Georgia, serif">Published March 10, 2014 | By Jun Liu</p>
		<div class="articleBody clear">
			<figure> <!-- The figure tag marks data (usually an image) that is part of the article -->
				<a href="test1.php"><img src="./img/fb_twitter_google.jpg" width="500" height="300" /></a>
			</figure>
			<p>This example presents the integration of Facebook and Twitter features inside a static page. Basically, it is shown the Facebook
			"Like" button and also the plugin to write comments.</p>
			<p>In addition, Google API is integrated in the "Contact Us" page by presenting a map with coordinates.</p>
			<a href="test1.php">Read more >></a>
		</div>
		</article>

		<article id="article2">
			<h2>Uploading pictures to your wall</h2>
			<div class="line"></div>
			<p style="color:#191970; font-family:Georgia, serif">Published March 12, 2014 | By Maria Montoya</p>
			<div class="articleBody clear">
				<figure>
					<a href="test.php"><img src="./img/facebook.jpg" width="500" height="300" /></a>
				</figure>    
				<p>This example presents some features of Facebook inserted in our page by using PHP.</p>
				<a href="test.php">Read more >></a>
			</div>
		</article>

		  <article id="article3">
			<h2>Chart Example</h2>
			<div class="line"></div>
			<p style="color:#191970; font-family:Georgia, serif">Published March 12, 2014 | By Maria Montoya</p>
			<div class="articleBody clear">
				<figure>
					<a href="chart.php"><img src="./img/googlechart.png" width="500" height="300" /></a>
				</figure>
				<p>In this example, we create a dynamic page and use Google Chart API with PHP in order to generate a pie chart from data contained on a JSON file.</p>
				<a href="chart.php">Read more >></a>
			</div>
		</article>

</section>

<aside class="sidebar">
	<?php if ($user_id): ?> 
	<h3>Welcome!</h3>
	<p><?php echo $fbname; ?></p>
	<img src="https://graph.facebook.com/<?php echo $fbid; ?>/picture?type=large" border="1"/>
	<?php endif ?>
	<?php if ($user_id):
		$count=0;
		echo '<p>These are some of your friends:</p>';
		echo '<table border="1" style="color:black; font-size:10pt; font-weight:normal; margin: auto;" cellpadding="1" cellspacing="1">';
		
        foreach ($friends["data"] as $value) {
            if( $count == 5 )    break;
			echo '<tr>';
            echo '<td><img src="https://graph.facebook.com/' . $value["id"] . '/picture"/></td>';
			echo '<td width="110"><p>'.$value["name"].'</p></td>';
			echo '</tr>';
			$count+=1;	  
        }
		
	    echo '</table>';
	endif ?>
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
		<li><a href="https://apps.facebook.com/naf-assignment/">Application homepage</a></li>
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