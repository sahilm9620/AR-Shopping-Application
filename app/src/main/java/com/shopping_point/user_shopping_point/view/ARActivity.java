package com.shopping_point.user_shopping_point.view;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.ar.core.Anchor;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.assets.RenderableSource;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.shopping_point.user_shopping_point.R;
import com.shopping_point.user_shopping_point.model.Product;
import java.io.File;
import java.io.IOException;
import static com.shopping_point.user_shopping_point.utils.Constant.PRODUCT;


public class ARActivity extends AppCompatActivity {
    private ArFragment arFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_r);
    Product product = getIntent().getParcelableExtra(PRODUCT);

        FirebaseApp.initializeApp(this);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference modelRef = storage.getReference().child(product.getModelName());
        Toast.makeText(this, "Model Name : " + product.getModelName(), Toast.LENGTH_SHORT).show();

        arFragment = (ArFragment) getSupportFragmentManager()
                .findFragmentById(R.id.arFragment);

        ///Toast.makeText(this, "Model Name : " + modelName, Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(this, "Model built", Toast.LENGTH_SHORT).show();

                    renderable = modelRenderable;
                });

    }

}
