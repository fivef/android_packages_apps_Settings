
package com.android.settings;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceGroup;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceScreen;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.R;
import com.android.settings.widgets.TouchInterceptor;
import com.android.settings.widgets.SeekBarPreference;
import com.scheffsblend.smw.Preferences.ImageListPreference;
import net.margaritov.preference.colorpicker.ColorPickerPreference;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class Bamquicksettings extends SettingsPreferenceFragment implements
        OnPreferenceChangeListener {

    private static final String TAG = "TogglesLayout";

    private static final String PREF_ENABLE_FASTTOGGLE = "enable_fast_toggle";
    private static final String PREF_CHOOSE_FASTTOGGLE_SIDE = "choose_fast_toggle_side";

    CheckBoxPreference mFastToggle;
    ListPreference mChooseFastToggleSide;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.jellybam_quick_settings_title);
        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.jellybam_quick_settings);

        mFastToggle = (CheckBoxPreference) findPreference(PREF_ENABLE_FASTTOGGLE);
        mFastToggle.setOnPreferenceChangeListener(this);

        mChooseFastToggleSide = (ListPreference) findPreference(PREF_CHOOSE_FASTTOGGLE_SIDE);
        mChooseFastToggleSide.setOnPreferenceChangeListener(this);
        mChooseFastToggleSide.setValue(Settings.System.getInt(getActivity().getContentResolver(),
                Settings.System.CHOOSE_FASTTOGGLE_SIDE, 1) + "");
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference == mFastToggle) {
            boolean val = (Boolean) newValue;
            Settings.System.putBoolean(getActivity().getContentResolver(),
                    Settings.System.FAST_TOGGLE, val);
            getActivity().getBaseContext().getContentResolver().notifyChange(Settings.System.getUriFor(Settings.System.FAST_TOGGLE), null);
            return true;
        } else if (preference == mChooseFastToggleSide) {
            int val = Integer.parseInt((String) newValue);
            Settings.System.putInt(getActivity().getContentResolver(),
                    Settings.System.CHOOSE_FASTTOGGLE_SIDE, val);
            getActivity().getBaseContext().getContentResolver().notifyChange(Settings.System.getUriFor(Settings.System.CHOOSE_FASTTOGGLE_SIDE), null);
            mChooseFastToggleSide.setValue(Settings.System.getInt(getActivity().getContentResolver(),
                Settings.System.CHOOSE_FASTTOGGLE_SIDE, 1) + "");
        }
        return false;
    }
}
