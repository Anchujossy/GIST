package com.example.admin.myapplication.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.admin.myapplication.R;

import java.util.ArrayList;


public class GistInfoFragment extends Fragment {
    TextView textViewName;
    String str;
    Bundle bundle;
    TextView textViewCode;
    ArrayList<String> commentsArrayList = new ArrayList<>();
    LinearLayout llBottomSheet;
    EditText editTextUserName;
    EditText editTextPassword;
    Dialog loginDialog;
    String strUsername;
    LinearLayout llComments;
    String strPassword;
    EditText edtComments;
    ImageView imgComment;
    TextView textViewUserName;
    TextView textViewComments;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        str = PreferenceManager.getDefaultSharedPreferences(getContext()).getString("MYLABEL", "defaultStringIfNothingFound");


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_gist_info, container, false);
        settingView(view);
        callLoginDialog();
        imgComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String strComments = edtComments.getText().toString();
                commentsArrayList.add(strComments);


                    View childView;
                    LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    childView = inflater.inflate(R.layout.comment_adapter, null);
                    llComments.addView(childView);
                    TextView textViewUsername = childView.findViewById(R.id.tv_username);
                    textViewUsername.setText("commenter");
                    TextView textViewComments = childView.findViewById(R.id.tv_comment);
                    textViewComments.setText(strComments);
                    edtComments.setText("");




            }
        });


        return view;
    }

    /**
     * @param view is passed to getting view to this fragment
     */
    private void settingView(View view) {
        textViewName = view.findViewById(R.id.tv_name);
        textViewName.setText(str);
        textViewCode = view.findViewById(R.id.tv_code);
        textViewUserName = view.findViewById(R.id.tv_username);
        textViewComments = view.findViewById(R.id.tv_comment);
        imgComment = view.findViewById(R.id.img_comment);
        textViewCode.setText("public class AddTwoNumbers {\n" +
                "\n" +
                "   public static void main(String[] args) {\n" +
                "        \n" +
                "      int num1 = 5, num2 = 15, sum;\n" +
                "      sum = num1 + num2;\n" +
                "\n" +
                "      System.out.println(\"Sum of these numbers: \"+sum);\n" +
                "   }\n" +
                "}");
        edtComments = view.findViewById(R.id.edt_comments);
        llComments = view.findViewById(R.id.ll_comment);


        llBottomSheet = view.findViewById(R.id.bottom_panel);
    }

    /**
     * this method is used to popup dialog for github sigin
     */
    private void callLoginDialog() {
        loginDialog = new Dialog(getContext());
        loginDialog.setContentView(R.layout.login);
        loginDialog.setCancelable(false);
        final Button comment = loginDialog.findViewById(R.id.btn_comment);

        editTextUserName = loginDialog.findViewById(R.id.edt_username);

        final TextInputLayout layoutPassword = loginDialog.findViewById(R.id.text_input_password);
        final TextInputLayout layoutUserName = loginDialog.findViewById(R.id.text_input_username);
        strUsername = editTextUserName.getText().toString();

        editTextPassword = loginDialog.findViewById(R.id.edt_password);
        strPassword = editTextPassword.getText().toString();

        loginDialog.show();
        ImageButton imgClose = loginDialog.findViewById(R.id.img_close);
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginDialog.dismiss();
            }
        });
        comment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if ((editTextUserName.getText().length() < 1) && (editTextPassword.getText().length() < 1)) {
                    layoutUserName.setError("username is required");
                    layoutPassword.setError("Password is required");

                } else {

                    layoutPassword.setError(null);
                    layoutUserName.setError(null);
                    loginDialog.dismiss();

                }
            }
        });
    }
}




