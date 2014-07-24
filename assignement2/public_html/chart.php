<?php
require 'fb_settings.php';   // Include fb_settings.php file
?>
<!--TEMPLATE REFERENCE: http://tutorialzine.com/2010/02/html5-css3-website-template/ - Tutorialzine -->
<!--CHART REFERENCE: https://developers.google.com/chart/interactive/docs/php_example -->
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
    <script type="text/javascript" src="https://www.google.com/jsapi?key=AIzaSyAfv8pNvR9MFRTZyQpLG2MJGeABUfrJ__w&sensor=false"></script>
    <script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
  	<script>(function(d, s, id) {
	  var js, fjs = d.getElementsByTagName(s)[0];
	  if (d.getElementById(id)) return;
	  js = d.createElement(s); js.id = id;
	  js.src = "//connect.facebook.net/en_US/all.js#xfbml=1&appId=221804518022167";
	  fjs.parentNode.insertBefore(js, fjs);
	}(document, 'script', 'facebook-jssdk'));
	</script>
	<script>!function(d,s,id){var js,fjs=d.getElementsByTagName(s)[0],p=/^http:/.test(d.location)?'http':'https';if(!d.getElementById(id)){js=d.createElement(s);js.id=id;js.src=p+'://platform.twitter.com/widgets.js';fjs.parentNode.insertBefore(js,fjs);}}(document, 'script', 'twitter-wjs');</script>

    <script type="text/javascript">
    // Load the Visualization API and the piechart package.
    google.load('visualization', '1', {'packages':['corechart']});
    google.setOnLoadCallback(drawChart);
    function drawChart() {
      var jsonData = $.ajax({
          url: "data.php",
          dataType:"json",
          async: false
          }).responseText;

      // Create a data table based on JSON file.
      var data = new google.visualization.DataTable(jsonData);

      // Instantiate and draw our chart, passing in some options.
      var chart = new google.visualization.PieChart(document.getElementById('chart_div'));
      var options= {
	  	     width:400,
		     height:240,
		     title:'Mobile Platforms'
		   };
      chart.draw(data, options);
    }
      google.load('visualization', '1', {packages:['table']});
      google.setOnLoadCallback(drawTable);
      function drawTable() {
           var jsonData = $.ajax({
          url: "data.php",
          dataType:"json",
          async: false
          }).responseText;
         var data = new google.visualization.DataTable(jsonData);
         var table = new google.visualization.Table(document.getElementById('table_data'));
         table.draw(data, {showRowNumber: true});
      }
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
	<article id="1">
	<h2>Google Chart API</h2>
	<div class="line"></div>
	<br>
	<div style="padding-left:5px; padding-top:8px; width:700px; height:50px;">
	<div style="float:left;"><p style="color:#191970; font-family:Georgia, serif">Published March 12, 2014 | By Maria Montoya</p></div>
	<div style="float:right; margin-left:10px; margin-top:11px;"><a href="https://twitter.com/share" class="twitter-share-button" data-text="naf assignment2">Tweet</a></div>
	<div style="float:right; margin-left:70px; margin-top:11px;"><a href="https://twitter.com/reidliujun" class="twitter-follow-button" data-show-count="false">Follow @reidliujun</a></div>
    </div>
    <div id="chart_div"></div>
    <div id="table_data"></div>
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
