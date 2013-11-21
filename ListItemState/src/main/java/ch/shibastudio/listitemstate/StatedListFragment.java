package ch.shibastudio.listitemstate;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by shibakaneki on 16.11.13.
 */
public class StatedListFragment extends ListFragment implements Observer{

    private final int NB_ITEMS = 200;
    private StatedListAdapter mAdapter;

    private class FlipTimerTask extends TimerTask {

        private ObserverFlipper mFlippable;

        public FlipTimerTask(ObserverFlipper flippable){
            mFlippable = flippable;
        }

        @Override
        public void run() {
            if(null != mFlippable){
                if(null != getActivity()){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // run on ui thread by calling the activity runOnUiThread method
                            mFlippable.flip(ModeController.getInstance().mode());
                        }
                    });
                }
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_statedlist, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstance){
        super.onActivityCreated(savedInstance);
        mAdapter = new StatedListAdapter(getActivity());
        populateList();
        getListView().setAdapter(mAdapter);
        setHasOptionsMenu(true);
        ModeController.getInstance().addObserver(this);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu){
        if(ModeController.getInstance().mode() == ModeController.eMode.eMode_NORMAL){
            menu.getItem(0).setIcon(R.drawable.ic_action_edit);
        }else if(ModeController.getInstance().mode() == ModeController.eMode.eMode_EDIT){
            menu.getItem(0).setIcon(R.drawable.ic_action_accept);
        }
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                toggleEditMode();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void populateList(){

        for(int i=0; i<NB_ITEMS; i++){
            mAdapter.addItem(new StatedListItem("Item " +i, false));
        }
    }

    private void toggleEditMode(){
        if(ModeController.getInstance().mode() == ModeController.eMode.eMode_NORMAL){
            ModeController.getInstance().setMode(ModeController.eMode.eMode_EDIT);

        }else if(ModeController.getInstance().mode() == ModeController.eMode.eMode_EDIT){
            ModeController.getInstance().setMode(ModeController.eMode.eMode_NORMAL);
        }
        getActivity().invalidateOptionsMenu();
    }

    public void flipItems() {
        // Get all the visible items and flip them
        if(null != mAdapter){
            int first = getListView().getFirstVisiblePosition();
            int last = getListView().getLastVisiblePosition();

            int time = 100;
            if(first < last){
                for(int i=first; i<=last; i++){
                    View v =  getListView().getChildAt(i-first);
                    ObserverFlipper vf = (ObserverFlipper)v.findViewById(R.id.flipper);
                    Timer flipTimer = new Timer("Flip Timer");
                    flipTimer.schedule(new FlipTimerTask(vf), time);

                    time += 100;
                }
            }
        }
    }

    @Override
    public void update(Observable observable, Object data) {
        if(ModeController.getInstance() == observable){
            flipItems();
        }
    }
}
