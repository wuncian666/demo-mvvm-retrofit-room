package com.example.myroom;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myroom.database.DataBase;
import com.example.myroom.databinding.ActivityMainBinding;
import com.example.myroom.retrofit.AwsInfoResponse;
import com.example.myroom.viewmodel.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = MainActivity.class.getSimpleName();
    private ActivityMainBinding binding;

    private MyAdapter myAdapter;

    private MainActivityViewModel mainActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        Button btCreate = binding.buttonCreate;
        Button btModify = binding.buttonModify;
        Button btClear = binding.buttonClear;

        EditText edGatewayUuid = binding.editTextGatewayUuid;

        RecyclerView recyclerView = binding.recyclerView;

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // 分隔線
        binding.recyclerView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        setRecyclerFunction(binding.recyclerView);

        myAdapter = new MyAdapter(this, mainActivityViewModel.getAswInfoList().getValue());

        myAdapter.setOnItemClickListener(myData ->
                edGatewayUuid.setText(myData.getGatewayUuid()));

        recyclerView.setAdapter(myAdapter);

        // observer: loading
        mainActivityViewModel.getProgressLiveData().observe(this, integer -> {
            binding.progress.setVisibility(integer);
        });

        // observer: when database change up date recycle view
        mainActivityViewModel.getAswInfoList().observe(this, awsInfoResponses -> {
            myAdapter.setOnItemClickListener(myData ->
                    edGatewayUuid.setText(myData.getGatewayUuid()));
            myAdapter.refreshView(awsInfoResponses);// 重整
        });

        // observer:
        mainActivityViewModel.getApiResultMutableData().observe(this, s -> {
            if (s.equals("Success")) {
                Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
            } else {
                // if failure or null
                Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
            }
        });

        // when click button request api
        binding.buttonCreate.setOnClickListener(v -> {
            String gatewayUuid = edGatewayUuid.getText().toString();
            if (gatewayUuid.length() == 0) return;

            mainActivityViewModel.requestApi(gatewayUuid.toUpperCase());// 大寫uuid
        });


        binding.buttonModify.setOnClickListener(v -> {
            // TODO: no process

            // post...
            //if (nowSelectedData == null) return;//如果目前沒前台沒有資料，則以下程序不執行

            String gatewayUuid = edGatewayUuid.getText().toString();

            // MyData data = new MyData(name, phone, hobby, elseInfo);

            mainActivityViewModel.updateAwsInfo(
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "");

            edGatewayUuid.setText("");
            //  nowSelectedData = null;
            Toast.makeText(this, "已更新資訊！", Toast.LENGTH_LONG).show();
        });


        binding.buttonClear.setOnClickListener(v -> {
            edGatewayUuid.setText("");
        });
    }

    private void setRecyclerFunction(RecyclerView recyclerView) {
        //設置RecyclerView手勢功能
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView,
                                        @NonNull RecyclerView.ViewHolder viewHolder) {
                return makeMovementFlags(
                        0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                switch (direction) {
                    case ItemTouchHelper.LEFT:
                    case ItemTouchHelper.RIGHT:
                        myAdapter.deleteData(position);
                        break;

                }
            }
        });
        helper.attachToRecyclerView(recyclerView);
    }
}