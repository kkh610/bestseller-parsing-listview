package halla.icsw.bookparsing;



import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    String strHtml = "";
    TextView tv;
    String[] data2= new String[21];
    String[] data= new String[21];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.tv);
        tv.setText("검색중...");

        Handler h = new Handler() {
            public void handleMessage(Message msg) {
                HTMLParsing();
            } };
        new WorkerThread(h).start();





    }

    class WorkerThread extends Thread {
        Handler h;
        String strLine;
        WorkerThread(Handler h) { this.h = h; }
        public void run() {
            try {
                URL aURL = new URL("https://www.aladin.co.kr/m/mbest.aspx?BranchType=1&start=momain");
                BufferedReader in = new BufferedReader(new
                        InputStreamReader( aURL.openStream() ));
                while ((strLine = in.readLine()) != null)
                    if(strLine.contains(" style=\"line-height:120%;"))
                        strHtml += strLine;
                in.close();
                h.sendMessage(new Message());
            } catch (Exception e) {
                tv.setText("네트워크 에러 : " + e.toString() );

            }
        }
    }


    void HTMLParsing() {
        try{

            String strContent = "";
            int start = 0, end = 0;
            int start1=0;

//            String[] data2;
            for (int i = 1; i <= 20; i++) {



                start = strHtml.indexOf(" style=\"line-height:120%;", end);
                end = strHtml.indexOf("<", start);

                strContent+=i+"위 : "+strHtml.substring(start+31,end)+"\n";

                data[i]=i+"위 : "+strHtml.substring(start+31,end);




            }



            tv.setText(strContent);
        } catch(Exception e) {
            tv.setText("파싱 에러 : " + e.toString() );
        }
        String data2[]={data[1], data[2],data[3],data[4],data[5],data[6],data[7],data[8],data[9],data[10],data[11],data[12],
                data[13],data[14],data[15],data[16],data[17],data[18],data[19],data[20]};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data2);
        ListView listView=findViewById(R.id.list);
        listView.setAdapter(adapter);
    }
}