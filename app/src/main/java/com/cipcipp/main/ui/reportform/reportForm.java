package com.cipcipp.main.ui.reportform;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cipcipp.main.R;
import com.cipcipp.main.helper.FirebaseHelper;
import com.cipcipp.main.model.Report;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Arrays;

public class reportForm extends AppCompatActivity {
    private String[] apps = {"pilih provider", "Loading data provider.."};
    private String[] values = {"pilih nominal", "Loading data nominal.."};
    private String[] preSelectedImage = {"pilih provider", "silakan pilih screenshot terlebih dahulu"};
    private String[] preSelectedImage2 = {"pilih nominal", "silakan pilih screenshot terlebih dahulu"};
    private static final int GALLERY_CODE = 1;
    private Uri imageUri;
    private ImageView inputImage;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private String title;
    private String title_;
    final FirebaseAuth firebaseAuthz = FirebaseAuth.getInstance();
    final FirebaseUser firebaseUserz = firebaseAuthz.getCurrentUser();
    private EditText true_price;
    private ArrayList<String> provinces = new ArrayList<>();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        try {
//            final JSONObject obj = new JSONObject(Indonesia.wawa);
//            for(int i = 1; i<34;i++) {
//                provinces.add(obj.getJSONObject(String.valueOf(i)).names().get(0).toString());
//            }
//        } catch (JSONException error) {
//            Log.e("error json",error+"");
//        }
        super.onCreate(savedInstanceState);
        final String title = getIntent().getStringExtra("title");
        storageReference = FirebaseStorage.getInstance().getReference();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("report").child(title);

        setContentView(R.layout.report_form);
        findViewById(R.id.spinner).setVisibility(View.GONE);
        findViewById(R.id.spinner2).setVisibility(View.GONE);
        findViewById(R.id.true_price).setVisibility(View.GONE);
        TextView report_header = findViewById(R.id.report_header);
        title_ = report_header.getText().toString() + " " + title;
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title_);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        (findViewById(R.id.report_header)).setVisibility(View.GONE);
        providerSpinListener(preSelectedImage);
        nominalSpinListener(preSelectedImage2);
        inputImage = findViewById(R.id.input_image);
        (inputImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_CODE);
            }
        });

        true_price = findViewById(R.id.true_price);
        Button report_button = findViewById(R.id.report_button);
        report_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageUri == null) {
                    Toast.makeText(reportForm.this, "pilih bukti screenshot terlebih dahulu", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        findViewById(R.id.spinner).setVisibility(View.VISIBLE);
        findViewById(R.id.spinner2).setVisibility(View.VISIBLE);
        findViewById(R.id.true_price).setVisibility(View.VISIBLE);
        if (requestCode == GALLERY_CODE && resultCode == RESULT_OK) {
            imageUri = data.getData();
            final RelativeLayout.LayoutParams first_state_image = (RelativeLayout.LayoutParams) inputImage.getLayoutParams();
            inputImage.setImageURI(imageUri);
            findViewById(R.id.textImage).setVisibility(View.GONE);
            RelativeLayout.LayoutParams imageViewParam = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            inputImage.setLayoutParams(imageViewParam);
            final Spinner spinner = providerSpinListener(apps);
            final Spinner spinner2 = nominalSpinListener(values);
            FirebaseHelper helper = new FirebaseHelper(reportForm.this, title);
            helper.getProviderName(new reportFormInt() {
                @Override
                public void onCallBack(ArrayList<String> strings, ArrayList<String> colValz) {
                    String[] providers = Arrays.copyOf(strings.toArray(), strings.toArray().length, String[].class);
                    String[] nominal = Arrays.copyOf(colValz.toArray(), colValz.toArray().length, String[].class);
                    final Spinner spinner = providerSpinListener(providers);
                    final Spinner spinner2 = nominalSpinListener(nominal);
                    findViewById(R.id.report_button).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (nullchecker(spinner, spinner2)) {
                                postReport(imageUri, first_state_image, spinner, spinner2);
                            }
                        }
                    });
                }

            });
            findViewById(R.id.report_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (nullchecker(spinner, spinner2)) {
                        postReport(imageUri, first_state_image, spinner, spinner2);
                    }
                }
            });
        }
    }

    private Spinner providerSpinListener(String[] apps) {
        final Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter adapterspinner = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, apps);
        adapterspinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterspinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return spinner;
    }

    private Spinner nominalSpinListener(String[] values) {
        final Spinner spinner2 = findViewById(R.id.spinner2);
        ArrayAdapter adapterspinner2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, values);
        adapterspinner2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapterspinner2);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return spinner2;
    }

    private void postReport(Uri imageUri, final RelativeLayout.LayoutParams first_state_image,
                            final Spinner spinner, final Spinner spinner2) {
        if (imageUri.getLastPathSegment() != null) {
            String uploading = "Uploading..";
            ((TextView) findViewById(R.id.report_header)).setText(uploading);
            findViewById(R.id.report_content).setVisibility(View.GONE);
            final StorageReference filepath = storageReference.child("SS").child(title).child(imageUri.getLastPathSegment());
            filepath.putFile(imageUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful() && task.getException() != null) {
                        throw task.getException();
                    }
                    return filepath.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        if (downloadUri != null && firebaseUserz != null) {
                            Report report = new Report(
                                    String.valueOf(spinner.getSelectedItem()),
                                    spinner2.getSelectedItem().toString(),
                                    true_price.getText().toString(),
                                    downloadUri.toString(),
                                    String.valueOf(java.lang.System.currentTimeMillis()),
                                    firebaseUserz.getEmail()
                            );
                            DatabaseReference newPost = databaseReference.push();
                            newPost.setValue(report).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(reportForm.this, "berhasil upload", Toast.LENGTH_SHORT).show();
                                    ((TextView) findViewById(R.id.report_header)).setText(title_);
                                    ((EditText) findViewById(R.id.true_price)).setText("");
                                    inputImage.setLayoutParams(first_state_image);
                                    inputImage.setImageDrawable(null);
                                    findViewById(R.id.report_content).setVisibility(View.VISIBLE);
                                    providerSpinListener(preSelectedImage);
                                    nominalSpinListener(preSelectedImage2);
                                }
                            });
                        }
                    } else {
                        Toast.makeText(reportForm.this, "error.. file tidak ditemukan.. harap hubungi help center", Toast.LENGTH_SHORT).show();
                        String error = "error";
                        ((TextView) findViewById(R.id.report_header)).setText(error);
                    }
                }
            });
        }
    }

    private boolean nullchecker(final Spinner spinner, final Spinner spinner2) {
        boolean checker = true;
        if (imageUri == null) {
            Toast.makeText(reportForm.this, "pilih bukti screenshot terlebih dahulu", Toast.LENGTH_SHORT).show();
            checker = false;
        } else if (spinner.getSelectedItemPosition() == 0) {
            Toast.makeText(reportForm.this, "opsi provider tidak boleh kosong..", Toast.LENGTH_SHORT).show();
            checker = false;
        } else if (spinner2.getSelectedItemPosition() == 0) {
            Toast.makeText(reportForm.this, "opsi nominal tidak boleh kosong..", Toast.LENGTH_SHORT).show();
            checker = false;
        } else if (true_price.getText().toString().equals("")) {
            Toast.makeText(reportForm.this, "field harga sebenarnya tidak boleh kosong", Toast.LENGTH_SHORT).show();
            checker = false;
        }
        return checker;
    }
}
