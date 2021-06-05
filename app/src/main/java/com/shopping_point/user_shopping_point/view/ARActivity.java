package com.shopping_point.user_shopping_point.view;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.ar.core.Anchor;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.assets.RenderableSource;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.shopping_point.user_shopping_point.R;
import com.shopping_point.user_shopping_point.model.Product;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

import static com.shopping_point.user_shopping_point.utils.Constant.PRODUCT;


public class ARActivity extends AppCompatActivity {
    private ArFragment arFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_r);
    Product product = getIntent().getParcelableExtra(PRODUCT);
        ProgressBar progressBar = findViewById(R.id.progress_mode) ;
        TextView textView = findViewById(R.id.model_text);
        ImageView imgProductImage =  findViewById(R.id.imgProductImage);
        TextView txtProductName = findViewById(R.id.txtProductName);
        TextView txtProductDesc =  findViewById(R.id.txtProductDesc);
        TextView txtProductPrice = findViewById(R.id.txtProductPrice);

        String imageUrl =  product.getProductImage().replaceAll("\\\\", "/");


        Glide.with(this)
                .load(imageUrl)
                .into(imgProductImage);
        String productName = product.getProductName();
        txtProductName.setText(productName);
        String productDesc = product.getProductDesc();

       txtProductDesc.setText(productDesc);

        DecimalFormat formatter = new DecimalFormat("#,###,###");
        String formattedPrice = formatter.format(product.getProductPrice());
        txtProductPrice.setText(formattedPrice + " ₹ ");


        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference modelRef = storage.getReference().child(product.getModelName());
        Toast.makeText(this, "Model Name : " + product.getModelName(), Toast.LENGTH_SHORT).show();

        arFragment = (ArFragment) getSupportFragmentManager()
                .findFragmentById(R.id.arFragment);

        ///Toast.makeText(this, "Model Name : " + modelName, Toast.LENGTH_SHORT).show();
//        findViewById(R.id.downloadBtn)
//                .setOnClickListener(v -> {
//
//
//                });
        progressBar.setVisibility(View.VISIBLE);
        textView.setVisibility(View.VISIBLE);

        try {
            File file = File.createTempFile("model", "glb");

            modelRef.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                    buildModel(file,progressBar,textView);
                    // Toast.makeText(ARActivity.this, "FILE : " + file.toString(), Toast.LENGTH_SHORT).show();
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
        arFragment.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {

//                AnchorNode anchorNode = new AnchorNode(hitResult.createAnchor());
//                anchorNode.setRenderable(renderable);
//                arFragment.getArSceneView().getScene().addChild(anchorNode);
//                TransformableNode transformableNode = new TransformableNode(arFragment.getTransformationSystem());
//                transformableNode.setLocalScale(new Vector3(0.25f,0.25f,0.25f));
//                transformableNode.setParent(anchorNode);
//                transformableNode.setRenderable(renderable);
//
//                arFragment.getArSceneView().getScene().addChild(anchorNode);
//                transformableNode.select();



            Anchor anchor = hitResult.createAnchor();
            AnchorNode anchorNode = new AnchorNode(anchor);
            anchorNode.setParent(arFragment.getArSceneView().getScene());
            TransformableNode node = new TransformableNode(arFragment.getTransformationSystem());
            node.getScaleController().setMaxScale(1.00f);
            node.getScaleController().setMinScale(0.40f);
            node.setParent(anchorNode);
            node.setRenderable(renderable);
            node.select();

        });
    }

    private ModelRenderable renderable;

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void buildModel(File file,ProgressBar progressBar,TextView textView) {

        RenderableSource renderableSource = RenderableSource
                .builder()
                .setSource(this, Uri.parse(file.getPath()), RenderableSource.SourceType.GLB)
                .setRecenterMode(RenderableSource.RecenterMode.ROOT)
                .build();

        ModelRenderable
                .builder()
                .setSource(this, renderableSource)
                .setRegistryId(file.getPath())
                .build()
                .thenAccept(modelRenderable -> {
                    Toast.makeText(this, "Tap on plane surface", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    textView.setVisibility(View.GONE);
                    renderable = modelRenderable;
                });

    }

}
