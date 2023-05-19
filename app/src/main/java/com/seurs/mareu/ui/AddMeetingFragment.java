package com.seurs.mareu.ui;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
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

import com.google.android.material.chip.ChipDrawable;
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
                    Meeting mNewMeeting = new Meeting(
                            "",
                            binding.place.getEditText().getText().toString(),
                            binding.topic.getEditText().getText().toString(),
                            new ArrayList<String>()
                    );
                    mService.onAddMeeting(mNewMeeting);
                    return true;
                }
                return false;
            }
        });

        binding.topic.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
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

        binding.onAddParticipant.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.onAddParticipant.getEditText() != null) {
                    binding.onAddParticipant.getEditText().getText().clear();
                }
            }
        });
        ChipDrawable chipDrawable = ChipDrawable.createFromResource(requireContext(), R.xml.standalone_chip);
        chipDrawable.setBounds(0, 0, chipDrawable.getIntrinsicWidth(), chipDrawable.getIntrinsicHeight());
        ImageSpan span = new ImageSpan(chipDrawable);
        Editable text = Objects.requireNonNull(binding.onAddParticipant.getEditText()).getText();
        text.setSpan(span, 0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


        binding.onAddParticipant.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                /*
                 * This method is called to notify you that, within s,
                 * the count characters beginning at start are about to be replaced by new text with length after.
                 */
                Log.d("PEACH", "beforeTextChanged: " + s + "," + start + "," + count + "," + after);

                s = "Bottom: span.\nBaseline: span";
                //lastlenght = charSequence.length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                /*
                 * This method is called to notify you that, within s,
                 * the count characters beginning at start have just replaced old text that had length before.
                 */
                Log.d("PEACH", "onTextChanged: " + s + "," + start + "," + before + "," + count);

                /*if (s.charAt(s.length() - 1) == 32) {
                    Log.d("PEACH", "onTextChanged: onTextChanged" + s);
                    String email = s.toString();
                    Chip chip = new Chip(requireContext());
                    binding.onAddParticipant.getEditText().setError("gdsg");
                }*/
                /*try {
                    if (lastlenght > s.length()) return;

                } catch (IndexOutOfBoundsException ex) {
                    //
                }*/

                if (s.length() == lastLength - chipLength) {
                    lastLength = s.length();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                /*
                 * This method is called to notify you that, somewhere within s,
                 * the text has been changed.
                 */
                Log.d("PEACH", "afterTextChanged: " + s);

                if (s.charAt(s.length() - 1) == 32) {
                    //SpannableString string = new SpannableString("Bottom: span.\nBaseline: span");
                    ChipDrawable chip = ChipDrawable.createFromResource(requireContext(), R.xml.standalone_chip);
                    chip.setText(s.subSequence(lastLength, s.length()));
                    chip.setBounds(0, 0, chip.getIntrinsicWidth(), chip.getIntrinsicHeight());
                    s.setSpan(new ImageSpan(chip), lastLength, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    lastLength = s.length();
                    //s.setSpan(new ImageSpan(requireContext(), R.mipmap.ic_launcher), 7, 8, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    //s.setSpan(new ImageSpan(requireContext(), R.mipmap.ic_launcher, DynamicDrawableSpan.ALIGN_BASELINE), 22, 23, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                // Btn Save setEnabled(editable.length() > 0);
            }
        });

        binding.onAddParticipant/*.getEditText()*/.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                Log.d("PEACH", "onKey: HELLO");
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
        });
    }

    private boolean validateEmailAddress(Editable s) {
        String email = s.toString();

        if (email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(requireContext(), "Email Validated Successfully", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(requireContext(), "Invalid Email Address", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}