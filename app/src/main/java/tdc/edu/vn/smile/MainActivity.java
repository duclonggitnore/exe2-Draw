package tdc.edu.vn.smile;

import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    Button btnSave;
    SmileyView smileyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setControl();
        setEvent();
    }

    private void setEvent() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Bạn muốn lưu hình ảnh?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        smileyView.setDrawingCacheEnabled(true);
                        String imageSaved = MediaStore.Images.Media.insertImage(getContentResolver(), smileyView.getDrawingCache(), UUID.randomUUID().toString() + ".png", "drawing");
                        if (imageSaved != null) {
                            Toast.makeText(getApplicationContext(), "Luu Thanh Cong", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Luu That Bai", Toast.LENGTH_LONG).show();
                        }
                        smileyView.destroyDrawingCache();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    private void setControl() {
        btnSave = findViewById(R.id.btnSave);
        smileyView = findViewById(R.id.frame);
    }
}
