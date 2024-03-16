package edu.jsu.mcis.cs408.memopad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.beans.PropertyChangeEvent;

import edu.jsu.mcis.cs408.memopad.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AbstractView {

    public static final String TAG = "MainActivity";
    private ActivityMainBinding binding;
    private MemoPadController controller;
    private DatabaseHandler db;
    private final MemoPadItemClickHandler itemClick = new MemoPadItemClickHandler();
    public MemoPadItemClickHandler getItemClick() { return itemClick; }



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        db = new DatabaseHandler(this, null, null, 1);
        updateRecyclerView();

        MemoPadModel model = new MemoPadModel();
        controller = new MemoPadController(model);

        controller.addView(this);

        model.init(db);

        binding.addButton.setOnClickListener(this);
        binding.deleteButton.setOnClickListener(this);

    }

    @Override
    public void modelPropertyChange(final PropertyChangeEvent evt) {

        String propertyName = evt.getPropertyName();
        String propertyValue = evt.getNewValue().toString();

        Log.i(TAG, "New " + propertyName + " Value from Model: " + propertyValue);

        if ( propertyName.equals(MemoPadController.ELEMENT_MEMO_LIST) ) {

            updateRecyclerView();
            /*
            String oldPropertyValue = binding.outputText1.getText().toString();

            if ( !oldPropertyValue.equals(propertyValue) ) {
                updateRecyclerView();
            }*/

        }

    }

    @Override
    public void onClick(View view) {

        String tag = view.getTag().toString();
        Toast toast = Toast.makeText(binding.getRoot().getContext(), tag, Toast.LENGTH_SHORT);
        toast.show();

        switch (tag) {

            case "addButton": {
                String memo = binding.memoInput.getText().toString();
                controller.addNewMemo(memo);
                break;
            }
            case "deleteButton": {
                controller.deleteMemo();
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + tag);
        }

    }

    private void updateRecyclerView() {

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, db.getAllMemosAsList());
        binding.output.setHasFixedSize(true);
        binding.output.setLayoutManager(new LinearLayoutManager(this));
        binding.output.setAdapter(adapter);

    }

    private class MemoPadItemClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int position = binding.output.getChildLayoutPosition(v);
            RecyclerViewAdapter adapter = (RecyclerViewAdapter)binding.output.getAdapter();
            if (adapter != null) {
                Memo memo = adapter.getItem(position);
                int id = memo.getId();
                Toast.makeText(v.getContext(), String.valueOf(id), Toast.LENGTH_SHORT).show();
                controller.setDeleteId(id);
            }
        }
    }

}