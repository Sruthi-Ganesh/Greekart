package com.example.android.greekart1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the

 */
public class NavigationDrawerFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String KEY ="user learned drawer";
    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout mdrawerLayout;
    private boolean mUserLearnedDrawer;
    private boolean mFromSAvedInstanceState;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    public NavigationDrawerFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);



        }

        mUserLearnedDrawer=Boolean.valueOf(readFromPreference(getContext(),KEY,"false"));
        if(savedInstanceState!=null)
        {
            mFromSAvedInstanceState=true;
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
   /* public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }*/

    public void setUp(int id, final DrawerLayout drawerLayout , final Toolbar toolbar) {
        final View containerView = getActivity().findViewById(id);
        mdrawerLayout = drawerLayout;


        drawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                if (!mUserLearnedDrawer) {
                    mUserLearnedDrawer = true;
                    saveToPreference(getContext(), KEY, String.valueOf(mUserLearnedDrawer));
                }
                getActivity().invalidateOptionsMenu();
                super.onDrawerOpened(drawerView);
            }

            public void onDrawerClosed(View drawerView) {
                getActivity().invalidateOptionsMenu();

                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                toolbar.setAlpha(1 - 0.6f * slideOffset);
                super.onDrawerSlide(drawerView, slideOffset);



            }


        };
        if(!mUserLearnedDrawer && !mFromSAvedInstanceState)
        {
            mdrawerLayout.openDrawer(containerView);
        }
        mdrawerLayout.setDrawerListener(drawerToggle);



        mdrawerLayout.post(new Runnable()
        {
            @Override
            public void run()
            {

                drawerToggle.syncState();
            }
        });

drawerToggle.setToolbarNavigationClickListener(new OnClickListener() {
    @Override
    public void onClick(View v) {
        mdrawerLayout.openDrawer(v);
    }
});



    }



   public void saveToPreference(Context context, String preferenceName, String preferenceValue)
   {
       SharedPreferences sharedPreference = context.getSharedPreferences("navigationdrawer",Context.MODE_PRIVATE);
       SharedPreferences.Editor editor =sharedPreference.edit();
       editor.putString(preferenceName,preferenceValue);
       editor.apply();
   }

   public String readFromPreference(Context context,String preferenceName, String defaultValue)
   {
       SharedPreferences sharedPreference = context.getSharedPreferences("navigationdrawer",Context.MODE_PRIVATE);
       return sharedPreference.getString(preferenceName,defaultValue);
   }


}
