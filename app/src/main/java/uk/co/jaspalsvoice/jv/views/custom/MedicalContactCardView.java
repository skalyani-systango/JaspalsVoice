package uk.co.jaspalsvoice.jv.views.custom;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import uk.co.jaspalsvoice.jv.JvApplication;
import uk.co.jaspalsvoice.jv.R;
import uk.co.jaspalsvoice.jv.models.Doctor;

/**
 * Created by Ana on 2/21/2016.
 */
public class MedicalContactCardView extends CardView {
    private String title;
    private String text1;
    private String text2;
    private String doctorType;
    private boolean editMode;

    private TextView titleView;

    private TextView label1View;
    private TextView text1View;
    private EditText edit1View;

    private TextView label2View;
    private TextView text2View;
    private EditText edit2View;

    private ViewGroup buttonsView;
    private Button cancelBtn;
    private Button saveBtn;

    private JvApplication application;

    public MedicalContactCardView(Context context) {
        super(context);
        init(context);
    }

    public MedicalContactCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MedicalContactCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(final Context context) {
        application = (JvApplication) context.getApplicationContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View root = inflater.inflate(R.layout.medical_contact_card_view, this);

        titleView = (TextView) root.findViewById(R.id.title);

        label1View = (TextView) root.findViewById(R.id.label1);
        text1View = (TextView) root.findViewById(R.id.text1);
        edit1View = (EditText) root.findViewById(R.id.edit1);

        label2View = (TextView) root.findViewById(R.id.label2);
        text2View = (TextView) root.findViewById(R.id.text2);
        edit2View = (EditText) root.findViewById(R.id.edit2);

        buttonsView = (ViewGroup) root.findViewById(R.id.buttons);
        cancelBtn = (Button) root.findViewById(R.id.cancel);
        saveBtn = (Button) root.findViewById(R.id.save);

        showDefaultText();

        titleView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                editMode = !editMode;

                if (editMode) {
                    showEditMode();
                } else {
                    showNonEditMode();
                }
            }
        });

        cancelBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                editMode = !editMode;

                setEdit(edit1View, text1);
                setEdit(edit2View, text2);
                showNonEditMode();
            }
        });

        saveBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                editMode = !editMode;

                new Save().execute(edit1View.getText().toString(), edit2View.getText().toString());
            }
        });
    }

    public void setTitle(String title) {
        this.title = title;
        titleView.setText(title);
    }

    public String getTitle() {
        return title;
    }

    public void setText1(String text) {
        this.text1 = text;
        text1View.setText(text);
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text) {
        this.text2 = text;
        text2View.setText(text);
    }

    public String getText1() {
        return text1;
    }

    public String getDoctorType() {
        return doctorType;
    }

    public void setDoctorType(String doctorType) {
        this.doctorType = doctorType;
    }

    public void setEdit(EditText editView, String text) {
        editView.setText(text);
    }

    public void setLabel1View(String text) {
        label1View.setText(text);
    }

    public void setLabel2View(String text) {
        label2View.setText(text);
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }

    public boolean isEditMode() {
        return editMode;
    }

    private void showNonEditMode() {
        edit1View.setVisibility(GONE);
        edit2View.setVisibility(GONE);
        buttonsView.setVisibility(GONE);
        text1View.setVisibility(VISIBLE);
        text2View.setVisibility(VISIBLE);
        showDefaultText();
        titleView.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_action_edit), null);
    }

    private void showEditMode() {
        titleView.setCompoundDrawables(null, null, null, null);
        text1View.setVisibility(GONE);
        text2View.setVisibility(GONE);
        edit1View.setText(text1View.getText());
        edit1View.setVisibility(VISIBLE);
        edit2View.setText(text2View.getText());
        edit2View.setVisibility(VISIBLE);
        buttonsView.setVisibility(VISIBLE);
    }

    private void showDefaultText() {
        if (TextUtils.isEmpty(text1)) {
            text1View.setText(R.string.default_text_when_not_specified);
        }
        if (TextUtils.isEmpty(text2)) {
            text2View.setText(R.string.default_text_when_not_specified);
        }
    }

    private class Save extends AsyncTask<String, Void, Doctor> {
        @Override
        protected Doctor doInBackground(String... params) {
            List<Doctor> doctors = new ArrayList<>();
            Doctor doctor = new Doctor();
            doctor.setName(params[0]);
            doctor.setContact(params[1]);
            doctor.setType(doctorType);
            doctors.add(doctor);
            application.getDbHelper().insertOrReplaceDoctor(doctors);
            return doctor;
        }

        @Override
        protected void onPostExecute(Doctor doctor) {
            setText1(doctor.getName());
            setText2(doctor.getContact());
            showNonEditMode();
        }
    }
}
