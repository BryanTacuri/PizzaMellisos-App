package com.example.pizzamellisos.promotion.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;

import com.example.pizzamellisos.R;
import com.example.pizzamellisos.entities.Product;
import com.example.pizzamellisos.entities.Promotion;
import com.example.pizzamellisos.utils.DownloadImage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class DialogPromotionFragment extends DialogFragment {
    private DatabaseReference fbDataBase;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    Uri selectedImage;
    Activity actividad;
    Boolean isEdit = false;
    String uuid;
    String urlDefault="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTH_8v_IffBeD7lBfi9a00G30AfmqOLd9vZwg&usqp=CAU";

    Button btn_exit;
    Button btn_add_promotion;
    Button btn_image_promotion;
    ImageView img_promotion;

    LinearLayout linearPromotions;
    CardView card;

    TextView txtNamePromotion;
    TextView txtProductsPromotion;
    TextView txtDiscountPromotion;
    TextView txtPricePromotion;
    //TextView txtDateStartPromotion;
    //TextView txtDateEndPromotion;

    public DialogPromotionFragment() {
        // Required empty public constructor
        this.fbDataBase= FirebaseDatabase.getInstance().getReference();
        this.storageReference=FirebaseStorage.getInstance().getReference();
    }

    @NonNull
    @Override
    public AlertDialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return createDialogAddPromotion();
    }

    private AlertDialog createDialogAddPromotion() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.fragment_dialog_add_promotion, null);
        builder.setView(v);

        btn_add_promotion = v.findViewById(R.id.btn_add_promotion_dialog);
        btn_exit = v.findViewById(R.id.btn_close_dialog_promotion);

        txtNamePromotion = v.findViewById(R.id.txt_name_promotion);
        txtProductsPromotion = v.findViewById(R.id.txt_products_promotion);
        txtDiscountPromotion = v.findViewById(R.id.txt_discount_promotion);
        txtPricePromotion = v.findViewById(R.id.text_price_promotion);
        //txtDateStartPromotion = v.findViewById(R.id.text_date_start_promotion);
        //txtDateEndPromotion = v.findViewById(R.id.text_date_end_promotion);
        btn_image_promotion = v.findViewById(R.id.btn_img_promotion);
        img_promotion = v.findViewById(R.id.img_promotion);

        if(getArguments()!=null){
            new DownloadImage(img_promotion).execute(getArguments().getString("url"));
            isEdit = getArguments().getBoolean("edit");
            txtNamePromotion.setText(getArguments().getString("name"));
            txtProductsPromotion.setText(getArguments().getString("prods"));
            txtDiscountPromotion.setText(getArguments().getString("disc"));
            txtPricePromotion.setText(getArguments().getString("price"));
            // txtDateStartPromotion.setText(getArguments().getString("dateStart"));
            // txtDateEndPromotion.setText(getArguments().getString("dateEnd"));
            urlDefault = getArguments().getString("url");
            uuid = getArguments().getString("uuid");
        }
        createEvents();
        return builder.create();
    }

    private void uploadImageAndData(){

        if(selectedImage!=null){
            final String uuid= UUID.randomUUID().toString();

            StorageReference riversRef= storageReference.child("images/"+uuid);

            riversRef.putFile(selectedImage)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Get a URL to the uploaded content
                            taskSnapshot
                                    .getStorage()
                                    .getDownloadUrl()
                                    .addOnSuccessListener(
                                            new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    String url=  uri.toString();
                                                    SaveOrEdit(url);
                                                }
                                            }
                                    );
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            // ...
                        }
                    });
        }else{
            System.out.println("Edit");
            SaveOrEdit(urlDefault);
        }
    }

    private void SaveOrEdit(String url){
        String name_promotion = txtNamePromotion.getText().toString();
        String products_promotion = txtProductsPromotion.getText().toString();
        float discount_promotion = Float.parseFloat(txtDiscountPromotion.getText().toString());
        Double price_promotion = Double.parseDouble(txtPricePromotion.getText().toString());
        //Date date_start = fecha(txtDateStartPromotion.getText().toString());
        //Date date_end = fecha.(txtDateEndPromotion.getText().ToString());
        if(!isEdit){
            Promotion p= new Promotion(UUID.randomUUID().toString(), name_promotion, products_promotion, discount_promotion, price_promotion, url);
            fbDataBase.child("promotions").child(p.getUid()).setValue(p);
            Toast.makeText(getContext(), "Agregado", Toast.LENGTH_LONG);
            dismiss();
        }else{
            Promotion p= new Promotion(uuid, name_promotion, products_promotion, discount_promotion, price_promotion, url);
            fbDataBase.child("promotions").child( p.getUid()).setValue(p);
            Toast.makeText(getContext(), "Editado", Toast.LENGTH_LONG);
            dismiss();
        }
    }

    private void createEvents() {
        btn_add_promotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImageAndData();
            }
        });
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Close", Toast.LENGTH_LONG);
                dismiss();
            }
        });
        btn_image_promotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");

                someActivityResultLauncher.launch(photoPickerIntent);
            }
        });
    }

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    // There are no request codes
                    // doSomeOperations();
                    Intent data = result.getData();
                    selectedImage = Objects.requireNonNull(data).getData();
                    InputStream imageStream = null;
                    try {
                        imageStream = getActivity().getApplicationContext().getContentResolver().openInputStream(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    BitmapFactory.decodeStream(imageStream);
                    img_promotion.setImageURI(selectedImage);// To display selected image in image view
                }
            });

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof  Activity){
            this.actividad=(Activity) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dialog_add_promotion, container, false);
    }
}
