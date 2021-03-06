
package in.hotchpotch.android.yabiseitokei2;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

import android.content.Context;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;

import android.util.AttributeSet;
import android.util.Log;

import android.widget.ImageView;

public class ImageLoader extends ImageView {
    private static final String TAG = "YABiseiTokei";
    private Bitmap mImage;
    private ExecutorService mExecutor = Executors.newSingleThreadExecutor();
    
    public ImageLoader(Context context) {
        super(context);
        setBackgroundColor(Color.BLACK);
    }

    public ImageLoader(Context context, AttributeSet attr) {
        super(context, attr);
        setBackgroundColor(Color.BLACK);
    }

    public void updateImage(String time) {
        mImage = null;
        final String path = getPhotoPath(time);

       // mExecutor.execute(new Runnable() { public void run() {
       //     if (Thread.interrupted()) {
       //         return;
       //     }
       
        // UI スレッドがこれだとブロックされるけど、
        // post が初回起動時にうまく実行されないのでしかたなく…
            InputStream fis = null;
            try {
                fis = new BufferedInputStream(new FileInputStream(path));
                mImage = BitmapFactory.decodeStream(fis);
                post(new Runnable() { 
                    public void run() {
                        if (mImage != null) {
                            setImageBitmap(mImage);

                        }
                    }
                });
            } catch (IOException e) {
                Log.e(TAG, String.format("error file, %s", e.getMessage()), e);
            } finally {
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        Log.e(TAG, String.format("error file, %s", e.getMessage()), e);
                    }
                }
            }
        //}
       // });
    }

    private String getPhotoPath(String time) {
        return String.format("%sPhotos/%s.jpg", YABiseiTokei.BISEI_APP_DIR, time);
    }
}


