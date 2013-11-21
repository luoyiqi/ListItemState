package ch.shibastudio.listitemstate;

import java.util.Observable;

/**
 * Created by shibakaneki on 18.11.13.
 */
public class ModeController extends Observable {
    public enum eMode{
        eMode_NORMAL,
        eMode_EDIT;
    }

    private static ModeController mInstance;
    private eMode mMode;

    private ModeController(){
        mMode = eMode.eMode_NORMAL;
    }

    public static ModeController getInstance(){
        if(null == mInstance){
            mInstance = new ModeController();
        }
        return mInstance;
    }

    public void setMode(eMode mode){
        mMode = mode;

        // Notify Observers
        setChanged();
        notifyObservers();
        clearChanged();
    }

    public eMode mode(){
        return mMode;
    }
}
