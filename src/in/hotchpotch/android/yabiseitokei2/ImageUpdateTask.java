package in.hotchpotch.android.yabiseitokei2;

import java.util.TimerTask;

import android.os.Handler;
import android.os.Message;

public class ImageUpdateTask extends TimerTask {
    private String mLastTime;
    private Handler updateHandler;

    public ImageUpdateTask(Handler handler) {
        super();
        updateHandler = handler;
    }

    @Override
    public void run() {
        String currentTime = Utils.getNowHHMM();
        if (!currentTime.equals(mLastTime)) {
            mLastTime = currentTime;
            Message msg = new Message();
            msg.obj = mLastTime;
            updateHandler.sendMessage(msg);
        }
    }
}


