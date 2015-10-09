package mytest.omegasoft.com.mytest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    @Bind(R.id.btnTest)
    Button btnTest;

    @Bind(R.id.txtText1)
    TextView txtText1;

    @Bind(R.id.txtText2)
    TextView txtText2;

    @Bind(R.id.txtText3)
    TextView txtText3;

    @Bind(R.id.txtText4)
    TextView txtText4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @OnClick(R.id.btnTest)
    void clickTestButton() {

    }
}
