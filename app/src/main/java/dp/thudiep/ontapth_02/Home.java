package dp.thudiep.ontapth_02;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Home extends AppCompatActivity {
    EditText editID,editFN,editLN,editEmail;
    Button btnThem,btnSua,btnXoa;
    CustomAdapter adt;
    ListView listView;
    TextView txtId,firstName,lastName,email;
    ArrayList<Users> arrEmpls;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        editID = findViewById(R.id.editID);
        editFN = findViewById(R.id.editFN);
        editLN = findViewById(R.id.editLN);
        editEmail = findViewById(R.id.editEmail);

        btnThem = findViewById(R.id.btnThem);
        btnSua = findViewById(R.id.btnSua);
        btnXoa = findViewById(R.id.btnXoa);

        txtId = findViewById(R.id.userID);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        email = findViewById(R.id.email);
        listView = findViewById(R.id.listView);

        // Get data từ mockAPI
        String urlGet="https://60b1e7c562ab150017ae16c5.mockapi.io/api/ontapth01/Users/";
        GetData(urlGet);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Users users =arrEmpls.get(position);
                id = users.getId();
                String firstName = users.getFirstName();
                String lastName = users.getLastName();
                String email = users.getEmail();
                System.setProperty("ID", id+"");
                System.setProperty("FirstName",firstName);
                System.setProperty("LastName",lastName);
                System.setProperty("Email",email);
            }
        });
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urlDel="https://60b1e7c562ab150017ae16c5.mockapi.io/api/ontapth01/Users";
                PostApi(urlDel);
                GetData(urlGet);
            }
        });
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urlDel="https://60b1e7c562ab150017ae16c5.mockapi.io/api/ontapth01/Users";
                PutApi(urlDel);
                GetData(urlGet);
            }
        });
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urlDel="https://60b1e7c562ab150017ae16c5.mockapi.io/api/ontapth01/Users";
                DeleteAPI(urlDel);
                GetData(urlGet);
            }
        });

    }

    // Add library Volley
    public void GetData(String url) {
        arrEmpls = new ArrayList<>();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                arrEmpls.add(new Users(object.getInt("id"),
                                        object.getString("email"),
                                        object.getString("firstName"),
                                        object.getString("lastName")));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adt = new CustomAdapter(getApplicationContext(), arrEmpls, R.layout.item);
                        listView.setAdapter(adt);
                        adt.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Home.this, "Lỗi", Toast.LENGTH_SHORT).show();
            }

        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
    public void DeleteAPI(String url) {
        StringRequest request = new StringRequest(
                Request.Method.DELETE, url+'/'+editID.getText().toString(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Home.this, "Successfully Delete", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Home.this, "Faild Delete", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    private void PutApi(String url) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.PUT, url + "/" + editID.getText().toString(),
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(Home.this, "Successfully", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Home.this, "Error by Put data!", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("email", editEmail.getText().toString());
                params.put("lastName", editFN.getText().toString());
                params.put("firstName", editLN.getText().toString());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    private void PostApi(String url) {
        StringRequest stringRequest = new StringRequest( Request.Method.POST,
                url+"",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Home.this, "Successfully", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Home.this, "Error by Post data!", Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("email", editFN.getText().toString());
                params.put("lastName", editFN.getText().toString());
                params.put("firstName", editFN.getText().toString());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}