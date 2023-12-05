package com.example.da1_t6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.MediaController;
import android.widget.VideoView;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;

public class hello_screen extends AppCompatActivity {
    private PlayerView playerView;
    private SimpleExoPlayer player;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_screen);
        playerView = findViewById(R.id.playerView);

        // Đường dẫn của video trong thư mục raw
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.videologo);

        // Tạo một ExoPlayer
        player = new SimpleExoPlayer.Builder(this).build();

        // Set Uri cho ExoPlayer
        player.setMediaItem(MediaItem.fromUri(videoUri));

        // Kết nối ExoPlayer với PlayerView
        playerView.setPlayer(player);

        // Bắt sự kiện khi video đã sẵn sàng để phát
        player.addListener(new ExoPlayer.EventListener() {
            @Override
            public void onIsPlayingChanged(boolean isPlaying) {
                if (isPlaying) {
                    // Sử dụng Handler để chuyển màn hình sau 5 giây
                    handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Chuyển đến màn hình chính hoặc màn hình khác
                            Intent intent = new Intent(hello_screen.this, DangKy.class);
                            startActivity(intent);

                            // Đóng màn hình hiện tại
                            finish();
                        }
                    }, 2500); // 5000 milliseconds = 5 giây
                }
            }
        });

        // Bắt sự kiện khi video phát xong

        // Bắt đầu phát video
        player.prepare();
        player.setPlayWhenReady(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Giải phóng ExoPlayer khi không sử dụng nữa
        if (player != null) {
            player.release();
            player = null;
        }
        // Hủy bỏ handler để tránh rò rỉ bộ nhớ
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }
}