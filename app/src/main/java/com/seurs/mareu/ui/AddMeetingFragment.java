package com.seurs.mareu.ui;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.chip.Chip;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
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
import java.util.Objects;

public class AddMeetingFragment extends Fragment {

    private MeetingApiService mService;
    private FragmentAddMeetingBinding binding;
    private List<Integer> mPlaces;
    private PlaceAdapter mAdapter;
    Calendar c;
    String isValidateTopic = "";
    String isValidatePlace = "";
    ArrayList<String> isValidateParticipants = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mService = DI.getMeetingApiService();
        mPlaces = mService.getPlaces();
        mAdapter = new PlaceAdapter(mPlaces);
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
        buildPlaceList();
    }

    private boolean validateEmailAddress(Editable s) {
        String email = s.toString();

        if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(requireContext(), "Email Validated Successfully", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(requireContext(), "Invalid Email Address", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean validate() {
        return !isValidateTopic.isEmpty() && !isValidatePlace.isEmpty() && !isValidateParticipants.isEmpty();
    }

    private void buildTopAppBar() {
        //binding.topAppBar.inflateMenu(R.menu.menu_add);
        binding.topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(AddMeetingFragment.this)
                        .navigate(R.id.action_AddMeetingFragment_to_ListMeetingFragment);
            }
        });
        binding.topAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_on_save) {
                    if (validate()) {
                        Meeting mNewMeeting = new Meeting(
                                isValidateTopic,
                                isValidatePlace,
                                Color.CYAN,
                                binding.onAddDate.getText().toString(),
                                binding.onAddHour.getText().toString(),
                                isValidateParticipants
                        );
                        mService.onAddMeeting(mNewMeeting);

                        NavHostFragment.findNavController(AddMeetingFragment.this)
                                .navigate(R.id.action_AddMeetingFragment_to_ListMeetingFragment);
                    }
                    return true;
                }
                return false;
            }
        });
    }

    private void buildPlaceList() {
        binding.colorsList.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.colorsList.setAdapter(mAdapter);
        mAdapter.setOnClickListener(new PlaceAdapter.OnClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });
    }

    private void buildTopic() {
        binding.topic.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //isValidateTopic = editable.length() > 0;
                isValidateTopic = editable.length() > 0 ? editable.toString() : "";
            }
        });
    }

    private void buildPlace() {
        binding.place.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //isValidatePlace = editable.length() > 0;
                isValidatePlace = editable.length() > 0 ? editable.toString() : "";
            }
        });
    }

    private void buildOnAddDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = dateFormat.format(c.getTime());

        binding.onAddDate.setText(date);
        binding.onAddDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialDatePicker<Long> mMaterialDatePicker = MaterialDatePicker.Builder.datePicker().build();
                mMaterialDatePicker.show(requireActivity().getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
                mMaterialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
                    @Override
                    public void onPositiveButtonClick(Object selection) {
                        binding.onAddDate.setText(mMaterialDatePicker.getHeaderText());
                    }
                });
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void buildOnAddHour() {
        binding.onAddHour.setText(c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE));
        binding.onAddHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialTimePicker mMaterialTimePicker = new MaterialTimePicker.Builder()
                        .setTitleText("SELECT YOUR TIME")
                        .setHour(12)
                        .setMinute(10)
                        .setTimeFormat(TimeFormat.CLOCK_12H)
                        .build();

                mMaterialTimePicker.show(requireActivity().getSupportFragmentManager(), "MATERIAL_TIME_PICKER");
                mMaterialTimePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
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
                    }
                });
            }
        });
    }

    private void buildOnAddParticipant() {
        binding.onAddParticipant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateEmailAddress(Objects.requireNonNull(binding.participant.getText()))) {
                    Chip chip = (Chip) getLayoutInflater().inflate(R.layout.chip, null, false);
                    chip.setText(binding.participant.getText());
                    chip.setOnCloseIconClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            binding.participants.removeView(view);
                            isValidateParticipants.remove(view.toString());
                        }
                    });

                    binding.participants.addView(chip);
                    isValidateParticipants.add(chip.getText().toString());
                }
            }
        });

        binding.participant.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                /*
                 * This method is called to notify you that, within s,
                 * the count characters beginning at start are about to be replaced by new text with length after.
                 */
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                /*
                 * This method is called to notify you that, within s,
                 * the count characters beginning at start have just replaced old text that had length before.
                 */
            }

            @Override
            public void afterTextChanged(Editable editable) {
                /*
                 * This method is called to notify you that, somewhere within s,
                 * the text has been changed.
                 */

                //binding.onAddParticipant.setEnabled(editable.length() > 0);
            }
        });
    }
}