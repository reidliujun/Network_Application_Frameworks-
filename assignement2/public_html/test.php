<?php
require 'fb_settings.php';   // Include fb_settings.php file
?>
<!--TEMPLATE REFERENCE: http://tutorialzine.com/2010/02/html5-css3-website-template/ - Tutorialzine -->
<!DOCTYPE html>
<html>
  <head>
  <title>Social Media and Mashups | Deeper Integration</title>
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
	 <article id="1">
   <h2>Uploading pictures to your wall</h2>
   <div class="line"></div>
   <div class="articleBody clear">
   <div style="padding-left:2px; padding-top:8px; width:700px; height:50px;">
	<div style="float:left;"><p style="color:#191970; font-family:Georgia, serif">Published March 08, 2014 | By Maria Montoya</p></div>
	<div style="float:right; margin-left:10px; margin-top:11px;"><a href="https://twitter.com/share" class="twitter-share-button" data-text="naf assignment2">Tweet</a></div>
	<div style="float:right; margin-left:40px; margin-top:11px;"><a href="https://twitter.com/reidliujun" class="twitter-follow-button" data-show-count="false">Follow @reidliujun</a></div>
    </div>
   </div>
  <p>Here you could find beautiful landscapes that can post on your wall. You should click on "Log in" in order to post the pictures. Once you are connected
  with your Facebook account you can click in one of the pictures and automatically it would appear on your wall.</p>
  <?php if ($user_id): ?>   
  <a href="post.php">Click to post into your wall</a>
  <?php endif ?>
  <br><br>
  <div class="articleBody clear">
   <table border="1" cellspacing="1" cellpadding="0" width="700">
   <tbody>
      <tr>
	    <?php if ($user_id): ?>   
                <th><a href="uploadphoto.php?name=./img/background1.jpg"><img src="./img/background1.jpg" style="width:230px;" /></a></th>
		<th><a href="uploadphoto.php?name=./img/background2.jpg"><img src="./img/background2.jpg" style="width:230px;" /></a></th>
		<th><a href="uploadphoto.php?name=./img/background3.jpg"><img src="./img/background3.jpg" style="width:230px;" /></a></th>     
		<?php else: ?>
		<th><img src="./img/background1.jpg" style="width:230px;" /></th>
		<th><img src="./img/background2.jpg" style="width:230px;" /></th>
		<th><img src="./img/background3.jpg" style="width:230px;" /></th>     
		<?php endif ?>
      </tr>
    </tbody>
    </table>
  </div>
  
  <br><br>
  <h3>Comments:</h3>
  <div style="text-align:left">
  <div class="fb-like" data-href="http://group01.naf.cs.hut.fi/" data-layout="standard" data-action="like" data-show-faces="true" data-share="true"></div>              
  <div class="fb-comments" data-href="http://group01.naf.cs.hut.fi/" data-numposts="5" data-colorscheme="light"></div></div>
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
   
   
  <script>(function(d, s, id) {
	  var js, fjs = d.getElementsByTagName(s)[0];
	  if (d.getElementById(id)) return;
	  js = d.createElement(s); js.id = id;
	  js.src = "//connect.facebook.net/en_US/all.js#xfbml=1&appId=221804518022167";
	  fjs.parentNode.insertBefore(js, fjs);
	}(document, 'script', 'facebook-jssdk'));
	</script>
	<script>!function(d,s,id){var js,fjs=d.getElementsByTagName(s)[0],p=/^http:/.test(d.location)?'http':'https';if(!d.getElementById(id)){js=d.createElement(s);js.id=id;js.src=p+'://platform.twitter.com/widgets.js';fjs.parentNode.insertBefore(js,fjs);}}(document, 'script', 'twitter-wjs');</script>
</html>
