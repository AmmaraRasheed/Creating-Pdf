package com.example.rabia.createpdf;

import android.content.Context;
import android.os.Build;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private WebView webView;
    private DatabaseReference databaseReference;
    private String name,cnic,status,class1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView=findViewById(R.id.webview);
        databaseReference=FirebaseDatabase.getInstance().getReference().child("Student");
        databaseReference.child("4XKrAChJ6iY8wZuvrT3wlwRPCkh2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                name=dataSnapshot.child("name").getValue().toString();
                cnic=dataSnapshot.child("CNIC").getValue().toString();
                status=dataSnapshot.child("status").getValue().toString();
                class1=dataSnapshot.child("classuser").getValue().toString();
                String html="<!DOCTYPE html>\n" +
                        "<html>\n" +
                        "<head>\n" +
                        "\t<title></title>\n" +
                        "\t<link rel=\"stylesheet\" type=\"text/css\" href=\"file:android_asset/CreatePdf.css\">\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "\t<div>\n" +
                        "\t\t<h3>View My Record</h3>\n" +
                        "\t\t<table border=\"1\">\n" +
                        "\t\t\t<tr>\n" +
                        "\t\t\t\t<td>Name</td>\n" +
                        "\t\t\t\t<td id=\"name\">"+name+"</td>\n" +
                        "\t\t\t</tr>\n" +
                        "\t\t\t<tr>\n" +
                        "\t\t\t\t<td>Age</td>\n" +
                        "\t\t\t\t<td id=\"age\">"+status+"</td>\n" +
                        "\t\t\t</tr>\n" +
                        "\t\t\t<tr>\n" +
                        "\t\t\t\t<td>CNIC</td>\n" +
                        "\t\t\t\t<td id=\"cnic\">"+cnic+"</td>\n" +
                        "\t\t\t</tr>\n" +
                        "\t\t\t<tr>\n" +
                        "\t\t\t\t<td>Class</td>\n" +
                        "\t\t\t\t<td id=\"class\">"+class1+"</td>\n" +
                        "\t\t\t</tr>\n" +
                        "\t\t</table>\n" +
                        "\t</div>\n" +
                        "\n" +
                        "</body>\n" +
                        "</html>";
                webView.loadDataWithBaseURL(null,html,"text/html","utf-8",null);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void CreatePdf(View view){
        Context context=MainActivity.this;
        PrintManager printManager=(PrintManager)MainActivity.this.getSystemService(context.PRINT_SERVICE);
        PrintDocumentAdapter adapter=null;
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            adapter=webView.createPrintDocumentAdapter();
        }
        String JobName=getString(R.string.app_name) +"Document";
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
           PrintJob printJob=printManager.print(JobName,adapter,new PrintAttributes.Builder().build());
        }

    }
}
