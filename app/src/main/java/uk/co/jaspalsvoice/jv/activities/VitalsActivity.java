package uk.co.jaspalsvoice.jv.activities;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import uk.co.jaspalsvoice.jv.JvApplication;
import uk.co.jaspalsvoice.jv.R;
import uk.co.jaspalsvoice.jv.models.VitalsBloodPressure;
import uk.co.jaspalsvoice.jv.views.VitalsBloodPressureCardView;

public class VitalsActivity extends BaseActivity {

    private VitalsBloodPressureCardView bloodPressureCardView;
    private int bloodPressureId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vitals);
        loadBloodPressureView();
    }

    private void loadBloodPressureView() {
        bloodPressureCardView = (VitalsBloodPressureCardView)
                findViewById(R.id.bloodPressureCardView);
        new BloodPressure().execute();

    }

    public int getBloodPressureId() {
        return bloodPressureId;
    }

    public void setBloodPressureId(int bloodPressureId) {
        this.bloodPressureId = bloodPressureId;
    }

    private class BloodPressure extends AsyncTask<Void, Void, List<VitalsBloodPressure>> {
        @Override
        protected List<VitalsBloodPressure> doInBackground(Void... params) {
            return ((JvApplication) getApplication()).getDbHelper().readAllBloodPressures();
        }

        @Override
        protected void onPostExecute(List<VitalsBloodPressure> bloodPressures) {
            bloodPressureCardView.displayRecords(new ArrayList<VitalsBloodPressure>(bloodPressures));
        }
    }

}
