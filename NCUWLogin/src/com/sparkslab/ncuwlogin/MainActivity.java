package com.sparkslab.ncuwlogin;

import com.sparkslab.ncuwlogin.callbacks.Constant;
import com.sparkslab.ncuwlogin.callbacks.GeneralCallback;
import com.sparkslab.ncuwlogin.callbacks.Memory;
import com.sparkslab.ncuwlogin.libs.LoginHelper;

import butterknife.ButterKnife;
import butterknife.InjectView;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity implements OnClickListener {

	@InjectView(R.id.button_login)
	Button mLoginButton;

	@InjectView(R.id.button_logout)
	Button mLogoutButton;

	@InjectView(R.id.editText_user)
	EditText mUsernameEditText;

	@InjectView(R.id.editText_password)
	EditText mPasswordEditText;

	@InjectView(R.id.textView_debug)
	TextView mDebugTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ButterKnife.inject(this);
		setUpViews();
	}

	private void setUpViews() {
		mLoginButton.setOnClickListener(this);
		mLogoutButton.setOnClickListener(this);
		mUsernameEditText.setText(Memory.getString(this,
				Constant.MEMORY_KEY_USER, ""));
		mPasswordEditText.setText(Memory.getString(this,
				Constant.MEMORY_KEY_PASSWORD, ""));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		if (v == mLoginButton) {
			Memory.setString(this, Constant.MEMORY_KEY_USER, mUsernameEditText
					.getText().toString());
			Memory.setString(this, Constant.MEMORY_KEY_PASSWORD,
					mPasswordEditText.getText().toString());
			LoginHelper.login(this, mUsernameEditText.getText().toString(),
					mPasswordEditText.getText().toString(),
					new GeneralCallback() {

						@Override
						public void onSuccess() {
							showMessage(R.string.login_sucessful);
						}

						@Override
						public void onFail(String reason) {
							showMessage(reason);
						}
					});
		} else if (v == mLogoutButton) {
			LoginHelper.logout(this, new GeneralCallback() {

				@Override
				public void onSuccess() {
					showMessage(R.string.logout_sucessful);
				}

				@Override
				public void onFail(String reason) {
					showMessage(reason);
				}
			});
		}
	}

	private void showMessage(int messageRes) {
		mDebugTextView.setVisibility(View.VISIBLE);
		mDebugTextView.setText(getText(messageRes));
	}

	private void showMessage(CharSequence message) {
		mDebugTextView.setVisibility(View.VISIBLE);
		mDebugTextView.setText(message);
	}
}