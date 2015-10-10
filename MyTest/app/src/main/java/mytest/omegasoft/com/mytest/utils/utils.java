package mytest.omegasoft.com.mytest.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import java.util.ArrayList;

import mytest.omegasoft.com.mytest.R;
import mytest.omegasoft.com.mytest.callbacks.DialogCallback;
import mytest.omegasoft.com.mytest.interfaces.Workout;
import mytest.omegasoft.com.mytest.models.Biking;
import mytest.omegasoft.com.mytest.models.Jogging;
import mytest.omegasoft.com.mytest.models.Yoga;
import mytest.omegasoft.com.mytest.views.CircleTimerView;

/**
 * Created by farhad on 10/10/15.
 */
public class utils {

    public static ArrayList<Workout> getEorkouts() {
        ArrayList<Workout> workouts = new ArrayList<>();
        workouts.add(new Jogging());
        workouts.add(new Yoga());
        workouts.add(new Biking());

        return workouts;
    }

    public static void showTimePickerDialog(Context context, int defaultTime, final DialogCallback dialogCallback) {
        // custom dialog
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.time_picker_dialog);
        dialog.setTitle(R.string.select_time);

        final CircleTimerView timerView = (CircleTimerView) dialog.findViewById(R.id.timepicker_circletimerview);
        timerView.setTime(defaultTime);

        Button btnOK = (Button) dialog.findViewById(R.id.timepicker_ok);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogCallback.ok(timerView.getCurrentTime());
                dialog.dismiss();
            }
        });

        Button btnCancel = (Button) dialog.findViewById(R.id.timepicker_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                dialogCallback.cancel();
            }
        });

        dialog.show();
    }


    public static void showNumberPickerDialog(Context context, int defaultTime, final DialogCallback dialogCallback) {
        // custom dialog
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.number_picker_dialog);
        dialog.setTitle(R.string.select_time);

        final NumberPicker numberPicker = (NumberPicker) dialog.findViewById(R.id.numberpicker_numberpicker);
        numberPicker.setValue(defaultTime);

        Button btnOK = (Button) dialog.findViewById(R.id.numberpicker_ok);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogCallback.ok(numberPicker.getValue());
                dialog.dismiss();
            }
        });

        Button btnCancel = (Button) dialog.findViewById(R.id.numberpicker_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                dialogCallback.cancel();
            }
        });

        dialog.show();
    }

    public static String timeToString(int time) {
        return String.format("%d:%02d:%02d", time / 3600, (time % 3600) / 60, (time % 60));
    }
}
