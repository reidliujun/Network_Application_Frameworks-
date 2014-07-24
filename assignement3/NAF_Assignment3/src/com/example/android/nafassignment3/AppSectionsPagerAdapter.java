package com.example.android.nafassignment3;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/*************************************************************************************** 
 * REFERENCE: http://developer.android.com/training/implementing-navigation/index.html *
 ***************************************************************************************/

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one of the primary
     * sections of the app.
     */
    public class AppSectionsPagerAdapter extends FragmentPagerAdapter {

        public AppSectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            switch (i) {
                case 0:
                	return new HelloWorld();
                case 1:
                	return new TxtRetriveUI();
                case 2:
                	return new ImgRetrieveUI();
                case 3:
                	return new YahooParser();
				case 4:
                	return new HslFeature();
				case 5:
                	return new TwitterClient();
				case 6:
					return new Admob();
                default:
                	return new HelloWorld();
                }
        }

        @Override
        public int getCount() {
            return 7;
        }

        @Override
        public CharSequence getPageTitle(int position) {
        	switch(position){
        		case 0:
        			return "Hello World";
        		case 1:
        			return "Network Usage";
        		case 2:
        			return "Load Image";
        		case 3:
        			return "Yahoo Weather";
				case 4:
        			return "HSL";
				case 5:
        			return "Twitter Client";
				case 6:
					return "Admob";
        		default:
					return "tab";		
        	}
        }
    }