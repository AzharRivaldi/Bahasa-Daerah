package com.azhar.kumpulanbahasa.activities;

import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.azhar.kumpulanbahasa.R;
import com.azhar.kumpulanbahasa.model.ModelBahasa;

public class DetailActivity extends AppCompatActivity {

    public static final String DETAIL_DATA = "DETAIL_DATA";
    String strProvinsi, strDeskripsi;
    ModelBahasa modelBahasa;
    Toolbar toolbar;
    TextView tvProvinsi, tvDeskripsi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        toolbar = findViewById(R.id.toolbar);
        tvProvinsi = findViewById(R.id.tvProvinsi);
        tvDeskripsi = findViewById(R.id.tvDeskripsi);

        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        modelBahasa = (ModelBahasa) getIntent().getSerializableExtra(DETAIL_DATA);
        if (modelBahasa != null) {
            strProvinsi = modelBahasa.getStrProvinsi();
            strDeskripsi = modelBahasa.getStrDeskripsi();

            tvProvinsi.setText(strProvinsi);

            tvDeskripsi.setText(Html.fromHtml(strDeskripsi, Html.FROM_HTML_MODE_LEGACY));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}