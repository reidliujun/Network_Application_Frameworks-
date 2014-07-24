This is the repository for the assignments in "Network Applicaiton Framework" course in Aalto universiy.


=======================================================================================================================================
Assignment 1:
This is a Eclipse project in JAVA regard to xml schema.

  Run example:
        java -classpath \src Classes.Validate_Read_XML validate
	java -classpath \src Classes.Validate_Read_XML list
	java -classpath \src Classes.Validate_Read_XML add
	java -classpath \src Classes.Validate_Read_XML del 2

	Python script in the 'files' folder change the xml file to json format by using xmltodic library.
	run example:
	python json_generate.py



=======================================================================================================================================
Assignment 2:
This is a blog website based on PHP, JavaScript and HTML5. The goal is to implement Facebook API, Twitter API and Google API.

	The public url for the website is: http://group01.naf.cs.hut.fi
	The application url with the friends location is: https://apps.facebook.com/naf-assignment/
	(additional information about checking the application url: in this demo, we use a self-certificate url for the canvas, therefore, it will show the problem of getting the view of the canvas. The problem can be solved as follows:
			- Open the browser, go to "https://group01.naf.cs.hut.fi/location", when warning page appears, click "trust the site", login to the site and then go to the canvas url again to check the page.
			- If the first solution does not work, try to remove the application from the facebook account setting, and try the first solution again.
	)



=======================================================================================================================================
Assignment 3:
This is an Android application based on Java and Android SDK. The goal is to read and display the online text and image files, use Yahoo Weather API to display the weather of a certain location and use HSL API to check a route. In addition, we implement an twitter client and a simply demo of Google Admob service.

	To run the application, one thing need to mention is to add the Google Service SDK and Facebook SDK to the project library. The detail is shown in https://developers.google.com/mobile-ads-sdk/docs/, https://developer.android.com/google/play-services/setup.html and https://developers.facebook.com/docs/android/. And also the libadapterinmobi.jar and MMAdMobAdapter_1_5_0.jar are extra ads network libraries which has to be added in the Android build library.

	The application has 7 tabs, and each tab shows the required feature of the assignment. For the usage of HSL feature, the add calendar button can be clicked after the route is generated. In the ads feature, to show the interstitial ads, a user has to click the load button first to load the ads. And also, in the code, for test purpose, "addTestDevice" can be uncommented in the Admob.java file.
