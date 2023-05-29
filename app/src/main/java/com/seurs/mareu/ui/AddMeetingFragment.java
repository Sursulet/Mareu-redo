package com.seurs.mareu.ui;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.chip.Chip;
import com.seurs.mareu.R;
import com.seurs.mareu.databinding.FragmentAddMeetingBinding;
import com.seurs.mareu.di.DI;
import com.seurs.mareu.model.Meeting;
import com.seurs.mareu.service.MeetingApiService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class AddMeetingFragment extends Fragment {

    private MeetingApiService mService;
    private FragmentAddMeetingBinding binding;
    Calendar c;

    int lastLength = 0, chipLength = 4;
    String isValidateTopic = "";
    String isValidatePlace = "";
    ArrayList<String> isValidateParticipants = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mService = DI.getMeetingApiService();
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
        binding.topAppBar.inflateMenu(R.menu.menu_add);
        binding.topAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_on_save) {
                    if (validate()) {
                        Meeting mNewMeeting = new Meeting(
                                binding.onAddHour.getText().toString(),
                                isValidatePlace,
                                isValidateTopic,
                                isValidateParticipants
                        );
                        mService.onAddMeeting(mNewMeeting);
                    }
                    return true;
                }
                return false;
            }
        });

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

        binding.onAddHour.setText(c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE));
        binding.onAddHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(requireActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        binding.onAddHour.setText(hourOfDay + ":" + minute);
                    }
                }, hour, minute, false);
                timePickerDialog.show();
            }
        });

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

                if (s.length() == lastLength - chipLength) {
                    lastLength = s.length();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                /*
                 * This method is called to notify you that, somewhere within s,
                 * the text has been changed.
                 */

                binding.onAddParticipant.setEnabled(editable.length() > 0);

                //Boolean test = Patterns.EMAIL_ADDRESS.matcher(s).matches();
                //Log.d("PEACH", "afterTextChanged: "+ s.length() + "//" + test);
                //Log.d("PEACH", "afterTextChanged: " + validateEmailAddress(s));
                //if (s.charAt(s.length() - 1) == 32) {
                    /*if (validateEmailAddress(s)) {
                        Log.d("PEACH", "afterTextChanged: IT'S A MAIL");
                        binding.onAddParticipant.setError(null);

                        Chip chip = (Chip) getLayoutInflater().inflate(R.layout.chip,null,false);
                        chip.setText(s);
                        binding.participants.addView(chip);
                        //binding.onAddParticipant.getEditText().getText().clear();
                        binding.onAddParticipant.setError("");
                    } else {
                        binding.onAddParticipant.setError("Invalid Email !");
                    }*/
                    /*
                    ChipDrawable chip = ChipDrawable.createFromResource(requireContext(), R.xml.standalone_chip);
                    chip.setText(s.subSequence(lastLength, s.length()));
                    chip.setBounds(0, 0, chip.getIntrinsicWidth(), chip.getIntrinsicHeight());
                    s.setSpan(new ImageSpan(chip), lastLength, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    lastLength = s.length();

                     */


                //s.clear();
                //}
                // Btn Save setEnabled(editable.length() > 0);
            }
        });

        /*binding.onAddParticipant.getEditText().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_UP) {
                    Editable name = binding.onAddParticipant.getEditText().getText();
                    if (!validateEmailAddress(name)) {
                        binding.onAddParticipant.getEditText().setText("OK f");
                        binding.onAddParticipant.getEditText().setError("gdsg");
                    }
                    return true;
                }
                return false;
            }
        });*/
    }

    private boolean validateEmailAddress(Editable s) {
        String email = s.toString();//.substring(0, s.length() - 1);
        //String email = text.substring(text.length()-1);
        Log.d("PEACH", "validateEmailAddress: " + email);

        if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(requireContext(), "Email Validated Successfully", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Log.d("PEACH", "validateEmailAddress: EMAIL " + email);
            Toast.makeText(requireContext(), "Invalid Email Address", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean validate() {
        return !isValidateTopic.isEmpty() && !isValidatePlace.isEmpty() && !isValidateParticipants.isEmpty();
    }
}