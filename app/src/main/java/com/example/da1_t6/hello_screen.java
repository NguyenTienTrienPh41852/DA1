package com.example.da1_t6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.MediaController;
import android.widget.VideoView;

public class hello_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_screen);
        VideoView videoView = findViewById(R.id.video);

        // Đường dẫn của video bạn muốn hiển thị
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.videologo);

        // Thiết lập MediaController để điều khiển video (play, pause, seek, ...)
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        // Đặt đường dẫn video cho VideoView
        videoView.setVideoURI(videoUri);

        // Bắt đầu phát video
        videoView.start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Chuyển đến màn hình chính hoặc màn hình khác
                Intent intent = new Intent(hello_screen.this, DangKy.class);
                startActivity(intent);
                // Đóng màn hình hiện tại
                finish();
            }
        }, 2500); // 1000 milliseconds = 1 giây

    }
}