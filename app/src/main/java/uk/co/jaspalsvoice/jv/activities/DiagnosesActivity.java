package uk.co.jaspalsvoice.jv.activities;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import uk.co.jaspalsvoice.jv.JvApplication;
import uk.co.jaspalsvoice.jv.R;
import uk.co.jaspalsvoice.jv.models.Diagnosis;
import uk.co.jaspalsvoice.jv.models.FoodAllergies;
import uk.co.jaspalsvoice.jv.views.DiagnosisCardView;

public class DiagnosesActivity extends AppCompatActivity {

    private DiagnosisCardView diagnosisCardView;
    private int diagnosisId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnoses);
        loadDiagnosisView();
    }

    private void loadDiagnosisView() {
        diagnosisCardView = (DiagnosisCardView) findViewById(R.id.diagnosisCardView);
        new DiagnosisRetrieve().execute();
    }

    public int getDiagnosisId() {
        return diagnosisId;
    }

    public void setDiagnosisId(int diagnosisId) {
        this.diagnosisId = diagnosisId;
    }

    private class DiagnosisRetrieve extends AsyncTask<Void, Void, List<Diagnosis>> {
        @Override
        protected List<Diagnosis> doInBackground(Void... params) {
            return ((JvApplication) getApplication()).getDbHelper().readAllDiagnosis();
        }

        @Override
        protected void onPostExecute(List<Diagnosis> diagnoses) {
            diagnosisCardView.displayRecords(new ArrayList<Diagnosis>(diagnoses));
        }
    }

}
