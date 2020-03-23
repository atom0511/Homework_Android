package trungatom.tqt.firstapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.os.Environment;
import android.os.FileUriExposedException;
import android.util.Log;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class ImageUtils {

    private static String folderName = "DrawingNotes";

    public static void saveImage(Bitmap bitmap, Context context) {

        //creat new folder to save image
        String root = Environment.getExternalStorageDirectory().toString();
        Log.d("atom", "saveImage " + root);
        File folder = new File(root, folderName);
        folder.mkdir();
        //create empty file (.png)
        String imageName = Calendar.getInstance().getTimeInMillis() + ".png";
        File imageFile = new File(folder, imageName);
        //save image

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.close();

            Toast.makeText(context, "Saved!", Toast.LENGTH_SHORT).show();

            MediaScannerConnection.scanFile(context,
                    new String[]{imageFile.getAbsolutePath()},
                    null, null
            );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static List<File> getListImage(){
        List<File> images = new ArrayList<>();
        File folder = new File(Environment.getExternalStorageDirectory().toString(), folderName);
        if(folder.listFiles() != null){
            images = Arrays.asList(folder.listFiles());
        }
        return images;
    }


}
