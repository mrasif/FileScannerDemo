package in.mrasif.apps.filescannerdemo;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import in.mrasif.apps.filescannerdemo.adapters.MyAdapter;
import in.mrasif.apps.filescannerdemo.utils.FileType;
import in.mrasif.apps.filescannerdemo.utils.FileManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    FileManager fileManager;

    Spinner spExtension;
    Button btnScanFiles;
    RecyclerView rvFiles;
    String exts[]={"mp3","mp4"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spExtension=findViewById(R.id.spExtension);
        btnScanFiles=findViewById(R.id.btnScanFiles);
        rvFiles=findViewById(R.id.rvFiles);
        fileManager = new FileManager(this);
        btnScanFiles.setOnClickListener(this);

        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,exts);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spExtension.setAdapter(aa);
        spExtension.setSelection(0);
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.btnScanFiles){
            if (fileManager.isExternalStorageReadable() && !TextUtils.isEmpty(exts[spExtension.getSelectedItemPosition()])) {
//                File dir = new File(fileManager.getExternalRootPath()+"/WhatsApp/");
                File dir = new File(fileManager.getExternalRootPath());
                rvFiles.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                rvFiles.setAdapter(new MyAdapter(getApplicationContext(),fileManager.ScanFiles(dir,exts[spExtension.getSelectedItemPosition()])));
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
