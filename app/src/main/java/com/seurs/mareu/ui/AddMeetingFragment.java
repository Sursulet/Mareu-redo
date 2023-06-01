package com.seurs.mareu.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.seurs.mareu.R;
import com.seurs.mareu.databinding.FragmentAddMeetingBinding;
import com.seurs.mareu.di.DI;
import com.seurs.mareu.model.Meeting;
import com.seurs.mareu.service.MeetingApiService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddMeetingFragment extends Fragment {

    private MeetingApiService mService;
    private FragmentAddMeetingBinding binding;
    private List<Integer> mPlaces;
    Calendar c;
    ArrayList<String> isValidateParticipants = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mService = DI.getMeetingApiService();
        mPlaces = mService.getPlaces();
        c = Calendar.getInstance();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddMeetingBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        buildTopAppBar();
        buildToggleButton();
        buildOnAddDate();
        buildOnAddHour();
        buildOnAddParticipant();
    }

    private boolean validateEmailAddress(Editable s) {
        String email = s.toString();

        if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            //Toast.makeText(requireContext(), "Email Validated Successfully", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(requireContext(), "Invalid Email Address", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void validate() {
        if (binding.topic.getEditText() != null && binding.manager.getEditText() != null) {
            if (binding.topic.getEditText().getText().toString().isEmpty()) {
                binding.topic.setError("Please, enter a topic !");
                return;
            }
            if (binding.manager.getEditText().getText().toString().isEmpty()) {
                binding.manager.setError("Please, enter a manager name");
                return;
            }
            if (isValidateParticipants.isEmpty()) {
                binding.participant.setError("Please, enter at least one participant email");
                return;
            }

            Meeting mNewMeeting = new Meeting(
                    binding.topic.getEditText().getText().toString(),
                    binding.manager.getEditText().getText().toString(),
                    binding.toggleButton.getCheckedButtonId(),
                    binding.onAddDate.getText().toString(),
                    binding.onAddHour.getText().toString(),
                    isValidateParticipants
            );
            mService.onAddMeeting(mNewMeeting);

            Toast.makeText(requireContext(), "Meeting Validated Successfully", Toast.LENGTH_SHORT).show();
            NavHostFragment.findNavController(AddMeetingFragment.this)
                    .navigate(R.id.action_AddMeetingFragment_to_ListMeetingFragment);
        }
    }

    private void buildTopAppBar() {
        //binding.topAppBar.inflateMenu(R.menu.menu_add);
        binding.topAppBar.setNavigationOnClickListener(view -> NavHostFragment.findNavController(AddMeetingFragment.this)
                .navigate(R.id.action_AddMeetingFragment_to_ListMeetingFragment));

        binding.topAppBar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_on_save) {
                validate();
                return true;
            }
            return false;
        });
    }

    private void buildToggleButton() {
        for (int item : mPlaces) {
            MaterialButton button = (MaterialButton) getLayoutInflater()
                    .inflate(R.layout.place_row_item, null, false);
            button.setId(item);
            button.setIconTintResource(item);
            binding.toggleButton.addView(button);
        }
        binding.toggleButton.check(mPlaces.get(0));
    }

    private void buildOnAddDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = dateFormat.format(c.getTime());

        binding.onAddDate.setText(date);
        binding.onAddDate.setOnClickListener(view -> {
            MaterialDatePicker<Long> mMaterialDatePicker = MaterialDatePicker.Builder.datePicker().build();
            mMaterialDatePicker.show(requireActivity().getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
            mMaterialDatePicker.addOnPositiveButtonClickListener(selection -> binding.onAddDate.setText(mMaterialDatePicker.getHeaderText()));
        });
    }

    @SuppressLint("SetTextI18n")
    private void buildOnAddHour() {
        binding.onAddHour.setText(c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE));
        binding.onAddHour.setOnClickListener(view -> {
            MaterialTimePicker mMaterialTimePicker = new MaterialTimePicker.Builder()
                    .setTitleText("SELECT YOUR TIME")
                    .setHour(12)
                    .setMinute(10)
                    .setTimeFormat(TimeFormat.CLOCK_12H)
                    .build();

            mMaterialTimePicker.show(requireActivity().getSupportFragmentManager(), "MATERIAL_TIME_PICKER");
            mMaterialTimePicker.addOnPositiveButtonClickListener(v -> {
                int hourPicked = mMaterialTimePicker.getHour();
                int minutePicked = mMaterialTimePicker.getMinute();

                String formattedTime = "";
                if (hourPicked > 12) {
                    if (minutePicked < 10) {
                        formattedTime = hourPicked - 12 + ":0" + minutePicked + " pm";
                    } else {
                        formattedTime = hourPicked - 12 + ":" + minutePicked + " pm";
                    }
                } else if (hourPicked == 12) {
                    if (minutePicked < 10) {
                        formattedTime = hourPicked + ":0" + minutePicked + " pm";
                    } else {
                        formattedTime = hourPicked + ":" + minutePicked + " pm";
                    }
                } else if (hourPicked == 0) {
                    if (minutePicked < 10) {
                        formattedTime = hourPicked + 12 + ":0" + minutePicked + " am";
                    } else {
                        formattedTime = hourPicked + 12 + ":" + minutePicked + " am";
                    }
                } else {
                    if (minutePicked < 10) {
                        formattedTime = hourPicked + ":0" + minutePicked + " am";
                    } else {
                        formattedTime = hourPicked + ":" + minutePicked + " am";
                    }
                }

                binding.onAddHour.setText(formattedTime);
            });
        });
    }

    private void buildOnAddParticipant() {
        binding.onAddParticipant.setOnClickListener(view -> {
            if (binding.participant.getEditText() != null) {
                if (validateEmailAddress(binding.participant.getEditText().getText())) {
                    Chip chip = (Chip) getLayoutInflater()
                            .inflate(R.layout.chip, null, false);

                    chip.setText(binding.participant.getEditText().getText());
                    chip.setOnCloseIconClickListener(v -> {
                        binding.participants.removeView(v);
                        isValidateParticipants.remove(chip.getText().toString());
                    });

                    binding.participants.addView(chip);
                    isValidateParticipants.add(chip.getText().toString());
                }
            }
        });
    }
}