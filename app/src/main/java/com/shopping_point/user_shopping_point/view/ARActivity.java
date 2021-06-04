package com.shopping_point.user_shopping_point.view;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.assets.RenderableSource;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.shopping_point.user_shopping_point.R;

import java.io.File;
import java.io.IOException;

public class ARActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_r);

        FirebaseApp.initializeApp(this);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference modelRef = storage.getReference().child("model.glb");
       // Toast.makeText(this, modelRef.toString(), Toast.LENGTH_SHORT).show();
        System.out.println("LINK::: " + modelRef.toString());
        ArFragment arFragment = (ArFragment) getSupportFragmentManager()
                .findFragmentById(R.id.arFragment);

        findViewById(R.id.downloadBtn)
                .setOnClickListener(v -> {

                    try {
                        File file = File.createTempFile("model", "glb");

                        modelRef.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                            @RequiresApi(api = Build.VERSION_CODES.N)
                            @Override
                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                                buildModel(file);
                               // Toast.makeText(ARActivity.this, "FILE : " + file.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                });

        arFragment.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {

            AnchorNode anchorNode = new AnchorNode(hitResult.createAnchor());
            anchorNode.setRenderable(renderable);
            arFragment.getArSceneView().getScene().addChild(anchorNode);

        });
    }

    private ModelRenderable renderable;

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void buildModel(File file) {

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
                    Toast.makeText(this, "Model built", Toast.LENGTH_SHORT).show();;
                    renderable = modelRenderable;
                });

    }
}
