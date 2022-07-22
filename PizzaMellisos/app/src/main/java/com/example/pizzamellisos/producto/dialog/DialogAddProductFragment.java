package com.example.pizzamellisos.producto.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pizzamellisos.R;
import com.example.pizzamellisos.entities.Product;
import com.example.pizzamellisos.utils.DownloadImage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Objects;
import java.util.UUID;


public class DialogAddProductFragment extends DialogFragment {
    private DatabaseReference fbDataBase;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    Uri selectedImage;
    Activity actividad;
    Boolean isEdit=false;
    String uuid;
    String urlDefault="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTH_8v_IffBeD7lBfi9a00G30AfmqOLd9vZwg&usqp=CAU";

    //IComunicaFragments iComunicaFragments;

    Button btn_salir;
    Button btn_add_prodcut;
    Button btn_image_product;
    ImageView img_product;

    LinearLayout lienarProducts;
    CardView  card;

    TextView txtNameProduct;
    TextView txtDescriptionProduct;
    TextView txtPrice;

    public DialogAddProductFragment() {
        // Required empty public constructor
        this.fbDataBase= FirebaseDatabase.getInstance().getReference();
        this.storageReference=FirebaseStorage.getInstance().getReference();
    }


    @NonNull
    @Override
    public AlertDialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        return createDialogAddProduct();
    }

    private AlertDialog createDialogAddProduct() {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater =getActivity().getLayoutInflater();
        View v=inflater.inflate(R.layout.fragment_dialog_add_product, null);
        builder.setView(v);

        btn_add_prodcut=v.findViewById(R.id.btn_add_prduct_dialog);
        btn_salir=v.findViewById(R.id.btn_close_dialog_product);

        txtNameProduct=v.findViewById(R.id.txtNameProduct);
        txtDescriptionProduct=v.findViewById(R.id.txtDescriptionProduct);
        txtPrice=v.findViewById(R.id.txtPriceProduct);
        btn_image_product=v.findViewById(R.id.btn_img_product);
        img_product=v.findViewById(R.id.img_product);


        if(getArguments()!=null){
            new DownloadImage(img_product)
                    .execute(getArguments().getString("url"));
            isEdit=getArguments().getBoolean("edit");
            txtNameProduct.setText(getArguments().getString("name"));
            txtDescriptionProduct.setText(getArguments().getString("desc"));
            txtPrice.setText(getArguments().getString("price"));
            urlDefault=getArguments().getString("url");
            uuid=getArguments().getString("uuid");
        }

       // img_product.setText(getArguments().getString("name"));



        //lienarProducts=v.findViewById(R.id.lytProducts);
        createEvents();
        return builder.create();
    }
    private void uploadImageAndData(){

        if(selectedImage!=null){
            final String uuid=UUID.randomUUID().toString();

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
                                                    // Uri downloadUrl = uri;
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
        String name_product=txtNameProduct.getText().toString();
        String Description=txtDescriptionProduct.getText().toString();
        double price=Double.parseDouble(txtPrice.getText().toString());

        //String url=  uri.toString();
        if(!isEdit){
            Product p= new Product(UUID.randomUUID().toString(), url, name_product, Description, price);
            fbDataBase.child("products").child(p.getUid()).setValue(p);
            Toast.makeText(getContext(), "Agregado", Toast.LENGTH_LONG);
            dismiss();
        }else{

            Product p= new Product(uuid, url, name_product, Description, price);
            fbDataBase.child("products").child( p.getUid()).setValue(p);
            Toast.makeText(getContext(), "Editado", Toast.LENGTH_LONG);
            dismiss();

        }

    }
    private void createEvents() {

        btn_add_prodcut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImageAndData();
            }
        });
        btn_salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Close", Toast.LENGTH_LONG);
                dismiss();
            }
        });
        btn_image_product.setOnClickListener(new View.OnClickListener() {
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
                    img_product.setImageURI(selectedImage);// To display selected image in image view
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
        return inflater.inflate(R.layout.fragment_dialog_add_product, container, false);
    }
}