package mytest.omegasoft.com.mytest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import mytest.omegasoft.com.mytest.adapters.MyAdapter;
import mytest.omegasoft.com.mytest.database.DatabaseHandler;
import mytest.omegasoft.com.mytest.database.model.History;

public class HistoryActivity extends AppCompatActivity {

    @Bind(R.id.history_recycler_view)
    RecyclerView mRecyclerView;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        ButterKnife.bind(this);


        DatabaseHandler databaseHandler = new DatabaseHandler(this);

        ArrayList<History> histories = databaseHandler.getAllHistories();

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(histories);
        mRecyclerView.setAdapter(mAdapter);


    }
}
