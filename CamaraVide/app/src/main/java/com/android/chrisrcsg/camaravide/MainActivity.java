package com.android.chrisrcsg.camaravide;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    int SOLICITUD_CAMARA = 123;
    int SOLICITUD_VIDEO = 456;
    ImageView imgView;
    VideoView videoView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgView = (ImageView)findViewById(R.id.imagen);
        videoView = (VideoView)findViewById(R.id.video);
    }

    public void tomarFoto(View v)
    {
        //Acceder a apps ya definidas
        Intent intentCamara = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intentCamara,SOLICITUD_CAMARA);
    }

    public void elegirVideo(View v){
        Intent intentVideo = new Intent();

        //tipos de recursos ya definidos
        intentVideo.setType("video/*");
        intentVideo.setAction(Intent.ACTION_GET_CONTENT);

        //Desplegar el mensaje para seleecionar el video
        startActivityForResult(Intent.createChooser(intentVideo,"Selecciona un video"),SOLICITUD_VIDEO);
    }


    public void onActivityResult(int solicitud, int resultCode, Intent datos)
    {
        if (solicitud == SOLICITUD_CAMARA && resultCode == RESULT_OK) {
            Bitmap foto = (Bitmap)datos.getExtras().get("data");
            //creando objeto de la clase Bitmap y obtener los bytes que ha recibido
            videoView.setVisibility(View.GONE);
            imgView.setVisibility(View.VISIBLE);
            imgView.setImageBitmap(foto);
        }

        else if(solicitud == SOLICITUD_VIDEO && resultCode == RESULT_OK){
            Uri videoUri = datos.getData();
            videoView.setVideoURI(videoUri);

            MediaController controlVideo = new MediaController(this);
            controlVideo.setAnchorView(videoView);
            videoView.setMediaController(controlVideo);
            videoView.setVisibility(View.VISIBLE);
            imgView.setVisibility(View.GONE);
            videoView.start();
        }
        else
            {
                Toast.makeText(this, "No se pudo completar la accion",Toast.LENGTH_SHORT).show();
            }
    }
}
