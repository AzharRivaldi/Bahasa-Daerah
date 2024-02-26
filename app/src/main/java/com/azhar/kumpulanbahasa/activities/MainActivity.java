package com.azhar.kumpulanbahasa.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.azhar.kumpulanbahasa.R;
import com.azhar.kumpulanbahasa.adapter.MainAdapter;
import com.azhar.kumpulanbahasa.model.ModelBahasa;
import com.azhar.kumpulanbahasa.service.ApiService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    SearchView searchData;
    RecyclerView rvDaftarBahasa;
    LinearLayout linearNoData;
    ProgressDialog progressDialog;
    MainAdapter mainAdapter;
    List<ModelBahasa> modelBahasaList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvDaftarBahasa = findViewById(R.id.rvDaftarBahasa);
        linearNoData = findViewById(R.id.linearNoData);
        searchData = findViewById(R.id.searchData);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Tunggu sebentar yaa");
        progressDialog.setCancelable(false);
        progressDialog.setMessage("sedang memeriksa data...");

        linearNoData.setVisibility(View.GONE);

        int searchPlateId = searchData.getContext()
                .getResources().getIdentifier("android:id/search_plate", null, null);
        View searchPlate = searchData.findViewById(searchPlateId);
        if (searchPlate != null) {
            searchPlate.setBackgroundColor(Color.TRANSPARENT);
        }

        searchData.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchData.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mainAdapter.getFilter().filter(newText);
                return true;
            }
        });

        rvDaftarBahasa.setHasFixedSize(true);
        rvDaftarBahasa.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false) {
            @Override
            public void onLayoutCompleted(RecyclerView.State state) {
                super.onLayoutCompleted(state);
                progressDialog.dismiss();
            }
        });

        //get list data API
        getListBahasa();
    }

    private void getListBahasa() {
        progressDialog.show();

        AndroidNetworking.get(ApiService.BASEURL)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        progressDialog.dismiss();

                        if (response.length() == 0) {
                            progressDialog.dismiss();
                            linearNoData.setVisibility(View.VISIBLE);
                            rvDaftarBahasa.setVisibility(View.GONE);
                        } else {
                            linearNoData.setVisibility(View.GONE);
                            rvDaftarBahasa.setVisibility(View.VISIBLE);
                            try {
                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject jsonObject = response.getJSONObject(i);

                                    ModelBahasa dataApi = new ModelBahasa();
                                    dataApi.setStrNomor(jsonObject.getString("nomor"));
                                    dataApi.setStrBahasa(jsonObject.getString("bahasa"));
                                    dataApi.setStrWilayah(jsonObject.getString("listWilayah")
                                            .replace("[", "")
                                            .replace("]", "")
                                            .replace("\"", "")
                                            .replace(",", ", "));

                                    JSONArray jsonArray = jsonObject.getJSONArray("listProvinsi");
                                    for (int j = 0; j < jsonArray.length(); j++) {
                                        JSONObject objectProvisi = jsonArray.getJSONObject(j);
                                        dataApi.setStrId(objectProvisi.getString("id"));
                                        dataApi.setStrProvinsi(objectProvisi.getString("provinsi"));
                                        dataApi.setStrDeskripsi(objectProvisi.getString("deskripsi")
                                                .replace("[", "")
                                                .replace("]", "")
                                                .replace("\"", ""));
                                    }
                                    modelBahasaList.add(dataApi);
                                }
                                mainAdapter = new MainAdapter(MainActivity.this, modelBahasaList);
                                rvDaftarBahasa.setAdapter(mainAdapter);
                                mainAdapter.notifyDataSetChanged();
                                progressDialog.dismiss();
                            } catch (JSONException e) {
                                linearNoData.setVisibility(View.VISIBLE);
                                rvDaftarBahasa.setVisibility(View.GONE);
                                Toast.makeText(MainActivity.this,
                                        "Ups, gagal! " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        progressDialog.dismiss();
                        linearNoData.setVisibility(View.VISIBLE);
                        rvDaftarBahasa.setVisibility(View.GONE);
                        Toast.makeText(MainActivity.this,
                                "Ups, error! " + anError.getErrorDetail(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}