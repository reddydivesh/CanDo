package com.android.jayanth.cando.Fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.jayanth.cando.Database.CandoDbHelper;
import com.android.jayanth.cando.R;
import com.android.jayanth.cando.model.CanDoDataModel;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static com.android.jayanth.cando.Constants.Constants.ARG_DATA;
import static com.android.jayanth.cando.Constants.Constants.CANDO_STATUS_ARCHIVE;
import static com.android.jayanth.cando.Constants.Constants.CANDO_STATUS_DONE;
import static com.android.jayanth.cando.Constants.Constants.CANDO_STATUS_TO_DO;
import static com.android.jayanth.cando.Constants.Constants.PRIORITY_HIGH;
import static com.android.jayanth.cando.Constants.Constants.PRIORITY_LOW;
import static com.android.jayanth.cando.Constants.Constants.PRIORITY_MID;

/**
 * Created by Jayanth Devulapally on 8/3/17.
 * Detail fragment wher the user can input or see the detail data about the can do.
 */

public class CanDoDetailsFragment extends Fragment {

    //interface for Action item listener.
    public interface OnActionItemClickListener {
        void onActionItemClick();
    }

    //edit text for the title
    TextInputEditText mTitleTextInputEditText;
    //input layout for the title
    TextInputLayout mDueDateTextInputLayout;

    //edit text for the notes
    TextInputEditText mNotesTextInputEditText;
    //input layout for the notes
    TextInputEditText mDueDateTextInputEditText;

    //Radio button and group for priority =
    RadioGroup mPriorityRadioGroup;
    RadioButton mPriorityRadioButton;

    //DBhelper
    CandoDbHelper mCandoHelper;

    //Action item listener
    OnActionItemClickListener onActionItemClickListener;

    //Data Model
    CanDoDataModel canDoDataModel;

    public static CanDoDetailsFragment newInstance(CanDoDataModel canDoDataModel) {
        //getting the data moel as serializable object
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATA, (Serializable) canDoDataModel);
        CanDoDetailsFragment fragment = new CanDoDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        //getting the serializable data
        if (getArguments() != null) {
            canDoDataModel = (CanDoDataModel) getArguments().getSerializable(ARG_DATA);
        }

        //setting the layout for the fragment
        View rootView = inflater.inflate(R.layout.add_task_dialog_fragment,container,false);

        //setting the title for the toolbar
        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.add_can_do));
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        //setting up the action bar
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
        }
        //showing the menu options
        setHasOptionsMenu(true);

        //setting the exit texts
        mTitleTextInputEditText = (TextInputEditText) rootView.findViewById(R.id.et_can_do_title);
        mNotesTextInputEditText = (TextInputEditText) rootView.findViewById(R.id.et_can_do_notes);

        //setting the radio group & button
        mPriorityRadioGroup = (RadioGroup) rootView.findViewById(R.id.rg_priority);
        mPriorityRadioButton = (RadioButton) mPriorityRadioGroup.findViewById(
                mPriorityRadioGroup.getCheckedRadioButtonId());
        //handling the radio button change
        mPriorityRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                mPriorityRadioButton = (RadioButton) group.findViewById(checkedId);
            }
        });

        mDueDateTextInputEditText = (TextInputEditText) rootView.findViewById(R.id.et_can_do_due_date);
        mDueDateTextInputLayout = (TextInputLayout) rootView.findViewById(R.id.til_can_do_due_Date);
        //configuring the date edit text to show calender dialog
        configureDueDateEditText();


        mCandoHelper = new CandoDbHelper(getActivity());

        if (canDoDataModel != null){
            //helper method to bind the data from model to the views
            showDialogWithDetails();
        }

        return rootView;
    }

    public void setOnActionItemClickListener(OnActionItemClickListener onActionItemClickListener){
        this.onActionItemClickListener = onActionItemClickListener;
    }

/*    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }*/

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        if (canDoDataModel != null){
            switch (canDoDataModel.getStatus()){
                case CANDO_STATUS_TO_DO:
                    getActivity().getMenuInflater().inflate(R.menu.menu_todo_options,menu);
                    break;

                case CANDO_STATUS_DONE:
                    getActivity().getMenuInflater().inflate(R.menu.menu_done_options,menu);
                    break;

                case CANDO_STATUS_ARCHIVE:
                    getActivity().getMenuInflater().inflate(R.menu.menu_archive_options,menu);
                    break;
            }
        } else {
            getActivity().getMenuInflater().inflate(R.menu.menu_add_options,menu);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            //handling the save action
            case R.id.action_save:
                saveCanDo();
                updateList();
                return true;

            //handling the update action
            case R.id.action_update:
                updateCanDo();
                updateList();
                return true;

            //handling the mark as done
            case R.id.action_done:
                changeCanDoStatus(canDoDataModel.getId(),CANDO_STATUS_DONE);
                updateList();
                getFragmentManager().popBackStack();
                return true;

            //handling the mark as archived
            case R.id.action_archive:
                changeCanDoStatus(canDoDataModel.getId(),CANDO_STATUS_ARCHIVE);
                updateList();
                getFragmentManager().popBackStack();
                return true;

            //handling the delete
            case R.id.action_delete:
                deleteCanDO(canDoDataModel.getId());
                updateList();
                getFragmentManager().popBackStack();
                return true;

            //canceling the fragment.
            case android.R.id.home:
                getFragmentManager().popBackStack();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //helper method to setup calender dialog for due date edit text
    private void configureDueDateEditText() {

        //setting up the calender
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        //configuring the date picker dialog
        final DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //setting the time to calender
                calendar.set(year,month,dayOfMonth);
                //date format for user to under stand easily
                SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, Y", Locale.getDefault());
                String date = dateFormat.format(calendar.getTime());
                //setting the date to edit text and clearing the focus
                mDueDateTextInputEditText.setText(date);
                mDueDateTextInputEditText.clearFocus();

            }
        },year,month,day);

        mDueDateTextInputEditText.setInputType(InputType.TYPE_NULL);

        //sowing the dialog if the edit text has the focous
        mDueDateTextInputEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) gidatePickerDialog.show();
            }
        });
    }

    //helper method for saving the data
    private void saveCanDo() {
        //getting the values from iput feilds
        String title = mTitleTextInputEditText.getText().toString();
        String notes = mNotesTextInputEditText.getText().toString();
        String dueDate = mDueDateTextInputEditText.getText().toString();
        int priority = getPriority();

        //displaying the message in toast based on the result.
         if (!title.equals("") && !notes.equals("") && !dueDate.equals("") &&  mCandoHelper.insertIntoDatabase(title,notes,dueDate,priority)){
             Toast.makeText(getContext(),R.string.can_do_added,Toast.LENGTH_SHORT).show();
             getFragmentManager().popBackStack();
         } else {
             Toast.makeText(getContext(),R.string.unable_to_add,Toast.LENGTH_SHORT).show();
         }

    }

    //helper method to change the status of the can do
    private void changeCanDoStatus(int canDoId, int changeTo){
        //displaying the message in toast based on the result.
        if (mCandoHelper.updateCanDoStatus(canDoId,changeTo)){
            Toast.makeText(getContext(),getStatusString(changeTo),Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(),R.string.unable_to_mark,Toast.LENGTH_SHORT).show();
        }
    }

    //helper method to update if any changes in the can do
    private void updateCanDo(){
        String updatedTitle = null, updatedNotes = null, updatedDate = null;
        int updatedPriority = -1;

        //getting any changed values
        if (!canDoDataModel.getTitle().equals(mTitleTextInputEditText.getText().toString())) updatedTitle = mTitleTextInputEditText.getText().toString();
        if (!canDoDataModel.getNotes().equals(mNotesTextInputEditText.getText().toString())) updatedNotes = mNotesTextInputEditText.getText().toString();
        if (!canDoDataModel.getDate().equals(mDueDateTextInputEditText.getText().toString())) updatedDate = mDueDateTextInputEditText.getText().toString();
        if (canDoDataModel.getPriority() != getPriority() ) updatedPriority = getPriority();

        ////displaying the message in toast based on the result.
        int result = mCandoHelper.updateCanDo(canDoDataModel.getId(),updatedTitle,updatedNotes,updatedDate,updatedPriority);

        if (result > 0){
            Toast.makeText(getContext(),R.string.updated,Toast.LENGTH_SHORT).show();
            getFragmentManager().popBackStack();
        } else if (result == -1) {
            Toast.makeText(getContext(),R.string.unable_to_update,Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(),R.string.nothing_to_delete,Toast.LENGTH_SHORT).show();
        }
    }

    //helper method to delete the can do from the table
    private void deleteCanDO(int canDoId){
        //displaying the message in toast based on the result.
        if (mCandoHelper.deleteCanDo(canDoId)){
            Toast.makeText(getContext(),R.string.deleted,Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(),R.string.unable_to_delete,Toast.LENGTH_SHORT).show();
        }
    }

    /*helper method to get the priority value based on teh radio button ID
    * HIGH - 2
    * MEDIUM - 1
    * LOW - 0
    * */
    private int getPriority() {
        switch (mPriorityRadioButton.getId()){
            case R.id.rb_priority_high:
                return PRIORITY_HIGH;

            case R.id.rb_priority_medium:
                return PRIORITY_MID;

            case R.id.rb_priority_low:
                return PRIORITY_LOW;

            default:
                return PRIORITY_HIGH;
        }
    }

    /*getting the status string based on int value
    * 0 -  To Do
    * 1 - Done
    * 2 - Archived
    * other - Changed */
    private String getStatusString(int statusId){
        switch(statusId){
            case CANDO_STATUS_TO_DO:
                return getString(R.string.to_do);

            case CANDO_STATUS_DONE:
                return getString(R.string.done);

            case CANDO_STATUS_ARCHIVE:
                return getString(R.string.archived);

            default:
                return getString(R.string.changed);
        }
    }

    //calling the listener
    private void updateList(){
        if (onActionItemClickListener !=null) onActionItemClickListener.onActionItemClick();
    }

    //helper method to setup the input view with values and input views are disabled when status is other than 'To Do'
    private void showDialogWithDetails() {
        //setting the input view with avilable values
        setDialogWithDetails();
        //disabling the input views if status is other than To Do
        if (canDoDataModel.getStatus() != 0) disableInputFields();
    }

    //helper method to set data to input views
    private void setDialogWithDetails() {
        mTitleTextInputEditText.setText(canDoDataModel.getTitle());
        mNotesTextInputEditText.setText(canDoDataModel.getNotes());
        mDueDateTextInputEditText.setText(canDoDataModel.getDate());
        mPriorityRadioGroup.check(getRadioButtonId(canDoDataModel.getPriority()));
    }

    //helper method to disable input views
    private void disableInputFields() {
        mTitleTextInputEditText.setEnabled(false);
        mNotesTextInputEditText.setEnabled(false);
        mDueDateTextInputEditText.setEnabled(false);
        disableRadioGroup();
    }

    //helper method to disable the radio group
    private void disableRadioGroup() {
        for (int i =0; i< mPriorityRadioGroup.getChildCount(); i++){
            mPriorityRadioGroup.getChildAt(i).setEnabled(false);
        }
    }


    //get the radio button id based on the priority in the model
    private int getRadioButtonId(int priority_id){
        switch (priority_id){
            case PRIORITY_HIGH:
                return R.id.rb_priority_high;

            case PRIORITY_MID:
                return R.id.rb_priority_medium;

            case PRIORITY_LOW:
                return R.id.rb_priority_low;

            default:
                return R.id.rb_priority_high;
        }
    }
}
