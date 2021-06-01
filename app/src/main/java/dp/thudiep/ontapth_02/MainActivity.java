package dp.thudiep.ontapth_02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    Button btnDangNhap;
    EditText email,password;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnDangNhap = findViewById(R.id.button);
        email = findViewById(R.id.editEmail);
        password = findViewById(R.id.editPW);

        mAuth = FirebaseAuth.getInstance();

        btnDangNhap.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DangNhap();
            }
        });
    }
    private void DangNhap(){
        String emails = email.getText().toString();
        String passwords = password.getText().toString();
        mAuth.signInWithEmailAndPassword(emails, passwords)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent2 = new Intent(MainActivity.this,Home.class);
                            startActivity(intent2);
                        } else {
                            Toast.makeText(MainActivity.this,"Lỗi",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


}