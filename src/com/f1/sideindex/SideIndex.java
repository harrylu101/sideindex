
package com.f1.sideindex;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 
 * @author harry
 * 
 */
public class SideIndex extends Activity {

    private static final String  TAG                        = SideIndex.class.getSimpleName ();

    private static final int     SIDE_INDEX_TITLE_TEXT_SIZE = 15;

    private GestureDetector      mGestureDetector;

    // x and y coordinates within our side index
    private static float         sideIndexX;
    private static float         sideIndexY;

    // height of side index
    private int                  sideIndexHeight;

    // number of items in the side index
    private int                  indexListSize;

    // list with items for side index
    private ArrayList <Object[]> indexList                  = null;

    // an array with countries to display in the list
    static String[]              COUNTRIES                  = new String[] {
            "East Timor", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea",
            "Estonia", "Ethiopia", "Faeroe Islands", "Falkland Islands", "Fiji", "Finland",
            "Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra", "Angola", "Anguilla",
            "Antarctica", "Antigua and Barbuda", "Argentina", "Armenia", "Aruba", "Australia",
            "Austria", "Azerbaijan", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium",
            "Monaco", "Mongolia", "Montserrat", "Morocco", "Mozambique", "Myanmar", "Namibia",
            "Nauru", "Nepal", "Netherlands", "Netherlands Antilles", "New Caledonia",
            "New Zealand", "Guyana", "Haiti", "Heard Island and McDonald Islands", "Honduras",
            "Hong Kong", "Hungary", "Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland",
            "Israel", "Italy", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati",
            "Kuwait", "Kyrgyzstan", "Laos", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya",
            "Liechtenstein", "Lithuania", "Luxembourg", "Nicaragua", "Niger", "Nigeria", "Niue",
            "Norfolk Island", "North Korea", "Northern Marianas", "Norway", "Oman", "Pakistan",
            "Palau", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines",
            "Pitcairn Islands", "Poland", "Portugal", "Puerto Rico", "Qatar",
            "French Southern Territories", "Gabon", "Georgia", "Germany", "Ghana", "Gibraltar",
            "Greece", "Greenland", "Grenada", "Guadeloupe", "Guam", "Guatemala", "Guinea",
            "Guinea-Bissau", "Martinique", "Mauritania", "Mauritius", "Mayotte", "Mexico",
            "Micronesia", "Moldova", "Bosnia and Herzegovina", "Botswana", "Bouvet Island",
            "Brazil", "British Indian Ocean Territory", "Saint Vincent and the Grenadines",
            "Samoa", "San Marino", "Saudi Arabia", "Senegal", "Seychelles", "Sierra Leone",
            "Singapore", "Slovakia", "Slovenia", "Solomon Islands", "Somalia", "South Africa",
            "South Georgia and the South Sandwich Islands", "South Korea", "Spain", "Sri Lanka",
            "Sudan", "Suriname", "Svalbard and Jan Mayen", "Swaziland", "Sweden", "Switzerland",
            "Syria", "Taiwan", "Tajikistan", "Tanzania", "Thailand", "The Bahamas", "The Gambia",
            "Togo", "Tokelau", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan",
            "Turks and Caicos Islands", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates",
            "United Kingdom", "United States", "United States Minor Outlying Islands", "Uruguay",
            "Uzbekistan", "Vanuatu", "Vatican City", "Venezuela", "Vietnam", "Virgin Islands",
            "Wallis and Futuna", "Western Sahara", "British Virgin Islands", "Brunei", "Bulgaria",
            "Burkina Faso", "Burundi", "Cote d'Ivoire", "Cambodia", "Cameroon", "Canada",
            "Cape Verde", "Cayman Islands", "Central African Republic", "Chad", "Chile", "China",
            "Reunion", "Romania", "Russia", "Rwanda", "Sqo Tome and Principe", "Saint Helena",
            "Saint Kitts and Nevis", "Saint Lucia", "Saint Pierre and Miquelon", "Belize", "Benin",
            "Bermuda", "Bhutan", "Bolivia", "Christmas Island", "Cocos (Keeling) Islands",
            "Colombia", "Comoros", "Congo", "Cook Islands", "Costa Rica", "Croatia", "Cuba",
            "Cyprus", "Czech Republic", "Democratic Republic of the Congo", "Denmark", "Djibouti",
            "Dominica", "Dominican Republic", "Former Yugoslav Republic of Macedonia", "France",
            "French Guiana", "French Polynesia", "Macau", "Madagascar", "Malawi", "Malaysia",
            "Maldives", "Mali", "Malta", "Marshall Islands", "Yemen", "Yugoslavia", "Zambia",
            "Zimbabwe"
                                                            };

    @Override
    public void onCreate (Bundle savedInstanceState) {

        super.onCreate (savedInstanceState);
        setContentView (R.layout.main);

        // don't forget to sort our array (in case it's not sorted)
        Arrays.sort (COUNTRIES);

        Log.d (TAG, COUNTRIES.toString ());

        final ListView lv1 = (ListView) findViewById (R.id.ListView01);
        lv1.setAdapter (new ArrayAdapter <String> (this, android.R.layout.simple_list_item_1,
                COUNTRIES));
        mGestureDetector = new GestureDetector (this, new SideIndexGestureListener ());
    }

    @Override
    public boolean onTouchEvent (MotionEvent event) {

        if (mGestureDetector.onTouchEvent (event)) {
            return true;
        }

        return false;

    }

    private ArrayList <Object[]> createIndex (String[] strArr) {

        ArrayList <Object[]> tmpIndexList = new ArrayList <Object[]> ();
        Object[] tmpIndexItem = null;

        int tmpPos = 0;
        String tmpLetter = "";
        String currentLetter = null;
        String strItem = null;

        for (int j = 0; j < strArr.length; j++) {
            strItem = strArr[j];
            currentLetter = strItem.substring (0, 1);

            // every time new letters comes
            // save it to index list
            if (!currentLetter.equals (tmpLetter)) {
                tmpIndexItem = new Object[3];
                tmpIndexItem[0] = tmpLetter;
                tmpIndexItem[1] = tmpPos;
                tmpIndexItem[2] = j - 1;

                tmpLetter = currentLetter;
                tmpPos = j;

                tmpIndexList.add (tmpIndexItem);
            }
        }

        // save also last letter
        tmpIndexItem = new Object[3];
        tmpIndexItem[0] = tmpLetter;
        tmpIndexItem[1] = tmpPos - 1;
        tmpIndexItem[2] = strArr.length - 1;
        tmpIndexList.add (tmpIndexItem);

        // and remove first temporary empty entry
        if (tmpIndexList != null && tmpIndexList.size () > 0) {
            tmpIndexList.remove (0);
        }

        return tmpIndexList;
    }

    @Override
    public void onWindowFocusChanged (boolean hasFocus) {

        super.onWindowFocusChanged (hasFocus);

        LinearLayout sideIndex = (LinearLayout) findViewById (R.id.sideIndex);
        sideIndexHeight = sideIndex.getHeight ();
        sideIndex.removeAllViews ();

        Log.d (TAG, "side index height :" + sideIndexHeight);
        Log.d (TAG, "size index view count :" + sideIndex.getChildCount ());

        // TextView for every visible item
        TextView tmpTV = null;

        // we'll create the index list
        indexList = createIndex (COUNTRIES);

        // number of items in the index List
        indexListSize = indexList.size ();

        // maximal number of items, which could be displayed
        int indexMaxSize = (int) Math.floor (sideIndex.getHeight () / SIDE_INDEX_TITLE_TEXT_SIZE);

        int tmpIndexListSize = indexListSize;

        // handling that case when indexListSize > indexMaxSize
        while (tmpIndexListSize > indexMaxSize) {
            tmpIndexListSize = tmpIndexListSize / 2;

        }

        // computing delta (only a part of items will be displayed to save a
        // place)
        double delta = indexListSize / tmpIndexListSize;

        String tmpLetter = null;
        Object[] tmpIndexItem = null;

        // show every m-th letter
        for (double i = 1; i <= indexListSize; i = i + delta) {
            tmpIndexItem = indexList.get ((int) i - 1);
            tmpLetter = tmpIndexItem[0].toString ();
            tmpTV = new TextView (this);
            tmpTV.setText (tmpLetter);
            tmpTV.setGravity (Gravity.CENTER);
            tmpTV.setTextSize (SIDE_INDEX_TITLE_TEXT_SIZE);
            LayoutParams params = new LayoutParams (LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT, 1);
            tmpTV.setLayoutParams (params);
            sideIndex.addView (tmpTV);
        }

        // and set a touch listener for it
        sideIndex.setOnTouchListener (new OnTouchListener () {
            @Override
            public boolean onTouch (View v, MotionEvent event) {

                // now you know coordinates of touch
                sideIndexX = event.getX ();
                sideIndexY = event.getY ();

                // and can display a proper item it country list
                displayListItem ();

                return false;
            }
        });
    }

    class SideIndexGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll (MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

            // we know already coordinates of first touch
            // we know as well a scroll distance
            sideIndexX = sideIndexX - distanceX;
            sideIndexY = sideIndexY - distanceY;

            // when the user scrolls within our side index
            // we can show for every position in it a proper
            // item in the country list
            if (sideIndexX >= 0 && sideIndexY >= 0) {
                displayListItem ();
            }

            return super.onScroll (e1, e2, distanceX, distanceY);
        }
    }

    /**
     * rewrite this part
     * 
     * compute where the item should be
     * get the item from the array list
     * get the starting position, and set selection
     * 
     */
    public void displayListItem () {

        // compute number of pixels for every side index item
        double pixelPerIndexItem = (double) sideIndexHeight / indexListSize;

        // compute the item index for given event position belongs to
        int itemPosition = (int) (sideIndexY / pixelPerIndexItem);

        // get the item (we can do it since we know item index)
        Object[] indexItem = indexList.get (itemPosition);

        // and compute the proper item in the country list
        int indexMin = Integer.parseInt (indexItem[1].toString ());

        ListView listView = (ListView) findViewById (R.id.ListView01);
        listView.setSelectionFromTop (indexMin, 0);

    }
}
