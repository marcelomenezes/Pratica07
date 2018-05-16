package m.marcelomenezes.pratica07;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements ForecastTask.ForecastListener {

    private Button botaoProcurar;
    private EditText cidadeText;

    private String cidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botaoProcurar = (Button) findViewById(R.id.button_ok);

        cidadeText = (EditText) findViewById(R.id.edit_city);

        cidade = cidadeText.getText().toString();

        botaoProcurar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                buttonOkClick(cidadeText);
            }
        });
    }



    public void buttonOkClick(View view) {

        String cityName = ((EditText) findViewById(R.id.edit_city)).getText().toString();
    new ForecastTask(this).execute(cityName);
    }



    @Override
    public  void  showForecast(String  []  forecast)  {
        if  (forecast  ==  null)  {String  cityName  =  ((EditText)findViewById(R.id.edit_city)).getText().toString();
        Toast  toast  = Toast.makeText(this, "Previsão  não  encontrada  para  "  +  cityName,  Toast.LENGTH_SHORT);
        toast.show();
        }  else  {ArrayList<CharSequence>  data  =  new ArrayList<CharSequence>(Arrays.asList(forecast));
        Intent intent  =  new  Intent(this, ForecastActivity.class);
            intent.putCharSequenceArrayListExtra("data", data);
            startActivity(intent);
        }
    }

}
