package com.example.quehacemoshoy;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class decideforme extends AppCompatActivity {

    String[] options = new String[8];
    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_decideforme);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tv=findViewById(R.id.textView);

        options= new String[]
                {"Listen to a music album and rate each song from 1 to 5.",
                "Go outside and photograph 10 flowers (you could even create a photo album of local flowers!).",
                "Draw a self-portrait in no more than 10 minutes and upload it to Instagram.",
                "Go buy milk, sugar, and strawberries, then make strawberry milk. Invite your best friend to have some with you!",
                "Host a tea party and invite your best friend.",
                "Play online pool until you win 3 games in a row.",
                "Reflect on your ideal future for one hour and write down your conclusions.", "Fill the bathtub and take a 20-minute bath while listening to music.",
                "Call your closest family member and talk with them about this app."};

        int numero = (int) (Math.random() * 9);
        tv.setText(options[numero]);
    }


}