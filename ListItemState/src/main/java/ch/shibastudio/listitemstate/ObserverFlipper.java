package ch.shibastudio.listitemstate;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ViewFlipper;

/**
 * Created by shibakaneki on 18.11.13.
 */
public class ObserverFlipper extends ViewFlipper{

    private ModeController.eMode mMode = ModeController.eMode.eMode_NORMAL;

    public ObserverFlipper(Context context) {
        super(context);
    }

    public ObserverFlipper(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void flip(ModeController.eMode mode) {
        if(mMode != mode){
            if(mode == ModeController.eMode.eMode_EDIT){
                setInAnimation(getContext(), R.anim.card_flip_left_in);
                setOutAnimation(getContext(), R.anim.card_flip_left_out);
                showNext();
            }else{
                setInAnimation(getContext(), R.anim.card_flip_left_in);
                setInAnimation(getContext(), R.anim.card_flip_left_in);
                showPrevious();
            }
            mMode = mode;
        }
    }
}
