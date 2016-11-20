package sabayouth.autodispenser;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sabayouth.autodispenser.model.Aktivitas;
import sabayouth.autodispenser.model.Alarm;
import sabayouth.autodispenser.model.DbHelper;

/**
 * Created by Yudha Putrama on 9/23/2015.
 */
public class AlarmFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_alarm, container, false);
        return rootView;
    }
}
