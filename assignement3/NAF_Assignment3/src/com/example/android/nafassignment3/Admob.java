package com.example.android.nafassignment3;
import java.util.GregorianCalendar;

import com.example.android.effectivenavigation.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.ads.mediation.inmobi.InMobiAdapterExtras;
import com.google.ads.mediation.millennial.MillennialAdapterExtras;

/**
 * reference: https://developers.google.com/mobile-ads-sdk/docs/admob/fundamentals
 */
public class Admob extends Fragment {
	private AdView adView;
	private static final String AD_UNIT_ID = "ca-app-pub-2502384087155826/4890328999";
	/** The interstitial ad. */
	private InterstitialAd interstitialAd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.admob, container, false);
        
     // Add an ad banner
        adView = (AdView)rootView.findViewById(R.id.adView);
	    AdRequest adRequest = new AdRequest.Builder()
//	    	.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
//	    	.addTestDevice("72BBB778248F86F464737E9BCCC2F750")
	    	.setGender(AdRequest.GENDER_FEMALE)
	    	.setBirthday(new GregorianCalendar(1985, 1, 1).getTime())
	    	.build();
	    adView.loadAd(adRequest);
	    
	 // Add an ad Interstitial
	    interstitialAd = new InterstitialAd(this.getActivity());
	    interstitialAd.setAdUnitId(AD_UNIT_ID);
	    
	    rootView.findViewById(R.id.loadButton)
        .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Millennial Media extra parameters.
                MillennialAdapterExtras millennialExtras = new MillennialAdapterExtras();
                millennialExtras.setIncomeInUsDollars(65000);
                
                InMobiAdapterExtras inMobiExtras = new InMobiAdapterExtras();
                inMobiExtras.setIncome(65000);
                
            	AdRequest adRequest1 = new AdRequest.Builder()
//    	    	.addTestDevice("72BBB778248F86F464737E9BCCC2F750")
    	    	.addNetworkExtras(millennialExtras)
    	    	.addNetworkExtras(inMobiExtras)
    	    	.build();
            	interstitialAd.loadAd(adRequest1);
            }
        });
	    
        rootView.findViewById(R.id.AdShowButton)
        .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	if (interstitialAd.isLoaded()) {
            		interstitialAd.show();
            	}
            }
        });
	    return rootView;
    }
}



