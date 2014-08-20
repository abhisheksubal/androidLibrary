package in.abhisheksubal.pharmacognize;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity implements OnClickListener {
	EditText etName, etPassword;
	Button bLogin;
	List<NameValuePair> nameValuePairs;
	HttpResponse response ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.login_page);
		super.onCreate(savedInstanceState);
		etName = (EditText) findViewById(R.id.etName);
		etPassword = (EditText) findViewById(R.id.etPassword);
		bLogin = (Button) findViewById(R.id.bLogin);


		bLogin.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bLogin:
			new Thread(new Runnable() {
                public void run() {
                   final String result =  runPhpBackEnd(); 
                   runOnUiThread(new Runnable() {
                       public void run() {
                           Toast.makeText(Login.this,result, Toast.LENGTH_LONG).show();
                       }
                   });
                }
              }).start();        
			break;
		}

	}
	String  runPhpBackEnd()
	{
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost("http://www.pharmacognize.com/login-use.php");
		nameValuePairs = new ArrayList<NameValuePair>(2);
		NameValuePair usernamePair = new BasicNameValuePair("txtLogin",
				etName.getText().toString());
		NameValuePair passwordPair = new BasicNameValuePair("txtPass",
				etPassword.getText().toString());

		nameValuePairs.add(usernamePair);
		nameValuePairs.add(passwordPair);
		try {
			post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			response = client.execute(post);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResponseHandler<String> rHandler = new BasicResponseHandler();
		try {
			rHandler.handleResponse(response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	final String receivedValue =  response.toString();
	return receivedValue ;
	}

}
